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
package org.scribble.parser.antlr;

/**
 * This interface defines the model adapter for the parser rules.
 *
 */
public interface ModelAdaptor {

	/**
	 * This method creates the model object(s) appropriate for the
	 * supplied stack of context, and returns them.
	 * 
	 * @param context The parser context
	 * @return The created model object
	 */
	public Object createModelObject(ParserContext context);
	
}
