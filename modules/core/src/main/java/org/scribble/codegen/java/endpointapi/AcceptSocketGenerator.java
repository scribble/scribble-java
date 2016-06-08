package org.scribble.codegen.java.endpointapi;

import java.util.Set;

import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;

public class AcceptSocketGenerator extends ScribSocketGenerator
{
	public AcceptSocketGenerator(StateChannelApiGenerator apigen, EndpointState curr)
	{
		super(apigen, curr);
	}

	@Override
	protected String getSuperClassType()
	{
		return ACCEPTSOCKET_CLASS + "<" + getSessionClassName() + ", " + getSelfClassName() + ">";
	}

	@Override
	protected void addImports()
	{
		super.addImports();
		//this.cb.addImports(getOpsPackageName() + ".*");
	}

	@Override
	protected void addMethods()
	{
		Set<IOAction> as = curr.getTakeable();
		if (as.size() > 1)
		{
			throw new RuntimeException("AcceptSocket generation not yet supported for accept-branches: " + as);
		}
		IOAction a = as.iterator().next();
		EndpointState succ = curr.take(a);
		makeAcceptMethod(a, succ);
	}

	private void makeAcceptMethod(IOAction a, EndpointState succ)
	{
		MethodBuilder mb = makeAcceptHeader(a, succ);
		mb.addBodyLine(JavaBuilder.SUPER + ".accept(ss, " + getSessionApiRoleConstant(a.obj) + ");");
		addReturnNextSocket(mb, succ);
	}

	private MethodBuilder makeAcceptHeader(IOAction a, EndpointState succ)
	{
		MethodBuilder mb = this.cb.newMethod();
		setAcceptHeaderWithoutReturnType(this.apigen, a, mb);
		setNextSocketReturnType(this.apigen, mb, succ);
		return mb;
	}

	// Doesn't include return type
	//public static void makeReceiveHeader(StateChannelApiGenerator apigen, IOAction a, EndpointState succ, MethodBuilder mb)
	public static void setAcceptHeaderWithoutReturnType(StateChannelApiGenerator apigen, IOAction a, MethodBuilder mb)
	{
		final String ROLE_PARAM = "role";
			
		mb.setName("accept");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException");
		mb.addParameters(SCRIBSERVERSOCKET_CLASS + " ss", SessionApiGenerator.getRoleClassName(a.obj) + " " + ROLE_PARAM);
	}
}
