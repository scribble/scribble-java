package org.scribble.ext.go.core.codegen.statetype;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreECrossSend;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEDotReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEDotSend;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEMultiChoicesReceive;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.main.GoJob;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTStateChanAPIBuilder
public class ParamCoreSTStateChanApiBuilder extends STStateChanApiBuilder
{
	protected final ParamCoreSTEndpointApiGenerator apigen;
	public final ParamActualRole actual;  // this.apigen.self.equals(this.actual.getName())
	//public final EGraph graph;
	
	private int counter = 1;
	
	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) param-core class later
	// actual.getName().equals(this.role)
	public ParamCoreSTStateChanApiBuilder(ParamCoreSTEndpointApiGenerator apigen, ParamActualRole actual, EGraph graph)
	{
		super(apigen.job, apigen.proto, apigen.self, graph,
				new ParamCoreSTOutputStateBuilder(new ParamCoreSTSendActionBuilder()),
				new ParamCoreSTReceiveStateBuilder(new ParamCoreSTReceiveActionBuilder()),
				new ParamCoreSTBranchStateBuilder(new ParamCoreSTBranchActionBuilder()),
				null, //new GoSTCaseBuilder(new GoSTCaseActionBuilder()),
				new ParamCoreSTEndStateBuilder());

		//throw new RuntimeException("[param-core] TODO:");
		this.apigen = apigen;
		this.actual = actual;
	}
	
	protected ParamActualRole getSelf()
	{
		return (ParamActualRole) this.getSelf();
	}
	
	// Not actual roles; param roles in EFSM actions -- cf. ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName
	public static String getGeneratedParamRoleName(ParamRole r) 
	{
		//return r.toString().replaceAll("\\[", "_").replaceAll("\\]", "_").replaceAll("\\.", "_");
		if (r.ranges.size() > 1)
		{
			throw new RuntimeException("[param-core] TODO: " + r);
		}
		ParamRange g = r.ranges.iterator().next();
		return r.getName() + "_" + g.start + "To" + g.end;
	}
	

	/*@Override
	public String getPackage()
	{
		//throw new RuntimeException("[param-core] TODO:");
		return this.gpn.getSimpleName().toString();
	}*/
	
	@Override
	protected String makeSTStateName(EState s)
	{
		//throw new RuntimeException("[param-core] TODO:");
		if (s.isTerminal())
		{
			////return "_EndState";
			//return ParamCoreSTApiGenConstants.GO_SCHAN_END_TYPE;
			return makeEndStateName(this.apigen.proto.getSimpleName(), this.actual);
		}
		return this.apigen.proto.getSimpleName() + "_"
				+ ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(this.actual)
				+ "_" + this.counter++;
	}
	
	public static String makeEndStateName(GProtocolName simpname, ParamActualRole r)
	{
		return simpname + "_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(r) + "_" + ParamCoreSTApiGenConstants.GO_SCHAN_END_TYPE;
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

	public String getActualRoleName()
	{
		return this.apigen.self.toString();
	}
	
	protected String getStateChanPremable(EState s)
	{
		//throw new RuntimeException("[param-core] TODO: ");
		Role r = this.actual.getName();
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		String tname = this.getStateChanName(s);
		String epType = ParamCoreSTEndpointApiGenerator.getGeneratedEndpointType(simpname, r); 
		String res =
				  this.apigen.generateRootPackageDecl() + "\n"
				+ "\n"
				+ this.apigen.generateScribbleRuntimeImports() + "\n"
				+ (s.isTerminal() || ((GoJob) apigen.job).noCopy ? "" : 
					Stream.of(ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_BYTES_PACKAGE, ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_GOB_PACKAGE)
							.map(x -> "import \"" + x + "\"").collect(Collectors.joining("\n")))
				+ "\n"
				+ "type " + tname + " struct{\n"
				//+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + ParamCoreSTApiGenConstants.GO_ENDPOINT_TYPE + "\n" 
				+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epType + "\n" 
				+ ParamCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + ParamCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ "}\n";
		
		if (s.id == this.graph.init.id)
		{
			res += "\n"
					+ "func (ep *" + epType + ") New" 
							//+ tname
							+ ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(this.actual) + "_1"  // cf. makeSTStateName
							+ "() *" + tname + " {\n"  // FIXME: factor out
					//+ "ep." + this.role + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_STARTPROTOCOL + "()\n"
					+ "return &" + tname + " { " + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": ep"
							+ ", " + ParamCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + ": new(" + ParamCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ")}\n"
					+ "}";
		}

		return res;
	}

	@Override
	public String buildAction(STActionBuilder ab, EState curr, EAction a)  // Here because action builder hierarchy not suitable (extended by action kind, not by target language)
	{
		EState succ = curr.getSuccessor(a);
		if (getStateKind(curr) == ParamCoreEStateKind.CROSS_RECEIVE && curr.getActions().size() > 1)
		{
			return
						"func (" + ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
								+ " *" + ab.getStateChanType(this, curr, a) + ") " + ab.getActionName(this, a) + "(" 
								+ ab.buildArgs(a)
								+ ") <-chan *" + ab.getReturnType(this, curr, succ) + " {\n"
					+ ab.buildBody(this, curr, a, succ) + "\n"
					+ "}";
		}
		else
		{
			//throw new RuntimeException("[param-core] TODO: ");
			return
						"func (" + ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
								+ " *" + ab.getStateChanType(this, curr, a) + ") " + ab.getActionName(this, a) + "(" 
								+ ab.buildArgs(a)
								+ ") *" + ab.getReturnType(this, curr, succ) + " {\n"
					+ ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
							+ "." + ParamCoreSTApiGenConstants.GO_LINEARRESOURCE_USE + "()\n"
					+ ab.buildBody(this, curr, a, succ) + "\n"
					+ "}";
		}
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
		String sEp = 
				//"s.ep"
				ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
		String res = "";

		if (succ.isTerminal())
		{
			res += sEp + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_FINISHPROTOCOL + "()\n";
		}
		res += "return " + getSuccStateChan(ab, curr, succ, sEp);
		return res;
	}

	public String getSuccStateChan(STActionBuilder ab, EState curr, EState succ, String sEp)
	{
		if (getStateKind(succ) == ParamCoreEStateKind.CROSS_RECEIVE && succ.getActions().size() > 1)
		{
			return ParamCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." +
					"New"
						//+ getStateChanName(succ)
						+ ((succ.id != this.graph.init.id) ? getStateChanName(succ)
								: ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(this.actual) + "_1")  // cf. ParamCoreSTStateChanApiBuilder::getStateChanPremable init state case
					+ "()";
		}
		else
		{
			String res = "&" + ab.getReturnType(this, curr, succ) + "{ " + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": " + sEp;
			if (!succ.isTerminal())
			{
				res += ", " + ParamCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
								+ ": new(" + ParamCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ")";  // FIXME: EndSocket LinearResource special case
			}
			res += " }";
			return res;
		}
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
						//api.put(getFilePath(this.cb.getCaseStateChanName(this, s)), this.cb.build(this, s));  // FIXME: factor out
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
