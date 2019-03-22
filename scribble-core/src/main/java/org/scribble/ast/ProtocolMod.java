package org.scribble.ast;

// CHECKME: move to Header?
public enum ProtocolMod
{
	AUX, 
	EXPLICIT;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case AUX:      return "aux";
			case EXPLICIT: return "explicit";
			default:       throw new RuntimeException("Unknown modifier: " + this);
		}
	}
}
