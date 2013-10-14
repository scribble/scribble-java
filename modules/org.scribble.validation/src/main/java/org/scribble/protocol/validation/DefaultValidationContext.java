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
package org.scribble.protocol.validation;

import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Module;

/**
 * This class implements the validation context.
 *
 */
public class DefaultValidationContext implements ValidationContext {
	
	private Module _thisModule=null;
	private java.util.Map<String,Module> _modules=new java.util.HashMap<String,Module>();
	private java.util.Map<String,ModelObject> _aliases=new java.util.HashMap<String,ModelObject>();
	private ComponentLoader _loader=null;

	/**
	 * This is the default constructor.
	 */
	public DefaultValidationContext() {
		
	}
	
	/**
	 * This is the constructor.
	 * 
	 * @param loader The loader
	 */
	public DefaultValidationContext(Module thisModule, ComponentLoader loader) {
		_thisModule = thisModule;
		_loader = loader;
	}
	
	/**
	 * This method registers the named module.
	 * 
	 * @param moduleName The module name
	 * @param module The module
	 */
	public void registerModule(String moduleName, Module module) {
		_modules.put(moduleName, module);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Module importModule(String module) {
		if (!_modules.containsKey(module)) {
			// Load module
			if (_loader != null) {
				Module m=_loader.loadModule(module);
				
				if (m != null) {
					_modules.put(module, m);
				}
			}
		}
		
		return (_modules.get(module));
	}

	/**
	 * {@inheritDoc}
	 */
	public ModelObject getMember(String fqn) {
		ModelObject ret=null;
		int index=fqn.lastIndexOf('.');
		
		Module module=null;
		String memberName=null;
		
		if (index != -1) {
			String moduleName=fqn.substring(0, index-1);
			memberName = fqn.substring(index+1);
			
			module = _modules.get(moduleName);
		} else {
			// Local definition
			memberName = fqn;
			module = _thisModule;
		}
		
		if (module != null) {
			ret = module.getProtocol(memberName);
			
			if (ret == null) {
				ret = module.getTypeDeclaration(memberName);
			}
		}
		
		return (ret);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ModelObject getMember(String moduleName, String memberName) {
		ModelObject ret=null;
		Module module=_modules.get(moduleName);
		
		if (module != null) {
			ret = module.getProtocol(memberName);
			
			if (ret == null) {
				ret = module.getTypeDeclaration(memberName);
			}
		}
		
		return (ret);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ModelObject getAlias(String alias) {
		return (_aliases.get(alias));
	}

	/**
	 * {@inheritDoc}
	 */
	public ModelObject registerAlias(String module, String member, String alias) {
		ModelObject ret=getMember(module, member);
		
		if (ret != null) {
			_aliases.put(alias, ret);
		}
		
		return (ret);
	}
	
}
