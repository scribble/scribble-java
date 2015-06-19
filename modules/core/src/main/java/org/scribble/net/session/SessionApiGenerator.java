package org.scribble.net.session;

import java.util.HashMap;
import java.util.Map;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.del.ModuleDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Job;
import org.scribble.visit.JobContext;
import org.scribble.visit.MessageIdCollector;

public class SessionApiGenerator
{
	private final Job job;
	private final GProtocolName gpn;
	private final String clazz;
	private final Map<MessageId, String> mids = new HashMap<>();
	private final Map<Role, String> roles = new HashMap<>();

	public SessionApiGenerator(Job job, GProtocolName gpn) throws ScribbleException
	{
		this.job = job;
		this.gpn = gpn;

		generateOps();
		generateRoles();
		this.clazz = generate();
	}
	
	private void generateOps() throws ScribbleException
	{
		JobContext jc = this.job.getContext();
		Module mod = jc.getModule(gpn.getPrefix());
		GProtocolName sn = gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(sn);
		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		for (MessageId mid : coll.getMessageIds())
		{
			this.mids.put(mid, generateOpClass(mid));
		}
	}
	
	private String generateOpClass(MessageId mid)
	{
		String s = getOpClassName(mid);

		String clazz = "";
		//clazz += "package " + getPackageName(gpn) + ";\n";
		//clazz += "\n";
		//clazz += "import org.scribble.sesstype.name.Op;\n";
		//clazz += "\n";
		//clazz += "public class " + s + " extends Op {\n";
		clazz += "class " + s + " extends Op {\n";
		clazz += "\tprivate static final long serialVersionUID = 1L;\n";
		clazz += "\n";
		clazz += "\tprotected " + s + "() {\n";
		clazz += "\t\tsuper(\"" + mid + "\");\n";
		clazz += "\t}\n";
		clazz += "}";
		return clazz;
	}
	
	public static String getOpClassName(MessageId mid)
	{
		String s = mid.toString();
		if (s.isEmpty() || s.charAt(0) < 65)
		{
			return "_" + s;
		}
		return s;
	}

	private void generateRoles() throws ScribbleException
	{
		JobContext jc = this.job.getContext();
		Module mod = jc.getModule(gpn.getPrefix());
		GProtocolName sn = gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(sn);
		for (Role r : gpd.header.roledecls.getRoles())
		{
			this.roles.put(r, generateRoleClass(r));
		}
	}
	
	private String generateRoleClass(Role r)
	{
		String s = getRoleClassName(r);

		String clazz = "";
		clazz += "class " + s + " extends Role {\n";
		clazz += "\tprivate static final long serialVersionUID = 1L;\n";
		clazz += "\n";
		clazz += "\tprotected " + s + "() {\n";
		clazz += "\t\tsuper(\"" + r + "\");\n";
		clazz += "\t}\n";
		clazz += "}";
		return clazz;
	}

	public static String getRoleClassName(Role r)
	{
		return r.toString();
	}
	
	//public String getSessionClass()
	public Map<String, String> getSessionClass()
	{
		String path = getPackageName(this.gpn).replace('.', '/') + "/" + getSessionClassName(this.gpn) + ".java";
		Map<String, String> map = new HashMap<>();
		map.put(path, this.clazz);
		return map;
	}

	public Map<MessageId, String> getOpClasses()
	{
		return this.mids;
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
		clazz += "import org.scribble.net.session.Session;\n";
		clazz += "import org.scribble.sesstype.name.GProtocolName;\n";
		clazz += "import org.scribble.sesstype.name.Op;\n";
		clazz += "import org.scribble.sesstype.name.Role;\n";
		clazz += "import org.scribble.sesstype.SessionTypeFactory;\n";
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
		for (MessageId mid : this.mids.keySet())
		{
			clazz += generateOp(mid);
		}
		clazz += "\n";
		clazz += "\tpublic " + sn +"() {\n";
		clazz += "\t\tsuper(" + sn + ".impath, " + sn + ".source, " + sn + ".proto);\n";
		clazz += "\t}\n";
		clazz += "}";//\n";
		for (String rc : this.roles.values())
		{
			clazz += "\n\n";
			clazz += rc;
		}
		for (String s : this.mids.values())
		{
			clazz += "\n\n";
			clazz += s;
		}
		return clazz;
	}

	private String generateRole(Role role)
	{
		//return "\tpublic static final Role " + role + " = new Role(\"" + role + "\");\n";
		String rc = getRoleClassName(role);
		return "\tpublic static final " + rc + " " + rc + " = new " + rc + "();\n";
	}
	
	private String generateOp(MessageId mid)
	{
		String s = getOpClassName(mid);
		return "\tpublic static final " + s + " " + s + " = new " + s + "();\n";
	}
}
