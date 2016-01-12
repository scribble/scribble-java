package org.scribble.codegen.java.endpointapi;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.PayloadType;

public class SendSocketGenerator extends ScribSocketGenerator
{
	public SendSocketGenerator(StateChannelApiGenerator apigen, EndpointState curr)
	{
		super(apigen, curr);
	}

	@Override
	protected String getSuperClassType()
	{
		return SENDSOCKET_CLASS + "<" + getSessionClassName() + ", " + getSelfClassName() + ">";
	}

	@Override
	protected void addImports()
	{
		this.cb.addImports("java.io.IOException");
		super.addImports();
	}

	// A method for each successor state
	//private void addSendMethods(ClassBuilder cb, EndpointState curr)
	@Override
	protected void addMethods()
	{
		final String ROLE_PARAM = "role";

		for (IOAction a : curr.getAcceptable())  // Scribble ensures all a are input or all are output
		{
			EndpointState succ = curr.accept(a);
			
			MethodBuilder mb = this.cb.newMethod();
			setSendHeaderWithoutReturnType(apigen, a, mb);
			setNextSocketReturnType(this.apigen, mb, succ);
			if (a.mid.isOp())
			{
				this.cb.addImports(getOpsPackageName() + ".*");  // FIXME: repeated
			}

			if (a.mid.isOp())
			{	
				List<String> args = getSendPayloadArgs(a);
				String body = JavaBuilder.SUPER + ".writeScribMessage(" + ROLE_PARAM + ", " + getSessionApiOpConstant(a.mid);
				if (!a.payload.isEmpty())
				{
					body += ", " + args.stream().collect(Collectors.joining(", "));
				}
				body += ");\n";
				mb.addBodyLine(body);
			}
			else //if (a.mid.isMessageSigName())
			{	
				final String MESSAGE_PARAM = "m";  // FIXME: factor out

				mb.addBodyLine(JavaBuilder.SUPER + ".writeScribMessage(" + ROLE_PARAM + ", " + MESSAGE_PARAM + ");");
			}

			addReturnNextSocket(mb, succ);
		}
	}

	private static List<String> getSendPayloadArgs(IOAction a)
	{
		final String ARG_PREFIX = "arg";

		return IntStream.range(0, a.payload.elems.size()).mapToObj((i) -> ARG_PREFIX + i++).collect(Collectors.toList());  // FIXME: factor out with params
	}

	public static void setSendHeaderWithoutReturnType(StateChannelApiGenerator apigen, IOAction a, MethodBuilder mb)
	{
		final String ROLE_PARAM = "role";
		Module main = apigen.getMainModule();  // FIXME: main not necessarily the right module?

		mb.setName("send");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException");
		mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM);  // More params added below
		if (a.mid.isOp())
		{
			addSendOpParams(apigen, mb, main, a);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			addSendMessageSigNameParams(mb, msd);
		}
	}

	protected static void addSendOpParams(StateChannelApiGenerator apigen, MethodBuilder mb, Module main, IOAction a)
	{
		List<String> args = getSendPayloadArgs(a);
		mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " op");  // opClass -- op param not actually used in body
		if (!a.payload.isEmpty())
		{
			Iterator<String> as = args.iterator();
			for (PayloadType<?> pt : a.payload.elems)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // FIXME: might not belong to main module  // TODO: if not DataType
				mb.addParameters(dtd.extName + " " + as.next());
			}
		}
	}

	protected static void addSendMessageSigNameParams(MethodBuilder mb, MessageSigNameDecl msd)
	{
		final String MESSAGE_PARAM = "m";

		mb.addParameters(msd.extName + " " + MESSAGE_PARAM);
	}
}
