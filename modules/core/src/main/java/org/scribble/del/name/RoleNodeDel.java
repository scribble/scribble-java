package org.scribble.del.name;

import org.scribble.ast.ModelNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.visit.NameDisambiguator;
import org.scribble.del.ModelDelBase;
import org.scribble.util.ScribbleException;


public class RoleNodeDel extends ModelDelBase
{
	public RoleNodeDel()
	{

	}

	@Override
	//public RoleNode leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException
	public ModelNode leaveDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb, ModelNode visited) throws ScribbleException
	{
		RoleNode rn = (RoleNode) visited;
		if (!disamb.isBoundRole(rn.toName()))
		{
			throw new ScribbleException("Role not bound: " + rn);
		}
		return rn;
	}
}
