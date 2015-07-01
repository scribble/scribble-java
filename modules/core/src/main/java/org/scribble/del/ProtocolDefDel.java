package org.scribble.del;

import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.ProtocolDefInliner;


public abstract class ProtocolDefDel extends ScribDelBase
{
	protected ProtocolDef<? extends ProtocolKind> inlined = null;

	protected abstract ProtocolDefDel copy();
	
	public ProtocolDef<? extends ProtocolKind> getInlinedProtocolDef()
	{
		return this.inlined;
	}

	public ProtocolDefDel setInlinedProtocolDef(ProtocolDef<? extends ProtocolKind> inlined)
	{
		ProtocolDefDel copy = copy();
		copy.inlined = inlined;
		return copy;
	}

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}
}
