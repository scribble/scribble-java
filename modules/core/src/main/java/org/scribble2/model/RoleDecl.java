package org.scribble2.model;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.name.simple.SimpleNameNode;
import org.scribble2.sesstype.name.Role;


//public interface RoleDecl extends HeaderParameterDecl
public class RoleDecl extends HeaderParameterDecl<RoleNode>
{
	public RoleDecl(RoleNode namenode)
	{
		//super(t, kind, namenode);
		super(namenode);
		//this.name = namenode;
	}

	@Override
	public Role toName()
	{
		return ((RoleNode) this.name).toName();
	}

	@Override
	protected RoleDecl reconstruct(SimpleNameNode namenode)
	{
		ModelDelegate del = del();
		RoleDecl rd = new RoleDecl((RoleNode) namenode);
		rd = (RoleDecl) rd.del(del);
		return rd;
	}

	public RoleDecl project(Role self)
	{
		Role role = this.name.toName();
		//RoleNode rn = new RoleNode(role.toString());
		RoleNode rn = (RoleNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.ROLE, role.toString());
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
		return new RoleDecl((RoleNode) this.name);
	}
	
	public boolean isSelfRoleDecl()
	{
		return false;
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