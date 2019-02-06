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

import java.io.InputStream;

/**
 * This class represents the resource.
 * 
 * CHECKME: maybe refactor core Resource classes out of cli package (generalise)
 *
 */
public interface Resource
{
	public static final String INLINE_LOCATION = "-";

	/**
	 * This method returns the resource path.
	 * 
	 * @return The resource path -- the "full" path including import path prefix
	 */
	String getLocation();
	
	default boolean isInlineResource()
	{
		return false;
	}

	default boolean isFileResource()
	{
		return false;
	}

	/**
	 * This method returns an input stream containing the resource content. -- FIXME: a "fresh" stream?
	 * 
	 * @return The input stream
	 */
	InputStream getInputStream();
}
