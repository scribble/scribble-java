package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;

public class GDo extends Do<Global> implements GSimpleInteractionNode
{
	public GDo(RoleArgList roles, NonRoleArgList args, GProtocolNameNode proto)
	{
		super(roles, args, proto);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new GDo(this.roles, this.args, getProtocolNameNode());
	}
	
	@Override
	public GDo clone()
	{
		RoleArgList roles = this.roles.clone();
		NonRoleArgList args = this.args.clone();
		GProtocolNameNode proto = this.getProtocolNameNode().clone();
		return AstFactoryImpl.FACTORY.GDo(roles, args, proto);
	}

	@Override
	protected GDo reconstruct(RoleArgList roles, NonRoleArgList args, ProtocolNameNode<Global> proto)
	{
		ScribDel del = del();
		GDo gd = new GDo(roles, args, (GProtocolNameNode) proto);
		gd = (GDo) gd.del(del);
		return gd;
	}

	@Override
	public GProtocolNameNode getProtocolNameNode()
	{
		return (GProtocolNameNode) this.proto;
	}

	@Override
	public GProtocolName getTargetFullProtocolName(ModuleContext mcontext)
	{
		return (GProtocolName) super.getTargetFullProtocolName(mcontext);
	}
}
