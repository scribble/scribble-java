package org.scribble.sesstype.name;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import org.scribble.sesstype.kind.Kind;


public abstract class Name<K extends Kind> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public K kind;  // non-final for serialization

	private String[] elems;

	protected Name(K kind, String... elems)
	{
		this.kind = kind;
		this.elems = elems;
	}

	public boolean isEmpty()
	{
		return this.elems.length == 0;
	}

	public boolean isPrefixed()
	{
		return this.elems.length > 1;
	}
	
	// Not SimpleName so that e.g. ModuleName can return a simple ModuleName
	public String getLastElement()
	{
		return this.elems[this.elems.length - 1];
	}

	public int getElementCount()
	{
		return this.elems.length;
	}
	
	public String[] getElements()
	{
		return this.elems;
	}

	public String[] getPrefixElements()
	{
		return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Name))
		{
			return false;
		}
		Name<?> n = (Name<?>) o;
		return n.canEqual(this) && this.kind.equals(n.kind) && Arrays.equals(this.elems, (n.elems));
	}
	
	public abstract boolean canEqual(Object o);

	@Override
	public int hashCode()
	{
		int hash = 2749;
		hash = 31 * hash + this.kind.hashCode();
		hash = 31 * hash + Arrays.hashCode(this.elems);
		return hash;
	}
	
	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		String name = this.elems[0];
		for (int i = 1; i < this.elems.length; i++)
		{
			name += "." + this.elems[i];
		}
		return name;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeObject(this.kind);
		out.writeObject(this.elems);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		this.kind = (K) in.readObject();
		this.elems = (String[]) in.readObject();
	}

	protected static String[] compileElements(String[] cn, String n)
	{
		if (cn.length == 0)
		{
			return new String[] { n };
		}
		String[] elems = Arrays.copyOf(cn, cn.length + 1);
		elems[elems.length - 1] = n;
		return elems;
	}
}
