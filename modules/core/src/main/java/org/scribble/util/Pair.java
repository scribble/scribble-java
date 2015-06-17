package org.scribble.util;

public class Pair<T1, T2>
{
	public final T1 left;
	public final T2 right;

	public Pair(T1 t1, T2 t2)
	{
		this.left = t1;
		this.right = t2;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 11;
		hash = 31 * hash + this.left.hashCode();
		hash = 31 * hash + this.right.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Pair))
		{
			return false;
		}
		@SuppressWarnings("rawtypes")
		Pair p = (Pair) o;  // Could store T1.class and T2.class as fields, but probably better to do "structurally" as here
		return this.left.equals(p.left) && this.right.equals(p.right);
	}
}
