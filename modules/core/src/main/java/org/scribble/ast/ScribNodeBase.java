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
package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.visit.ModelVisitor;
import org.scribble.ast.visit.Substitutor;
import org.scribble.del.ScribDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.util.ScribbleUtil;

/**
 * This is the generic object from which all Scribble model objects
 * are derived.
 */
// Currently name nodes don't have dels (aren't made by node factory)
public abstract class ScribNodeBase implements ScribNode
{
	protected ScribDel del;

	// Internal shallow copy for (immutable) ModelNodes
	//@Override
	protected abstract ScribNodeBase copy();
	
	@Override
	public ScribNode accept(ModelVisitor nv) throws ScribbleException
	{
		//return visitChild(this, nv);  // FIXME: weird to call visitChild with "this" as the child (only done for root visit calls)
		return nv.visit(null, this);
	}

	@Override
	public ScribNode visitChildren(ModelVisitor nv) throws ScribbleException
	{
		return this;
	}
	
	protected ScribNode visitChild(ScribNode child, ModelVisitor nv) throws ScribbleException
	{
		return nv.visit(this, child);
	}

	/*@Override
	public ModelNode visitChildrenInSubprotocols(SubprotocolVisitor nv) throws ScribbleException
	{
		return visitChildren(nv);
	}*/
	
	@Override
	public final ScribDel del()
	{
		return this.del;
	}
	
	@Override
	public final ScribNodeBase del(ScribDel del)
	{
		ScribNodeBase copy = copy();
		copy.del = del;
		return copy;
	}

	/*@Override
	public final <T extends ModelNodeBase> T del(T n, ModelDelegate del)
	{
		T copy = n.copy();
		copy.del = del;
		return copy;
	}*/
	
	/*protected static <T extends ModelNodeBase> T reconstruct(T n)
	{
		return castReconstruction(n.del(n.del()), n);  // del setter returns a shallow copy -- which is enough for immutable model nodes
	}*/

	@Override
	public ScribNode substituteNames(Substitutor subs)
	{
		return this;
	}
		
	// FIXME: remove parent parameter, to make uniform with visitChild
	// Used when a generic cast would otherwise be needed (non-generic children casts don't need this)
	protected final static <T extends ScribNode> T visitChildWithClassCheck(ScribNode parent, T child, ModelVisitor nv) throws ScribbleException
	{
		ScribNode visited = ((ScribNodeBase) parent).visitChild(child, nv);
		if (visited.getClass() != child.getClass())  // Visitor is not allowed to replace the node by a different node type
		{
			throw new RuntimeException(nv.getClass() + " generic visit error: " + child.getClass() + ", " + visited.getClass());
		}
		@SuppressWarnings("unchecked")
		T t = (T) visited;
		return t;
	}

	protected final static <T extends ScribNode> List<T> visitChildListWithClassCheck(ScribNode parent, List<T> children, ModelVisitor nv) throws ScribbleException
	{
		/*List<T> visited = new LinkedList<>();
		for (T n : children)
		{
			visited.add(visitChildWithClassCheck(parent, n, nv));
		}
		return visited;*/
		// Maybe this hack is not worth it?  Better to throw directly as ScribbleException
		try
		{
			return children.stream()
					.map((n) -> ScribbleUtil.handleLambdaScribbleException(() -> ScribNodeBase.visitChildWithClassCheck(parent, n, nv)))
					.collect(Collectors.toList());
		}
		catch (RuntimeScribbleException rse)
		{
			Throwable cause = rse.getCause();
			if (cause instanceof ScribbleException)
			{
				throw (ScribbleException) cause;
			}
			throw (RuntimeException) cause;
		}
	}
	
	/*private static <T extends ModelNodeBase> T castReconstruction(ModelNodeBase n, T t)
	{
		if (!n.getClass().equals(t.getClass()))
		{
			throw new RuntimeException("Cast error: " + n.getClass() + ", " + t.getClass());
		}
		@SuppressWarnings("unchecked")
		T tmp = (T) n;
		return tmp;
	}*/
}
