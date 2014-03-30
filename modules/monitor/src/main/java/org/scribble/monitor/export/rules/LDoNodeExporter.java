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
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LDo;
import org.scribble.model.local.LProtocolDefinition;
import org.scribble.monitor.model.Do;
import org.scribble.monitor.model.SessionType;

/**
 * This class exports a Do into a session type
 * to be monitored.
 *
 */
public class LDoNodeExporter implements NodeExporter {

	/**
	 * {@inheritDoc}
	 */
	public void export(ModuleContext context, ExportState state, ModelObject mobj, SessionType type) {
		LDo elem=(LDo)mobj;
		
		Do doNode=new Do();
		
		type.getNodes().add(doNode);
		
		ModelObject mo=context.getMember(elem.getProtocol());
		
		// TODO: Need to handle cyclic dependencies - store protocol definition when
		// first used, against the index
		
		// TODO: Handle different instantiations of a protocol if parameterised or
		// using protocol instance
		
		if (mo instanceof LProtocolDefinition) {
			doNode.setProtocolIndex(type.getNodes().size());
			
			LBlock block=((LProtocolDefinition)mo).getBlock();
			
			NodeExporter ne=NodeExporterFactory.getNodeExporter(block);
			
			ne.export(context, state, block, type);
		}
	}
	
}
