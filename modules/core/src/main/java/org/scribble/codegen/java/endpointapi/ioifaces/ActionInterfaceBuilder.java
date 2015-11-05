package org.scribble.codegen.java.endpointapi.ioifaces;

import org.scribble.codegen.java.endpointapi.ApiTypeBuilder;
import org.scribble.codegen.java.endpointapi.ReceiveSocketBuilder;
import org.scribble.codegen.java.endpointapi.ScribSocketBuilder;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.name.PayloadType;

public class ActionInterfaceBuilder extends ApiTypeBuilder
{
	private IOAction a;
	private InterfaceBuilder ib = new InterfaceBuilder();

	public ActionInterfaceBuilder(StateChannelApiGenerator apigen, IOAction a)
	{
		super(apigen);
	}

	@Override
	public InterfaceBuilder build()
	{
		ib.setName(getActionInterfaceName(this.a));
		ib.addParameters("_Succ");  // TODO
		if (this.a instanceof Receive)
		{
			buildReceiveMethod();
		}
		else
		{
			buildSendMethod();
		}
		return ib;
	}
	
	private AbstractMethodBuilder buildReceiveMethod()
	{
		AbstractMethodBuilder mb = this.ib.newAbstractMethod();  // FIXME: factor out with ReceiveSocketBuilder
		ReceiveSocketBuilder.setReceiveHeaderWithoutReturnType(this.apigen, this.a, mb);
		mb.setReturn("_Succ");  // TODO
		return mb;
	}
	
	private AbstractMethodBuilder buildSendMethod()
	{
		AbstractMethodBuilder mb = this.ib.newAbstractMethod();  // FIXME: factor out with SendSocketBuilder
		...TODO
		return mb;
	}
	
	public static String getActionInterfaceName(IOAction a)
	{
		String name = (a instanceof Receive)
				? "In"
				: "Out";
		name += "_" + a.peer;	
		name += "$" + a.mid;
		for (PayloadType<?> pay : a.payload.elems)
		{
			name += "$" + pay;
		}
		return name;
	}
}
