package org.scribble.ext.go.core.codegen.statetype3;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.DataTypeDecl;
import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.ast.RPCoreDelegDecl;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossSend;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEDotReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEDotSend;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEMultiChoicesReceive;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.PayloadElemType;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTStateChanAPIBuilder
public class RPCoreSTStateChanApiBuilder extends STStateChanApiBuilder
{
	protected final RPCoreSTApiGenerator apigen;
	public final RPRoleVariant actual;  // this.apigen.self.equals(this.actual.getName())
	//public final EGraph graph;
	
	public final RPCoreSTReceiveActionBuilder vb;
	
	private int counter = 1;
	
	private final Set<DataTypeDecl> datats; // FIXME: use "main.getDataTypeDecl((DataType) pt);" instead -- cf. OutputSocketGenerator#addSendOpParams
	
	// FIXME: make ParamCoreEState
	enum ParamCoreEStateKind { CROSS_SEND, CROSS_RECEIVE, DOT_SEND, DOT_RECEIVE, MULTICHOICES_RECEIVE, TERMINAL }
	
	protected static ParamCoreEStateKind getStateKind(EState s)
	{
		List<EAction> as = s.getActions();
		if (as.isEmpty())
		{
			return ParamCoreEStateKind.TERMINAL;	
		}
		else if (as.stream().allMatch(a -> a instanceof RPCoreECrossSend))
		{
			return ParamCoreEStateKind.CROSS_SEND;
		}
		else if (as.stream().allMatch(a -> a instanceof RPCoreECrossReceive))
		{
			return ParamCoreEStateKind.CROSS_RECEIVE;
		}
		else if (as.stream().allMatch(a -> a instanceof RPCoreEDotSend))
		{
			return ParamCoreEStateKind.DOT_SEND;
		}
		else if (as.stream().allMatch(a -> a instanceof RPCoreEDotReceive))
		{
			return ParamCoreEStateKind.DOT_RECEIVE;
		}
		else if (as.stream().allMatch(a -> a instanceof RPCoreEMultiChoicesReceive))
		{
			return ParamCoreEStateKind.MULTICHOICES_RECEIVE;
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + s);
		}
	}
	
	
	

	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) rp-core class later
	// actual.getName().equals(this.role)
	public RPCoreSTStateChanApiBuilder(RPCoreSTApiGenerator apigen, RPRoleVariant actual, EGraph graph)
	{
		super(apigen.job, apigen.proto, apigen.self, graph,
				new RPCoreSTOutputStateBuilder(new RPCoreSTSplitActionBuilder(), new RPCoreSTSendActionBuilder()),
				new RPCoreSTReceiveStateBuilder(new RPCoreSTReduceActionBuilder(), new RPCoreSTReceiveActionBuilder()),
				new RPCoreSTSelectStateBuilder(new RPCoreSTSelectActionBuilder()),
				null, //new GoSTCaseBuilder(new GoSTCaseActionBuilder()),
				new RPCoreSTEndStateBuilder());

		//throw new RuntimeException("[rp-core] TODO:");
		this.apigen = apigen;
		this.actual = actual;
		
		this.vb = ((RPCoreSTReceiveStateBuilder) this.rb).vb;
		
		this.datats = this.apigen.job.getContext().getMainModule().getNonProtocolDecls().stream()
				.filter(d -> (d instanceof DataTypeDecl)).map(d -> ((DataTypeDecl) d)).collect(Collectors.toSet());
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
					throw new RuntimeException("[rp-core] TODO: " + s);
				}
				case DOT_RECEIVE:
				{
					throw new RuntimeException("[rp-core] TODO: " + s);
				}
				case MULTICHOICES_RECEIVE:
				{
					throw new RuntimeException("[rp-core] TODO: " + s);
				}
				case TERMINAL:    api.put(getFilePath(getStateChanName(s)), this.eb.build(this, s)); break;  // FIXME: without subpackages, all roles share same EndSocket
				default:          throw new RuntimeException("[rp-core] Shouldn't get in here: " + s);
			}
		}
		return api;
	}

	@Override
	public String getFilePath(String filename)
	{
		//throw new RuntimeException("[rp-core] TODO:");
		if (filename.startsWith("_"))  // Cannot use "_" prefix, ignored by Go
		{
			filename = "$" + filename.substring(1);
		}
		return this.gpn.toString().replaceAll("\\.", "/") 
				+ "/" + RPCoreSTApiGenerator.getGeneratedRoleVariantName(this.actual)
				+ "/" + filename + ".go";
	}
	
	protected String getStateChanPremable(EState s)
	{
		//Role r = this.actual.getName();

		GProtocolName simpname = this.apigen.proto.getSimpleName();
		String tname = this.getStateChanName(s);
		////String epType = ParamCoreSTEndpointApiGenerator.getGeneratedEndpointType(simpname, r); 
		String epType = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, this.actual); 
		
		String res =
				  //this.apigen.generateRootPackageDecl() + "\n"
				  "package " + RPCoreSTApiGenerator.getGeneratedRoleVariantName(this.actual) + "\n"
				+ "\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"
				//+ this.apigen.generateScribbleRuntimeImports() + "\n"
				+ "import \"log\"\n"

				/*+ (s.isTerminal() || ((GoJob) apigen.job).noCopy ? "" : 
					Stream.of(ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_BYTES_PACKAGE, ParamCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_GOB_PACKAGE)
							.map(x -> "import \"" + x + "\"").collect(Collectors.joining("\n")))
				+ "\n"*/
				+ ((s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)  // FIXME: refactor into state builders
						//? "import \"github.com/rhu1/scribble-go-runtime/test/util\"\n" : "")
						? "import \"sort\"\n" : "")
				
				+ ((s.getStateKind() == EStateKind.OUTPUT || s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)
						? s.getActions().stream().flatMap(a -> a.payload.elems.stream()).collect(Collectors.toSet()).stream()
								.map(p -> makeImportExtName((DataType) p))
										.collect(Collectors.joining(""))
						: "")

				+ ((s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)
						? "\nvar _ = sort.Sort\n" : "")

				+ "\n"
				+ "type " + tname + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epType + "\n" 
				//+ ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " session.ParamEndpoint" + "\n"   // FIXME: factor out
				+ "}\n";
		
		/*if (s.id == this.graph.init.id)
		{
			res += "\n"
					+ "func New" 
							+ "(ep session.ParamEndpoint) *" + tname + " {\n"  // FIXME: factor out
					+ "return &" + tname + " { " + ParamCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": ep"
							+ ", " + ParamCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + ": new(" + ParamCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ")}\n"
					+ "}";
		}*/

		return res;
	}

	@Override
	public String buildAction(STActionBuilder ab, EState curr, EAction a)  // Here because action builder hierarchy not suitable (extended by action kind, not by target language)
	{
		EState succ = curr.getSuccessor(a);
		if (getStateKind(curr) == ParamCoreEStateKind.CROSS_RECEIVE && curr.getActions().size() > 1)
		{
			return
						"func (" + RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
								+ " *" + ab.getStateChanType(this, curr, a) + ") " + ab.getActionName(this, a) + "(" 
								+ ab.buildArgs(this, a)
								+ ") <-chan *" + ab.getReturnType(this, curr, succ) + " {\n"
					+ ab.buildBody(this, curr, a, succ) + "\n"
					+ "}";
		}
		else
		{
			//throw new RuntimeException("[rp-core] TODO: ");
			return
						"func (" + RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER
								+ " *" + ab.getStateChanType(this, curr, a) + ") " + ab.getActionName(this, a) + "(" 
								+ ab.buildArgs(this, a)
								+ ") *" + ab.getReturnType(this, curr, succ) + " {\n"
					+ RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
							+ "." + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_USE + "()\n"
					+ ab.buildBody(this, curr, a, succ) + "\n"
					+ "}";
		}
	}

	@Override
	public String buildActionReturn(STActionBuilder ab, EState curr, EState succ)  // FIXME: refactor action builders as interfaces and use generic parameter for kind
	{
		//throw new RuntimeException("[rp-core] TODO: ");
		String sEp = 
				//"s.ep"
				RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
		String res = "";

		/*if (succ.isTerminal())  // FIXME
		{
			res += sEp + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_ENDPOINT + "." + ParamCoreSTApiGenConstants.GO_ENDPOINT_FINISHPROTOCOL + "()\n";
		}*/

		res += "return " + getSuccStateChan(ab, curr, succ, sEp);
		return res;
	}

	public String getSuccStateChan(STActionBuilder ab, EState curr, EState succ, String sEp)
	{
		if (getStateKind(succ) == ParamCoreEStateKind.CROSS_RECEIVE && succ.getActions().size() > 1)
		{
			return RPCoreSTApiGenConstants.GO_IO_FUN_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." +
					"New"
						//+ getStateChanName(succ)
						+ ((succ.id != this.graph.init.id) ? getStateChanName(succ)
								//: ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(this.actual) + "_1")  // cf. ParamCoreSTStateChanApiBuilder::getStateChanPremable init state case
								: "Init")  // cf. ParamCoreSTStateChanApiBuilder::getStateChanPremable init state case
					+ "()";
		}
		else
		{
			String res = "&" + ab.getReturnType(this, curr, succ) + "{ " + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": " + sEp;
			if (!succ.isTerminal())
			{
				res += ", " + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
								+ ": new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ")";  // FIXME: EndSocket LinearResource special case
			}
			res += " }";
			return res;
		}
	}
	
	@Override
	public String getChannelName(STStateChanApiBuilder api, EAction a)  // Not used?
	{
		//throw new RuntimeException("[rp-core] TODO: ");
		return
				//"s.ep.Chans[s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + "]";
				"s.ep.GetChan(s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + ")";
	}
	
	protected RPRoleVariant getSelf()
	{
		return (RPRoleVariant) this.getSelf();
	}

	/*public String getActualRoleName()
	{
		return this.apigen.self.toString();
	}*/
	


	

	/*@Override
	public String getPackage()
	{
		//throw new RuntimeException("[rp-core] TODO:");
		return this.gpn.getSimpleName().toString();
	}*/
	
	@Override
	protected String makeSTStateName(EState s)
	{
		return (s.isTerminal())
				? makeEndStateName(this.apigen.proto.getSimpleName(), this.actual)
				: (s.id == this.graph.init.id)
						? "Init"  // FIXME: factor out
						: //this.apigen.proto.getSimpleName() + "_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(this.actual) + "_" + this.counter++;
							"State" + this.counter++;
	}
	
	public static String makeEndStateName(GProtocolName simpname, RPRoleVariant r)
	{
		return //simpname + "_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(r) + "_" + 
				RPCoreSTApiGenConstants.GO_SCHAN_END_TYPE;
	}
	
	// Not actual roles; param roles in EFSM actions -- cf. ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName
	public static String getGeneratedParamRoleName(RPIndexedRole r) 
	{
		//return r.toString().replaceAll("\\[", "_").replaceAll("\\]", "_").replaceAll("\\.", "_");
		if (r.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + r);
		}
		RPInterval g = r.intervals.iterator().next();
		return r.getName() + "_" + g.start + "To" + g.end;
	}
	
	
	

	public boolean isDelegType(DataType t)
	{
		return this.datats.stream().filter(i -> i.getDeclName().equals(t)).iterator().next() instanceof RPCoreDelegDecl;  // FIXME: make a map
	}
	
	protected String getExtName(DataType t)
	{
		return this.datats.stream().filter(i -> i.getDeclName().equals(t)).iterator().next().extName;  // FIXME: make a map
	}

	protected String getExtSource(DataType t)
	{
		return this.datats.stream().filter(i -> i.getDeclName().equals(t)).iterator().next().extSource;  // FIXME: make a map
	}

	private String makeImportExtName(DataType t)
	{
		String extName = getExtName(t);
		switch (extName)
		{
			case "int":	 // FIXME: factor out with batesHack
			case "[]int":	
			case "[][]int":	
			case "string":	
			case "[]string":	
			case "[][]string":	
			case "byte":
			case "[]byte":
			case "[][]byte":
			{
				return "";
			}
			default:
			{
				return "import \"" + getExtSource(t) + "\"\n";
			}
		}
	}
	


	protected String batesHack(PayloadElemType<?> t)
	{
		/*String tmp = t.toString();
		return (tmp.equals("bates")) ? "[]byte" : tmp;*/
		DataType dt = (DataType) t;
		String extName = getExtName(dt);
		
		switch (extName)
		{
			case "int":
			case "[]int":  // FIXME: generalise arbitrary dimension array
			case "[][]int":
			case "string":
			case "[]string":
			case "[][]string":
			case "byte":
			case "[]byte":
			case "[][]byte":
			{
				return extName;
			}
			default:
			{
				return extName;  // FIXME
			}
		}
	}
}

