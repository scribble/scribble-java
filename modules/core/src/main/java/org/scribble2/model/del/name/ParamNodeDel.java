package org.scribble2.model.del.name;

import org.scribble2.model.ModelNode;
import org.scribble2.model.del.ModelDelBase;
import org.scribble2.model.name.simple.ParamNode;
import org.scribble2.model.visit.BoundNameChecker;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.util.ScribbleException;


public class ParamNodeDel extends ModelDelBase
{
	public ParamNodeDel()
	{

	}

	@Override
	public ParamNode<? extends Kind> leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException
	{
		ParamNode<? extends Kind> pn = cast(visited);
		if (!checker.isBoundParam(pn.toName()))
		{
			throw new ScribbleException("Parameter not bound: " + pn);  // Will never happen due to name disambiguation
		}
		return pn;
	}
	
	private static ParamNode<? extends Kind> cast(ModelNode n)
	{
		@SuppressWarnings("unchecked")
		ParamNode<? extends Kind> tmp = (ParamNode<? extends Kind>) n;
		return tmp;
	}
}
