package scribble2.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;

import scribble2.main.ScribbleException;
import scribble2.sesstype.name.Role;
import scribble2.visit.NodeVisitor;
import scribble2.visit.Projector;
import scribble2.visit.env.ProjectionEnv;

public class RoleDeclList extends NameDeclList<RoleDecl>
{
	public RoleDeclList(CommonTree ct, List<RoleDecl> rds)
	{
		super(ct, rds);
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

	@Override
	public RoleDeclList leaveProjection(Projector proj)  // Redundant now
	{
		/*List<RoleDecl> roledecls =
				this.decls.stream().map((rd) -> (RoleDecl) ((ProjectionEnv) rd.getEnv()).getProjection()).collect(Collectors.toList());	
		RoleDeclList projection = new RoleDeclList(null, roledecls);*/
		RoleDeclList projection = project(proj.peekSelf());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	public RoleDeclList project(Role self)
	{
		List<RoleDecl> roledecls =
				this.decls.stream().map((rd) -> rd.project(self)).collect(Collectors.toList());	
		return new RoleDeclList(null, roledecls);
	}
	
	@Override
	public RoleDeclList visitChildren(NodeVisitor nv) throws ScribbleException
	{
		NameDeclList<RoleDecl> nds = super.visitChildren(nv);
		//List<RoleDecl> rds = NameDeclList.toRoleDeclList.apply(nds.decls);
		//List<RoleDecl> rds = nds.decls.stream().map(RoleDecl.toRoleDecl).collect(Collectors.toList());
		return new RoleDeclList(this.ct, nds.decls);
	}

	/*public int length()
	{
		return this.decls.size();
	}*/
	
	public List<Role> getRoles()
	{
		return this.decls.stream().map((decl) -> decl.name.toName()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
}
