package org.scribble.codegen.java;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.scribble.visit.JobContext;
import org.scribble.visit.Projector;

public class EndpointApiGenerator
{
	public static final String SESSIONENDPOINT_CLASS = "org.scribble.net.session.SessionEndpoint";
	public static final String SCRIBMESSAGE_CLASS = "org.scribble.net.ScribMessage";
	public static final String SCRIBBLERUNTIMEEXCEPTION_CLASS = "org.scribble.main.ScribbleRuntimeException";
	public static final String BUFF_CLASS = "org.scribble.net.Buff";
	public static final String BUFF_VAL = "val";
	public static final String OPENUM_INTERFACE = "org.scribble.net.session.OpEnum";
	public static final String SCRIBFUTURE_CLASS = "org.scribble.net.ScribFuture";

	public static final String INITSOCKET_CLASS = "org.scribble.net.scribsock.InitSocket";
	public static final String SENDSOCKET_CLASS = "org.scribble.net.scribsock.SendSocket";
	public static final String RECEIVESOCKET_CLASS = "org.scribble.net.scribsock.ReceiveSocket";
	public static final String BRANCHSOCKET_CLASS = "org.scribble.net.scribsock.BranchSocket";
	public static final String BRANCHRECEIVESOCKET_CLASS = "org.scribble.net.scribsock.BranchReceiveSocket";
	
	private static final String SCRIBSOCKET_EP_FIELD = ClassBuilder.THIS + ".ep";
	
	private final Job job;
	private final GProtocolName gpn;  // full name
	private final LProtocolName lpn;

	private int counter = 1;

	private String root = null;
	Map<EndpointState, String> classNames = new HashMap<>();
	private Map<String, ClassBuilder> classes = new HashMap<>();  // class name key

	private static String RECEIVE_OP_PARAM = "op";
	private static String RECEIVE_MESSAGE_PARAM = "m";
	private static String RECEIVE_ARG_PREFIX = "arg";

	private static final String SCRIBMESSAGE_PAYLOAD_FIELD = "payload";

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
		//mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + this.root + "(" + SCRIBSOCKET_EP_FIELD + ");");
		makeReturnNextSocket(mb, this.root);

		return cb;
	}

	private ClassBuilder constructClass(String superc, String className, EndpointState curr)
	{
		ClassBuilder cb = constructClassExceptMethods(superc, className, curr);
		addMethods(cb, curr);
		return cb;
	}

	private ClassBuilder constructClassExceptMethods(String superc, String className, EndpointState curr)
	{
		ClassBuilder cb = new ClassBuilder();
		cb.setName(className);
		cb.setPackage(getPackageName());
		cb.addModifiers(ClassBuilder.PUBLIC);
		cb.setSuperClass(superc);
		addImports(cb, curr);
		addConstructor(cb, className);
		return cb;
	}

	private void addImports(ClassBuilder cb, EndpointState ps)
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
			EndpointState succ = curr.accept(a);
			String next = this.classNames.get(succ);
			
			MethodBuilder mb = cb.newMethod("send");
			mb.setReturn(next);
			mb.addModifiers(ClassBuilder.PUBLIC);
			mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException");
			
			if (a.mid.isOp())
			{	
				String opref = getPrefixedOpClassName(a.mid);
				List<String> args = new LinkedList<>();

				mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM);
				mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " op");  // Not used in body
				if (!a.payload.isEmpty())
				{
					int i = 1;
					for (PayloadType<?> pt : a.payload.elems)
					{
						DataType dt = (DataType) pt;  // TODO: if not DataType
						DataTypeDecl dtd = main.getDataTypeDecl(dt);  // FIXME: might not belong to main module
						String arg = ARG_PREFIX + i++;
						args.add(arg);
						mb.addParameters(dtd.extName + " " + arg);
					}
				}

				String body = ClassBuilder.SUPER + ".writeScribMessage(" + ROLE_PARAM + ", " + opref;
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

				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM, msd.extName + " " + MESSAGE_PARAM);
				mb.addBodyLine(ClassBuilder.SUPER + ".writeScribMessage(" + ROLE_PARAM + ", " + MESSAGE_PARAM + ");");
			}
			makeReturnNextSocket(mb, next);
		}
	}
	
	private static boolean isTerminalClassName(String n)
	{
		return n.equals(ClassBuilder.VOID);
	}
	
	private static void makeReturnNextSocket(MethodBuilder mb, String nextClass)
	{
		if (isTerminalClassName(nextClass))
		{
			mb.addBodyLine(SCRIBSOCKET_EP_FIELD + ".setCompleted();");  // Do before the IO action? in case of exception?
			mb.addBodyLine(ClassBuilder.RETURN + ";");
		}
		else
		{
			mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + nextClass + "(" + SCRIBSOCKET_EP_FIELD + ");");
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
		MethodBuilder mb = makeReceiveHeader(cb, nextClass, opClass);
		if (a.mid.isOp())
		{
			addReceiveOpParams(mb, main, a);
			mb.addBodyLine(SCRIBMESSAGE_CLASS + " " + RECEIVE_MESSAGE_PARAM + " = "
						+ ClassBuilder.SUPER + ".readScribMessage(" + getPrefixedRoleClassName(a.peer) + ");");
			addPayloadBuffSetters(main, a, mb);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			addReceiveMessageSigNameParams(mb, a, msd);
			mb.addBodyLine(SCRIBMESSAGE_CLASS + " " + RECEIVE_MESSAGE_PARAM + " = "
						+ ClassBuilder.SUPER + ".readScribMessage(" + getPrefixedRoleClassName(a.peer) + ");");
			mb.addBodyLine(RECEIVE_ARG_PREFIX + "." + BUFF_VAL + " = (" + msd.extName + ") " + RECEIVE_MESSAGE_PARAM + ";");
		}
		makeReturnNextSocket(mb, nextClass);
	}

  // [nextClass] async([opClass] op, Buff<futureClass> arg)
	private void makeAsyncMethod(ClassBuilder cb, IOAction a, String nextClass, String opClass, String futureClass)
	{
		MethodBuilder mb = cb.newMethod("async"); 
		// Blurb stuff similar to makeReceiveHeader
		mb.addModifiers(ClassBuilder.PUBLIC);//, ClassBuilder.SYNCHRONIZED);
		mb.setReturn(nextClass);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb.addParameters(opClass + " " + RECEIVE_OP_PARAM);
		mb.addParameters(BUFF_CLASS + "<" + futureClass + "> " + RECEIVE_ARG_PREFIX);  // Method for future-buf even if no payload, for sync action
		mb.addBodyLine(ClassBuilder.SUPER + ".use();");
		//mb2.addBodyLine(ARG_PREFIX + ".val = " + " " + ClassBuilder.SUPER + ".getFuture(" + getPrefixedRoleClassName(a.peer) + ");");
		mb.addBodyLine(RECEIVE_ARG_PREFIX + "." + BUFF_VAL + " = "
					+ ClassBuilder.NEW + " " + futureClass + "(" + ClassBuilder.SUPER + ".getFuture(" + getPrefixedRoleClassName(a.peer) + "));");
		makeReturnNextSocket(mb, nextClass);
	}

  // boolean isDone()
	private void makeIsDoneMethod(ClassBuilder cb, IOAction a)
	{
		MethodBuilder mb = cb.newMethod("isDone");
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.setReturn("boolean");
		mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.SUPER + ".isDone(" + getPrefixedRoleClassName(a.peer) + ");");
	}

  // [nextClass] async([opClass] op) -- wrapper for makeAsyncMethod
	private static void makeAsyncDiscardMethod(ClassBuilder cb, IOAction a, String nextClass, String opClass, String futureClass)
	{
		MethodBuilder mb = cb.newMethod("async"); 
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.setReturn(nextClass);
		mb.addParameters(opClass + " " + RECEIVE_OP_PARAM);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		if (!isTerminalClassName(nextClass))
		{
			mb.addBodyLine(ClassBuilder.RETURN + " async(" + RECEIVE_OP_PARAM + ", " + getGarbageBuff(futureClass) + ");");
		}
	}

	private static MethodBuilder makeReceiveHeader(ClassBuilder cb, String next, String opClass)
	{
		MethodBuilder mb = cb.newMethod("receive");
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.setReturn(next);
		mb.addParameters(opClass + " " + RECEIVE_OP_PARAM);  // More params may be added later (payload-arg/future Buffs)
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
		ClassBuilder future = cb.newClass();
		cb.addImports("java.util.concurrent.CompletableFuture");  // cb, not future
		String futureClass = "Future_" + cb.getName();  // FIXME: fresh
		future.setName(futureClass);
		future.setSuperClass(SCRIBFUTURE_CLASS);
		if (!a.payload.isEmpty())
		{
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)
			{
				String type = main.getDataTypeDecl((DataType) pt).extName;
				FieldBuilder f = future.newField("pay" + i);
				f.setType(type);
				f.addModifiers(ClassBuilder.PUBLIC);
				i++;
			}
		}
		final String FUTURE_PARAM = "fut";
		MethodBuilder cons = future.newConstructor("CompletableFuture<" + SCRIBMESSAGE_CLASS + "> " + FUTURE_PARAM);
		cons.addModifiers(ClassBuilder.PROTECTED);
		cons.addBodyLine(ClassBuilder.SUPER + "(" + FUTURE_PARAM + ");");
		MethodBuilder sync = future.newMethod("sync");
		sync.addExceptions("ExecutionException", "InterruptedException");
		sync.addModifiers(ClassBuilder.PUBLIC);
		sync.setReturn(futureClass);
		sync.addBodyLine(SCRIBMESSAGE_CLASS + " m = " + ClassBuilder.SUPER + ".get();");
		if (!a.payload.isEmpty())
		{
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)
			{
				String type = main.getDataTypeDecl((DataType) pt).extName;
				sync.addBodyLine(ClassBuilder.THIS + "." + "pay" + i + " = (" + type + ") m.payload[" + (i - 1) + "];");
				i++;
			}
		}
		sync.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.THIS + ";");
		return futureClass;
	}
	
	private void addBranchMethod(ClassBuilder cb, EndpointState ps)
	{
		final String MESSAGE_VAR = "m";
		final String SCRIBMESSAGE_OP_FIELD = "op";
		final String OPENUM_VAR = "openum";
		final String OP = MESSAGE_VAR + "." + SCRIBMESSAGE_OP_FIELD;

		JobContext jc = this.job.getContext();
		Module main = jc.getMainModule();

		String next = constructBranchReceiveClass(ps, main);
		String enumClass = this.classNames.get(ps) + "Enum";

		cb.addImports("java.util.concurrent.ExecutionException");
		
		MethodBuilder mb = cb.newMethod("branch");
		mb.setReturn(next);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException", "ExecutionException", "InterruptedException");
		
		Role peer = ps.getAcceptable().iterator().next().peer;
		mb.addBodyLine(SCRIBMESSAGE_CLASS + " " + MESSAGE_VAR + " = "
				+ ClassBuilder.SUPER + ".readScribMessage(" + getPrefixedRoleClassName(peer) + ");");
		mb.addBodyLine(enumClass + " " + OPENUM_VAR + ";");

		boolean first = true;
		for (IOAction a : ps.getAcceptable())
		{
			mb.addBodyLine(((first) ? "" : "else ") + "if (" + OP + ".equals(" + getPrefixedOpClassName(a.mid) + ")) {");
			mb.addBodyLine(1, OPENUM_VAR + " = "
					+ enumClass + "." + SessionApiGenerator.getOpClassName(a.mid) + ";");
			mb.addBodyLine("}");
			first = false;
		}
		mb.addBodyLine("else {");
		mb.addBodyLine(1, "throw " + ClassBuilder.NEW + " RuntimeException(\"Won't get here: \" + " + OP + ");");
		mb.addBodyLine("}");
		mb.addBodyLine(ClassBuilder.RETURN + " "
				+ ClassBuilder.NEW + " " + next + "(this.ep, " + OPENUM_VAR + ", " + MESSAGE_VAR + ");");
		
		EnumBuilder eb = cb.newEnum(enumClass);
		eb.addModifiers(ClassBuilder.PUBLIC);
		eb.addInterfaces(OPENUM_INTERFACE);

		ps.getAcceptable().stream().forEach((a) ->
				eb.addValues(SessionApiGenerator.getOpClassName(a.mid)));
	}

	// FIXME: factor with regular receive
	private String constructBranchReceiveClass(EndpointState ps, Module main)
	{
		final String OP_FIELD = "op";
		final String OP_PARAM = OP_FIELD;
		final String MESSAGE_FIELD = "m";
		final String MESSAGE_PARAM = MESSAGE_FIELD;
		final String ARG_PREFIX = "arg";

		String branchName = this.classNames.get(ps);
		String enumClass = branchName + "Enum";
		String className = newClassName();
		ClassBuilder cb = constructClassExceptMethods(BRANCHRECEIVESOCKET_CLASS, className, ps);
		
		MethodBuilder ctor = cb.getConstructors().iterator().next();
		ctor.addParameters(branchName + "." + enumClass + " " + OP_PARAM,
				SCRIBMESSAGE_CLASS + " " + MESSAGE_PARAM);
		ctor.addBodyLine(ClassBuilder.THIS + "." + OP_FIELD + " = " + OP_PARAM + ";");
		ctor.addBodyLine(ClassBuilder.THIS + "." + MESSAGE_FIELD + " = " + MESSAGE_PARAM + ";");

		FieldBuilder fb1 = cb.newField(OP_FIELD);
		fb1.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.FINAL);
		fb1.setType(this.classNames.get(ps) + "." + enumClass);
		
		FieldBuilder fb2 = cb.newField(MESSAGE_FIELD);
		fb2.addModifiers(ClassBuilder.PRIVATE, ClassBuilder.FINAL);
		fb2.setType(SCRIBMESSAGE_CLASS);

		for (IOAction a : ps.getAcceptable())
		{
			EndpointState succ = ps.accept(a);
			String nextClass = this.classNames.get(succ);
			String opClass = SessionApiGenerator.getOpClassName(a.mid);

			addBranchReceiveReceiveMethod(main, MESSAGE_FIELD, ARG_PREFIX, cb, a, nextClass, opClass);
			addBranchReceiveReceiveDiscardMethod(main, OP_PARAM, cb, a, nextClass, opClass);
		}

		this.classes.put(className, cb);
		return className;
	}

	private void addBranchReceiveReceiveMethod(Module main, final String MESSAGE_FIELD, final String ARG_PREFIX, ClassBuilder cb, IOAction a, String nextClass, String opClass)
	{
		MethodBuilder mb1 = makeReceiveHeader(cb, nextClass, opClass);
		if (a.mid.isOp())
		{
			addReceiveOpParams(mb1, main, a);
			mb1.addBodyLine(ClassBuilder.SUPER + ".use();");
			addBranchCheck(getPrefixedOpClassName(a.mid), mb1, MESSAGE_FIELD);
			addPayloadBuffSetters(main, a, mb1);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			addReceiveMessageSigNameParams(mb1, a, msd);
			mb1.addBodyLine(ClassBuilder.SUPER + ".use();");
			addBranchCheck(getPrefixedOpClassName(a.mid), mb1, MESSAGE_FIELD);
			mb1.addBodyLine(ARG_PREFIX + "." + BUFF_VAL+ " = (" + msd.extName + ") " + MESSAGE_FIELD + ";");
		}
		makeReturnNextSocket(mb1, nextClass);
	}

	private static void addBranchReceiveReceiveDiscardMethod(Module main, final String OP_PARAM, ClassBuilder cb, IOAction a, String nextClass, String opClass)
	{
		if (!a.payload.isEmpty() || a.mid.isMessageSigName())
		{
			MethodBuilder mb2 = makeReceiveHeader(cb, nextClass, opClass);
			String ln = (isTerminalClassName(nextClass)) ? "" : ClassBuilder.RETURN + " ";
			if (a.mid.isOp())
			{
				ln += "receive(" + OP_PARAM + ", " + a.payload.elems.stream()
						.map((pt) -> getGarbageBuff(main.getDataTypeDecl(((DataType) pt)).extName)).collect(Collectors.joining(", ")) + ");";
			}
			else
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // Factor out? (send/receive/branchreceive/...)
				ln += "receive(" + OP_PARAM + ", " + getGarbageBuff(msd.extName) + ");";
			}
			mb2.addBodyLine(ln);
		}
	}
	
	private static String getGarbageBuff(String futureClass)
	{
		//return ClassBuilder.NEW + " " + BUFF_CLASS + "<>()";  // Makes a trash Buff every time, but clean -- would be more efficient to generate the code to spawn the future without buff-ing it (partly duplicate of the normal receive generated code) 
		return "(" + BUFF_CLASS + "<" + futureClass + ">) this.ep.gc";  // FIXME: generic cast warning (this.ep.gc is Buff<?>) -- also retains unnecessary reference to the last created garbage future (but allows no-arg receive/async to be generated as simple wrapper call)
	}

	private static void addBranchCheck(String opClassName, MethodBuilder mb, String messageField)
	{
		final String SCRIBMESSAGE_OP_FIELD = "op";

		String op = ClassBuilder.THIS + "." + messageField + "." + SCRIBMESSAGE_OP_FIELD;
		mb.addBodyLine("if (!" + op + ".equals(" + opClassName + ")) {");
		mb.addBodyLine(1, "throw " + ClassBuilder.NEW + " "
					+ SCRIBBLERUNTIMEEXCEPTION_CLASS + "(\"Wrong branch, received: \" + " + op + ");");
		mb.addBodyLine("}");
	}

	private String getPackageName() // Java output package (not Scribble package)
	{
		return SessionApiGenerator.getPackageName(this.gpn);
	}
	
	// Not fully qualified, just session class prefix
	// Actually used to target the constant singleton value of this type (same "name")
	private String getPrefixedRoleClassName(Role role)
	{
		return SessionApiGenerator.getSessionClassName(this.gpn) + "." + role.toString();
	}
	
	// Not fully qualified, just session class prefix
	// Actually used to target the constant singleton value of this type (same "name")
	private String getPrefixedOpClassName(MessageId<?> mid)
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
			this.classNames.put(ps, ClassBuilder.VOID);
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
	
	private int nextCount()
	{
		return this.counter++;
	}
}
