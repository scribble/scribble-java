package org.scribble.codegen.java.endpointapi;

import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.main.ScribbleException;

// Build a (top-level) type declaration for the API generation output
public abstract class StateChannelTypeGenerator
{
	protected final StateChannelApiGenerator apigen;

	public StateChannelTypeGenerator(StateChannelApiGenerator apigen)
	{
		this.apigen = apigen;
	}
	
	public abstract TypeBuilder generateType() throws ScribbleException;  // FIXME: APIGenerationException?
}
