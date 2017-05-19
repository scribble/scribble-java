package org.scribble.codegen.statetype.go;

import org.scribble.codegen.statetype.STAPIBuilder;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class GSTAPIBuilder extends STAPIBuilder
{
	private int counter = 1;
	
	public GSTAPIBuilder(GProtocolName gpn, Role role, EGraph graph)
	{
		super(gpn, role, graph,
				new GSTOutputStateBuilder(new GSTSendActionBuilder()),
				new GSTReceiveStateBuilder(new GSTReceiveActionBuilder()),
				new GSTEndStateBuilder());
	}
	
	@Override
	protected String makeSTStateName(EState s)
	{
		return s.isTerminal() ? "EndSocket" : this.gpn.getSimpleName() + "_" + role + "_" + this.counter++;
	}

	@Override
	public String getFilePath(EState s)
	{
		return this.gpn.toString().replaceAll("\\.", "/") + "/" + makeSTStateName(s) + ".go";
	}
}
