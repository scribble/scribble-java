/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.codegen.java.endpointapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.ConstructorBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.del.ModuleDel;
import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.util.MessageIdCollector;

public class SessionApiGenerator extends ApiGen
{
	public static final String GPROTOCOLNAME_CLASS = "org.scribble.sesstype.name.GProtocolName";
	public static final String OP_CLASS = "org.scribble.sesstype.name.Op";
	public static final String ROLE_CLASS = "org.scribble.sesstype.name.Role";
	public static final String SESSION_CLASS = "org.scribble.runtime.net.session.Session";
	public static final String SESSIONTYPEFACTORY_CLASS = "org.scribble.sesstype.SessionTypeFactory";

	private static final String IMPATH_FIELD = "IMPATH";
	private static final String SOURCE_FIELD = "SOURCE";
	private static final String PROTO_FIELD = "PROTO";

	private final Set<Role> roles = new HashSet<>();
	private final Set<MessageId<?>> mids = new HashSet<>();
	
	private final ClassBuilder cb = new ClassBuilder();
	private final Map<String, ClassBuilder> classes = new HashMap<>();  // All classes in same package, for protected constructor access
	
	public SessionApiGenerator(Job job, GProtocolName fullname) throws ScribbleException
	{
		super(job, fullname);
		constructRoleClasses();
		constructOpClasses();
		constructSessionClass();  // Depends on the above two being done first
	}
	
	@Override
	public Map<String, String> generateApi()
	{
		String simpname = getSessionClassName(this.gpn);
		//String path = getPackageName(this.gpn).replace('.', '/') + "/" + simpname + ".java";
		String path = makePath(this.gpn, simpname);
		StringBuilder sb = new StringBuilder(this.cb.build());
		//this.roles.values().forEach((cb) -> sb.append("\n\n").append(cb.generate()) );
		//this.mids.values().forEach((cb) -> sb.append("\n\n").append(cb.generate()) );
		Map<String, String> map = new HashMap<>();
		map.put(path, sb.toString());
		
		this.classes.keySet().stream().forEach((n) -> map.put(n, this.classes.get(n).build()));
		
		return map;
	}

	private static String makePath(GProtocolName gpn, String simpname)
	{
		return getEndpointApiRootPackageName(gpn).replace('.', '/') + "/" + simpname + ".java";
	}

	/*public Map<MessageId<?>, String> getOpClasses()
	{
		return this.mids;
	}*/
	
	private void constructSessionClass()
	{
		String packname = getEndpointApiRootPackageName(this.gpn);
		String simpname = getSessionClassName(this.gpn);
		
		this.cb.setName(simpname);
		this.cb.setPackage(packname);
		this.cb.addImports(/*"java.io.IOException", */"java.util.Arrays", "java.util.Collections", "java.util.LinkedList", "java.util.List");
		//this.cb.addImports("org.scribble.main.ScribbleRuntimeException", "org.scribble.net.session.SessionEndpoint", "org.scribble.net.ScribMessageFormatter");
		this.cb.addImports("org.scribble.sesstype.name.Role");
		this.cb.addImports(getRolesPackageName(this.gpn) + ".*");
		if (!this.mids.isEmpty())
		{
			this.cb.addImports(getOpsPackageName(this.gpn) + ".*");
		}
		this.cb.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.FINAL);
		this.cb.setSuperClass(SessionApiGenerator.SESSION_CLASS);
		
		FieldBuilder fb1 = this.cb.newField(SessionApiGenerator.IMPATH_FIELD);
		fb1.setType("List<String>");
		fb1.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.STATIC, JavaBuilder.FINAL);
		fb1.setExpression("new LinkedList<>()");

		FieldBuilder fb2 = this.cb.newField(SessionApiGenerator.SOURCE_FIELD);
		fb2.setType("String");
		fb2.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.STATIC, JavaBuilder.FINAL);
		fb2.setExpression("\"getSource\"");

		FieldBuilder fb3 = this.cb.newField(SessionApiGenerator.PROTO_FIELD);
		fb3.setType(SessionApiGenerator.GPROTOCOLNAME_CLASS);
		fb3.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.STATIC, JavaBuilder.FINAL);
		fb3.setExpression(SessionApiGenerator.SESSIONTYPEFACTORY_CLASS
				+ ".parseGlobalProtocolName(\"" + gpn + "\")");

		this.roles.stream().forEach((r) -> addRoleField(this.cb, r));
		this.mids.stream().forEach((mid) -> addOpField(this.cb, mid));

		ConstructorBuilder ctor = this.cb.newConstructor();
		ctor.addModifiers(JavaBuilder.PUBLIC);
		//ctor.setName(simpname);  // ?
		ctor.addBodyLine(JavaBuilder.SUPER + "("
				+ simpname + "." + SessionApiGenerator.IMPATH_FIELD + ", "
				+ simpname + "." + SessionApiGenerator.SOURCE_FIELD + ", "
				+ simpname + "." + SessionApiGenerator.PROTO_FIELD + ");");
		
		FieldBuilder fb4 = this.cb.newField("ROLES");
		fb4.setType("List<Role>");
		fb4.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.STATIC, JavaBuilder.FINAL);
		String roles = "Collections.unmodifiableList(Arrays.asList(new Role[] {";
		roles += this.roles.stream().map((r) -> r.toString()).collect(Collectors.joining(", "));
		roles += "}))";
		fb4.setExpression(roles);
		
		/*for (Role r : this.roles)
		{
			String role = getRoleClassName(r);
			MethodBuilder mb = this.cb.newMethod("project");  // No: use new on SessionEndpoint directly for Eclipse resource leak warning
			mb.addModifiers(ClassBuilder.PUBLIC);
			mb.setReturn("SessionEndpoint<" + simpname + ", " + role + ">");
			mb.addParameters(role + " role", "ScribMessageFormatter smf");
			mb.addExceptions("ScribbleRuntimeException", "IOException");
			mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.SUPER + ".project(role, smf);");
		}*/

		MethodBuilder mb = this.cb.newMethod("getRoles");
		mb.addAnnotations("@Override");
		mb.addModifiers(JavaBuilder.PUBLIC);
		mb.setReturn("List<Role>");
		mb.addParameters();
		mb.addBodyLine(JavaBuilder.RETURN + " " + simpname + ".ROLES;");
	}

	private void addRoleField(ClassBuilder cb, Role role)
	{
		addSingletonConstant(cb, "roles", getRoleClassName(role));  // FIXME: factor out 
	}
	
	private void addOpField(ClassBuilder cb, MessageId<?> mid)
	{
		addSingletonConstant(cb, "ops", getOpClassName(mid));  // FIXME: factor out 
	}
	
	private void addSingletonConstant(ClassBuilder cb, String subpack, String type)
	{
		FieldBuilder fb = cb.newField(type);
		fb.setType(type);
		fb.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.STATIC, JavaBuilder.FINAL);
		//fb.setExpression(ClassBuilder.NEW + " " + type + "()");
		fb.setExpression(getEndpointApiRootPackageName(this.gpn) + "." + subpack + "." + type + "." + type);  // Currently requires source Scribble to be in a package that is not the root -- can fix by generating to a subpackage based on Module and/or protocol
	}
	
	private void constructOpClasses() throws ScribbleException
	{
		Module mod = this.job.getContext().getModule(this.gpn.getPrefix());
		GProtocolName simpname = this.gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		for (MessageId<?> mid : coll.getNames())
		{
			//constructOpClass(this.cb.newClass(), mid);
			//constructOpClass(new ClassBuilder(), getEndpointApiRootPackageName(this.gpn), mid);
			constructOpClass(new ClassBuilder(), getOpsPackageName(this.gpn), mid);
			this.mids.add(mid);
		}
	}

	private void constructRoleClasses() throws ScribbleException
	{
		Module mod = this.job.getContext().getModule(this.gpn.getPrefix());
		GProtocolName simpname = this.gpn.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		for (Role r : gpd.header.roledecls.getRoles())
		{
			//constructRoleClass(this.cb.newClass(), r);
			//constructRoleClass(new ClassBuilder(), getEndpointApiRootPackageName(this.gpn), r);
			constructRoleClass(new ClassBuilder(), getRolesPackageName(this.gpn), r);
			this.roles.add(r);
		}
	}
	
	private ClassBuilder constructRoleClass(ClassBuilder cb, String pack, Role r)
	{
		return constructSingletonClass(cb, pack, SessionApiGenerator.ROLE_CLASS, getRoleClassName(r));
	}

	private ClassBuilder constructOpClass(ClassBuilder cb, String pack, MessageId<?> mid)
	{
		return constructSingletonClass(cb, pack, SessionApiGenerator.OP_CLASS, getOpClassName(mid));
	}
	
	private ClassBuilder constructSingletonClass(ClassBuilder cb, String pack, String superc, String type)
	{
		cb.setName(type);
		cb.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.FINAL);
		cb.setPackage(pack);
		cb.setSuperClass(superc);

		FieldBuilder fb = cb.newField("serialVersionUID");
		fb.addModifiers(JavaBuilder.PRIVATE, JavaBuilder.STATIC, JavaBuilder.FINAL);
		fb.setType("long");
		fb.setExpression("1L");
		
		FieldBuilder fb2 = cb.newField(type);
		fb2.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.STATIC, JavaBuilder.FINAL);
		fb2.setType(type);
		fb2.setExpression(JavaBuilder.NEW + " " + type + "()");
		
		ConstructorBuilder mb = cb.newConstructor();
		mb.addModifiers(JavaBuilder.PRIVATE);
		mb.addBodyLine(JavaBuilder.SUPER + "(\"" + type + "\");");

		this.classes.put(pack.replace('.', '/') + "/" + type + ".java", cb);
		return cb;
	}
	
	// Returns the simple Session Class name
	public static String getSessionClassName(GProtocolName gpn)
	{
		return gpn.getSimpleName().toString();
	}

	// Returns the Java output package: currently the Scribble package excluding the Module
	public static String getEndpointApiRootPackageName(GProtocolName gpn)
	{
		//return gpn.getPrefix().getPrefix().toString();  // Java output package name (not Scribble package)
		// FIXME: factor out with StateChannelApiGenerator.generateClasses
		//return gpn.getPrefix().getPrefix().toString() + "." + gpn.getSimpleName();  // Java output package name (not Scribble package)
		return gpn.toString();
	}

	public static String getRolesPackageName(GProtocolName gpn)
	{
		return getEndpointApiRootPackageName(gpn) + ".roles";
	}

	public static String getOpsPackageName(GProtocolName gpn)
	{
		return getEndpointApiRootPackageName(gpn) + ".ops";
	}

	public static String getStateChannelPackageName(GProtocolName gpn, Role self)
	{
		return getEndpointApiRootPackageName(gpn) + ".channels." + self;
	}
	
	// Returns the simple Role Class name
	public static String getRoleClassName(Role r)
	{
		return r.toString();
	}
	
	// Returns the simple Op Class name: names starting with a digit are prefixed by '_'
	public static String getOpClassName(MessageId<?> mid)
	{
		String s = mid.toString();
		return (s.isEmpty() || s.charAt(0) < 65) ? "_" + s : s;  // Hacky?
	}
}
