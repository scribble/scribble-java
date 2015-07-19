package org.scribble.codegen;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	private final GProtocolName gpn;  // full name
	//private final String clazz;
	private final Map<MessageId<?>, String> mids = new HashMap<>();
	private final Map<Role, String> roles = new HashMap<>();
	
	private final Map<String, ClassBuilder> classes = new HashMap<>();

	public SessionApiGenerator(Job job, GProtocolName fullname) throws ScribbleException
	{
		this.job = job;
		this.gpn = fullname;
		
		ClassBuilder cb = new ClassBuilder();
		String packname = getPackageName(this.gpn);
		String simpname = getSessionClassName(this.gpn);
		String typename = packname + "." + simpname;
		cb.setName(simpname);
		this.classes.put(typename, cb);

		generateRoles();
		generateOps();
		//this.clazz = generate();
		construct();
	}
	
	private void construct()
	{
		JobContext jc = this.job.getContext();
		GProtocolDecl gpd = (GProtocolDecl) jc.getModule(gpn.getPrefix()).getProtocolDecl(this.gpn.getSimpleName());
		String packname = getPackageName(this.gpn);
		String simpname = getSessionClassName(this.gpn);
		String fullname = packname + "." + simpname;  // Factor out with constructor
		
		ClassBuilder cb = this.classes.get(fullname);
		
		cb.setPackage(packname);

		cb.addImports(
				"java.util.LinkedList", "java.util.List", "org.scribble.net.session.Session",
				"org.scribble.sesstype.name.GProtocolName", "org.scribble.sesstype.name.Op",
				"org.scribble.sesstype.name.Role", "org.scribble.sesstype.SessionTypeFactory");

		cb.addModifiers("public");
		cb.setSuperClass("Session");
		
		FieldBuilder fb1 = cb.newField("impath");
		fb1.setType("List<String>");
		fb1.addModifiers("public", "static", "final");
		fb1.setExpression("new LinkedList<>()");

		FieldBuilder fb2 = cb.newField("source");
		fb2.setType("String");
		fb2.addModifiers("public", "static", "final");
		fb2.setExpression("\"getSource\"");

		FieldBuilder fb3 = cb.newField("proto");
		fb3.setType("GProtocolName");
		fb3.addModifiers("public", "static", "final");
		fb3.setExpression("SessionTypeFactory.parseGlobalProtocolName(\"" + gpn + "\")");

		for (Role role : gpd.header.roledecls.getRoles())
		{
			generateRole(cb, role);
		}

		for (MessageId<?> mid : this.mids.keySet())
		{
			generateOp(cb, mid);
		}

		MethodBuilder ctor = cb.newConstructor();
		ctor.addModifiers("public");
		ctor.setName(simpname);
		ctor.addBodyLine("super(" + simpname + ".impath, " + simpname + ".source, " + simpname + ".proto);");

		/*for (String rc : this.roles.values())
		{
			clazz += "\n\n";
			clazz += rc;
		}
		for (String s : this.mids.values())
		{
			clazz += "\n\n";
			clazz += s;
		}*/
		//return cb.generate();
	}
	
	private void generateOps() throws ScribbleException
	{
		JobContext jc = this.job.getContext();
		Module mod = jc.getModule(gpn.getPrefix());
		GProtocolName sn = gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(sn);
		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		for (MessageId<?> mid : coll.getNames())
		{
			//this.mids.put(mid, generateOpClass(mid));
			this.mids.put(mid, null);
			generateOpClass(mid);
		}
	}
	
	private void generateOpClass(MessageId<?> mid)
	{
		ClassBuilder cb = new ClassBuilder();
		String name = getOpClassName(mid);
		cb.setName(name);
		cb.setSuperClass("Op");
		this.classes.put(name, cb);

		FieldBuilder fb = cb.newField("serialVersionUID");
		fb.addModifiers("private", "static", "final");
		fb.setType("long");
		fb.setExpression("1L");
		
		MethodBuilder mb = cb.newConstructor();
		mb.addModifiers("protected");
		mb.addBodyLine("super(\"" + mid + "\");");
	}
	
	public static String getOpClassName(MessageId<?> mid)
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
			//this.roles.put(r, generateRoleClass(r));
			generateRoleClass(r);
		}
	}
	
	private void generateRoleClass(Role r)
	{
		ClassBuilder cb = new ClassBuilder();
		String name = getRoleClassName(r);
		cb.setName(name);
		cb.setSuperClass("Role");
		this.classes.put(name, cb);

		FieldBuilder fb = cb.newField("serialVersionUID");
		fb.addModifiers("private", "static", "final");
		fb.setType("long");
		fb.setExpression("1L");
		
		MethodBuilder mb = cb.newConstructor();
		mb.addModifiers("protected");
		mb.addBodyLine("super(\"" + r + "\");");
	}

	public static String getRoleClassName(Role r)
	{
		return r.toString();
	}
	
	//public String getSessionClass()
	public Map<String, String> getSessionClass()
	{
		String simpname = getSessionClassName(this.gpn);
		String fullname = getPackageName(this.gpn) + "." + simpname;
		String path = getPackageName(this.gpn).replace('.', '/') + "/" + simpname + ".java";
		Map<String, String> map = new HashMap<>();
		
		String res = this.classes.get(fullname).generate();
		//res += "\n\n" + this.classes.keySet().stream()
				/*.collect(Collector.of(
					StringBuilder::new, 
					(sb, s) -> { if (!s.equals(fullname)) { sb.append(this.classes.get(s).generate()).append("\n\n"); } },
					StringBuilder::append,
					StringBuilder::toString));*/
				/*.filter((k) -> !k.equals(fullname))
				  .map((k) -> this.classes.get(k).generate()).collect(Collectors.joining("\n\n"));*/
		for (Entry<String, ClassBuilder> e : this.classes.entrySet())
		{
			if (!e.getKey().equals(fullname))
			{
				res += "\n\n" + e.getValue().generate();
			}
		}
		
		map.put(path, res);
		return map;
	}

	public Map<MessageId<?>, String> getOpClasses()
	{
		return this.mids;
	}
	
	public static String getPackageName(GProtocolName gpn)
	{
		//return gpn.getPrefix().toString();  // Java output package name (not Scribble package)
		return gpn.getPrefix().getPrefix().toString();  // Java output package name (not Scribble package)
	}
	
	public static String getSessionClassName(GProtocolName gpn)
	{
		return gpn.getSimpleName().toString();
	}

	private void generateRole(ClassBuilder cb, Role role)
	{
		//return "\tpublic static final Role " + role + " = new Role(\"" + role + "\");\n";
		String rc = getRoleClassName(role);
		//return "\tpublic static final " + rc + " " + rc + " = new " + rc + "();\n";
		FieldBuilder fb = cb.newField(rc);
		fb.setType(rc);
		fb.addModifiers("public", "static", "final");
		fb.setExpression("new " + rc + "()");
	}
	
	private void generateOp(ClassBuilder cb, MessageId<?> mid)
	{
		String oc = getOpClassName(mid);
		//return "\tpublic static final " + s + " " + s + " = new " + s + "();\n";
		FieldBuilder fb = cb.newField(oc);
		fb.setType(oc);
		fb.addModifiers("public", "static", "final");
		fb.setExpression("new " + oc + "()");
	}
}
