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

import java.util.logging.Logger;

import org.scribble.protocol.model.global.GActivity;
import org.scribble.protocol.model.global.GBlock;
import org.scribble.protocol.model.global.GChoice;
import org.scribble.protocol.model.global.GContinue;
import org.scribble.protocol.model.global.GDo;
import org.scribble.protocol.model.global.GMessage;
import org.scribble.protocol.model.global.GParallel;
import org.scribble.protocol.model.global.GProtocol;
import org.scribble.protocol.model.global.GRecursion;
import org.scribble.protocol.model.global.GSpawn;

public class GlobalModelAdaptor extends BaseModelAdaptor {

    private static final Logger LOG=Logger.getLogger(GlobalModelAdaptor.class.getName());

    private static final java.util.Map<String,Class<?>> TOKEN_CLASS=
            new java.util.HashMap<String, Class<?>>();
    private static final java.util.List<String> CLEAR_TOKEN_LIST_RULES=
            new java.util.Vector<String>();
    private static final java.util.Map<String,Class<?>> PARSER_GROUPING_RULE_CLASS=
            new java.util.HashMap<String, Class<?>>();
    private static final java.util.Map<String,Class<?>> LIST_CLASS=
            new java.util.HashMap<String, Class<?>>();
    private static final java.util.Map<String,String> PROPERTY_TOKENS=
            new java.util.HashMap<String, String>();

    static {
        // The map of root tokens, that begin a grammer
        // rule, and the model class they are associated
        // with
    	
    	// Global model
        TOKEN_CLASS.put("choice", GChoice.class);
        TOKEN_CLASS.put("continue", GContinue.class);
        TOKEN_CLASS.put("do", GDo.class);
        TOKEN_CLASS.put("par", GParallel.class);
        TOKEN_CLASS.put("global", GProtocol.class);
        TOKEN_CLASS.put("rec", GRecursion.class);
        TOKEN_CLASS.put("spawn", GSpawn.class);

        // Clear token list - determines whether prior to processing
        // a list of tokens, the 'current token' should be cleared

    
        // This may define the model object that should be
        // created after processing the named grammer rule
        PARSER_GROUPING_RULE_CLASS.put("message", GMessage.class);
        PARSER_GROUPING_RULE_CLASS.put("globalInteractionBlock", GBlock.class);

        
        LIST_CLASS.put("contents", GActivity.class);
        LIST_CLASS.put("blocks", GBlock.class);
        LIST_CLASS.put("paths", GBlock.class);

        // When a particular class has multiple properties of the
        // same type, then a preceding token must be used to
        // determine which property to set. This map provides the
        // mapping between the property name and the token.
        PROPERTY_TOKENS.put("message:fromRole", "from");
        PROPERTY_TOKENS.put("message:toRole", "to");        
        PROPERTY_TOKENS.put("choice:role", "at");
    }
    
    /**
     * {@inheritDoc}
     */
	public Object create(String token) {
		Object ret=super.create(token);
		
		if (ret == null) {
	        Class<?> cls=TOKEN_CLASS.get(token);
	        
	        LOG.fine("Token class for '"+token
	                +"' is: "+cls);
	
	        if (cls != null) {
	            try {
	                ret = cls.newInstance();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
		}
        
        return (ret);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Class<?> getModelClassForRule(String ruleName) {
		Class<?> ret=super.getModelClassForRule(ruleName);
		
		if (ret == null) {
			ret = PARSER_GROUPING_RULE_CLASS.get(ruleName);
		}
		
		return (ret);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Class<?> getListElementClass(String propertyName) {
		Class<?> ret=super.getListElementClass(propertyName);
		
		if (ret == null) {
			ret = LIST_CLASS.get(propertyName);
		}
		
		return (ret);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean shouldClearToken(String ruleName) {
		return (super.shouldClearToken(ruleName) || CLEAR_TOKEN_LIST_RULES.contains(ruleName));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getTokenForRuleAndProperty(String ruleName, String propertyName) {
		String ret=super.getTokenForRuleAndProperty(ruleName, propertyName);
		
		if (ret == null) {
			ret = PROPERTY_TOKENS.get(ruleName+":"+propertyName);
		}
		
		return (ret);
	}

}
