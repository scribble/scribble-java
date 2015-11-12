package org.scribble.codegen.java.endpointapi;

import org.scribble.model.local.EndpointState;

public class EndSocketGenerator extends ScribSocketGenerator
{
	public EndSocketGenerator(StateChannelApiGenerator apigen, EndpointState curr)
	{
		super(apigen, curr);
	}
	
	@Override
	protected String getClassName()
	{
		return GENERATED_ENDSOCKET_NAME;
	}

	@Override
	protected String getSuperClassType()
	{
		return ENDSOCKET_CLASS + "<" + getSessionClassName() + ", " + getSelfClassName() + ">";
	}

	@Override
	protected void addImports()
	{
		super.addImports();
	}

	@Override
	protected void addMethods()
	{

	}
}
