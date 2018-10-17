package org.scribble.ext.go.core.ast.global;

import org.scribble.ext.go.core.ast.RPCoreActionKind;
import org.scribble.type.kind.Global;

public enum RPCoreGActionKind implements RPCoreActionKind<Global>
{
	CROSS_TRANSFER,
	DOT_TRANSFER;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case CROSS_TRANSFER:  return "->";
			case DOT_TRANSFER:    return "=>";
			default:              throw new RuntimeException("[param-core] Won't get here: " + this);
		}
	}
}
