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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;

public abstract class Parallel<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	private final List<? extends ProtocolBlock<K>> blocks;

	protected Parallel(CommonTree source, List<? extends ProtocolBlock<K>> blocks)
	{
		super(source);
		this.blocks = new LinkedList<>(blocks);
	}

	public abstract Parallel<K> reconstruct(List<? extends ProtocolBlock<K>> blocks);

	@Override
	public Parallel<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<? extends ProtocolBlock<K>> blocks = visitChildListWithClassEqualityCheck(this, this.blocks, nv);
		return reconstruct(blocks);
	}
	
	public List<? extends ProtocolBlock<K>> getBlocks()
	{
		return Collections.unmodifiableList(this.blocks);
	}

	/*@Override
	public NodeContextBuilder enterContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.pushContext(new CompoundInteractionNodeContext());
		return builder;
	}

	@Override
	public Parallel<T> leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		CompoundInteractionNodeContext pcontext = (CompoundInteractionNodeContext) builder.popContext();
		List<ProtocolBlockContext> bcontexts =
				this.blocks.stream().map((b) -> b.getContext()).collect(Collectors.toList());
		pcontext = (CompoundInteractionNodeContext) pcontext.merge(bcontexts);
		builder.replaceContext(((CompoundInteractionContext) builder.peekContext()).merge(pcontext));
		//return new Parallel<>(this.ct, this.blocks, pcontext);
		return reconstruct(this.ct, this.blocks, pcontext, getEnv());
	}

	@Override
	public Parallel<T> leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}

	@Override
	public Parallel<T> leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}
	
	/*@Override
	public Parallel checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{k
		return visitWithEnv(wfc);
	}

	@Override
	public Parallel project(Projector proj) throws ScribbleException
	{
		return visitWithEnv(proj);
	}

	@Override
	public Parallel checkReachability(ReachabilityChecker rc) throws ScribbleException
	{
		return visitWithEnv(rc);
	}

	public Parallel visitWithEnv(EnvVisitor nv) throws ScribbleException
	{
		Env env = nv.getEnv();
		List<ProtocolBlock> blocks = new LinkedList<>();
		for (ProtocolBlock block : this.blocks)
		{
			nv.setEnv(new Env(env));
			ProtocolBlock visited = (ProtocolBlock) nv.visit(block);
			blocks.add(visited);
		}
		nv.setEnv(env);
		return new Parallel(this.ct, blocks);
	}*/

	/*@Override
	public String toString()
	{
		String sep = " " + Constants.AND_KW + " ";
		return Constants.PAR_KW + " "
					+ this.blocks.stream().map((block) -> block.toString()).collect(Collectors.joining(sep));
	}*/
}
