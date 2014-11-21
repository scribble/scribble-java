package org.scribble2.model.name.simple;

import org.scribble2.sesstype.name.Kind;
import org.scribble2.sesstype.name.SimpleName;


public class ScopeNode extends SimpleNameNode
{
	//public static final String EMPTY_SCOPENAME_IDENTIFIER = "";  // Same pattern as empty operator

	public ScopeNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected ScopeNode copy()
	{
		return new ScopeNode(this.identifier);
	}

	@Override
	//public Scope toName()
	public SimpleName toName()
	{
		//return new Scope(Scope.ROOT_SCOPE, new SimpleName(K, this.identifier));
		return new SimpleName(Kind.SCOPE, this.identifier);
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
