package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ModelDel;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Role;
import org.scribble.sesstype.name.Scope;


public class NonRoleArgList extends DoArgList<NonRoleArg>
{
	public NonRoleArgList(List<NonRoleArg> instans)
	{
		super(instans);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new NonRoleArgList(this.args);
	}

	@Override
	protected DoArgList<NonRoleArg> reconstruct(List<NonRoleArg> instans)
	{
		ModelDel del = del();
		//NonRoleArgList ail = ModelFactoryImpl.FACTORY.ArgList(instans);
		NonRoleArgList ail = new NonRoleArgList(instans);
		ail = (NonRoleArgList) ail.del(del);
		return ail;
	}

	@Override
	public NonRoleArgList project(Role self)
	{
		List<NonRoleArg> instans =
				this.args.stream().map((ai) -> ai.project(self)).collect(Collectors.toList());	
		return ModelFactoryImpl.FACTORY.NonRoleArgList(instans);
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
		for (NonRoleArg a : this.args.subList(1, this.args.size()))
		{
			s += ", " + a;
		}
		return s + ">";
	}
}
