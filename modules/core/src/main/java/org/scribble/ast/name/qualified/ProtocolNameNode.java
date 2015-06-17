package org.scribble.ast.name.qualified;

import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;



//public class ProtocolNameNode<K extends ProtocolKind> extends MemberNameNode<ProtocolName<K>, K>
//public class ProtocolNameNode extends MemberNameNode<ProtocolName, ProtocolKind>
public abstract class ProtocolNameNode<K extends ProtocolKind> extends MemberNameNode<K>
{
	//public ProtocolNameNodes(PrimitiveNameNode... ns)
	public ProtocolNameNode(String... ns)
	{
		super(ns);
	}

	/*@Override
	//protected ProtocolNameNode<K> copy()
	protected ProtocolNameNode<K> copy()
	{
		//return new ProtocolNameNode<>(this.elems);
		return new ProtocolNameNode<>(this.elems);
	}*/
	
	@Override
	/*//public ProtocolName<K> toName()
	public ProtocolName<K> toName()
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
	}*/
	public abstract ProtocolName<K> toName();
}
