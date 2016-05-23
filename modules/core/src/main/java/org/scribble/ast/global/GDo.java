package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LDo;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.JobContext;

public class GDo extends Do<Global> implements GSimpleInteractionNode
{
	public GDo(RoleArgList roles, NonRoleArgList args, GProtocolNameNode proto)
	{
		super(roles, args, proto);
	}

	public LDo project(Role self, LProtocolNameNode fullname)
	{
		RoleArgList roleinstans = this.roles.project(self);
		NonRoleArgList arginstans = this.args.project(self);
		LDo projection = AstFactoryImpl.FACTORY.LDo(roleinstans, arginstans, fullname);
		return projection;
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
	public GDo reconstruct(RoleArgList roles, NonRoleArgList args, ProtocolNameNode<Global> proto)
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
	public GProtocolName getTargetProtocolDeclFullName(ModuleContext mcontext)
	{
		return (GProtocolName) super.getTargetProtocolDeclFullName(mcontext);
	}

	@Override
	public GProtocolDecl getTargetProtocolDecl(JobContext jcontext, ModuleContext mcontext)
	{
		return (GProtocolDecl) super.getTargetProtocolDecl(jcontext, mcontext);
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}
}
