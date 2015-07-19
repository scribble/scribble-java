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
	Map<EndpointState, String> classNames = new HashMap<>();
	private String root = null;
	//private Map<String, String> classes = new HashMap<>();  // class name -> class source
	private Map<String, ClassBuilder> classes = new HashMap<>();  // class name key
	
	//private enum SocketType { SEND, RECEIVE, BRANCH, END, INIT }

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
		this.classes.put(className, constructClass(ps));
		for (EndpointState succ : ps.getSuccessors())
		{
			constructClasses(succ);
		}

		// Depends on the above being done first
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
		
		MethodBuilder mb1 = cb.newConstructor(SESSIONENDPOINT_CLASS + " se");
		mb1.addModifiers(ClassBuilder.PUBLIC);
		mb1.addBodyLine(ClassBuilder.SUPER + "(se);");
		
		MethodBuilder mb2 = cb.newMethod("init");
		mb2.setReturn(this.root);
		mb2.addModifiers(ClassBuilder.PUBLIC);
		mb2.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb2.addBodyLine(ClassBuilder.SUPER + ".use();");  // Factor out
		mb2.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + this.root + "(this.ep);");

		return cb;
	}

	private ClassBuilder constructClass(EndpointState ps)
	{
		ClassBuilder cb = new ClassBuilder();
		String className = this.classNames.get(ps);
		cb.setName(className);
		cb.setPackage(getPackageName());
		cb.addModifiers(ClassBuilder.PUBLIC);
		cb.setSuperClass(getSocketClass(ps));
		makeImports(cb, ps);
		makeConstructor(cb, className);
		makeMethods(cb, ps);
		return cb;
	}

	private void makeImports(ClassBuilder cb, EndpointState ps)
	{
		cb.addImports("java.io.IOException");
		cb.addImports(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getSessionClassName(this.gpn));
	}
	
	private void makeConstructor(ClassBuilder cb, String className)
	{
		MethodBuilder mb1 = cb.newConstructor(SESSIONENDPOINT_CLASS + " se");
		mb1.addModifiers(ClassBuilder.PROTECTED);
		mb1.addBodyLine(ClassBuilder.SUPER + "(se);");
	}
	
	private void makeMethods(ClassBuilder cb, EndpointState ps)
	{
		if (ps.isTerminal())
		{
			return;
			//throw new RuntimeException("Shouldn't get in here: " + ps);
		}

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
			String opref = getOp(a.mid);
			
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

		MethodBuilder mb = cb.newMethod("receive");
		mb.setReturn(next);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");

		if (a.mid.isOp())
		{
			mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " op");
			if (!a.payload.isEmpty())
			{
				int i = 1;
				for (PayloadType<?> pt : a.payload.elems)
				{
					DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt); // TODO: if not DataType  // FIXME: maybe not main
					mb.addParameters(BUFF_CLASS + "<? " + ClassBuilder.SUPER + " " + dtd.extName + "> arg" + i++);
				}
			}
			mb.addBodyLine(SCRIBMESSAGE_CLASS + " m = " + ClassBuilder.SUPER + ".readScribMessage(" + getRole(a.peer) + ");");
			if (!a.payload.isEmpty())
			{
				int i = 1;
				for (PayloadType<?> pt : a.payload.elems)
				{
					DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt); // TODO: if not DataType  // FIXME: maybe not main
					mb.addBodyLine("arg" + i + ".val = (" + dtd.extName + ") m.payload[" + (i++ - 1) +"];");
				}
			}
		}
		else //if (a.mid.isMessageSigName())
		{
			MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
			mb.addParameters(SessionApiGenerator.getOpClassName(a.mid) + " op");
			mb.addParameters("Buff<? super " + msd.extName + "> b");
			mb.addBodyLine(SCRIBMESSAGE_CLASS + " m = " + ClassBuilder.SUPER + ".readScribMessage(" + getRole(a.peer) + ");");
			mb.addBodyLine("b.val = (" + msd.extName + ") m;");
		}
		mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + next + "(this.ep);");
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
		mb.addBodyLine(SCRIBMESSAGE_CLASS + " m = " + ClassBuilder.SUPER + ".readScribMessage(" + getRole(peer) + ");");
		mb.addBodyLine(this.classNames.get(ps) + "Enum openum;");

		boolean first = true;
		for (IOAction a : ps.getAcceptable())
		{
			mb.addBodyLine(((first) ? "" : "else ") + "if (m.op.equals(" + getOp(a.mid) + ")) {");
			mb.addBodyLine(1, "openum = " + this.classNames.get(ps) + "Enum." + SessionApiGenerator.getOpClassName(a.mid) + ";");
			mb.addBodyLine("}");
			first = false;
		}
		mb.addBodyLine("else {");
		mb.addBodyLine(1, "throw new RuntimeException(\"Won't get here: \" + m.op);");
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
		String name = newClassName();
		
		ClassBuilder cb = new ClassBuilder();
		cb.setName(name);
		cb.setPackage(getPackageName());
		makeImports(cb, ps);
		cb.addModifiers(ClassBuilder.PUBLIC);
		cb.setSuperClass(RECEIVESOCKET_CLASS);
		
		FieldBuilder fb1 = cb.newField("op");
		fb1.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.FINAL);
		fb1.setType(this.classNames.get(ps) + "." + this.classNames.get(ps) + "Enum");
		
		FieldBuilder fb2 = cb.newField("m");
		fb2.addModifiers(ClassBuilder.PRIVATE, ClassBuilder.FINAL);
		fb2.setType(SCRIBMESSAGE_CLASS);
		
		MethodBuilder ctor = cb.newConstructor(SESSIONENDPOINT_CLASS + " se ",
				this.classNames.get(ps) + "." + this.classNames.get(ps) + "Enum op", SCRIBMESSAGE_CLASS + " m");
		ctor.addModifiers(ClassBuilder.PROTECTED);
		ctor.addBodyLine(ClassBuilder.SUPER + "(se);");
		ctor.addBodyLine("this.op = op;");
		ctor.addBodyLine("this.m = m;");

		for (IOAction a : ps.getAcceptable())
		{
			EndpointState succ = ps.accept(a);
			String next = this.classNames.get(succ);
			
			MethodBuilder mb = cb.newMethod("receive");
			mb.setReturn(next);
			mb.addModifiers(ClassBuilder.PUBLIC);
			mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS, "IOException", "ClassNotFoundException");
			
			if (a.mid.isOp())
			{
				// FIXME: problem if package and protocol have the same name
				mb.addParameters(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getOpClassName(a.mid) + " op");
				if (!a.payload.isEmpty())
				{
					int i = 1;
					for (PayloadType<?> pt : a.payload.elems)
					{
						DataTypeDecl dtd = main.getDataTypeDecl((DataType) pt);  // TODO: if not DataType
						mb.addParameters(BUFF_CLASS + "<? " + ClassBuilder.SUPER + " " + dtd.extName + "> arg" + i++);
					}
				}

				mb.addBodyLine(ClassBuilder.SUPER + ".use();");
				mb.addBodyLine("if (!this.m.op.equals(" + getOp(a.mid) + ")) {");
				mb.addBodyLine(1, "throw " + ClassBuilder.NEW + " "
							+ SCRIBBLERUNTIMEEXCEPTION_CLASS + "(\"Wrong branch, received: \" + this.m.op);");
				mb.addBodyLine("}");
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
			else //if (a.mid.isMessageSigName())
			{
				MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
				// FIXME: problem if package and protocol have the same name
				mb.addParameters(SessionApiGenerator.getPackageName(this.gpn) + "." + SessionApiGenerator.getOpClassName(a.mid) + " op");
				mb.addParameters(BUFF_CLASS + "<? " + ClassBuilder.SUPER + " " + msd.extName + "> b");

				mb.addBodyLine(ClassBuilder.SUPER + ".use();");
				mb.addBodyLine("if (!this.m.op.equals(" + getOp(a.mid) + ")) {");
				mb.addBodyLine("1, throw new ScribbleRuntimeException(\"Wrong branch, received: \" + this.m.op);");
				mb.addBodyLine("}");
				mb.addBodyLine("b.val = (" + msd.extName + ") m;");
			}
			mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + next + "(this.ep);\n");
		}
		this.classes.put(name, cb);
		return name;
	}

	private String getPackageName() // Java output package (not Scribble package)
	{
		//return this.gpn.getPrefix().toString();
		return SessionApiGenerator.getPackageName(this.gpn);
		//return this.gpn.toString();// + "." + this.role;  // role causes clash with Role constant in session class
	}
	
	private String getOp(MessageId<?> mid)
	{
		return SessionApiGenerator.getSessionClassName(this.gpn) + "." + SessionApiGenerator.getOpClassName(mid);
	}
	
	private String getRole(Role role)
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
