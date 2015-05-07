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

import java.nio.file.Path;


/**
 * This interface provides the resource location capability.
 *
 */
public interface IResourceLocator {

	/**
	 * This method obtains the resource associated with the
	 * supplied path.
	 * 
	 * @param path The resource path
	 * @return The resource, or null if not found
	 */
	@Deprecated
	public Resource getResource(String path);

	public Resource getResource(Path path);  // Should be something more abstract, some kind of URI

	//public Resource getResourceByFullPath(String path);  // Doesn't search import paths
	//public Resource getResource(String path);  // Tries without import paths first, then searches import paths
}
