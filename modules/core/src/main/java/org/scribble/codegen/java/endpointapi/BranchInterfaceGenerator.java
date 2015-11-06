package org.scribble.codegen.java.endpointapi;

import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.MessageSigName;

// Factor out
public class BranchInterfaceGenerator extends AuxApiTypeGenerator
{
	private final EndpointState curr;

	// Pre: cb is the BrranchSocketBuilder
	public BranchInterfaceGenerator(StateChannelApiGenerator apigen, ClassBuilder parent, EndpointState curr)
	{
		super(apigen, parent);
		this.curr = curr;
	}

	@Override
	public InterfaceBuilder generateType()
	{
		Module main = this.apigen.getMainModule();
		GProtocolName gpn = this.apigen.getGProtocolName();

		// Handler interface
		InterfaceBuilder ib = new InterfaceBuilder();
		ib.setPackage(SessionApiGenerator.getStateChannelPackageName(gpn, this.apigen.getSelf()));  // FIXME: factor out with ScribSocketBuilder
		ib.addImports("java.io.IOException");
		ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");  // FIXME: factor out with ScribSocketBuilder
		ib.setName(getBranchInterfaceName(parent));
		ib.addModifiers(InterfaceBuilder.PUBLIC);
		for (IOAction a : curr.getAcceptable())
		{
			EndpointState succ = curr.accept(a);
			String nextClass = this.apigen.getSocketClassName(succ);
			String opClass = SessionApiGenerator.getOpClassName(a.mid);

			AbstractMethodBuilder mb3 = ib.newAbstractMethod("receive");
			mb3.addModifiers(JavaBuilder.PUBLIC);
			mb3.setReturn(InterfaceBuilder.VOID);
			mb3.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException");
			//if (!nextClass.equals(ClassBuilder.VOID))
			if (succ.isTerminal())
			{
				// FIXME: repeated
				ib.addImports(SessionApiGenerator.getEndpointApiRootPackageName(gpn) + ".*");  // FIXME: factor out with ScribSocketBuilder
				ib.addImports(SessionApiGenerator.getRolesPackageName(this.apigen.getGProtocolName()) + ".*");
				mb3.addParameters(ScribSocketGenerator.GEN_ENDSOCKET_CLASS
						//+ "<" + SessionApiGenerator.getSessionClassName(this.apigen.getGProtocolName()) + ", " + this.apigen.getSelf() + ">"
						+ " schan");  // FIXME: factor out
			}
			else
			{
				mb3.addParameters(nextClass + " schan");  // FIXME: factor out
			}
			mb3.addParameters(opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);  // More params added below

			if (a.mid.isOp())
			{	
				ReceiveSocketGenerator.addReceiveOpParams(mb3, this.apigen.getMainModule(), a);
			}
			else //if (a.mid.isMessageSigName())
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				ReceiveSocketGenerator.addReceiveMessageSigNameParams(mb3, msd);
			}
		}
		//this.ifaces.put(ifname, ib);
		return ib;
	}

	// Pre: cb is the BranchSocketBuilder
	protected static String getBranchInterfaceName(TypeBuilder cb)
	{
		return cb.getName() + "_Handler";
	}
}
