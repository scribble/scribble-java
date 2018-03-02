package org.scribble.codegen.eventdriven;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// From ParamCoreEndpointApiGenerator
public class EDEndpointApiGenerator
{
	public final Job job;
	public final GProtocolName proto;
	public final Role self;  // FIXME: base endpoint API gen is role-oriented, while session API generator should be neutral
	
	public EDEndpointApiGenerator(Job job, GProtocolName fullname, Role self)
	{
		this.job = job;
		this.proto = fullname;
		this.self = self;
	}

	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) param-core class later
	public Map<String, String> build() throws ScribbleException
	{
		Map<String, String> res = new HashMap<>();  // filepath -> source 
		res.putAll(buildSessionApi());
		return res;
	}

	public Map<String, String> buildSessionApi() throws ScribbleException // FIXME: factor out
	{
		this.job.debugPrintln("\n[param-core] Running " + EDEndpointApiGenerator.class + " for " + this.proto + "@" + this.self);

		Map<String, String> res = new HashMap<>();
		res.putAll(new EDSessionApiBuilder(this.job, this.proto).generateApi());
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

		String pack = SessionApiGenerator.getEndpointApiRootPackageName(this.proto);
		String name = this.proto.getSimpleName() + "_" + this.self;
		String sess = pack + "." + this.proto.getSimpleName();
		String role = SessionApiGenerator.getRolesPackageName(this.proto) + "." + this.self;
		String epClass = "";
		
		epClass += "package " + pack + ".handlers." + this.self + ";\n";
		epClass += "\n";
		epClass += "import java.util.function.Function;\n";
		epClass += "import java.util.HashMap;\n";
		epClass += "import java.util.Map;\n";
		epClass += "\n";
		epClass += "public class " + name + " extends org.scribble.runtime.net.session.EventDrivenEndpoint<" + sess + ", " + role + "> {\n";
		epClass += "private final Map<Object, Function<Object, org.scribble.runtime.net.ScribMessage>> outputs = new HashMap<>();\n";
		epClass += "\n";
		epClass += "public " + name + "(" + sess  + " sess, " + role
				+ " self, org.scribble.runtime.net.ScribMessageFormatter smf) throws java.io.IOException, org.scribble.main.ScribbleRuntimeException {\n";
		epClass += "super(sess, self, smf);\n";
		epClass += "}\n";
		epClass += "\n";
		
		/*epClass += "public enum State {" + states.stream().map(s -> getEDStateEnum(this.proto, this.self, s)) + "}\n";
		epClass += "\n";*/

		epClass += states.stream().map(s -> generateRegister(this.proto, this.self, s)).collect(Collectors.joining("\n"));
		/*epClass += "public void register(State s, Function<Object, org.scribble.runtime.net.ScribMessage> h) {\n";
		epClass += "this.outputs.put(s, h)";
		epClass += "}\n";
		epClass += "\n";
		epClass += "public void register(State s, Function<Object, org.scribble.runtime.net.ScribMessage> h) {\n";
		epClass += "this.outputs.put(s, h)";
		epClass += "}\n";*/
		
		epClass += "}\n";

		for (EState s : states)
		{
			epClass += "\n";
			epClass += "final class " + name + "_" + s.id + " {\n";
			epClass += "public static final " + name + "_" + s.id + " id = new " + name + "_" + s.id + "();\n";
			epClass += "private " + name + "_" + s.id + "() {} \n";
			epClass += "}\n";
		}
		
		Map<String, String> res = new HashMap<>();
		String prefix = SessionApiGenerator.getEndpointApiRootPackageName(this.proto).replace('.', '/') + "/handlers/" + this.self + "/" ;  // StateChannelApiGenerator#generateApi
		res.put(prefix + name + ".java", epClass);
		return res;
	}

  /*// cf. StateChannelApiGenerator#newSocketClassName
	public static String getEDStateEnum(GProtocolName gpn, Role self, EState s)
	{
		return gpn.getSimpleName() + "_" + self + "_" + s.id;
	}*/

	private String generateRegister(GProtocolName gpn, Role self, EState s)
	{
		String res = "";
		switch (s.getStateKind())
		{
			case OUTPUT:
			{
				// FIXME: "untyped"
				res += "public void register(" + gpn.getSimpleName() + "_" + self + "_" + s.id +" sid, Function<Object, org.scribble.runtime.net.ScribMessage> h) {\n";
				res += "this.outputs.put(sid, h);\n";
				res += "}\n";
				break;
			}
			case UNARY_INPUT:
			case POLY_INPUT:
				break;
			case TERMINAL:
				break;
			case ACCEPT:
			case WRAP_SERVER:
				throw new RuntimeException("[scrib] TODO: " + s);
			default:
				throw new RuntimeException("[scrib] Shouldn't get in here: " + s);
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
