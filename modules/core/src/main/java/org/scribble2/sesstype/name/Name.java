package org.scribble2.sesstype.name;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import org.scribble2.sesstype.kind.Kind;


public abstract class Name<K extends Kind> implements Serializable//, IName
{
	private static final long serialVersionUID = 1L;
	
	protected final KindEnum kindenum = null;
	public final K kind;
	
	//public static final CompoundName EMPTY_NAME = new CompoundName();

	//private final List<String> elems;
	//public final String[] elems;
	private String[] elems;  // non-final, for serialization

	//public CompoundName(List<String> elems)
	//public CompoundName(KindEnum kind, String... elems)
	protected Name(K kind, String... elems)
	{
		//this.kindenum = kind;
		this.kind = kind;
		this.elems = elems;
	}

	//@Override
	public KindEnum getKindEnum()
	{
		return this.kindenum;
	}

	/*@Override
	public K getKind()
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
		//if (o == null || this.getClass() != o.getClass())
		if (!(o instanceof Name))
		{
			return false;
		}
		//return this.elems.equals(((CompoundName) o).elems);
		return this.kind.equals(Name.class.cast(o).kind) && Arrays.equals(this.elems, (Name.class.cast(o).elems));
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

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		this.elems = (String[]) in.readObject();
	}

	//protected static String[] compileElements(Name cn, String n)
	protected static String[] compileElements(String[] cn, String n)
	{
		if (cn.length == 0)
		{
			return new String[] { n };
		}
		//String[] prefix = cn.getElements();
		//String[] elems = Arrays.copyOf(prefix, prefix.length + 1);
		String[] elems = Arrays.copyOf(cn, cn.length + 1);
		elems[elems.length - 1] = n;
		return elems;
	}

	//private void readObjectNoData() throws ObjectStreamException;
}
