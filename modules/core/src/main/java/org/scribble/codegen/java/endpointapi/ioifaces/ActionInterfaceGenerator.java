package org.scribble.codegen.java.endpointapi.ioifaces;

import org.scribble.codegen.java.endpointapi.ReceiveSocketGenerator;
import org.scribble.codegen.java.endpointapi.SendSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelTypeGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.PayloadType;

public class ActionInterfaceGenerator extends StateChannelTypeGenerator
{
	private final IOAction a;
	private final InterfaceBuilder ib = new InterfaceBuilder();

	public ActionInterfaceGenerator(StateChannelApiGenerator apigen, IOAction a)
	{
		super(apigen);
		this.a = a;
	}

	@Override
	public InterfaceBuilder generateType()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();

		this.ib.setName(getActionInterfaceName(this.a));
		this.ib.setPackage(IOInterfacesGenerator.getPackageName(this.apigen.getGProtocolName(), this.apigen.getSelf()));
		this.ib.addImports("java.io.IOException");
		this.ib.addImports(SessionApiGenerator.getEndpointApiRootPackageName(gpn) + ".*");  // FIXME: factor out with ScribSocketGenerator
		this.ib.addImports(SessionApiGenerator.getRolesPackageName(gpn) + ".*");
		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		this.ib.addModifiers(JavaBuilder.PUBLIC);
		this.ib.addParameters("__Succ extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(this.a));
		AbstractMethodBuilder mb = this.ib.newAbstractMethod();  // FIXME: factor out with ReceiveSocketBuilder
		if (this.a instanceof Receive)
		{
			ReceiveSocketGenerator.setReceiveHeaderWithoutReturnType(this.apigen, this.a, mb);
		}
		else //if (this.a instanceof Send)
		{
			SendSocketGenerator.setSendHeaderWithoutReturnType(this.apigen, this.a, mb);
		}
		mb.setReturn("__Succ");
		return ib;
	}
	
	public static String getActionInterfaceName(IOAction a)
	{
		String name = (a instanceof Receive)
				? "In"
				: "Out";
		name += "_" + getActionString(a);
		return name;
	}

	public static String getActionString(IOAction a)
	{
		String name = a.peer + "$" + a.mid;
		for (PayloadType<?> pay : a.payload.elems)
		{
			name += "$" + pay;
		}
		return name;
	}
}
