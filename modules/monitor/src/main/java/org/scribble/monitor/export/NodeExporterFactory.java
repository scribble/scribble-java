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

import org.scribble.model.ModelObject;
import org.scribble.model.local.LBlock;
import org.scribble.model.local.LChoice;
import org.scribble.model.local.LContinue;
import org.scribble.model.local.LDo;
import org.scribble.model.local.LInterruptible;
import org.scribble.model.local.LParallel;
import org.scribble.model.local.LReceive;
import org.scribble.model.local.LRecursion;
import org.scribble.model.local.LSend;

/**
 * This class provides a factory for node exporters related to
 * local protocol objects.
 *
 */
public class NodeExporterFactory {
	
	private static java.util.Map<Class<? extends ModelObject>, NodeExporter> _nodeExporters=
							new java.util.HashMap<Class<? extends ModelObject>, NodeExporter>();
	
	static {
		_nodeExporters.put(LBlock.class, new LBlockNodeExporter());
		_nodeExporters.put(LChoice.class, new LChoiceNodeExporter());
		_nodeExporters.put(LContinue.class, new LContinueNodeExporter());
		_nodeExporters.put(LDo.class, new LDoNodeExporter());
		_nodeExporters.put(LInterruptible.class, new LInterruptibleNodeExporter());
		_nodeExporters.put(LParallel.class, new LParallelNodeExporter());
		_nodeExporters.put(LReceive.class, new LReceiveNodeExporter());
		_nodeExporters.put(LRecursion.class, new LRecursionNodeExporter());
		_nodeExporters.put(LSend.class, new LSendNodeExporter());
	}

	/**
	 * This method returns a node exporter associated with the
	 * supplied model object.
	 * 
	 * @param mobj The model object
	 * @return The node exporter, or null if not found
	 */
	public static NodeExporter getNodeExporter(ModelObject mobj) {
		return (_nodeExporters.get(mobj.getClass()));
	}
	
}
