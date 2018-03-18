package org.scribble.codegen.java.callbackapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.ConstructorBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.Payload;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.PayloadElemType;
import org.scribble.type.name.Role;

// FIXME: integrate with JEndpointApiGenerator -- this class should correspond to StateChanApiGenerator (relying on the common SessionApiGenerator)
public class CBEndpointApiGenerator2
{
	public final Job job;
	public final GProtocolName proto;
	public final Role self;  // FIXME: base endpoint API gen is role-oriented, while session API generator should be neutral
	
	//private final boolean subtypes;  // Generate full hierarchy (states -> states, not just indivdual state -> cases) -- cf. ioifaces

	public CBEndpointApiGenerator2(Job job, GProtocolName fullname, Role self, boolean subtypes)
	{
		this.job = job;
		this.proto = fullname;
		this.self = self;
		
		//this.subtypes = subtypes;
		if (subtypes)
		{
			throw new RuntimeException("TODO: -subtypes");
		}
	}

	public Map<String, String> build() throws ScribbleException
	{
		Map<String, String> res = new HashMap<>();  // filepath -> source 
		res.putAll(buildSessionApi());
		return res;
	}

	// FIXME: factor out -- integrate with JEndpointApiGenerator
	public Map<String, String> buildSessionApi() throws ScribbleException
	{
		this.job.debugPrintln("\n[param-core] Running " + CBEndpointApiGenerator2.class + " for " + this.proto + "@" + this.self);

		Map<String, String> res = new HashMap<>();
		res.putAll(new SessionApiGenerator(this.job, this.proto).generateApi());
		res.putAll(buildEndpointClass());
		return res;
	}

	String getHandlersPackage()
	{
		return SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers";
	}
	
	String getHandlersSelfPackage()
	{
		return getHandlersPackage() + "." + this.self;
	}

	String getStatesSelfPackage()
	{
		return getHandlersPackage() + ".states." + this.self;
	}
	
	public Map<String, String> buildEndpointClass() throws ScribbleException
	{
		Map<String, String> res = new HashMap<>();

		JobContext jc = this.job.getContext();
		EState init = (this.job.minEfsm ? jc.getMinimisedEGraph(this.proto, this.self) : jc.getEGraph(this.proto, this.self)).init;  // FIXME: factor out with StateChannelApiGenerator constructor
		Set<EState> states = new HashSet<>();
		states.add(init);
		states.addAll(MState.getReachableStates(init));

		// frontend handler class
		String rootPack = SessionApiGenerator.getEndpointApiRootPackageName(this.proto);
		String endpointName = this.proto.getSimpleName() + "_" + this.self;
		String initStateName = getStatesSelfPackage() + "." + endpointName + "_" + init.id;  // FIXME: factor out
		String sessClassName = rootPack + "." + this.proto.getSimpleName();
		String prefix = getHandlersSelfPackage().replace('.', '/') + "/";  // StateChannelApiGenerator#generateApi

		//String epClass = generateEndpointClass(rootPack, endpointName, sessClass, init, states);
		ClassBuilder endpointClass = new ClassBuilder(endpointName);
		endpointClass.setPackage(getHandlersSelfPackage());
		endpointClass.addModifiers("public");
		endpointClass.addParameters("D");
		endpointClass.setSuperClass("org.scribble.runtime.session.CBEndpoint<" + sessClassName + ", " + getRolesPackage() + "." + this.self + ", D>");
		ConstructorBuilder cb = endpointClass.newConstructor(sessClassName + " sess", getRolesPackage() + "." + this.self + " self", "org.scribble.runtime.message.ScribMessageFormatter smf", "D data");
		cb.addExceptions("java.io.IOException", "org.scribble.main.ScribbleRuntimeException");
		cb.addBodyLine("super(sess, self, smf, " + initStateName + ".id, data);");
		for (EState s : states)
		{
			String tmp = (s.getStateKind() == EStateKind.TERMINAL) ? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + s.id;
			cb.addBodyLine("this.states.put(\"" + tmp + "\", " + getStatesSelfPackage() + "." + tmp + ".id);");
		}
		MethodBuilder mb = endpointClass.newMethod("run");
		mb.addModifiers("public");
		mb.setReturn("java.util.concurrent.Future<Void>");
		mb.addExceptions("org.scribble.main.ScribbleRuntimeException");
		mb.addAnnotations("@Override");
		mb.addBodyLine("java.util.Set<Object> states = java.util.stream.Stream.of(" + initStateName + ".id).collect(java.util.stream.Collectors.toSet());");
		mb.addBodyLine("java.util.Set<Object> regd = new java.util.HashSet<>();");
		mb.addBodyLine("regd.addAll(this.inputs.keySet());");
		mb.addBodyLine("regd.addAll(this.outputs.keySet());");
		mb.addBodyLine("if (!states.equals(regd)) {");
		mb.addBodyLine("states.removeAll(regd);");
		mb.addBodyLine("throw new org.scribble.main.ScribbleRuntimeException(\"Missing state registrations: \" + states);");
		mb.addBodyLine("}");
		mb.addBodyLine("return super.run();");
		for (EState s : states)
		{
			switch (s.getStateKind())
			{
				case OUTPUT:
				{
					MethodBuilder callback = endpointClass.newMethod("callback");
					callback.addModifiers("public");
					callback.setReturn(endpointName + "<D>");
					callback.addParameters(getStatesSelfPackage() + "." + endpointName + "_" + s.id + " sid",  // FIXME: factor out
							"java.util.function.Function<D, " + getMessagesPackage(rootPack) + "." + getMessageAbstractName(endpointName, s) + "> cb");  // FIXME: factor out
					callback.addBodyLine("this.outputs.put(sid, cb);");
					callback.addBodyLine("return this;");
					break;
				}
				case UNARY_INPUT:
				case POLY_INPUT:
				case WRAP_SERVER:
				case ACCEPT:
				{
					throw new RuntimeException("TODO: " + s.getStateKind());
				}
				case TERMINAL:
				{
					break;
				}
				default:
				{
					throw new RuntimeException("Shouldn't get in here: " + s.getStateKind());
				}
			}
		}
		res.put(prefix + endpointName + ".java", endpointClass.build());
		
		// states
		String sprefix = getStatesSelfPackage().replace('.', '/') + "/";  // StateChannelApiGenerator#generateApi
		for (EState s : states)
		{
			EStateKind kind = s.getStateKind();
			String stateId = (kind == EStateKind.TERMINAL) ? "End" : endpointName + "_" + s.id;
			String stateClass = generateStateClass(s, rootPack, kind, stateId);
			res.put(sprefix + stateId + ".java", stateClass);
			
			// messages
			if (s.getStateKind() == EStateKind.OUTPUT)
			{
				String mprefix = getMessagesPackage(rootPack).replace('.', '/') + "/";

				/*String messageIfName = endpointName + 
						s.getActions().stream().map(a -> "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining())).collect(Collectors.joining());
				String messageInterface = generateMessageInterface(s, messageIfName, endpointName, rootPack, mprefix);
				res.put(mprefix + "interfaces/" + messageIfName + ".java", messageInterface);*/
				
				String messageAbstractName = getMessageAbstractName(endpointName, s);  // FIXME: is interface
				//String messageAbstract = generateMessageAbstract(messageIfName, messageAbstractName, endpointName, rootPack, mprefix);
				InterfaceBuilder messageAbstract = new InterfaceBuilder(messageAbstractName);
				messageAbstract.setPackage(getMessagesPackage(rootPack));
				messageAbstract.addModifiers("public");
				messageAbstract.addInterfaces("org.scribble.runtime.handlers.ScribOutputEvent");
				res.put(mprefix + messageAbstractName + ".java", messageAbstract.build());

				Module main = this.job.getContext().getMainModule();
				Map<Payload, ClassBuilder> messageClasses = new HashMap<>();  // A class per Payload for this state
				for (EAction a : s.getAllActions())
				{
					boolean isSig = jc.getMainModule().getNonProtocolDecls().stream()  // FIXME
						.anyMatch(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString()));

					ClassBuilder messageClass;
					if (!messageClasses.containsKey(a.payload))
					{
						String name = //"Pay_" +
								this.self + "_" + s.id + "_"  // FIXME: factor out
								+ (a.payload.elems.isEmpty()
										? "Unit"
										: a.payload.elems.stream().map(e ->
												{
													String extName = main.getDataTypeDecl((DataType) e).extName;
													return (extName.indexOf(".") != -1)
															? extName.substring(extName.lastIndexOf(".")+1, extName.length())
															: extName;
												}).collect(Collectors.joining("_"))
									);  // FIXME: extName sime // Not terminal
						messageClass = new ClassBuilder(name);
						messageClasses.put(a.payload, messageClass);
						messageClass.setPackage(getMessagesPackage(rootPack));
						messageClass.addModifiers("public");
						messageClass.setSuperClass("org.scribble.runtime.message.ScribMessage");
						messageClass.addInterfaces(messageAbstractName);
						MethodBuilder getPeer = messageClass.newMethod("getPeer");
						getPeer.addModifiers("public");
						getPeer.addAnnotations("@Override");
						getPeer.setReturn("org.scribble.type.name.Role");
						getPeer.addBodyLine("return " + getRolesPackage() + "." + this.self + "." + this.self + ";");
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
					}
					else
					{
						messageClass = messageClasses.get(a.payload);
					}

					
					/*String messageName = isSig
							? endpointName + "_" + SessionApiGenerator.getOpClassName(a.mid)
							: endpointName + "_" + s.id + "_" + SessionApiGenerator.getOpClassName(a.mid);
					String messageClass = generateMessageClass(isSig, messageName, s, a, messageIfName, rootPack, messageAbstractName);
					res.put(mprefix + messageName + ".java", messageClass);*/
					int[] i = { 2 };
					String[] args = new String[a.payload.elems.size() + 2];
					args[0] = getRolesPackage() + "." + a.peer + " peer";
					args[1] = getOpsPackage() + "." + SessionApiGenerator.getOpClassName(a.mid) + " op";
					a.payload.elems.forEach(e -> args[i[0]++] = main.getDataTypeDecl((DataType) e).extName + " arg" + (i[0]-3));
					ConstructorBuilder ob = messageClass.newConstructor(args);
					ob.addBodyLine("super(op" + IntStream.range(0, a.payload.elems.size()).mapToObj(j -> ", arg" + j).collect(Collectors.joining()) + ");");
				}
				messageClasses.values().forEach(c -> res.put(mprefix + c.getName() + ".java", c.build()));
			}
			
			/*// branches
			if (s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)
			{
				String bprefix = SessionApiGenerator.getEndpointApiRootPackageName(this.proto).replace('.', '/') + "/handlers/" + this.self + "/";
				String branchName = endpointName + "_" + s.id + "_Branch";

				for (EAction a : s.getAllActions())
				{
					// FIXME: factor out
					boolean isSig = jc.getMainModule().getNonProtocolDecls().stream()
						.anyMatch(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString()));
					MessageSigNameDecl msnd = null;
					if (isSig)
					{
						msnd = (MessageSigNameDecl) jc.getMainModule().getNonProtocolDecls().stream()
								.filter(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString())).iterator().next();
					}
					
					String receiveIfName = endpointName + "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining());
					String receiveInterface = generateReceiveInterface(isSig, msnd, jc, a, rootPack, receiveIfName);
					res.put(bprefix + "interfaces/" + receiveIfName + ".java", receiveInterface);
				}

				String branchAbstract = generateBranch(bprefix, jc, s, endpointName, rootPack, branchName);
				res.put(bprefix + branchName + ".java", branchAbstract);
			}*/
		}
		
		return res;
	}

	String generateEndpointClass(String rootPack, String endpointName, String sess, EState init, Set<EState> states)
	{
		String role = SessionApiGenerator.getRolesPackageName(this.proto) + "." + this.self;
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
		
		return epClass;
	}
	
	String generateReceiveInterface(boolean isSig, MessageSigNameDecl msnd, JobContext jc, EAction a, String rootPack, String receiveIfName)
	{
					String receiveInterface = "";
					receiveInterface += "package " + rootPack + ".handlers." + this.self + ".interfaces;\n";
					receiveInterface += "\n";
					receiveInterface += "public interface " + receiveIfName + "<D> {\n";


					receiveInterface += "\npublic void receive(D data, " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + a.peer + " peer, ";
					if (isSig)
					{
						receiveInterface += msnd.extName + " m";
					}
					else
					{
						receiveInterface += SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + " op"; 
						int i = 1;
						for (PayloadElemType<?> pet : a.payload.elems)
						{
							DataTypeDecl dtd = jc.getMainModule().getDataTypeDecl((DataType) pet);
							receiveInterface += ", " + dtd.extName + " arg" + i++;
						}
					}
					receiveInterface += ");\n";
					receiveInterface += "}\n";
					
					return receiveInterface;
	}
	
	String generateBranch(String bprefix, JobContext jc, EState s, String endpointName, String rootPack, String branchName)
	{
				String branchAbstract = "";
				branchAbstract += "package " + rootPack + ".handlers." + this.self + ";\n";
				branchAbstract += "";
				branchAbstract += "public abstract class " + branchName + "<D> implements org.scribble.runtime.handlers.ScribBranch<D>";
				branchAbstract += s.getAllActions().stream().map(a ->
						", " + rootPack + ".handlers." + this.self + ".interfaces." + endpointName + "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining()) + "<D>").collect(Collectors.joining(""));
				branchAbstract += " {\n";

				branchAbstract += "\n";
				branchAbstract += "@Override\n";
				branchAbstract += "public void dispatch(D data, org.scribble.runtime.message.ScribMessage m) {\n";
				branchAbstract += "switch (m.op.toString()) {\n";
				for (EAction a : s.getAllActions())
				{
					// FIXME: factor out
					boolean isSig = jc.getMainModule().getNonProtocolDecls().stream()
						.anyMatch(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString()));
					MessageSigNameDecl msnd = null;
					if (isSig)
					{
						msnd = (MessageSigNameDecl) jc.getMainModule().getNonProtocolDecls().stream()
								.filter(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString())).iterator().next();
					}

					branchAbstract += "case \"" + SessionApiGenerator.getOpClassName(a.mid) + "\": receive(data, " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + a.peer + "." + a.peer + ", ";
					if (isSig)
					{
						branchAbstract += "(" + msnd.extName + ") m";
					}
					else
					{
						branchAbstract += "(" + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + ") m.op";
					}
					int i = 0;
					for (PayloadElemType<?> pet : a.payload.elems)
					{
						DataTypeDecl dtd = jc.getMainModule().getDataTypeDecl((DataType) pet);
						branchAbstract += ", (" + dtd.extName + ") m.payload[" + i++ + "]";
					}
					branchAbstract += "); break;\n";
				}
				branchAbstract += "default: throw new RuntimeException(\"Shouldn't get in here: \" + m);\n";
				branchAbstract += "}\n";
				branchAbstract += "}\n";
				branchAbstract += "}\n";
				
				return branchAbstract;
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
	
	MessageSigNameDecl getMessageSigNameDecl(MessageSigName mid)
	{
		return (MessageSigNameDecl)
				this.job.getContext().getMainModule().getNonProtocolDecls().stream()  // FIXME: main module?
					.filter(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(mid.toString()))
					.iterator().next();
	}
	
	String getMessagesPackage(String rootPack)
	{
		return rootPack + ".handlers.states." + this.self + ".messages";
	}
	
	String generateMessageClass(boolean isSig, String messageName, EState s,
			EAction a, String endpointName, String rootPack, String messageAbstractName)
	{
		MessageSigNameDecl msnd = null;
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
			messageClass += "private final " + msnd.extName + " m;\n";
		}
		messageClass += "\n";
		messageClass += "public " + messageName + "(" + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + a.peer + " peer";
		if (isSig)
		{
			messageClass += ", " + msnd.extName + " m"; 
		}
		else
		{
			int i = 1;
			Module main = this.job.getContext().getMainModule();
			for (PayloadElemType<?> pet : a.payload.elems)
			{
				DataTypeDecl dtd = main.getDataTypeDecl((DataType) pet);
				messageClass += ", " + dtd.extName + " arg" + i++;
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
		
		return messageClass;
}

String generateMessageAbstract(String messageIfName, String messageAbstractName, String endpointName, String rootPack, String mprefix)
	{
				String messageAbstract = "";
				messageAbstract += "package " + rootPack + ".handlers.states." + this.self + ".messages;\n";
				messageAbstract += "\n";
				messageAbstract += "public interface " + messageAbstractName + " extends org.scribble.runtime.handlers.ScribOutputEvent, " + rootPack + ".handlers.states." + this.self + ".messages.interfaces." + messageIfName + " {\n";
				messageAbstract += "}\n";
				
				return messageAbstract;
	}

	String generateStateClass(EState s, String rootPack, EStateKind kind, String stateId)
	{
			String stateKind;
			switch (kind)
			{
				case OUTPUT:     stateKind = "org.scribble.runtime.handlers.states.ScribOutputState"; break;
				case UNARY_INPUT:
				case POLY_INPUT: stateKind = "org.scribble.runtime.handlers.states.ScribInputState";  break;
				case TERMINAL:   stateKind = "org.scribble.runtime.handlers.states.ScribEndState";    break;
				case ACCEPT:
				case WRAP_SERVER:
					throw new RuntimeException("TODO");
				default:
					throw new RuntimeException("TODO");
			}
			String stateClass = "";
			stateClass += "package " + rootPack + ".handlers.states." + this.self + ";\n";
			stateClass += "\n";
			stateClass += "public final class " + stateId + " extends " + stateKind + " {\n";
			stateClass += "public static final " + stateId + " id = new " + stateId + "("
					+ ((kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT) ? SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + s.getActions().get(0).peer + "." + s.getActions().get(0).peer : "")
					+ ");\n";
			stateClass += "private " + stateId + "("
					+ ((kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT) ? "org.scribble.type.name.Role peer" : "")
					+ ") {\n";
			stateClass += ((kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT) ? "super(\"" + stateId + "\", peer);" : "super(\"" + stateId + "\");") + "\n";
			for (EAction a : s.getAllActions())
			{
				stateClass += "this.succs.put(" + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + "." + SessionApiGenerator.getOpClassName(a.mid)
						+ ", "
						/*+ ((s.getSuccessor(a).id == s.id)
								? "this"
								: ((s.getSuccessor(a).getStateKind() == EStateKind.TERMINAL) ? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + s.getSuccessor(a).id) + ".id")*/
						+ "\"" + ((s.getSuccessor(a).getStateKind() == EStateKind.TERMINAL) ? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + s.getSuccessor(a).id) + "\""
						+ ");\n";
			}
			stateClass += "}\n";
			stateClass += "}\n";
			
			return stateClass;
	}

	private String generateRegister(GProtocolName gpn, Role self, EState s)
	{
		String prefix = SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + "." ;
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
				res += "return this;\n";*/
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
		return res;
	}
	
	String getMessageAbstractName(String endpointName, EState s)
	{
		//return endpointName + "_" + s.id + "_Message";
		return "Pay_" + this.self + "_" + s.id;
	}
	
	String getRolesPackage()
	{
		return SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles";
	}
	
	String getOpsPackage()
	{
		return SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops";
	}

	public String generateScribbleRuntimeImports()
	{
		return getScribbleRuntimeImports().stream().map(x -> "import \"" + x + "\"\n").collect(Collectors.joining());
	}

	private List<String> getScribbleRuntimeImports()  // FIXME: factor up
	{
		return Stream.of(
					//EDApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE
				""
				).collect(Collectors.toList());
	}
}
