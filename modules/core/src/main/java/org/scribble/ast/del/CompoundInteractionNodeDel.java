package org.scribble.ast.del;

import org.scribble.ast.CompoundInteractionNode;
import org.scribble.ast.ModelNode;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.ast.visit.env.WellFormedChoiceEnv;
import org.scribble.util.ScribbleException;

// Unlike global/local interaction model nodes that extend the base constructs (choice/recursion/etc) that extend simple/compound,
// each interaction construct delegate extends base global/local delegates that extend simple/compound
// e.g. CompoundInteractionNode -> Choice -> Global/LocalChoice
//      CompoundInteractionNodeDelegate -> Global/LocalCompoundInteractionNodeDelegate -> [Global/Local]ChoiceDelegate
// works better for each that way: global/local model nodes share the base node visiting pattern, while global/local delegates share Env handling based on simple/compound (i.e. Env merging) for passes according to global or local
public abstract class CompoundInteractionNodeDel extends CompoundInteractionDel implements InteractionNodeDel
{
	//public CompoundInteractionNodeDelegate(Env env)
	public CompoundInteractionNodeDel()
	{
		//super(env);
	}

	/*@Override
	public WellFormedChoiceChecker enterWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker) throws ScribbleException
	{
		//WellFormedChoiceEnv env = new WellFormedChoiceEnv(checker.peekEnv());
		WellFormedChoiceEnv env = checker.peekEnv().push();
		/*env.initial.clear();
		env.initialInterrupts.clear();*
		//env = env.enableChoiceSubject(((GlobalChoice) child).subj.toName());
		//checker.setEnv(env);
		checker.pushEnv(env);
		return checker;
	}*/
	//public void enter(Choice<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> cho, WellFormedChoiceChecker checker)
	
	@Override
	public CompoundInteractionNode leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		WellFormedChoiceEnv env = checker.popEnv();
		setEnv(env);
		env = checker.popEnv().mergeContext(env);  // Overrides super method to merge results back into parent context
		checker.pushEnv(env);
		//setEnv(env);
		return (CompoundInteractionNode) visited;
	}
	
	/*@Override
	public CompoundInteractionNode leaveReachabilityCheck(ModelNode parent, ModelNode child, ReachabilityChecker checker, ModelNode visited) throws ScribbleException
	{
		ReachabilityEnv env = checker.popEnv();
		env = checker.popEnv().merge(env);  // Overrides super method to merge results back into parent context
		checker.pushEnv(env);
		setEnv(env);
		return (CompoundInteractionNode) visited;
	}*/
}
