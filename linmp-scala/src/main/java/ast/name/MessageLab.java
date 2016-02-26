package ast.name;

public class MessageLab extends NameNode
{
	public MessageLab(String name)
	{
		super(name);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return (o instanceof MessageLab);
	}
}
