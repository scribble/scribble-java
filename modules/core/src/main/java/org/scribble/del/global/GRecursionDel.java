package org.scribble.del.global;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LContinue;
import org.scribble.ast.local.LInteractionNode;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.RecursionDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.WFChoicePathChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ProjectionEnv;
import org.scribble.visit.env.WFChoiceEnv;
import org.scribble.visit.env.WFChoicePathEnv;

public class GRecursionDel extends RecursionDel implements GCompoundInteractionNodeDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		GRecursion gr = (GRecursion) visited;
		RecVarNode recvar = gr.recvar.clone();
		GProtocolBlock block = (GProtocolBlock) ((InlineProtocolEnv) gr.block.del().env()).getTranslation();	
		GRecursion inlined = AstFactoryImpl.FACTORY.GRecursion(recvar, block);
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		return (GRecursion) super.leaveProtocolInlining(parent, child, inl, gr);
	}

	@Override
	public GRecursion leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GRecursion rec = (GRecursion) visited;
		WFChoiceEnv merged = checker.popEnv().mergeContext((WFChoiceEnv) rec.block.del().env());
		checker.pushEnv(merged);
		return (GRecursion) super.leaveInlinedWFChoiceCheck(parent, child, checker, rec);
	}

	@Override
	public GRecursion leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GRecursion gr = (GRecursion) visited;
		RecVarNode recvar = gr.recvar.clone();
		LRecursion projection = null;
		Set<RecVar> rvs = new HashSet<>();
		rvs.add(recvar.toName());
		LProtocolBlock pruned = prune((LProtocolBlock) ((ProjectionEnv) gr.block.del().env()).getProjection(), rvs);
		if (!pruned.isEmpty())
		{
			projection = AstFactoryImpl.FACTORY.LRecursion(recvar, pruned);
		}
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GRecursion) GCompoundInteractionNodeDel.super.leaveProjection(parent, child, proj, gr);
	}
	
	// Set unnecessary -- nested irrelevants continues should already have been pruned
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
			LInteractionNode lin = lis.get(0);
			if (lin instanceof LContinue)
			{
				if (rvs.contains(((LContinue) lin).recvar.toName()))
				{
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
	public void enterWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll) throws ScribbleException
	{
		WFChoicePathEnv env = coll.peekEnv().enterContext();
		env = env.clear();  // Because merge will append the child paths to the parent paths -- without clearing, the child paths already have the parent suffixes
		coll.pushEnv(env);
	}

	@Override
	public GRecursion leaveWFChoicePathCheck(ScribNode parent, ScribNode child, WFChoicePathChecker coll, ScribNode visited) throws ScribbleException
	//public GRecursion leavePathCollection(ScribNode parent, ScribNode child, PathCollectionVisitor coll, ScribNode visited) throws ScribbleException
	{
		GRecursion rec = (GRecursion) visited;
		WFChoicePathEnv merged = coll.popEnv().mergeContext((WFChoicePathEnv) rec.block.del().env());  // Corresponding push is in CompoundInteractionDel
		coll.pushEnv(merged);
		return (GRecursion) super.leaveWFChoicePathCheck(parent, child, coll, rec);
		//return (GRecursion) super.leavePathCollection(parent, child, coll, rec);
	}
}
