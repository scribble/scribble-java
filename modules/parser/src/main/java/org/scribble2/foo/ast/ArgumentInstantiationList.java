package org.scribble2.foo.ast;

import java.util.List;

import org.antlr.runtime.Token;

public class ArgumentInstantiationList extends InstantiationList<ArgumentInstantiation>
{
	public ArgumentInstantiationList(Token t, List<ArgumentInstantiation> as)
	{
		super(t, as);
	}
	
	public boolean isEmpty()
	{
		return this.instans.isEmpty();
	}
	
	/*public List<Argument> getArguments()
	{
		return this.instans.stream().map((ai) -> ai.arg.toArgument()).collect(Collectors.toList());
	}*/

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
