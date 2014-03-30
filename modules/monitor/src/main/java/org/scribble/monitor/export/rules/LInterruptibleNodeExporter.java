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
import org.scribble.model.Message;
import org.scribble.model.ModelObject;
import org.scribble.model.PayloadElement;
import org.scribble.model.PayloadTypeDecl;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LInterruptible;
import org.scribble.monitor.model.Choice;
import org.scribble.monitor.model.Interruptible;
import org.scribble.monitor.model.Receive;
import org.scribble.monitor.model.Send;
import org.scribble.monitor.model.SessionType;

/**
 * This class exports an Interruptible into a session type
 * to be monitored.
 *
 */
public class LInterruptibleNodeExporter implements NodeExporter {

	/**
	 * {@inheritDoc}
	 */
	public void export(ModuleContext context, ExportState state, ModelObject mobj, SessionType type) {
		LInterruptible elem=(LInterruptible)mobj;
		
		Interruptible interruptibleNode=new Interruptible();
		
		type.getNodes().add(interruptibleNode);
		
		interruptibleNode.setBlockIndex(type.getNodes().size());
		
		LBlock block=elem.getBlock();
			
		NodeExporter ne=NodeExporterFactory.getNodeExporter(block);
			
		ne.export(context, state, block, type);
		
		// Export throws		
		if (elem.getThrows() != null && elem.getThrows().getMessages().size() > 0) {
			
			interruptibleNode.setThrows(type.getNodes().size());
			
			if (elem.getThrows().getMessages().size() > 1) {
				Choice choiceNode=new Choice();
				type.getNodes().add(choiceNode);
				
				for (Message mesg : elem.getThrows().getMessages()) {					
					choiceNode.getPathIndexes().add(type.getNodes().size());
					
					exportThrow(context, state, elem, type, mesg);
				}
			} else {
				exportThrow(context, state, elem, type, elem.getThrows().getMessages().get(0));
			}
		}
		
		// Export catches		
		if (elem.getCatches().size() > 0) {
			
			interruptibleNode.setCatches(type.getNodes().size());
			
			if (elem.getCatches().size() > 1 || elem.getCatches().get(0).getMessages().size() > 1) {
				Choice choiceNode=new Choice();
				type.getNodes().add(choiceNode);
				
				for (LInterruptible.Catch c : elem.getCatches()) {
					
					for (Message mesg : c.getMessages()) {
						choiceNode.getPathIndexes().add(type.getNodes().size());
						
						exportCatch(context, state, c, type, mesg);
					}
				}
			} else {
				exportCatch(context, state, elem.getCatches().get(0),
						type, elem.getCatches().get(0).getMessages().get(0));
			}
		}
	}
	
	protected void exportThrow(ModuleContext context, ExportState state, LInterruptible elem,
							SessionType type, Message mesg) {
		Send sendNode=new Send();
		sendNode.setOperator(mesg.getMessageSignature().getOperator());
		
		for (PayloadElement pe : mesg.getMessageSignature().getPayloadElements()) {
			
			// TODO: Need to provide utility functions for extracting the payload type
			// and make sure the context methods are as clear as possible
			
			PayloadTypeDecl ptype=elem.getModule().getTypeDeclaration(pe.getName());

			if (ptype == null) {
				ModelObject alias=context.getMember(pe.getName());
				
				if (alias instanceof PayloadTypeDecl) {
					ptype = (PayloadTypeDecl)alias;
				}
			}

			if (ptype != null) {
				sendNode.getTypes().add(ptype.getType());
			}
		}
		
		// TODO: Ned to cater for list of 'to' roles
		sendNode.setToRole(elem.getThrows().getToRoles().get(0).getName());
		
		type.getNodes().add(sendNode);
	}

	protected void exportCatch(ModuleContext context, ExportState state, LInterruptible.Catch elem,
			SessionType type, Message mesg) {
		// TODO: Handle parameter
		
		Receive recvNode=new Receive();
		recvNode.setOperator(mesg.getMessageSignature().getOperator());
		
		for (PayloadElement pe : mesg.getMessageSignature().getPayloadElements()) {
			PayloadTypeDecl ptype=elem.getModule().getTypeDeclaration(pe.getName());

			if (ptype == null) {
				ModelObject alias=context.getMember(pe.getName());
				
				if (alias instanceof PayloadTypeDecl) {
					ptype = (PayloadTypeDecl)alias;
				}
			}

			if (ptype != null) {
				recvNode.getTypes().add(ptype.getType());
			}
		}
		
		// TODO: Ned to cater for list of 'to' roles
		recvNode.setFromRole(elem.getRole().getName());
		
		type.getNodes().add(recvNode);
	}
}
