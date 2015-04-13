package org.scribble2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.name.Role;


public class ArgumentInstantiationList extends InstantiationList<ArgumentInstantiation>
{
	public ArgumentInstantiationList(List<ArgumentInstantiation> as)
	{
		super(as);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new ArgumentInstantiationList(this.instans);
	}

	public ArgumentInstantiationList project(Role self)
	{
		List<ArgumentInstantiation> arginstans =
				this.instans.stream().map((ai) -> ai.project(self)).collect(Collectors.toList());	
		return new ArgumentInstantiationList(arginstans);
	}
	
	public boolean isEmpty()
	{
		return this.instans.isEmpty();
	}
	
	public List<Argument> getArguments()
	{
		return this.instans.stream().map((ai) -> ai.arg.toArgument()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		String s = "<" + this.instans.get(0);
		for (ArgumentInstantiation a : this.instans.subList(1, this.instans.size()))
		{
			s += ", " + a;
		}
		return s + ">";
	}
}
