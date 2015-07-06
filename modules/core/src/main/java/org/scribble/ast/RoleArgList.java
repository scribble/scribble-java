package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

public class RoleArgList extends DoArgList<RoleArg>
{
	public RoleArgList(List<RoleArg> roles)
	{
		super(roles);
	}

	@Override
	protected RoleArgList copy()
	{
		return new RoleArgList(this.args);
	}
	
	@Override
	public RoleArgList clone()
	{
		List<RoleArg> roles = ScribUtil.cloneList(this.args);
		return AstFactoryImpl.FACTORY.RoleArgList(roles);
	}

	@Override
	public RoleArgList reconstruct(List<RoleArg> roles)
	{
		ScribDel del = del();
		RoleArgList rl = new RoleArgList(roles);
		rl = (RoleArgList) rl.del(del);
		return rl;
	}

	// FIXME: move to delegate?
	@Override
	public RoleArgList project(Role self)
	{
		List<RoleArg> instans =
				this.args.stream().map((ri) -> ri.project(self)).collect(Collectors.toList());	
		return AstFactoryImpl.FACTORY.RoleArgList(instans);
	}

	// The role arguments
	public List<Role> getRoles()
	{
		return this.args.stream().map((ri) -> ri.val.toName()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		String s = "(" + this.args.get(0);
		for (RoleArg ri : this.args.subList(1, this.args.size()))
		{
			s += ", " + ri;
		}
		return s + ")";
	}
}
