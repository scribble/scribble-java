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
		return new RoleArgList(getDoArgs());
	}
	
	@Override
	public RoleArgList clone()
	{
		List<RoleArg> roles = ScribUtil.cloneList(getDoArgs());
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

	// Move to delegate?
	@Override
	public RoleArgList project(Role self)
	{
		List<RoleArg> instans =
				getDoArgs().stream().map((ri) -> ri.project(self)).collect(Collectors.toList());	
		return AstFactoryImpl.FACTORY.RoleArgList(instans);
	}

	// The role arguments
	public List<Role> getRoles()
	{
		return getDoArgs().stream().map((ri) -> ri.val.toName()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return "(" + super.toString() + ")";
	}
}
