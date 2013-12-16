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
package org.scribble.common.module;

import org.scribble.common.resources.Resource;
import org.scribble.model.ModelObject;
import org.scribble.model.Module;

/**
 * This class implements the validation context.
 *
 */
public class DefaultModuleContext implements ModuleContext {
	
	private Resource _resource=null;
	private Module _thisModule=null;
	private ModuleCache _modules=null;
	private java.util.Map<String,ModelObject> _importedMembers=new java.util.HashMap<String,ModelObject>();
	private ModuleLoader _loader=null;

	/**
	 * This is the constructor.
	 * 
	 * @param resource The resource
	 * @param module The module
	 * @param loader The loader
	 * @param cache The module cache
	 */
	public DefaultModuleContext(Resource resource, Module thisModule,
						ModuleLoader loader, ModuleCache cache) {
		_thisModule = thisModule;
		_loader = loader;
		_modules = (cache == null ? new ModuleCache() : cache);
	}
	
	/**
	 * This method returns the resource associated with the module.
	 * 
	 * @return The resource
	 */
	public Resource getResource() {
		return (_resource);
	}
	
	/**
	 * This method registers the named module.
	 * 
	 * @param module The module
	 */
	public void registerModule(Module module) {
		_modules.register(module);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Module importModule(String moduleName) {
		Module ret=_modules.getModule(moduleName);
		
		if (ret == null) {
			// Load module
			if (_loader != null) {
				ret = _loader.loadModule(moduleName);
				
				if (ret != null) {
					_modules.register(ret);
				}
			}
		}
		
		return (ret);
	}

	/**
	 * {@inheritDoc}
	 */
	public ModelObject getMember(String fqn) {
		int index=fqn.lastIndexOf('.');
		
		String moduleName=null;
		String memberName=null;
		
		if (index != -1) {
			moduleName=fqn.substring(0, index-1);
			memberName = fqn.substring(index+1);
		} else {
			// Local definition
			memberName = fqn;
		}
		
		return (getMember(moduleName, memberName));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ModelObject getMember(String moduleName, String memberName) {
		ModelObject ret=null;
		Module module=(moduleName == null ? _thisModule : _modules.getModule(moduleName));
		
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
	public ModelObject getImportedMember(String name) {
		return (_importedMembers.get(name));
	}

	/**
	 * {@inheritDoc}
	 */
	public ModelObject registerImportedMember(String module, String member, String alias) {
		ModelObject ret=getMember(module, member);
		
		if (ret != null) {
			if (alias != null) {
				_importedMembers.put(alias, ret);
			} else {
				_importedMembers.put(member, ret);
			}
		}
		
		return (ret);
	}
	
}
