package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;

// Cf. NonRoleParamDeclList
public class NonRoleArgList extends DoArgList<NonRoleArg>
{
	public NonRoleArgList(List<NonRoleArg> args)
	{
		super(args);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new NonRoleArgList(getDoArgs());
	}
	
	@Override
	public NonRoleArgList clone()
	{
		List<NonRoleArg> args = ScribUtil.cloneList(getDoArgs());
		return AstFactoryImpl.FACTORY.NonRoleArgList(args);
	}

	@Override
	public DoArgList<NonRoleArg> reconstruct(List<NonRoleArg> instans)
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
				getDoArgs().stream().map((ai) -> ai.project(self)).collect(Collectors.toList());	
		return AstFactoryImpl.FACTORY.NonRoleArgList(instans);
	}
	
	public boolean isEmpty()
	{
		return getDoArgs().isEmpty();
	}
	
	public List<NonRoleArgNode> getArgumentNodes()
	{
		return getDoArgs().stream().map((ai) -> ai.val).collect(Collectors.toList());
	}

	public List<Arg<? extends NonRoleArgKind>> getArguments()
	{
		return getDoArgs().stream().map((ai) -> ai.val.toArg()).collect(Collectors.toList());
	}

	@Override
	public String toString()
	{
		return (getDoArgs().isEmpty())
				? ""
				: "<" + super.toString() + ">";
	}
}
