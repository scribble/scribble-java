package ast.name;

public class Role extends NameNode
{
	public Role(String name)
	{
		super(name);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return (o instanceof Role);
	}
}
