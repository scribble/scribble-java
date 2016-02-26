package ast.name;

import ast.global.GlobalType;

public class RecVar extends NameNode implements GlobalType
{
	public RecVar(String name)
	{
		super(name);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return (o instanceof RecVar);
	}
}
