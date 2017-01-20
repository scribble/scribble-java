package org.scribble.ext.f17.ast;

public class AnnotString implements ScribAnnot
{
	public final String val;  // Excludes the ""
	
	public AnnotString(String val)
	{
		this.val = val;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AnnotString))
		{
			return false;
		}
		return this.val.equals(((AnnotString) o).val);
	}

	@Override
	public int hashCode()
	{
		int hash = 3331;
		hash = 31 * this.val.hashCode();
		return hash;
	}
	
	@Override
	public final String toString()
	{
		return "@\"" + this.val + "\"";
	}
}
