package org.scribble.ast.name;

import java.util.Arrays;

import org.scribble.sesstype.kind.Kind;

//public abstract class CompoundNameNode<T extends Name<K>, K extends Kind> extends NameNode<T, K>
@Deprecated
public abstract class CompoundNameNode<K extends Kind> extends NameNode<K>
{
	// Kind parameter used for typing help, but NameNodes don't record kind as state (not part of the syntax) -- so kind doesn't affect e.g. equals (names nodes of different kinds are still only compared syntactically)
	
	//public final List<PrimitiveNameNode> names;
	protected final String[] elems;

	//public CompoundNameNodes(List<PrimitiveNameNode> names)
	public CompoundNameNode(String... elems)
	{
		//this.names = new LinkedList<>(names);
		this.elems = elems;
	}
	
	/*public CompoundNameNodes(String name)
	{
		// Factor out
		List<PrimitiveNameNode> pnns = new LinkedList<>();
		for (String n : Arrays.asList(name.split("\\.")))
		{
			pnns.add(new PrimitiveNameNode(null, n));
		}
		this.names = pnns;
	}*/
	
	public String[] getElements()
	{
		return this.elems;
	}

	public int getElementCount()
	{
		return this.elems.length;
	}
	
	public boolean isEmpty()
	{
		return this.elems.length == 0;
	}
	
	protected boolean isPrefixed()
	{
		return this.elems.length > 1;
	}
	
	//protected abstract CompoundNameNodes getPrefix();
	/*protected CompoundNameNodes getPrefix()
	{
		return new CompoundNameNodes(getPrefixElements());
	}*/
	
	protected String getLastElement()
	{
		return this.elems[this.elems.length - 1];
	}
	
	protected String[] getPrefixElements()
	{
		return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
	}

	/*@Override
	public Name toName()
	{
		return new CompoundName(Kind.AMBIGUOUS, toStringArray());
	}*/

	// FIXME:
	@Override
	public String toString()
	{
		return toName().toString();
		//return Arrays.toString(this.elems);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || this.getClass() != o.getClass())
		{
			return false;
		}
		//return this.elems.equals(((CompoundNameNode) o).elems);
		return this.elems.equals(CompoundNameNode.class.cast(o).elems);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
	
	protected String[] toStringArray()
	{
		String[] names = new String[this.elems.length];
		for (int i = 0; i < this.elems.length; i++)
		{
			names[i] = this.elems[i];
		}
		return names;
	}
	
	/*protected static String[] getIdentifiers(PrimitiveNameNode[] ns)
	{
		String[] ids = new String[ns.length];
		for (int i = 0; i < ns.length; i++)
		{
			ids[i] = ns[i].identifier;
		}
		return ids;
	}*/
}
