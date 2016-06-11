package org.scribble.codegen.java.endpointapi;

import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.MessageSigName;

// Factor out
public class HandlerInterfaceGenerator extends AuxStateChannelTypeGenerator
{
	private final EndpointState curr;

	// Pre: cb is the BranchSocketBuilder
	public HandlerInterfaceGenerator(StateChannelApiGenerator apigen, ClassBuilder parent, EndpointState curr)
	{
		super(apigen, parent);
		this.curr = curr;
	}

	@Override
	public InterfaceBuilder generateType() throws ScribbleException
	{
		GProtocolName gpn = this.apigen.getGProtocolName();

		// Handler interface
		InterfaceBuilder ib = new InterfaceBuilder();
		ib.setPackage(SessionApiGenerator.getStateChannelPackageName(gpn, this.apigen.getSelf()));  // FIXME: factor out with ScribSocketBuilder
		ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");  // FIXME: factor out with ScribSocketBuilder
		//ib.setName(getHandlerInterfaceName(parent));
		ib.setName(getHandlerInterfaceName(this.parent.getName()));
		ib.addModifiers(InterfaceBuilder.PUBLIC);

		for (IOAction a : this.curr.getTakeable())  // Doesn't need to be sorted
		{
			EndpointState succ = this.curr.take(a);
			String nextClass = this.apigen.getSocketClassName(succ);

			AbstractMethodBuilder mb3 = ib.newAbstractMethod();
			mb3.addAnnotations("@Override");
			setHandleMethodHeaderWithoutParamTypes(this.apigen, mb3);
			if (succ.isTerminal())
			{
				mb3.addParameters(ScribSocketGenerator.GENERATED_ENDSOCKET_NAME + " schan");  // FIXME: factor out
			}
			else
			{
				mb3.addParameters(nextClass + " schan");  // FIXME: factor out
			}
			addHandleMethodOpAndPayloadParams(this.apigen, a, mb3);
			
			if (this.curr.take(a).isTerminal())
			{
				// FIXME: potentially repeated (but OK)
				ib.addImports(SessionApiGenerator.getEndpointApiRootPackageName(gpn) + ".*");  // FIXME: factor out with ScribSocketBuilder
				ib.addImports(SessionApiGenerator.getRolesPackageName(this.apigen.getGProtocolName()) + ".*");
			}
		}

		return ib;
	}

	// void return type -- have to deal with Succ as param
	public static void setHandleMethodHeaderWithoutParamTypes(StateChannelApiGenerator apigen, MethodBuilder mb)
	{
		mb.setName("receive");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.setReturn(InterfaceBuilder.VOID);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");
	}
	
	public static void addHandleMethodOpAndPayloadParams(StateChannelApiGenerator apigen, IOAction a, MethodBuilder mb) throws ScribbleException
	{
		Module main = apigen.getMainModule();
		String opClass = SessionApiGenerator.getOpClassName(a.mid);

		mb.addParameters(opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);  // More params added below

		if (a.mid.isOp())
		{	
			ReceiveSocketGenerator.addReceiveOpParams(mb, apigen.getMainModule(), a, false);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			ReceiveSocketGenerator.addReceiveMessageSigNameParams(mb, msd, false);
		}
	}

	// Pre: cb is the BranchSocketBuilder
	//public static String getHandlerInterfaceName(TypeBuilder cb)
	public static String getHandlerInterfaceName(String branchName)
	{
		return branchName + "_Handler";
	}
}
