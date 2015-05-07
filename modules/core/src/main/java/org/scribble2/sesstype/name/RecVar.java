package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.RecVarKind;

//public class RecursionVar extends SimpleName
public class RecVar extends Name<RecVarKind>
{
	private static final long serialVersionUID = 1L;

	protected RecVar()
	{
		super(RecVarKind.KIND);
	}

	public RecVar(String text)
	{
		//super(KindEnum.RECURSIONVAR, text);
		super(RecVarKind.KIND, text);
	}
}
