package org.scribble.codegen;

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
import org.scribble.visit.MessageIdCollector;

public class SessionApiGenerator
{
	private final Job job;
	private final GProtocolName gpn;  // full name

	private final ClassBuilder cb = new ClassBuilder();
	private final Map<Role, ClassBuilder> roles = new HashMap<>();
	private final Map<MessageId<?>, ClassBuilder> mids = new HashMap<>();

	public SessionApiGenerator(Job job, GProtocolName fullname) throws ScribbleException
	{
		this.job = job;
		this.gpn = fullname;

		constructRoleClasses();
		constructOpClasses();
		constructSessionClass();  // Depends on the above two being done first
	}
	
	public Map<String, String> generateSessionClass()
	{
		String simpname = getSessionClassName(this.gpn);
		String path = getPackageName(this.gpn).replace('.', '/') + "/" + simpname + ".java";
		StringBuilder sb = new StringBuilder(this.cb.generate());
		this.roles.values().forEach((cb) -> sb.append("\n\n").append(cb.generate()) );
		this.mids.values().forEach((cb) -> sb.append("\n\n").append(cb.generate()) );
		Map<String, String> map = new HashMap<>();
		map.put(path, sb.toString());
		return map;
	}

	/*public Map<MessageId<?>, String> getOpClasses()
	{
		return this.mids;
	}*/
	
	private void constructSessionClass()
	{
		String packname = getPackageName(this.gpn);
		String simpname = getSessionClassName(this.gpn);
		
		this.cb.setName(simpname);
		this.cb.setPackage(packname);

		this.cb.addImports(
				"java.util.LinkedList", "java.util.List", "org.scribble.net.session.Session",
				"org.scribble.sesstype.name.GProtocolName", "org.scribble.sesstype.name.Op",
				"org.scribble.sesstype.name.Role", "org.scribble.sesstype.SessionTypeFactory");

		this.cb.addModifiers("public");
		this.cb.setSuperClass("Session");
		
		FieldBuilder fb1 = this.cb.newField("impath");
		fb1.setType("List<String>");
		fb1.addModifiers("public", "static", "final");
		fb1.setExpression("new LinkedList<>()");

		FieldBuilder fb2 = this.cb.newField("source");
		fb2.setType("String");
		fb2.addModifiers("public", "static", "final");
		fb2.setExpression("\"getSource\"");

		FieldBuilder fb3 = this.cb.newField("proto");
		fb3.setType("GProtocolName");
		fb3.addModifiers("public", "static", "final");
		fb3.setExpression("SessionTypeFactory.parseGlobalProtocolName(\"" + gpn + "\")");

		this.roles.keySet().stream().forEach((r) -> addRoleField(this.cb, r));
		this.mids.keySet().stream().forEach((mid) -> addOpField(this.cb, mid));

		MethodBuilder ctor = this.cb.newConstructor();
		ctor.addModifiers("public");
		ctor.setName(simpname);
		ctor.addBodyLine("super(" + simpname + ".impath, " + simpname + ".source, " + simpname + ".proto);");
	}

	private static void addRoleField(ClassBuilder cb, Role role)
	{
		addSingletonConstant(cb, getRoleClassName(role));
	}
	
	private static void addOpField(ClassBuilder cb, MessageId<?> mid)
	{
		addSingletonConstant(cb, getOpClassName(mid));
	}
	
	private static void addSingletonConstant(ClassBuilder cb, String type)
	{
		FieldBuilder fb = cb.newField(type);
		fb.setType(type);
		fb.addModifiers("public", "static", "final");
		fb.setExpression("new " + type + "()");
	}
	
	private void constructOpClasses() throws ScribbleException
	{
		Module mod = this.job.getContext().getModule(gpn.getPrefix());
		GProtocolName simpname = gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		coll.getNames().stream().forEach((mid) -> this.mids.put(mid, constructOpClass(mid)));
	}

	private void constructRoleClasses() throws ScribbleException
	{
		Module mod = this.job.getContext().getModule(gpn.getPrefix());
		GProtocolName simpname = gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		gpd.header.roledecls.getRoles().stream().forEach((r) -> this.roles.put(r, constructRoleClass(r))); 
	}
	
	private static ClassBuilder constructOpClass(MessageId<?> mid)
	{
		return constructSingletonClass("Op", getOpClassName(mid));
	}
	
	private static ClassBuilder constructRoleClass(Role r)
	{
		return constructSingletonClass("Role", getRoleClassName(r));
	}

	private static ClassBuilder constructSingletonClass(String superc, String name)
	{
		ClassBuilder cb = new ClassBuilder();
		cb.setName(name);
		cb.setSuperClass(superc);

		FieldBuilder fb = cb.newField("serialVersionUID");
		fb.addModifiers("private", "static", "final");
		fb.setType("long");
		fb.setExpression("1L");
		
		MethodBuilder mb = cb.newConstructor();
		mb.addModifiers("protected");
		mb.addBodyLine("super(\"" + name + "\");");

		return cb;
	}
	
	public static String getSessionClassName(GProtocolName gpn)
	{
		return gpn.getSimpleName().toString();
	}

	public static String getPackageName(GProtocolName gpn)
	{
		return gpn.getPrefix().getPrefix().toString();  // Java output package name (not Scribble package)
	}
	
	public static String getRoleClassName(Role r)
	{
		return r.toString();
	}
	
	public static String getOpClassName(MessageId<?> mid)
	{
		String s = mid.toString();
		return (s.isEmpty() || s.charAt(0) < 65) ? "_" + s : s;
	}
}
