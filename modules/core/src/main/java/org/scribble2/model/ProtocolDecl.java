package org.scribble2.model;

import org.scribble2.sesstype.name.ProtocolName;

public interface ProtocolDecl extends ModelNode
{
	ProtocolName getFullProtocolName(Module mod);
	
	ProtocolHeader getHeader();
	ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> getDef();
	
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
