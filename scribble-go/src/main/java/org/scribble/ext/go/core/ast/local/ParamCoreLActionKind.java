package org.scribble.ext.go.core.ast.local;

public enum ParamCoreLActionKind
{
	SEND,
	RECEIVE;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case SEND:    return "!";
			case RECEIVE: return "?";
			default:      throw new RuntimeException("Won't get here: " + this);
		}
	}
}
