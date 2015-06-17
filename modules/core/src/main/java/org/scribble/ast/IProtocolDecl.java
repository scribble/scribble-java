package org.scribble.ast;

import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;

@Deprecated
public interface IProtocolDecl<K extends ProtocolKind> extends ScribNode
{
	ProtocolName getFullProtocolName(Module mod);
	
	ProtocolHeader getHeader();
	//ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> getDef();
	ProtocolDef<K> getDef();
	
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
