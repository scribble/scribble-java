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
package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

public abstract class InteractionSeq<K extends ProtoKind>
		extends ScribNodeBase implements ProtoKindNode<K>
{
	// ScribTreeAdaptor#create constructor
	public InteractionSeq(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public InteractionSeq(InteractionSeq<K> node)
	{
		super(node);
	}
	
	public abstract List<? extends SessionNode<K>> getInteractionChildren();

	// "add", not "set"
	public void addScribChildren(List<? extends SessionNode<K>> elems)
	{
		// Cf. above getters and Scribble.g children order
		addChildren(elems);
	}
	
	@Override
	public abstract InteractionSeq<K> dupNode();

	public InteractionSeq<K> reconstruct(List<? extends SessionNode<K>> elems)
	{
		InteractionSeq<K> dup = dupNode();
		dup.addScribChildren(elems);
		dup.setDel(del());  // No copy
		return dup;
	}
	
	@Override
	public ScribNode visitChildren(AstVisitor nv) throws ScribException
	{
		List<SessionNode<K>> actions = new LinkedList<>();
		for (SessionNode<K> in : getInteractionChildren())
		{
			//ProtocolKindNode<K> visited = visitProtocolKindChildWithCastCheck(this, in, nv, ProtocolKindNode.class, in.getKind(), KIND_CAST);  
					// No: ProjectedChoiceDoPruning (and others?) needs to return null; CastCheck doesn't allow that  // CHECKME
					// TODO: make a unit test for this
			ScribNode visited = visitChild(in, nv);
			if (visited instanceof InteractionSeq<?>)
			{
				@SuppressWarnings("unchecked")
				InteractionSeq<K> tmp = (InteractionSeq<K>) visited;
				actions.addAll(tmp.getInteractionChildren());
			}
			else
			{
				@SuppressWarnings("unchecked")
				SessionNode<K> tmp = (SessionNode<K>) visited;
				actions.add(tmp);
			}
		}
		return reconstruct(actions);
	}
	
	public boolean isEmpty()
	{
		return getChildCount() == 0; //this.inters.isEmpty();
	}
	
	@Override
	public String toString()
	{
		return getInteractionChildren().stream().map(i -> i.toString())
				.collect(Collectors.joining("\n"));
	}
}
