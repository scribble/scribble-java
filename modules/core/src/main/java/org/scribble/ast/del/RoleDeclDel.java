package org.scribble.ast.del;

import org.scribble.ast.ModelNode;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.visit.NameDisambiguator;
import org.scribble.ast.visit.WellFormedChoiceChecker;
import org.scribble.ast.visit.env.WellFormedChoiceEnv;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;

public class RoleDeclDel extends ModelDelBase
{
	@Override
	//public void enterBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker) throws ScribbleException
	public void enterDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb) throws ScribbleException
	{
		RoleDecl rd = (RoleDecl) child;
		disamb.addRole(rd.getDeclName());
	}

	@Override
	public RoleDecl leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		WellFormedChoiceEnv env = checker.popEnv();
		RoleDecl rd = (RoleDecl) visited;
		Role role = rd.getDeclName();
		//Name dn = rd.getDeclarationName();
		/*if (env.isRoleBound(role))  // TODO: do for basic name checking (not WF choice)
		{
			throw new ScribbleException("Duplicate role delcaration: " + role);
		}*/

		//env.roles.enableRole(Role.EMPTY_ROLE, dn, RolesEnv.DEFAULT_ENABLING_OP);
		env = env.enableRoleForRootProtocolDecl(role);
		checker.pushEnv(env);
		//return rd;

		return (RoleDecl) super.leaveWFChoiceCheck(parent, child, checker, rd);
	}
}
