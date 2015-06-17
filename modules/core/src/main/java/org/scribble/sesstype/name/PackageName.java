package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.PackageKind;

public class PackageName extends QualifiedName<PackageKind>
{
	private static final long serialVersionUID = 1L;
	
	public static final PackageName EMPTY_PACKAGENAME = new PackageName();

	//public PackageName(PackageName parent, String child)

	public PackageName(String... elems)
	{
		//super(KindEnum.PACKAGE, elems);
		super(PackageKind.KIND, elems);
	}

	@Override
	public PackageName getPrefix()
	{
		return new PackageName(getPrefixElements());
	}

	@Override
	public PackageName getSimpleName()
	{
		return new PackageName(getLastElement());
	}
}
