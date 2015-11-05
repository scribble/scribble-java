package org.scribble.codegen.java.endpointapi;

import org.scribble.codegen.java.util.TypeBuilder;

// An auxiliary class builder: supplemental to the class being built via the parent ClassBuilder
public abstract class AuxApiTypeGenerator extends StateChannelTypeGenerator
{
	protected final TypeBuilder parent;

	public AuxApiTypeGenerator(StateChannelApiGenerator apigen, TypeBuilder parent)
	{
		super(apigen);
		this.parent = parent;
	}
}
