package org.scribble2.model.name.simple;

import org.scribble2.sesstype.kind.RecVarKind;
import org.scribble2.sesstype.name.RecVar;



//public class RecursionVarNode extends SimpleNameNode
public class RecVarNode extends SimpleNameNode<RecVar, RecVarKind>
{
	public RecVarNode(String identifier)
	{
		super(identifier);
	}

	/*@Override
	protected RecursionVarNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		RecursionVarNode rvn = new RecursionVarNode(identifier);
		rvn = (RecursionVarNode) rvn.del(del);
		return rvn;
	}*/

	@Override
	protected RecVarNode copy()
	{
		return new RecVarNode(this.identifier);
	}

	@Override
	public RecVar toName()
	{
		return new RecVar(this.identifier);
	}
}
