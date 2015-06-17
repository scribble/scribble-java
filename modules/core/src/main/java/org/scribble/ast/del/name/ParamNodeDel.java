package org.scribble.ast.del.name;

import org.scribble.ast.del.ModelDelBase;


public class ParamNodeDel extends ModelDelBase
{
	public ParamNodeDel()
	{

	}

	/*@Override
	//public ParamNode<? extends Kind> leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException
	public ModelNode leaveDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb, ModelNode visited) throws ScribbleException
	{
		ParamNode<? extends Kind> pn = cast(visited);
		if (!disamb.isBoundParameter(pn.toName()))
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
	}*/
}
