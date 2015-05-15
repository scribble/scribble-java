package org.scribble2.model;

import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.NameNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.Role;


//public interface RoleDecl extends HeaderParameterDecl
//public class RoleDecl extends HeaderParamDecl<RoleNode, Role>
//public class RoleDecl extends HeaderParamDecl<Role, RoleKind>
public class RoleDecl extends HeaderParamDecl<RoleKind>
{
	//public RoleDecl(RoleNode name)
	//public RoleDecl(NameNode<Role, RoleKind> name)
	//public RoleDecl(NameNode<RoleKind> name)
	public RoleDecl(RoleNode name)
	{
		//super(t, kind, namenode);
		super(name);
		//this.name = namenode;
	}

  /*// FIXME: types do not enforce this to return Role
	// -- but seems NameDeclNode will need to expose both NameNode and Name as separate type parameters to do so
	// -- in that case, then would need, e.g. RoleDecl and Role, in the type params -- not enforced to put Role there either, but at least avoids any casts
	@Override
	public Role toName()
	{
		//return ((RoleNode) this.name).toName();
		return (Role) super.toName();
	}*/

	@Override
	//protected RoleDecl reconstruct(RoleNode namenode)
	//protected RoleDecl reconstruct(NameNode<Role, RoleKind> namenode)
	protected RoleDecl reconstruct(NameNode<RoleKind> namenode)
	{
		ModelDel del = del();
		RoleDecl rd = new RoleDecl((RoleNode) namenode);
		rd = (RoleDecl) rd.del(del);
		return rd;
	}

	
	@Override
	public RoleDecl project(Role self)
	{
		//Role role = this.name.toName();
		Name<RoleKind> role = this.name.toName();
		//RoleNode rn = new RoleNode(role.toString());
		//RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, role.toString());
		RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(RoleKind.KIND, role.toString());
		if (role.equals(self))
		{
			//return new SelfRoleDecl(rn);
			return ModelFactoryImpl.FACTORY.SelfRoleDecl(rn);
		}
		//return new RoleDecl(rn);
		return ModelFactoryImpl.FACTORY.RoleDecl(rn);
	}
	
	@Override
	public Role getDeclName()
	{
		return (Role) super.getDeclName();
	}

	@Override
	protected RoleDecl copy()
	{
		return new RoleDecl((RoleNode) this.name);
	}
	
	public boolean isSelfRoleDecl()
	{
		return false;
	}

	@Override
	public String getKeyword()
	{
		return Constants.ROLE_KW;
	}
}

/*class RoleDecl extends HeaderParameterDecl<RoleNode> //implements NameDeclaration
{
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, RoleDecl> toRoleDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (RoleDecl) nd;* /

	//public final RoleNode role;
	
	public RoleDecl(RoleNode rn)
	{
		//super(ct, Kind.ROLE, rn);
		super(rn);
		//this.role = rn;
	}

	/*@Override
	public RoleDecl leaveProjection(Projector proj)  // Redundant now
	{
		RoleDecl projection = project(proj.peekSelf());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
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
	}* /
}*/
