package org.scribble.codegen.java.endpointapi;

import java.util.Set;

import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.model.endpoint.EndpointState;
import org.scribble.model.endpoint.actions.LMIOAction;

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
		Set<LMIOAction> as = curr.getTakeable();
		if (as.size() > 1)
		{
			throw new RuntimeException("AcceptSocket generation not yet supported for accept-branches: " + as);
		}
		LMIOAction a = as.iterator().next();
		EndpointState succ = curr.take(a);
		makeAcceptMethod(a, succ);
	}

	private void makeAcceptMethod(LMIOAction a, EndpointState succ)
	{
		MethodBuilder mb = makeAcceptHeader(a, succ);
		mb.addBodyLine(JavaBuilder.SUPER + ".accept(ss, " + getSessionApiRoleConstant(a.obj) + ");");
		addReturnNextSocket(mb, succ);
	}

	private MethodBuilder makeAcceptHeader(LMIOAction a, EndpointState succ)
	{
		MethodBuilder mb = this.cb.newMethod();
		setAcceptHeaderWithoutReturnType(this.apigen, a, mb);
		setNextSocketReturnType(this.apigen, mb, succ);
		return mb;
	}

	// Doesn't include return type
	//public static void makeReceiveHeader(StateChannelApiGenerator apigen, IOAction a, EndpointState succ, MethodBuilder mb)
	public static void setAcceptHeaderWithoutReturnType(StateChannelApiGenerator apigen, LMIOAction a, MethodBuilder mb)
	{
		final String ROLE_PARAM = "role";
			
		mb.setName("accept");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException");
		mb.addParameters(SessionApiGenerator.getRoleClassName(a.obj) + " " + ROLE_PARAM, SCRIBSERVERSOCKET_CLASS + " ss");
	}
}
