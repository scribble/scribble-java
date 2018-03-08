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
package org.scribble.codegen.java.statechanapi;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.ConstructorBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EState;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

// Parameterize on output class type
public abstract class ScribSockGen extends StateChanTypeGen
{
	public static final String JAVA_SCHEMA = "java";  // FIXME: factor out
	
	public static final String SESSIONENDPOINT_CLASS = "org.scribble.runtime.session.SessionEndpoint";
	public static final String MPSTENDPOINT_CLASS = "org.scribble.runtime.session.MPSTEndpoint";
	public static final String EXPLICITENDPOINT_CLASS = "org.scribble.runtime.session.ExplicitEndpoint";
	public static final String OPENUM_INTERFACE = "org.scribble.runtime.session.OpEnum";

	public static final String BUF_CLASS = "org.scribble.runtime.util.Buf";

	//public static final String CONNECTSOCKET_CLASS = "org.scribble.statechans.ConnectSocket";
	public static final String ACCEPTSOCKET_CLASS = "org.scribble.runtime.statechans.AcceptSocket";
	//public static final String DISCONNECTSOCKET_CLASS = "org.scribble.statechans.DisconnectSocket";
	public static final String OUTPUTSOCKET_CLASS = "org.scribble.runtime.statechans.OutputSocket";
	public static final String RECEIVESOCKET_CLASS = "org.scribble.runtime.statechans.ReceiveSocket";
	public static final String BRANCHSOCKET_CLASS = "org.scribble.runtime.statechans.BranchSocket";
	public static final String CASESOCKET_CLASS = "org.scribble.runtime.statechans.CaseSocket";
	public static final String ENDSOCKET_CLASS = "org.scribble.runtime.statechans.EndSocket";

	public static final String SCRIBSERVERSOCKET_CLASS = "org.scribble.runtime.net.ScribServerSocket";

	public static final String GENERATED_ENDSOCKET_NAME = "EndSocket";
	
	public static final String BUFF_VAL_FIELD = "val";
	public static final String SCRIBSOCKET_SE_FIELD = JavaBuilder.THIS + ".se";
	public static final String SCRIBMESSAGE_PAYLOAD_FIELD = "payload";

	public static final String RECEIVE_MESSAGE_PARAM = "m";
	public static final String RECEIVE_ARG_PREFIX = "arg";

	public static final String CASE_OP_FIELD = "op";
	public static final String CASE_OP_PARAM = CASE_OP_FIELD;
	public static final String CASE_MESSAGE_FIELD = "m";
	public static final String CASE_MESSAGE_PARAM = CASE_MESSAGE_FIELD;
	public static final String CASE_ARG_PREFIX = "arg";
	
	protected final EState curr;
	protected final String className;

	protected final ClassBuilder cb = new ClassBuilder();
	
	public ScribSockGen(StateChannelApiGenerator apigen, EState curr)
	{
		super(apigen);
		this.curr = curr;
		this.className = getClassName();
	}
	
	protected String getClassName()
	{
		return this.apigen.getSocketClassName(this.curr);
	}

	@Override
	public ClassBuilder generateType() throws ScribbleException
	{
		constructClass();  // So className can be "overridden" in subclass constructor (CaseSocket)
		return this.cb;
	}

	protected void constructClass() throws ScribbleException
	{
		constructClassExceptMethods();
		addMethods();
	}

	protected void constructClassExceptMethods()
	{
		this.cb.setName(this.className);
		//this.cb.setPackage(getSessionPackageName());
		this.cb.setPackage(getStateChannelPackageName());
		this.cb.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.FINAL);
		//cb.setSuperClass(superc + "<" + SessionApiGenerator.getSessionClassName(this.gpn) + ", " + SessionApiGenerator.getRoleClassName(this.self) + ">");
		this.cb.setSuperClass(getSuperClassType());
		addImports();
		addConstructor();
		
		FieldBuilder cast = this.cb.newField("cast");
		cast.addModifiers(JavaBuilder.PUBLIC, JavaBuilder.STATIC, JavaBuilder.FINAL);
		cast.setType(this.className);
		cast.setExpression("null");
	}
	
	protected abstract String getSuperClassType();

	protected void addImports()
	{
		//this.cb.addImports("java.io.IOException");
		//this.cb.addImports(getSessionPackageName() + "." + getSessionClassName());
		this.cb.addImports(getEndpointApiRootPackageName() + ".*");
		this.cb.addImports(getRolesPackageName() + ".*");
	}
	
	protected MethodBuilder addConstructor()
	{
		final String SESSIONENDPOINT_PARAM = "se";
		final String sess = getSessionClassName();
		final String role = getSelfClassName();

		ConstructorBuilder ctor = cb.newConstructor(SESSIONENDPOINT_CLASS + "<" + sess + ", " + role + "> " + SESSIONENDPOINT_PARAM, "boolean dummy");
		ctor.addModifiers(JavaBuilder.PROTECTED);
		ctor.addBodyLine(JavaBuilder.SUPER + "(" + SESSIONENDPOINT_PARAM + ");");
		if (this.curr.equals(this.apigen.getInitialState()))
		{
			addInitialStateConstructor();
		}
		return ctor;
	}

	protected void addInitialStateConstructor()
	{
		final String SESSIONENDPOINT_PARAM = "se";
		String sess = getSessionClassName();
		String role = getSelfClassName();
		/*MethodBuilder mb = cb.newMethod("init");
		mb.addModifiers(ClassBuilder.PUBLIC, ClassBuilder.STATIC);
		mb.setReturn(this.root);
		mb.addParameters(SESSIONENDPOINT_CLASS + "<" + role + "> " + SESSIONENDPOINT_PARAM);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb.addBodyLine(SESSIONENDPOINT_PARAM + ".init();");
		mb.addBodyLine(ClassBuilder.RETURN + " " + ClassBuilder.NEW + " " + this.root + "(" + SESSIONENDPOINT_PARAM + ");");*/

		GProtocolDecl gpd = (GProtocolDecl) this.apigen.getJob().getContext().getModule(this.apigen.gpn.getPrefix()).getProtocolDecl(this.apigen.gpn.getSimpleName());
		String epClass = gpd.isExplicitModifier() ? EXPLICITENDPOINT_CLASS : MPSTENDPOINT_CLASS;
		ConstructorBuilder ctor2 = cb.newConstructor(epClass + "<" + sess + ", " + role + "> " + SESSIONENDPOINT_PARAM);

		ctor2.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS);
		ctor2.addModifiers(JavaBuilder.PUBLIC);
		ctor2.addBodyLine(JavaBuilder.SUPER + "(" + SESSIONENDPOINT_PARAM + ");");
		ctor2.addBodyLine(SESSIONENDPOINT_PARAM + ".init();");
	}
	
	protected abstract void addMethods() throws ScribbleException;
	
	/*@Deprecated
	protected void setNextSocketReturnType(MethodBuilder mb, EState succ)
	{
		setNextSocketReturnType(this.apigen, mb, succ);
	}*/
	
	//protected void addReturnNextSocket(MethodBuilder mb, String nextClass)
	protected void addReturnNextSocket(MethodBuilder mb, EState s)
	{
		String nextClass;
		//if (isTerminalClassName(nextClass))
		if (s.isTerminal())
		{
			mb.addBodyLine(SCRIBSOCKET_SE_FIELD + ".setCompleted();");  // Do before the IO action? in case of exception?
			nextClass = GENERATED_ENDSOCKET_NAME;// + "<>";
		}
		else
		{
			nextClass = this.apigen.getSocketClassName(s);
		}
		mb.addBodyLine(JavaBuilder.RETURN + " " + JavaBuilder.NEW + " " + nextClass + "(" + SCRIBSOCKET_SE_FIELD + ", true);");
	}

	protected String getGarbageBuf(String futureClass)
	{
		//return ClassBuilder.NEW + " " + BUFF_CLASS + "<>()";  // Makes a trash Buff every time, but clean -- would be more efficient to generate the code to spawn the future without buff-ing it (partly duplicate of the normal receive generated code) 
		return "(" + BUF_CLASS + "<" + futureClass + ">) " + SCRIBSOCKET_SE_FIELD + ".gc";  // FIXME: generic cast warning (this.ep.gc is Buff<?>) -- also retains unnecessary reference to the last created garbage future (but allows no-arg receive/async to be generated as simple wrapper call)
	}
	
	// Not fully qualified, just Session API class prefix
	// The constant singleton value of this type in the Session API (which is the same "name" as the class)
	protected String getSessionApiRoleConstant(Role role)
	{
		return SessionApiGenerator.getSessionClassName(this.apigen.getGProtocolName()) + "." + role;
	}
	
	// Not fully qualified, just Session API class prefix
	// The constant singleton value of this type in the Session API (which is the same "name" as the class)
	protected String getSessionApiOpConstant(MessageId<?> mid)
	{
		return SessionApiGenerator.getSessionClassName(this.apigen.getGProtocolName()) + "." + SessionApiGenerator.getOpClassName(mid);
	}
	
	// Wrappers for EndpointApiGenerator getters
	protected String getSessionClassName()
	{
		return SessionApiGenerator.getSessionClassName(this.apigen.getGProtocolName());
	}
	
	protected String getSelfClassName()
	{
		return SessionApiGenerator.getRoleClassName(this.apigen.getSelf());
	}

	protected String getEndpointApiRootPackageName()
	{
		/*GProtocolName gpn = this.apigen.getGProtocolName();
		return SessionApiGenerator.getSessionApiPackageName(this.apigen.getGProtocolName()) + ".api" + gpn.getSimpleName();*/
		return SessionApiGenerator.getEndpointApiRootPackageName(this.apigen.getGProtocolName());
	}

	protected String getRolesPackageName()
	{
		return SessionApiGenerator.getRolesPackageName(this.apigen.getGProtocolName());
	}

	protected String getOpsPackageName()
	{
		return SessionApiGenerator.getOpsPackageName(this.apigen.getGProtocolName());
	}

	protected String getStateChannelPackageName()
	{
		//return getSessionPackageName() + ".channels." + this.apigen.getSelf();
		return SessionApiGenerator.getStateChannelPackageName(this.apigen.getGProtocolName(), this.apigen.getSelf());
	}

	public static void setNextSocketReturnType(StateChannelApiGenerator apigen, MethodBuilder mb, EState succ)
	{
		String ret;
		if (succ.isTerminal())
		{
			GProtocolName gpn = apigen.getGProtocolName();
			Role self = apigen.getSelf();
			ret = SessionApiGenerator.getStateChannelPackageName(gpn, self) + "." + GENERATED_ENDSOCKET_NAME;
					//+ "<" + SessionApiGenerator.getSessionClassName(gpn) + ", " + SessionApiGenerator.getRoleClassName(self) + ">";
		}
		else
		{
			ret = apigen.getSocketClassName(succ);
		}
		mb.setReturn(ret);
	}
	
	protected static void checkJavaDataTypeDecl(DataTypeDecl dtd) throws ScribbleException
	{
		checkJavaSchema(dtd);
	}

	protected static void checkMessageSigNameDecl(MessageSigNameDecl msd) throws ScribbleException
	{
		checkJavaSchema(msd);
	}
	
	protected static void checkJavaSchema(NonProtocolDecl<?> npd) throws ScribbleException
	{
		if (!npd.schema.equals(ScribSockGen.JAVA_SCHEMA))  // FIXME: factor out
		{
			throw new ScribbleException(npd.getSource(), "Unsupported data type schema: " + npd.schema);
		}
	}
	
	/*private static boolean isTerminalClassName(String n)
	{
		//return n.equals(ClassBuilder.VOID);
		return n.equals(EndpointApiGenerator.ENDSOCKET_CLASS);
	}*/

	/*// Pre: not a terminal state (i.e. className is not void)
	private ClassBuilder constructInitClass(String className)
	{
		final String SESSIONENDPOINT_PARAM = "se";
		String role = SessionApiGenerator.getRoleClassName(this.self);

		ClassBuilder cb = new ClassBuilder();
		cb.setName(className);
		cb.setPackage(getPackageName());
		cb.setSuperClass(INITSOCKET_CLASS + "<" + role + ">");
		cb.addModifiers(ClassBuilder.PUBLIC);
		
		MethodBuilder ctor = cb.newConstructor(SESSIONENDPOINT_CLASS + "<" + role + "> " + SESSIONENDPOINT_PARAM);
		ctor.addModifiers(ClassBuilder.PUBLIC);
		ctor.addBodyLine(ClassBuilder.SUPER + "(" + SESSIONENDPOINT_PARAM + ");");
		
		MethodBuilder mb = cb.newMethod("init");
		mb.setReturn(this.root);
		mb.addModifiers(ClassBuilder.PUBLIC);
		mb.addExceptions(SCRIBBLERUNTIMEEXCEPTION_CLASS);
		mb.addBodyLine(ClassBuilder.SUPER + ".use();");  // Factor out
		//mb.addBodyLine(SCRIBSOCKET_SE_FIELD + ".init();");  // Factor out
		addReturnNextSocket(mb, this.root);

		return cb;
	}*/
}
