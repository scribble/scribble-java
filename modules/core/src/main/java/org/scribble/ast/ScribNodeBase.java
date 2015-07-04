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
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;
import org.scribble.visit.Substitutor;

/**
 * This is the generic object from which all Scribble model objects
 * are derived.
 */
public abstract class ScribNodeBase implements ScribNode
{
	protected ScribDel del;

	// Internal shallow copy for (immutable) ModelNodes
	//@Override
	protected abstract ScribNodeBase copy();
	
	@Override
	public abstract ScribNodeBase clone();
	
	@Override
	public ScribNode accept(AstVisitor nv) throws ScribbleException
	{
		return nv.visit(null, this);
	}

	@Override
	public ScribNode visitChildren(AstVisitor nv) throws ScribbleException
	{
		return this;
	}
	
	protected ScribNode visitChild(ScribNode child, AstVisitor nv) throws ScribbleException
	{
		return nv.visit(this, child);
	}
	
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
	public final <T extends ScribNode> T del(T n, ScribDel del)
	{
		T copy = n.copy();
		copy.del = del;
		return copy;
	}*/

	@Override
	public ScribNode substituteNames(Substitutor subs)
	{
		return this;
	}
		
	// FIXME: remove parent parameter, to make uniform with visitChild
	// Used when a generic cast would otherwise be needed (non-generic children casts don't need this) -- doesn't check any generic parameters, relies on concrete values being instances of non-parameterised types
	// Subtype constraint on visited could still be too restrictive, e.g. AmbigNameNodeDel (although it doesn't matter there) -- e.g. unfolding continues into recursions
	protected final static <T extends ScribNode> T visitChildWithStrictClassCheck(ScribNode parent, T child, AstVisitor nv) throws ScribbleException
	{
		ScribNode visited = ((ScribNodeBase) parent).visitChild(child, nv);
		//if (visited.getClass() != child.getClass())  // Visitor is not allowed to replace the node by a different node type
		if (!child.getClass().isAssignableFrom(visited.getClass()))  // Same subtyping flexibility as standard cast
		{
			throw new RuntimeException(nv.getClass() + " generic visit error: " + child.getClass() + ", " + visited.getClass());
		}
		@SuppressWarnings("unchecked")
		T t = (T) visited;
		return t;
	}

	protected final static <N extends ScribNode, R extends ScribNode> List<R> visitChildListWith(ScribNode parent, List<N> children, AstVisitor nv, Function<N, R> c) throws ScribbleException
	{
		/*List<T> visited = new LinkedList<>();
		for (T n : children)
		{
			visited.add(c.call());
		}
		return visited;*/
		// Maybe this exception hack is not worth it?  Better to throw directly as ScribbleException
		try
		{
			return children.stream().map((n) -> c.apply(n)).collect(Collectors.toList());
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

	protected final static <T extends ScribNode> List<T> visitChildListWithStrictClassCheck(ScribNode parent, List<T> children, AstVisitor nv) throws ScribbleException
	{
		return visitChildListWith(parent, children, nv, new Function<T, T>()
			{
				@Override
				public T apply(T t)
				{
					return ScribUtil.handleLambdaScribbleException(() -> ScribNodeBase.visitChildWithStrictClassCheck(parent, t, nv));
				}
			});
	}
	
	//protected final static <C extends ScribNode> C visitChildWithClassCheck(Class<C> c, ScribNode parent, ScribNode child, AstVisitor nv) throws ScribbleException
	//protected final static <T extends ScribNode, R extends ProtocolKindNode<K>, K extends ProtocolKind> R visitChildWithCastCheck(Class<T> c, Function<ProtocolKindNode<K>, R> f, ScribNode parent, ScribNode child, AstVisitor nv) throws ScribbleException
	// R = T<K>  i.e. the generic (ProtocolKindNode) class T parameterised by K
	protected final static <T extends ProtocolKindNode<?>, K extends ProtocolKind, R extends ProtocolKindNode<K>> R visitChildWithCastCheck(Class<T> c, K k, Function<ScribNode, R> f, ScribNode parent, ScribNode child, AstVisitor nv) throws ScribbleException
	{
		ScribNode visited = ((ScribNodeBase) parent).visitChild(child, nv);
		if (!c.isAssignableFrom(visited.getClass()))
		{
			throw new RuntimeException(nv.getClass() + " generic visit error: " + c + ", " + visited.getClass());
		}
		/*if ((GNode.class.isAssignableFrom(c) && !(child instanceof GNode)) || (LNode.class.isAssignableFrom(c) && !(child instanceof LNode)))
		{
			throw new RuntimeException(nv.getClass() + " generic visit error: " + c + ", " + visited.getClass());
		}*/
		//T2 t = (T2) c.cast(visited);
		//return f.apply(t);
		ProtocolKindNode<?> n = (ProtocolKindNode<?>) visited;
		if ((n.isGlobal() && !k.equals(Global.KIND)) || (n.isLocal() && !k.equals(Local.KIND)))
		{
			throw new RuntimeException(nv.getClass() + " generic visit error: " + n.getClass() + ", " + k);
		}
		return f.apply(n);
	}

	//protected final static <C extends ScribNode> List<C> visitChildListWithClassCheck(Class<C> c, ScribNode parent, List<? extends ScribNode> children, AstVisitor nv) throws ScribbleException
	//protected final static <T extends ScribNode, T2 extends T, R extends ScribNode> List<R> visitChildListWithCastCheck(Class<T> c, Function<T2, R> f, ScribNode parent, List<? extends ScribNode> children, AstVisitor nv) throws ScribbleException
	protected final static <T extends ScribNode, T1 extends ProtocolKindNode<?>, K extends ProtocolKind, R extends ProtocolKindNode<K>> List<R> visitChildListWithCastCheck(Class<T1> c, K k, Function<ScribNode, R> f, ScribNode parent, List<T> children, AstVisitor nv) throws ScribbleException
	{
		return visitChildListWith(parent, children, nv, new Function<T, R>()
			{
				@Override
				public R apply(T t)
				{
					return ScribUtil.handleLambdaScribbleException(() -> ScribNodeBase.visitChildWithCastCheck(c, k, f, parent, t, nv));
				}
			});
	}
}
