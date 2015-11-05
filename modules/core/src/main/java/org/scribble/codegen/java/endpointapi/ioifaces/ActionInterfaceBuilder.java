package org.scribble.codegen.java.endpointapi.ioifaces;

import org.scribble.codegen.java.endpointapi.ApiTypeBuilder;
import org.scribble.codegen.java.endpointapi.ReceiveSocketBuilder;
import org.scribble.codegen.java.endpointapi.SendSocketBuilder;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.name.PayloadType;

public class ActionInterfaceBuilder extends ApiTypeBuilder
{
	private final IOAction a;
	private final InterfaceBuilder ib = new InterfaceBuilder();

	public ActionInterfaceBuilder(StateChannelApiGenerator apigen, IOAction a)
	{
		super(apigen);
		this.a = a;
	}

	@Override
	public InterfaceBuilder build()
	{
		this.ib.setName(getActionInterfaceName(this.a));
		this.ib.addModifiers(JavaBuilder.PUBLIC);
		this.ib.addParameters("__Succ");  // TODO
		AbstractMethodBuilder mb = this.ib.newAbstractMethod();  // FIXME: factor out with ReceiveSocketBuilder
		if (this.a instanceof Receive)
		{
			ReceiveSocketBuilder.setReceiveHeaderWithoutReturnType(this.apigen, this.a, mb);
		}
		else //if (this.a instanceof Send)
		{
			SendSocketBuilder.setSendHeaderWithoutReturnType(this.apigen, this.a, mb);
		}
		mb.setReturn("__Succ");
		return ib;
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
