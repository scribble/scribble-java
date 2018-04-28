package org.scribble.ext.go.del;

import org.scribble.ast.ScribNode;
import org.scribble.del.CompoundInteractionNodeDel;
import org.scribble.ext.go.ast.RPDel;
import org.scribble.ext.go.ast.RPForeach;
import org.scribble.main.ScribbleException;
import org.scribble.visit.InlinedProtocolUnfolder;
import org.scribble.visit.env.UnfoldingEnv;

// Based on ChoiceDecl
public class RPForeachDel extends CompoundInteractionNodeDel implements RPDel
{
	/*// Role names not disambiguated?
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{
		Recursion<?> rec = (Recursion<?>) child;
		RecVar rv = rec.recvar.toName();
		//disamb.addRecVar(rv);
		disamb.pushRecVar(rv);
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		RecVar rv = ((Recursion<?>) child).recvar.toName();  // visited may be already name mangled  // Not any more (refactored to inlining)
		disamb.popRecVar(rv);
		return rec;
	}*/

	/*@Override  // No special inlining behaviour for Foreach -- just visitChildren
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inliner) throws ScribbleException
	{
		super.enterProtocolInlining(parent, child, inliner);
		RPForeach<?> rec = (RPForeach<?>) child;
		inliner.pushRecVar(rec.recvar.toName());
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inliner, ScribNode visited) throws ScribbleException
	{
		//Recursion<?> rec = (Recursion<?>) visited;
		RecVar origRV = ((Recursion<?>) child).recvar.toName();  // visited may be already name mangled
		inliner.popRecVar(origRV);
		return super.leaveProtocolInlining(parent, child, inliner, visited);
	}*/

	/*// Anything special needed?
	@Override
	public void enterInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf) throws ScribbleException
	{
		super.enterInlinedProtocolUnfolding(parent, child, unf);
		Recursion<?> lr = (Recursion<?>) child;
		RecVar recvar = lr.recvar.toName();
		unf.setRecVar(unf.job.af, recvar, lr);  // Cloned on use (on continue)

		UnfoldingEnv env = unf.peekEnv().enterContext();
		env = env.pushChoiceParent();  // Above is already a copy, but fine
		unf.pushEnv(env);
	}*/

	@Override
	public RPForeach<?> leaveInlinedProtocolUnfolding(ScribNode parent, ScribNode child, InlinedProtocolUnfolder unf, ScribNode visited) throws ScribbleException
	{
		RPForeach<?> fe = (RPForeach<?>) visited;
		UnfoldingEnv merged = unf.popEnv().mergeContext((UnfoldingEnv) fe.block.del().env()); // Merge child into current...
		unf.pushEnv(merged);
		return (RPForeach<?>) super.leaveInlinedProtocolUnfolding(parent, child, unf, fe);   // ...super merges current into parent

		/*Choice<?> cho = (Choice<?>) visited;
		List<UnfoldingEnv> benvs =
				cho.getBlocks().stream().map(b -> (UnfoldingEnv) b.del().env()).collect(Collectors.toList());
		UnfoldingEnv merged = unf.popEnv().mergeContexts(benvs); 
		unf.pushEnv(merged);
		return (Choice<?>) super.leaveInlinedProtocolUnfolding(parent, child, unf, visited);  // Done merge of children here, super does merge into parent*/
	}

	/*@Override
	public ScribNode leaveIndexVarCollection(ScribNode parent, ScribNode child, RPCoreIndexVarCollector coll, ScribNode visited) throws ScribbleException
	{
		// No: the foreach index vars are bound
	}*/

	/*// Needed? -- how about Recursion?
	@Override
	public void enterExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker) throws ScribbleException
	{
		ExplicitCorrelationEnv env = checker.peekEnv().enterContext();
		checker.pushEnv(env);
	}

	@Override
	public Choice<?> leaveExplicitCorrelationCheck(ScribNode parent, ScribNode child, ExplicitCorrelationChecker checker, ScribNode visited) throws ScribbleException
	{
		Choice<?> cho = (Choice<?>) visited;
		List<ExplicitCorrelationEnv> benvs =
				cho.getBlocks().stream().map((b) -> (ExplicitCorrelationEnv) b.del().env()).collect(Collectors.toList());
		ExplicitCorrelationEnv merged = checker.popEnv().mergeContexts(benvs); 
		checker.pushEnv(merged);
		return (Choice<?>) super.leaveExplicitCorrelationCheck(parent, child, checker, visited);  // Done merge of children here, super does merge into parent	
	}*/
}
