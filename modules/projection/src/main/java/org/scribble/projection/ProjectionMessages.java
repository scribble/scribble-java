/*
 * Copyright 2009-14 www.scribble.org
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
package org.scribble.projection;

/**
 * This class is responsible for returning the projection messages
 * used by the rules.
 *
 */
public class ProjectionMessages {

	/**
	 * This method returns the message associated with the supplied key.
	 * 
	 * @param key The key
	 * @return The message
	 */
	public static final String getMessage(String key) {
		return (java.util.ResourceBundle.getBundle("scribble.projection.Messages").getString(key));
	}
}
