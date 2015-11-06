package org.scribble.codegen.java.endpointapi.ioifaces;

import org.scribble.codegen.java.endpointapi.CaseSocketGenerator;
import org.scribble.codegen.java.endpointapi.ReceiveSocketGenerator;
import org.scribble.codegen.java.endpointapi.ScribSocketGenerator;
import org.scribble.codegen.java.endpointapi.SendSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelTypeGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.PayloadType;

public class ActionInterfaceGenerator extends StateChannelTypeGenerator
{
	private final EndpointState curr;
	private final IOAction a;
	private final InterfaceBuilder ib = new InterfaceBuilder();

	public ActionInterfaceGenerator(StateChannelApiGenerator apigen, EndpointState curr, IOAction a)
	{
		super(apigen);
		this.curr = curr;
		this.a = a;
	}

	@Override
	public InterfaceBuilder generateType()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();

		this.ib.setName(getActionInterfaceName(this.curr, this.a));
		this.ib.setPackage(IOInterfacesGenerator.getPackageName(this.apigen.getGProtocolName(), this.apigen.getSelf()));
		this.ib.addImports("java.io.IOException");
		this.ib.addImports(SessionApiGenerator.getEndpointApiRootPackageName(gpn) + ".*");  // FIXME: factor out with ScribSocketGenerator
		this.ib.addImports(SessionApiGenerator.getRolesPackageName(gpn) + ".*");
		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		this.ib.addModifiers(JavaBuilder.PUBLIC);
		this.ib.addParameters("__Succ extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(this.curr, this.a));
		AbstractMethodBuilder mb = this.ib.newAbstractMethod();  // FIXME: factor out with ReceiveSocketBuilder
		if (this.a instanceof Receive)
		{
			if (this.curr.getAcceptable().size() > 1)
			{
				CaseSocketGenerator.setCaseReceiveHeaderWithoutReturnType(this.apigen, this.a, mb);
			}
			else
			{
				ReceiveSocketGenerator.setReceiveHeaderWithoutReturnType(this.apigen, this.a, mb);
			}
		}
		else //if (this.a instanceof Send)
		{
			SendSocketGenerator.setSendHeaderWithoutReturnType(this.apigen, this.a, mb);
		}
		EndpointState succ = this.curr.accept(this.a);
		if (succ.isTerminal())
		{
			ScribSocketGenerator.setNextSocketReturnType(this.apigen, mb, succ);
		}
		else
		{
			mb.setReturn("__Succ");
		}
		return ib;
	}
	
	public static String getActionInterfaceName(EndpointState curr, IOAction a)
	{
		/*String name = (a instanceof Receive)
				? "In"
				: "Out";*/
		String name;
		if (curr.getAcceptable().iterator().next() instanceof Receive)
		{
			if (curr.getAcceptable().size() > 1)
			{
				name = "Case";  // FIXME: make subtype of In?
			}
			else
			{
				name = "In";
			}
		}
		else
		{
			name = "Out";
		}
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
