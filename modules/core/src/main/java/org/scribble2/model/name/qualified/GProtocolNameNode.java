package org.scribble2.model.name.qualified;

import org.scribble2.sesstype.kind.Global;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.sesstype.name.ModuleName;



public class GProtocolNameNode extends ProtocolNameNode<Global>
{
	public GProtocolNameNode(String... ns)
	{
		super(ns);
	}

	@Override
	protected GProtocolNameNode copy()
	{
		return new GProtocolNameNode(this.elems);
	}
	
	@Override
	//public ProtocolName<K> toName()
	public GProtocolName toName()
	{
		//String membname = getLastElement();
		//ProtocolName<K> membname = new ProtocolName<>(null, getLastElement());  // FIXME: global/local
		GProtocolName membname = new GProtocolName(getLastElement());
		if (!isPrefixed())
		{
			//return new ProtocolName(membname);
			return membname;
		}
		ModuleName modname = getModuleNamePrefix();
		//return new ProtocolName<>(null, modname, membname);  // FIXME
		return new GProtocolName(modname, membname);
	}
}
