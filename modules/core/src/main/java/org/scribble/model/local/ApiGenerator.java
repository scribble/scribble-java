package org.scribble.model.local;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.ast.visit.Job;
import org.scribble.ast.visit.JobContext;
import org.scribble.ast.visit.Projector;
import org.scribble.net.session.SessionGenerator;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.sesstype.name.Role;

public class ApiGenerator
{
	private final Job job;
	private final GProtocolName gpn;
	private final Role role;
	private final LProtocolName lpn;

	private int counter = 1;
	Map<ProtocolState, String> classNames = new HashMap<>();
	private String root = null;
	private Map<String, String> classes = new HashMap<>();  // class name -> class source
	
	private enum SocketType { SEND, RECEIVE, BRANCH, END, INIT }
	private final Map<SocketType, String> SOCKET_CLASSES;

	{
		SOCKET_CLASSES = new HashMap<>();
		SOCKET_CLASSES.put(SocketType.SEND, "org.scribble.net.SendSocket");
		SOCKET_CLASSES.put(SocketType.RECEIVE, "org.scribble.net.ReceiveSocket");
		SOCKET_CLASSES.put(SocketType.BRANCH, "org.scribble.net.BranchSocket");
		SOCKET_CLASSES.put(SocketType.END, "org.scribble.net.EndSocket");
		SOCKET_CLASSES.put(SocketType.INIT, "org.scribble.net.InitSocket");
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
		String init = this.lpn.getSimpleName().toString() + "_" + 0;  // FIXME: factor out with newClassName
		this.classes.put(init, generateInitClass(init));
	}

	private String generateInitClass(String className)
	{
		String clazz = "";
		clazz += "package " + getPackageName() + ";\n";
		clazz += "\n";
		clazz += "import org.scribble.net.session.SessionEndpoint;\n";
		clazz += "import org.scribble.util.ScribbleRuntimeException;\n";
		clazz += "\n";
		clazz += "public class " + className + " extends " + SOCKET_CLASSES.get(SocketType.INIT) + " {\n";
		clazz += "\tpublic " + className + "(SessionEndpoint se) {\n";
		clazz += "\t\tsuper(se);\n";
		clazz += "\t}";
		clazz += "\n\n";
		
		clazz += "\tpublic " + this.root + " init() throws ScribbleRuntimeException {\n";
		clazz += "\t\tsuper.use();\n";
		clazz += "\t\treturn new " + this.root + "(this.ep);\n";
		clazz += "\t}\n";
		
		clazz += "}\n";
		return clazz;
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
		imports += "import org.scribble.net.Buff;\n";
		imports += "import org.scribble.net.ScribMessage;\n";
		imports += "import org.scribble.net.session.SessionEndpoint;\n";
		imports += "import org.scribble.util.ScribbleRuntimeException;\n";
		imports += "\n";
		imports += "import " + SessionGenerator.getPackageName(this.gpn) + "." + SessionGenerator.getSessionClassName(this.gpn) + ";\n";
		//imports += "import org.scribble.util.SessionIncompleteException;\n";
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
		/*if (this.root.equals(className))
		{
			cons += "\tpublic ";
		}
		else*/
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

		JobContext jc = this.job.getContext();
		Module main = jc.getMainModule();

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
					String opref = getOp(a.mid);
					
					if (a.mid.isOp())
					{	
						method += "\tpublic " + next + " send(" + SessionGenerator.getRoleClassName(a.peer) + " role" + ", " + SessionGenerator.getOpClassName(a.mid) + " op";
						if (!a.payload.isEmpty())
						{
							int i = 1;
							for (PayloadType<? extends Kind> pt : a.payload.elems)
							{
								DataType dt = (DataType) pt;  // TODO: if not DataType
								DataTypeDecl dtd = main.getDataTypeDecl(dt);  // FIXME: might not belong to main module
								method += ", " + dtd.extName + " arg" + i++;
							}
						}
						method += ") throws ScribbleRuntimeException, IOException {\n";
						//method += "\t\tsuper.writeScribMessage(" + getRole(a.peer) + ", new ScribMessage(" + opref;
						method += "\t\tsuper.writeScribMessage(role, new ScribMessage(" + opref;
						if (!a.payload.isEmpty())
						{
							int i = 1;
							for (PayloadType<? extends Kind> pt : a.payload.elems)
							{
								method += ", arg" + i++;
							}
						}
						method += "));\n";
					}
					else //if (a.mid.isMessageSigName())
					{	
						MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
						method += "\tpublic " + next + " send(" + SessionGenerator.getRoleClassName(a.peer) + " role, " + msd.extName + " m";
						method += ") throws ScribbleRuntimeException, IOException {\n";
						//method += "\t\tsuper.writeScribMessage(" + getRole(a.peer) + ", m);\n";
						method += "\t\tsuper.writeScribMessage(role, m);\n";
					}
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
				if (a.mid.isOp())
				{
					method += "\tpublic " + next + " receive(" + SessionGenerator.getOpClassName(a.mid) + " op";
					if (!a.payload.isEmpty())
					{
						int i = 1;
						for (PayloadType<? extends Kind> pt : a.payload.elems)
						{
							DataType dt = (DataType) pt;  // TODO: if not DataType
							DataTypeDecl dtd = main.getDataTypeDecl(dt);
							//method += ", Buff<" + dtd.extName + "> arg" + i++;
							method += ", Buff<? super " + dtd.extName + "> arg" + i++;
						}
					}
					method += ") throws ScribbleRuntimeException, IOException, ClassNotFoundException {\n";
					method += "\t\tScribMessage m = super.readScribMessage(" + getRole(a.peer) + ");\n";
					if (!a.payload.isEmpty())
					{
						int i = 1;
						for (PayloadType<? extends Kind> pt : a.payload.elems)
						{
							DataType dt = (DataType) pt;  // TODO: if not DataType
							DataTypeDecl dtd = main.getDataTypeDecl(dt);
							method += "\t\targ" + i + ".val = (" + dtd.extName + ") m.payload[" + (i++ - 1) +"];\n";
						}
					}
				}
				else //if (a.mid.isMessageSigName())
				{
					MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
					method += "\tpublic " + next + " receive(" + SessionGenerator.getOpClassName(a.mid) + " op";
					//method += ", Buff<" + msd.extName + "> b";
					method += ", Buff<? super " + msd.extName + "> b";
					method += ") throws ScribbleRuntimeException, IOException, ClassNotFoundException {\n";
					method += "\t\tScribMessage m = super.readScribMessage(" + getRole(a.peer) + ");\n";
					method += "\t\tb.val = (" + msd.extName + ") m;\n";
				}
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
					if (a.mid.isOp())
					{
						// FIXME: problem if package and protocol have the same name
						clazz += "\tpublic " + next + " receive(" + SessionGenerator.getPackageName(this.gpn) + "." + SessionGenerator.getOpClassName(a.mid) + " op";
						//clazz += "\tpublic " + next + " receive(" + SessionGenerator.getOpClassName(a.mid) + " op";
						if (!a.payload.isEmpty())
						{
							int i = 1;
							for (PayloadType<? extends Kind> pt : a.payload.elems)
							{
								DataType dt = (DataType) pt;  // TODO: if not DataType
								DataTypeDecl dtd = main.getDataTypeDecl(dt);
								//clazz += ", Buff<" + dtd.extName + "> arg" + i++;
								clazz += ", Buff<? super " + dtd.extName + "> arg" + i++;
							}
						}
						clazz += ") throws ScribbleRuntimeException, IOException, ClassNotFoundException {\n";
						//clazz += "\t\tScribMessage m = super.readScribMessage(" + getRole(a.peer) + ");\n";
						clazz += "\t\tsuper.use();\n";
						clazz += "\t\tif (!this.m.op.equals(" + getOp(a.mid) + ")) {\n";
						clazz += "\t\t\tthrow new ScribbleRuntimeException(\"Wrong branch, received: \" + this.m.op);\n";
						clazz += "\t\t}\n";
						if (!a.payload.isEmpty())
						{
							int i = 1;
							for (PayloadType<? extends Kind> pt : a.payload.elems)
							{
								DataType dt = (DataType) pt;  // TODO: if not DataType
								DataTypeDecl dtd = main.getDataTypeDecl(dt);
								clazz += "\t\targ" + i + ".val = (" + dtd.extName + ") m.payload[" + (i++ - 1) +"];\n";
							}
						}
					}
					else //if (a.mid.isMessageSigName())
					{
						MessageSigNameDecl msd = main.getMessageSigDecl(((MessageSigName) a.mid).getSimpleName());  // FIXME: might not belong to main module
						// FIXME: problem if package and protocol have the same name
						clazz += "\tpublic " + next + " receive(" + SessionGenerator.getPackageName(this.gpn) + "." + SessionGenerator.getOpClassName(a.mid) + " op";
						//clazz += ", Buff<" + msd.extName + "> b";
						clazz += ", Buff<? super " + msd.extName + "> b";
						clazz += ") throws ScribbleRuntimeException, IOException, ClassNotFoundException {\n";
						clazz += "\t\tsuper.use();\n";
						clazz += "\t\tif (!this.m.op.equals(" + getOp(a.mid) + ")) {\n";
						clazz += "\t\t\tthrow new ScribbleRuntimeException(\"Wrong branch, received: \" + this.m.op);\n";
						clazz += "\t\t}\n";
						clazz += "\t\tb.val = (" + msd.extName + ") m;\n";
					}
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
				method += "\t\tif (m.op.equals(" + getOp(first.mid) + ")) {\n";
				//method += "\t\t\treturn " + this.classNames.get(ps) + "Enum." + first.mid.toString() + ";\n";
				method += "\t\t\topenum = " + this.classNames.get(ps) + "Enum." + SessionGenerator.getOpClassName(first.mid) + ";\n";
				method += "\t\t}\n";
				for (; as.hasNext(); )
				{
					IOAction a = as.next();
					method += "\t\telse if (m.op.equals(" + getOp(a.mid) + ")) {\n";
					//method += "\t\t\treturn " + this.classNames.get(ps) + "Enum." + a.mid.toString() + ";\n";
					method += "\t\t\topenum = " + this.classNames.get(ps) + "Enum." + SessionGenerator.getOpClassName(a.mid) + ";\n";
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
				method += "\tpublic enum " + this.classNames.get(ps) + "Enum implements org.scribble.net.session.OpEnum { ";   // FIXME: should be in methods
				method += SessionGenerator.getOpClassName(first.mid);
				for (; as.hasNext(); )
				{
					IOAction a = as.next();
					method += ", " + SessionGenerator.getOpClassName(a.mid);
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
	
	private String getOp(MessageId mid)
	{
		return SessionGenerator.getSessionClassName(gpn) + "." + SessionGenerator.getOpClassName(mid);
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
		Map<String, String> map = new HashMap<String, String>();
		for (String s : this.classes.keySet())
		{
			map.put(SessionGenerator.getPackageName(this.gpn).replace('.', '/') + '/' + s + ".java", this.classes.get(s));
		}
		return map;
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
