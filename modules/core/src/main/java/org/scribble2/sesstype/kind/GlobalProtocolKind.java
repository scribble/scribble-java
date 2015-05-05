package org.scribble2.sesstype.kind;

public class GlobalProtocolKind extends ProtocolKind
{
	public static final GlobalProtocolKind KIND = new GlobalProtocolKind();
	
	private static final String text = "global";
	
	protected GlobalProtocolKind()
	{
		
	}
	
	@Override
	public String toString()
	{
		return GlobalProtocolKind.text;
	}
}
