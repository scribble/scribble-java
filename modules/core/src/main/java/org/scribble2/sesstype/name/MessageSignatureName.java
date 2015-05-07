package org.scribble2.sesstype.name;

import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.ScopedMessage;


// The name of a declared (imported) message signature member
public class MessageSignatureName extends MemberName implements Message
{
	private static final long serialVersionUID = 1L;

	public MessageSignatureName(ModuleName modname, String membname)
	{
		super(KindEnum.TYPE, modname, membname);
	}
	
	public MessageSignatureName(String simplename)
	{
		super(KindEnum.TYPE, simplename);
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

	@Override
	public ScopedMessage toScopedMessage(Scope scope)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public ScopedMessageSignatureName toScopedMessage(Scope scope)
	{
		return new ScopedMessageSignatureName(scope, this.toString());
	}*/
}
