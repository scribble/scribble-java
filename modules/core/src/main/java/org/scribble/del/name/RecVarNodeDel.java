package org.scribble.del.name;

import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.NameDisambiguator;

public class RecVarNodeDel extends ScribDelBase
{
	public RecVarNodeDel()
	{

	}

	@Override
	public RecVarNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		// Consistent with bound RoleNode checking
		RecVarNode rn = (RecVarNode) visited;
		RecVar rv = rn.toName();
		if (!disamb.isBoundRecVar(rv))
		{
			throw new ScribbleException("Rec variable not bound: " + rn);
		}
		//return rn;
		return rn.reconstruct(disamb.getCanonicalRecVarName(rv));
	}
}
