package org.scribble.codegen.java.endpointapi;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.endpointapi.ioifaces.BranchInterfaceGenerator;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.sesstype.name.Role;

public class BranchSocketGenerator extends ScribSocketGenerator
{
	public BranchSocketGenerator(StateChannelApiGenerator apigen, EndpointState curr)
	{
		super(apigen, curr);
	}

	@Override
	protected String getSuperClassType()
	{
		return BRANCHSOCKET_CLASS + "<" + getSessionClassName() + ", " + getSelfClassName() + ">";
	}

	@Override
	protected void addImports()
	{
		this.cb.addImports("java.io.IOException");
		super.addImports();
	}

	//private void addBranchMethod(ClassBuilder cb, EndpointState curr)
	@Override
	protected void addMethods()
	{
		final String ROLE_PARAM = "role";
		final String MESSAGE_VAR = "m";
		final String OPENUM_VAR = "openum";
		final String OP = MESSAGE_VAR + "." + StateChannelApiGenerator.SCRIBMESSAGE_OP_FIELD;

		Module main = this.apigen.getMainModule();

		//String next = constructCaseClass(curr, main);
		ClassBuilder caseclass = new CaseSocketGenerator(apigen, curr).generateType();
		String next = caseclass.getName();
		String enumClass = getBranchEnumClassName(this.apigen, this.curr);

		//cb.addImports("java.util.concurrent.ExecutionException");
		
		MethodBuilder mb = cb.newMethod("branch");
		mb.setReturn(next);
		mb.addParameters(SessionApiGenerator.getRoleClassName(curr.getAcceptable().iterator().next().peer) + " " + ROLE_PARAM);
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");//, "ExecutionException", "InterruptedException");
		
		Role peer = curr.getAcceptable().iterator().next().peer;
		mb.addBodyLine(StateChannelApiGenerator.SCRIBMESSAGE_CLASS + " " + MESSAGE_VAR + " = "
				+ JavaBuilder.SUPER + ".readScribMessage(" + getSessionApiRoleConstant(peer) + ");");
		mb.addBodyLine(enumClass + " " + OPENUM_VAR + ";");
		boolean first = true;
		for (IOAction a : curr.getAcceptable())
		{
			mb.addBodyLine(((first) ? "" : "else ") + "if (" + OP + ".equals(" + getSessionApiOpConstant(a.mid) + ")) {");
			mb.addBodyLine(1, OPENUM_VAR + " = "
					+ enumClass + "." + SessionApiGenerator.getOpClassName(a.mid) + ";");
			mb.addBodyLine("}");
			first = false;
		}
		mb.addBodyLine("else {");
		mb.addBodyLine(1, "throw " + JavaBuilder.NEW + " RuntimeException(\"Won't get here: \" + " + OP + ");");
		mb.addBodyLine("}");
		mb.addBodyLine(JavaBuilder.RETURN + " "
				+ JavaBuilder.NEW + " " + next + "(" + SCRIBSOCKET_SE_FIELD + ", true, " + OPENUM_VAR + ", " + MESSAGE_VAR + ");");  // FIXME: dummy boolean not needed
		
		/* // Now using Branch IO State I/f enum
		EnumBuilder eb = cb.newMemberEnum(enumClass);
		eb.addModifiers(JavaBuilder.PUBLIC);
		eb.addInterfaces(OPENUM_INTERFACE);
		this.curr.getAcceptable().stream().forEach((a) -> eb.addValues(SessionApiGenerator.getOpClassName(a.mid)));*/
		

		// Handler branch method
		String ifname = HandlerInterfaceGenerator.getBranchInterfaceName(this.cb);
		MethodBuilder mb2 = this.cb.newMethod("branch");
		mb2.addParameters(SessionApiGenerator.getRoleClassName(peer) + " " + ROLE_PARAM);
		//mb2.addParameters("java.util.concurrent.Callable<" + ifname + "> branch");
		mb2.addParameters(ifname + " branch");
		mb2.setReturn(JavaBuilder.VOID);
		mb2.addModifiers(JavaBuilder.PUBLIC);
		mb2.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");//, "ExecutionException", "InterruptedException");
		mb2.addBodyLine(StateChannelApiGenerator.SCRIBMESSAGE_CLASS + " " + MESSAGE_VAR + " = "
				+ JavaBuilder.SUPER + ".readScribMessage(" + getSessionApiRoleConstant(peer) + ");");
		first = true;
		for (IOAction a : this.curr.getAcceptable())
		{
			EndpointState succ = this.curr.accept(a);
			if (first)
			{
				first = false;
			}
			else
			{
				mb2.addBodyLine("else");
			}
			mb2.addBodyLine("if (" + MESSAGE_VAR + "." + StateChannelApiGenerator.SCRIBMESSAGE_OP_FIELD + ".equals(" + getSessionApiOpConstant(a.mid) + ")) {");
			if (succ.isTerminal())
			{
				mb2.addBodyLine(1, SCRIBSOCKET_SE_FIELD + ".setCompleted();");
			}
			String ln = "branch.receive(";
			//if (!succ.isTerminal())
			{
				//FIXME: factor out with addReturn?
				 ln += JavaBuilder.NEW + " " + (succ.isTerminal() ? ScribSocketGenerator.GEN_ENDSOCKET_CLASS : this.apigen.getSocketClassName(succ)) + "(" + SCRIBSOCKET_SE_FIELD + ", true), ";
			}
			ln += getSessionApiOpConstant(a.mid);
					
			// Based on receive parameters
			if (a.mid.isOp())
			{
				if (!a.payload.isEmpty())
				{
					String buffSuper = JavaBuilder.NEW + " " + BUF_CLASS + "<>(";
					int i = 0;
					for (PayloadType<?> pt : a.payload.elems)
					{
						DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
						ln += ", " + buffSuper + "(" + dtd.extName + ") " + RECEIVE_MESSAGE_PARAM + "." + SCRIBMESSAGE_PAYLOAD_FIELD + "[" + i++ + "])";
					}
				}
			}
			else
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				ln += ", " + JavaBuilder.NEW + " " + BUF_CLASS + "<>((" + msd.extName + ") " +  RECEIVE_MESSAGE_PARAM + "." + SCRIBMESSAGE_PAYLOAD_FIELD + "[0])";
			}
				
			ln += ");";
			mb2.addBodyLine(1, ln);
			mb2.addBodyLine("}");
		}
		mb2.addBodyLine("else {");
		mb2.addBodyLine(1, "throw " + JavaBuilder.NEW + " RuntimeException(\"Won't get here: \" + " + OP + ");");
		mb2.addBodyLine("}");
		
		this.apigen.addTypeDecl(new HandlerInterfaceGenerator(this.apigen, this.cb, this.curr).generateType());
	}

	protected static String getBranchEnumClassName(StateChannelApiGenerator apigen, EndpointState curr)
	{
		//return apigen.getSocketClassName(curr) + "_Enum";
		return BranchInterfaceGenerator.getBranchInterfaceEnumName(apigen.getSelf(), curr);
	}
}
