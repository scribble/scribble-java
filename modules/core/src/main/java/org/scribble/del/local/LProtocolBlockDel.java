package org.scribble.del.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.ProtocolBlockDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.visit.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.InlineProtocolEnv;

public class LProtocolBlockDel extends ProtocolBlockDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		LProtocolBlock gpd = (LProtocolBlock) visited;
		LInteractionSeq seq = (LInteractionSeq) ((InlineProtocolEnv) gpd.seq.del().env()).getTranslation();	
		LProtocolBlock inlined = AstFactoryImpl.FACTORY.LProtocolBlock(seq);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (LProtocolBlock) ScribDelBase.popAndSetVisitorEnv(this, inl, gpd);
	}
	
	/*@Override
	public ScribNode leaveChoiceUnguardedSubprotocolCheck(ScribNode parent, ScribNode child, ChoiceUnguardedSubprotocolChecker checker, ScribNode visited) throws ScribbleException
	{
		
	}*/

	@Override
	public void enterReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, checker);
	}

	@Override
	public LProtocolBlock leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		return (LProtocolBlock) ScribDelBase.popAndSetVisitorEnv(this, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}

	/*@Override
	public void enterProjectedSubprotocolPruning(ScribNode parent, ScribNode child, ProjectedSubprotocolPruner pruner) throws ScribbleException
	{

	}*/

	/*@Override
	public ScribNode leaveProjectedSubprotocolPruning(ScribNode parent, ScribNode child, ProjectedSubprotocolPruner pruner, ScribNode visited) throws ScribbleException
	{
		LProtocolBlock block = (LProtocolBlock) visited;
		return block;
	}*/

	/*// Cf GRecursion.prune
	private static LProtocolBlock prune(LProtocolBlock block)
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
			if (lin == null)
			{
				return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Collections.emptyList()));
			}
		}
		return block;
			/*else if (lin instanceof MessageTransfer<?> || lin instanceof LContinue)
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
						if (!prune(b).isEmpty())
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
					LProtocolBlock prune = prune(((LRecursion) lin).getBlock()); 
					if (prune.isEmpty())
					{
						return prune;
					}
					else
					{
						return AstFactoryImpl.FACTORY.LProtocolBlock(AstFactoryImpl.FACTORY.LInteractionSeq(Arrays.asList(AstFactoryImpl.FACTORY.LRecursion(((LRecursion) lin).recvar, prune))));
					}
				}
				else
				{
					throw new RuntimeException("TODO: " + lin);
				}
			}
		}* /
	}*/
}
