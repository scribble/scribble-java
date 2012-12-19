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
package org.scribble.protocol.parser.antlr;

/**
 * This class provides capabilities to aid building the model associated
 * with a parsed protocol module.
 *
 */
public interface ModelAdaptor {

	/**
	 * This method creates a model object associated with the supplied
	 * token.
	 * 
	 * @param token The token
	 * @return The model object, or null if none associated with the token
	 */
	public Object create(String token);
	
	/**
	 * This method returns a model class associated with the supplied
	 * rule name.
	 * 
	 * @param ruleName The rule name
	 * @return The model class, or null if not applicable for the rule name
	 */
	public Class<?> getModelClassForRule(String ruleName);
	
	/**
	 * This method returns a list element's model class associated with the supplied
	 * property name.
	 * 
	 * @param propertyName The property name
	 * @return The model class, or null if not applicable for the property name
	 */
	public Class<?> getListElementClass(String propertyName);
	
	/**
	 * This method determines whether the current token
	 * should be cleared based on the supplied rule name.
	 * 
	 * @param ruleName The rule name
	 * @return Whether the current token should be removed
	 */
	public boolean shouldClearToken(String ruleName);
	
	/**
	 * This method identifies a protocol activity token associated with a parsing
	 * rule name and property name on a model object currently being processed.
	 * 
	 * @param ruleName The current parsing rule name
	 * @param propertyName The property name on the current model object
	 * @return
	 */
	public String getTokenForRuleAndProperty(String ruleName, String propertyName);
	
}
