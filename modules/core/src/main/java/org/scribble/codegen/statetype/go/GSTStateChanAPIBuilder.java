package org.scribble.codegen.statetype.go;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.main.Job;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

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
		if (s.isTerminal())
		{
			 return "_EndState";
		}
		String name = this.gpn.getSimpleName() + "_" + role + "_" + this.counter++;
		return (s.id == this.graph.init.id) ? name : "_" + name;
	}

	@Override
	public String getFilePath(String filename)
	{
		if (filename.startsWith("_"))  // Cannot use "_" prefix, ignored by Go
		{
			filename = "$" + filename.substring(1);
		}
		return this.gpn.toString().replaceAll("\\.", "/") + "/" + filename + ".go";
	}
	
	@Override
	public Map<String, String> buildSessionAPI()  // FIXME: factor out
	{
		Module mod = this.job.getContext().getModule(this.gpn.getPrefix());
		GProtocolName simpname = this.gpn.getSimpleName();
		ProtocolDecl<Global> gpd = mod.getProtocolDecl(simpname);
		/*MessageIdCollector coll = new MessageIdCollector(this.job, ((ModuleDel) mod.del()).getModuleContext());
		try
		{
			gpd.accept(coll);
		}
		catch (ScribbleException e)
		{
			throw new RuntimeScribbleException(e);
		}*/

		List<Role> roles = gpd.header.roledecls.getRoles();
		//Set<MessageId<?>> mids = coll.getNames();
		Set<EState> instates = new HashSet<>();
		Predicate<EState> f = (s) -> s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT;
		if (f.test(this.graph.init))
		{
			instates.add(this.graph.init);
		}
		instates.addAll(MState.getReachableStates(this.graph.init).stream().filter(f).collect(Collectors.toSet()));
	
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
				  	  "type _" + r + " struct { }\n"
				  	+ "\n"
				  	+ "func (*_" + r +") GetRoleName() string {\n"
				  	+ "return \"" + r + "\"\n"
				  	+ "}\n"
				  	+ "\n"
				  	+ "var __" + r + " *_" + r + "\n"
				  	+ "\n"
				  	+ "func new" + r + "() *_" + r + " {"  // FIXME: not concurrent
				  	+ "if __" + r + " == nil {\n"
				  	+ "__"+ r + " = &_" + r + "{}\n"
				  	+ "}\n"
				  	+ "return __" + r + "\n"
				  	+ "}"
				  ).collect(Collectors.joining("\n\n")) + "\n"
				+ "\n"
				+ "type " + simpname + " struct {\n"
				+ roles.stream().map(r -> r + " *_" + r).collect(Collectors.joining("\n")) + "\n"  // FIXME: role constants should be pointers?
				+ "}\n"
				+ "\n" 
				+ "func New" + simpname + "() *" + simpname + "{\n"
				+ "return &" + simpname + "{ " + roles.stream().map(r -> "new" + r + "()").collect(Collectors.joining(", ")) + " }\n"
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
		}*/

		// labels
		String labels = 
					"package " + getPackage();  // FIXME: factor out
		for (EState s : instates)
		{
			String ename = GSTBranchStateBuilder.getBranchEnumType(this, s);
			List<EAction> as = s.getActions();
			labels +=
				  "\n\ntype " + ename + " int\n"
				+ "\n"
				+ "const (\n"
				+ GSTBranchStateBuilder.getBranchEnumValue(as.get(0).mid) + " " + ename + " = iota \n"
				+ as.subList(1, as.size()).stream().map(a -> GSTBranchStateBuilder.getBranchEnumValue(a.mid)).collect(Collectors.joining("\n")) + "\n"
				+ ")";
		}
		/*String labels = 
				  "package " + getPackage() + "\n";
		/*for (MessageId<?> mid : mids)
		{
			labels +=
					  "\ntype op" + mid + " string\n"
					+ "\n"
					+ "const " + mid + " op" + mid + " = \"" + mid + "\"\n";
		}*/
		res.put(dir + "labels_" + this.role + ".go", labels);

		/*// types
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
				+ "state *net.LinearResource\n"  // FIXME: EndSocket special case?  // FIXME: only seems to work as a pointer (something to do with method calls via value recievers?  is it copying the value before calling the function?)
				+ "}";
		
		if (s.id == api.graph.init.id)
		{
			res +=
					  "\n\n"
					+ "func New" + tname + "(ep *net.MPSTEndpoint) *" + tname + " {\n"  // FIXME: factor out
					+ "return &" + tname + " { ep: ep, state: &net.LinearResource { } }\n"
					+ "}";
		}

		return res;
	}

	@Override
	public String buildAction(STActionBuilder ab, EState curr, EAction a)  // Here because action builder hierarchy not suitable (extended by action kind, not by target language)
	{
		EState succ = curr.getSuccessor(a);
		return
				  "func (s *" + ab.getStateChanType(this, curr, a) + ") " + ab.getActionName(this, a) + "(" 
				+ ab.buildArgs(a)
				+ ") *" + ab.getReturnType(this, curr, succ) + " {\n"
				+ "s.state.Use()\n"
				+ ab.buildBody(this, curr, a, succ) + "\n"
				+ "}";
	}
	
	@Override
	public String getChannelName(STStateChanAPIBuilder api, EAction a)
	{
		return
				//"role" + this.role + "." + a.peer;
				"s.ep.Chans[s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + "]";
	}

	@Override
	public String buildActionReturn(STActionBuilder ab, EState curr, EState succ)  // FIXME: refactor action builders as interfaces and use generic parameter for kind
	{
		String res = "";

		if (succ.isTerminal())
		{
			res += "s.ep.Done = true\n";
		}
		res += "return &" + ab.getReturnType(this, curr, succ) + "{ ep: s.ep";
		if (!succ.isTerminal())
		{
			res += ", state: &net.LinearResource {}";  // FIXME: EndSocket LinearResource special case
		}
		res += " }";

		return res;
	}
}
