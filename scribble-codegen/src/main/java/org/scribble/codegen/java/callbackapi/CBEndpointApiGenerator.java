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
import org.scribble.codegen.java.sessionapi.SessionApiGenerator;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.PayloadElemType;
import org.scribble.type.name.Role;

// FIXME: integrate with JEndpointApiGenerator -- this class should correspond to StateChanApiGenerator (relying on the common SessionApiGenerator)
// From ParamCoreEndpointApiGenerator
public class CBEndpointApiGenerator
{
	public final Job job;
	public final GProtocolName proto;
	public final Role self;  // FIXME: base endpoint API gen is role-oriented, while session API generator should be neutral
	
	private final boolean subtypes;

	public CBEndpointApiGenerator(Job job, GProtocolName fullname, Role self, boolean subtypes)
	{
		this.job = job;
		this.proto = fullname;
		this.self = self;
		
		this.subtypes = subtypes;
		if (this.subtypes)
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

	public Map<String, String> buildSessionApi() throws ScribbleException // FIXME: factor out -- integrate with JEndpointApiGenerator
	{
		this.job.debugPrintln("\n[param-core] Running " + CBEndpointApiGenerator.class + " for " + this.proto + "@" + this.self);

		Map<String, String> res = new HashMap<>();
		res.putAll(new SessionApiGenerator(this.job, this.proto).generateApi());
		res.putAll(buildEndpointClass());
		return res;
	}
	
	public Map<String, String> buildEndpointClass() throws ScribbleException
	{
		JobContext jc = this.job.getContext();
		EState init = (this.job.minEfsm ? jc.getMinimisedEGraph(this.proto, this.self) : jc.getEGraph(this.proto, this.self)).init;  // FIXME: factor out with StateChannelApiGenerator constructor
		Set<EState> states = new HashSet<>();
		states.add(init);
		states.addAll(MState.getReachableStates(init));

		// frontend handler class
		String pack = SessionApiGenerator.getEndpointApiRootPackageName(this.proto);
		String name = this.proto.getSimpleName() + "_" + this.self;
		String sess = pack + "." + this.proto.getSimpleName();
		String role = SessionApiGenerator.getRolesPackageName(this.proto) + "." + this.self;
		String epClass = "";
		
		epClass += "package " + pack + ".handlers." + this.self + ";\n";
		epClass += "\n";
		/*epClass += "import java.util.function.Function;\n";
		epClass += "import java.util.HashMap;\n";
		epClass += "import java.util.Map;\n";*/
		epClass += "\n";
		epClass += "public class " + name + "<D> extends org.scribble.runtime.session.CBEndpoint<" + sess + ", " + role + ", D> {\n";

		/*epClass += "private final Map<Object, Function<Object, org.scribble.runtime.message.ScribMessage>> outputs = new HashMap<>();\n";
		epClass += "\n";*/

		epClass += "public " + name + "(" + sess  + " sess, " + role
				+ " self, org.scribble.runtime.message.ScribMessageFormatter smf, D data) throws java.io.IOException, org.scribble.main.ScribbleRuntimeException {\n";
		epClass += "super(sess, self, smf, " + pack + ".handlers.states." + this.self + "." + name + "_" + init.id + ".id" + ", data);\n";  // FIXME: factor out
		for (EState s : states)
		{
			String tmp = (s.getStateKind() == EStateKind.TERMINAL) ? "End" : this.proto.getSimpleName() + "_" + this.self + "_" + s.id;
			epClass += "this.states.put(\"" + tmp + "\", " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + "." + tmp + ".id);\n";
		}
		epClass += "}\n";
		epClass += "\n";
		
		/*epClass += "public enum State {" + states.stream().map(s -> getEDStateEnum(this.proto, this.self, s)) + "}\n";
		epClass += "\n";*/

		epClass += states.stream().map(s -> generateRegister(this.proto, this.self, s)).collect(Collectors.joining("\n"));
		/*epClass += "public void register(State s, Function<Object, org.scribble.runtime.message.ScribMessage> h) {\n";
		epClass += "this.outputs.put(s, h)";
		epClass += "}\n";
		epClass += "\n";
		epClass += "public void register(State s, Function<Object, org.scribble.runtime.message.ScribMessage> h) {\n";
		epClass += "this.outputs.put(s, h)";
		epClass += "}\n";*/
		
		epClass += "@Override\n";
		epClass += "public java.util.concurrent.Future<Void> run() throws org.scribble.main.ScribbleRuntimeException {\n";
		epClass += "java.util.Set<Object> states = java.util.stream.Stream.of("
				+ states.stream().filter(s -> s.getStateKind() != EStateKind.TERMINAL)
						.map(s -> SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + "." + this.proto.getSimpleName() + "_" + this.self + "_" + s.id + ".id")
						.collect(Collectors.joining(", ")) + ").collect(java.util.stream.Collectors.toSet());\n";
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
		
		Map<String, String> res = new HashMap<>();
		String prefix = SessionApiGenerator.getEndpointApiRootPackageName(this.proto).replace('.', '/') + "/handlers/" + this.self + "/";  // StateChannelApiGenerator#generateApi
		res.put(prefix + name + ".java", epClass);
		
		// states
		String sprefix = SessionApiGenerator.getEndpointApiRootPackageName(this.proto).replace('.', '/') + "/handlers/states/" + this.self + "/";  // StateChannelApiGenerator#generateApi
		for (EState s : states)
		{
			EStateKind kind = s.getStateKind();
			String stateId = (kind == EStateKind.TERMINAL) ? "End" : name + "_" + s.id;
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
			stateClass += "package " + pack + ".handlers.states." + this.self + ";\n";
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
			res.put(sprefix + stateId + ".java", stateClass);
			
			// messages
			if (s.getStateKind() == EStateKind.OUTPUT)
			{
				String mprefix = SessionApiGenerator.getEndpointApiRootPackageName(this.proto).replace('.', '/') + "/handlers/states/" + this.self + "/messages/";

				// FIXME: sort cases
				// FIXME: generate lattice -- cf. original handler i/f's (with "subtyping")
				String messageIfName = name + 
						//a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining());
						s.getActions().stream().map(a -> "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining())).collect(Collectors.joining());
				String messageInterface = "";
				messageInterface += "package " + pack + ".handlers.states." + this.self + ".messages.interfaces;\n";
				messageInterface += "\n";
				messageInterface += "public interface " + messageIfName + " {\n";
				messageInterface += "}\n";
				res.put(mprefix + "interfaces/" + messageIfName + ".java", messageInterface);
				
				String messageAbstractName = name + "_" + s.id + "_Message";
				String messageAbstract = "";
				messageAbstract += "package " + pack + ".handlers.states." + this.self + ".messages;\n";
				messageAbstract += "\n";
				messageAbstract += "public interface " + messageAbstractName + " extends org.scribble.runtime.handlers.ScribOutputEvent, " + pack + ".handlers.states." + this.self + ".messages.interfaces." + messageIfName + " {\n";
				/*messageAbstract += "private static final long serialVersionUID = 1L;\n";
				messageAbstract += "\n";
				messageAbstract += "public " + messageAbstractName + "(org.scribble.type.name.Role peer, org.scribble.type.name.Op op, Object... payload) {\n";
				messageAbstract += "super(peer, op, payload);\n";
				messageAbstract += "}\n";*/
				messageAbstract += "}\n";
				res.put(mprefix + messageAbstractName + ".java", messageAbstract);

				for (EAction a : s.getAllActions())
				{
					/*String messageIfName = name + "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining());
					String messageInterface = "";
					messageInterface += "package " + pack + ".handlers.states." + this.self + ".messages.interfaces;\n";
					messageInterface += "\n";
					messageInterface += "public interface " + messageIfName + " {\n";
					messageInterface += "}\n";
					res.put(mprefix + "interfaces/" + messageIfName + ".java", messageInterface);*/
					
					boolean isSig = jc.getMainModule().getNonProtocolDecls().stream()
						.anyMatch(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString()));
					MessageSigNameDecl msnd = null;
					if (isSig)
					{
						msnd = (MessageSigNameDecl) jc.getMainModule().getNonProtocolDecls().stream()
								.filter(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString())).iterator().next();
					}

					String messageName;
					if (isSig)
					{
						messageName = name + "_" + SessionApiGenerator.getOpClassName(a.mid);
					}
					else
					{
						messageName = name + "_" + s.id + "_" + SessionApiGenerator.getOpClassName(a.mid);
					}
					String messageClass = "";
					messageClass += "package " + pack + ".handlers.states." + this.self + ".messages;\n";
					messageClass += "\n";
					// extends ScribMessage just for convenience of storing payload (peer/op pre-known) -- instances of this class never "directly" sent, cf., CBEndpoint
					messageClass += "public class " + messageName + " extends org.scribble.runtime.message.ScribMessage implements " + messageAbstractName; //+ " implements " + pack + ".handlers.states." + this.self + ".messages.interfaces." + messageIfName;
					if (isSig)
					{
						messageClass += ", org.scribble.runtime.handlers.ScribSigMessage";
					}
					messageClass += " {\n";
					//messageClass += "public final java.util.List<Object> pay = new java.util.LinkedList<>();\n";
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
						for (PayloadElemType<?> pet : a.payload.elems)
						{
							DataTypeDecl dtd = jc.getMainModule().getDataTypeDecl((DataType) pet);
							messageClass += ", " + dtd.extName + " arg" + i++;
						}
					}
					messageClass += ") {\n";
					/*for (int j = 1; j <= a.payload.elems.size(); j++)
					{
						messageClass += "this.pay.add(arg" + j + ");\n";
					}*/
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
					res.put(mprefix + messageName + ".java", messageClass);
				}
			}
			
			// branches
			if (s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)
			{
				String bprefix = SessionApiGenerator.getEndpointApiRootPackageName(this.proto).replace('.', '/') + "/handlers/" + this.self + "/";
				String branchName = name + "_" + s.id + "_Branch";
				String branchAbstract = "";
				branchAbstract += "package " + pack + ".handlers." + this.self + ";\n";
				branchAbstract += "";
				branchAbstract += "public abstract class " + branchName + "<D> implements org.scribble.runtime.handlers.ScribBranch<D>";
				branchAbstract += s.getAllActions().stream().map(a ->
						", " + pack + ".handlers." + this.self + ".interfaces." + name + "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining()) + "<D>").collect(Collectors.joining(""));
				branchAbstract += " {\n";
				/*for (EAction a : s.getAllActions())
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

					branchInterface += "\npublic abstract void receive(D data, " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".roles." + a.peer + " peer, ";
					if (isSig)
					{
						branchInterface += msnd.extName + " m";
					}
					else
					{
						branchInterface += SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + " op"; 
						int i = 1;
						for (PayloadElemType<?> pet : a.payload.elems)
						{
							DataTypeDecl dtd = jc.getMainModule().getDataTypeDecl((DataType) pet);
							branchInterface += ", " + dtd.extName + " arg" + i++;
						}
					}
					branchInterface += ");\n";
				}*/

				branchAbstract += "\n";
				branchAbstract += "@Override\n";
				branchAbstract += "public void dispatch(D data, org.scribble.runtime.message.ScribMessage m) {\n";
				branchAbstract += "switch (m.op.toString()) {\n";
				for (EAction a : s.getAllActions())
				{
					String receiveIfName = name + "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining());
					String receiveInterface = "";
					receiveInterface += "package " + pack + ".handlers." + this.self + ".interfaces;\n";
					receiveInterface += "\n";
					receiveInterface += "public interface " + receiveIfName + "<D> {\n";
					// FIXME: factor out
					boolean isSig = jc.getMainModule().getNonProtocolDecls().stream()
						.anyMatch(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString()));
					MessageSigNameDecl msnd = null;
					if (isSig)
					{
						msnd = (MessageSigNameDecl) jc.getMainModule().getNonProtocolDecls().stream()
								.filter(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString())).iterator().next();
					}

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
					res.put(bprefix + "interfaces/" + receiveIfName + ".java", receiveInterface);
					
					// FIXME: factor out
					/*boolean isSig = jc.getMainModule().getNonProtocolDecls().stream()
						.anyMatch(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString()));
					MessageSigNameDecl msnd = null;
					if (isSig)
					{
						msnd = (MessageSigNameDecl) jc.getMainModule().getNonProtocolDecls().stream()
								.filter(npd -> (npd instanceof MessageSigNameDecl) && ((MessageSigNameDecl) npd).getDeclName().toString().equals(a.mid.toString())).iterator().next();
					}*/

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

				res.put(bprefix + branchName + ".java", branchAbstract);
			}
		}
		
		/*// SessionApiGenerator#constructOpClasses
		Module mod = this.job.getContext().getModule(this.proto.getPrefix());
		GProtocolName simpname = this.proto.getSimpleName();
		GProtocolDecl gpd = (GProtocolDecl) mod.getProtocolDecl(simpname);
		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		gpd.accept(coll);
		for (MessageId<?> mid : coll.getNames())
		{
			String messageName = SessionApiGenerator.getOpClassName(mid);
			String messageClass = "";
			messageClass += "package " + pack + ".handlers.messages;\n";
			messageClass += "\n";
			messageClass += "public class " + messageName + " {\n";
			messageClass += "public final java.util.List<Object> pay = new java.util.LinkedList<>();\n";
			messageClass += "public " + messageName + "(" + + ")";
			messageClass += "}\n";
			res.put(prefix + messageName + ".java", messageClass);
		}*/
		
		return res;
	}

  /*// cf. StateChannelApiGenerator#newSocketClassName
	public static String getEDStateEnum(GProtocolName gpn, Role self, EState s)
	{
		return gpn.getSimpleName() + "_" + self + "_" + s.id;
	}*/

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
				/*for (EAction a : s.getAllActions())
				{
					String messageIfName = name + "__" + a.peer + "_" + SessionApiGenerator.getOpClassName(a.mid) + a.payload.elems.stream().map(e -> "_" + e).collect(Collectors.joining());  // FIXME: factor out;
					res += "public " + name + " callback(" + prefix + name + "_" + s.id
							+ " sid, java.util.function.Function<D, "
							+ SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers.states." + this.self + ".messages.interfaces." + messageIfName + "> h) {\n";
					res += "this.outputs.put(sid, h);\n";
					res += "return this;\n";
					res += "}\n";
					res += "\n";
				}*/
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
				/*for (EAction a : s.getAllActions())
				{
					res += ", org.scribble.util.function.Function2<" + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid)
							+ ", Object"
							+ ", Void> h_" + a.mid;
				}
				res += "java.util.Map<org.scribble.type.name.Op, org.scribble.util.function.Function2<? extends org.scribble.type.name.Op, Object, Void>> tmp = this.inputs.get(sid);\n";
				res += "if (tmp == null) {\n";
				res += "tmp = new java.util.HashMap<>();\n";
				res += "this.inputs.put(sid, tmp);\n";
				for (EAction a : s.getAllActions())
				{
					res += "tmp.put(" + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".ops." + SessionApiGenerator.getOpClassName(a.mid) + "." + SessionApiGenerator.getOpClassName(a.mid) + ", h_" + a.mid + ");\n";
				}
				res += "}\n";*/
				res += ", " + SessionApiGenerator.getEndpointApiRootPackageName(this.proto) + ".handlers." + this.self + "." + name + "_" + s.id + "_Branch<D> b";
				res += ") {\n";
				/*res += "this.inputs.put(sid, b);\n";
				res += "return this;\n";*/
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

	/*public String generateRootPackageDecl()
	{
		return "package " + getRootPackage();
	}
	
	private String getRootPackage()  // Derives only from proto name
	{
		//throw new RuntimeException("[param-core] TODO:");
		return this.proto.getSimpleName().toString();
	}*/

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
