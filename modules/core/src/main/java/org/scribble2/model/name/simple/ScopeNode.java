package org.scribble2.model.name.simple;

import org.scribble2.sesstype.kind.ScopeKind;
import org.scribble2.sesstype.name.Scope;


//public class ScopeNode extends SimpleNameNode<Scope, ScopeKind>
public class ScopeNode extends SimpleNameNode<ScopeKind>
{
	//public static final String EMPTY_SCOPENAME_IDENTIFIER = "";  // Same pattern as empty operator

	public ScopeNode(String identifier)
	{
		super(identifier);
	}

	/*@Override
	protected ScopeNode reconstruct(String identifier)
	{
		ModelDel del = del();  // Default delegate assigned in ModelFactoryImpl for all simple names
		ScopeNode sn = new ScopeNode(identifier);
		sn = (ScopeNode) sn.del(del);
		return sn;
	}*/

	@Override
	protected ScopeNode copy()
	{
		//return new ScopeNode(this.identifier);
		return new ScopeNode(getIdentifier());
	}

	@Override
	public Scope toName()
	//public SimpleName toName()
	{
		//return new Scope(Scope.ROOT_SCOPE, new SimpleName(K, this.identifier));
		//return new SimpleName(KindEnum.SCOPE, this.identifier);
		//return new Scope(Scope.EMPTY_SCOPE, this.identifier);
		//return new Scope(this.identifier);
		return new Scope(getIdentifier());
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
