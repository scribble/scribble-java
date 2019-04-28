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
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.core.type.kind.Global;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.kind.ProtoKind;
import org.scribble.del.DelFactory;
import org.scribble.del.ScribDel;
import org.scribble.util.RuntimeScribException;
import org.scribble.util.ScribException;
import org.scribble.util.ScribNodeUtil;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;

/**
 * This is the generic object from which all Scribble model objects
 * are derived.
 * 
 * ScribNodeBase is to ScribNode, as ANTLR CommonTree is to Tree.
 */
public abstract class ScribNodeBase extends CommonTree implements ScribNode
{
	private final CommonTree source;  // Track "original" source where applicable

	protected ScribDel del;
	
	// Used by (Scrib)TreeAdapator
	public ScribNodeBase(Token payload)
	{
		super(payload);
		this.source = this;  // Record parser construction as source
	}

	// Copy constructor
	protected ScribNodeBase(ScribNodeBase node)
	{
		super(node);
		this.source = node.getSource();  // Keep ref to original as source (cf., this)
			// A (partially) reconstructed/generated node may still have no parent (i.e., null) at the moment an error is raised during visit pass 
			// Can always use AntlrSourceException to blame the "child" (the original node) during visit rather than "visited", E.g., bad.syntax.disamb.doarglist.DoArgList06
	}

	@Override
	public final CommonTree getSource()
	{
		return this.source;
	}

	@Override
	public ScribNode getParent()
	{
		return (ScribNode) super.getParent();
	}

	@Override
	public ScribNode getChild(int i)
	{
		return (ScribNode) super.getChild(i);
	}
	
	// N.B. "overriding" base ANTLR behaviour of (sometimes?) returning null when
	// getChildCount() == 0 by returning an empty list instead
	@Override
	public List<? extends ScribNode> getChildren()
	{
		if (getChildCount() == 0)
		{
			return Collections.emptyList();
		}
		return ((List<?>) super.getChildren()).stream().map(x -> (ScribNode) x)
				.collect(Collectors.toList());
	}
	
	// CHECKME: redundant?  given this.addChildren
	// Non-defenseive
	// CHECKME: "generic" visitChildren and reconstruct via get/setChildren? -- maybe too implicit, cannot easily see child visiting order
	protected void setChildren(List<ScribNode> children)
	{
		children.forEach(x ->
			{
				//x.setParent(this);  // Not needed, cf. BaseTree#addChild(Tree)
				addChild(x);
			});
	}

	// CHECKME: rework as ANTLR node (deep) copy (maybe "clone") -- no: ANTLR dupNode is node only copy (actually, dupNode is node "shell" only, doesn't even keep children, let alone copy them)
	// N.B. does not copy children nor del
	// Should call Tree#dupNode constructor (i.e., children not dup'd) -- do not "return this" (unless childress), ends up with children duplicated (because node "dup'd" with children, then children added again)
	@Override
	public abstract ScribNodeBase dupNode();

	// Internal shallow copy for (immutable) ModelNodes -- does not keep the del (copy is used internally for del setter, and keeping the del needs knowledge of super fields) 
	// Generally copy is only for shallow copy of object and "immediate" fields (not super fields), cf. ProtocolDecl, ProtocolDeclContext, etc
	@Deprecated
	protected final ScribNodeBase copy()  // Deprecated: because ANTLR Tree is not immutable (has a "parent" field)
	{
		throw new RuntimeException("Deprecated");
	}

	// Deep (tree) clone, i.e., children also cloned
	// Makes a new del via AF (i.e. fresh, not a copy of old del) -- cf. reconstruct, keeps old del (the same instance)
	// No: now just keep old del -- by default, del's are immutable
	/*@Override
	public abstract ScribNodeBase clone(AstFactory af);*/

	public ScribNodeBase clone()
	{
		ScribNodeBase dup = (ScribNodeBase) dupNode();
		getChildren().forEach(x -> dup.addChild(x.clone()));
		dup.del = this.del;
		return dup;
	}
	
	@Override
	public final ScribDel del()
	{
		return this.del;
	}
	
	// Decorate with a fresh del (cf. reconstruct: dupNode, setDel)
	public abstract void decorateDel(DelFactory df);
	
	// Defensive
	/*@Override
	public final ScribNodeBase del(ScribDel del)
	{
		ScribNodeBase clone = clone();  // Need full clone because parent field prevents immutable construction
		clone.setDel(del);
		return clone;
	}*/
	
	// Non-defensive
	protected final void setDel(ScribDel del)
	{
		this.del = del;
	}

	// Defensive helper with cast check -- currently unused
	public static final <T extends ScribNode> T del(T n, ScribDel del)
	{
		ScribNodeBase copy = ((ScribNodeBase) n).clone();  // Need deep clone, since children have parent field
		copy.setDel(del);
		return ScribNodeUtil.castNodeByClass(n, copy);
	}
	
	@Override
	public ScribNode accept(AstVisitor v) throws ScribException
	{
		return v.visit(this);
	}

	@Override
	public ScribNode visitChildren(AstVisitor v) throws ScribException
	{
		return this;
	}
	
	// Used to be more relevant, previously there was an explicit parent parameter
	protected ScribNode visitChild(ScribNode child, AstVisitor v) throws ScribException
	{
		return v.visit(child);  // cf. child.accept(v) ?
	}
		
	// FIXME: remove parent parameter, to make uniform with visitChild
	// Used when a generic cast would otherwise be needed (non-generic children casts don't need this) -- doesn't check any generic parameters, relies on concrete values being instances of non-parameterised types
	// Subtype constraint on visited could still be too restrictive, e.g. AmbigNameNodeDel (although it doesn't matter there), e.g. unfolding continue's into recursion's
	protected final static <T extends ScribNode> T visitChildWithClassEqualityCheck(
			ScribNode parent, T child, AstVisitor v) throws ScribException
	{
		ScribNode visited = ((ScribNodeBase) parent).visitChild(child, v);
		// Same subtyping flexibility as standard cast
		return ScribNodeUtil.checkNodeClassEquality(child, visited);
	}

	protected final static <T extends ScribNode> List<T> 
			visitChildListWithClassEqualityCheck(
					ScribNode parent, List<T> children, AstVisitor v)
					throws ScribException
	{
		return visitChildListWith(parent, children, v,
				(T t) -> ScribUtil.handleLambdaScribbleException(
					() -> ScribNodeBase
						.visitChildWithClassEqualityCheck(parent, t, v))); // -> T
	}

	// Just a list-map with handling for promoted exceptions (via handleLambdaScribbleException) -- could move to Util (where handleLambdaScribbleException is)
	private final static <T extends ScribNode, R extends ScribNode> List<R> visitChildListWith(
			ScribNode parent, List<T> children, AstVisitor nv, Function<T, R> c)
			throws ScribException
	{
		/*List<T> visited = new LinkedList<>();
		for (T n : children)
		{
			visited.add(c.call());
		}
		return visited;*/
		// Maybe the below exception hack is not worth it?  Simpler to throw directly as ScribbleException, as above 
		try
		{
			return children.stream().map(x -> c.apply(x))
					.collect(Collectors.toList());
		}
		catch (RuntimeScribException rse)
		{
			Throwable cause = rse.getCause();
			if (cause instanceof ScribException)
			{
				throw (ScribException) cause;
			}
			throw (RuntimeException) cause;
		}
	}


	// Following would fit better in ProtocolKindNode, except it is an interface

	// Takes clazz+kind to handle generic ProtocolKindNodes -- cf. ScribUtil.castNodeByClass, for casting to ground class types
	// R is expected to be N<K>, i.e. the generic (ProtocolKindNode) class N parameterised by K
	protected final static <N extends ProtoKindNode<?>, K extends ProtoKind, 
			R extends ProtoKindNode<K>>
			R visitProtocolKindChildWithCastCheck(
					ScribNode parent, ScribNode child, AstVisitor nv, Class<N> clazz, K kind,
					Function<ScribNode, R> cast) throws ScribException
	{
		ScribNode visited = ((ScribNodeBase) parent).visitChild(child, nv);
		if (!clazz.isAssignableFrom(visited.getClass()))
		{
			throw new RuntimeException(nv.getClass() + " generic visit error: "
					+ clazz + ", " + visited.getClass());
		}
		ProtoKindNode<?> pkn = (ProtoKindNode<?>) visited;
		if ((pkn.isGlobal() && !kind.equals(Global.KIND))
				|| (pkn.isLocal() && !kind.equals(Local.KIND)))
		{
			throw new RuntimeException(nv.getClass() + " generic visit error: "
					+ pkn.getClass() + ", " + kind);
		}
		return cast.apply(pkn);
	}

	protected final static <T extends ScribNode, N extends ProtoKindNode<?>, 
			K extends ProtoKind, R extends ProtoKindNode<K>> 
			List<R> visitProtocolKindChildListWithCastCheck(
					ScribNode parent, List<T> children, AstVisitor nv, Class<N> c, K k,
					Function<ScribNode, R> f) throws ScribException
	{
		return visitChildListWith(parent, children, nv,
				(T t) -> ScribUtil.handleLambdaScribbleException(
					() -> ScribNodeBase
						.visitProtocolKindChildWithCastCheck(parent, t, nv, c, k, f)));
		// -> R
	}
}
