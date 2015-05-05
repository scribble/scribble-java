package org.scribble2.sesstype.name;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import org.scribble2.sesstype.kind.Kind;


public class KindedName<K extends Kind> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public final K kind;
	
	private String[] elems;  // non-final, for serialization

	public KindedName(K kind, String... elems)
	{
		this.kind = kind;
		this.elems = elems;
	}

	/*public K getKind()
	{
		return this.kind;
	}*/
	
	public boolean isEmpty()
	{
		//return this.elems.length == 1 && this.elems[0].equals("");
		return this.elems.length == 0;
	}

	public boolean isPrefixed()
	{
		return this.elems.length > 1;
	}
	
	// Not SimpleName so that e.g. ModuleName can return a simple ModuleName
	//public SimpleName getSimpleName()
	//protected Name getSimpleName()
	public String getLastElement()
	{
		//return new SimpleName(this.kind, this.elems[this.elems.length - 1]);
		//return new CompoundName(this.elems[this.elems.length - 1]);
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
		//return new CompoundName(Arrays.copyOfRange(this.elems, 0, this.elems.length - 1));
		return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
	}

	@Override
	public int hashCode()
	{
		int hash = 2749;
		//hash = 31 * hash + this.elems.hashCode();
		hash = 31 * hash + this.kind.hashCode();
		hash = 31 * hash + Arrays.hashCode(this.elems);
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || !this.getClass().equals(o.getClass()))
		//if (!(o instanceof KindedName))
		{
			return false;
		}
		//return this.elems.equals(((CompoundName) o).elems);
		return this.kind.equals(KindedName.class.cast(o).kind) && Arrays.equals(this.elems, KindedName.class.cast(o).elems);
	}
	
	@Override
	public String toString()
	{
		if (isEmpty())
		{
			return "";
		}
		//String name = this.elems.get(0);
		String name = this.elems[0];
		//for (String elem : this.elems.subList(1, this.elems.size()))
		//for (String elem : this.elems.subList(1, this.elems.size()))
		for (int i = 1; i < this.elems.length; i++)
		{
			//name += "." + elem;
			name += "." + this.elems[i];
		}
		return name;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeObject(this.elems);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException
	{
		this.elems = (String[]) in.readObject();
	}

	//private void readObjectNoData() throws ObjectStreamException;
}
