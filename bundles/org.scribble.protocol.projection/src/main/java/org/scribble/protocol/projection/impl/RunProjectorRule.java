/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.projection.impl;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.*;
import org.scribble.protocol.util.ProtocolModelUtil;
import org.scribble.protocol.util.RunUtil;

/**
 * This class provides the Run implementation of the
 * projector rule.
 */
public class RunProjectorRule implements ProjectorRule {

	/**
	 * This method determines whether the projection rule is
	 * appropriate for the supplied model object.
	 * 
	 * @param obj The model object to be projected
	 * @return Whether the rule is relevant for the
	 * 				model object
	 */
	public boolean isSupported(ModelObject obj) {
		return(obj.getClass() == Run.class);
	}
	
	/**
	 * This method projects the supplied model object based on the
	 * specified role.
	 * 
	 * @param model The model object
	 * @param role The role
	 * @param l The model listener
	 * @return The projected model object
	 */
	public Object project(ProjectorContext context, ModelObject model,
					Role role, Journal l) {
		Run ret=new Run();
		Run source=(Run)model;
		
		ret.derivedFrom(source);
		
		java.util.List<Parameter> params=
			source.getParameters();
		int mappedIndex=-1;
		
		for (int i=0; i < params.size(); i++) {
			Parameter db=params.get(i);

			// Don't project declaration if same as role - this
			// will be done in the model include statement,
			// not the bindings
			if (db.getName().equals(role.getName()) == false) {
				
				Parameter dbcopy=
						new Parameter(db.getName());
					
				dbcopy.derivedFrom(db);
					
				ret.getParameters().add(dbcopy);
			} else {
				mappedIndex = i;
			}
		}
		
		if (source.getFromRole() != null) {
			ret.setFromRole((Role)context.project(source.getFromRole(), role, l));
		}
		
		if (source.getProtocolReference() != null) {
			boolean f_inner=false;
			
			// Store protocol against mapped role
			Protocol defn=RunUtil.getInnerProtocol(source.enclosingProtocol(), 
						source.getProtocolReference()); //source.getProtocol();
			
			if (defn == null) {
				// Check if protocol import defined for protocol
				ProtocolImport pi=ProtocolModelUtil.getProtocolImport(source.getModel(),
								source.getProtocolReference());
				
				if (pi == null) {
					l.error("Referenced protocol '"+source.getProtocolReference().getName()+
								"' not found within model or in import statements", source.getProperties());
				} else {
					ProtocolModel pm=context.getProtocolContext().getProtocolModel(pi, l);
					
					if (pm != null) {
						defn = pm.getProtocol();
					} else {
						l.error("Referenced protocol '"+source.getProtocolReference().getName()+
								"' could not be loaded from location '"+pi.getLocation()+"'",
								source.getProperties());
					}
				}
			} else {
				f_inner = true;
			}
			
			if (defn != null && mappedIndex != -1) {
				
				Role mappedRole=null;
				
				if (mappedIndex < defn.getParameterDefinitions().size()) {
					ParameterDefinition pd=defn.getParameterDefinitions().get(mappedIndex);
					
					if (pd.getType() != null) {
						l.error("Run parameter is not a role", source.getProperties());
					} else {
						mappedRole = new Role(pd.getName());
					}
				} else {
					l.error("Run parameter is not a role", source.getProperties());
				}
				
				if (mappedRole != null) {
					// If inner reference, then record interest
					// in project of definition against this mapped
					// role
					if (f_inner) {
						context.registerInterest(defn, mappedRole);
					}
					
					ret.setProtocolReference((ProtocolReference)context.project(
								source.getProtocolReference(), mappedRole, l));
				}
			} else {
				ret = null;
			}
		}

		return(ret);
	}
}
