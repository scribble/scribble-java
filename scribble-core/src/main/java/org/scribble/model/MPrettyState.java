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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.type.kind.ProtocolKind;

public abstract class MPrettyState
		<L, A extends MAction<K>, S extends MPrettyState<L, A, S, K>, K extends ProtocolKind>
		extends MState<L, A, S, K>
		implements MPrettyPrint
{
	public MPrettyState(Set<L> labs)  // Immutable singleton node
	{
		super(labs);
	}
	
	public String toLongString()
	{
		String s = "\"" + this.id + "\":[";
		Iterator<S> ss = this.succs.iterator();
		s += this.actions.stream().map((a) -> a + "=\"" + ss.next().id + "\"").collect(Collectors.joining(", "));
		return s + "]";
	}
	
	@Override
	public final String toDot()
	{
		String s = "digraph G {\n" // rankdir=LR;\n
				+ "compound = true;\n";
		s += toDot(new HashSet<>());
		return s + "\n}";
	}

	//protected final String toDot(Set<S> seen)
	protected final String toDot(Set<MPrettyState<L, A, S, K>> seen)
	{
		seen.add(this);
		String dot = toNodeDot();
		//for (Entry<A, S> e : this.edges.entrySet())
		for (int i = 0; i < this.actions.size(); i ++)
		{
			/*A msg = e.getKey();
			S p = e.getValue();*/
			A a = this.actions.get(i);
			S s = this.succs.get(i);
			dot += "\n" + toEdgeDot(a, s);
			if (!seen.contains(s))
			{
				dot += "\n" + s.toDot(seen);
			}
		}
		return dot;
	}

	protected final String toEdgeDot(String src, String dest, String lab)
	{
		return src + " -> " + dest + " [ " + lab + " ];";
	}

	// dot node declaration
	// Override to change drawing declaration of "this" node
	protected String toNodeDot()
	{
		return getDotNodeId() + " [ " + getNodeLabel() + " ];";
	}
	
	protected String getNodeLabel()
	{
		String labs = this.labs.toString();
		//return "label=\"" + labs.substring(1, labs.length() - 1) + "\"";
		return "label=\"" + this.id + ": " + labs.substring(1, labs.length() - 1) + "\"";  // FIXME
	}
	
	protected String getDotNodeId()
	{
		return "\"" + this.id + "\"";
	}

	// Override to change edge drawing from "this" as src
	protected String toEdgeDot(A msg, S next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));  // CHECKME: next.getEdgeLabel or this.?
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(A msg)
	{
		return "label=\"" + msg + "\"";
	}
	
	@Override
	public final String toAut()
	{
		Set<MPrettyState<L, A, S, K>> all = new HashSet<>();
		all.add(this);
		all.addAll(getReachableStates(this));
		String aut = "";
		int edges = 0;
		Set<Integer> seen = new HashSet<>();
		for (MPrettyState<L, A, S, K> s : all)
		{
			if (seen.contains(s.id))
			{
				continue;
			}
			seen.add(s.id);
			Iterator<A> as = s.getAllActions().iterator();
			Iterator<S> ss = s.getAllSuccessors().iterator();
			for (; as.hasNext(); edges++)
			{
				A a = as.next();
				S succ = ss.next();
				String msg = a.toStringWithMessageIdHack();  // HACK
				aut += "\n(" + s.id + ",\"" + msg + "\"," + succ.id + ")";
			}
		}
		return "des (" + this.id + "," + edges + "," + all.size() + ")" + aut + "\n";
	}

	@Override
	public int hashCode()
	{
		int hash = 71;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof MPrettyState))
		{
			return false;
		}
		return super.equals(o);  // Checks canEquals
	}

	@Override
	public String toString()
	{
		return Integer.toString(this.id);  // FIXME -- ?
	}
}
