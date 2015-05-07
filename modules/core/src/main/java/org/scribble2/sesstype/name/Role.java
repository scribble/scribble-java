package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.RoleKind;


//public class Role extends SimpleName
public class Role extends Name<RoleKind>
{
	public static final Role EMPTY_ROLE = new Role();

	private static final long serialVersionUID = 1L;

	protected Role()
	{
		//super(KindEnum.ROLE);
		super(RoleKind.KIND);
	}

	public Role(String text)
	{
		//super(KindEnum.ROLE, text);
		super(RoleKind.KIND, text);
	}
}
