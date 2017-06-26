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
package org.scribble.model;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;

// Helper class for EndpointGraphBuilder -- can access the protected setters of S
public abstract class GraphBuilderUtil
		<L,                             // Labels on states (cosmetic)
		 A extends MAction<K>,          // Action type: labels on edges
		 S extends MState<L, A, S, K>,  // State type
		 K extends ProtocolKind>        // Global/local actions/states -- Need to quantify K explicitly
{
	protected S entry;
	protected S exit;   // Tracking exit is convenient for merges (otherwise have to generate dummy merge nodes)
	
	protected GraphBuilderUtil()
	{

	}
	
	protected void init(S entry, S exit)  // Should be used by subclass constructor and reset
	{
		this.entry = entry;//newState(Collections.emptySet());
		this.exit = exit;//newState(Collections.emptySet());
	}
	
	// Separated from constructor in order to use newState
	public abstract void reset();
	
	//public abstract S newState(Set<RecVar> labs);
	
	public void addEntryLabel(L lab)
	{
		this.entry.addLabel(lab);
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
	
	protected final void removeEdgeAux(S s, A a, S succ) throws ScribbleException  // Exception necessary?
	{
		s.removeEdge(a, succ);
	}

	public S getEntry()
	{
		return this.entry;
	}

	public void setEntry(S entry)
	{
		this.entry = entry;
	}

	public S getExit()
	{
		return this.exit;
	}

	public void setExit(S exit)
	{
		this.exit = exit;
	}
}
