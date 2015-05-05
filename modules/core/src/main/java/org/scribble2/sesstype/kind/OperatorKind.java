package org.scribble2.sesstype.kind;

public class OperatorKind extends Kind
{
	public static final OperatorKind KIND = new OperatorKind();
	
	private static final String text = "operator";
	
	protected OperatorKind()
	{
		
	}
	
	@Override
	public String toString()
	{
		return OperatorKind.text;
	}
}
