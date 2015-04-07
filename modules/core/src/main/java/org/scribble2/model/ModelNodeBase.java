/*
 * Copyright 2009 www.scribble.org
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
package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.model.visit.SubprotocolVisitor;
import org.scribble2.model.visit.Substitutor;
import org.scribble2.util.ScribbleException;

/**
 * This is the generic object from which all Scribble model objects
 * are derived.
 */
// Currently name nodes don't have dels (aren't made by node factory)
public abstract class ModelNodeBase implements ModelNode
{
	private ModelDelegate del;

	// Internal shallow copy for (immutable) ModelNodes
	//@Override
	protected abstract ModelNodeBase copy();
	
	@Override
	public ModelNode visit(ModelVisitor nv) throws ScribbleException
	{
		return visitChild(this, nv);  // FIXME: weird to call visitChild with "this" as the child (only done for root visit calls) -- use null instead?
	}
	
	protected ModelNode visitChild(ModelNode child, ModelVisitor nv) throws ScribbleException
	{
		return nv.visit(this, child);
	}

	@Override
	public ModelNode visitChildren(ModelVisitor nv) throws ScribbleException
	{
		return this;
	}

	@Override
	public ModelNode visitChildrenInSubprotocols(SubprotocolVisitor nv) throws ScribbleException
	{
		return visitChildren(nv);
	}
	
	@Override
	public final ModelDelegate del()
	{
		return this.del;
	}
	
	@Override
	public final ModelNodeBase del(ModelDelegate del)
	{
		ModelNodeBase copy = copy();
		copy.del = del;
		return copy;
	}
	
	// FIXME: move to delegate
	@Override
	public ModelNode substitute(Substitutor subs) throws ScribbleException
	{
		//return visit(subs);
		return this;
	}
		
	// Used when a generic cast would otherwise be needed (non-generic children casts don't need this)
	protected final static <T extends ModelNode> T visitChildWithClassCheck(ModelNode parent, T child, ModelVisitor nv) throws ScribbleException
	{
		ModelNode visited = ((ModelNodeBase) parent).visitChild(child, nv);
		if (visited.getClass() != child.getClass())  // Visitor is not allowed to replace the node by a different node type
		{
			throw new RuntimeException("Visitor generic visit error: " + child.getClass() + ", " + visited.getClass());
		}
		@SuppressWarnings("unchecked")
		T t = (T) visited;
		return t;
	}

	protected final static <T extends ModelNode> List<T> visitChildListWithClassCheck(ModelNode parent, List<T> children, ModelVisitor nv) throws ScribbleException
	{
		List<T> visited = new LinkedList<>();
		for (T n : children)
		{
			visited.add(visitChildWithClassCheck(parent, n, nv));
		}
		return visited;
	}
}
