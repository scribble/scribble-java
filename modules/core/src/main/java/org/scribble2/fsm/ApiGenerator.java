package org.scribble2.fsm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.scribble2.model.visit.Job;
import org.scribble2.model.visit.Projector;
import org.scribble2.net.session.SessionGenerator;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.Op;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleRuntimeException;

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
	
	private enum SocketType { SEND, RECEIVE, BRANCH, END }
	private final Map<SocketType, String> SOCKET_CLASSES;

	{
		SOCKET_CLASSES = new HashMap<>();
		SOCKET_CLASSES.put(SocketType.SEND, "org.scribble2.net.SendSocket");
		SOCKET_CLASSES.put(SocketType.RECEIVE, "org.scribble2.net.ReceiveSocket");
		SOCKET_CLASSES.put(SocketType.BRANCH, "org.scribble2.net.BranchSocket");
		SOCKET_CLASSES.put(SocketType.END, "org.scribble2.net.EndSocket");
	}

	//public ApiGenerator(Job job, LProtocolName lpn)
	public ApiGenerator(Job job, GProtocolName gpn, Role role)
	{
		this.job = job;
		this.gpn = gpn;
		this.role = role;
		this.lpn = Projector.makeProjectedProtocolNameNode(new GProtocolName(this.job.getContext().main, gpn), role).toName();

		ProtocolState init = job.getContext().getFsm(lpn).init;
		generateClassNames(init);
		/*for (ProtocolState ps : classNames.keySet())
		{
			generateClass(ps);
		}*/
		generateClasses(init);
	}
	
	private void generateClassNames(ProtocolState ps)
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
		for (ProtocolState succ : ps.getSuccessors())
		{
			generateClassNames(succ);
		}
	}

	private void generateClasses(ProtocolState ps)
	{
		String className = this.classNames.get(ps);
		if (this.classes.containsKey(className))
		{
			return;
		}
		this.classes.put(className, generateClass(ps));
		for (ProtocolState succ : ps.getSuccessors())
		{
			generateClasses(succ);
		}
	}

	private String generateClass(ProtocolState ps)
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
		clazz += generateMethods(ps);
		clazz += "}\n";
		return clazz;
	}

	/*private String generateClass(ProtocolState ps)
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
		}* /
		clazz += "}\n";
		/*if (className.equals(this.root))
		{
			clazz += "\n";
			clazz += "class " + getSessionIncompleteExceptionName() + " extends SessionIncompleteException {\n";
			clazz += "\tprivate static final long serialVersionUID = 1L;\n";
			clazz += "}";
		}* /
		return clazz;
	}*/
	
	private String generateImports(ProtocolState ps)
	{
		String imports = "";
		imports += "import java.io.IOException;\n";
		imports += "\n";
		imports += "import org.scribble2.net.ScribMessage;\n";
		imports += "import org.scribble2.net.session.SessionEndpoint;\n";
		imports += "import org.scribble2.util.ScribbleRuntimeException;\n";
		imports += "\n";
		imports += "import " + SessionGenerator.getPackageName(this.gpn) + "." + SessionGenerator.getSessionClassName(this.gpn) + ";\n";
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
	
	//private String generateMethod(IOAction a, ProtocolState succ)
	private String generateMethods(ProtocolState ps)
	{
		if (ps.isTerminal())  // Shouldn't get in here
		{
			return "";
		}
		String method = "";
		SocketType st = getSocketType(ps);
		switch (st)
		{
			case SEND:
			{
				for (IOAction a : ps.getAcceptable())  // Scribble ensures all a are input or all are output
				{
					ProtocolState succ = ps.accept(a);
					//String next = (succ.isTerminal()) ? SOCKET_CLASSES.get(SocketType.END) : this.classNames.get(succ);
					String next = this.classNames.get(succ);
					Op op = (Op) a.mid;
					String opref = getOp(op);
					method += "\tpublic " + next + " send(" + SessionGenerator.getOpClassName(op) + " op) throws ScribbleRuntimeException, IOException {\n";
					method += "\t\tsuper.writeScribMessage(" + getRole(a.peer) + ", new ScribMessage(" + opref + "));\n";
					method += "\t\treturn new " + next + "(this.ep);\n";
					method += "\t}\n";
				}
				break;
			}
			case RECEIVE:
			{
				IOAction a = ps.getAcceptable().iterator().next();
				ProtocolState succ = ps.accept(a);
				//String next = (succ.isTerminal()) ? SOCKET_CLASSES.get(SocketType.END) : this.classNames.get(succ);
				String next = this.classNames.get(succ);
				Op op = (Op) a.mid;
				method += "\tpublic " + next + " receive(" + SessionGenerator.getOpClassName(op) + " op) throws ScribbleRuntimeException, IOException, ClassNotFoundException {\n";
				method += "\t\tScribMessage m = super.readScribMessage(" + getRole(a.peer) + ");\n";
				method += "\t\treturn new " + next + "(this.ep);\n";
				method += "\t}\n";
				break;
			}
			case BRANCH:
			{
				//throw new RuntimeException("TODO: " + st);
				
				/*Map<IOAction, String> tmp = new HashMap<>();
				for (IOAction a : ps.getAcceptable())
				{
					tmp.put(a, newClassName());
				}*/
				// FIXME: factor out
				String tmp = newClassName();
				String clazz = "";
				clazz += "package " + getPackageName() + ";\n";
				clazz += "\n";
				clazz += generateImports(ps);
				clazz += "\n";
				clazz += "public class " + tmp + " extends " + SOCKET_CLASSES.get(SocketType.RECEIVE) + " {\n";
				clazz += "\n";
				clazz += "\tpublic final " + this.classNames.get(ps) + "." + this.classNames.get(ps) + "Enum op;\n";
				clazz += "\tprivate final ScribMessage m;\n";
				//clazz += generateConstructor(tmp);
				clazz += "\tprotected " + tmp + "(SessionEndpoint se, " + this.classNames.get(ps) + "." + this.classNames.get(ps) + "Enum op, ScribMessage m) {\n";
				clazz += "\t\tsuper(se);\n";
				clazz += "\t\tthis.op = op;\n";
				clazz += "\t\tthis.m = m;\n";
				clazz += "\t}";
				clazz += "\n\n";
				for (IOAction a : ps.getAcceptable())
				{
					ProtocolState succ = ps.accept(a);
					String next = this.classNames.get(succ);
					clazz += "\tpublic " + next + " receive(" + SessionGenerator.getPackageName(this.gpn) + "." + SessionGenerator.getOpClassName((Op) a.mid) + " op) throws ScribbleRuntimeException, IOException, ClassNotFoundException {\n";
					//clazz += "\t\tScribMessage m = super.readScribMessage(" + getRole(a.peer) + ");\n";
					clazz += "\t\tsuper.use();\n";
					clazz += "\t\tif (!this.m.op.equals(" + getOp((Op) a.mid) + ")) {\n";
					clazz += "\t\t\tthrow new ScribbleRuntimeException(\"Wrong branch, received: \" + this.m.op);\n";
					clazz += "\t\t}\n";
					clazz += "\t\treturn new " + next + "(this.ep);\n";
					clazz += "\t}\n";
				}
				clazz += "}";
				this.classes.put(tmp, clazz);
				
				Iterator<IOAction> as = ps.getAcceptable().iterator();
				IOAction first = as.next();
				String next = tmp;
				method += "\tpublic " + next + " branch() throws ScribbleRuntimeException, IOException, ClassNotFoundException {\n";
				method += "\t\tScribMessage m = super.readScribMessage(" + getRole(first.peer) + ");\n";
				method += "\t\t" + this.classNames.get(ps) + "Enum openum;\n";
				method += "\t\tif (m.op.equals(" + getOp((Op) first.mid) + ")) {\n";
				//method += "\t\t\treturn " + this.classNames.get(ps) + "Enum." + first.mid.toString() + ";\n";
				method += "\t\t\topenum = " + this.classNames.get(ps) + "Enum." + SessionGenerator.getOpClassName((Op) first.mid) + ";\n";
				method += "\t\t}\n";
				for (; as.hasNext(); )
				{
					IOAction a = as.next();
					method += "\t\telse if (m.op.equals(" + getOp((Op) a.mid) + ")) {\n";
					//method += "\t\t\treturn " + this.classNames.get(ps) + "Enum." + a.mid.toString() + ";\n";
					method += "\t\t\topenum = " + this.classNames.get(ps) + "Enum." + SessionGenerator.getOpClassName((Op) a.mid) + ";\n";
					method += "\t\t}\n";
				}
				method += "\t\telse {\n";
				method += "\t\t\tthrow new RuntimeException(\"Won't get here: \" + m.op);\n";
				method += "\t\t}\n";
				method += "\t\treturn new " + next + "(this.ep, openum, m);\n";
				method += "\t}\n";

				as = ps.getAcceptable().iterator();
				first = as.next();
				method += "\n";
				method += "\tpublic enum " + this.classNames.get(ps) + "Enum implements org.scribble2.net.session.OpEnum { ";   // FIXME: should be in methods
				method += SessionGenerator.getOpClassName((Op) first.mid);
				for (; as.hasNext(); )
				{
					IOAction a = as.next();
					method += ", " + SessionGenerator.getOpClassName((Op) a.mid);
				}
				method += "; }\n";
				break;
			}
			default:
			{
				throw new RuntimeException("TODO: " + st);
			}
		}
		return method;
	}
	
	private String getPackageName() // Java output package (not Scribble package)
	{
		//return this.gpn.getPrefix().toString();
		return SessionGenerator.getPackageName(this.gpn);
		//return this.gpn.toString();// + "." + this.role;  // role causes clash with Role constant in session class
	}
	
	private String getOp(Op op)
	{
		return SessionGenerator.getSessionClassName(gpn) + "." + SessionGenerator.getOpClassName(op);
	}
	
	private String getRole(Role role)
	{
		return SessionGenerator.getSessionClassName(gpn) + "." + role.toString();
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
			return (as.size() > 1) ? SocketType.BRANCH : SocketType.RECEIVE;
		}
		else
		{
			throw new RuntimeException("TODO");
		}
	}
	
	public Map<String, String> getClasses()
	{
		return this.classes;
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
	
	/*private String getSessionIncompleteExceptionName()
	{
		return this.gpn.toString().replace('.', '_') + "_" + this.role + "IncompleteException";
	}*/
}
