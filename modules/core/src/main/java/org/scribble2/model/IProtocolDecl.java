package org.scribble2.model;

import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.ProtocolName;

@Deprecated
public interface IProtocolDecl<K extends ProtocolKind> extends ModelNode
{
	ProtocolName getFullProtocolName(Module mod);
	
	ProtocolHeader getHeader();
	//ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> getDef();
	ProtocolDefinition<K> getDef();
	
	boolean isGlobal();
	boolean isLocal();
	/*public boolean isGlobal()
	{
		return false;
	}

	public boolean isLocal()
	{
		return false;
	}*/
}
