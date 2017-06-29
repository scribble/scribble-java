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
package org.scribble.ast.local;

import java.util.List;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Interruptible;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.ScopeNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.ScopeKind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

//public class LocalInterruptible extends Interruptible<LocalProtocolBlock, LocalInterrupt> implements LocalInteractionNode
public class LInterruptible extends Interruptible<Local> implements LCompoundInteractionNode
{
	//protected LocalInterruptible(ScopeNode scope, LocalProtocolBlock block, List<LocalInterrupt> interrs)
	protected LInterruptible(CommonTree source, ScopeNode scope, ProtocolBlock<Local> block, List<LInterrupt> interrs)
	{
		super(source, scope, block, interrs);
	}	

	@Override
	protected ScribNodeBase copy()
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public LInterruptible clone(AstFactory af)
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public boolean isEmptyScope()
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public Name<ScopeKind> getScopeElement()
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public LInteractionNode merge(AstFactory af, LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		throw new RuntimeException("TODO: " + this);
	}

	@Override
	public Set<Message> getEnabling()
	{
		throw new RuntimeException("TODO: " + this);
	}

	/*// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LCompoundInteractionNode.super.getKind();
	}*/
	
	
	
	
	
	
	/*public LocalInterruptible(CommonTree ct, ScopeNode scope, LocalProtocolBlock block, LocalThrows thro, List<LocalCatches> cats)
	{
		this(ct, scope, block, compileInterrupts(thro, cats), null, null);
	}

	/*public LocalInterruptible(CommonTree ct, ScopeNode scope, LocalProtocolBlock block, List<LocalInterrupt> interrs, CompoundInteractionNodeContext icontext)
	{
		super(ct, scope, block, interrs, icontext);
	}* /

	protected LocalInterruptible(CommonTree ct, ScopeNode scope, LocalProtocolBlock block, List<LocalInterrupt> interrs, CompoundInteractionNodeContext icontext, Env env)
	{
		super(ct, scope, block, interrs, icontext, env);
	}
	
	private static List<LocalInterrupt> compileInterrupts(LocalThrows thro, List<LocalCatches> cats)
	{
		List<LocalInterrupt> interrs = new LinkedList<>();
		if (thro != null)
		{
			interrs.add(thro);
		}
		interrs.addAll(cats);
		return interrs;
	}

	@Override
	protected LocalInterruptible reconstruct(CommonTree ct, ScopeNode scope, LocalProtocolBlock block, List<LocalInterrupt> interrs, CompoundInteractionNodeContext icontext, Env env)
	{
		return new LocalInterruptible(ct, scope, block, interrs, icontext, env);
	}
	
	@Override
	public LocalInterruptible leaveGraphBuilding(GraphBuilder builder)
	{
		throw new RuntimeException("TODO: ");
	}

	/*@Override
	public LocalInterruptible leaveContextBuilding(Node parent, NodeContextBuilder builder) throws ScribbleException
	{
		Interruptible<LocalProtocolBlock, LocalInterrupt> intt = super.leaveContextBuilding(parent, builder);
		return new LocalInterruptible(intt.ct, intt.scope, intt.block, interrs, (CompoundInteractionNodeContext) intt.getContext());
	}

	@Override
	public LocalInterruptible leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Interruptible<LocalProtocolBlock, LocalInterrupt> intt = super.leaveWFChoiceCheck(checker);
		return new LocalInterruptible(intt.ct, intt.scope, intt.block, intt.interrs, (CompoundInteractionNodeContext) intt.getContext(), intt.getEnv());
	}

	@Override
	public LocalInterruptible visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Interruptible<LocalProtocolBlock, LocalInterrupt> intt = super.visitChildren(nv);
		return new LocalInterruptible(intt.ct, intt.scope, intt.block, intt.interrs, intt.getContext(), intt.getEnv());
	}*/
}
