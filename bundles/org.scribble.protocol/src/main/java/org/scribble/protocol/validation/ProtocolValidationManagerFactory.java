/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.protocol.validation;

/**
 * This is the factory class for obtaining the validation
 * manager.
 *
 */
public class ProtocolValidationManagerFactory {

	/**
	 * This method returns the validation manager.
	 * 
	 * @return The validation manager
	 */
	public static ProtocolValidationManager getValidationManager() {
		return(m_instance);
	}
	
	private static ProtocolValidationManager m_instance=
			new org.scribble.protocol.validation.DefaultProtocolValidationManager();
}
