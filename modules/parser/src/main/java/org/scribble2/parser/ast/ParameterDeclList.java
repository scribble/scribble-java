package scribble2.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;

import scribble2.main.ScribbleException;
import scribble2.sesstype.name.Parameter;
import scribble2.sesstype.name.Role;
import scribble2.visit.NodeVisitor;
import scribble2.visit.Projector;
import scribble2.visit.env.ProjectionEnv;

public class ParameterDeclList extends NameDeclList<ParameterDecl>
{
	public ParameterDeclList(CommonTree ct, List<ParameterDecl> pds)
	{
		super(ct, pds);
	}

	// Not doing anything except cloning
	@Override
	public ParameterDeclList leaveProjection(Projector proj)  // Redundant now
	{
		/*List<ParameterDecl> paramdecls =
				this.decls.stream().map((pd) -> (ParameterDecl) ((ProjectionEnv) pd.getEnv()).getProjection()).collect(Collectors.toList());	
		ParameterDeclList projection = new ParameterDeclList(null, paramdecls);*/
		ParameterDeclList projection = project(proj.peekSelf());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	public ParameterDeclList project(Role self)
	{
		List<ParameterDecl> paramdecls =
				this.decls.stream().map((pd) -> pd.project(self)).collect(Collectors.toList());	
		return new ParameterDeclList(null, paramdecls);
	}
	
	/*@Override 
	public ParameterDeclList checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		Set<Name> ns = new HashSet<>();
		Set<Name> dns = new HashSet<>();
		for (ParameterDecl pd : this.pds)
		{
			Name n = pd.namenode.toName();
			Name dn = pd.getDeclarationName();
			if (ns.contains(n))  // FIXME: should also be distinct from payload type names (for arguments)
			{
				throw new ScribbleException("Duplicate parameter declaration: " + n);
			}
			if (dns.contains(dn))
			{
				throw new ScribbleException("Duplicate parameter delcaration: " + dn);
			}
			ns.add(n);
			dns.add(dn);
		}
		return (ParameterDeclList) super.checkWellFormedness(wfc);
	}
	
	@Override 
	public ParameterDeclList leave(EnvVisitor nv) throws ScribbleException
	{
		ParameterDeclList pdl = (ParameterDeclList) super.leave(nv);
		Env env = nv.getEnv();
		for (ParameterDecl pd : this.pds)
		{
			env.params.addParameter(pd.getDeclarationName(), pd.kind);
		}
		return pdl;
	}*/
	
	@Override
	public ParameterDeclList visitChildren(NodeVisitor nv) throws ScribbleException
	{
		NameDeclList<ParameterDecl> nds = super.visitChildren(nv);
		//List<ParameterDecl> pds = NameDeclList.toParameterDeclList.apply(nds.decls);
		//List<ParameterDecl> pds = nds.decls.stream().map(ParameterDecl.toParameterDecl).collect(Collectors.toList());
		return new ParameterDeclList(this.ct, nds.decls);
	}
	
	public List<Parameter> getParameters()
	{
		return this.decls.stream().map((decl) -> decl.name.toName()).collect(Collectors.toList());
	}
	
	/*public List<Argument> asArguments()
	{
		//return this.decls.stream().map((decl) -> decl.name.toName()).collect(Collectors.toList());
		return getParameters().stream().map((p) -> (Argument) p).collect(Collectors.toList());
	}*/

	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		return "<" + super.toString() + ">";
	}
}
