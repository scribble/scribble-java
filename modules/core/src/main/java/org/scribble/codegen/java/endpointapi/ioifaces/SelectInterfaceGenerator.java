package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.Map;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;

public class SelectInterfaceGenerator extends IOStateInterfaceGenerator
{
	public SelectInterfaceGenerator(StateChannelApiGenerator apigen, Map<IOAction, InterfaceBuilder> actions, EndpointState curr)
	{
		super(apigen, actions, curr);
	}
	
	@Override
	public InterfaceBuilder generateType()
	{
		if (this.curr.getAllTakeable().stream().anyMatch((a) -> !a.isSend())) // HACK (connect/disconnect)
		{
			return null;
		}
		return super.generateType();
	}
}
