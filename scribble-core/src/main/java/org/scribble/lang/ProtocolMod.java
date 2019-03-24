package org.scribble.lang;


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
	
	public static ProtocolMod fromAst(org.scribble.ast.ProtocolMod ast)
	{
		switch (ast.toString())
		{
			case "aux":      return AUX;
			case "explicit": return EXPLICIT;
			default:         throw new RuntimeException("Unknown modifier: " + ast);
		}
	}
}
