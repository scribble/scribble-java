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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.runtime.Token;
import org.scribble.model.ModelObject;

/**
 * This interface represents the context used by
 * the parser.
 *
 */
public class DefaultParserContext implements ParserContext {
	
	private static final Logger LOG=Logger.getLogger(DefaultParserContext.class.getName());
	
	private java.util.Stack<Object> _components=new java.util.Stack<Object>();
	private java.util.Map<String,Object> _properties=new java.util.HashMap<String,Object>();
	
	/**
	 * {@inheritDoc}
	 */
	public Object pop() {
		Object child=_components.peek();
		
        if (child instanceof Token) {
            Token token=(Token)child;
            
            _properties.put(ModelObject.START_LINE, token.getLine());                    
            _properties.put(ModelObject.START_COLUMN, token.getCharPositionInLine());
            
            if (!_properties.containsKey(ModelObject.END_LINE)) {
                _properties.put(ModelObject.END_LINE, token.getLine());                    
                _properties.put(ModelObject.END_COLUMN, token.getCharPositionInLine()
                        +token.getText().length());
            }
        } else if (child instanceof ModelObject) {
            ModelObject chobj=(ModelObject)child;
            
            _properties.put(ModelObject.START_LINE, chobj.getProperties().get(ModelObject.START_LINE));                    
            _properties.put(ModelObject.START_COLUMN, chobj.getProperties().get(ModelObject.START_COLUMN));
        
            if (chobj.getProperties().containsKey(ModelObject.END_LINE)) {
                
                _properties.put(ModelObject.END_LINE, chobj.getProperties().get(ModelObject.END_LINE));                    
                _properties.put(ModelObject.END_COLUMN, chobj.getProperties().get(ModelObject.END_COLUMN));
            }
        }
		
		return (_components.pop());
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object peek() {
		return (_components.peek());
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void push(Object obj) {
		_components.push(obj);
		
		// Update model object with location info from previously popped components
		if (obj instanceof ModelObject) {
			((ModelObject)obj).getProperties().putAll(_properties);
			
			if (LOG.isLoggable(Level.FINE)) {
				LOG.fine("Properties for '"+obj+"' are "+_properties);
			}
		}

		_properties.clear();
	}

}
