package org.scribble.ext.go.core.codegen.statetype;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreECrossSend;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEDotReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEDotSend;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEMultiChoicesReceive;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.main.Job;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.GProtocolName;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTStateChanAPIBuilder
public class ParamCoreSTStateChanApiBuilder extends STStateChanApiBuilder
{
	private int counter = 1;
	
	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) param-core class later
	public ParamCoreSTStateChanApiBuilder(Job job, GProtocolName gpn, ParamActualRole role, EGraph graph)
	{
		super(job, gpn, role, graph,
				new ParamCoreSTOutputStateBuilder(new ParamCoreSTSendActionBuilder()),
				null, //new GoSTReceiveStateBuilder(new GoSTReceiveActionBuilder()),
				null, //new GoSTBranchStateBuilder(new GoSTBranchActionBuilder()),
				null, //new GoSTCaseBuilder(new GoSTCaseActionBuilder()),
				new ParamCoreSTEndStateBuilder());

		//throw new RuntimeException("[param-core] TODO:");
	}
	
	protected ParamActualRole getSelf()
	{
		return (ParamActualRole) this.getSelf();
	}

	@Override
	public String getPackage()
	{
		//throw new RuntimeException("[param-core] TODO:");
		return this.gpn.getSimpleName().toString();
	}
	
	@Override
	protected String makeSTStateName(EState s)
	{
		//throw new RuntimeException("[param-core] TODO:");
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
		//throw new RuntimeException("[param-core] TODO:");
		if (filename.startsWith("_"))  // Cannot use "_" prefix, ignored by Go
		{
			filename = "$" + filename.substring(1);
		}
		return this.gpn.toString().replaceAll("\\.", "/") + "/" + filename + ".go";
	}
	
	@Override
	public Map<String, String> buildSessionAPI()  // FIXME: factor out
	{
		//throw new RuntimeException("[param-core] TODO:");
		return new ParamCoreSTSessionApiBuilder(this).buildSessionAPI();
	}

	protected static String getPackageDecl(STStateChanApiBuilder api)
	{
		//throw new RuntimeException("[param-core] TODO: ");
		return "package " + api.getPackage();
	}

	protected static String getStateChanPremable(STStateChanApiBuilder api, EState s)
	{
		//throw new RuntimeException("[param-core] TODO: ");
		String tname = api.getStateChanName(s);
		String res =
				  ParamCoreSTStateChanApiBuilder.getPackageDecl(api) + "\n"
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

		return res;
	}

	@Override
	public String buildAction(STActionBuilder ab, EState curr, EAction a)  // Here because action builder hierarchy not suitable (extended by action kind, not by target language)
	{
		//throw new RuntimeException("[param-core] TODO: ");
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
	public String getChannelName(STStateChanApiBuilder api, EAction a)
	{
		//throw new RuntimeException("[param-core] TODO: ");
		return
				//"s.ep.Chans[s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + "]";
				"s.ep.GetChan(s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + ")";
	}

	@Override
	public String buildActionReturn(STActionBuilder ab, EState curr, EState succ)  // FIXME: refactor action builders as interfaces and use generic parameter for kind
	{
		//throw new RuntimeException("[param-core] TODO: ");
		String res = "";

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

		return res;
	}
	
	@Override
	public Map<String, String> build()  // filepath -> source
	{
		Map<String, String> api = new HashMap<>();
		Set<EState> states = new LinkedHashSet<>();
		states.add(this.graph.init);
		states.addAll(MState.getReachableStates(this.graph.init));
		for (EState s : states)
		{
			switch (getStateKind(s))
			{
				case CROSS_SEND:      api.put(getFilePath(getStateChanName(s)), this.ob.build(this, s)); break;
				case CROSS_RECEIVE: 
				{
					if (s.getActions().size() > 1)
					{
						api.put(getFilePath(getStateChanName(s)), this.bb.build(this, s));
						api.put(getFilePath(this.cb.getCaseStateChanName(this, s)), this.cb.build(this, s));  // FIXME: factor out
					}
					else
					{
						api.put(getFilePath(getStateChanName(s)), this.rb.build(this, s));
					}
					break;
				}
				case DOT_SEND:
				{
					throw new RuntimeException("[param-core] TODO: " + s);
				}
				case DOT_RECEIVE:
				{
					throw new RuntimeException("[param-core] TODO: " + s);
				}
				case MULTICHOICES_RECEIVE:
				{
					throw new RuntimeException("[param-core] TODO: " + s);
				}
				case TERMINAL:    api.put(getFilePath(getStateChanName(s)), this.eb.build(this, s)); break;  // FIXME: without subpackages, all roles share same EndSocket
				default:          throw new RuntimeException("[param-core] Shouldn't get in here: " + s);
			}
		}
		return api;
	}
	
	
	// FIXME: make ParamCoreEState
	enum ParamCoreEStateKind { CROSS_SEND, CROSS_RECEIVE, DOT_SEND, DOT_RECEIVE, MULTICHOICES_RECEIVE, TERMINAL }
	
	protected static ParamCoreEStateKind getStateKind(EState s)
	{
		List<EAction> as = s.getActions();
		if (as.isEmpty())
		{
			return ParamCoreEStateKind.TERMINAL;	
		}
		else if (as.stream().allMatch(a -> a instanceof ParamCoreECrossSend))
		{
			return ParamCoreEStateKind.CROSS_SEND;
		}
		else if (as.stream().allMatch(a -> a instanceof ParamCoreECrossReceive))
		{
			return ParamCoreEStateKind.CROSS_RECEIVE;
		}
		else if (as.stream().allMatch(a -> a instanceof ParamCoreEDotSend))
		{
			return ParamCoreEStateKind.DOT_SEND;
		}
		else if (as.stream().allMatch(a -> a instanceof ParamCoreEDotReceive))
		{
			return ParamCoreEStateKind.DOT_RECEIVE;
		}
		else if (as.stream().allMatch(a -> a instanceof ParamCoreEMultiChoicesReceive))
		{
			return ParamCoreEStateKind.MULTICHOICES_RECEIVE;
		}
		else
		{
			throw new RuntimeException("[param-core] Shouldn't get in here: " + s);
		}
	}
}
