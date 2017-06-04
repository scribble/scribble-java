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

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.simple.ScopeNode;
import org.scribble.sesstype.kind.ProtocolKind;

public abstract class Interruptible<K extends ProtocolKind> extends CompoundInteractionNode<K> implements ScopedNode
{
	public final ScopeNode scope;
	public final ProtocolBlock<K> block;
	private final List<? extends Interrupt> interrs;

	protected Interruptible(CommonTree source, ScopeNode scope, ProtocolBlock<K> block, List<? extends Interrupt> interrs)
	{
		super(source);
		this.scope = scope;
		this.block = block;
		this.interrs = interrs;
	}
	
	/*protected abstract Interruptible<T1, T2> reconstruct(CommonTree ct, ScopeNode scope, T1 block, List<T2> interrs, CompoundInteractionNodeContext icontext, Env env);

	@Override
	public NodeContextBuilder enterContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.pushContext(new CompoundInteractionNodeContext());
		return builder;
	}

	@Override
	public Interruptible<T1, T2> leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		CompoundInteractionNodeContext icontext = (CompoundInteractionNodeContext) builder.popContext();
		builder.replaceContext(((CompoundInteractionContext) builder.peekContext()).merge(icontext));
		icontext = (CompoundInteractionNodeContext) icontext.merge(this.block.getContext());
		//return new Interruptible<T1, T2>(this.ct, this.scope, this.block, this.interrs, icontext);
		return reconstruct(this.ct, this.scope, this.block, this.interrs, icontext, getEnv());
	}

	@Override
	public Interruptible<T1, T2> leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;

		/*Role src = this.src.toName();
		for (Message msg : this.msgs.stream().map((mn) -> mn.toMessage()).collect(Collectors.toList()))
		{
			for (Role dest : ((GlobalInterruptContext) getContext()).getDestinations())
			{
				//checker.getEnv().addInterrupt(src, dest, msg);  // No: src must be enabled, and if receiver may be enabled by this interrupt then it must also be receiving normally inside the interruptible block
				
				System.out.println("2a: " + src + ", " + dest + ", " + msg);
				
				checker.getEnv().addMessage(src, dest, msg);
			}
		}* /
	}

	@Override
	public Interruptible<T1, T2> leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}
	
	@Override
	public Interruptible<T1, T2> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ScopeNode scope = this.scope;
		if (scope != null)
		{
			scope = (ScopeNode) visitChild(this.scope, nv);
		}
		//ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>> block = (ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>) nv.visit(this.block);
		T1 block = visitChildWithClassCheck(this, this.block, nv);
		List<T2> interrs = visitChildListWithClassCheck(this, this.interrs, nv);
		//return new Interruptible<>(this.ct, scope, block, interrs, getContext(), getEnv());
		return reconstruct(this.ct, scope, block, interrs, getContext(), getEnv());
	}
	
	@Override
	public boolean isEmptyScope()
	{
		return false;
	}

	@Override
	//public Scope getScope()
	public SimpleName getScopeElement()
	{
		return this.scope.toName();
	}*/

	public boolean isScopeNodeImplicit()
	{
		return this.scope == null;
	}
	
	@Override
	public String toString()
	{
		String s = Constants.INTERRUPTIBLE_KW + " ";
		if (!isScopeNodeImplicit())
		{
			s += this.scope + ": ";
		}
		s += this.block + " " + Constants.WITH_KW + " {\n";
		s += this.interrs.stream().map((i) -> i.toString()).collect(Collectors.joining("\n")) + "\n";
		return s + "}";
	}
}
