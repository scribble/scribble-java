package org.scribble.codegen.java;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.PayloadType;

public class SendSocketBuilder extends ScribSocketBuilder
{
	public SendSocketBuilder(StateChannelApiGenerator apigen, EndpointState curr)
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
		super.addImports();
		this.cb.addImports(getOpsPackageName() + ".*");
	}

	// A method for each successor state
	//private void addSendMethods(ClassBuilder cb, EndpointState curr)
	@Override
	protected void addMethods()
	{
		final String ROLE_PARAM = "role";
		final String ARG_PREFIX = "arg";

		Module main = this.apigen.getMainModule();
		for (IOAction a : curr.getAcceptable())  // Scribble ensures all a are input or all are output
		{
			EndpointState succ = curr.accept(a);
			
			MethodBuilder mb = this.cb.newMethod("send");
			mb.addModifiers(ClassBuilder.PUBLIC);
			setNextSocketReturnType(mb, succ);
			mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM);  // More params added below
			mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException");
			
			if (a.mid.isOp())
			{	
				List<String> args =
						IntStream.range(0, a.payload.elems.size()).mapToObj((i) -> ARG_PREFIX + i++).collect(Collectors.toList());
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

				String body = ClassBuilder.SUPER + ".writeScribMessage(" + ROLE_PARAM + ", " + getSessionApiOpConstant(a.mid);
				if (!a.payload.isEmpty())
				{
					body += ", " + args.stream().collect(Collectors.joining(", "));
				}
				body += ");\n";
				mb.addBodyLine(body);
			}
			else //if (a.mid.isMessageSigName())
			{	
				final String MESSAGE_PARAM = "m";

				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module  // FIXME: factor out?
				mb.addParameters(msd.extName + " " + MESSAGE_PARAM);
				mb.addBodyLine(ClassBuilder.SUPER + ".writeScribMessage(" + ROLE_PARAM + ", " + MESSAGE_PARAM + ");");
			}

			addReturnNextSocket(mb, succ);
		}
	}
}
