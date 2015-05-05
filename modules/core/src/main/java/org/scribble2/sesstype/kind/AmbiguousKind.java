package org.scribble2.sesstype.kind;

public class AmbiguousKind extends Kind
{
	public static final AmbiguousKind KIND = new AmbiguousKind();
	
	private static final String text = "ambiguous";
	
	protected AmbiguousKind()
	{
		
	}
	
	@Override
	public String toString()
	{
		return AmbiguousKind.text;
	}
}
