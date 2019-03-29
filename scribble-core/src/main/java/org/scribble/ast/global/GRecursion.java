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
package org.scribble.ast.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ConnectAction;
import org.scribble.ast.Do;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.Recursion;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.type.kind.Global;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;


public class GRecursion extends Recursion<Global>
		implements GCompoundInteractionNode
{
	// ScribTreeAdaptor#create constructor
	public GRecursion(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected GRecursion(GRecursion node)
	{
		super(node);
	}
	
	@Override
	public GRecursion dupNode()
	{
		return new GRecursion(this);
	}

	@Override
	public GProtocolBlock getBlockChild()
	{
		return (GProtocolBlock) getChild(1);
	}

	public LRecursion project(AstFactory af, Role self, LProtocolBlock block)
	{
		RecVarNode recvar = getRecVarChild().clone();//af);
		LRecursion projection = null;
		Set<RecVar> rvs = new HashSet<>();
		rvs.add(recvar.toName());
		LProtocolBlock pruned = prune(af, block, rvs);
		if (!pruned.isEmpty())
		{
			projection = af.LRecursion(this.source, recvar, pruned);
		}
		return projection;
	}

	// Pruning must be considered here (at Recursion) due to unguarded recvars
	// Set should be unnecessary (singleton OK) -- *nested* irrelevant continues should already have been pruned
	// CHECKME? refactor and separate into dels? -- maybe not: since pruning is a bit too much of a "centralised algorithm" -- currently relying on TODO exception for unhandled cases
	protected static LProtocolBlock prune(AstFactory af, LProtocolBlock block,
			Set<RecVar> rvs) // FIXME: Set unnecessary
	{
		LInteractionSeq seq = block.getInteractSeqChild();
		if (block.isEmpty())
		{
			return block;
		}
		List<? extends LInteractionNode> lis = block.getInteractSeqChild()
				.getInteractionChildren();
		if (lis.size() > 1)
		{
			return block;
		}
		else //if (lis.size() == 1)
		{
			// Only pruning if single statement body: if more than 1, must be some (non-empty?) statement before a continue -- cannot (shouldn't?) be a continue followed by some other statement due to reachability
			LInteractionNode lin = lis.get(0);
			if (lin instanceof LContinue)
			{
				if (rvs.contains(((LContinue) lin).getRecVarChild().toName()))
				{
					// FIXME: need equivalent for projection-irrelevant recursive-do in a protocoldecl
					return af.LProtocolBlock(block.getSource(),
							af.LInteractionSeq(seq.getSource(), Collections.emptyList()));
				}
				else
				{
					return block;
				}
			}
			else if (lin instanceof MessageTransfer<?> || lin instanceof Do<?>
					|| lin instanceof ConnectAction<?>)
			{
				return block;
			}
			else
			{
				if (lin instanceof LChoice)
				{
					List<LProtocolBlock> pruned = new LinkedList<LProtocolBlock>();
					for (LProtocolBlock b : ((LChoice) lin).getBlockChildren())
					{
						if (!prune(af, b, rvs).isEmpty())
						{
							pruned.add(b);
						}
					}
					if (pruned.isEmpty())
					{
						return af.LProtocolBlock(block.getSource(),
								af.LInteractionSeq(seq.getSource(), Collections.emptyList()));
					}	
					else
					{
						return af.LProtocolBlock(block.getSource(),
								af.LInteractionSeq(seq.getSource(),
										Arrays.asList(af.LChoice(lin.getSource(),
												((LChoice) lin).getSubjectChild(), pruned))));
					}	
				}
				else if (lin instanceof LRecursion)
				{
					rvs = new HashSet<>(rvs);
					//rvs.add(((LRecursion) lin).recvar.toName());  // Set unnecessary
					LProtocolBlock pruned = prune(af, ((LRecursion) lin).getBlockChild(),
							rvs);  // Need to check if the current rec has any cases to prune in the nested rec (already pruned, but for the nested recvars only)
					if (pruned.isEmpty())
					{
						return pruned;
					}
					else
					{
						return af.LProtocolBlock(block.getSource(),
								af.LInteractionSeq(seq.getSource(),
										Arrays.asList(af.LRecursion(lin.getSource(),
												((LRecursion) lin).getRecVarChild(), pruned))));
					}
					/*if (((LRecursion) lin).block.isEmpty())
					{
						return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Arrays.asList(AstFactoryImpl.FACTORY.LRecursion(((LRecursion) lin).recvar, ((LRecursion) lin).getBlock()))));
					}
					else
					{
						return ((LRecursion) lin).getBlock();
					}*/
				}
				else
				{
					throw new RuntimeException("TODO: " + lin);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public GRecursion(CommonTree source, RecVarNode recvar, GProtocolBlock block)
	{
		super(source, recvar, block);
	}

	/*@Override
	protected GRecursion copy()
	{
		return new GRecursion(this.source, this.recvar, getBlockChild());
	}
	
	@Override
	public GRecursion clone(AstFactory af)
	{
		RecVarNode recvar = this.recvar.clone(af);
		GProtocolBlock block = getBlockChild().clone(af);
		return af.GRecursion(this.source, recvar, block);
	}

	@Override
	public GRecursion reconstruct(RecVarNode recvar, ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		GRecursion gr = new GRecursion(this.source, recvar, (GProtocolBlock) block);
		gr = (GRecursion) gr.del(del);
		return gr;
	}*/
}
