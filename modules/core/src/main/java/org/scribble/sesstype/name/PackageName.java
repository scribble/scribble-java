package org.scribble.sesstype.name;

import org.scribble.sesstype.kind.PackageKind;

public class PackageName extends QualifiedName<PackageKind>
{
	private static final long serialVersionUID = 1L;
	
	public static final PackageName EMPTY_PACKAGENAME = new PackageName();

	public PackageName(String... elems)
	{
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

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PackageName))
		{
			return false;
		}
		PackageName n = (PackageName) o;
		return n.canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof PackageName;
	}

	@Override
	public int hashCode()
	{
		int hash = 2803;
		hash = 31 * super.hashCode();
		return hash;
	}
}
