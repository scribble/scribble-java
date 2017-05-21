package org.scribble.codegen.statetype.go;

import java.util.Map;

import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.main.Job;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
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
		//return (s.id == this.graph.init.id) ? name : "_" + name;  // For "private" non-initial state channels
		return name;
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
		return new GSTSessionAPIBuilder(this).buildSessionAPI();
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
					+ "func New" + tname + "(ep *_Endpoint_" + api.gpn.getSimpleName() + "_" + api.role + ") *" + tname + " {\n"  // FIXME: factor out
					+ "return &" + tname + " { ep: ep." + api.role + ", state: &net.LinearResource { } }\n"
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
