package org.scribble.del.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.RecursionDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ProjectionEnv;
import org.scribble.visit.env.WFChoiceEnv;

public class GRecursionDel extends RecursionDel implements GCompoundInteractionNodeDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		GRecursion gr = (GRecursion) visited;
		//RecVarNode recvar = gr.recvar.clone();
		RecVarNode recvar = (RecVarNode) ((InlineProtocolEnv) gr.recvar.del().env()).getTranslation();	
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
		LProtocolBlock block =
				(LProtocolBlock) ((ProjectionEnv) gr.block.del().env()).getProjection();
				//((GProtocolBlockDel) gr.block.del()).project(gr.getBlock(), self);
		LRecursion projection = gr.project(proj.peekSelf(), block);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GRecursion) GCompoundInteractionNodeDel.super.leaveProjection(parent, child, proj, gr);
	}
	
	/*@Override
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
	}*/
	
	/*@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder graph) throws ScribbleException
	{
		super.enterModelBuilding(parent, child, graph);
		GRecursion gr = (GRecursion) child;
		RecVar rv = gr.recvar.toName();
		graph.builder.addEntryLabel(rv);
		graph.builder.pushRecursionEntry(rv, graph.builder.getEntry());
	}

	@Override
	public GRecursion leaveModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder graph, ScribNode visited) throws ScribbleException
	{
		GRecursion gr = (GRecursion) visited;
		RecVar rv = gr.recvar.toName();
		graph.builder.popRecursionEntry(rv);
		return (GRecursion) super.leaveModelBuilding(parent, child, graph, gr);
	}*/
}
