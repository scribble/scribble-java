package org.scribble2.fsm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.scribble2.model.visit.Job;
import org.scribble2.model.visit.Projector;
import org.scribble2.net.session.SessionGenerator;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.Op;
import org.scribble2.sesstype.name.Role;

public class ApiGenerator
{
	private final Job job;
	private final GProtocolName gpn;
	private final Role role;
	private final LProtocolName lpn;

	private int counter = 0;
	Map<ProtocolState, String> classNames = new HashMap<>();
	private String root = null;
	private Map<String, String> classes = new HashMap<>();  // class name -> class source
	
	private enum SocketType { SEND, RECIEVE, BRANCH, END }
	private final Map<SocketType, String> SOCKET_CLASSES;

	{
		SOCKET_CLASSES = new HashMap<>();
		SOCKET_CLASSES.put(SocketType.SEND, "org.scribble2.net.SendSocket");
		SOCKET_CLASSES.put(SocketType.RECIEVE, "org.scribble2.net.ReceiveSocket");
		SOCKET_CLASSES.put(SocketType.END, "org.scribble2.net.EndSocket");
	}

	//public ApiGenerator(Job job, LProtocolName lpn)
	public ApiGenerator(Job job, GProtocolName gpn, Role role)
	{
		this.job = job;
		//this.lpn = lpn;
		this.gpn = gpn;
		this.role = role;
		this.lpn = Projector.makeProjectedProtocolNameNode(new GProtocolName(this.job.getContext().main, gpn), role).toName();

		ProtocolState init = job.getContext().getFsm(lpn).init;
		generateClassNames(init);
		for (ProtocolState ps : classNames.keySet())
		{
			generateClass(ps);
		}
	}
	
	private String getPackageName() // Java output package (not Scribble package)
	{
		//return this.gpn.getPrefix().toString();
		return SessionGenerator.getPackageName(this.gpn);
	}
	
	public Map<String, String> getClasses()
	{
		return this.classes;
	}

	private void generateClass(ProtocolState ps)
	{
		String className = this.classNames.get(ps);
		String clazz = "";
		clazz += "package " + getPackageName() + ";\n";
		clazz += "\n";
		clazz += generateImports(ps);
		clazz += "\n";
		clazz += "public class " + className + " extends " + SOCKET_CLASSES.get(getSocketType(ps)) + " {\n";
		clazz += generateConstructor(className);
		clazz += "\n\n";
		//for (IOAction a : ps.getAcceptable())
		{
			// Scribble ensures all a are input or all are output
			//clazz += generateMethod(a, ps.accept(a));
			clazz += generateMethods(ps);
		}
		/*if (className.equals(this.root))
		{
			clazz += "\n";
			clazz += "\t@Override\n";
			clazz += "\tpublic void close() {\n";
			clazz += "\t\tif (!this.ep.isCompleted()) {\n";
			clazz += "\t\t\tthrow new " + getSessionIncompleteExceptionName() + "();\n";
			clazz += "\t\t}\n";
			clazz += "\t}\n";
		}*/
		clazz += "}\n";
		/*if (className.equals(this.root))
		{
			clazz += "\n";
			clazz += "class " + getSessionIncompleteExceptionName() + " extends SessionIncompleteException {\n";
			clazz += "\tprivate static final long serialVersionUID = 1L;\n";
			clazz += "}";
		}*/
		this.classes.put(className, clazz);
	}
	
	/*private String getSessionIncompleteExceptionName()
	{
		return this.gpn.toString().replace('.', '_') + "_" + this.role + "IncompleteException";
	}*/
	
	private String generateConstructor(String className)
	{
		String cons = "";
		if (this.root.equals(className))
		{
			cons += "\tpublic ";
		}
		else
		{
			cons += "\tprotected ";
		}
		cons += className + "(SessionEndpoint se) {\n";
		cons += "\t\tsuper(se);\n";
		cons += "\t}";
		return cons;
	}
	
	private String generateImports(ProtocolState ps)
	{
		String imports = "";
		imports += "import java.io.IOException;\n";
		imports += "\n";
		imports += "import org.scribble2.net.session.SessionEndpoint;\n";
		imports += "import org.scribble2.util.ScribbleRuntimeException;\n";
		//imports += "import org.scribble2.util.SessionIncompleteException;\n";
		/*imports += "\n";
		imports += "import " + getPackageName() + "." + this.role + ";\n";
		for (IOAction a : ps.getAcceptable())
		{
			imports += "import " + getPackageName() + "." + SessionGenerator.getOpClassName((Op) a.mid) + ";\n";
			imports += "import " + getPackageName() + "." + a.peer + ";\n";
		}*/
		return imports;
	}
	
	private String getOp(Op op)
	{
		return SessionGenerator.getSessionClassName(gpn) + "." + SessionGenerator.getOpClassName(op);
	}

	private String getRole(Role role)
	{
		return SessionGenerator.getSessionClassName(gpn) + "." + role.toString();
	}
	
	//private String generateMethod(IOAction a, ProtocolState succ)
	private String generateMethods(ProtocolState ps)
	{
		Set<IOAction> as = ps.getAcceptable();
		if (as.isEmpty())
		{
			return "";
		}
		String method = "";
		SocketType st = getSocketType(ps);
		switch (st)
		{
			case SEND:
			{
				for (IOAction a : ps.getAcceptable())
				{
					ProtocolState succ = ps.accept(a);
					String next = this.classNames.get(succ);
					Op op = (Op) a.mid;
					String opref = getOp(op);
					method += "\tpublic " + next + " send_" + op + "() throws ScribbleRuntimeException, IOException {\n";
					method += "\t\tsuper.send(" + getRole(a.peer) + ", " + opref + ");\n";
					method += "\t\treturn new " + next + "(this.ep);\n";
					method += "\t}\n";
				}
				break;
			}
			case RECIEVE:
			{
				IOAction a = ps.getAcceptable().iterator().next();
				ProtocolState succ = ps.accept(a);
				String next = this.classNames.get(succ);
				method += "\tpublic " + next + " receive() throws ScribbleRuntimeException, IOException, ClassNotFoundException {\n";
				method += "\t\tsuper.receive(" + getRole(a.peer) + ");\n";
				method += "\t\t\treturn new " + next + "(this.ep);\n";
				method += "\t}\n";
				break;
			}
			case BRANCH:
			{
				throw new RuntimeException("TODO");
				/*method += this.classNames.get(succ) + " receive() {\n";
				method += "\t\torg.scribble2.sesstype.name.MessageId mid = super.receive(" + a.peer + ");\n";
				method +=  ...;
						
				turn ops (or states?) into types, and then use overloading to guide cast...
				
				..HERE: work out code generation for multi-branch*/
			}
			default:
			{
				throw new RuntimeException("TODO: " + st);
			}
		}
		return method;
	}
	
	private void generateClassNames(ProtocolState ps)
	{
		if (this.classNames.containsKey(ps))
		{
			return;
		}
		String c = newClassName(ps);
		this.classNames.put(ps, c);
		if (this.root == null)
		{
			this.root = c;
		}
		for (ProtocolState succ : ps.getSuccessors())
		{
			generateClassNames(succ);
		}
	}
	
	private String newClassName(ProtocolState ps)
	{
		String sn = this.lpn.getSimpleName().toString();
		return sn + "_" + nextCount();
	}
	
	private int nextCount()
	{
		return this.counter++;
	}
	
	private SocketType getSocketType(ProtocolState ps)
	{
		if (ps.isTerminal())
		{
			return SocketType.END;
		}
		Set<IOAction> as = ps.getAcceptable();
		IOAction a = as.iterator().next();
		if (a instanceof Send)
		{
			return SocketType.SEND;
		}
		else if (a instanceof Receive)
		{
			return (as.size() > 1) ? SocketType.BRANCH : SocketType.RECIEVE;
		}
		else
		{
			throw new RuntimeException("TODO");
		}
	}
}
