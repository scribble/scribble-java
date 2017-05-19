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
	
	protected static String getPremable1(String pack, String statename)
	{
		return
				  "package " + pack
				+ "\n"
				+ "type " + statename + " struct{}";
	}

	@Override
	public String getPreamble(STAPIBuilder api, EState s)
	{
		return getPremable1(getPackage(api), api.getSTStateName(s));
	}
}
