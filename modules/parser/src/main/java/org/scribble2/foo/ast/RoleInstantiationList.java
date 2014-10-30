package org.scribble2.foo.ast;

import java.util.List;

import org.antlr.runtime.Token;

public class RoleInstantiationList extends InstantiationList<RoleInstantiation>
{
	//public final List<RoleInstantiation> ris;

	public RoleInstantiationList(Token t, List<RoleInstantiation> ris)
	{
		super(t, ris);
		//this.is = ris;
	}

	/*public RoleInstantiationList project(Role self)
	{
		List<RoleInstantiation> roleinstans =
				this.instans.stream().map((ri) -> ri.project(self)).collect(Collectors.toList());	
		return new RoleInstantiationList(null, roleinstans);
	}

	/*@Override
	public RoleInstantiationList checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		RoleInstantiationList ril = (RoleInstantiationList) super.checkWellFormedness(wfc);
		GlobalDo gd = (GlobalDo) wfc.peek().parent.node;
		Env env = wfc.getEnv();
		GlobalProtocolDecl gpd = env.getGlobalProtocolDecl(gd.getFullTargetProtocolName(env));
		if (gpd.rdl.length() != length())
		{
			throw new ScribbleException("Bad number of role arguments: " + ril);
		}
		Iterator<RoleDecl> rds = gpd.rdl.rds.iterator();
		Set<Role> seen = new HashSet<>();
		for (RoleInstantiation ri : ril.is)
		{
			Role arg = ri.arg.toName();
			if (seen.contains(arg))
			{
				throw new ScribbleException("Duplicate role argument: " + arg);  // Only needed to support projection atm -- for repeat role argments, need to substitute the global first (reducing the number of role args appropriately) and then project
			}
			if (!env.roles.isRoleDeclared(arg))
			{
				throw new ScribbleException("Bad role argument: " + arg);
			}
			seen.add(arg);
			RoleDecl rd = rds.next();
			if (ri.hasTargetParameter())
			{
				Role param = ri.param.toName();
				if (!param.equals(rd.name.toName()))
				{
					throw new ScribbleException("Bad role parameter: " + ri);
				}
			}
		}
		return ril;
	}
	
	@Override
	public RoleInstantiationList visitChildren(NodeVisitor nv) throws ScribbleException
	{
		List<RoleInstantiation> ris = nv.visitAll(this.is);
		return new RoleInstantiationList(this.ct, ris);
	}* /
	
	// The role arguments
	public List<Role> getRoles()
	{
		return this.instans.stream().map((ri) -> ri.arg.toName()).collect(Collectors.toList());
	}*/

	@Override
	public String toString()
	{
		String s = "(" + this.instans.get(0);
		for (RoleInstantiation ri : this.instans.subList(1, this.instans.size()))
		{
			s += ", " + ri;
		}
		return s + ")";
	}
}
