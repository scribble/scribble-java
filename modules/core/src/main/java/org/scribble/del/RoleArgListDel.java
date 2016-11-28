package org.scribble.del;

import java.util.List;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.wf.NameDisambiguator;

public class RoleArgListDel extends DoArgListDel
{
	public RoleArgListDel()
	{

	}

	@Override
	public RoleArgList leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		visited = super.leaveDisambiguation(parent, child, disamb, visited);

		// Duplicate check not needed for NonRoleArgList
		RoleArgList ral = (RoleArgList) visited;
		List<Role> roles = ral.getRoles();
		//if (roles.size() != new HashSet<>(roles).size())
		if (roles.size() != roles.stream().distinct().count())
		{
			throw new ScribbleException("Duplicate role args: " + roles);
		}
		return ral;
	}

	@Override
	protected RoleDeclList getParamDeclList(ProtocolDecl<?> pd)
	{
		return pd.header.roledecls;
	}
}
