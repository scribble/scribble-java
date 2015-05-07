package org.scribble2.sesstype.name;

import org.scribble2.sesstype.kind.RecVarKind;

//public class RecursionVar extends SimpleName
public class RecursionVar extends Name<RecVarKind>
{
	private static final long serialVersionUID = 1L;

	protected RecursionVar()
	{
		super(RecVarKind.KIND);
	}

	public RecursionVar(String text)
	{
		//super(KindEnum.RECURSIONVAR, text);
		super(RecVarKind.KIND, text);
	}
}
