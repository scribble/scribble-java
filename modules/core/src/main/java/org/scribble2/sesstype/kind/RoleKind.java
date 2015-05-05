package org.scribble2.sesstype.kind;

public class RoleKind extends Kind
{
	public static final RoleKind KIND = new RoleKind();
	
	private static final String text = "module";
	
	protected RoleKind()
	{
		
	}
	
	@Override
	public String toString()
	{
		return RoleKind.text;
	}
}
