package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.name.Role;
import org.scribble.sesstype.name.Scope;


public class NonRoleArgList extends DoArgList<NonRoleArg>
{
	public NonRoleArgList(List<NonRoleArg> instans)
	{
		super(instans);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new NonRoleArgList(this.args);
	}

	@Override
	protected DoArgList<NonRoleArg> reconstruct(List<NonRoleArg> instans)
	{
		ScribDel del = del();
		NonRoleArgList ail = new NonRoleArgList(instans);
		ail = (NonRoleArgList) ail.del(del);
		return ail;
	}

	@Override
	public NonRoleArgList project(Role self)
	{
		List<NonRoleArg> instans =
				this.args.stream().map((ai) -> ai.project(self)).collect(Collectors.toList());	
		return AstFactoryImpl.FACTORY.NonRoleArgList(instans);
	}
	
	public boolean isEmpty()
	{
		return this.args.isEmpty();
	}
	
	public List<NonRoleArgNode> getArgumentNodes()
	{
		return this.args.stream().map((ai) -> ai.val).collect(Collectors.toList());
	}

	public List<Arg<? extends NonRoleArgKind>> getArguments(Scope scope)
	{
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
