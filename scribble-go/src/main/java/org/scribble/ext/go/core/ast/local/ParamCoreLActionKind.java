package org.scribble.ext.go.core.ast.local;

import org.scribble.ext.go.core.ast.ParamCoreActionKind;
import org.scribble.type.kind.Local;

public enum ParamCoreLActionKind implements ParamCoreActionKind<Local>
{
	SEND_ALL,
	RECEIVE_ALL,
	MULTICHOICES_RECEIVE_ALL;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case SEND_ALL:    return "!";
			case RECEIVE_ALL: return "?";
			case MULTICHOICES_RECEIVE_ALL: return "?*";
			default:          throw new RuntimeException("[param-core] Won't get here: " + this);
		}
	}
}
