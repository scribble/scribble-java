package ast.name;

public abstract class NameNode
{
	public final String name;
	
	protected NameNode(String name)
	{
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof NameNode))
		{
			return false;
		}
		NameNode that = (NameNode) o;
		return that.canEqual(this) && this.name.equals(that.name);
	}
	
	public abstract boolean canEqual(Object o);
	
	@Override
	public int hashCode()
	{
		int hash = 881;
		hash = 31 * hash + this.name.hashCode();
		return hash;
	}

	@Override
	public String toString()
	{
		return this.name.toString();
	}
}
