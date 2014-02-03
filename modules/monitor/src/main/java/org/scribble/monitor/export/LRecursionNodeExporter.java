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
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LRecursion;
import org.scribble.monitor.model.Recursion;
import org.scribble.monitor.model.SessionType;

/**
 * This class exports a Recursion into a session type
 * to be monitored.
 *
 */
public class LRecursionNodeExporter implements NodeExporter {

	/**
	 * {@inheritDoc}
	 */
	public void export(ModuleContext context, ExportState state, ModelObject mobj, SessionType type) {
		LRecursion elem=(LRecursion)mobj;
		
		Recursion recursionNode=new Recursion();
		
		type.getNodes().add(recursionNode);
		
		recursionNode.setBlockIndex(type.getNodes().size());
		
		state.registerLabelIndex(elem.getLabel(), type.getNodes().size());
		
		LBlock block=elem.getBlock();
			
		NodeExporter ne=NodeExporterFactory.getNodeExporter(block);
			
		ne.export(context, state, block, type);
		
		state.unregisterLabel(elem.getLabel());
	}
	
}
