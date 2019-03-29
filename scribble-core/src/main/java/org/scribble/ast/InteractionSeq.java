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
import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.job.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class InteractionSeq<K extends ProtocolKind>
		extends ScribNodeBase implements ProtocolKindNode<K>
{
	// ScribTreeAdaptor#create constructor
	public InteractionSeq(Token t)
	{
		super(t);
		this.inters = null;
	}

	// Tree#dupNode constructor
	public InteractionSeq(InteractionSeq<K> node)
	{
		super(node);
		this.inters = null;
	}
	
	@Override
	public abstract InteractionSeq<K> dupNode();
	
	public abstract List<? extends InteractionNode<K>> getInteractionChildren();
	
	public boolean isEmpty()
	{
		return getChildCount() == 0; //this.inters.isEmpty();
	}

	public InteractionSeq<K> reconstruct(List<? extends InteractionNode<K>> ins)
	{
		InteractionSeq<K> pd = dupNode();
		ScribDel del = del();
		ins.forEach(x -> pd.addChild(x));
		pd.setDel(del);  // No copy
		return pd;
	}
	
	@Override
	public ScribNode visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<InteractionNode<K>> actions = new LinkedList<>();
		for (InteractionNode<K> in : getInteractionChildren())
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
				InteractionNode<K> tmp = (InteractionNode<K>) visited;
				actions.add(tmp);
			}
		}
		return reconstruct(actions);
	}
	
	@Override
	public String toString()
	{
		return getInteractionChildren().stream().map(i -> i.toString())
				.collect(Collectors.joining("\n"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private final List<? extends InteractionNode<K>> inters;

	protected InteractionSeq(CommonTree source, List<? extends InteractionNode<K>> inters)
	{
		super(source);
		this.inters = inters;
	}
	
}
