package org.scribble2.sesstype.kind;

public class RecursionVarKind extends Kind
{
	public static final RecursionVarKind KIND = new RecursionVarKind();
	
	private static final String text = "recvar";
	
	protected RecursionVarKind()
	{
		
	}
	
	@Override
	public String toString()
	{
		return RecursionVarKind.text;
	}
}
