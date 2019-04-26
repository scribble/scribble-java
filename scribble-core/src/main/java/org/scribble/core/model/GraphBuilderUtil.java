/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.core.model;

import org.scribble.core.type.kind.ProtoKind;

// Helper class for (Endpoint)GraphBuilder -- can access the protected setters of S
public abstract class GraphBuilderUtil
		<L,                             // Labels on states (cosmetic)
		 A extends MAction<K>,          // Action type: labels on edges
		 S extends MState<L, A, S, K>,  // State type
		 K extends ProtoKind>           // Global/local actions/states -- Need to quantify K explicitly
{
	public final ModelFactory mf;  // N.B. new states should be made by this.newState, not this.ef.newEState
	
	// Doesn't call reset
	protected GraphBuilderUtil(ModelFactory mf)
	{
		this.mf = mf;
	}
	
	protected abstract void reset();
	
	//public abstract S newState(L labs);  // Doesn't factor out well with SState, doesn't use L and takes an SConfig
	
	protected void addEntryLabAux(S s, L lab)
	{
		s.addLabel(lab);
	}

	public void addEdge(S s, A a, S succ)
	{
		addEdgeAux(s, a, succ);
	}

	// Just a visibility workaround helper -- cf. addEdge: public method that may be overridden
	protected final void addEdgeAux(S s, A a, S succ)
	{
		s.addEdge(a, succ);
	}
	
	protected void removeEdgeAux(S s, A a, S succ) //throws ScribException  // Exception necessary?
	{
		s.removeEdge(a, succ);
	}
}
