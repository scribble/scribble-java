package org.scribble2.foo.ast;

import org.antlr.runtime.Token;
import org.scribble2.foo.ast.name.simple.RoleNode;

public class RoleDecl extends HeaderParameterDecl<RoleNode> //implements NameDeclaration
{
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, RoleDecl> toRoleDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (RoleDecl) nd;*/

	//public final RoleNode role;
	
	public RoleDecl(Token t, RoleNode rn)
	{
		//super(ct, Kind.ROLE, rn);
		super(t, rn);
		//this.role = rn;
	}

	/*@Override
	public RoleDecl leaveProjection(Projector proj)  // Redundant now
	{
		RoleDecl projection = project(proj.peekSelf());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	public RoleDecl project(Role self)
	{
		Role role = this.name.toName();
		RoleNode rn = new RoleNode(null, role.toString());
		if (role.equals(self))
		{
			return new SelfRoleDecl(null, rn);
		}
		return new RoleDecl(null, rn);
	}

	@Override
	public RoleDecl visitChildren(NodeVisitor nv) throws ScribbleException
	{
		HeaderNameDecl<? extends PrimitiveNameNode> nd = super.visitChildren(nv);
		return new RoleDecl(nd.ct, (RoleNode) nd.name);
	}*/
	
	/*@Override
	public Role getDeclarationName()
	{
		Name name = (hasAlias()) ? this.alias.toName() : this.name.toName(); 
		return new Role(name.text);
	}*/
	
	/*public boolean isSelfRoleDecl()
	{
		return false;
	}*/
}