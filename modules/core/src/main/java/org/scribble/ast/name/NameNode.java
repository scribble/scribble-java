package org.scribble.ast.name;

import java.util.Arrays;

import org.scribble.ast.ScribNodeBase;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Named;

// Kind parameter used for typing help, but NameNodes don't record kind as state (not part of the syntax) -- so kind doesn't affect e.g. equals (i.e. names nodes of different kinds are still only compared syntactically)
public abstract class NameNode<K extends Kind> extends ScribNodeBase implements Named<K>
{
	protected final String[] elems;

	public NameNode(String... elems)
	{
		this.elems = elems;
	}
	
	@Override
	public abstract NameNode<K> clone();
	
	public String[] getElements()
	{
		return Arrays.copyOf(this.elems, this.elems.length);
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
	
	protected String[] getPrefixElements()
	{
		return Arrays.copyOfRange(this.elems, 0, this.elems.length - 1);
	}
	
	protected String getLastElement()
	{
		return this.elems[this.elems.length - 1];
	}
	
	@Override
	public boolean equals(Object o)  // FIXME: should NameNodes ever be used in an equality checking context? (cf. other AST nodes) -- this work should be done using sesstype.Name instead?
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof NameNode<?>))
		{
			return false;
		}
		NameNode<?> nn = (NameNode<?>) o;
		return nn.canEqual(this) && Arrays.equals(this.elems, nn.elems);
	}
	
	public abstract boolean canEqual(Object o);
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}

	@Override
	public String toString()
	{
		return toName().toString();
	}
}
