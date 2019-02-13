package org.scribble.lang.local;

import org.scribble.lang.MessageTransfer;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.type.Message;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public class LSend extends MessageTransfer<Local>
		implements LType
{

	public LSend(org.scribble.ast.MessageTransfer<Local> source,
			Message msg, Role dst)
	{
		super(source, Role.SELF, msg, dst);
	}

	// FIXME: unnecessary src 
	@Override
	public LSend reconstruct(
			org.scribble.ast.MessageTransfer<Local> source, Role src, Message msg,
			Role dst)
	{
		return new LSend(source, msg, dst);
	}

	@Override
	public LSend substitute(Substitutions<Role> subs)
	{
		return (LSend) super.substitute(subs);
	}

	@Override
	public LSend getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		return (LSend) super.getInlined(i);
	}

	@Override
	public LSend unfoldAllOnce(STypeUnfolder<Local> u)
	{
		return this;
	}
	
	@Override
	public String toString()
	{
		return this.msg + " to " + this.dst + ";";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1481;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LSend))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LSend;
	}

}
