package org.scribble.sesstype.kind;

public abstract class AbstractKind implements Kind
{
	public AbstractKind()
	{

	}
	
	@Override
	public abstract boolean equals(Object o);

	public abstract boolean canEqual(Object o);  // Not really needed due to singleton pattern

	@Override
	public String toString()
	{
		String s = this.getClass().toString();
		return s.substring("class org.sribble.sesstype.kind.".length() + 1, s.length());
	}
}
