package org.scribble2.model.del;

import org.scribble2.model.ModelNode;
import org.scribble2.model.RoleDecl;
import org.scribble2.model.visit.BoundNameChecker;
import org.scribble2.model.visit.WellFormedChoiceChecker;
import org.scribble2.model.visit.env.WellFormedChoiceEnv;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class RoleDeclDel extends ModelDelBase
{
	@Override
	public void enterBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker) throws ScribbleException
	{
		RoleDecl rd = (RoleDecl) child;
		checker.addRole(rd.toName());
	}

	@Override
	public RoleDecl leaveWFChoiceCheck(ModelNode parent, ModelNode child, WellFormedChoiceChecker checker, ModelNode visited) throws ScribbleException
	{
		WellFormedChoiceEnv env = checker.popEnv();
		RoleDecl rd = (RoleDecl) visited;
		Role role = rd.name.toName();
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
