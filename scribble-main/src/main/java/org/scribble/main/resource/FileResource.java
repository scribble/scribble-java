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

import java.nio.file.Path;

/**
 * This class represents the resource.
 *
 */
public class FileResource extends AbstractResource
{
	private final Path path;
	private java.io.InputStream _inputStream = null;

	/**
	 * The constructor.
	 * 
	 * @param path The optional resource path
	 * @param is The input stream
	 */
	public FileResource(Path path, java.io.InputStream is)
	{
		super(path.toString());
		this.path = path;
		this._inputStream = is;
	}
	
	public Path getPath()
	{
		return path;
	}
	
	@Override
	public boolean isFileResource()
	{
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public java.io.InputStream getInputStream()
	{
		return (this._inputStream);
	}
}
