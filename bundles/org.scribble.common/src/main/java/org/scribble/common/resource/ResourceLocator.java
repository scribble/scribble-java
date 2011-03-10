/*
 * Copyright 2010 www.scribble.org
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
package org.scribble.common.resource;

/**
 * This interface represents a resource locator that can be used to
 * load artifacts relative to another resource being processed.
 *
 */
public interface ResourceLocator {

	/**
	 * This method can be used to retrieve the URI of a resource which
	 * is located at the specified URI, potentially relative to a resource
	 * that is being processed.
	 * 
	 * @param uri The relative URI of the resource to load
	 * @return The URI, or null if not found
	 * @throws Exception Failed to obtain URI
	 */
	public java.net.URI getResourceURI(String uri) throws Exception;

}
