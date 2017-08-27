package org.scribble.ext.go.core.codegen.statetype;

import java.util.Map;

import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanAPIBuilder;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.main.Job;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.GProtocolName;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTStateChanAPIBuilder
public class ParamCoreSTStateChanAPIBuilder extends STStateChanAPIBuilder
{
	//private int counter = 1;
	
	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) param-core class later
	public ParamCoreSTStateChanAPIBuilder(Job job, GProtocolName gpn, ParamActualRole role, EGraph graph)
	{
		super(job, gpn, role, graph,
				null, //new GoSTOutputStateBuilder(new GoSTSendActionBuilder()),
				null, //new GoSTReceiveStateBuilder(new GoSTReceiveActionBuilder()),
				null, //new GoSTBranchStateBuilder(new GoSTBranchActionBuilder()),
				null, //new GoSTCaseBuilder(new GoSTCaseActionBuilder()),
				null); //new GoSTEndStateBuilder());

		throw new RuntimeException("[param-core] TODO:");
	}
	
	protected ParamActualRole getSelf()
	{
		return (ParamActualRole) this.getSelf();
	}

	@Override
	public String getPackage()
	{
		throw new RuntimeException("[param-core] TODO:");
		//return this.gpn.getSimpleName().toString();
	}
	
	@Override
	protected String makeSTStateName(EState s)
	{
		throw new RuntimeException("[param-core] TODO:");
		/*if (s.isTerminal())
		{
			 return "_EndState";
		}
		String name = this.gpn.getSimpleName() + "_" + role + "_" + this.counter++;
		//return (s.id == this.graph.init.id) ? name : "_" + name;  // For "private" non-initial state channels
		return name;*/
	}

	@Override
	public String getFilePath(String filename)
	{
		throw new RuntimeException("[param-core] TODO:");
		/*if (filename.startsWith("_"))  // Cannot use "_" prefix, ignored by Go
		{
			filename = "$" + filename.substring(1);
		}
		return this.gpn.toString().replaceAll("\\.", "/") + "/" + filename + ".go";*/
	}
	
	@Override
	public Map<String, String> buildSessionAPI()  // FIXME: factor out
	{
		throw new RuntimeException("[param-core] TODO:");
		//return new GoSTSessionAPIBuilder(this).buildSessionAPI();
	}

	protected static String getPackageDecl(STStateChanAPIBuilder api)
	{
		throw new RuntimeException("[param-core] TODO: ");
		//return "package " + api.getPackage();
	}

	protected static String getStateChanPremable(STStateChanAPIBuilder api, EState s)
	{
		throw new RuntimeException("[param-core] TODO: ");
		/*String tname = api.getStateChanName(s);
		String res =
				  ParamSTStateChanAPIBuilder.getPackageDecl(api) + "\n"
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
					+ "func New" + tname + "(ep *_Endpoint" + api.gpn.getSimpleName() + "_" + api.role + ") *" + tname + " {\n"  // FIXME: factor out
					+ "ep." + api.role + ".SetInit()\n"
					+ "return &" + tname + " { ep: ep." + api.role + ", state: &net.LinearResource { } }\n"
					+ "}";
		}

		return res;*/
	}

	@Override
	public String buildAction(STActionBuilder ab, EState curr, EAction a)  // Here because action builder hierarchy not suitable (extended by action kind, not by target language)
	{
		throw new RuntimeException("[param-core] TODO: ");
		/*EState succ = curr.getSuccessor(a);
		return
				  "func (s *" + ab.getStateChanType(this, curr, a) + ") " + ab.getActionName(this, a) + "(" 
				+ ab.buildArgs(a)
				+ ") *" + ab.getReturnType(this, curr, succ) + " {\n"
				+ "s.state.Use()\n"
				+ ab.buildBody(this, curr, a, succ) + "\n"
				+ "}";*/
	}
	
	@Override
	public String getChannelName(STStateChanAPIBuilder api, EAction a)
	{
		throw new RuntimeException("[param-core] TODO: ");
		/*return
				////"s.ep.Chans[s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + "]";
				//"s.ep.GetChan(s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + ")";
				null;*/
	}

	@Override
	public String buildActionReturn(STActionBuilder ab, EState curr, EState succ)  // FIXME: refactor action builders as interfaces and use generic parameter for kind
	{
		throw new RuntimeException("[param-core] TODO: ");
		/*String res = "";

		if (succ.isTerminal())
		{
			res += "s.ep.SetDone()\n";
		}
		res += "return &" + ab.getReturnType(this, curr, succ) + "{ ep: s.ep";
		if (!succ.isTerminal())
		{
			res += ", state: &net.LinearResource {}";  // FIXME: EndSocket LinearResource special case
		}
		res += " }";

		return res;*/
	}
}
