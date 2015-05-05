package org.scribble2.sesstype.name;

//import org.scribble2.parser.AntlrConstants;

public enum KindEnum
{
  // FIXME: factor out constants
	ROLE("role"),
	SIG("sig"),
	TYPE("type"),
	PACKAGE("package"),
	MODULE("module"),
	PROTOCOL("protocol"),
	OPERATOR("operator"),
	SCOPE("scope"),
	RECURSIONVAR("recursionvar"),
	AMBIGUOUS("ambiguous");
	
	private final String kind;
	
	private KindEnum(String kind)
	{
		this.kind = kind;
	}
	
	@Override
	public String toString()
	{
		return this.kind;
	}
}
