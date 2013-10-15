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
package org.scribble.parser;

/**
 * This interface provides the resource location capability.
 *
 */
public interface ResourceLocator {

	/**
	 * This method obtains the input stream associated with the
	 * module for the supplied name.
	 * 
	 * @param name The module name
	 * @return The input stream, or null if not found
	 */
	public java.io.InputStream getModule(String name);

}
