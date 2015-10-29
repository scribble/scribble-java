package org.scribble.codegen.java;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Job;
import org.scribble.visit.Projector;

// TODO: "wildcard" unary async: op doesn't matter -- for branch-receive op "still needed" to cast to correct branch state
// TODO: "functional state interfaces", e.g. for smtp ehlo and quit actions

// FIXME: selector(?) hanging on runtimeexception (from message formatter)
// FIXME: consume futures before wrap/reconnect
public class EndpointApiGenerator
{
	private static final String SESSIONENDPOINT_CLASS = "org.scribble.net.session.SessionEndpoint";
	private static final String SCRIBMESSAGE_CLASS = "org.scribble.net.ScribMessage";
	private static final String SCRIBBLERUNTIMEEXCEPTION_CLASS = "org.scribble.main.ScribbleRuntimeException";
	private static final String BUFF_CLASS = "org.scribble.net.Buff";
	private static final String OPENUM_INTERFACE = "org.scribble.net.session.OpEnum";
	private static final String SCRIBFUTURE_CLASS = "org.scribble.net.ScribFuture";

	private static final String INITSOCKET_CLASS = "org.scribble.net.scribsock.InitSocket";
	private static final String SENDSOCKET_CLASS = "org.scribble.net.scribsock.SendSocket";
	private static final String RECEIVESOCKET_CLASS = "org.scribble.net.scribsock.ReceiveSocket";
	private static final String BRANCHSOCKET_CLASS = "org.scribble.net.scribsock.BranchSocket";
	private static final String CASESOCKET_CLASS = "org.scribble.net.scribsock.CaseSocket";
	private static final String ENDSOCKET_CLASS = "org.scribble.net.scribsock.EndSocket";
	
	private static final String BUFF_VAL = "val";
	private static final String SCRIBSOCKET_SE_FIELD = ClassBuilder.THIS + ".se";
	private static final String SCRIBMESSAGE_OP_FIELD = "op";
	private static final String SCRIBMESSAGE_PAYLOAD_FIELD = "payload";

	private static final String RECEIVE_OP_PARAM = "op";
	private static final String RECEIVE_MESSAGE_PARAM = "m";
	private static final String RECEIVE_ARG_PREFIX = "arg";

	private static final String CASE_OP_FIELD = "op";
	private static final String CASE_OP_PARAM = CASE_OP_FIELD;
	private static final String CASE_MESSAGE_FIELD = "m";
	private static final String CASE_MESSAGE_PARAM = CASE_MESSAGE_FIELD;
	private static final String CASE_ARG_PREFIX = "arg";
	
	private final Job job;
	private final GProtocolName gpn;  // full name
	private final LProtocolName lpn;

	private int counter = 1;

	private String root = null;
	Map<EndpointState, String> classNames = new HashMap<>();
	private Map<String, ClassBuilder> classes = new HashMap<>();  // class name key
	private Map<String, InterfaceBuilder> ifaces = new HashMap<>();  // FIXME: integrate with above

	public EndpointApiGenerator(Job job, GProtocolName fullname, Role role)
	{
		this.job = job;
		this.gpn = fullname;
		this.lpn = Projector.projectFullProtocolName(fullname, role);

		EndpointState init = job.getContext().getEndpointGraph(fullname, role).init;
		generateClassNames(init);
		constructClasses(init);
	}
	
	public Map<String, String> generateClasses()
	{
		Map<String, String> map = new HashMap<String, String>();
		for (String s : this.classes.keySet())
		{
			String path = SessionApiGenerator.getPackageName(this.gpn).replace('.', '/') + '/' + s + ".java";
			map.put(path, this.classes.get(s).generate());
		}
		for (String s : this.ifaces.keySet())
		{
			String path = SessionApiGenerator.getPackageName(this.gpn).replace('.', '/') + '/' + s + ".java";
			map.put(path, this.ifaces.get(s).generate());
		}
		return map;
	}

	private void constructClasses(EndpointState curr)
	{
		if (curr.isTerminal())
		{
			return;  // className is "void" -- terminal states are implicitly inlined into the actions for each case (send, receive, etc) by returning void instead of an explicit terminal state
		}
		String className = this.classNames.get(curr);
		if (this.classes.containsKey(className))
		{
			return;
		}
		this.classes.put(className, constructClass(getSocketClass(curr), this.classNames.get(curr), curr));
		for (EndpointState succ : curr.getSuccessors())
		{
			constructClasses(succ);
		}

		// Depends on the above being done first (for this.root)
		String init = this.lpn.getSimpleName().toString() + "_" + 0;  // FIXME: factor out with newClassName
		this.classes.put(init, constructInitClass(init));
	}

	// Pre: not a terminal state (i.e. className is not void)
	private ClassBuilder constructInitClass(String className)
	{
		final String SESSIONENDPOINT_PARAM = "se";

		ClassBuilder cb = new ClassBuilder();
		cb.setName(className);
		cb.setPackage(getPackageName());
		cb.setSuperClass(INITSOCKET_CLASS);
		cb.addModifiers(ClassBuilder.PUBLIC);
		
		MethodBuilder ctor = cb.newConstructor(SESSIONENDPOINT_CLASS + " " + SESSIONENDPOINT_PARAM);
		ctor.addModifiers(ClassBuilder.PUBLIC);
		ctor.addBodyLine(ClassBuilder.SUPER + "(" + SESSIONENDPOINT_PARAM + ");");
		
		MethodBuilder mb = cb.newMethod("init");
		mb.setReturn(this.root);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb.addBodyLine(ClassBuilder.SUPER + ".use();");  // Factor out
		//mb.addBodyLine(SCRIBSOCKET_SE_FIELD + ".init();");  // Factor out
		addReturnNextSocket(mb, this.root);

		return cb;
	}

	private ClassBuilder constructClass(String superc, String className, EndpointState curr)
	{
		ClassBuilder cb = constructClassExceptMethods(superc, className);
		addMethods(cb, curr);
		return cb;
	}

	private ClassBuilder constructClassExceptMethods(String superc, String className)
	{
		ClassBuilder cb = new ClassBuilder();
		cb.setName(className);
		cb.setPackage(getPackageName());
		cb.addModifiers(ClassBuilder.PUBLIC);
		cb.setSuperClass(superc);
		addImports(cb);
		addConstructor(cb, className);
		return cb;
	}

	private void addImports(ClassBuilder cb)
	{
		cb.addImports("java.io.IOException");
		cb.addImports("java.util.concurrent.CompletableFuture");
		cb.addImports("java.util.concurrent.ExecutionException");
		cb.addImports(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getSessionClassName(this.gpn));
	}
	
	private MethodBuilder addConstructor(ClassBuilder cb, String className)
	{
		final String SESSIONENDPOINT_PARAM = "se";

		MethodBuilder ctor = cb.newConstructor(SESSIONENDPOINT_CLASS + " " + SESSIONENDPOINT_PARAM);
		ctor.addModifiers(ClassBuilder.PROTECTED);
		ctor.addBodyLine(ClassBuilder.SUPER + "(" + SESSIONENDPOINT_PARAM + ");");
		return ctor;
	}
	
	private void addMethods(ClassBuilder cb, EndpointState curr)
	{
		String st = getSocketClass(curr);
		switch (st)
		{
			case SENDSOCKET_CLASS:
			{
				addSendMethods(cb, curr);
				break;
			}
			case RECEIVESOCKET_CLASS:
			{
				addReceiveMethods(cb, curr);
				break;
			}
			case BRANCHSOCKET_CLASS:
			{
				addBranchMethod(cb, curr);
				break;
			}
			default:
			{
				throw new RuntimeException("TODO: " + st);
			}
		}
	}

	// A method for each successor state
	private void addSendMethods(ClassBuilder cb, EndpointState curr)
	{
		final String ROLE_PARAM = "role";
		final String ARG_PREFIX = "arg";

		Module main = this.job.getContext().getMainModule();
		for (IOAction a : curr.getAcceptable())  // Scribble ensures all a are input or all are output
		{
			String nextClass = this.classNames.get(curr.accept(a));
			
			MethodBuilder mb = cb.newMethod("send");
			mb.addModifiers(ClassBuilder.PUBLIC);
			mb.setReturn(nextClass);
			mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM);  // More params added below
			mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException");
			
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

			addReturnNextSocket(mb, nextClass);
		}
	}
	
	private static void addReturnNextSocket(MethodBuilder mb, String nextClass)
	{
		if (isTerminalClassName(nextClass))
		{
			mb.addBodyLine(SCRIBSOCKET_SE_FIELD + ".setCompleted();");  // Do before the IO action? in case of exception?
			//mb.addBodyLine(ClassBuilder.RETURN + ";");
			//mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + ENDSOCKET_CLASS + "(" + SCRIBSOCKET_SE_FIELD + ");");
		}
		//else
		{
			mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + nextClass + "(" + SCRIBSOCKET_SE_FIELD + ");");
		}
	}

	// Single receive (unary branch -- singleton next state)
	// But generates a set of methods related to this input (regular receive, async with future, async with discard, arrived polling) -- TODO: call-back
	// FIXME: most general async would also allow whole input-only compound statements (choice, recursion) to be bypassed
	private void addReceiveMethods(ClassBuilder cb, EndpointState curr)
	{
		Module main = this.job.getContext().getMainModule();  // FIXME: main not necessarily the right module?

		IOAction a = curr.getAcceptable().iterator().next();
		String nextClass = this.classNames.get(curr.accept(a));
		String opClass = SessionApiGenerator.getOpClassName(a.mid);
		String futureClass = makeFutureClass(cb, main, a);  // Wraps all payload elements as fields (set by future completion)
		// FIXME: problem if package and protocol have the same name

		makeReceiveMethod(cb, main, a, nextClass, opClass);  // [nextClass] receive([opClass] op, Buff<? super T> arg, ...)
		makeAsyncMethod(cb, a, nextClass, opClass, futureClass);  // [nextClass] async([opClass] op, Buff<futureClass> arg)
		makeIsDoneMethod(cb, a);  // boolean isDone()
		makeAsyncDiscardMethod(cb, a, nextClass, opClass, futureClass);  // [nextClass] async([opClass] op)
	}

  // [nextClass] receive([opClass] op, Buff<? super T> arg, ...)
	private void makeReceiveMethod(ClassBuilder cb, Module main, IOAction a, String nextClass, String opClass)
	{
		MethodBuilder mb = makeReceiveHeader(cb, nextClass, a.peer, opClass);
		if (a.mid.isOp())
		{
			addReceiveOpParams(mb, main, a);
			mb.addBodyLine(SCRIBMESSAGE_CLASS + " " + RECEIVE_MESSAGE_PARAM + " = "
						+ ClassBuilder.SUPER + ".readScribMessage(" + getSessionApiRoleConstant(a.peer) + ");");
			addPayloadBuffSetters(main, a, mb);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			addReceiveMessageSigNameParams(mb, a, msd);
			mb.addBodyLine(SCRIBMESSAGE_CLASS + " " + RECEIVE_MESSAGE_PARAM + " = "
						+ ClassBuilder.SUPER + ".readScribMessage(" + getSessionApiRoleConstant(a.peer) + ");");
			mb.addBodyLine(RECEIVE_ARG_PREFIX + "." + BUFF_VAL + " = (" + msd.extName + ") " + RECEIVE_MESSAGE_PARAM + ";");
		}
		addReturnNextSocket(mb, nextClass);
	}

  // [nextClass] async([opClass] op, Buff<futureClass> arg)
	private void makeAsyncMethod(ClassBuilder cb, IOAction a, String nextClass, String opClass, String futureClass)
	{
		final String ROLE_PARAM = "role";
		
		MethodBuilder mb = cb.newMethod("async"); 
		// Blurb stuff similar to makeReceiveHeader
		mb.addModifiers(ClassBuilder.PUBLIC);//, ClassBuilder.SYNCHRONIZED);
		mb.setReturn(nextClass);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM);
		mb.addParameters(opClass + " " + RECEIVE_OP_PARAM);
		mb.addParameters(BUFF_CLASS + "<" + futureClass + "> " + RECEIVE_ARG_PREFIX);  // Method for future-buf even if no payload, for sync action
		//mb.addBodyLine(ClassBuilder.SUPER + ".use();");
		//mb2.addBodyLine(ARG_PREFIX + ".val = " + " " + ClassBuilder.SUPER + ".getFuture(" + getPrefixedRoleClassName(a.peer) + ");");
		mb.addBodyLine(RECEIVE_ARG_PREFIX + "." + BUFF_VAL + " = "
					+ ClassBuilder.NEW + " " + futureClass + "(" + ClassBuilder.SUPER + ".getFuture(" + getSessionApiRoleConstant(a.peer) + "));");
		addReturnNextSocket(mb, nextClass);
	}

  // boolean isDone()
	private void makeIsDoneMethod(ClassBuilder cb, IOAction a)
	{
		MethodBuilder mb = cb.newMethod("isDone");
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.setReturn("boolean");
		mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.SUPER + ".isDone(" + getSessionApiRoleConstant(a.peer) + ");");
	}

  // [nextClass] async([opClass] op) -- wrapper for makeAsyncMethod
	private void makeAsyncDiscardMethod(ClassBuilder cb, IOAction a, String nextClass, String opClass, String futureClass)
	{
		final String ROLE_PARAM = "role";
		
		MethodBuilder mb = cb.newMethod("async"); 
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.setReturn(nextClass);
		mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM, opClass + " " + RECEIVE_OP_PARAM);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		if (!isTerminalClassName(nextClass))
		{
			mb.addBodyLine(ClassBuilder.RETURN + " async(" + getSessionApiRoleConstant(a.peer) + ", " + RECEIVE_OP_PARAM + ", " + getGarbageBuff(futureClass) + ");");
		}
	}

	private static MethodBuilder makeReceiveHeader(ClassBuilder cb, String next, Role peer, String opClass)
	{
		final String ROLE_PARAM = "role";

		MethodBuilder mb = cb.newMethod("receive");
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.setReturn(next);
		mb.addParameters(SessionApiGenerator.getRoleClassName(peer) + " " + ROLE_PARAM, opClass + " " + RECEIVE_OP_PARAM);  // More params may be added later (payload-arg/future Buffs)
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException, ExecutionException, InterruptedException");
		return mb;
	}

	// FIXME: main may not be the right module
	private static void addReceiveOpParams(MethodBuilder mb, Module main, IOAction a)
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

	private static void addPayloadBuffSetters(Module main, IOAction a, MethodBuilder mb)
	{
		if (!a.payload.isEmpty())
		{
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)  // Could factor out this loop (arg names) with addReceiveOpParams (as for send)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
				mb.addBodyLine(RECEIVE_ARG_PREFIX + i + "." + BUFF_VAL + " = (" + dtd.extName + ") "
							+ RECEIVE_MESSAGE_PARAM + "." + SCRIBMESSAGE_PAYLOAD_FIELD + "[" + (i++ - 1) +"];");
			}
		}
	}

	private static void addReceiveMessageSigNameParams(MethodBuilder mb, IOAction a, MessageSigNameDecl msd)
	{
		mb.addParameters(BUFF_CLASS + "<? " + ClassBuilder.SUPER + " " + msd.extName + "> " + RECEIVE_ARG_PREFIX);
	}

	private static String makeFutureClass(ClassBuilder cb, Module main, IOAction a)
	{
		final String FUTURE_PARAM = "fut";

		String futureClass = "Future_" + cb.getName();  // Fresh enough? need only one future class per receive (unary receive)

		cb.addImports("java.util.concurrent.CompletableFuture");  // "parent" cb, not the future class

		ClassBuilder future = cb.newClass();
		future.setName(futureClass);
		future.setSuperClass(SCRIBFUTURE_CLASS);
		List<String> types = new LinkedList<>(); 
		if (a.mid.isOp())
		{
			if (!a.payload.isEmpty())
			{
				int i = 1;
				for (PayloadType<?> pt : a.payload.elems)
				{
					String type = main.getDataTypeDecl((DataType) pt).extName;
					types.add(type);
					FieldBuilder f = future.newField("pay" + i++);
					f.setType(type);
					f.addModifiers(ClassBuilder.PUBLIC);
				}
			}
		}
		else
		{
			String type = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName()).extName;
			types.add(type);
			FieldBuilder f = future.newField("msg");
			f.setType(type);
			f.addModifiers(ClassBuilder.PUBLIC);
		}

		MethodBuilder cons = future.newConstructor("CompletableFuture<" + SCRIBMESSAGE_CLASS + "> " + FUTURE_PARAM);
		cons.addModifiers(ClassBuilder.PROTECTED);
		cons.addBodyLine(ClassBuilder.SUPER + "(" + FUTURE_PARAM + ");");

		MethodBuilder sync = future.newMethod("sync");
		sync.addModifiers(ClassBuilder.PUBLIC);
		sync.setReturn(futureClass);
		sync.addExceptions("ExecutionException", "InterruptedException");
		sync.addBodyLine(SCRIBMESSAGE_CLASS + " m = " + ClassBuilder.SUPER + ".get();");
		if (a.mid.isOp())
		{
			if (!a.payload.isEmpty())
			{
				int i = 1;
				for (String type : types)
				{
					sync.addBodyLine(ClassBuilder.THIS + "." + "pay" + i + " = (" + type + ") m.payload[" + (i - 1) + "];");
					i++;
				}
			}
		}
		else
		{
			sync.addBodyLine(ClassBuilder.THIS + "." + "msg" + " = (" + types.get(0) + ") m;");
		}
		sync.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.THIS + ";");

		return futureClass;
	}
	
	private void addBranchMethod(ClassBuilder cb, EndpointState curr)
	{
		final String ROLE_PARAM = "role";
		final String MESSAGE_VAR = "m";
		final String OPENUM_VAR = "openum";
		final String OP = MESSAGE_VAR + "." + SCRIBMESSAGE_OP_FIELD;

		Module main = this.job.getContext().getMainModule();

		String next = constructCaseClass(curr, main);
		String enumClass = this.classNames.get(curr) + "Enum";

		cb.addImports("java.util.concurrent.ExecutionException");
		
		MethodBuilder mb = cb.newMethod("branch");
		mb.setReturn(next);
		mb.addParameters(SessionApiGenerator.getRoleClassName(curr.getAcceptable().iterator().next().peer) + " " + ROLE_PARAM);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException", "ExecutionException", "InterruptedException");
		
		Role peer = curr.getAcceptable().iterator().next().peer;
		mb.addBodyLine(SCRIBMESSAGE_CLASS + " " + MESSAGE_VAR + " = "
				+ ClassBuilder.SUPER + ".readScribMessage(" + getSessionApiRoleConstant(peer) + ");");
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
		mb.addBodyLine(1, "throw " + ClassBuilder.NEW + " RuntimeException(\"Won't get here: \" + " + OP + ");");
		mb.addBodyLine("}");
		mb.addBodyLine(ClassBuilder.RETURN + " "
				+ ClassBuilder.NEW + " " + next + "(" + SCRIBSOCKET_SE_FIELD + ", " + OPENUM_VAR + ", " + MESSAGE_VAR + ");");
		
		EnumBuilder eb = cb.newEnum(enumClass);
		eb.addModifiers(ClassBuilder.PUBLIC);
		eb.addInterfaces(OPENUM_INTERFACE);
		curr.getAcceptable().stream().forEach((a) -> eb.addValues(SessionApiGenerator.getOpClassName(a.mid)));
		

		// Handler branch method
		String ifname = cb.getName() + "_Handler";
		MethodBuilder mb2 = cb.newMethod("branch");
		mb2.addParameters(SessionApiGenerator.getRoleClassName(peer) + " " + ROLE_PARAM);
		//mb2.addParameters("java.util.concurrent.Callable<" + ifname + "> branch");
		mb2.addParameters(ifname + " branch");
		mb2.setReturn(ClassBuilder.VOID);
		mb2.addModifiers(ClassBuilder.PUBLIC);
		mb2.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException", "ExecutionException", "InterruptedException");
		mb2.addBodyLine(SCRIBMESSAGE_CLASS + " " + MESSAGE_VAR + " = "
				+ ClassBuilder.SUPER + ".readScribMessage(" + getSessionApiRoleConstant(peer) + ");");
		first = true;
		for (IOAction a : curr.getAcceptable())
		{
			EndpointState succ = curr.accept(a);
			if (first)
			{
				first = false;
			}
			else
			{
				mb2.addBodyLine("else");
			}
			mb2.addBodyLine("if (" + MESSAGE_VAR + "." + SCRIBMESSAGE_OP_FIELD + ".equals(" + getSessionApiOpConstant(a.mid) + ")) {");
			if (succ.isTerminal())
			{
				mb2.addBodyLine(1, SCRIBSOCKET_SE_FIELD + ".setCompleted();");
			}
			String ln = "branch.receive(";
			//if (!succ.isTerminal())
			{
				 ln += "new " + this.classNames.get(succ) + "(" + SCRIBSOCKET_SE_FIELD + "), ";
			}
			ln += getSessionApiOpConstant(a.mid);
					
			// Based on receive parameters
			if (a.mid.isOp())
			{
				if (!a.payload.isEmpty())
				{
					String buffSuper = ClassBuilder.NEW + " " + BUFF_CLASS + "<>(";
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
				ln += ClassBuilder.NEW + " " + BUFF_CLASS + "<>((" + msd + ") " +  RECEIVE_MESSAGE_PARAM + "." + SCRIBMESSAGE_PAYLOAD_FIELD + "[0])";
			}
				
			ln += ");";
			mb2.addBodyLine(1, ln);
			mb2.addBodyLine("}");
		}
		mb2.addBodyLine("else {");
		mb2.addBodyLine(1, "throw " + ClassBuilder.NEW + " RuntimeException(\"Won't get here: \" + " + OP + ");");
		mb2.addBodyLine("}");

		// Handler interface
		InterfaceBuilder ib = new InterfaceBuilder();
		ib.setPackage(getPackageName());
		ib.addImports("java.io.IOException");
		ib.setName(ifname);
		ib.addModifiers(InterfaceBuilder.PUBLIC);
		for (IOAction a : curr.getAcceptable())
		{
			EndpointState succ = curr.accept(a);
			String nextClass = this.classNames.get(succ);
			String opClass = SessionApiGenerator.getOpClassName(a.mid);

			MethodBuilder mb3 = ib.newMethod("receive");
			mb3.addModifiers(ClassBuilder.PUBLIC);
			mb3.setReturn(InterfaceBuilder.VOID);
			mb3.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException");
			//if (!nextClass.equals(ClassBuilder.VOID))
			//if (!succ.isTerminal())
			{
				mb3.addParameters(nextClass + " schan");
			}
			mb3.addParameters(opClass + " " + RECEIVE_OP_PARAM);  // More params added below

			if (a.mid.isOp())
			{	
				addReceiveOpParams(mb3, main, a);
			}
			else //if (a.mid.isMessageSigName())
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				addReceiveMessageSigNameParams(mb3, a, msd);
			}
		}
		this.ifaces.put(ifname, ib);
	}

	// FIXME: factor with regular receive
	private String constructCaseClass(EndpointState curr, Module main)
	{
		String branchName = this.classNames.get(curr);  // Name of "parent" branch class (curr state is the branch state)
		String enumClassName = branchName + "." + branchName + "Enum";
		//String className = newClassName();  // Name of branch-receive class
		String className = branchName + "_Cases";

		ClassBuilder cb = constructClassExceptMethods(CASESOCKET_CLASS, className);
		
		MethodBuilder ctor = cb.getConstructors().iterator().next();
		ctor.addParameters(enumClassName + " " + CASE_OP_PARAM, SCRIBMESSAGE_CLASS + " " + CASE_MESSAGE_PARAM);
		ctor.addBodyLine(ClassBuilder.THIS + "." + CASE_OP_FIELD + " = " + CASE_OP_PARAM + ";");
		ctor.addBodyLine(ClassBuilder.THIS + "." + CASE_MESSAGE_FIELD + " = " + CASE_MESSAGE_PARAM + ";");

		FieldBuilder fb1 = cb.newField(CASE_OP_FIELD);  // The op enum, for convenient switch/if/etc by user (correctly derived by code generation from the received ScribMessage)
		fb1.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.FINAL);
		fb1.setType(enumClassName);
		
		FieldBuilder fb2 = cb.newField(CASE_MESSAGE_FIELD);  // The received ScribMessage (branch-check checks the user-selected receive op against the ScribMessage op)
		fb2.addModifiers(ClassBuilder.PRIVATE, ClassBuilder.FINAL);
		fb2.setType(SCRIBMESSAGE_CLASS);

		for (IOAction a : curr.getAcceptable())
		{
			EndpointState succ = curr.accept(a);
			String nextClass = this.classNames.get(succ);
			String opClass = SessionApiGenerator.getOpClassName(a.mid);

			addCaseReceiveMethod(cb, main, a, nextClass, opClass);
			addCaseReceiveDiscardMethod(cb, main, a, nextClass, opClass);
		}

		this.classes.put(className, cb);
		return className;
	}

	private void addCaseReceiveMethod(ClassBuilder cb, Module main, IOAction a, String nextClass, String opClass)
	{
		MethodBuilder mb = makeReceiveHeader(cb, nextClass, a.peer, opClass);
		if (a.mid.isOp())
		{
			addReceiveOpParams(mb, main, a);
			mb.addBodyLine(ClassBuilder.SUPER + ".use();");
			addBranchCheck(getSessionApiOpConstant(a.mid), mb, CASE_MESSAGE_FIELD);
			addPayloadBuffSetters(main, a, mb);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			addReceiveMessageSigNameParams(mb, a, msd);
			mb.addBodyLine(ClassBuilder.SUPER + ".use();");
			addBranchCheck(getSessionApiOpConstant(a.mid), mb, CASE_MESSAGE_FIELD);
			mb.addBodyLine(CASE_ARG_PREFIX + "." + BUFF_VAL+ " = (" + msd.extName + ") " + CASE_MESSAGE_FIELD + ";");
		}
		addReturnNextSocket(mb, nextClass);
	}

	private void addCaseReceiveDiscardMethod(ClassBuilder cb, Module main, IOAction a, String nextClass, String opClass)
	{
		if (!a.payload.isEmpty() || a.mid.isMessageSigName())
		{
			MethodBuilder mb = makeReceiveHeader(cb, nextClass, a.peer, opClass);
			String ln = (isTerminalClassName(nextClass)) ? "" : ClassBuilder.RETURN + " ";
			ln += "receive(" + getSessionApiRoleConstant(a.peer) + ", " + CASE_OP_PARAM + ", ";
			if (a.mid.isOp())
			{
				ln += a.payload.elems.stream()
							 .map((pt) -> getGarbageBuff(main.getDataTypeDecl(((DataType) pt)).extName)).collect(Collectors.joining(", ")) + ");";
			}
			else
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // Factor out? (send/receive/branchreceive/...)
				ln += getGarbageBuff(msd.extName) + ");";
			}
			mb.addBodyLine(ln);
		}
	}

	private static void addBranchCheck(String opClassName, MethodBuilder mb, String messageField)
	{
		String op = ClassBuilder.THIS + "." + messageField + "." + SCRIBMESSAGE_OP_FIELD;
		mb.addBodyLine("if (!" + op + ".equals(" + opClassName + ")) {");
		mb.addBodyLine(1, "throw " + ClassBuilder.NEW + " " + SCRIBBLERUNTIMEEXCEPTION_CLASS + "(\"Wrong branch, received: \" + " + op + ");");
		mb.addBodyLine("}");
	}
	
	private static String getGarbageBuff(String futureClass)
	{
		//return ClassBuilder.NEW + " " + BUFF_CLASS + "<>()";  // Makes a trash Buff every time, but clean -- would be more efficient to generate the code to spawn the future without buff-ing it (partly duplicate of the normal receive generated code) 
		return "(" + BUFF_CLASS + "<" + futureClass + ">) " + SCRIBSOCKET_SE_FIELD + ".gc";  // FIXME: generic cast warning (this.ep.gc is Buff<?>) -- also retains unnecessary reference to the last created garbage future (but allows no-arg receive/async to be generated as simple wrapper call)
	}

	private String getPackageName() // Java output package (not Scribble package)
	{
		return SessionApiGenerator.getPackageName(this.gpn);
	}
	
	// Not fully qualified, just Session API class prefix
	// The constant singleton value of this type in the Session API (which is the same "name" as the class)
	private String getSessionApiRoleConstant(Role role)
	{
		return SessionApiGenerator.getSessionClassName(this.gpn) + "." + role.toString();
	}
	
	// Not fully qualified, just Session API class prefix
	// The constant singleton value of this type in the Session API (which is the same "name" as the class)
	private String getSessionApiOpConstant(MessageId<?> mid)
	{
		return SessionApiGenerator.getSessionClassName(this.gpn) + "." + SessionApiGenerator.getOpClassName(mid);
	}
	
	private String getSocketClass(EndpointState ps)
	{
		/*if (ps.isTerminal())
		{
			return ENDSOCKET_CLASS;
		}*/
		Set<IOAction> as = ps.getAcceptable();
		IOAction a = as.iterator().next();
		if (a instanceof Send)
		{
			return SENDSOCKET_CLASS;
		}
		else if (a instanceof Receive)
		{
			return (as.size() > 1) ? BRANCHSOCKET_CLASS : RECEIVESOCKET_CLASS;
		}
		else
		{
			throw new RuntimeException("TODO");
		}
	}
	
	private void generateClassNames(EndpointState ps)
	{
		if (this.classNames.containsKey(ps))
		{
			return;
		}
		if (ps.isTerminal())  // Terminal states inlined implicitly into terminal actions by returning void (instead of explicit terminal state)
		{
			//this.classNames.put(ps, ClassBuilder.VOID);
			this.classNames.put(ps, ENDSOCKET_CLASS);
			return;
		}
		String c = newClassName();
		this.classNames.put(ps, c);
		if (this.root == null)
		{
			this.root = c;
		}
		for (EndpointState succ : ps.getSuccessors())
		{
			generateClassNames(succ);
		}
	}
	
	private String newClassName()
	{
		String sn = this.lpn.getSimpleName().toString();
		return sn + "_" + nextCount();
	}
	
	private static boolean isTerminalClassName(String n)
	{
		//return n.equals(ClassBuilder.VOID);
		return n.equals(ENDSOCKET_CLASS);
	}
	
	private int nextCount()
	{
		return this.counter++;
	}
}
