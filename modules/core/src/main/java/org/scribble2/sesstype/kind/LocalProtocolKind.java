package org.scribble2.sesstype.kind;

public class LocalProtocolKind extends ProtocolKind
{
	public static final LocalProtocolKind KIND = new LocalProtocolKind();
	
	private static final String text = "local";
	
	protected LocalProtocolKind()
	{
		
	}
	
	@Override
	public String toString()
	{
		return LocalProtocolKind.text;
	}
}
