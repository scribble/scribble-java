package org.scribble.codegen.java.endpointapi.ioifaces;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelTypeGenerator;
import org.scribble.model.local.EndpointState;

public abstract class IOInterfaceGenerator extends StateChannelTypeGenerator
{
	protected final EndpointState curr;

	public IOInterfaceGenerator(StateChannelApiGenerator apigen, EndpointState curr)
	{
		super(apigen);
		this.curr = curr;
	}
}
