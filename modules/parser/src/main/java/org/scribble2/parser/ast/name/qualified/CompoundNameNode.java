package org.scribble2.parser.ast.name.qualified;

import java.util.Arrays;

import org.antlr.runtime.Token;
import org.scribble2.parser.ast.ScribbleASTBase;

public abstract class CompoundNameNode extends ScribbleASTBase //implements Named
{
	//public final List<PrimitiveNameNode> names;
	protected final String[] elems;

	//public CompoundNameNodes(List<PrimitiveNameNode> names)
	public CompoundNameNode(Token t, String... elems)
	{
		super(t);
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
		//return new CompoundName(Kind.AMBIGUOUS, toStringArray());
		throw new RuntimeException("Shouldn't get in here: " + this.elems);
	}*/

	@Override
	public String toString()
	{
		//return toName().toString();
		return null;
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
		return this.elems.equals(((CompoundNameNode) o).elems);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
	
	/*protected String[] toStringArray()
	{
		String[] names = new String[this.elems.length];
		for (int i = 0; i < this.elems.length; i++)
		{
			names[i] = this.elems[i];
		}
		return names;
	}
	
	protected static String[] getIdentifiers(PrimitiveNameNode[] ns)
	{
		String[] ids = new String[ns.length];
		for (int i = 0; i < ns.length; i++)
		{
			ids[i] = ns[i].identifier;
		}
		return ids;
	}*/
}
