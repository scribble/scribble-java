package org.scribble2.model.name.simple;


public class ScopeNode extends SimpleNameNode
{
	//public static final String EMPTY_SCOPENAME_IDENTIFIER = "";  // Same pattern as empty operator

	public ScopeNode(String name)
	{
		super(name);
	}
	
	/*@Override
	public SimpleName toName()
	{
		/*if (this.identifier.equals(EMPTY_SCOPENAME_IDENTIFIER))
		{
			return Scope.EMPTY_SCOPE;
		}* /
		return new SimpleName(Kind.SCOPE, this.identifier);  // Distinguish scope element kind from complete scope?
	}*/
}
