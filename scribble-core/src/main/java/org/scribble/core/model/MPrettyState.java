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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.type.kind.ProtoKind;

public abstract class MPrettyState
		<L, A extends MAction<K>, S extends MPrettyState<L, A, S, K>, K extends ProtoKind>
		extends MState<L, A, S, K>
		implements MPrettyPrint
{
	public MPrettyState(Set<L> labs)  // Immutable singleton node
	{
		super(labs);
	}
	
	public String toVerboseString()
	{
		String s = "\"" + this.id + "\":[";
		Iterator<S> ss = this.succs.iterator();
		s += this.actions.stream().map(x -> x + "=\"" + ss.next().id + "\"")
				.collect(Collectors.joining(", "));
		return s + "]";
	}
	
	// Move up to MState?
	@Override
	public final String toDot()
	{
		StringBuilder b = new StringBuilder();
		b.append("digraph G {\n"); // rankdir=LR;\n
		b.append("compound = true;\n");
		b.append(toStateDot() + "\n");
		Set<S> ss = getReachableStates();
		ss.remove(this);  // Avoids generic cast of alternative, ss.add((S) this) -- or else do Set<MPrettyState<L, A, S, K>>
		ss.forEach(x -> b.append(x.toStateDot() + "\n"));
		b.append("}");
		return b.toString();
	}

	protected final String toStateDot()
	{
		StringBuilder b = new StringBuilder();
		b.append(toNodeDot());
		Iterator<A> as = getActions().iterator();
		Iterator<S> ss = getSuccs().iterator();
		while (as.hasNext())
		{
			A na = as.next();
			S ns = ss.next();
			b.append("\n" + toEdgeDot(na, ns));
		}
		return b.toString();
	}

	// dot node declaration
	// Override to change drawing declaration of "this" node
	protected String toNodeDot()
	{
		return getDotNodeId() + " [ " + getNodeLabel() + " ];";
	}
	
	protected String getDotNodeId()
	{
		return "\"" + this.id + "\"";
	}
	
	protected String getNodeLabel()
	{
		String labs = this.labs.toString();
		return "label=\"" + this.id + ": " + labs.substring(1, labs.length() - 1)
				+ "\"";
				// TODO: revise ?
	}

	// Override to change edge drawing from "this" as src
	protected String toEdgeDot(A msg, S next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(),
				next.getEdgeLabel(msg));  // CHECKME: next.getEdgeLabel or this.?
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(A msg)
	{
		return "label=\"" + msg + "\"";
	}

	protected String toEdgeDot(String src, String dest, String lab)
	{
		return src + " -> " + dest + " [ " + lab + " ];";
	}
	
	// Move up to MState?
	@Override
	public final String toAut()
	{
		Set<MPrettyState<L, A, S, K>> all = new HashSet<>();
		all.add(this);
		all.addAll(getReachableStates());  // The only way to avoid generic cast?  not ideal though
		String aut = "";
		int edges = 0;
		for (MPrettyState<L, A, S, K> s : all)
		{
			Iterator<A> as = s.getActions().iterator();
			Iterator<S> succs = s.getSuccs().iterator();
			for (; as.hasNext(); edges++)
			{
				A a = as.next();
				S succ = succs.next();
				String msg = a.toStringWithMsgIdHack();  // HACK
				aut += "\n(" + s.id + ",\"" + msg + "\"," + succ.id + ")";
			}
		}
		return "des (" + this.id + "," + edges + "," + all.size() + ")"
				+ aut + "\n";
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
}
