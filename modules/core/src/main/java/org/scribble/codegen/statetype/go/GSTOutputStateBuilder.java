package org.scribble.codegen.statetype.go;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.codegen.statetype.STOutputStateBuilder;
import org.scribble.codegen.statetype.STSendActionBuilder;
import org.scribble.model.endpoint.EState;

public class GSTOutputStateBuilder extends STOutputStateBuilder
{
	public GSTOutputStateBuilder(STSendActionBuilder sb)
	{
		super(sb);
	}
	
	protected static String getPackageDecl(STAPIBuilder api)
	{
		return "package " + api.getPackage();
	}
	
	protected static String getPremable1(STAPIBuilder api, EState s)
	{
		return
				  getPackageDecl(api) + "\n"
				+ "\n"
				+ "type " + api.getSTStateName(s) + " struct{}";
	}

	@Override
	public String getPreamble(STAPIBuilder api, EState s)
	{
		return getPremable1(api, s);
	}
}
