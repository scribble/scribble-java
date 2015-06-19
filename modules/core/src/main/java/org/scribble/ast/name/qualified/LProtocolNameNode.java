package org.scribble.ast.name.qualified;

import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;



public class LProtocolNameNode extends ProtocolNameNode<Local>
{
	public LProtocolNameNode(String... ns)
	{
		super(ns);
	}

	@Override
	protected LProtocolNameNode copy()
	{
		return new LProtocolNameNode(this.elems);
	}
	
	@Override
	public LProtocolName toName()
	{
		LProtocolName membname = new LProtocolName(getLastElement());
		return isPrefixed()
				? new LProtocolName(getModuleNamePrefix(), membname)
				: membname;
	}
		
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LProtocolNameNode))
		{
			return false;
		}
		return ((LProtocolNameNode) o).canEqual(this) && super.equals(o);
	}
	
	public boolean canEqual(Object o)
	{
		return o instanceof LProtocolNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 421;
		hash = 31 * hash + this.elems.hashCode();
		return hash;
	}
}