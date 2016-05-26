package org.scribble.ast.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;


public class GRecursion extends Recursion<Global> implements GCompoundInteractionNode
{
	public GRecursion(RecVarNode recvar, GProtocolBlock block)
	{
		super(recvar, block);
	}

	public LRecursion project(Role self, LProtocolBlock block)
	{
		RecVarNode recvar = this.recvar.clone();
		LRecursion projection = null;
		Set<RecVar> rvs = new HashSet<>();
		rvs.add(recvar.toName());
		//System.out.println("\n111: " + block);
		LProtocolBlock pruned = prune(block, rvs);
		//System.out.println("\n222: " + pruned);
		if (!pruned.isEmpty())
		{
			projection = AstFactoryImpl.FACTORY.LRecursion(recvar, pruned);
		}
		return projection;
	}

	// Set should be unnecessary (singleton OK) -- *nested* irrelevant continues should already have been pruned
	private static LProtocolBlock prune(LProtocolBlock block, Set<RecVar> rvs)
	{
		if (block.isEmpty())
		{
			return block;
		}
		List<? extends LInteractionNode> lis = block.getInteractionSeq().getInteractions();
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
				if (rvs.contains(((LContinue) lin).recvar.toName()))
				{
					// FIXME: need equivalent for projection-irrelevant recursive-do in a protocoldecl
					return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Collections.emptyList()));
				}
				else
				{
					return block;
				}
			}
			else if (lin instanceof MessageTransfer<?>)
			{
				return block;
			}
			else
			{
				if (lin instanceof LChoice)
				{
					List<LProtocolBlock> pruned = new LinkedList<LProtocolBlock>();
					for (LProtocolBlock b : ((LChoice) lin).getBlocks())
					{
						if (!prune(b, rvs).isEmpty())
						{
							pruned.add(b);
						}
					}
					if (pruned.isEmpty())
					{
						return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Collections.emptyList()));
					}	
					else
					{
						return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Arrays.asList(AstFactoryImpl.FACTORY.LChoice(((LChoice) lin).subj, pruned))));
					}	
				}
				else if (lin instanceof LRecursion)
				{
					rvs = new HashSet<>(rvs);
					//rvs.add(((LRecursion) lin).recvar.toName());  // Set unnecessary
					LProtocolBlock prune = prune(((LRecursion) lin).getBlock(), rvs);  // Need to check if the current rec has any cases to prune in the nested rec (already pruned, but for the nested recvars only)
					if (prune.isEmpty())
					{
						return prune;
					}
					else
					{
						return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Arrays.asList(AstFactoryImpl.FACTORY.LRecursion(((LRecursion) lin).recvar, prune))));
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

	@Override
	protected GRecursion copy()
	{
		return new GRecursion(this.recvar, getBlock());
	}
	
	@Override
	public GRecursion clone()
	{
		RecVarNode recvar = this.recvar.clone();
		GProtocolBlock block = getBlock().clone();
		return AstFactoryImpl.FACTORY.GRecursion(recvar, block);
	}

	@Override
	public GRecursion reconstruct(RecVarNode recvar, ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		GRecursion gr = new GRecursion(recvar, (GProtocolBlock) block);
		gr = (GRecursion) gr.del(del);
		return gr;
	}
	
	@Override
	public GProtocolBlock getBlock()
	{
		return (GProtocolBlock) this.block;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GCompoundInteractionNode.super.getKind();
	}
}
