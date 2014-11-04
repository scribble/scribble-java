package org.scribble2.sesstype.name;


public class Role extends SimpleName
{
	public static final Role EMPTY_ROLE = new Role();

	private static final long serialVersionUID = 1L;

	protected Role()
	{
		super(Kind.ROLE);
	}

	public Role(String text)
	{
		super(Kind.ROLE, text);
	}
}
