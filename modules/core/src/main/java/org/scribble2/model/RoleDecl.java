package org.scribble2.model;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.KindedName;
import org.scribble2.sesstype.name.Role;


//public interface RoleDecl extends HeaderParameterDecl
//public class RoleDecl extends HeaderParameterDecl<RoleNode, Role>
public class RoleDecl extends HeaderParameterDecl<RoleKind>
{
	//public RoleDecl(RoleNode name)
	public RoleDecl(SimpleKindedNameNode<RoleKind> name)
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
	protected RoleDecl reconstruct(SimpleKindedNameNode<RoleKind> namenode)
	{
		ModelDelegate del = del();
		RoleDecl rd = new RoleDecl(namenode);
		rd = (RoleDecl) rd.del(del);
		return rd;
	}

	
	@Override
	public RoleDecl project(Role self)
	{
		//Role role = this.name.toName();
		KindedName<RoleKind> role = this.name.toName();
		////RoleNode rn = new RoleNode(role.toString());
		//RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, role.toString());
		SimpleKindedNameNode<RoleKind> rn = ModelFactoryImpl.FACTORY.SimpleKindedNameNode(RoleKind.KIND, role.toString());
		if (role.equals(self))
		{
			//return new SelfRoleDecl(rn);
			return ModelFactoryImpl.FACTORY.SelfRoleDecl(rn);
		}
		//return new RoleDecl(rn);
		return ModelFactoryImpl.FACTORY.RoleDecl(rn);
	}

	@Override
	protected RoleDecl copy()
	{
		return new RoleDecl(this.name);
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
