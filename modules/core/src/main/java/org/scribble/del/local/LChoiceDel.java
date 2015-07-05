package org.scribble.del.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ChoiceDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.visit.ProjectedChoiceSubjectFixer;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.ReachabilityChecker;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ReachabilityEnv;

public class LChoiceDel extends ChoiceDel implements LCompoundInteractionNodeDel
{
	@Override
	public ScribNode leaveProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer, ScribNode visited)
	{
		LChoice lc = (LChoice) visited;
		List<LProtocolBlock> blocks = lc.getBlocks();
		RoleNode subj = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND,
				blocks.get(0).getInteractionSeq().getActions().get(0).inferLocalChoiceSubject(fixer).toString());
		LChoice projection = AstFactoryImpl.FACTORY.LChoice(subj, blocks);
		return projection;
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		LChoice gc = (LChoice) visited;
		List<LProtocolBlock> blocks = 
				gc.blocks.stream().map((b) -> (LProtocolBlock) ((InlineProtocolEnv) b.del().env()).getTranslation()).collect(Collectors.toList());	
		RoleNode subj = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, gc.subj.toName().toString());
		LChoice inlined = AstFactoryImpl.FACTORY.LChoice(subj, blocks);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));
		return (LChoice) super.leaveProtocolInlining(parent, child, builder, gc);
	}

	@Override
	public LChoice leaveReachabilityCheck(ScribNode parent, ScribNode child, ReachabilityChecker checker, ScribNode visited) throws ScribbleException
	{
		LChoice cho = (LChoice) visited;
		List<ReachabilityEnv> benvs =
				cho.blocks.stream().map((b) -> (ReachabilityEnv) b.del().env()).collect(Collectors.toList());
		ReachabilityEnv merged = checker.popEnv().mergeForChoice(benvs);
		checker.pushEnv(merged);
		return (LChoice) LCompoundInteractionNodeDel.super.leaveReachabilityCheck(parent, child, checker, visited);  // records the current checker Env to the current del; also pops and merges that env into the parent env
	}
}
