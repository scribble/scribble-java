package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LAccept;
import org.scribble.del.ConnectionActionDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.Accept;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.UnguardedChoiceDoProjectionChecker;
import org.scribble.visit.EndpointGraphBuilder;
import org.scribble.visit.ProjectedChoiceSubjectFixer;
import org.scribble.visit.env.UnguardedChoiceDoEnv;

public class LWrapServerDel extends ConnectionActionDel implements LSimpleInteractionNodeDel
{
	@Override
	public LAccept leaveEndpointGraphBuilding(ScribNode parent, ScribNode child, EndpointGraphBuilder builder, ScribNode visited) throws ScribbleException
	{
		LAccept la = (LAccept) visited;
		Role peer = la.src.toName();
		builder.builder.addEdge(builder.builder.getEntry(), new Accept(peer), builder.builder.getExit());
		return (LAccept) super.leaveEndpointGraphBuilding(parent, child, builder, la);
	}

	@Override
	public void enterProjectedChoiceSubjectFixing(ScribNode parent, ScribNode child, ProjectedChoiceSubjectFixer fixer)
	{
		fixer.setChoiceSubject(((LAccept) child).src.toName());
	}

	@Override
	public void enterUnguardedChoiceDoProjectionCheck(ScribNode parent, ScribNode child, UnguardedChoiceDoProjectionChecker checker) throws ScribbleException
	{
		super.enterUnguardedChoiceDoProjectionCheck(parent, child, checker);
		LAccept la = (LAccept) child;
		UnguardedChoiceDoEnv env = checker.popEnv();
		env = env.setChoiceSubject(la.src.toName());
		checker.pushEnv(env);
	}
}
