package org.scribble.ext.go.core.codegen.statetype3;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.ast.RPCoreDelegDecl;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossSend;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageSigName;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTStateChanAPIBuilder
public class RPCoreSTStateChanApiBuilder extends STStateChanApiBuilder
{
	protected final RPCoreSTApiGenerator apigen;
	public final RPRoleVariant variant;  // variant.getName().equals(this.role)
	
	private int counter = 2;  // 1 named as Init
	private final Set<DataTypeDecl> dtds; // FIXME: use "main.getDataTypeDecl((DataType) pt);" instead -- cf. OutputSocketGenerator#addSendOpParams
	private final Set<MessageSigNameDecl> msnds;
	
	private Map<Integer, String> imedNames = new HashMap<>();  // Cf. STStateChanApiBuilder.names
	
	private final Set<RPForeachVar> fvars = new HashSet<>();  // HACK FIXME: should make explicit RPIndexExprNode ast and name disamb endpoint vs. foreach vars
	private final List<EGraph> todo = new LinkedList<>();  // HACK FIXME: to preserve "order" of state building -- cf. fvars hack for var scope

	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) rp-core class later
	// Pre: variant.getName().equals(this.role)
	public RPCoreSTStateChanApiBuilder(RPCoreSTApiGenerator apigen, RPRoleVariant variant, EGraph graph)
	{
		this(apigen, variant, graph, Collections.emptySet());
	}

	private RPCoreSTStateChanApiBuilder(RPCoreSTApiGenerator apigen, RPRoleVariant variant, EGraph graph,
			Set<RPForeachVar> fvars)  // HACK FIXME
	{
		super(apigen.job, apigen.proto, apigen.self, graph,
				new RPCoreSTOutputStateBuilder(new RPCoreSTSplitActionBuilder(), new RPCoreSTSendActionBuilder()),
				new RPCoreSTReceiveStateBuilder(new RPCoreSTReduceActionBuilder(), new RPCoreSTReceiveActionBuilder()),

				// Select-based branch, or type switch branch by default
				(apigen.job.selectApi)
						? new RPCoreSTSelectStateBuilder(new RPCoreSTSelectActionBuilder())
						: new RPCoreSTBranchStateBuilder(new RPCoreSTBranchActionBuilder()),
				(apigen.job.selectApi)
						? null
						: new RPCoreSTCaseBuilder(new RPCoreSTCaseActionBuilder()),

				new RPCoreSTEndStateBuilder());

		this.apigen = apigen;
		this.variant = variant;
		
		Module mod = apigen.job.getContext().getModule(this.apigen.proto.getPrefix());
		this.dtds = mod.getNonProtocolDecls().stream()
				.filter(d -> (d instanceof DataTypeDecl)).map(d -> ((DataTypeDecl) d)).collect(Collectors.toSet());
		this.msnds = mod.getNonProtocolDecls().stream()
				.filter(d -> (d instanceof MessageSigNameDecl)).map(d -> ((MessageSigNameDecl) d)).collect(Collectors.toSet());
		
		this.fvars.addAll(fvars);
	}
	
	@Override
	public Map<String, String> build()  // filepath -> source
	{
		Map<String, String> res = new HashMap<>();
		Set<EState> states = new LinkedHashSet<>();
		states.add(this.graph.init);
		states.addAll(MState.getReachableStates(this.graph.init));
		boolean hasTerm = false;
		for (EState s : states)
		{
			switch (RPCoreSTStateChanApiBuilder.getStateKind(s))
			{
				case CROSS_SEND: res.put(getFilePath(getStateChanName(s)), this.ob.build(this, s)); break;
				case CROSS_RECEIVE: 
				{
					if (s.getActions().size() > 1)
					{
						if (((GoJob) this.job).selectApi)  // Select-based branch
						{
							res.put(getFilePath(getStateChanName(s)), this.bb.build(this, s));
						}
						else // Type switch -based branch
						{
							res.put(getFilePath(getStateChanName(s)), this.bb.build(this, s));
							res.put(getFilePath(this.cb.getCaseStateChanName(this, s)), this.cb.build(this, s));
						}
					}
					else
					{
						res.put(getFilePath(getStateChanName(s)), this.rb.build(this, s));
					}
					break;
				}
				/*case DOT_SEND:  // FIXME: CFSMs should have only !^1, ! and ?
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
				}*/
				case TERMINAL: 
					String name = getStateChanName(s);  // HACK FIXME: to generate names even if state not generated here
					if (s.id != this.graph.init.id)  // "End" not built for for single (nested) state FSMs (will be "Init")
					{
						res.put(getFilePath(name), this.eb.build(this, s));
						hasTerm = false;
					}
					break;
				default: throw new RuntimeException("[rp-core] Shouldn't get in here: " + s);
			}
			RPCoreEState s1 = (RPCoreEState) s;
			if (s1.hasNested())
			{
				res.putAll(buildForeachIntermediaryState(s1));
			}
		}
		if (!hasTerm)
		{
			// Always make and "End" (including non-terminating FSMs) -- doesn't matter it's not the actual end state
			//RPCoreEState end = ((RPCoreEModelFactory) this.job.ef).newEState(Collections.emptySet());

			// FIXME: factor out with getStateChanPremable
			GProtocolName simpname = this.apigen.proto.getSimpleName();
			String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, this.variant); 
			String end = "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(this.variant) + "\n"
					+ "\n"
					+ "type End struct {\n"
					//+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
					+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epkindTypeName + "\n" 
					+ "}\n";
			res.put(getFilePath("End"), end);
		}
		
		// FIXME HACK
		for (EGraph g : this.todo)
		{
			res.putAll(new RPCoreSTStateChanApiBuilder(this.apigen, this.variant, g, this.fvars).build());
		}
		
		return res;
	}

	@Override
	public String getFilePath(String filename)
	{
		if (filename.startsWith("_"))  // Cannot use "_" prefix, ignored by Go
		{
			filename = "$" + filename.substring(1);
		}
		return this.gpn.toString().replaceAll("\\.", "/") 
				+ "/" + RPCoreSTApiGenerator.getEndpointKindPackageName(this.variant)  // State chans located with Endpoint Kind API
				+ "/" + filename + ".go";
	}
	
	// Pre: s.hasNested() -- i.e., s is the "outer" state
	// FIXME: factor out with getStateChanPremable
	protected Map<String, String> buildForeachIntermediaryState(RPCoreEState s)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		String scTypeName = this.getIntermediaryStateChanName(s);
		String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, this.variant); 
		
		Map<String, String> res = new HashMap<>();
		
		String feach =
				  "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(this.variant) + "\n"
				+ "\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n";

				// State channel type
		feach += "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epkindTypeName + "\n" 
				+ "}\n";
		
		// Foreach method -- cf. RPCoreSTSessionApiBuilder, top-level Run
		String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
		RPCoreEState init = s.getNested();
		String initName = "Init_" + init.id;
		String succName = s.isTerminal() ? "End" : getStateChanName(s);  // FIXME: factor out
		RPForeachVar p = s.getParam();
		
		this.fvars.add(p); // HACK FIXME: state visiting order not guaranteed (w.r.t. lexical var scope)
		
		feach += "\n"
				+ "func (" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + " *" + scTypeName
						+ ") Foreach(f func(*" + initName + ") End) *End {\n"
						+ "for " + p + " := " + generateIndexExpr(s.getInterval().start) + "; "  // FIXME: general interval expressions
								+ p + " <= " + generateIndexExpr(s.getInterval().end) + "; " + p + " = " + p + " + 1 {\n"
						//+ sEp + "." + s.getParam() + "=" + s.getParam() + "\n"  // FIXME: nested Endpoint type/struct?
						+ sEp + ".Params[\"" + p + "\"] = " + p + "\n"  // FIXME: nested Endpoint type/struct?

				// Duplicated from RPCoreSTSessionApiBuilder  // FIXME: factor out
				+ ((this.apigen.job.selectApi && init.getStateKind() == EStateKind.POLY_INPUT)
						? "f(newBranch" + initName + "(ini))\n"
						: "f(&" + initName + "{ new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ")," + sEp + " })\n")  // cf. state chan builder  // FIXME: chan struct reuse

				+ "}\n"
				+ "return " + getSuccStateChan(s, succName, sEp) + "\n"
				+ "}\n";

		res.put(getFilePath(scTypeName), feach);
		
		RPCoreEState term = (RPCoreEState) MState.getTerminal(init);
		//res.putAll(new RPCoreSTStateChanApiBuilder(this.apigen, this.variant, new EGraph(init, term)).build());
		this.todo.add(new EGraph(init, term));

		return res;
	}
	
	// Factored out here from state-specific builders
	protected String getStateChanPremable(EState s)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		String scTypeName = this.getStateChanName(s);
		String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, this.variant); 
		
		String res =
				  "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(this.variant) + "\n"
				+ "\n"
				+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n"

				// FIXME: error handling via Err field -- fallback should be panic
				+ "import \"log\"\n";
				
		// Not needed by select-branch or Case objects  // FIXME: refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.OUTPUT || s.getStateKind() == EStateKind.UNARY_INPUT
				|| (s.getStateKind() == EStateKind.POLY_INPUT && !this.apigen.job.selectApi))
		{
			res += makeMessageImports(s);
		}

		// FIXME: still needed? -- refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)
		{
			res += "import \"sort\"\n";
			res += "import \"reflect\"\n";
			res += "\n";
			res += "var _ = sort.Sort\n";
			res += "var _ = reflect.TypeOf\n";
		}

				// State channel type
		res += "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epkindTypeName + "\n" 
				+ "}\n";

		return res;
	}

	public String makeMessageImports(EState s)
	{
		String res = "";
		for (String extSource : (Iterable<String>)
				s.getAllActions().stream()
				 .filter(a -> a.mid.isOp())
				 .flatMap(a -> a.payload.elems.stream()
						.filter(p -> !getExtName((DataType) p).matches("(\\[\\])*(int|string|byte)")))
				 .map(p -> getExtSource((DataType) p))
				 .distinct()::iterator)
		{
			res += "import \"" + extSource + "\"\n";
		}
		for (String extSource : (Iterable<String>)
				s.getAllActions().stream().filter(a -> a.mid.isMessageSigName())
				 .map(a -> getExtSource((MessageSigName) a.mid)).distinct()::iterator)
		{
			res += "import \"" + extSource + "\"\n";
		}
		return res;
	}

	// "Base case" -- more specific versions should be overriden in action builders
  // Here because action builder hierarchy not suitable (extended by action kind, not by target language)
	@Override
	public String buildAction(STActionBuilder ab, EState curr, EAction a)
	{
		EState succ = curr.getSuccessor(a);
		return
					"func (" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER
						+ " *" + ab.getStateChanType(this, curr, a) + ") " + ab.getActionName(this, a) + "(" 
						+ ab.buildArgs(this, a)
						+ ") *" + ab.getReturnType(this, curr, succ) + " {\n"
				+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
						+ "." + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_USE + "()\n"
				+ ab.buildBody(this, curr, a, succ) + "\n"
				+ "}";
	}

	// "Base case" -- more specific versions should be overriden in action builders
  // FIXME: refactor action builders as interfaces and use generic parameter for kind
	@Override
	public String buildActionReturn(STActionBuilder ab, EState curr, EState succ)
	{
		String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
		String res = "";
		res += "return " + getSuccStateChan(ab, curr, succ, sEp);
		return res;
	}

	protected String getSuccStateChan(STActionBuilder ab, EState curr, EState succ, String sEp)
	{
		String name = ((RPCoreEState) succ).hasNested()
				? getIntermediaryStateChanName(succ)
				: getStateChanName(succ);  //ab.getReturnType(this, curr, succ)
		return getSuccStateChan(succ, name, sEp);
	}
		
	protected String getSuccStateChan(EState succ, String name, String sEp)
	{
		if (((GoJob) this.job).selectApi &&
				getStateKind(succ) == RPCoreEStateKind.CROSS_RECEIVE && succ.getActions().size() > 1)
		{
			// Needs to be here (not in action builder) -- build (hacked) return for all state kinds
			// FIXME: factor out with RPCoreSTSessionApiBuilder and RPCoreSTSelectStateBuilder#getPreamble
			return "newBranch" + name + "(" + sEp + ")";
		}
		else
		{
			String res = "&" + name + "{ " + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": " + sEp;
			if (!succ.isTerminal())  // FIXME: terminal foreach state
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
		throw new RuntimeException("[rp-core] Shouldn't get in here: " + a);
		//return "s.ep.GetChan(s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + ")";
	}
	
	@Override
	public String getStateChanName(EState s)
	{
		String name = this.imedNames.get(s.id);
		if (name == null)
		{
			RPCoreEState s1 = (RPCoreEState) s;
			name = makeSTStateName(s1);
			if (s1.hasNested())
			{
				this.imedNames.put(s1.id, name);  // The "original" name, now used for foreach -- doing this way round for convenience of original state channel naming
				name = name + "_";  // FIXME
			}
			this.names.put(s.id, name);
		}
		return name;
	}

	public String getIntermediaryStateChanName(EState s)
	{
		if (!this.names.containsKey(s.id))
		{
			throw new RuntimeException("Shouldn't get in here: " + s.id);
		}
		return this.imedNames.get(s.id);
	}
	
	@Override
	protected String makeSTStateName(EState s)
	{
		return s.id == this.graph.init.id  // Includes single (nested) state endpoint kinds (i.e, Init, not End)
				? "Init_" + s.id  // FIXME: factor out (makeInitStateName)
				: (s.isTerminal()
						? makeEndStateName(this.apigen.proto.getSimpleName(), this.variant)
						: //this.apigen.proto.getSimpleName() + "_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(this.actual) + "_"
						  "State"
								+ this.counter++);
	}
	
	public static String makeEndStateName(GProtocolName simpname, RPRoleVariant r)
	{
		return //simpname + "_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(r) + "_" + 
				RPCoreSTApiGenConstants.GO_SCHAN_END_TYPE;
	}
	
	// Not actual variants -- rather, indexed roles in EFSM actions -- cf. ParamCoreSTEndpointApiGenerator.getGeneratedRoleVariantName
	public static String getGeneratedIndexedRoleName(RPIndexedRole r) 
	{
		//return r.toString().replaceAll("\\[", "_").replaceAll("\\]", "_").replaceAll("\\.", "_");
		if (r.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + r);
		}
		RPInterval g = r.intervals.iterator().next();
		return r.getName() + "_" + g.start + "to" + g.end;
	}
	
	public String generateIndexExpr(RPIndexExpr e)
	{
		if (e instanceof RPIndexInt)
		{
			return e.toString();
		}
		else if (e instanceof RPIndexVar)
		{
			String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
			if (e instanceof RPForeachVar
					|| this.fvars.stream().anyMatch(p -> p.toString().equals(e.toString())))  // FIXME HACK
			{
				return sEp + ".Params[\"" + e + "\"]";
			}
			else
			{
				return sEp
						//+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_PARAMS + "[\"" + e + "\"]";
						+ "." + e;
			}
		}
		else
		{
			throw new RuntimeException("[rp-core] TODO: " + e);
		}
	}

	/*public String makeExtNameImport(DataType t)
	{
		String extName = getExtName(t);
		return extName.matches("(\\[\\])*(int|string|byte)")
				? ""
				: "import \"" + getExtSource(t) + "\"\n";
	}*/

	
	

	protected boolean isDelegType(DataType t)
	{
		return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get() instanceof RPCoreDelegDecl;  // FIXME: make a map
	}

	/*protected String getExtName(AbstractName<?> n)
	{
		if (n instanceof DataType)
		{
			DataType t = (DataType) n;
			return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get().extName;
		}
		else if (n instanceof MessageSigName)
		{
			MessageSigName msn = (MessageSigName) n;
			return this.msnds.stream().filter(x -> x.getDeclName().equals(msn)).findAny().get().extName;
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + n);
		}
	}

	protected String getExtSource(AbstractName<?> n)
	{
		if (n instanceof DataType)
		{
			DataType t = (DataType) n;
			return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get().extSource;  // FIXME: make a map
		}
		else if (n instanceof MessageSigName)
		{
			MessageSigName msn = (MessageSigName) n;
			return this.msnds.stream().filter(x -> x.getDeclName().equals(msn)).findAny().get().extSource; 
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + n);
		}
	}*/
	
	protected String getExtName(DataType t)
	{
		return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get().extName;
	}
	
	protected String getExtSource(DataType t)
	{
		return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get().extSource;  // FIXME: make a map
	}

	protected String getExtName(MessageSigName n)
	{
		return this.msnds.stream().filter(x -> x.getDeclName().equals(n)).findAny().get().extName;
	}

	protected String getExtSource(MessageSigName n)
	{
		return this.msnds.stream().filter(x -> x.getDeclName().equals(n)).findAny().get().extSource; 
	}
	
	
	
	
	// FIXME: make a ParamCoreEState
	protected enum RPCoreEStateKind { CROSS_SEND, CROSS_RECEIVE, DOT_SEND, DOT_RECEIVE, MULTICHOICES_RECEIVE, TERMINAL }
	
	protected static RPCoreEStateKind getStateKind(EState s)
	{
		List<EAction> as = s.getActions();
		if (as.isEmpty())
		{
			return RPCoreEStateKind.TERMINAL;	
		}
		else if (as.stream().allMatch(a -> a instanceof RPCoreECrossSend))
		{
			return RPCoreEStateKind.CROSS_SEND;
		}
		else if (as.stream().allMatch(a -> a instanceof RPCoreECrossReceive))
		{
			return RPCoreEStateKind.CROSS_RECEIVE;
		}
		/*else if (as.stream().allMatch(a -> a instanceof RPCoreEDotSend))  // FIXME: CFSMs should have only !^1, ! and ?
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
		}*/
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + s);
		}
	}

	
	
	
	
	
	
	
	
	
	
	/*@Override
	public String getPackage()
	{
		//throw new RuntimeException("[rp-core] TODO:");
		return this.gpn.getSimpleName().toString();
	}*/
	
	/*public String getActualRoleName()
	{
		return this.apigen.self.toString();
	}*/
	
	/*protected RPRoleVariant getSelf()
	{
		return (RPRoleVariant) this.getSelf();
	}*/
}

