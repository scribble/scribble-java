package org.scribble.codegen.java.endpointapi.ioifaces;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.actions.LMIOAction;

public class SuccessorInterfaceGenerator extends IOInterfaceGenerator
{
	private final LMIOAction a;
	private final InterfaceBuilder ib = new InterfaceBuilder();

	public SuccessorInterfaceGenerator(StateChannelApiGenerator apigen, EndpointState curr, LMIOAction a)
	{
		super(apigen, curr);
		this.a = a;
	}

	@Override
	public InterfaceBuilder generateType()
	{
		this.ib.setName(getSuccessorInterfaceName(this.a));
		this.ib.setPackage(IOInterfacesGenerator.getIOInterfacePackageName(this.apigen.getGProtocolName(), this.apigen.getSelf()));
		this.ib.addModifiers(JavaBuilder.PUBLIC);
		//this.ib.addInterfaces(ifaces);(...State...);
		return ib;
	}
	
	public static String getSuccessorInterfaceName(LMIOAction a)
	{
		return "Succ_" + ActionInterfaceGenerator.getActionInterfaceName(a);
	}
}
