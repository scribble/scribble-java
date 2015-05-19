package org.scribble2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.Arg;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Role;
import org.scribble2.sesstype.name.Scope;


public class ArgList extends DoArgList<NonRoleArgument>
{
	public ArgList(List<NonRoleArgument> instans)
	{
		super(instans);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new ArgList(this.args);
	}

	@Override
	protected DoArgList<NonRoleArgument> reconstruct(List<NonRoleArgument> instans)
	{
		ModelDel del = del();
		ArgList ail = ModelFactoryImpl.FACTORY.ArgumentInstantiationList(instans);
		ail = (ArgList) ail.del(del);
		return ail;
	}

	@Override
	public ArgList project(Role self)
	{
		List<NonRoleArgument> instans =
				this.args.stream().map((ai) -> ai.project(self)).collect(Collectors.toList());	
		return ModelFactoryImpl.FACTORY.ArgumentInstantiationList(instans);
	}
	
	public boolean isEmpty()
	{
		return this.args.isEmpty();
	}
	
	public List<ArgNode> getArgumentNodes()
	{
		return this.args.stream().map((ai) -> ai.val).collect(Collectors.toList());
	}

	public List<Arg<? extends Kind>> getArguments(Scope scope)
	{
		//return this.instans.stream().map((ai) -> ai.arg.toArgument(scope)).collect(Collectors.toList());
		return this.args.stream().map((ai) -> ai.val.toArg()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		String s = "<" + this.args.get(0);
		for (NonRoleArgument a : this.args.subList(1, this.args.size()))
		{
			s += ", " + a;
		}
		return s + ">";
	}
}
