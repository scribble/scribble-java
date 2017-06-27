/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.main.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.scribble.main.ScribbleException;

/**
 * This class provides a directory based resource locator.
 *
 */
// FIXME: rename exceptions
public class DirectoryResourceLocator extends ResourceLocator
{
	//private static final Logger LOG = Logger.getLogger(DirectoryResourceLocator.class.getName());  // TODO:
	
	private List<Path> impaths;

	/**
	 * This is the constructor for the directory resource
	 * locator, initialised with a ':' separated list
	 * of root directories.
	 * 
	 * @param paths The ':' separated list of directory paths
	 */
	public DirectoryResourceLocator(List<Path> paths)
	{
		this.impaths = new LinkedList<>(paths);
	}
	
	// FIXME: need to sort out what "getResource" should mean at level of ResourceLocator abstraction, e.g. if arg is specifically a Path or more abstract, whether it is the complete location or partial, etc
	@Override
	public FileResource getResource(Path path) throws ScribbleException
	{
		for (Path impath : this.impaths)
		{
			Path prefixedpath = impath.resolve(path);
			if (Files.exists(prefixedpath))
			{
				return openFileInputStreamResource(prefixedpath);
			}
		}
		throw new ScribbleException("Couldn't open resource: " + path);
	}

	// "full" path from working directory, as opposed to "relative" paths from import prefixes
	public static FileResource getResourceByFullPath(Path path) throws ScribbleException  // FIXME: should be abstracted out as front-end functionality, e.g. DirectoryResourceLocator, to find/load main module; then MainContext uses abstract ResourceLocator to load rest
	{
		if (!Files.exists(path))
		{
			throw new ScribbleException("File couldn't be opened: " + path);
		}
		return openFileInputStreamResource(path);
	}
	
	private static FileResource openFileInputStreamResource(Path path) throws ScribbleException
	{
		try
		{
			return new FileResource(path, Files.newInputStream(path));
		}
		catch (IOException e)
		{
			throw new ScribbleException(e);
		}
	}
}
