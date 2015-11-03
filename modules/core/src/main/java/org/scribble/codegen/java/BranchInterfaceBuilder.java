package org.scribble.codegen.java;

import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.MessageSigName;

// Factor out
public class BranchInterfaceBuilder
{
	protected final EndpointApiGenerator apigen;
	private final ClassBuilder cb;
	private final EndpointState curr;

	// Pre: cb is the BRanchSocketBuilder
	public BranchInterfaceBuilder(EndpointApiGenerator apigen, ClassBuilder cb, EndpointState curr)
	{
		this.apigen = apigen;
		this.cb = cb;
		this.curr = curr;
	}

	protected InterfaceBuilder build()
	{
		Module main = this.apigen.getMainModule();

		// Handler interface
		InterfaceBuilder ib = new InterfaceBuilder();
		ib.setPackage(SessionApiGenerator.getPackageName(this.apigen.getGProtocolName()));  // FIXME: factor out
		ib.addImports("java.io.IOException");
		ib.setName(getBranchInterfaceName(cb));
		ib.addModifiers(InterfaceBuilder.PUBLIC);
		for (IOAction a : curr.getAcceptable())
		{
			EndpointState succ = curr.accept(a);
			String nextClass = this.apigen.getSocketClassName(succ);
			String opClass = SessionApiGenerator.getOpClassName(a.mid);

			MethodBuilder mb3 = ib.newMethod("receive");
			mb3.addModifiers(ClassBuilder.PUBLIC);
			mb3.setReturn(InterfaceBuilder.VOID);
			mb3.addExceptions(EndpointApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException");
			//if (!nextClass.equals(ClassBuilder.VOID))
			if (succ.isTerminal())
			{
				mb3.addParameters(ScribSocketBuilder.ENDSOCKET_CLASS + "<" + SessionApiGenerator.getSessionClassName(this.apigen.getGProtocolName()) + ", " + this.apigen.getSelf() + ">" + " schan");  // FIXME: factor out
			}
			else
			{
				mb3.addParameters(nextClass + " schan");  // FIXME: factor out
			}
			mb3.addParameters(opClass + " " + EndpointApiGenerator.RECEIVE_OP_PARAM);  // More params added below

			if (a.mid.isOp())
			{	
				ReceiveSocketBuilder.addReceiveOpParams(mb3, this.apigen.getMainModule(), a);
			}
			else //if (a.mid.isMessageSigName())
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				ReceiveSocketBuilder.addReceiveMessageSigNameParams(mb3, a, msd);
			}
		}
		//this.ifaces.put(ifname, ib);
		return ib;
	}

	// Pre: cb is the BranchSocketBuilder
	protected static String getBranchInterfaceName(ClassBuilder cb)
	{
		return cb.getName() + "_Handler";
	}
}
