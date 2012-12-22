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

import org.scribble.protocol.model.ImportDecl;
import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.model.Parameter;
import org.scribble.protocol.model.PayloadType;
import org.scribble.protocol.model.PayloadTypeDecl;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.RoleInstantiation;

/**
 * This is the base implementation for the Model Adaptor interface.
 * This class provides capabilities that are independent of whether the
 * model is global or local.
 *
 */
public class BaseModelAdaptor implements ModelAdaptor {

    private static final Logger LOG=Logger.getLogger(BaseModelAdaptor.class.getName());

    private static final java.util.Map<String,Class<?>> TOKEN_CLASS=
            new java.util.HashMap<String, Class<?>>();
    private static final java.util.Map<String,Class<?>> PARSER_GROUPING_RULE_CLASS=
            new java.util.HashMap<String, Class<?>>();
    private static final java.util.Map<String,Class<?>> LIST_CLASS=
            new java.util.HashMap<String, Class<?>>();
    private static final java.util.Map<String,String> PROPERTY_TOKENS=
            new java.util.HashMap<String, String>();
    private static final java.util.List<String> CLEAR_TOKEN_LIST_RULES=
            new java.util.Vector<String>();

    static {
        // The map of root tokens, that begin a grammer
        // rule, and the model class they are associated
        // with
    	
    	// Common model
        TOKEN_CLASS.put("package", org.scribble.protocol.model.Package.class);
        TOKEN_CLASS.put("import", org.scribble.protocol.model.ImportDecl.class);
        TOKEN_CLASS.put("type", org.scribble.protocol.model.PayloadTypeDecl.class);
        TOKEN_CLASS.put("sig", org.scribble.protocol.model.Parameter.class);
        TOKEN_CLASS.put("role", org.scribble.protocol.model.Role.class);

        // This may define the model object that should be
        // created after processing the named grammer rule
        PARSER_GROUPING_RULE_CLASS.put("payloadType", PayloadType.class);
        PARSER_GROUPING_RULE_CLASS.put("messageSignature", MessageSignature.class);
        PARSER_GROUPING_RULE_CLASS.put("roleInstantiation", RoleInstantiation.class);
        PARSER_GROUPING_RULE_CLASS.put("roleName", Role.class);
        PARSER_GROUPING_RULE_CLASS.put("packageName", String.class);
        PARSER_GROUPING_RULE_CLASS.put("simpleName", String.class);

        // Defines the list element base type associated with a
        // property name
        LIST_CLASS.put("roles", Role.class);
        LIST_CLASS.put("parameters", Parameter.class);
        LIST_CLASS.put("arguments", MessageSignature.class);
        LIST_CLASS.put("roleInstantiations", RoleInstantiation.class);
        LIST_CLASS.put("typeDeclarations", PayloadTypeDecl.class);
        LIST_CLASS.put("types", PayloadType.class);
        LIST_CLASS.put("imports", ImportDecl.class);
        
        // When a particular class has multiple properties of the
        // same type, then a preceding token must be used to
        // determine which property to set. This map provides the
        // mapping between the property name and the token.
        PROPERTY_TOKENS.put("payloadTypeDecl:format", "<");
        PROPERTY_TOKENS.put("payloadType:variable", ":");
        PROPERTY_TOKENS.put("payloadType:type", "");
        PROPERTY_TOKENS.put("roleInstantiation:name", "");
        PROPERTY_TOKENS.put("roleInstantiation:alias", "as");
        
        CLEAR_TOKEN_LIST_RULES.add("payloadType");
        CLEAR_TOKEN_LIST_RULES.add("roleInstantiation");
    }
    
    /**
     * {@inheritDoc}
     */
	public Object create(String token) {
		Object ret=null;
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
        
        return (ret);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Class<?> getModelClassForRule(String ruleName) {
		return (PARSER_GROUPING_RULE_CLASS.get(ruleName));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Class<?> getListElementClass(String propertyName) {
		return (LIST_CLASS.get(propertyName));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean shouldClearToken(String ruleName) {
		return (CLEAR_TOKEN_LIST_RULES.contains(ruleName));
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTokenForRuleAndProperty(String ruleName, String propertyName) {
		return (PROPERTY_TOKENS.get(ruleName+":"+propertyName));
	}

}
