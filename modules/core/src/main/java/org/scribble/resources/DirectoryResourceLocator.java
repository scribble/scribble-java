/*
 * Copyright 2009-11 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.resources;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class provides a directory based resource locator.
 *
 */
public class DirectoryResourceLocator implements ResourceLocator
{
	private static final Logger LOG = Logger.getLogger(DirectoryResourceLocator.class.getName());
	
	private String[] _paths=null;

	/**
	 * This is the constructor for the directory resource
	 * locator, initialised with a ':' separated list
	 * of root directories.
	 * 
	 * @param paths The ':' separated list of directory paths
	 */
	public DirectoryResourceLocator(String paths) {
		_paths = paths.split(":");
	}

	public DirectoryResourceLocator(List<String> paths) {
		//_paths = paths.split(":");
		_paths = new String[paths.size()];
		_paths = paths.toArray(_paths);
	}
	
	/**
	 * This method returns the root location containing the supplied
	 * resource.
	 * 
	 * @return The resource's root location
	 */
	public String getResourceRoot(Resource resource) {
		String ret=null;
		
		for (String path : _paths) {
			String fullPath=path+java.io.File.separator+resource.getPath();
			
			java.io.File f=new java.io.File(fullPath);
			
			if (f.exists()) {
				ret = path;
				break;
			}
		}
		
		return (ret);
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource getResource(String path) {
		return searchResourceOnImportPaths(path);
	}

	private Resource openResource(String path, File f)
	{
		try
		{
			// ret = new InputStreamResource(relativePath, new java.io.FileInputStream(f));
			return new InputStreamResource(path, new FileInputStream(f)); // RAY
		}
		catch (Exception e)
		{
			//LOG.log(Level.SEVERE, "Failed to create file input stream for '" + f + "'", e);
			throw new RuntimeException("Failed to create file input stream for '" + f + "'", e);
		}
		//return null;
	}

	public Resource getResourceByFullPath(String path)
	{
		return openResource(path, openFile(path, true));
	}

	public Resource searchResourceOnImportPaths(String relativePath)
	{
		// Resource ret= getResourceByFullPath(relativePath); // Debatable
		File f = openFile(relativePath, false);
		if (f.isFile()) // relativePath is actually a full path
		{
			return openResource(relativePath, f);
		}

		// Find module
		for (String path : _paths)
		{
			String tmp = path;

			/*if (!fullPath.endsWith(java.io.File.separator)) {  // FIXME: cygwin/windows separators
				fullPath += java.io.File.separator;
			}*/

			String fullPath = tmp + relativePath;
			return openResource(fullPath, openFile(fullPath, true));
		}
		throw new RuntimeException("Couldn't open resource: " + relativePath);
	}
	
	private static File openFile(String path, boolean check)
	{
		File f = new File(path);
		if (check && !f.isFile())
		{
			throw new RuntimeException("File couldn't be opened: " + path);
		}
		return f;
	}
}
