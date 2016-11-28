package org.scribble.codegen.java.endpointapi.ioifaces;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelTypeGenerator;
import org.scribble.model.endpoint.EState;

public abstract class IOInterfaceGenerator extends StateChannelTypeGenerator
{
	protected final EState curr;

	public IOInterfaceGenerator(StateChannelApiGenerator apigen, EState curr)
	{
		super(apigen);
		this.curr = curr;
	}
}
