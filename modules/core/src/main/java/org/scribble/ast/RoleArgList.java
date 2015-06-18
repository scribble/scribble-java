package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.sesstype.name.Role;

public class RoleArgList extends DoArgList<RoleArg>
{
	public RoleArgList(List<RoleArg> instans)
	{
		super(instans);
	}

	@Override
	protected RoleArgList copy()
	{
		return new RoleArgList(this.args);
	}

	@Override
	protected RoleArgList reconstruct(List<RoleArg> instans)
	{
		ScribDel del = del();
		RoleArgList rl = new RoleArgList(instans);
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
