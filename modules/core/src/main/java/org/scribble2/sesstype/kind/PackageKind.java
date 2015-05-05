package org.scribble2.sesstype.kind;

public class PackageKind extends Kind
{
	public static final PackageKind KIND = new PackageKind();

	private static final String text = "operator";
	
	protected PackageKind()
	{
		
	}
	
	@Override
	public String toString()
	{
		return PackageKind.text;
	}
}
