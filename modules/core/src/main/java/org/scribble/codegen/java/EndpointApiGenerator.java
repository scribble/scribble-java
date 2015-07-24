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

	public static final String INITSOCKET_CLASS = "org.scribble.net.InitSocket";
	public static final String SENDSOCKET_CLASS = "org.scribble.net.SendSocket";
	public static final String RECEIVESOCKET_CLASS = "org.scribble.net.ReceiveSocket";
	public static final String BRANCHSOCKET_CLASS = "org.scribble.net.BranchSocket";
	public static final String ENDSOCKET_CLASS = "org.scribble.net.EndSocket";
	
	private static final String SCRIBSOCKET_EP_FIELD = ClassBuilder.THIS + ".ep";
	
	private final Job job;
	private final GProtocolName gpn;  // full name
	private final LProtocolName lpn;

	private int counter = 1;

	private String root = null;
	Map<EndpointState, String> classNames = new HashMap<>();
	private Map<String, ClassBuilder> classes = new HashMap<>();  // class name key

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

	private void constructClasses(EndpointState ps)
	{
		if (ps.isTerminal())
		{
			return;
		}
		String className = this.classNames.get(ps);
		if (this.classes.containsKey(className))
		{
			return;
		}
		this.classes.put(className, constructClass(getSocketClass(ps), this.classNames.get(ps), ps));
		for (EndpointState succ : ps.getSuccessors())
		{
			constructClasses(succ);
		}

		// Depends on the above being done first (for this.root)
		String init = this.lpn.getSimpleName().toString() + "_" + 0;  // FIXME: factor out with newClassName
		this.classes.put(init, constructInitClass(init));
	}

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
		mb.addBodyLine(ClassBuilder.RETURN + " "
					+ ClassBuilder.NEW + " " + this.root + "(" + SCRIBSOCKET_EP_FIELD + ");");

		return cb;
	}

	private ClassBuilder constructClass(String superc, String className, EndpointState ps)
	{
		ClassBuilder cb = constructClassExceptMethods(superc, className, ps);
		//if (!ps.isTerminal())
		{
			makeMethods(cb, ps);
		}
		return cb;
	}

	private ClassBuilder constructClassExceptMethods(String superc, String className, EndpointState ps)
	{
		ClassBuilder cb = new ClassBuilder();
		cb.setName(className);
		cb.setPackage(getPackageName());
		cb.addModifiers(ClassBuilder.PUBLIC);
		cb.setSuperClass(superc);
		makeImports(cb, ps);
		makeConstructor(cb, className);
		return cb;
	}

	private void makeImports(ClassBuilder cb, EndpointState ps)
	{
		cb.addImports("java.io.IOException");
		cb.addImports(SessionApiGenerator.getPackageName(this.gpn) + "."
					+ SessionApiGenerator.getSessionClassName(this.gpn));
	}
	
	private MethodBuilder makeConstructor(ClassBuilder cb, String className)
	{
		final String SESSIONENDPOINT_PARAM = "se";

		MethodBuilder ctor = cb.newConstructor(SESSIONENDPOINT_CLASS + " " + SESSIONENDPOINT_PARAM);
		ctor.addModifiers(ClassBuilder.PROTECTED);
		ctor.addBodyLine(ClassBuilder.SUPER + "(" + SESSIONENDPOINT_PARAM + ");");
		return ctor;
	}
	
	private void makeMethods(ClassBuilder cb, EndpointState ps)
	{
		String st = getSocketClass(ps);
		switch (st)
		{
			case SENDSOCKET_CLASS:
			{
				makeSend(cb, ps);
				break;
			}
			case RECEIVESOCKET_CLASS:
			{
				makeReceive(cb, ps);
				break;
			}
			case BRANCHSOCKET_CLASS:
			{
				makeBranch(cb, ps);
				break;
			}
			default:
			{
				throw new RuntimeException("TODO: " + st);
			}
		}
	}

	private void makeSend(ClassBuilder cb, EndpointState ps)
	{
		final String ROLE_PARAM = "role";
		final String ARG_PREFIX = "arg";

		JobContext jc = this.job.getContext();
		Module main = jc.getMainModule();

		for (IOAction a : ps.getAcceptable())  // Scribble ensures all a are input or all are output
		{
			EndpointState succ = ps.accept(a);
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

				String body = ClassBuilder.SUPER + ".writeScribMessage(" + ROLE_PARAM + ", "
                    		+ ClassBuilder.NEW + " " + SCRIBMESSAGE_CLASS + "(" + opref;
				if (!a.payload.isEmpty())
				{
					body += ", " + args.stream().collect(Collectors.joining(", "));
				}
				body += "));\n";
				mb.addBodyLine(body);
			}
			else //if (a.mid.isMessageSigName())
			{	
				final String MESSAGE_PARAM = "m";

				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " " + ROLE_PARAM, 
						msd.extName + " " + MESSAGE_PARAM);
				mb.addBodyLine(ClassBuilder.SUPER + ".writeScribMessage(" + ROLE_PARAM 
						+ ", " + MESSAGE_PARAM + ");");
			}
			mb.addBodyLine(ClassBuilder.RETURN + " "
						+ ClassBuilder.NEW + " " + next + "(" + SCRIBSOCKET_EP_FIELD + ");");
		}
	}

	private void makeReceive(ClassBuilder cb, EndpointState ps)
	{
		final String OP_PARAM = "op";
		final String MESSAGE_PARAM = "m";
		final String ARG_PREFIX = "arg";

		JobContext jc = this.job.getContext();
		Module main = jc.getMainModule();

		IOAction a = ps.getAcceptable().iterator().next();
		EndpointState succ = ps.accept(a);
		String next = this.classNames.get(succ);

		MethodBuilder mb1 = makeReceiveBlurb(cb, next);
		if (a.mid.isOp())
		{
			addReceiveOpParams(main, a, mb1, OP_PARAM, ARG_PREFIX);
			mb1.addBodyLine(SCRIBMESSAGE_CLASS + " " + MESSAGE_PARAM + " = "
						+ ClassBuilder.SUPER + ".readScribMessage(" + getPrefixedRoleClassName(a.peer) + ");");
			addReceiveOpPayloadIntoBuffs(main, a, mb1, MESSAGE_PARAM, ARG_PREFIX);
		}
		else //if (a.mid.isMessageSigName())
		{
			final String MESSAGE_VAR = MESSAGE_PARAM;

			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			addReceiveMessageSigNameParams(a, mb1, msd, OP_PARAM, ARG_PREFIX);
			mb1.addBodyLine(SCRIBMESSAGE_CLASS + " " + MESSAGE_VAR + " = "
						+ ClassBuilder.SUPER + ".readScribMessage(" + getPrefixedRoleClassName(a.peer) + ");");
			mb1.addBodyLine(ARG_PREFIX + "." + BUFF_VAL + " = (" + msd.extName + ") " + MESSAGE_VAR + ";");
		}
		mb1.addBodyLine(ClassBuilder.RETURN + " "
				+ ClassBuilder.NEW + " " + next + "(" + SCRIBSOCKET_EP_FIELD + ");");
		
		MethodBuilder mb2 = cb.newMethod("...future..."); 
		
		//HERE:... (bounded) receive thread and input action indexing ... gc for unneeded messages? weak references...
	}

	private static MethodBuilder makeReceiveBlurb(ClassBuilder cb, String next)
	{
		MethodBuilder mb = cb.newMethod("receive");
		mb.setReturn(next);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");
		return mb;
	}

	private static void addReceiveOpParams(
			Module main, IOAction a, MethodBuilder mb, String opParam, String argPrefix)
	{
		// FIXME: problem if package and protocol have the same name
		//mb.addParameters(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getOpClassName(a.mid) + " op");
		mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " " + opParam);
		if (!a.payload.isEmpty())
		{
			String buffSuper = BUFF_CLASS + "<? " + ClassBuilder.SUPER + " ";
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
				mb.addParameters(buffSuper + dtd.extName + "> " + argPrefix + i++);
			}
		}
	}

	private static void addReceiveOpPayloadIntoBuffs(
			Module main, IOAction a, MethodBuilder mb, String messageParam, String argPrefix)
	{
		final String SCRIBMESSAGE_PAYLOAD_FIELD = "payload";

		if (!a.payload.isEmpty())
		{
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)  // Could factor out this loop with addReceiveOpParams (as for send)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
				mb.addBodyLine(argPrefix + i + "." + BUFF_VAL + " = (" + dtd.extName + ") "
							+ messageParam + "." + SCRIBMESSAGE_PAYLOAD_FIELD + "[" + (i++ - 1) +"];");
			}
		}
	}

	private static void addReceiveMessageSigNameParams(
			IOAction a, MethodBuilder mb, MessageSigNameDecl msd, String opParam, String argPrefix)
	{
		// FIXME: problem if package and protocol have the same name
		//mb.addParameters(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getOpClassName(a.mid) + " op");
		mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " " + opParam);
		mb.addParameters(BUFF_CLASS + "<? " + ClassBuilder.SUPER + " " + msd.extName + "> " + argPrefix);
	}
	
	private void makeBranch(ClassBuilder cb, EndpointState ps)
	{
		final String MESSAGE_VAR = "m";
		final String SCRIBMESSAGE_OP_FIELD = "op";
		final String OPENUM_VAR = "openum";
		final String OP = MESSAGE_VAR + "." + SCRIBMESSAGE_OP_FIELD;

		JobContext jc = this.job.getContext();
		Module main = jc.getMainModule();

		String next = constructBranchReceiveClass(ps, main);
		String enumClass = this.classNames.get(ps) + "Enum";
		
		MethodBuilder mb = cb.newMethod("branch");
		mb.setReturn(next);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");
		
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
		ClassBuilder cb = constructClassExceptMethods(RECEIVESOCKET_CLASS, className, ps);
		
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
			String next = this.classNames.get(succ);
			
			MethodBuilder mb = makeReceiveBlurb(cb, next);
			if (a.mid.isOp())
			{
				addReceiveOpParams(main, a, mb, OP_PARAM, ARG_PREFIX);
				mb.addBodyLine(ClassBuilder.SUPER + ".use();");
				addBranchCheck(getPrefixedOpClassName(a.mid), mb, MESSAGE_FIELD);
				addReceiveOpPayloadIntoBuffs(main, a, mb, MESSAGE_PARAM, ARG_PREFIX);
			}
			else //if (a.mid.isMessageSigName())
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				addReceiveMessageSigNameParams(a, mb, msd, OP_PARAM, ARG_PREFIX);
				mb.addBodyLine(ClassBuilder.SUPER + ".use();");
				addBranchCheck(getPrefixedOpClassName(a.mid), mb, MESSAGE_FIELD);
				mb.addBodyLine(ARG_PREFIX + "." + BUFF_VAL+ " = (" + msd.extName + ") " + MESSAGE_FIELD + ";");
			}
			mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + next + "(this.ep);\n");
		}

		this.classes.put(className, cb);
		return className;
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
	private String getPrefixedOpClassName(MessageId<?> mid)
	{
		return SessionApiGenerator.getSessionClassName(this.gpn) + "." + SessionApiGenerator.getOpClassName(mid);
	}
	
	// Not fully qualified, just session class prefix
	private String getPrefixedRoleClassName(Role role)
	{
		return SessionApiGenerator.getSessionClassName(this.gpn) + "." + role.toString();
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
		if (this.classNames.containsKey(ps) || ps.isTerminal())
		{
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
