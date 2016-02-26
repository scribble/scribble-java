package ast.global;

import ast.name.RecVar;

public class GlobalRec implements GlobalType
{
	public final RecVar recvar;
	public final GlobalType body;
	
	public GlobalRec(RecVar recvar, GlobalType body)
	{
		this.recvar = recvar;
		this.body = body;
	}
}
