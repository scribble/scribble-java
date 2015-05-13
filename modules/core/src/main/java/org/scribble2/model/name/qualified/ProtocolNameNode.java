package org.scribble2.model.name.qualified;

import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.ProtocolName;



//public class ProtocolNameNode<K extends ProtocolKind> extends MemberNameNode<ProtocolName<K>, K>
public class ProtocolNameNode extends MemberNameNode<ProtocolName, ProtocolKind>
{
	//public ProtocolNameNodes(PrimitiveNameNode... ns)
	public ProtocolNameNode(String... ns)
	{
		super(ns);
	}

	@Override
	//protected ProtocolNameNode<K> copy()
	protected ProtocolNameNode copy()
	{
		//return new ProtocolNameNode<>(this.elems);
		return new ProtocolNameNode(this.elems);
	}
	
	@Override
	//public ProtocolName<K> toName()
	public ProtocolName toName()
	{
		//String membname = getLastElement();
		//ProtocolName<K> membname = new ProtocolName<>(null, getLastElement());  // FIXME: global/local
		ProtocolName membname = new ProtocolName(getLastElement());
		if (!isPrefixed())
		{
			//return new ProtocolName(membname);
			return membname;
		}
		ModuleName modname = getModuleNamePrefix();
		//return new ProtocolName<>(null, modname, membname);  // FIXME
		return new ProtocolName(modname, membname);
	}
}
