package org.scribble.ast.local;

import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;

public class LDo extends Do<Local> implements LSimpleInteractionNode
{
	public LDo(RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto)
	{
		super(roleinstans, arginstans, proto);
	}

	@Override
	protected LDo copy()
	{
		return new LDo(this.roles, this.args, (LProtocolNameNode) this.proto);
	}
	
	@Override
	protected LDo reconstruct(RoleArgList roleinstans, NonRoleArgList arginstans, ProtocolNameNode<Local> proto)
	{
		ScribDel del = del();
		LDo ld = new LDo(roleinstans, arginstans, (LProtocolNameNode) proto);
		ld = (LDo) ld.del(del);
		return ld;
	}

	@Override
	public LProtocolName getTargetFullProtocolName(ModuleContext mcontext)
	{
		return (LProtocolName) super.getTargetFullProtocolName(mcontext);
	}
}
