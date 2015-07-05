package org.scribble.del.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Choice;
import org.scribble.ast.Do;
import org.scribble.ast.InteractionNode;
import org.scribble.ast.Interruptible;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.Parallel;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.ScribNode;
import org.scribble.ast.SimpleInteractionNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LChoice;
import org.scribble.ast.local.LDo;
import org.scribble.ast.local.LInterruptible;
import org.scribble.ast.local.LParallel;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ChoiceDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.kind.RoleKind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.JobContext;
import org.scribble.visit.ModuleVisitor;
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
		RoleNode subj = (RoleNode) AstFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, getSubject(fixer, blocks.get(0)).toString());
		LChoice projection = AstFactoryImpl.FACTORY.LChoice(subj, blocks);
		return projection;
	}

	// Relies on WF rule for same enabler role in all choice blocks
	private static Role getSubject(ModuleVisitor mv, ProtocolBlock<Local> block)
	{
		InteractionNode<Local> ln = block.seq.actions.get(0);
		Role subj;
		if (ln instanceof SimpleInteractionNode)
		{
			// By well-formedness and projection, cannot be RecursionVar, but it can be a (recursive) subprotocol
			if (ln instanceof MessageTransfer)
			{
				subj = ((MessageTransfer<Local>) ln).src.toName();
			}
			else if (ln instanceof Do)
			{
				LDo ld = (LDo) ln;
				ModuleContext mc = mv.getModuleContext();
				JobContext jc = mv.getJobContext();
				return getSubject(mv, ld.getTargetProtocolDecl(jc, mc).def.block);
			}
			else
			{
				throw new RuntimeException("TODO: " + ln);
			}
		}
		else //if (ln instanceof CompoundInteractionNode)
		{
			// Factor out CompoundBlockedNode?
			if (ln instanceof Choice)
			{
				return getSubject(mv, ((LChoice) ln).blocks.get(0));
			}
			else if (ln instanceof Recursion)
			{
				return getSubject(mv, ((LRecursion) ln).block);
			}
			else if (ln instanceof Parallel)
			{
				return getSubject(mv, ((LParallel) ln).blocks.get(0));
			}
			else if (ln instanceof Interruptible)
			{
				return getSubject(mv, ((LInterruptible) ln).block);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + ln);
			}
		}
		return subj;
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

	/*@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		UnfoldingEnv env = unf.peekEnv().enterContext();
		env = env.pushChoiceParent();
		unf.pushEnv(env);
	}

	@Override
	public ScribNode leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		LChoice cho = (LChoice) visited;
		List<UnfoldingEnv> benvs =
				cho.blocks.stream().map((b) -> (UnfoldingEnv) b.del().env()).collect(Collectors.toList());
		UnfoldingEnv merged = unf.popEnv().mergeContexts(benvs); 
		unf.pushEnv(merged);
		return (LChoice) super.leaveInlinedProtocolUnfolding(parent, child, unf, visited);
	}*/

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
