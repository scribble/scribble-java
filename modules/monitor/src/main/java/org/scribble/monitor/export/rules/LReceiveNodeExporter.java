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
package org.scribble.monitor.export.rules;

import org.scribble.context.ModuleContext;
import org.scribble.model.ModelObject;
import org.scribble.model.PayloadElement;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.local.LReceive;
import org.scribble.monitor.model.Receive;
import org.scribble.monitor.model.SessionType;
import org.scribble.monitor.model.Parameter;

/**
 * This class exports a receive into a session type
 * to be monitored.
 *
 */
public class LReceiveNodeExporter implements NodeExporter {

	/**
	 * {@inheritDoc}
	 */
	public void export(ModuleContext context, ExportState state, ModelObject mobj, SessionType type) {
		LReceive recv=(LReceive)mobj;
		
		// TODO: Handle parameter
		
		Receive recvNode=new Receive();
		recvNode.setOperator(recv.getMessage().getMessageSignature().getOperator());
		
		for (PayloadElement pe : recv.getMessage().getMessageSignature().getPayloadElements()) {
			PayloadTypeDecl ptype=recv.getModule().getTypeDeclaration(pe.getName());

			if (ptype == null) {
				ModelObject alias=context.getMember(pe.getName());
				
				if (alias instanceof PayloadTypeDecl) {
					ptype = (PayloadTypeDecl)alias;
				}
			}

			if (ptype != null) {
				recvNode.getParameters().add(new Parameter(ptype.getType()));
			}
		}
		
		// TODO: Ned to cater for list of 'to' roles
		recvNode.setFromRole(recv.getFromRole().getName());
		
		type.getNodes().add(recvNode);
	}
	
}
