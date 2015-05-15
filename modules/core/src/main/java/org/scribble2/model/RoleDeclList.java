package org.scribble2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.kind.RoleKind;
import org.scribble2.sesstype.name.Role;

//public class RoleDeclList extends HeaderParamDeclList<RoleDecl, Role>
//public class RoleDeclList extends HeaderParamDeclList<Role, RoleKind>
public class RoleDeclList extends HeaderParamDeclList<RoleKind>
{
	//public RoleDeclList(List<RoleDecl> decls)
	//public RoleDeclList(List<HeaderParamDecl<Role, RoleKind>> decls)
	//public RoleDeclList(List<HeaderParamDecl<RoleKind>> decls)
	public RoleDeclList(List<RoleDecl> decls)
	{
		super(decls);
	}
	
	public List<RoleDecl> getRoleDecls()
	{
		return this.decls.stream().map((d) -> (RoleDecl) d).collect(Collectors.toList());
	}

	@Override
	protected RoleDeclList copy()
	{
		return new RoleDeclList(getRoleDecls());
	}

	@Override
	//protected HeaderParamDeclList<RoleDecl, Role> reconstruct(List<RoleDecl> decls)
	//protected HeaderParamDeclList<Role, RoleKind> reconstruct(List<HeaderParamDecl<Role, RoleKind>> decls)
	protected HeaderParamDeclList<RoleKind> reconstruct(List<? extends HeaderParamDecl<RoleKind>> decls)
	{
		ModelDel del = del();
		RoleDeclList rdl = new RoleDeclList(getRoleDecls());
		rdl = (RoleDeclList) rdl.del(del);
		return rdl;
	}

	// FIXME: move to delegate?
	@Override
	public RoleDeclList project(Role self)
	{
		//List<RoleDecl> roledecls = this.decls.stream().map((rd) -> rd.project(self)).collect(Collectors.toList());	
		//List<HeaderParamDecl<Role, RoleKind>> roledecls = this.decls.stream().map((rd) -> rd.project(self)).collect(Collectors.toList());	
		//List<HeaderParamDecl<RoleKind>> roledecls = this.decls.stream().map((rd) -> rd.project(self)).collect(Collectors.toList());	
		//return new RoleDeclList(roledecls);
		return ModelFactoryImpl.FACTORY.RoleDeclList(getRoleDecls());
	}

	/*@Override 
	public RoleDeclList checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		Set<Name> ns = new HashSet<>();
		Set<Name> dns = new HashSet<>();
		for (RoleDecl rd : this.rds)
		{
			Name n = rd.name.toName();
			Name dn = rd.getDeclarationName();
			if (ns.contains(n))
			{
				throw new ScribbleException("Duplicate role delcaration: " + n);
			}
			if (dns.contains(dn))
			{
				throw new ScribbleException("Duplicate role delcaration: " + dn);
			}
			ns.add(dn);
			dns.add(dn);
		}
		return (RoleDeclList) super.checkWellFormedness(wfc);
	}
	
	@Override 
	public RoleDeclList leave(EnvVisitor nv) throws ScribbleException
	{
		RoleDeclList rdl = (RoleDeclList) super.leave(nv);
		Env env = nv.getEnv();
		for (RoleDecl rd : rdl.rds)
		{
			env.roles.enableRole(Role.EMPTY_ROLE, rd.getDeclarationName(), RolesEnv.DEFAULT_ENABLING_OP);
		}
		return rdl;
	}*/

	/*@Override
	public RoleDeclList leaveProjection(Projector proj)  // Redundant now
	{
		/*List<RoleDecl> roledecls =
				this.decls.stream().map((rd) -> (RoleDecl) ((ProjectionEnv) rd.getEnv()).getProjection()).collect(Collectors.toList());	
		RoleDeclList projection = new RoleDeclList(null, roledecls);* /
		RoleDeclList projection = project(proj.peekSelf());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	@Override
	public RoleDeclList visitChildren(NodeVisitor nv) throws ScribbleException
	{
		HeaderParameterDeclList<RoleDecl> nds = super.visitChildren(nv);
		//List<RoleDecl> rds = NameDeclList.toRoleDeclList.apply(nds.decls);
		//List<RoleDecl> rds = nds.decls.stream().map(RoleDecl.toRoleDecl).collect(Collectors.toList());
		return new RoleDeclList(this.ct, nds.decls);
	}*/

	/*public int length()
	{
		return this.decls.size();
	}*/
	
	//public List<Name<RoleKind>> getRoles()
	public List<Role> getRoles()
	{
		//return this.decls.stream().map((decl) -> decl.getName()).collect(Collectors.toList());
		return this.decls.stream().map((decl) -> ((RoleDecl) decl).getDeclName()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
}
