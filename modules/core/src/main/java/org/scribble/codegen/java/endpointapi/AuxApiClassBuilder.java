package org.scribble.codegen.java.endpointapi;

import org.scribble.codegen.java.util.TypeBuilder;

// An auxiliary class builder: supplemental to the class being built via the parent ClassBuilder
public abstract class AuxApiClassBuilder extends ApiTypeBuilder
{
	protected final TypeBuilder parent;

	public AuxApiClassBuilder(StateChannelApiGenerator apigen, TypeBuilder parent)
	{
		super(apigen);
		this.parent = parent;
	}
}
