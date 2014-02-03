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
package org.scribble.monitor.export;

import org.scribble.common.module.ModuleContext;
import org.scribble.model.ModelObject;
import org.scribble.model.PayloadElement;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.local.LSend;
import org.scribble.monitor.model.Send;
import org.scribble.monitor.model.SessionType;

/**
 * This class exports a send into a session type
 * to be monitored.
 *
 */
public class LSendNodeExporter implements NodeExporter {

	/**
	 * {@inheritDoc}
	 */
	public void export(ModuleContext context, ExportState state, ModelObject mobj, SessionType type) {
		LSend send=(LSend)mobj;
		
		// TODO: Handle parameter
		
		Send sendNode=new Send();
		sendNode.setOperator(send.getMessage().getMessageSignature().getOperator());
		
		for (PayloadElement pe : send.getMessage().getMessageSignature().getPayloadElements()) {
			
			// TODO: Need to provide utility functions for extracting the payload type
			// and make sure the context methods are as clear as possible
			
			PayloadTypeDecl ptype=send.getModule().getTypeDeclaration(pe.getName());

			if (ptype == null) {
				ModelObject alias=context.getImportedMember(pe.getName());
				
				if (alias instanceof PayloadTypeDecl) {
					ptype = (PayloadTypeDecl)alias;
				}
			}

			if (ptype != null) {
				sendNode.getTypes().add(ptype.getType());
			}
		}
		
		// TODO: Need to cater for list of 'to' roles
		sendNode.setToRole(send.getToRoles().get(0).getName());
		
		type.getNodes().add(sendNode);
	}
	
}
