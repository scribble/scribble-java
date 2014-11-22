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
package org.scribble.trace.parser.antlr;

/**
 * This interface represents the context used by
 * the parser.
 *
 */
public interface ParserContext {
	
	/**
	 * This method removes a component from the context
	 * and returns it to the caller.
	 * 
	 * @return The popped component
	 */
	public Object pop();
	
	/**
	 * This method accesses the current component
	 * from the context and returns it to the caller.
	 * This operation does not remove the current
	 * component from the context.
	 * 
	 * @return The current component
	 */
	public Object peek();
	
	/**
	 * This method adds a component to the parser
	 * context.
	 * 
	 * @param obj The new component
	 */
	public void push(Object obj);

}
