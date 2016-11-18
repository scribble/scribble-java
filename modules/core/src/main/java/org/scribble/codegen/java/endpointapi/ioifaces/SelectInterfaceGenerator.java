package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.Map;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EndpointState;
import org.scribble.model.endpoint.actions.LMIOAction;

public class SelectInterfaceGenerator extends IOStateInterfaceGenerator
{
	public SelectInterfaceGenerator(StateChannelApiGenerator apigen, Map<LMIOAction, InterfaceBuilder> actions, EndpointState curr)
	{
		super(apigen, actions, curr);
	}
	
	@Override
	public InterfaceBuilder generateType() throws ScribbleException
	{
		if (this.curr.getAllTakeable().stream().anyMatch((a) -> !a.isSend())) // TODO (connect/disconnect)
		{
			//return null;
			throw new RuntimeException("TODO: " + this.curr);
		}
		return super.generateType();
	}
}
