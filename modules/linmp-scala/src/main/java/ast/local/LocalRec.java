package ast.local;

import ast.name.RecVar;

public class LocalRec implements LocalType
{
	//public final Role self;
	
	public final RecVar recvar;
	public final LocalType body;
	
	//public LocalRec(Role self, RecVar recvar, LocalType body)
	public LocalRec(RecVar recvar, LocalType body)
	{
		//this.self = self;
		this.recvar = recvar;
		this.body = body;
	}
	
	@Override
	public String toString()
	{
		return "mu " + this.recvar + "." + this.body;
	}
}
