package org.scribble2.model.del;

import org.scribble2.model.CompoundInteractionNode;
import org.scribble2.model.ModelNode;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.util.ScribbleException;

public class CompoundInteractionNodeDelegate extends CompoundInteractionDelegate implements InteractionNodeDelegate
{
	//public CompoundInteractionNodeDelegate(Env env)
	public CompoundInteractionNodeDelegate()
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
		env = checker.popEnv().merge(env);  // Overrides super method to merge results back into parent context
		checker.pushEnv(env);
		setEnv(env);
		return (CompoundInteractionNode) visited;
	}
}
