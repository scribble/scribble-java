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

import org.antlr.runtime.CommonToken;
import org.scribble.model.Argument;
import org.scribble.model.ParameterDecl;
import org.scribble.model.ProtocolDecl;
import org.scribble.model.RoleDecl;
import org.scribble.model.RoleInstantiation;
import org.scribble.model.global.GBlock;
import org.scribble.model.global.GProtocolDefinition;
import org.scribble.model.global.GProtocolInstance;

/**
 * This class provides the model adapter for the 'globalProtocolDecl' parser rule.
 *
 */
public class GlobalProtocolDeclModelAdaptor extends AbstractModelAdaptor {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Object createModelObject(ParserContext context) {
		ProtocolDecl ret=null;
		
		if (context.peek() instanceof GBlock) {
			ret = new GProtocolDefinition();
			
			setEndProperties(ret, context.peek());
			
			((GProtocolDefinition)ret).setBlock((GBlock)context.pop());
		} else {
			ret = new GProtocolInstance();

			setEndProperties(ret, context.pop()); // consume ;

			((GProtocolInstance)ret).getRoleInstantiations().addAll((java.util.List<RoleInstantiation>)context.pop());
			
			if (context.peek() instanceof java.util.List<?>) {
				((GProtocolInstance)ret).getArguments().addAll((java.util.List<Argument>)context.pop());
			}
			
			StringBuffer protocol=new StringBuffer(((CommonToken)context.pop()).getText());
			
			// Check for module and set as separate property
			while (context.peek() instanceof CommonToken
					&& ((CommonToken)context.peek()).getText().equals(".")) {
				context.pop(); // consume '.'
				
				protocol.insert(0, ".");
				
				protocol.insert(0, ((CommonToken)context.pop()).getText());
			}
			
			((GProtocolInstance)ret).setMemberName(protocol.toString());		

			context.pop(); // instantiates
		}
		
		ret.getRoleDeclarations().addAll((java.util.List<RoleDecl>)context.pop());
		
		if (context.peek() instanceof java.util.List<?>) {
			ret.getParameterDeclarations().addAll((java.util.List<ParameterDecl>)context.pop());
		}
		
		ret.setName(((CommonToken)context.pop()).getText());
		
		context.pop(); // protocol
		
		setStartProperties(ret, context.pop()); // global
		
		context.push(ret);
		
		return ret;
	}

}
