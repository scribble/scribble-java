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
package org.scribble.context;

import org.scribble.model.ModelObject;
import org.scribble.model.Module;
import org.scribble.resources.Resource;

/**
 * This class implements the validation context.
 *
 */
public class DefaultModuleContext implements ModuleContext {
	
	private Resource _resource=null;
	private Module _thisModule=null;
	private ModuleLoader _loader=null;

	/**
	 * This is the constructor.
	 * 
	 * @param resource The resource
	 * @param module The module
	 * @param loader The loader
	 */
	public DefaultModuleContext(Resource resource, Module thisModule,
						ModuleLoader loader) {
		_resource = resource;
		_thisModule = thisModule;
		_loader = loader;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Resource getResource() {
		return (_resource);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Module importModule(String moduleName) {
		return (_loader == null ? null : _loader.loadModule(moduleName));
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
		Module module=(moduleName == null ? _thisModule : _loader.loadModule(moduleName));
		
		if (module != null) {
			ret = module.getProtocol(memberName);
			
			if (ret == null) {
				ret = module.getTypeDeclaration(memberName);
			}
		}
		
		return (ret);
	}
	
}
