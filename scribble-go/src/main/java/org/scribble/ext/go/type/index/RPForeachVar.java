package org.scribble.ext.go.type.index;

// Foreach index variable occurrence -- hacky?
// Currently only used for parsing foreach param decls -- occurrences of vars inside foreach not disambiguated between endpoint-vars and foreach-vars
// (Currently no actual RPIndexExprNode ast -- so no name disambiguation)

// FIXME: @Deprecated -- causes problems with checking equality between var occurrences, etc. -- or use same equals/hash for both?...
public class RPForeachVar extends RPIndexVar 
{
	protected RPForeachVar(String name)
	{
		super(name); 
	}
	
	/*@Override
	public Set<RPIndexVar> getVars()
	{
		return Collections.emptySet();  // No: still treat foreach vars as vars here -- scope managmenet should be handled by, e.g., RPCoreLForeach
	}*/
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof RPForeachVar))
		{
			return false;
		}
		return super.equals(this)  // Does canEqual
				&& this.name.equals(((RPForeachVar) o).name);
	}
	
	@Override
	protected boolean canEqual(Object o)
	{
		return o instanceof RPForeachVar;
	}

	@Override
	public int hashCode()
	{
		int hash = 2243;
		hash = 31 * hash + super.hashCode();
		hash = 31 * hash + this.name.hashCode();
		return hash;
	}
}
