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
package org.scribble.codegen.java.callbackapi;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.ast.DataDecl;
import org.scribble.ast.Module;
import org.scribble.ast.SigDecl;
import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.ConstructorBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.core.job.Core;
import org.scribble.core.job.CoreArgs;
import org.scribble.core.job.CoreContext;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.EStateKind;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.PayElemType;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.SigName;
import org.scribble.job.Job;
import org.scribble.job.JobContext;
import org.scribble.util.ScribException;

// FIXME: integrate with JEndpointApiGenerator -- this class should correspond to StateChanApiGenerator (relying on the common SessionApiGenerator)
// FIXME: consider collecting up all interfaces as statics inside a container class -- also states?
// FIXME: integrate CB (branch) interfaces with old iointerfacs/callback API
// FIXME: consider also "expanding" nested message constructors by op first? -- and consider "partial constructors" along expansions, can they be parameterised typed?
public class CBEndpointApiGenerator3
{
	public final Job job;
	public final Core core;
	public final GProtoName proto;
	public final Role self;  // FIXME: base endpoint API gen is role-oriented, while session API generator should be neutral
	
	protected final Map<Integer, String> stateNames = new HashMap<>();
	
	//private final boolean subtypes;  // Generate full hierarchy (states -> states, not just indivdual state -> cases) -- cf. ioifaces

	public CBEndpointApiGenerator3(Job job, GProtoName fullname, Role self,
			boolean subtypes)
	{
		this.job = job;
		this.core = job.getCore();
		this.proto = fullname;
		this.self = self;
		
		//this.subtypes = subtypes;
		if (subtypes)
		{
			throw new RuntimeException("TODO: -subtypes");
		}
	}

	public Map<String, String> build() throws ScribException
	{
		this.stateNames.clear();
		Map<String, String> res = new HashMap<>();  // filepath -> source 
		res.putAll(buildSessionApi());
		return res;
	}

	// FIXME: factor out -- integrate with JEndpointApiGenerator
	public Map<String, String> buildSessionApi() throws ScribException
	{
		this.job.verbosePrintln("\n[param-core] Running " + CBEndpointApiGenerator3.class + " for " + this.proto + "@" + this.self);

		Map<String, String> res = new HashMap<>();
		res.putAll(new SessionApiGenerator(this.job, this.proto).generateApi());
		res.putAll(buildEndpointClass());
		return res;
	}
	
	public Map<String, String> buildEndpointClass() throws ScribException
	{
		Module main = this.job.getContext().getMainModule();
		Map<String, String> res = new HashMap<>();

		CoreContext jobc2 = this.core.getContext();
		EState init = (this.core.config.args.get(CoreArgs.MIN_EFSM)
				? jobc2.getMinimisedEGraph(this.proto, this.self)
				: jobc2.getEGraph(this.proto, this.self)
				).init;
				// TODO: factor out with StateChannelApiGenerator constructor
		Set<EState> states = new HashSet<>();
		states.add(init);
		states.addAll(init.getReachableStates());

		String endpointName = this.proto.getSimpleName() + "_" + this.self;
		int i = 1;
		for (EState s : states)
		{
			EStateKind kind = s.getStateKind();
			String stateName = (kind == EStateKind.TERMINAL) ? "End" : endpointName + "_" + i++;
			this.stateNames.put(s.id, stateName);
		}

		// frontend handler class
		String rootPack = SessionApiGenerator.getEndpointApiRootPackageName(this.proto);
		String sessClassName = rootPack + "." + this.proto.getSimpleName();
		String prefix = getHandlersSelfPackage().replace('.', '/') + "/";  // StateChannelApiGenerator#generateApi

		String epClass = generateEndpointClass(rootPack, endpointName, sessClassName, init, states);
		res.put(prefix + endpointName + ".java", epClass);
		
		// output-choice message i/f's
		Set<String> outputCallbacks = new HashSet<>();  // e.g., Proto1_A__B__1_Int
		Set<String> outputChoices = new HashSet<>();    // e.g., Proto1_A__B__1_Int__C__2_Int__C__4_Str, i.e., { B__1_Int, C__2_Int, C__4_Str }
		Map<String, Set<Set<String>>> reg = new HashMap<>();  // e.g., Proto1_A__B__1_Int -> { Proto1_A__B__1_Int__C__2_Int__C__4_Str, ... }
		for (EState s : (Iterable<EState>) states.stream().filter(x -> x.getStateKind() == EStateKind.OUTPUT)::iterator)
		{
			List<EAction> as = s.getActions();
			Set<String> set = as.stream().map(this.getCallbackSuffix).collect(Collectors.toSet());
			for (EAction a : as)
			{
				String callbackName =
						//endpointName
						"OCallback_" + this.self  // FIXME: factor out  // Cf. ICallback, has self (due to message expansion nesting) -- but ScribMessage values of this type don't record self? -- Select i/f's have self in name though
						+ "__" + this.getCallbackSuffix.apply(a);
				outputCallbacks.add(callbackName);
				if (set.size() > 1)
				{
					Set<Set<String>> tmp = reg.get(callbackName);
					if (tmp == null)
					{
						tmp = new HashSet<>();
						reg.put(callbackName, tmp);
					}
					tmp.add(set);
				}
			}
			if (as.size() > 1)
			{
				String outputChoiceName =
							//endpointName
							"Select_" + this.self  // FIXME: factor out  // Cf. original iointerfaces
						+ as.stream().sorted(Comparator.comparing(a -> a.toString()))
								.map(a -> "__" + this.getCallbackSuffix.apply(a)).collect(Collectors.joining());
				outputChoices.add(outputChoiceName);
			}
		}
		for (String name : outputChoices)  // TODO: -subtypes -- full subtyping between outputChoice i/f's
		{
			InterfaceBuilder outputChoice = new InterfaceBuilder(name);
			outputChoice.addModifiers("public");
			String pack = getHandlersSelfPackage() + ".outputs.choices";  // FIXME: factor out
			outputChoice.setPackage(pack);
			res.put(pack.replace('.', '/') + "/" + name + ".java", outputChoice.build());
		}
		for (String name : outputCallbacks)
		{
			InterfaceBuilder outputCallback = new InterfaceBuilder(name);
			outputCallback.addModifiers("public");
			if (reg.containsKey(name))
			{
				reg.get(name).forEach(iface ->
						outputCallback.addInterfaces(getHandlersSelfPackage() + ".outputs.choices."
								//+ endpointName
								+ "Select_" + this.self  // FIXME: factor out
								+ iface.stream().sorted().map(f -> "__" + f).collect(Collectors.joining()))
				);
			}
			String pack = getHandlersSelfPackage() + ".outputs";  // FIXME: factor out
			outputCallback.setPackage(pack);
			res.put(pack.replace('.', '/') + "/" + name + ".java", outputCallback.build());
		}
		
		// states
		String sprefix = getStatesSelfPackage().replace('.', '/') + "/";  // StateChannelApiGenerator#generateApi
		for (EState s : states)
		{
			EStateKind kind = s.getStateKind();
			String stateName = this.stateNames.get(s.id);
			String stateClass = generateStateClass(s, rootPack, kind, stateName, endpointName);
			res.put(sprefix + stateName + ".java", stateClass);
		}
			
		/*// messages
		for (EState s : states)
		{
			if (s.getStateKind() == EStateKind.OUTPUT)
			{
				String mprefix = getMessagesPackage(rootPack).replace('.', '/') + "/";
				String messageAbstractName = getMessageAbstractName(endpointName, s);  
				String messageAbstractClass = generateMessageAbstractClass(messageAbstractName, rootPack, mprefix);
				res.put(mprefix + messageAbstractName + ".java", messageAbstractClass);
				Map<Payload, ClassBuilder> messageClasses = generateMessageClasses(endpointName, s, mprefix, rootPack);
				messageClasses.values().forEach(c -> res.put(mprefix + c.getName() + ".java", c.build()));
			}
		}*/
			
		// branches
		JobContext jobc = this.job.getContext();
		for (EState s : states)
		{
			if (s.getStateKind() == EStateKind.UNARY_RECEIVE || s.getStateKind() == EStateKind.POLY_RECIEVE)
			{
				String bprefix = getHandlersSelfPackage().replace('.', '/') + "/";
				String branchName = this.stateNames.get(s.id) + "_Branch";

				for (EAction a : s.getActions())
				{
					// FIXME: factor out
					boolean isSig = main.getNonProtoDeclChildren().stream()
							.anyMatch(npd -> (npd instanceof SigDecl)
									&& ((SigDecl) npd).getDeclName().toString()
											.equals(a.mid.toString()));
					SigDecl msnd = null;
					if (isSig)
					{
						msnd = (SigDecl) main.getNonProtoDeclChildren().stream()
								.filter(npd -> (npd instanceof SigDecl)
										&& ((SigDecl) npd).getDeclName().toString()
												.equals(a.mid.toString()))
								.iterator().next();
					}
					
					String receiveIfName =
							  //endpointName + "__"
							  "ICallback__"  // CHECKME: confusing against "icallback"
							+ a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid)
							+ a.payload.elems.stream().map(e -> "_" + getExtName(e)).collect(Collectors.joining());  // FIXME: factor out
					String receiveInterface = generateReceiveInterface(isSig, msnd, jobc, a, rootPack, receiveIfName);
					res.put((getHandlersSelfPackage() + ".inputs.").replace('.', '/')
							+ receiveIfName + ".java", receiveInterface);
				}

				String branchAbstract = generateBranch(bprefix, jobc, s, endpointName,
						rootPack, branchName);
				res.put(bprefix + branchName + ".java", branchAbstract);
			}
		}
		
		return res;
	}

	protected String generateEndpointClass(String rootPack, String endpointName, String sessClassName, EState init, Set<EState> states)
	{
		String initStateName = getStatesSelfPackage() + "." + this.stateNames.get(init.id);//endpointName + "_" + init.id;  // FIXME: factor out

		ClassBuilder endpointClass = new ClassBuilder(endpointName);
		endpointClass.setPackage(getHandlersSelfPackage());
		endpointClass.addModifiers("public");
		endpointClass.addParameters("D");
		endpointClass.setSuperClass("org.scribble.runtime.session.CBEndpoint<" + sessClassName + ", " + getRolesPackage() + "." + this.self + ", D>");
		ConstructorBuilder cb = endpointClass.newConstructor(sessClassName + " sess", getRolesPackage() + "." + this.self + " self", "org.scribble.runtime.message.ScribMessageFormatter smf", "D data");
		cb.addModifiers("public");
		cb.addExceptions("java.io.IOException", "org.scribble.main.ScribRuntimeException");
		cb.addBodyLine("super(sess, self, smf, " + initStateName + ".id, data);");
		for (EState s : states)
		{
			//String tmp = (s.getStateKind() == EStateKind.TERMINAL) ? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + s.id;
			String tmp = this.stateNames.get(s.id);
			cb.addBodyLine("this.states.put(\"" + tmp + "\", " + getStatesSelfPackage() + "." + tmp + ".id);");
		}
		MethodBuilder mb = endpointClass.newMethod("run");
		mb.addModifiers("public");
		mb.setReturn("java.util.concurrent.Future<Void>");
		mb.addExceptions("org.scribble.main.ScribRuntimeException");
		mb.addAnnotations("@Override");
		mb.addBodyLine("java.util.Set<Object> states = java.util.stream.Stream.of("
				+ states.stream().filter(s -> s.getStateKind() != EStateKind.TERMINAL)
						.map(s -> getStatesSelfPackage() + "." + this.stateNames.get(s.id) + ".id")
						.collect(Collectors.joining(", ")) + ").collect(java.util.stream.Collectors.toSet());\n");
		mb.addBodyLine("java.util.Set<Object> regd = new java.util.HashSet<>();");
		mb.addBodyLine("regd.addAll(this.inputs.keySet());");
		mb.addBodyLine("regd.addAll(this.outputs.keySet());");
		mb.addBodyLine("if (!states.equals(regd)) {");
		mb.addBodyLine("states.removeAll(regd);");
		mb.addBodyLine("throw new org.scribble.main.ScribRuntimeException(\"Missing state registrations: \" + states);");
		mb.addBodyLine("}");
		mb.addBodyLine("return super.run();");
		for (EState s : states)
		{
			if (s.getStateKind() != EStateKind.TERMINAL)
			{
				generateRegister(endpointClass, endpointName, rootPack, s);
			}
		}
		return endpointClass.build();

		/*String role = SessionApiGenerator.getRolesPackageName(this.proto) + "." + this.self;
		String epClass = "";
		
		epClass += "package " + rootPack + ".handlers." + this.self + ";\n";
		epClass += "\n";
		epClass += "\n";
		epClass += "public class " + endpointName + "<D> extends org.scribble.runtime.session.CBEndpoint<" + sess + ", " + role + ", D> {\n";

		epClass += "public " + endpointName + "(" + sess  + " sess, " + role
				+ " self, org.scribble.runtime.message.ScribMessageFormatter smf, D data) throws java.io.IOException, org.scribble.main.ScribbleRuntimeException {\n";
		epClass += "super(sess, self, smf, " + rootPack + ".handlers.states." + this.self + "." + endpointName + "_" + init.id + ".id" + ", data);\n";  // FIXME: factor out
		for (EState s : states)
		{
			String tmp = (s.getStateKind() == EStateKind.TERMINAL) ? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + s.id;
			epClass += "this.states.put(\"" + tmp + "\", " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + "." + tmp + ".id);\n";
		}
		epClass += "}\n";
		epClass += "\n";
		

		epClass += states.stream().map(s -> generateRegister(this.proto, this.self, s)).collect(Collectors.joining("\n"));
		
		epClass += "@Override\n";
		epClass += "public java.util.concurrent.Future<Void> run() throws org.scribble.main.ScribbleRuntimeException {\n";
		epClass += "java.util.Set<Object> states = java.util.stream.Stream.of(" + states.stream().filter(s -> s.getStateKind() != EStateKind.TERMINAL).map(s ->
				SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + "." + this.proto.getSimpleName() + "_" + this.self + "_" + s.id + ".id").collect(Collectors.joining(", ")) + ").collect(java.util.stream.Collectors.toSet());\n";
		epClass += "java.util.Set<Object> regd = new java.util.HashSet<>();\n";
		epClass += "regd.addAll(this.inputs.keySet());\n";
		epClass += "regd.addAll(this.outputs.keySet());\n";
		epClass += "if (!states.equals(regd)) {\n";
		epClass += "states.removeAll(regd);\n";
		epClass += "throw new org.scribble.main.ScribbleRuntimeException(\"Missing state registrations: \" + states);\n";
		epClass += "}\n";
		epClass += "return super.run();\n";
		epClass += "}\n";
		
		epClass += "}\n";
		
		return epClass;*/
	}

	//private String generateRegister(GProtocolName gpn, Role self, EState s)
	protected void generateRegister(ClassBuilder endpointClass, String endpointName, String rootPack, EState s)
	{
			switch (s.getStateKind())
			{
				case OUTPUT:
				{
					MethodBuilder callback = endpointClass.newMethod("callback");
					callback.addModifiers("public");
					callback.setReturn(endpointName + "<D>");
					callback.addParameters(getStatesSelfPackage() + "." + this.stateNames.get(s.id) + " sid",  // FIXME: factor out
							"java.util.function.Function<D, "
									//+ getMessagesPackage(rootPack) + "." + getMessageAbstractName(endpointName, s)
									+ getStatesSelfPackage() + "." + this.stateNames.get(s.id) + ".Message"  // FIXME: factor out -- messageIf
									+ "> cb"
					);
					/*callback.addBodyLine("this.outputs.put(sid, cb);");
					callback.addBodyLine("return this;");*/
					callback.addBodyLine("return icallback(sid, cb);");

					MethodBuilder icallback = endpointClass.newMethod("icallback");
					icallback.addModifiers("public");
					icallback.setReturn(endpointName + "<D>");
					String iface = getHandlersSelfPackage()
							+ ((s.getActions().size() > 1) ? ".outputs.choices.Select" : ".outputs.OCallback")  // FIXME: factor out
							//+ endpointName
							+ "_" + this.self
							+ s.getActions().stream().sorted(Comparator.comparing(a -> a.toString()))
									.map(a -> "__" + this.getCallbackSuffix.apply(a)).collect(Collectors.joining());  // FIXME: factor out
					icallback.addTypeParameters("T extends " + iface + " & org.scribble.runtime.handlers.ScribOutputEvent");
					icallback.addParameters(getStatesSelfPackage() + "." + this.stateNames.get(s.id) + " sid",  // FIXME: factor out
							"java.util.function.Function<D, "
									+ "? extends T"
									+ "> cb"
					);
					icallback.addBodyLine("this.outputs.put(sid, cb);");
					icallback.addBodyLine("return this;");

					break;
				}
				case UNARY_RECEIVE:
				case POLY_RECIEVE:
				{
					MethodBuilder callback = endpointClass.newMethod("callback");
					callback.addModifiers("public");
					callback.setReturn(endpointName + "<D>");
					callback.addParameters(getStatesSelfPackage() + "." + this.stateNames.get(s.id) + " sid",
							getHandlersSelfPackage() + "." + this.stateNames.get(s.id) + "_Branch<D> b"
					);  // FIXME: factor out
					/*callback.addBodyLine("this.inputs.put(sid, b);");
					callback.addBodyLine("return this;");*/
					callback.addBodyLine("return icallback(sid, b);");

					MethodBuilder icallback = endpointClass.newMethod("icallback");
					icallback.addModifiers("public");
					icallback.setReturn(endpointName + "<D>");
					String typepar = s.getActions().stream().map(a -> 
							  getHandlersSelfPackage() + ".inputs."
							//+ this.proto.getSimpleName() + "_" + this.self  // i.e, endpointName
							+ "ICallback"  // FIXME: factor out
							+ "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid)
							+ a.payload.elems.stream().map(e -> "_" + getExtName(e)).collect(Collectors.joining("")) + "<D>"
					).collect(Collectors.joining(" & "));
					icallback.addTypeParameters("T extends " + typepar + " & org.scribble.runtime.handlers.ScribBranch<D>");
					icallback.addParameters(getStatesSelfPackage() + "." + this.stateNames.get(s.id) + " sid", "T b");  // FIXME: factor out
					icallback.addBodyLine("this.inputs.put(sid, b);");
					icallback.addBodyLine("return this;");
					break;
				}
				case SERVER_WRAP:
				case ACCEPT:
				{
					throw new RuntimeException("TODO: " + s.getStateKind());
				}
				case TERMINAL:
				default:
				{
					throw new RuntimeException("Shouldn't get in here: " + s.getStateKind());
				}
			}
			
		/*String prefix = SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + "." ;
		String res = "";
		String name = gpn.getSimpleName() + "_" + self;
		switch (s.getStateKind())
		{
			case OUTPUT:
			{
				// FIXME: "untyped" (ScribEvent) -- need state-specific "enums"
				res += "public " + name + "<D> callback(" + prefix + name + "_" + s.id
						+ " sid, java.util.function.Function<D, "
						+ SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + ".messages." + name + "_" + s.id + "_Message> h) {\n";  // FIXME: factor out
				/*res += "this.outputs.put(sid, h);\n";
				res += "return this;\n";* /
				res += "return icallback(sid, h);\n";
				res += "}\n";
				res += "\n";
				String messageIfName = name + 
						s.getActions().stream().map(a -> "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining())).collect(Collectors.joining());
				res += "public\n"
						+ "<T extends " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + ".messages.interfaces." + messageIfName + " & org.scribble.runtime.handlers.ScribOutputEvent>\n"  // FIXME: factor out
						+ name + "<D> icallback(" + prefix + name + "_" + s.id
						+ " sid, java.util.function.Function<D, T> h) {\n";
				res += "this.outputs.put(sid, h);\n";
				res += "return this;\n";
				res += "}\n";
				res += "\n";
				break;
			}
			case UNARY_INPUT:
			case POLY_INPUT:
			{
				res += "public " + name + "<D> callback(" + prefix + name + "_" + s.id + " sid";
				res += ", " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers." + this.self + "." + name + "_" + s.id + "_Branch<D> b";
				res += ") {\n";
				res += "return icallback(sid, b);\n";
				res += "}\n";
				res += "\n";
				String T = s.getAllActions().stream().map(a -> SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers." + this.self + ".interfaces." + name + "__" + a.peer + "_"
						+ SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining("")) + "<D>").collect(Collectors.joining(" & "))
						+ " & org.scribble.runtime.handlers.ScribBranch<D>";
				res += "public " + "<T extends " + T + ">\n" 
						+ name + "<D> icallback(" + prefix + name + "_" + s.id
						+ " sid, T" 
						//+ SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers." + this.self + ".interfaces." + receiveIfName + "
						+ " b) {\n";  // FIXME: factor out
				res += "this.inputs.put(sid, b);\n";
				res += "return this;\n";
				res += "}\n";
				res += "\n";
				break;
			}
			case TERMINAL:
			{
				break;
			}
			case ACCEPT:
			case WRAP_SERVER:
			{
				throw new RuntimeException("[scrib] TODO: " + s);
			}
			default:
			{
				throw new RuntimeException("[scrib] Shouldn't get in here: " + s);
			}
		}
		return res;*/
	}
	
	protected String generateStateClass(EState s, String rootPack, EStateKind kind, String stateName, String endpointName)
	{
		Module main = this.job.getContext().getMainModule();
		
		String stateKind;
		switch (kind)
		{
			case OUTPUT:     stateKind = "org.scribble.runtime.handlers.states.ScribOutputState"; break;
			case UNARY_RECEIVE:
			case POLY_RECIEVE: stateKind = "org.scribble.runtime.handlers.states.ScribInputState";  break;
			case TERMINAL:   stateKind = "org.scribble.runtime.handlers.states.ScribEndState";    break;
			case ACCEPT:
			case SERVER_WRAP:
				throw new RuntimeException("TODO");
			default:
				throw new RuntimeException("TODO");
		}

		ClassBuilder stateClass = new ClassBuilder(stateName);
		stateClass.setPackage(getStatesSelfPackage());
		stateClass.setSuperClass(stateKind);
		stateClass.addModifiers("public", "final");
		FieldBuilder id = stateClass.newField("id");
		id.addModifiers("public", "static", "final");
		id.setType(stateName);
		id.setExpression("new " + stateName + "("
					//+ ((kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT) ? getRolesPackage() + "." + this.self + "." + this.self : "")  // FIXME: factor out
					+ ")");
		/*String[] params = (kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT)
				? new String[] { "org.scribble.type.name.Role peer" } : new String[0];*/
		ConstructorBuilder stateCons = stateClass.newConstructor();
		stateCons.addModifiers("private");
		Role peer = (kind == EStateKind.UNARY_RECEIVE || kind == EStateKind.POLY_RECIEVE) ? s.getDetActions().iterator().next().peer : null;
		stateCons.addBodyLine("super(\"" + stateName + "\""
				+ ((kind == EStateKind.UNARY_RECEIVE || kind == EStateKind.POLY_RECIEVE) ? ", " + getRolesPackage() + "." + peer + "." + peer : "")  // FIXME: factor out
				+ ");");
		for (EAction a : s.getActions())
		{
			EState succ = s.getDetSuccessor(a);
			stateCons.addBodyLine("this.succs.put(" + getOpsPackage() + "." + SessionApiGenerator.getOpClassName(a.mid) + "." + SessionApiGenerator.getOpClassName(a.mid)  // FIXME: factor out
					+ ", \""
					/*+ ((succ.getStateKind() == EStateKind.TERMINAL)
							? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + succ.id)  // FIXME: factor out*/
					+ this.stateNames.get(succ.id)
					+ "\");"
			);
		}
		
		if (s.getStateKind() == EStateKind.OUTPUT)  // FIXME: factor out
		{
			if (s.getDetActions().size() > 0)
			{
				InterfaceBuilder messageIf = stateClass.newMemberInterface("Message");  // FIXME: factor out
				messageIf.addModifiers("public", "static");
				messageIf.addInterfaces("org.scribble.runtime.handlers.ScribOutputEvent");
				String iface = getHandlersSelfPackage()  // FIXME: factor out with generateRegister
						+ ((s.getActions().size() > 1) ? ".outputs.choices.Select" : ".outputs.OCallback")  // FIXME: factor out
						//+ endpointName
						+ "_" + this.self
						+ s.getActions().stream().sorted(Comparator.comparing(a -> a.toString()))
								.map(a -> "__" + this.getCallbackSuffix.apply(a)).collect(Collectors.joining());  // FIXME: factor out
				messageIf.addInterfaces(iface);
			}
			s.getActions().stream().map(a -> a.peer).distinct().forEachOrdered(r ->
			{
				ClassBuilder roleClass = stateClass.newMemberClass(r.toString());
				roleClass.addModifiers("public", "static");
				ConstructorBuilder roleCons = roleClass.newConstructor();
				roleCons.addModifiers("private");
				s.getActions().stream().filter(a -> a.peer.equals(r))
						.forEachOrdered(a ->
				{
					String opName = SessionApiGenerator.getOpClassName(a.mid);
					ClassBuilder opClass = roleClass.newMemberClass(opName);
					opClass.addModifiers("public", "static");
					opClass.setSuperClass("org.scribble.runtime.message.ScribMessage");
					opClass.addInterfaces(stateName + ".Message");  // FIXME: factor out
					opClass.addInterfaces(getHandlersSelfPackage() + ".outputs."  // FIXME: factor out
							//+ endpointName
							+ "OCallback_" + this.self  // FIXME: factor out
							+ "__" + this.getCallbackSuffix.apply(a));
					FieldBuilder svu = opClass.newField("serialVersionUID");
					svu.addModifiers("private", "static", "final");
					svu.setType("long");
					svu.setExpression("1L");
					int[] i = { 0 };
					
					// FIXME: factor out
					boolean isSig = main.getNonProtoDeclChildren().stream()
						.anyMatch(npd -> (npd instanceof SigDecl) && ((SigDecl) npd).getDeclName().toString().equals(a.mid.toString()));
					SigDecl msnd = null;
					if (isSig)
					{
						msnd = (SigDecl) main.getNonProtoDeclChildren().stream()
								.filter(npd -> (npd instanceof SigDecl) && ((SigDecl) npd).getDeclName().toString().equals(a.mid.toString())).iterator().next();
						FieldBuilder m = opClass.newField("m");
						m.addModifiers("private", "final");
						m.setType(msnd.getExtName());
					}
					
					ConstructorBuilder opCons; 
					if (isSig)
					{
						opCons = opClass.newConstructor(msnd.getExtName() + " m");
						opClass.addInterfaces("org.scribble.runtime.handlers.ScribSigMessage");
					}
					else 
					{
						opCons = opClass.newConstructor(a.payload.elems.stream().map(e ->
									main.getTypeDeclChild((DataName) e).getExtName() + " arg" + i[0]++
							).collect(Collectors.joining(", ")));
					}
					opCons.addModifiers("public");
					opCons.addBodyLine("super(" + getOpsPackage() + "." + opName + "." + opName  // CHECKME: different "op types" have same equals/hashCode -- OK?
							+ IntStream.range(0, a.payload.elems.size()).mapToObj(j -> ", arg" + j).collect(Collectors.joining(""))
							+ ");");
					if (isSig)
					{
						opCons.addBodyLine("this.m = m;");
						MethodBuilder getSig = opClass.newMethod("getSig");
						getSig.addModifiers("public");
						getSig.addAnnotations("@Override");
						getSig.setReturn("org.scribble.runtime.message.ScribMessage");
						getSig.addBodyLine("return this.m;");
					}
					MethodBuilder getPeer = opClass.newMethod("getPeer");
					getPeer.addModifiers("public");
					getPeer.addAnnotations("@Override");
					getPeer.setReturn("org.scribble.core.type.name.Role");
					getPeer.addBodyLine("return " + getRolesPackage() + "." + a.peer + "." + a.peer + ";");
					MethodBuilder getOp = opClass.newMethod("getOp");
					getOp.addModifiers("public");
					getOp.addAnnotations("@Override");
					getOp.setReturn("org.scribble.core.type.name.Op");
					getOp.addBodyLine("return " + getOpsPackage() + "." + SessionApiGenerator.getOpClassName(a.mid) + "." + SessionApiGenerator.getOpClassName(a.mid) + ";");
					MethodBuilder getPayload = opClass.newMethod("getPayload");
					getPayload.addModifiers("public");
					getPayload.addAnnotations("@Override");
					getPayload.setReturn("Object[]");
					getPayload.addBodyLine("return this.payload;");
				});
			});
		}

		return stateClass.build();
		
		/*String stateClass = "";
		stateClass += "package " + rootPack + ".handlers.states." + this.self + ";\n";
		stateClass += "\n";
		stateClass += "public final class " + stateName + " extends " + stateKind + " {\n";
		stateClass += "public static final " + stateName + " id = new " + stateName + "("
				+ ((kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT) ? SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + s.getActions().get(0).peer + "." + s.getActions().get(0).peer : "")
				+ ");\n";
		stateClass += "private " + stateName + "("
				+ ((kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT) ? "org.scribble.type.name.Role peer" : "")
				+ ") {\n";
		stateClass += ((kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT) ? "super(\"" + stateName + "\", peer);" : "super(\"" + stateName + "\");") + "\n";
		for (EAction a : s.getAllActions())
		{
			stateClass += "this.succs.put(" + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + "." + SessionApiGenerator.getOpClassName(a.mid)
					+ ", "
					/*+ ((s.getSuccessor(a).id == s.id)
							? "this"
							: ((s.getSuccessor(a).getStateKind() == EStateKind.TERMINAL) ? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + s.getSuccessor(a).id) + ".id")* /
					+ "\"" + ((s.getSuccessor(a).getStateKind() == EStateKind.TERMINAL) ? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + s.getSuccessor(a).id) + "\""
					+ ");\n";
		}
		stateClass += "}\n";
		stateClass += "}\n";
		
		return stateClass;*/
	}

	// FIXME: refactor using TypeBuilder API
	protected String generateBranch(String bprefix, JobContext jc, EState s, String endpointName, String rootPack, String branchName)
	{
		String branchAbstract = "";
		branchAbstract += "package " + getHandlersSelfPackage() + ";\n";
		branchAbstract += "";
		branchAbstract += "public abstract class " + branchName + "<D> implements org.scribble.runtime.handlers.ScribBranch<D>";
		branchAbstract += s.getActions().stream().map(a ->
				", " + getHandlersSelfPackage() + ".inputs."
				//+ endpointName
				+ "ICallback"  // FIXME: factor out
				+ "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid)
				+ a.payload.elems.stream().map(e -> "_" + getExtName(e)).collect(Collectors.joining()) + "<D>"  // FIXME: factor out
		).collect(Collectors.joining(""));
		branchAbstract += " {\n";

		branchAbstract += "\n";
		branchAbstract += "@Override\n";
		branchAbstract += "public void dispatch(D data, org.scribble.runtime.message.ScribMessage m) {\n";
		branchAbstract += "switch (m.op.toString()) {\n";
		for (EAction a : s.getActions())
		{
			// FIXME: factor out
			boolean isSig = jc.getMainModule().getNonProtoDeclChildren().stream()
				.anyMatch(npd -> (npd instanceof SigDecl) && ((SigDecl) npd).getDeclName().toString().equals(a.mid.toString()));
			SigDecl msnd = null;
			if (isSig)
			{
				msnd = (SigDecl) jc.getMainModule().getNonProtoDeclChildren().stream()
						.filter(npd -> (npd instanceof SigDecl) && ((SigDecl) npd).getDeclName().toString().equals(a.mid.toString())).iterator().next();
			}

			branchAbstract += "case \"" + SessionApiGenerator.getOpClassName(a.mid) + "\": receive(data, " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + a.peer + "." + a.peer + ", ";
			if (isSig)
			{
				branchAbstract += "(" + msnd.getExtName() + ") m";
			}
			else
			{
				branchAbstract += "(" + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + ") m.op";
			}
			int i = 0;
			for (PayElemType<?> pet : a.payload.elems)
			{
				DataDecl dtd = jc.getMainModule().getTypeDeclChild((DataName) pet);
				branchAbstract += ", (" + dtd.getExtName() + ") m.payload[" + i++ + "]";
			}
			branchAbstract += "); break;\n";
		}
		branchAbstract += "default: throw new RuntimeException(\"Shouldn't get in here: \" + m);\n";
		branchAbstract += "}\n";
		branchAbstract += "}\n";
		branchAbstract += "}\n";
		
		return branchAbstract;
	}
	
	protected String generateReceiveInterface(boolean isSig,
			SigDecl msnd, JobContext jc, EAction a, String rootPack,
			String receiveIfName)
	{
		String receiveInterface = "";
		receiveInterface += "package " + getHandlersSelfPackage() + ".inputs;\n";
		receiveInterface += "\n";
		receiveInterface += "public interface " + receiveIfName + "<D> {\n";  // Cf. original callback API, have extra <D> parameter (o/w same)

		// Cf. output-callback i/f, only specifies peer and message (not self)
		receiveInterface += "\nvoid receive(D data, "
				+ SessionApiGenerator.getEndpointApiRootPackageName(this.proto)
				+ ".roles." + a.peer + " peer, ";
		if (isSig)
		{
			receiveInterface += msnd.getExtName() + " m";
		}
		else
		{
			receiveInterface += SessionApiGenerator
					.getEndpointApiRootPackageName(this.proto) + ".ops."
					+ SessionApiGenerator.getOpClassName(a.mid) + " op";
			int i = 1;
			for (PayElemType<?> pet : a.payload.elems)
			{
				DataDecl dtd = jc.getMainModule().getTypeDeclChild((DataName) pet);
				receiveInterface += ", " + dtd.getExtName() + " arg" + i++;
			}
		}
		receiveInterface += ");\n";
		receiveInterface += "}\n";
		
		return receiveInterface;
	}

	/*String generateMessageAbstractClass(String messageAbstractName, String rootPack, String mprefix)
	{
		InterfaceBuilder messageAbstract = new InterfaceBuilder( messageAbstractName);
		messageAbstract.setPackage(getMessagesPackage(rootPack));
		messageAbstract.addModifiers("public");
		messageAbstract.addInterfaces("org.scribble.runtime.handlers.ScribOutputEvent");
		return messageAbstract.build();

		/* //String messageIfName, String messageAbstractName, String endpointName, String rootPack, String mprefix)
		String messageAbstract = "";
		messageAbstract += "package " + rootPack + ".handlers.states." + this.self + ".messages;\n";
		messageAbstract += "\n";
		messageAbstract += "public interface " + messageAbstractName + " extends org.scribble.runtime.handlers.ScribOutputEvent, " + rootPack + ".handlers.states." + this.self + ".messages.interfaces." + messageIfName + " {\n";
		messageAbstract += "}\n";
		
		return messageAbstract;* /
	}
	
	Map<Payload, ClassBuilder> generateMessageClasses(String endpointName, EState s, String mprefix, String rootPack)
	{	
		/*String messageIfName = endpointName + 
				s.getActions().stream().map(a -> "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining())).collect(Collectors.joining());
		String messageInterface = generateMessageInterface(s, messageIfName, endpointName, rootPack, mprefix);
		res.put(mprefix + "interfaces/" + messageIfName + ".java", messageInterface);* /
		
		String messageAbstractName = getMessageAbstractName(endpointName, s);  // FIXME: is interface -- factor out
		//String messageAbstract = generateMessageAbstract(messageIfName, messageAbstractName, endpointName, rootPack, mprefix);

		Module main = this.job.getContext().getMainModule();
		Map<Payload, ClassBuilder> messageClasses = new HashMap<>();  // A class per Payload for this state
		for (EAction a : s.getAllActions())
		{
			/*boolean isSig = jc.getMainModule().getNonProtocolDecls().stream()  // FIXME
				.anyMatch(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString()));* /

			ClassBuilder messageClass;
			if (!messageClasses.containsKey(a.payload))
			{
				messageClass = generateMessageClass(//isSig, messageName,
						s, a, endpointName, rootPack, messageAbstractName);
				messageClasses.put(a.payload, messageClass);
			}
			else
			{
				messageClass = messageClasses.get(a.payload);
			}

			/*String messageName = isSig
					? endpointName + "_" + SessionApiGenerator.getOpClassName(a.mid)
					: endpointName + "_" + s.id + "_" + SessionApiGenerator.getOpClassName(a.mid);
			String messageClass = generateMessageClass(isSig, messageName, s, a, messageIfName, rootPack, messageAbstractName);
			res.put(mprefix + messageName + ".java", messageClass);* /
			int[] i = { 2 };
			String[] args = new String[a.payload.elems.size() + 2];
			args[0] = getRolesPackage() + "." + a.peer + " peer";
			args[1] = getOpsPackage() + "." + SessionApiGenerator.getOpClassName(a.mid) + " op";
			a.payload.elems.forEach(e -> args[i[0]++] = main.getDataTypeDecl((DataType) e).getExtName() + " arg" + (i[0]-3));
			ConstructorBuilder ob = messageClass.newConstructor(args);
			ob.addModifiers("public");
			ob.addBodyLine("super(op" + IntStream.range(0, a.payload.elems.size()).mapToObj(j -> ", arg" + j).collect(Collectors.joining()) + ");");
		}
		return messageClasses;
	}
	
	String generateMessageInterface(EState s, String messageIfName,  String endpointName, String rootPack, String mprefix)
	{
				// FIXME: sort cases
				// FIXME: generate lattice -- cf. original handler i/f's (with "subtyping")
				String messageInterface = "";
				messageInterface += "package " + rootPack + ".handlers.states." + this.self + ".messages.interfaces;\n";
				messageInterface += "\n";
				messageInterface += "public interface " + messageIfName + " {\n";
				messageInterface += "}\n";
				return messageInterface;
	}
	
	ClassBuilder generateMessageClass(//boolean isSig, String messageName,
			EState s, EAction a, String endpointName, String rootPack, String messageAbstractName)
	{
		Module main = this.job.getContext().getMainModule();
		
		ClassBuilder messageClass;
		String name = //"Pay_" +
				this.self + "_" + s.id + "_"  // FIXME: factor out
				+ (a.payload.elems.isEmpty()
						? "Unit"
						: a.payload.elems.stream().map(e ->
								{
									String extName = main.getDataTypeDecl((DataType) e).getExtName();
									return (extName.indexOf(".") != -1)
											? extName.substring(extName.lastIndexOf(".")+1, extName.length())
											: extName;
								}).collect(Collectors.joining("_"))
					);  // FIXME: extName sime // Not terminal
		messageClass = new ClassBuilder(name);
		//messageClasses.put(a.payload, messageClass);
		messageClass.setPackage(getMessagesPackage(rootPack));
		messageClass.addModifiers("public");
		messageClass.setSuperClass("org.scribble.runtime.message.ScribMessage");
		messageClass.addInterfaces(messageAbstractName);
		MethodBuilder getPeer = messageClass.newMethod("getPeer");
		getPeer.addModifiers("public");
		getPeer.addAnnotations("@Override");
		getPeer.setReturn("org.scribble.type.name.Role");
		getPeer.addBodyLine("return " + getRolesPackage() + "." + a.peer + "." + a.peer + ";");
		MethodBuilder getOp = messageClass.newMethod("getOp");
		getOp.addModifiers("public");
		getOp.addAnnotations("@Override");
		getOp.setReturn("org.scribble.type.name.Op");
		getOp.addBodyLine("return " + getOpsPackage() + "." + SessionApiGenerator.getOpClassName(a.mid) + "." + SessionApiGenerator.getOpClassName(a.mid) + ";");
		MethodBuilder getPayload = messageClass.newMethod("getPayload");
		getPayload.addModifiers("public");
		getPayload.addAnnotations("@Override");
		getPayload.setReturn("Object[]");
		getPayload.addBodyLine("return this.payload;");
		FieldBuilder fb = messageClass.newField("serialVersionUID");
		fb.addModifiers("private", "static", "final");
		fb.setType("long");
		fb.setExpression("1L");
		
		return messageClass;

		/*MessageSigNameDecl msnd = null;
		if (isSig)
		{
			msnd = getMessageSigNameDecl((MessageSigName) a.mid);
		}

		String messageClass = "";
		messageClass += "package " + getMessagesPackage(rootPack) + ";\n";
		messageClass += "\n";
		// extends ScribMessage just for convenience of storing payload (peer/op pre-known) -- instances of this class never "directly" sent, cf., CBEndpoint
		messageClass += "public class " + messageName + " extends org.scribble.runtime.message.ScribMessage implements " + messageAbstractName;
		if (isSig)
		{
			messageClass += ", org.scribble.runtime.handlers.ScribSigMessage";
		}
		messageClass += " {\n";
		messageClass += "private static final long serialVersionUID = 1L;\n";
		if (isSig)
		{
			messageClass += "private final " + msnd.getExtName() + " m;\n";
		}
		messageClass += "\n";
		messageClass += "public " + messageName + "(" + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + a.peer + " peer";
		if (isSig)
		{
			messageClass += ", " + msnd.getExtName() + " m"; 
		}
		else
		{
			int i = 1;
			Module main = this.job.getContext().getMainModule();
			for (PayloadElemType<?> pet : a.payload.elems)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pet);
				messageClass += ", " + dtd.getExtName() + " arg" + i++;
			}
		}
		messageClass += ") {\n";
		messageClass += "super("
				+ SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + "." + SessionApiGenerator.getOpClassName(a.mid)
				+ IntStream.rangeClosed(1, a.payload.elems.size()).mapToObj(j -> ", arg" + j).collect(Collectors.joining("")) + ");\n";
		if (isSig)
		{
			messageClass += "this.m = m;\n";
		}
		messageClass += "}\n";
		messageClass += "\n";
		messageClass += "@Override\n";
		messageClass += "public org.scribble.type.name.Role getPeer() {\n";
		messageClass += "return " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + a.peer + "." + a.peer + ";\n";
		messageClass += "}\n";
		messageClass += "\n";
		messageClass += "@Override\n";
		messageClass += "public org.scribble.type.name.Op getOp() {\n";
		messageClass += "return " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + "." + SessionApiGenerator.getOpClassName(a.mid) + ";\n";
		messageClass += "}\n";
		messageClass += "\n";
		messageClass += "@Override\n";
		messageClass += "public Object[] getPayload() {\n";
		messageClass += "return this.payload;\n";
		messageClass += "}\n";
		if (isSig)
		{
			messageClass += "\n";
			messageClass += "@Override\n";
			messageClass += "public org.scribble.runtime.message.ScribMessage getSig() {\n";
			messageClass += "return this.m;\n";
			messageClass += "}\n";
		}
		messageClass += "}\n";
		
		return messageClass;* /
	}*/

	protected String getHandlersPackage()
	{
		return SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".callbacks";
	}
	
	protected String getHandlersSelfPackage()
	{
		return getHandlersPackage() + "." + this.self;
	}

	protected String getStatesSelfPackage()
	{
		return getHandlersPackage() + "." + this.self + ".states";
	}
	
	protected String getMessageAbstractName(String endpointName, EState s)
	{
		//return endpointName + "_" + s.id + "_Message";
		return "Pay_" + this.self + "_" + s.id;
	}
	
	protected String getRolesPackage()
	{
		return SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles";
	}
	
	protected String getOpsPackage()
	{
		return SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops";
	}
	
	protected String getMessagesPackage(String rootPack)
	{
		return rootPack + ".handlers." + this.self + ".states.messages";
	}
	
	protected SigDecl getMessageSigNameDecl(SigName mid)
	{
		return (SigDecl)
				this.job.getContext().getMainModule().getNonProtoDeclChildren().stream()  // FIXME: main module?
					.filter(npd -> (npd instanceof SigDecl) && ((SigDecl) npd).getDeclName().toString().equals(mid.toString()))
					.iterator().next();
	}

	//protected final Function<PayloadElemType<?>, String> getExtName = e ->
	protected String getExtName(PayElemType<?> e)
	{
		String extName = this.job.getContext().getMainModule().getTypeDeclChild((DataName) e).getExtName();
		return (extName.indexOf(".") != -1)
				? extName.substring(extName.lastIndexOf(".")+1, extName.length())
				: extName;
	};

	protected final Function<EAction, String> getCallbackSuffix = a ->
			a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid)
					+ a.payload.elems.stream().map(e -> "_" + getExtName(e)).collect(Collectors.joining(""));  // FIXME: factor out

	/*public String generateScribbleRuntimeImports()
	{
		return getScribbleRuntimeImports().stream().map(x -> "import \"" + x + "\"\n").collect(Collectors.joining());
	}

	private List<String> getScribbleRuntimeImports()  // FIXME: factor up
	{
		return Stream.of(
					//EDApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE
				""
				).collect(Collectors.toList());
	}*/
}
