package org.scribble2.sesstype.name;

import org.scribble2.sesstype.Message;



// The name of a declared (imported) message signature member
public class MessageSignatureName extends MemberName implements Message
{
	private static final long serialVersionUID = 1L;

	public MessageSignatureName(ModuleName modname, String membname)
	{
		super(Kind.TYPE, modname, membname);
	}
	
	public MessageSignatureName(String simplename)
	{
		super(Kind.TYPE, simplename);
	}

	@Override
	public MessageSignatureName getSimpleName()
	{
		return new MessageSignatureName(getLastElement());
	}
	
	@Override
	public boolean isParameter()
	{
		return false;
	}

	/*@Override
	public ScopedMessageSignatureName toScopedMessage(Scope scope)
	{
		return new ScopedMessageSignatureName(scope, this.toString());
	}*/
}
