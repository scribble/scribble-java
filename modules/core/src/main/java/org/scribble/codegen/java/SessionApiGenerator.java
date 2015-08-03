package org.scribble.codegen.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	public static final String GPROTOCOLNAME_CLASS = "org.scribble.sesstype.name.GProtocolName";
	public static final String OP_CLASS = "org.scribble.sesstype.name.Op";
	public static final String ROLE_CLASS = "org.scribble.sesstype.name.Role";
	public static final String SESSION_CLASS = "org.scribble.net.session.Session";
	public static final String SESSIONTYPEFACTORY_CLASS = "org.scribble.sesstype.SessionTypeFactory";

	private static final String IMPATH_FIELD = "IMPATH";
	private static final String SOURCE_FIELD = "SOURCE";
	private static final String PROTO_FIELD = "PROTO";

	private final Job job;
	private final GProtocolName gpn;  // full name

	private final ClassBuilder cb = new ClassBuilder();
	private final Set<Role> roles = new HashSet<>();
	private final Set<MessageId<?>> mids = new HashSet<>();

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
		//this.roles.values().forEach((cb) -> sb.append("\n\n").append(cb.generate()) );
		//this.mids.values().forEach((cb) -> sb.append("\n\n").append(cb.generate()) );
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
		this.cb.addImports("java.util.LinkedList", "java.util.List");
		this.cb.addModifiers(ClassBuilder.PUBLIC);
		this.cb.setSuperClass(SessionApiGenerator.SESSION_CLASS);
		
		FieldBuilder fb1 = this.cb.newField(SessionApiGenerator.IMPATH_FIELD);
		fb1.setType("List<String>");
		fb1.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.STATIC, ClassBuilder.FINAL);
		fb1.setExpression("new LinkedList<>()");

		FieldBuilder fb2 = this.cb.newField(SessionApiGenerator.SOURCE_FIELD);
		fb2.setType("String");
		fb2.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.STATIC, ClassBuilder.FINAL);
		fb2.setExpression("\"getSource\"");

		FieldBuilder fb3 = this.cb.newField(SessionApiGenerator.PROTO_FIELD);
		fb3.setType(SessionApiGenerator.GPROTOCOLNAME_CLASS);
		fb3.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.STATIC, ClassBuilder.FINAL);
		fb3.setExpression(SessionApiGenerator.SESSIONTYPEFACTORY_CLASS
				+ ".parseGlobalProtocolName(\"" + gpn + "\")");

		this.roles.stream().forEach((r) -> addRoleField(this.cb, r));
		this.mids.stream().forEach((mid) -> addOpField(this.cb, mid));

		MethodBuilder ctor = this.cb.newConstructor();
		ctor.addModifiers(ClassBuilder.PUBLIC);
		ctor.setName(simpname);
		ctor.addBodyLine(ClassBuilder.SUPER + "("
				+ simpname + "." + SessionApiGenerator.IMPATH_FIELD + ", "
				+ simpname + "." + SessionApiGenerator.SOURCE_FIELD + ", "
				+ simpname + "." + SessionApiGenerator.PROTO_FIELD + ");");
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
		fb.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.STATIC, ClassBuilder.FINAL);
		fb.setExpression(ClassBuilder.NEW + " " + type + "()");
	}
	
	private void constructOpClasses() throws ScribbleException
	{
		Module mod = this.job.getContext().getModule(gpn.getPrefix());
		GProtocolName simpname = gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		for (MessageId<?> mid : coll.getNames())
		{
			constructOpClass(this.cb.newClass(), mid);
			this.mids.add(mid);
		}
	}

	private void constructRoleClasses() throws ScribbleException
	{
		Module mod = this.job.getContext().getModule(gpn.getPrefix());
		GProtocolName simpname = gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		for (Role r : gpd.header.roledecls.getRoles())
		{
			constructRoleClass(this.cb.newClass(), r);
			this.roles.add(r);
		}
	}
	
	private static ClassBuilder constructRoleClass(ClassBuilder cb, Role r)
	{
		return constructSingletonClass(cb, SessionApiGenerator.ROLE_CLASS, getRoleClassName(r));
	}

	private static ClassBuilder constructOpClass(ClassBuilder cb, MessageId<?> mid)
	{
		return constructSingletonClass(cb, SessionApiGenerator.OP_CLASS, getOpClassName(mid));
	}
	
	private static ClassBuilder constructSingletonClass(ClassBuilder cb, String superc, String type)
	{
		cb.setName(type);
		cb.setSuperClass(superc);

		FieldBuilder fb = cb.newField("serialVersionUID");
		fb.addModifiers(ClassBuilder.PRIVATE, ClassBuilder.STATIC, ClassBuilder.FINAL);
		fb.setType("long");
		fb.setExpression("1L");
		
		MethodBuilder mb = cb.newConstructor();
		mb.addModifiers(ClassBuilder.PROTECTED);
		mb.addBodyLine(ClassBuilder.SUPER + "(\"" + type + "\");");

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
		return (s.isEmpty() || s.charAt(0) < 65) ? "_" + s : s;  // Hacky?
	}
}
