package org.scribble2.model;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Role;
import org.scribble2.sesstype.name.Scope;


public class ArgumentInstantiationList extends InstantiationList<ArgumentInstantiation>
{
	public ArgumentInstantiationList(List<ArgumentInstantiation> instans)
	{
		super(instans);
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new ArgumentInstantiationList(this.instans);
	}

	@Override
	protected InstantiationList<ArgumentInstantiation> reconstruct(List<ArgumentInstantiation> instans)
	{
		ModelDel del = del();
		ArgumentInstantiationList ail = ModelFactoryImpl.FACTORY.ArgumentInstantiationList(instans);
		ail = (ArgumentInstantiationList) ail.del(del);
		return ail;
	}

	@Override
	public ArgumentInstantiationList project(Role self)
	{
		List<ArgumentInstantiation> instans =
				this.instans.stream().map((ai) -> ai.project(self)).collect(Collectors.toList());	
		return ModelFactoryImpl.FACTORY.ArgumentInstantiationList(instans);
	}
	
	public boolean isEmpty()
	{
		return this.instans.isEmpty();
	}
	
	public List<ArgumentNode> getArgumentNodes()
	{
		return this.instans.stream().map((ai) -> ai.arg).collect(Collectors.toList());
	}

	public List<Argument<? extends Kind>> getArguments(Scope scope)
	{
		//return this.instans.stream().map((ai) -> ai.arg.toArgument(scope)).collect(Collectors.toList());
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
