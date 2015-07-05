package org.scribble.del.global;

import java.util.List;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Continue;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.RecursionDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.visit.InlinedWFChoiceChecker;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.InlinedWFChoiceEnv;
import org.scribble.visit.env.ProjectionEnv;
import org.scribble.visit.env.WFChoiceEnv;

public class GRecursionDel extends RecursionDel implements GCompoundInteractionNodeDel
{
	@Override
	public GRecursion leaveWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GRecursion rec = (GRecursion) visited;
		WFChoiceEnv merged = checker.popEnv().mergeContext((WFChoiceEnv) rec.block.del().env());
		checker.pushEnv(merged);
		return (GRecursion) super.leaveWFChoiceCheck(parent, child, checker, rec);
	}

	@Override
	public GRecursion leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GRecursion gr = (GRecursion) visited;
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, gr.recvar.toName().toString());
		LProtocolBlock block = (LProtocolBlock) ((ProjectionEnv) gr.block.del().env()).getProjection();	
		LRecursion projection = null;
		if (!block.isEmpty())
		{
			List<? extends InteractionNode<Local>> lis = block.seq.actions;
			if (!(lis.size() == 1 && lis.get(0) instanceof Continue))
			{
				projection = AstFactoryImpl.FACTORY.LRecursion(recvar, block);
			}
		}
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GRecursion) GCompoundInteractionNodeDel.super.leaveProjection(parent, child, proj, gr);
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		GRecursion gr = (GRecursion) visited;
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, gr.recvar.toName().toString());
		GProtocolBlock block = (GProtocolBlock) ((InlineProtocolEnv) gr.block.del().env()).getTranslation();	
		GRecursion inlined = AstFactoryImpl.FACTORY.GRecursion(recvar, block);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (GRecursion) super.leaveProtocolInlining(parent, child, builder, gr);
	}

	@Override
	public GRecursion leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, InlinedWFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		GRecursion rec = (GRecursion) visited;
		InlinedWFChoiceEnv merged = checker.popEnv().mergeContext((InlinedWFChoiceEnv) rec.block.del().env());
		checker.pushEnv(merged);
		return (GRecursion) super.leaveInlinedWFChoiceCheck(parent, child, checker, rec);
	}
}
