package org.scribble2.sesstype.kind;

public class ModuleKind extends Kind
{
	public static final ModuleKind KIND = new ModuleKind();
	
	private static final String text = "module";
	
	protected ModuleKind()
	{
		
	}
	
	@Override
	public String toString()
	{
		return ModuleKind.text;
	}
}
