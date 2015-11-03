package org.scribble.codegen.java;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.PayloadType;

public class ReceiveSocketBuilder extends ScribSocketBuilder
{
	public ReceiveSocketBuilder(StateChannelApiGenerator apigen, EndpointState curr)
	{
		super(apigen, curr);
	}

	@Override
	protected String getSuperClassType()
	{
		return RECEIVESOCKET_CLASS + "<" + getSessionClassName() + ", " + getSelfClassName() + ">";
	}

	@Override
	protected void addImports()
	{
		super.addImports();
		this.cb.addImports(getOpsPackageName() + ".*");
	}

	// Single receive (unary branch -- singleton next state)
	// But generates a set of methods related to this input (regular receive, async with future, async with discard, arrived polling) -- TODO: call-back
	// FIXME: most general async would also allow whole input-only compound statements (choice, recursion) to be bypassed
	//private void addReceiveMethods(ClassBuilder cb, EndpointState curr)
	@Override
	protected void addMethods()
	{
		Module main = this.apigen.getMainModule();  // FIXME: main not necessarily the right module?

		IOAction a = curr.getAcceptable().iterator().next();
		//String nextClass = this.apigen.getSocketClassName(curr.accept(a));
		EndpointState succ = curr.accept(a);
		ClassBuilder futureClass = new InputFutureBuilder(this.apigen, this.cb, a).build();  // Wraps all payload elements as fields (set by future completion)
		// FIXME: problem if package and protocol have the same name -- still?
		this.apigen.addClass(futureClass);

		makeReceiveMethod(main, a, succ);  // [nextClass] receive([opClass] op, Buff<? super T> arg, ...)
		makeAsyncMethod(a, succ, futureClass.getName());  // [nextClass] async([opClass] op, Buff<futureClass> arg)
		makeIsDoneMethod(a);  // boolean isDone()
		makeAsyncDiscardMethod(a, succ, futureClass.getName());  // [nextClass] async([opClass] op)
	}

  // [nextClass] receive([opClass] op, Buf<? super T> arg, ...)
	//private void makeReceiveMethod(ClassBuilder cb, Module main, IOAction a, String nextClass, String opClass)
	private void makeReceiveMethod(Module main, IOAction a, EndpointState succ)
	{
		MethodBuilder mb = makeReceiveHeader(a, succ);
		if (a.mid.isOp())
		{
			addReceiveOpParams(mb, main, a);
			String ln = a.payload.isEmpty() ? "" : StateChannelApiGenerator.SCRIBMESSAGE_CLASS + " " + RECEIVE_MESSAGE_PARAM + " = ";
			ln += ClassBuilder.SUPER + ".readScribMessage(" + getSessionApiRoleConstant(a.peer) + ");";
			mb.addBodyLine(ln);
			addPayloadBuffSetters(main, a, mb);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			addReceiveMessageSigNameParams(mb, a, msd);
			mb.addBodyLine(StateChannelApiGenerator.SCRIBMESSAGE_CLASS + " " + RECEIVE_MESSAGE_PARAM + " = "
						+ ClassBuilder.SUPER + ".readScribMessage(" + getSessionApiRoleConstant(a.peer) + ");");
			mb.addBodyLine(RECEIVE_ARG_PREFIX + "." + BUFF_VAL_FIELD + " = (" + msd.extName + ") " + RECEIVE_MESSAGE_PARAM + ";");
		}
		addReturnNextSocket(mb, succ);
	}

	private MethodBuilder makeReceiveHeader(IOAction a, EndpointState succ)
	{
		final String ROLE_PARAM = "role";
		String opClass = SessionApiGenerator.getOpClassName(a.mid);

		MethodBuilder mb = cb.newMethod("receive");
		mb.addModifiers(ClassBuilder.PUBLIC);
		setNextSocketReturnType(mb, succ);
		mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM, opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);  // More params may be added later (payload-arg/future Buffs)
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");
		//, "ExecutionException", "InterruptedException");
		return mb;
	}

  // [nextClass] async([opClass] op, Buf<? super futureClass> arg)
	private void makeAsyncMethod(IOAction a, EndpointState succ, String futureClass)
	{
		final String ROLE_PARAM = "role";
		String opClass = SessionApiGenerator.getOpClassName(a.mid);
		
		MethodBuilder mb = this.cb.newMethod("async"); 
		// Blurb stuff similar to makeReceiveHeader
		mb.addModifiers(ClassBuilder.PUBLIC);//, ClassBuilder.SYNCHRONIZED);
		setNextSocketReturnType(mb, succ);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM);
		mb.addParameters(opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);
		mb.addParameters(BUFF_CLASS + "<" + futureClass + "> " + RECEIVE_ARG_PREFIX);  // Method for future-buf even if no payload, for sync action
		//mb.addBodyLine(ClassBuilder.SUPER + ".use();");
		//mb2.addBodyLine(ARG_PREFIX + ".val = " + " " + ClassBuilder.SUPER + ".getFuture(" + getPrefixedRoleClassName(a.peer) + ");");
		mb.addBodyLine(RECEIVE_ARG_PREFIX + "." + BUFF_VAL_FIELD + " = "
					+ ClassBuilder.NEW + " " + futureClass + "(" + ClassBuilder.SUPER + ".getFuture(" + getSessionApiRoleConstant(a.peer) + "));");
		addReturnNextSocket(mb, succ);
	}

  // [nextClass] async([opClass] op) -- wrapper for makeAsyncMethod
	private void makeAsyncDiscardMethod(IOAction a, EndpointState succ, String futureClass)
	{
		final String ROLE_PARAM = "role";
		String opClass = SessionApiGenerator.getOpClassName(a.mid);
		
		MethodBuilder mb = this.cb.newMethod("async"); 
		mb.addAnnotations("@SuppressWarnings(\"unchecked\")");  // To cast the generic garbage buf
		mb.addModifiers(ClassBuilder.PUBLIC);
		setNextSocketReturnType(mb, succ);
		mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM, opClass + " " + StateChannelApiGenerator.RECEIVE_OP_PARAM);
		mb.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb.addBodyLine(ClassBuilder.RETURN + " async(" + getSessionApiRoleConstant(a.peer) + ", " + StateChannelApiGenerator.RECEIVE_OP_PARAM + ", " + getGarbageBuf(futureClass) + ");");
	}

  // boolean isDone()
	private void makeIsDoneMethod(IOAction a)
	{
		MethodBuilder mb = cb.newMethod("isDone");
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.setReturn("boolean");
		mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.SUPER + ".isDone(" + getSessionApiRoleConstant(a.peer) + ");");
	}

	// FIXME: main may not be the right module
	protected static void addReceiveOpParams(MethodBuilder mb, Module main, IOAction a)
	{
		if (!a.payload.isEmpty())
		{
			String buffSuper = BUFF_CLASS + "<? " + ClassBuilder.SUPER + " ";
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
				mb.addParameters(buffSuper + dtd.extName + "> " + RECEIVE_ARG_PREFIX + i++);
			}
		}
	}

	protected static void addPayloadBuffSetters(Module main, IOAction a, MethodBuilder mb)
	{
		if (!a.payload.isEmpty())
		{
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)  // Could factor out this loop (arg names) with addReceiveOpParams (as for send)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
				mb.addBodyLine(RECEIVE_ARG_PREFIX + i + "." + BUFF_VAL_FIELD + " = (" + dtd.extName + ") "
							+ RECEIVE_MESSAGE_PARAM + "." + SCRIBMESSAGE_PAYLOAD_FIELD + "[" + (i++ - 1) +"];");
			}
		}
	}

	protected static void addReceiveMessageSigNameParams(MethodBuilder mb, IOAction a, MessageSigNameDecl msd)
	{
		mb.addParameters(BUFF_CLASS + "<? " + ClassBuilder.SUPER + " " + msd.extName + "> " + RECEIVE_ARG_PREFIX);
	}
}
