package org.scribble.codegen.statetype.go;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.del.ModuleDel;
import org.scribble.main.Job;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.util.MessageIdCollector;

public class GSTStateChanAPIBuilder extends STStateChanAPIBuilder
{
	private int counter = 1;
	
	public GSTStateChanAPIBuilder(Job job, GProtocolName gpn, Role role, EGraph graph)
	{
		super(job, gpn, role, graph,
				new GSTOutputStateBuilder(new GSTSendActionBuilder()),
				new GSTReceiveStateBuilder(new GSTReceiveActionBuilder()),
				new GSTBranchStateBuilder(new GSTBranchActionBuilder()),
				new GSTCaseBuilder(new GSTCaseActionBuilder()),
				new GSTEndStateBuilder());
	}

	@Override
	public String getPackage()
	{
		return this.gpn.getSimpleName().toString();
	}
	
	@Override
	protected String makeSTStateName(EState s)
	{
		return s.isTerminal() ? "EndSocket" : this.gpn.getSimpleName() + "_" + role + "_" + this.counter++;
	}

	@Override
	public String getFilePath(String filename)
	{
		return this.gpn.toString().replaceAll("\\.", "/") + "/" + filename + ".go";
	}
	
	@Override
	public Map<String, String> buildSessionAPI()  // FIXME: factor out
	{
		Module mod = this.job.getContext().getModule(this.gpn.getPrefix());
		GProtocolName simpname = this.gpn.getSimpleName();
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(simpname);
		MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		try
		{
			gpd.accept(coll);
		}
		catch (ScribbleException e)
		{
			throw new RuntimeScribbleException(e);
		}

		List<Role> roles = gpd.header.roledecls.getRoles();
		Set<MessageId<?>> mids = coll.getNames();
	
		Map<String, String> res = new HashMap<>();
		String dir = this.gpn.toString().replaceAll("\\.", "/") + "/";

		/*// endpoints
		String endpoints =
				  "package " + getPackage() + "\n"
				+ roles.stream().map(r -> 
						  "type endpoint" + r + " struct {\n"
						+ roles.stream().filter(rr -> !rr.equals(r)).map(rr -> rr + " chan T").collect(Collectors.joining("\n")) + "\n"
						+ "}\n"
						+ "\n"
						+ "func (ep endpoint" + r + ") Close() error {\n"
						+ roles.stream().filter(rr -> rr.toString().compareTo(r.toString()) > 0).map(rr -> "close(ep" + "." + rr + ")").collect(Collectors.joining("\n")) + "\n"
						+ "return nil\n"
						+ "}\n"
						+ "\n"
						+ "var role" + r + " endpoint" + r
				).collect(Collectors.joining("\n\n"));
		res.put(dir + "endpoints.go", endpoints);*/

		// roles
		String sessclass =
					"package " + getPackage() + "\n"  // FIXME: factor out
				+ "\n"
				+ roles.stream().map(r -> 
				  	  "type " + r + " struct { }\n"
				  	+ "\n"
				  	+ "func (" + r +") IsRole() bool {\n"
				  	+ "return true\n"
				  	+ "}"
				  ).collect(Collectors.joining("\n\n")) + "\n"
				+ "\n"
				+ "type " + simpname + " struct {\n"
				+ roles.stream().map(r -> r + " " + r).collect(Collectors.joining("\n")) + "\n"
				+ "}\n"
				+ "\n" 
				+ "func New" + simpname + "() *" + simpname + "{\n"
				+ "return &" + simpname + "{ " + roles.stream().map(r -> r + ": " + r + "{}").collect(Collectors.joining(", ")) + " }\n"
				+ "}";
		res.put(dir + simpname + ".go", sessclass);
		/*for (Role r : roles)
		{
			String init = this.gpn.getSimpleName() + "_" + r + "_" + 1;  // FIXME: factor out naming scheme
			String role =
					  "package " + getPackage() + "\n"
					+ "\n"
					+ "import \"io\"\n"
					+ "\n"
					+ "func New" + r + "("
					+ roles.stream().filter(rr -> !rr.equals(r)).map(rr -> rr + " chan T").collect(Collectors.joining(", "))
					+ ") (" + init + ", io.Closer) {\n"
					+ "role" + r + " = endpoint" + r + "{\n"
					+ roles.stream().filter(rr -> !rr.equals(r)).map(rr -> rr + ": " + rr).collect(Collectors.joining(",\n")) + ",\n"
					+ "}\n"
					+ "return " + init + "{}, role" + r + "\n"
					+ "}";
			res.put(dir + "role_" + r + ".go", role);
		}

		// labels
		String labels = 
				  "package " + getPackage() + "\n";
		/*for (MessageId<?> mid : mids)
		{
			labels +=
					  "\ntype op" + mid + " string\n"
					+ "\n"
					+ "const " + mid + " op" + mid + " = \"" + mid + "\"\n";
		}* /
		res.put(this.gpn.toString().replaceAll("\\.", "/") + "/labels.go", labels);

		// types
		String types =
				  "package " + getPackage() + "\n"
				+ "\n"
				+ "type T interface {}";
		res.put(dir + "types.go", types);*/
		
		return res;
	}

	protected static String getPackageDecl(STStateChanAPIBuilder api)
	{
		return "package " + api.getPackage();
	}

	protected static String getStateChanPremable(STStateChanAPIBuilder api, EState s)
	{
		String tname = api.getStateChanName(s);
		String res =
				  GSTStateChanAPIBuilder.getPackageDecl(api) + "\n"
				+ "\n"
				+ "import \"org/scribble/runtime/net\"\n"
				+ "\n"
				+ "type " + tname + " struct{\n"
				+ "ep *net.MPSTEndpoint\n"  // FIXME: factor out
				+ "}";
		
		if (s.id == api.graph.init.id)
		{
			res +=
					  "\n\n"
					+ "func New" + tname + "(ep *net.MPSTEndpoint) *" + tname + " {\n"  // FIXME: factor out
					+ "return &" + tname + " { ep: ep }\n"
					+ "}";
		}

		return res;
	}

	@Override
	public String buildAction(STActionBuilder ab, EState curr, EAction a)  // Here because action builder hierarchy not suitable (extended by action kind, not by target language)
	{
		EState succ = curr.getSuccessor(a);
		return
				  "func (s " + ab.getStateChanType(this, curr, a) + ") " + ab.getSTActionName(this, a) + "(" 
				+ ab.buildArgs(a)
				+ ") " + ab.getReturnType(curr, this, succ) + " {"
				+ "\n" + ab.buildBody(this, curr, a, succ)
				+ "\n}";
	}
	
	@Override
	public String getChannelName(STStateChanAPIBuilder api, EAction a)
	{
		return
				//"role" + this.role + "." + a.peer;
				"s.ep.Chans[s.ep.Proto.(" + api.gpn.getSimpleName() + ")." + a.peer + "]";
	}
}
