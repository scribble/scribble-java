package org.scribble2.model.del.name;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ModelDelBase;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.visit.BoundNameChecker;
import org.scribble2.util.ScribbleException;


public class RoleNodeDel extends ModelDelBase
{
	public RoleNodeDel()
	{

	}

	@Override
	public RoleNode leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException
	{
		RoleNode rn = (RoleNode) visited;
		if (!checker.isBoundRole(rn.toName()))
		{
			throw new ScribbleException("Role not bound: " + rn);
		}
		return rn;
	}
}
