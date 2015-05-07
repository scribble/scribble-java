package org.scribble2.model.name.simple;

import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.name.KindEnum;
import org.scribble2.sesstype.name.SimpleName;


public class ScopeNode extends SimpleNameNode
{
	//public static final String EMPTY_SCOPENAME_IDENTIFIER = "";  // Same pattern as empty operator

	public ScopeNode(String identifier)
	{
		super(identifier);
	}

	@Override
	protected ScopeNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		ScopeNode sn = new ScopeNode(identifier);
		sn = (ScopeNode) sn.del(del);
		return sn;
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
		return new SimpleName(KindEnum.SCOPE, this.identifier);
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
