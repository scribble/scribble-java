package org.scribble2.model;

import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.ProtocolName;

public interface ProtocolDecl<K extends Kind> extends ModelNode
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
