package org.scribble2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.name.Role;

public class RoleArgumentList extends DoArgumentList<RoleArgument>
{
	//public final List<RoleInstantiation> ris;

	public RoleArgumentList(List<RoleArgument> instans)
	{
		super(instans);
		
		//this.is = ris;
	}

	@Override
	protected RoleArgumentList copy()
	{
		return new RoleArgumentList(this.instans);
	}

	@Override
	protected RoleArgumentList reconstruct(List<RoleArgument> instans)
	{
		ModelDel del = del();
		RoleArgumentList rl = new RoleArgumentList(instans);
		rl = (RoleArgumentList) rl.del(del);
		return rl;
	}

	// FIXME: move to delegate?
	@Override
	public RoleArgumentList project(Role self)
	{
		List<RoleArgument> instans =
				this.instans.stream().map((ri) -> ri.project(self)).collect(Collectors.toList());	
		return ModelFactoryImpl.FACTORY.RoleInstantiationList(instans);
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
	}*/
	
	// The role arguments
	public List<Role> getRoles()
	{
		return this.instans.stream().map((ri) -> ri.arg.toName()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		String s = "(" + this.instans.get(0);
		for (RoleArgument ri : this.instans.subList(1, this.instans.size()))
		{
			s += ", " + ri;
		}
		return s + ")";
	}
}
