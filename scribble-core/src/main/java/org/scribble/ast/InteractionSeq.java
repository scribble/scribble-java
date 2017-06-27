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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class InteractionSeq<K extends ProtocolKind> extends ScribNodeBase implements ProtocolKindNode<K>
{
	private final List<? extends InteractionNode<K>> inters;
	
	/*@SuppressWarnings("unchecked")
	private final Function<ScribNode, InteractionNode<K>> cast = (n) -> (InteractionNode<K>) n;*/

	protected InteractionSeq(CommonTree source, List<? extends InteractionNode<K>> inters)
	{
		super(source);
		this.inters = inters;
	}
	
	public abstract InteractionSeq<K> reconstruct(List<? extends InteractionNode<K>> ins);
	
	@SuppressWarnings("unchecked")
	private final Function<ScribNode, ProtocolKindNode<K>> KIND_CAST = (n) -> (ProtocolKindNode<K>) n;
	
	@Override
	public ScribNode visitChildren(AstVisitor nv) throws ScribbleException
	{
		//List<? extends InteractionNode<K>> actions = visitChildListWithStrictClassCheck(this, this.actions, nv);
		//List<InteractionNode<K>> actions = visitChildListWithCastCheck(this, this.inters, nv, InteractionNode.class, getKind(), this.cast);  // Maybe too strict (e.g. rec unfolding maybe shouldn't return the rec)
		List<InteractionNode<K>> actions = new LinkedList<>();
		for (InteractionNode<K> in : this.inters)
		{
			//ScribNode visited = visitChild(in, nv);
			ProtocolKindNode<K> visited = visitProtocolKindChildWithCastCheck(this, in, nv, ProtocolKindNode.class, in.getKind(), KIND_CAST);
			if (visited instanceof InteractionSeq<?>)
			{
				InteractionSeq<K> tmp = (InteractionSeq<K>) visited;
				actions.addAll(tmp.inters);
			}
			else
			{
				InteractionNode<K> tmp = (InteractionNode<K>) visited;
				actions.add(tmp);
			}
		}
		return reconstruct(actions);
	}
	
	public List<? extends InteractionNode<K>> getInteractions()
	{
		return Collections.unmodifiableList(this.inters);
	}
	
	public boolean isEmpty()
	{
		return this.inters.isEmpty();
	}
	
	@Override
	public String toString()
	{
		return this.inters.stream().map((i) -> i.toString()).collect(Collectors.joining("\n"));
	}
}
