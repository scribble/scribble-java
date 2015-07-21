package org.scribble.del;

import org.scribble.ast.RoleDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.WFChoiceChecker;
import org.scribble.visit.NameDisambiguator;

public class RoleDeclDel extends ScribDelBase
{
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{
		RoleDecl rd = (RoleDecl) child;
		disamb.addRole(rd.getDeclName());  // Could check distinct here, but doing it uniformly in HeaderParamDeclListDel
	}

	@Override
	public RoleDecl leaveInlinedWFChoiceCheck(ScribNode parent, ScribNode child, WFChoiceChecker checker, ScribNode visited) throws ScribbleException
	{
		RoleDecl rd = (RoleDecl) visited;
		Role role = rd.getDeclName();
		checker.pushEnv(checker.popEnv().enableRoleForRootProtocolDecl(role));
		return (RoleDecl) super.leaveInlinedWFChoiceCheck(parent, child, checker, rd);
	}
}
