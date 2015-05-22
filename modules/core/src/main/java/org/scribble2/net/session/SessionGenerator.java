package org.scribble2.net.session;

import java.util.HashMap;
import java.util.Map;

import org.scribble2.model.Module;
import org.scribble2.model.del.ModuleDel;
import org.scribble2.model.global.GProtocolDecl;
import org.scribble2.model.visit.Job;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.OpCollector;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.sesstype.name.Op;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class SessionGenerator
{
	private final Job job;
	private final GProtocolName gpn;
	private final String clazz;
	private final Map<Op, String> ops = new HashMap<>();

	public SessionGenerator(Job job, GProtocolName gpn) throws ScribbleException
	{
		this.job = job;
		this.gpn = gpn;

		generateOps();
		this.clazz = generate();
	}
	
	private void generateOps() throws ScribbleException
	{
		JobContext jc = this.job.getContext();
		Module mod = jc.getModule(gpn.getPrefix());
		GProtocolName sn = gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(sn);
		OpCollector coll = new OpCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		for (Op op : coll.getOps())
		{
			this.ops.put(op, generateOpClass(op));

		}
	}
	
	public static String getOpClassName(Op op)
	{
		String s = op.toString();
		if (s.isEmpty() || s.charAt(0) < 65)
		{
			return "_" + s;
		}
		return s;
	}
	
	private String generateOpClass(Op op)
	{
		String clazz = "";
		String s = getOpClassName(op);
		clazz += "class " + s + " extends Op {\n";
		clazz += "\tprivate static final long serialVersionUID = 1L;\n";
		clazz += "\n";
		clazz += "\tprotected " + s + "() {\n";
		clazz += "\t\tsuper(\"" + op + "\");\n";
		clazz += "\t}\n";
		clazz += "}";
		return clazz;
	}
	
	public String getSessionClass()
	{
		return this.clazz;
	}
	
	public static String getPackageName(GProtocolName gpn)
	{
		return gpn.getPrefix().toString();  // Java output package name (not Scribble package)
	}
	
	public static String getSessionClassName(GProtocolName gpn)
	{
		return gpn.getSimpleName().toString();
	}
	
	private String generate()
	{
		JobContext jc = this.job.getContext();
		GProtocolDecl gpd = (GProtocolDecl) jc.getModule(gpn.getPrefix()).getProtocolDecl(this.gpn.getSimpleName());
		String packn = getPackageName(this.gpn);
		String sn = getSessionClassName(this.gpn);

		String clazz = "";
		clazz += "package " + packn + ";\n";
		clazz += "\n";
		clazz += "import java.util.LinkedList;\n";
		clazz += "import java.util.List;\n";
		clazz += "\n";
		clazz += "import org.scribble2.net.session.Session;\n";
		clazz += "import org.scribble2.sesstype.name.GProtocolName;\n";
		clazz += "import org.scribble2.sesstype.name.Op;\n";
		clazz += "import org.scribble2.sesstype.name.Role;\n";
		clazz += "import org.scribble2.sesstype.SessionTypeFactory;\n";
		clazz += "\n";
		clazz += "public class " + sn + " extends Session {\n";
		clazz += "\tpublic static final List<String> impath = new LinkedList<>();\n";
		clazz += "\tpublic static final String source = \"" + "getSource" + "\";\n";
		clazz += "\tpublic static final GProtocolName proto = SessionTypeFactory.parseGlobalProtocolName(\"" + gpn + "\");\n";
		clazz += "\n";
		for (Role role : gpd.header.roledecls.getRoles())
		{
			clazz += generateRole(role);
		}
		clazz += "\n";
		for (Op op : this.ops.keySet())
		{
			clazz += generateOp(op);
		}
		clazz += "\n";
		clazz += "\tpublic " + sn +"() {\n";
		clazz += "\t\tsuper(" + sn + ".impath, " + sn + ".source, " + sn + ".proto);\n";
		clazz += "\t}\n";
		clazz += "}\n";
		for (String s : this.ops.values())
		{
			clazz += "\n";
			clazz += s;
		}
		return clazz;
	}

	private String generateRole(Role role)
	{
		return "\tpublic static final Role " + role + " = new Role(\"" + role + "\");\n";
	}
	
	private String generateOp(Op op)
	{
		String s = getOpClassName(op);
		return "\tpublic static final " + s + " " + s + " = new " + s + "();\n";
	}
}
