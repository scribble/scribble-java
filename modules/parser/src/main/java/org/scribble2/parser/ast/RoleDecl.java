package scribble2.ast;

import org.antlr.runtime.tree.CommonTree;

import scribble2.ast.local.SelfRoleDecl;
import scribble2.ast.name.PrimitiveNameNode;
import scribble2.ast.name.RoleNode;
import scribble2.main.ScribbleException;
//import scribble2.sesstype.name.NameDeclaration;
import scribble2.sesstype.name.Kind;
import scribble2.sesstype.name.Role;
import scribble2.visit.NodeVisitor;
import scribble2.visit.Projector;
import scribble2.visit.env.ProjectionEnv;

public class RoleDecl extends NameDecl<RoleNode> //implements NameDeclaration
{
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, RoleDecl> toRoleDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (RoleDecl) nd;*/

	//public final RoleNode role;
	
	public RoleDecl(CommonTree ct, RoleNode rn)
	{
		super(ct, Kind.ROLE, rn);
		//this.role = rn;
	}

	@Override
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
		NameDecl<? extends PrimitiveNameNode> nd = super.visitChildren(nv);
		return new RoleDecl(nd.ct, (RoleNode) nd.name);
	}
	
	/*@Override
	public Role getDeclarationName()
	{
		Name name = (hasAlias()) ? this.alias.toName() : this.name.toName(); 
		return new Role(name.text);
	}*/
	
	public boolean isSelfRoleDecl()
	{
		return false;
	}
}
