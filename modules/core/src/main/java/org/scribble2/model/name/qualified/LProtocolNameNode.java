package org.scribble2.model.name.qualified;

import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.ModuleName;



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
	//public ProtocolName<K> toName()
	public LProtocolName toName()
	{
		//String membname = getLastElement();
		//ProtocolName<K> membname = new ProtocolName<>(null, getLastElement());  // FIXME: global/local
		LProtocolName membname = new LProtocolName(getLastElement());
		if (!isPrefixed())
		{
			//return new ProtocolName(membname);
			return membname;
		}
		ModuleName modname = getModuleNamePrefix();
		//return new ProtocolName<>(null, modname, membname);  // FIXME
		return new LProtocolName(modname, membname);
	}
}
