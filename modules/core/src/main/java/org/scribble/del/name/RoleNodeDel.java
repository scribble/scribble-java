package org.scribble.del.name;

import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.visit.NameDisambiguator;
import org.scribble.del.ScribDelBase;
import org.scribble.util.ScribbleException;


public class RoleNodeDel extends ScribDelBase
{
	public RoleNodeDel()
	{

	}

	@Override
	//public RoleNode leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		RoleNode rn = (RoleNode) visited;
		if (!disamb.isBoundRole(rn.toName()))
		{
			throw new ScribbleException("Role not bound: " + rn);
		}
		return rn;
	}
}
