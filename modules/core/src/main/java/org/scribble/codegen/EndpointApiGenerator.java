package org.scribble.codegen;

import java.util.HashMap;
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
import org.scribble.visit.JobContext;
import org.scribble.visit.Projector;

public class EndpointApiGenerator
{
	public static final String SESSIONENDPOINT_CLASS = "org.scribble.net.session.SessionEndpoint";
	public static final String SCRIBMESSAGE_CLASS = "org.scribble.net.ScribMessage";
	public static final String SCRIBBLERUNTIMEEXCEPTION_CLASS = "org.scribble.main.ScribbleRuntimeException";
	public static final String BUFF_CLASS = "org.scribble.net.Buff";
	public static final String OPENUM_INTERFACE = "org.scribble.net.session.OpEnum";

	public static final String INITSOCKET_CLASS = "org.scribble.net.InitSocket";
	public static final String SENDSOCKET_CLASS = "org.scribble.net.SendSocket";
	public static final String RECEIVESOCKET_CLASS = "org.scribble.net.ReceiveSocket";
	public static final String BRANCHSOCKET_CLASS = "org.scribble.net.BranchSocket";
	public static final String ENDSOCKET_CLASS = "org.scribble.net.EndSocket";
	
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

		EndpointState init = job.getContext().getEndointGraph(fullname, role).init;
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
		ClassBuilder cb = new ClassBuilder();
		cb.setName(className);
		cb.setPackage(getPackageName());
		cb.setSuperClass(INITSOCKET_CLASS);
		cb.addModifiers(ClassBuilder.PUBLIC);
		
		MethodBuilder ctor = cb.newConstructor(SESSIONENDPOINT_CLASS + " se");
		ctor.addModifiers(ClassBuilder.PUBLIC);
		ctor.addBodyLine(ClassBuilder.SUPER + "(se);");
		
		MethodBuilder mb = cb.newMethod("init");
		mb.setReturn(this.root);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb.addBodyLine(ClassBuilder.SUPER + ".use();");  // Factor out
		mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + this.root + "(this.ep);");

		return cb;
	}

	private ClassBuilder constructClass(String superc, String className, EndpointState ps)
	{
		ClassBuilder cb = constructClassExceptMethods(superc, className, ps);
		if (!ps.isTerminal())
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
		cb.addImports(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getSessionClassName(this.gpn));
	}
	
	private MethodBuilder makeConstructor(ClassBuilder cb, String className)
	{
		MethodBuilder ctor = cb.newConstructor(SESSIONENDPOINT_CLASS + " se");
		ctor.addModifiers(ClassBuilder.PROTECTED);
		ctor.addBodyLine(ClassBuilder.SUPER + "(se);");
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
		JobContext jc = this.job.getContext();
		Module main = jc.getMainModule();

		for (IOAction a : ps.getAcceptable())  // Scribble ensures all a are input or all are output
		{
			EndpointState succ = ps.accept(a);
			String next = this.classNames.get(succ);
			String opref = getPrefixedOpClassName(a.mid);
			
			MethodBuilder mb = cb.newMethod("send");
			mb.setReturn(next);
			mb.addModifiers(ClassBuilder.PUBLIC);
			mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException");
			
			if (a.mid.isOp())
			{	
				mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " role");
				mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " op");
				if (!a.payload.isEmpty())
				{
					int i = 1;
					for (PayloadType<?> pt : a.payload.elems)
					{
						DataType dt = (DataType) pt;  // TODO: if not DataType
						DataTypeDecl dtd = main.getDataTypeDecl(dt);  // FIXME: might not belong to main module
						mb.addParameters(dtd.extName + " arg" + i++);
					}
				}

				String ln = ClassBuilder.SUPER + ".writeScribMessage(role, "
                    		+ ClassBuilder.NEW + " " + SCRIBMESSAGE_CLASS + "(" + opref;
				if (!a.payload.isEmpty())
				{
					ln += ", " + IntStream.range(1, a.payload.elems.size() + 1)
					      		.mapToObj((i) -> "arg" + i).collect(Collectors.joining(", "));
				}
				ln += "));\n";
				mb.addBodyLine(ln);
			}
			else //if (a.mid.isMessageSigName())
			{	
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				mb.addParameters(SessionApiGenerator.getRoleClassName(a.peer) + " role",  msd.extName + " m");
				mb.addBodyLine(ClassBuilder.SUPER + ".writeScribMessage(role, m);");
			}
			mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + next + "(this.ep);");
		}
	}

	private void makeReceive(ClassBuilder cb, EndpointState ps)
	{
		JobContext jc = this.job.getContext();
		Module main = jc.getMainModule();

		IOAction a = ps.getAcceptable().iterator().next();
		EndpointState succ = ps.accept(a);
		String next = this.classNames.get(succ);

		MethodBuilder mb = makeReceiveBlurb(cb, next);
		if (a.mid.isOp())
		{
			addReceiveOpParams(main, a, mb);
			mb.addBodyLine(SCRIBMESSAGE_CLASS + " m = " + ClassBuilder.SUPER + ".readScribMessage(" + getPrefixedRoleClassName(a.peer) + ");");
			addReceiveOpPayloadIntoBuffs(main, a, mb);
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			addReceiveMessageSigNameParams(a, mb, msd);
			mb.addBodyLine(SCRIBMESSAGE_CLASS + " m = " + ClassBuilder.SUPER + ".readScribMessage(" + getPrefixedRoleClassName(a.peer) + ");");
			mb.addBodyLine("b.val = (" + msd.extName + ") m;");
		}
		mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + next + "(this.ep);");
	}

	private static MethodBuilder makeReceiveBlurb(ClassBuilder cb, String next)
	{
		MethodBuilder mb = cb.newMethod("receive");
		mb.setReturn(next);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");
		return mb;
	}

	private void addReceiveMessageSigNameParams(IOAction a, MethodBuilder mb, MessageSigNameDecl msd)
	{
		// FIXME: problem if package and protocol have the same name
		//mb.addParameters(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getOpClassName(a.mid) + " op");
		mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " op");
		mb.addParameters(BUFF_CLASS + "<? " + ClassBuilder.SUPER + " " + msd.extName + "> b");
	}

	private static void addReceiveOpParams(Module main, IOAction a, MethodBuilder mb)
	{
		// FIXME: problem if package and protocol have the same name
		//mb.addParameters(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getOpClassName(a.mid) + " op");
		mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " op");
		if (!a.payload.isEmpty())
		{
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
				mb.addParameters(BUFF_CLASS + "<? " + ClassBuilder.SUPER + " " + dtd.extName + "> arg" + i++);
			}
		}
	}

	private static void addReceiveOpPayloadIntoBuffs(Module main, IOAction a, MethodBuilder mb)
	{
		if (!a.payload.isEmpty())
		{
			int i = 1;
			for (PayloadType<?> pt : a.payload.elems)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
				mb.addBodyLine("arg" + i + ".val = (" + dtd.extName + ") m.payload[" + (i++ - 1) +"];");
			}
		}
	}
	
	private void makeBranch(ClassBuilder cb, EndpointState ps)
	{
		JobContext jc = this.job.getContext();
		Module main = jc.getMainModule();

		String next = constructBranchReceiveClass(ps, main);
		
		MethodBuilder mb = cb.newMethod("branch");
		mb.setReturn(next);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");
		
		Role peer = ps.getAcceptable().iterator().next().peer;
		mb.addBodyLine(SCRIBMESSAGE_CLASS + " m = " + ClassBuilder.SUPER + ".readScribMessage(" + getPrefixedRoleClassName(peer) + ");");
		mb.addBodyLine(this.classNames.get(ps) + "Enum openum;");

		boolean first = true;
		for (IOAction a : ps.getAcceptable())
		{
			mb.addBodyLine(((first) ? "" : "else ") + "if (m.op.equals(" + getPrefixedOpClassName(a.mid) + ")) {");
			mb.addBodyLine(1, "openum = " + this.classNames.get(ps) + "Enum." + SessionApiGenerator.getOpClassName(a.mid) + ";");
			mb.addBodyLine("}");
			first = false;
		}
		mb.addBodyLine("else {");
		mb.addBodyLine(1, "throw " + ClassBuilder.NEW + " RuntimeException(\"Won't get here: \" + m.op);");
		mb.addBodyLine("}");
		mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + next + "(this.ep, openum, m);");
		
		EnumBuilder eb = cb.newEnum(this.classNames.get(ps) + "Enum");
		eb.addModifiers(ClassBuilder.PUBLIC);
		eb.addInterfaces(OPENUM_INTERFACE);

		ps.getAcceptable().stream().forEach((a) ->
				eb.addValues(SessionApiGenerator.getOpClassName(a.mid)));
	}

	// FIXME: factor with regular receive
	private String constructBranchReceiveClass(EndpointState ps, Module main)
	{
		String className = newClassName();
		ClassBuilder cb = constructClassExceptMethods(RECEIVESOCKET_CLASS, className, ps);
		
		MethodBuilder ctor = cb.getConstructors().iterator().next();
		ctor.addParameters(this.classNames.get(ps) + "." + this.classNames.get(ps) + "Enum op", SCRIBMESSAGE_CLASS + " m");
		ctor.addBodyLine("this.op = op;");
		ctor.addBodyLine("this.m = m;");

		FieldBuilder fb1 = cb.newField("op");
		fb1.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.FINAL);
		fb1.setType(this.classNames.get(ps) + "." + this.classNames.get(ps) + "Enum");
		
		FieldBuilder fb2 = cb.newField("m");
		fb2.addModifiers(ClassBuilder.PRIVATE, ClassBuilder.FINAL);
		fb2.setType(SCRIBMESSAGE_CLASS);

		for (IOAction a : ps.getAcceptable())
		{
			EndpointState succ = ps.accept(a);
			String next = this.classNames.get(succ);
			
			MethodBuilder mb = makeReceiveBlurb(cb, next);
			if (a.mid.isOp())
			{
				addReceiveOpParams(main, a, mb);
				mb.addBodyLine(ClassBuilder.SUPER + ".use();");
				addBranchCheck(getPrefixedOpClassName(a.mid), mb);
				addReceiveOpPayloadIntoBuffs(main, a, mb);
			}
			else //if (a.mid.isMessageSigName())
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				addReceiveMessageSigNameParams(a, mb, msd);
				mb.addBodyLine(ClassBuilder.SUPER + ".use();");
				addBranchCheck(getPrefixedOpClassName(a.mid), mb);
				mb.addBodyLine("b.val = (" + msd.extName + ") m;");
			}
			mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + next + "(this.ep);\n");
		}

		this.classes.put(className, cb);
		return className;
	}

	private static void addBranchCheck(String opClassName, MethodBuilder mb)
	{
		mb.addBodyLine("if (!this.m.op.equals(" + opClassName + ")) {");
		mb.addBodyLine(1, "throw " + ClassBuilder.NEW + " "
					+ SCRIBBLERUNTIMEEXCEPTION_CLASS + "(\"Wrong branch, received: \" + this.m.op);");
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
		if (ps.isTerminal())
		{
			return ENDSOCKET_CLASS;
		}
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
		if (this.classNames.containsKey(ps))// || ps.isTerminal())
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
