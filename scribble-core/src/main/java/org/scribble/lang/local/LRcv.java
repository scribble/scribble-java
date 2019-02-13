package org.scribble.lang.local;

import org.scribble.lang.MessageTransfer;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.type.Message;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public class LRcv extends MessageTransfer<Local>
		implements LType
{

	public LRcv(org.scribble.ast.MessageTransfer<Local> source,
			Role src, Message msg)
	{
		super(source, src, msg, Role.SELF);
	}

	// FIXME: unnecessary dst 
	@Override
	public LRcv reconstruct(
			org.scribble.ast.MessageTransfer<Local> source, Role src, Message msg,
			Role dst)
	{
		return new LRcv(source, src, msg);
	}

	@Override
	public LRcv substitute(Substitutions<Role> subs)
	{
		return (LRcv) super.substitute(subs);
	}

	@Override
	public LRcv getInlined(STypeInliner i)//, Deque<SubprotoSig> stack)
	{
		return (LRcv) super.getInlined(i);
	}

	@Override
	public LRcv unfoldAllOnce(STypeUnfolder<Local> u)
	{
		return this;
	}

	@Override
	public String toString()
	{
		return this.msg + " from " + this.src + ";";
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
		if (!(o instanceof LRcv))
		{
			return false;
		}
		return super.equals(o);  // Does canEquals
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LRcv;
	}

}
