package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LWrapServer;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.actions.EWrapServer;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.EGraphBuilder;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;
import org.scribble.visit.context.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.context.env.UnguardedChoiceDoEnv;

public class LWrapServerDel extends ConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LWrapServer leaveEGraphBuilding(ScribNode parent, ScribNode child, EGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LWrapServer la = (LWrapServer) visited;
		Role peer = la.src.toName();
		builder.util.addEdge(builder.util.getEntry(), new EWrapServer(peer), builder.util.getExit());
		return (LWrapServer) super.leaveEGraphBuilding(parent, child, builder, la);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LWrapServer) child).src.toName());
	}

	@Override
	public void enterUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker) throws ScribbleException
	{
		super.enterUnguardedChoiceDoProjectionCheck(parent, child, checker);
		LWrapServer la = (LWrapServer) child;
		UnguardedChoiceDoEnv env = checker.popEnv();
		env = env.setChoiceSubject(la.src.toName());
		checker.pushEnv(env);
	}
}
