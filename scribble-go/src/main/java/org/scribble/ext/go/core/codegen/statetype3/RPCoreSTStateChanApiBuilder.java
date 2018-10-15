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
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.codegen.statetype.STActionBuilder;
import org.scribble.codegen.statetype.STStateChanApiBuilder;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.global.RPCoreGProtocolDeclTranslator;
import org.scribble.ext.go.core.ast.global.RPCoreGType;
import org.scribble.ext.go.core.codegen.statetype3.RPCoreSTApiGenerator.Mode;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossSend;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.type.name.RPCoreGDelegationType;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPBinIndexExpr;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexInt;
import org.scribble.ext.go.type.index.RPIndexIntPair;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.name.DataType;
import org.scribble.type.name.GDelegationType;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.PayloadElemType;
import org.scribble.util.Pair;

// Duplicated from org.scribble.ext.go.codegen.statetype.go.GoSTStateChanAPIBuilder
public class RPCoreSTStateChanApiBuilder extends STStateChanApiBuilder
{
	protected final RPCoreSTApiGenerator apigen;
	public final Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family;
	public final RPRoleVariant variant;  // variant.getName().equals(this.role)
	
	//private int counter = 2;  // 1 named as Init
	private final Set<DataTypeDecl> dtds; // FIXME: use "main.getDataTypeDecl((DataType) pt);" instead -- cf. OutputSocketGenerator#addSendOpParams
	private final Set<MessageSigNameDecl> msnds;
	
	//private Map<Integer, String> imedNames = new HashMap<>();  // "Original" state names -- FIXME: deprecate  // Cf. STStateChanApiBuilder.names
	
	private final Set<RPForeachVar> fvars = new HashSet<>();  // HACK FIXME: should make explicit RPIndexExprNode ast and name disamb endpoint vs. foreach vars
	private final List<EGraph> todo = new LinkedList<>();  // HACK FIXME: to preserve "order" of state building -- cf. fvars hack for var scope

	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) rp-core class later
	// Pre: variant.getName().equals(this.role)
	public RPCoreSTStateChanApiBuilder(RPCoreSTApiGenerator apigen,
			Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family, RPRoleVariant variant, EGraph graph, Map<Integer, String> names)
	{
		this(apigen, family, variant, graph, //2, 
				names, //Collections.emptyMap(), 
				Collections.emptySet());
	}

	private RPCoreSTStateChanApiBuilder(RPCoreSTApiGenerator apigen,
			Pair<Set<RPRoleVariant>, Set<RPRoleVariant>> family, RPRoleVariant variant, EGraph graph, 
			//int counter, 
			Map<Integer, String> names, 
			//Map<Integer, String> imedNames, 
			Set<RPForeachVar> fvars)  
					// HACK FIXME -- make a "nested builder" -- problem is final this.graph 
					// FIXME: probably easier to to make a "nested" constructor
	{
		super(apigen.job, apigen.proto, //apigen.self, 
				variant.getName(),
				graph,
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
		this.family = family;
		this.variant = variant;
		
		Module mod = apigen.job.getContext().getModule(this.apigen.proto.getPrefix());
		this.dtds = mod.getNonProtocolDecls().stream()
				.filter(d -> (d instanceof DataTypeDecl)).map(d -> ((DataTypeDecl) d)).collect(Collectors.toSet());
		this.msnds = mod.getNonProtocolDecls().stream()
				.filter(d -> (d instanceof MessageSigNameDecl)).map(d -> ((MessageSigNameDecl) d)).collect(Collectors.toSet());
		
		//this.counter = counter;
		this.names.putAll(names);
		//this.imedNames.putAll(imedNames);
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
				case CROSS_SEND: res.put(getStateChannelFilePath(getStateChanName(s)), this.ob.build(this, s)); break;
				case CROSS_RECEIVE: 
				{
					if (s.getActions().size() > 1)
					{
						if (((GoJob) this.job).selectApi)  // Select-based branch
						{
							res.put(getStateChannelFilePath(getStateChanName(s)), this.bb.build(this, s));
						}
						else // Type switch -based branch
						{
							res.put(getStateChannelFilePath(getStateChanName(s)), this.bb.build(this, s));
							res.put(getStateChannelFilePath(this.cb.getCaseStateChanName(this, s)), this.cb.build(this, s));
						}
					}
					else
					{
						res.put(getStateChannelFilePath(getStateChanName(s)), this.rb.build(this, s));
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
						if (!((RPCoreEState) s).hasNested())
						{
							res.put(getStateChannelFilePath(name), this.eb.build(this, s));
							hasTerm = true;
						}
					}
					break;
				default: throw new RuntimeException("[rp-core] Shouldn't get in here: " + s);
			}
			RPCoreEState s1 = (RPCoreEState) s;
			if (s1.hasNested())
			{
				Map<String, String> tmp = buildForeachIntermediaryState(s1);
				res.putAll(tmp);
				if (s.isTerminal())
				{
					hasTerm = true;
				}
			}
		}
		if (!hasTerm) //&& res.keySet().stream().noneMatch(k -> k.endsWith("End.go")))  // FIXME -- Need to distinguish "End" if nested -- only use "End" for non-nested?
		{
			// Always make an "End" (including non-terminating FSMs) -- doesn't matter it's not the actual end state
			//RPCoreEState end = ((RPCoreEModelFactory) this.job.ef).newEState(Collections.emptySet());

			// FIXME: factor out with getStateChanPremable
			GProtocolName simpname = this.apigen.proto.getSimpleName();
			String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, this.variant); 
			String end = "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(this.variant) + "\n"
					+ "\n"
					+ "type End struct {\n"
						+ RPCoreSTApiGenConstants.GO_SCHAN_ERROR + " error\n"
					//+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
					+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epkindTypeName + "\n" 

					+ (this.apigen.job.parForeach ? "Thread int\n" : "")

					+ "}\n";
			res.put(getStateChannelFilePath("End"), end);
		}
		
		// FIXME HACK
		for (EGraph g : this.todo)
		{
			RPCoreSTStateChanApiBuilder nested = new RPCoreSTStateChanApiBuilder(
					this.apigen, this.family, this.variant, g,
					//this.counter, 
					this.names, //this.imedNames, 
					this.fvars);
			res.putAll(nested.build());
		}
		
		return res;
	}

	// Returns path to use as offset to -d
	// -- cf. packpath, "absolute" Go import path (github.com/...) -- would coincide if protocol full name (i.e., module) used "github.com/..."
	@Override
	public String getStateChannelFilePath(String filename)
	{
		if (filename.startsWith("_"))  // Cannot use "_" prefix, ignored by Go
		{
			filename = "$" + filename.substring(1);
		}
		boolean isCommonEndpointKind = //this.apigen.families.keySet().stream().allMatch(f -> f.left.contains(this.variant));  // No: doesn't consider dial/accept
				//this.apigen.families.keySet().stream().filter(f -> f.left.contains(this.variant)).distinct().count() == 1;  // No: too conservative -- should be about required peer endpoint kinds (to dial/accept with)
				//this.apigen.peers.get(this.variant).size() == 1;
				this.apigen.isCommonEndpointKind(variant);
		return //getStateChannelPackagePathPrefix() 
					// Duplicated from RPCoreSTSessionApiBuilder#getEndpointKindFilePath
					this.gpn.toString().replaceAll("\\.", "/") 
					//+ "/" + this.apigen.getFamilyPackageName(family)
				+ (isCommonEndpointKind ? "" : "/" + this.apigen.getFamilyPackageName(this.family))
				+ "/" + RPCoreSTApiGenerator.getEndpointKindPackageName(this.variant)  // State chans located with Endpoint Kind API
				+ "/" + filename + ".go";
	}
	
	// Pre: s.hasNested() -- i.e., s is the "outer" state
	// TODO: factor out with getStateChanPremable
	protected Map<String, String> buildForeachIntermediaryState(RPCoreEState s)
	{
		GProtocolName simpname = this.apigen.proto.getSimpleName();
		String scTypeName = this.names.get(s.id);  //this.getStateChanName(s);  //this.getIntermediaryStateChanName(s);
		String epkindTypeName = RPCoreSTApiGenerator.getEndpointKindTypeName(simpname, this.variant); 
		
		Map<String, String> res = new HashMap<>();
		
		String feach =
				  "package " + RPCoreSTApiGenerator.getEndpointKindPackageName(this.variant) + "\n"
				+ "\n";
	
		if (this.apigen.mode == Mode.IntPair)
		{
			feach += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE + "\"\n";
		}

		// State channel type
		feach += "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ERROR + " error\n"

				//+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ "id uint64\n"

				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epkindTypeName + "\n";
		
		if (this.apigen.job.parForeach)
		{
			feach += "Thread int\n";
		}

		feach += "}\n";
		
		// Foreach method -- cf. RPCoreSTSessionApiBuilder, top-level Run
		String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
		RPCoreEState init = s.getNested();
		RPCoreEState term = (RPCoreEState) MState.getTerminal(init);
		String initName = this.names.get(init.id); //"Init_" + init.id;
		String termName = (term == null) ? "End" : this.names.get(term.id);
		String succName = s.isTerminal() ? "End" : getStateChanName(s);  // FIXME: factor out
			//getIntermediaryStateChanName(s);  // Functionality subsumed by getStateChanName
		RPForeachVar p = s.getParam();

		this.fvars.add(p); // HACK FIXME: state visiting order not guaranteed (w.r.t. lexical var scope)

		// TODO: factor out with send, receive, etc.
		String lte;
		String inc;
		switch (this.apigen.mode)
		{
			case Int:  
			{
				lte = " <= " + generateIndexExpr(s.getInterval().end);  
				inc = p + "+1";
				break;
			}
			case IntPair:  
			{
				lte = ".Lte(" + generateIndexExpr(s.getInterval().end) + ")";  
				inc = p + ".Inc(" + generateIndexExpr(s.getInterval().end) + ")";
				break;
			}
			default:  throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
		}

    String initState = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "._" + initName;
		feach += "\n"
				+ "func (" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + " *" + scTypeName
						+ ") Foreach(f func(*" + initName + ") " + termName + ") *" + succName + " {\n";
						
		// Duplicated from buildAction
		feach +=
				  "if " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ERROR + " != nil {\n"
				+ "panic(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ERROR + ")\n"
				+ "}\n"

				+ "if " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + "id != "  // Not using atomic.LoadUint64 on id for now
										//+ "atomic.LoadUint64(&" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + "lin)"
										+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + "lin"
										+ " {\n"
								+ "panic(\"Linear resource already used\")\n" // + reflect.TypeOf(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "))\n"
								+ "}\n";
						
		feach +=
						  "for " + p + " := " + generateIndexExpr(s.getInterval().start) + "; "  // FIXME: general interval expressions
								+ p + lte + "; " + p + " = " + inc + "{\n";
						//+ sEp + "." + s.getParam() + "=" + s.getParam() + "\n"  // FIXME: nested Endpoint type/struct?
		
		if (this.apigen.job.parForeach)
		{
		  feach += sEp + ".Params[" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + ".Thread][\"" + p + "\"] = " + p + "\n";
		}
		else
		{
			feach += sEp + ".Params[\"" + p + "\"] = " + p + "\n";  // FIXME: nested Endpoint type/struct?
		}

		feach +=
				// Duplicated from RPCoreSTSessionApiBuilder  // FIXME: factor out with makeReturnSuccStateChan
				/*+ ((this.apigen.job.selectApi && init.getStateKind() == EStateKind.POLY_INPUT)
						? "f(newBranch" + initName + "(ini))\n"
						: "f(&" + initName + "{ nil, new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + "), " + sEp + " })\n")  // cf. state chan builder  // FIXME: chan struct reuse*/
					  RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".lin = "  // FIXME: sync
					+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".lin + 1\n"
					+ initState +".id = " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".lin\n"
					+ "f(" + initState + ")\n"

				+ "}\n"
				//+ "return " + makeCreateSuccStateChan(s, succName) + "\n"
				+ makeReturnSuccStateChan(s, succName) + "\n"
				+ "}\n";
		
		// Parallel method
		//feach += "\n";
		

		res.put(getStateChannelFilePath(scTypeName), feach);
		
		//RPCoreEState term = (RPCoreEState) MState.getTerminal(init);
		//res.putAll(new RPCoreSTStateChanApiBuilder(this.apigen, this.variant, new EGraph(init, term)).build());
		if (term != null)
		{
			this.todo.add(new EGraph(init, term));
		}

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
				+ "\n";
				//+ "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n";

				// FIXME: error handling via Err field -- fallback should be panic
				//+ "import \"log\"\n";
				
		// Not needed by select-branch or Case objects  // FIXME: refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.OUTPUT || s.getStateKind() == EStateKind.UNARY_INPUT
				|| (s.getStateKind() == EStateKind.POLY_INPUT && !this.apigen.job.selectApi))
		{
			res += makeMessageImports(s);
		}

		// FIXME: still needed? -- refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.UNARY_INPUT || s.getStateKind() == EStateKind.POLY_INPUT)
		{
			switch (this.apigen.mode)
			{
				case Int:  res += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_SESSION_PACKAGE + "\"\n";  break;
				case IntPair:  res += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE + "\"\n";  break;
				default:  throw new RuntimeException("Shouldn't get in here:" + this.apigen.mode);
			}

			res += "import \"sync/atomic\"\n";
			res += "import \"reflect\"\n";
			res += "import \"sort\"\n";
			res += "\n";
			
			res += "var _ = session2.NewMPChan\n";

			res += "var _ = atomic.AddUint64\n";
			res += "var _ = reflect.TypeOf\n";
			res += "var _ = sort.Sort\n";
		}
		else if (s.getStateKind() == EStateKind.OUTPUT)
		{
			if (this.apigen.mode == Mode.IntPair)
			{
				res += "import \"" + RPCoreSTApiGenConstants.GO_SCRIBBLERUNTIME_PAIR_SESSION_PACKAGE + "\"\n";
				res += "\n";
				res += "var _ = session2.XY\n";  // For nested states that only use foreach vars (so no session2.XY)
			}
		}

				// State channel type
		res += "\n"
				+ "type " + scTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.GO_SCHAN_ERROR + " error\n"

				//+ RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE + " *" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE +"\n"
				+ "id uint64\n"

				+ RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + " *" + epkindTypeName + "\n";

		if (this.apigen.job.parForeach)
		{
			res += "Thread int\n";
		}

		res += "}\n";

		return res;
	}

	public String makeMessageImports(EState s)
	{
		String res = "";
		for (String extSource : (Iterable<String>) s.getAllActions().stream()
				.filter(a -> a.mid.isOp())
				.flatMap(a -> a.payload.elems.stream().filter(p -> !(p instanceof GDelegationType))
						.filter(p -> !getExtName((DataType) p).matches("(\\[\\])*(int|string|byte)"))
						.filter(p -> !getExtName((DataType) p).matches("(\\*)*(int|string|byte)")))
				.map(p -> getExtSource((DataType) p))
				.distinct()::iterator)
		{
			res += "import \"" + extSource + "\"\n";
		}
		for (PayloadElemType<?> pet : (Iterable<PayloadElemType<?>>) s.getAllActions().stream()
				.filter(a -> a.mid.isOp())
				.flatMap(a -> a.payload.elems.stream().filter(p -> (p instanceof RPCoreGDelegationType))
				//.map(p -> getDelegatedChanPackageName((RPCoreGDelegationType) p))
						)
				.distinct()::iterator)
		{
			res += "import \"" + this.apigen.packpath + "/" + ((RPCoreGDelegationType) pet).getGlobalProtocol().getSimpleName()
							// *pet* gpn name (i.e., for state chan type being delegated) -- cf. *this*.gpn.toString() in getStateChannelFilePath
					+ "/" + getDelegatedChanPackageName((RPCoreGDelegationType) pet) + "\"\n";
		}
		for (String extSource : (Iterable<String>) s.getAllActions().stream()
				.filter(a -> a.mid.isMessageSigName())
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
						+ ") *" //+ ab.getReturnType(this, curr, succ)  // No: uses getStateChanName, which returns intermed name for nested states
								+ getSuccStateChanName(succ)
						+ " {\n"

				  // FIXME: currently redundant for case objects (cf. branch action err handling)
				+ "if " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ERROR + " != nil {\n"
				+ "panic(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ERROR + ")\n"
				+ "}\n"

				  // HACK FIXME: pre-create case objects
				+ ((ab instanceof RPCoreSTCaseActionBuilder)
						? RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
								+ "." + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_USE + "()\n"
						: "if " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + "id != "  // Not using atomic.LoadUint64 on id for now
										//+ "atomic.LoadUint64(&" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + "lin)"
										+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + "lin"
										+ " {\n"
								+ "panic(\"Linear resource already used\")\n" // + reflect.TypeOf(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "))\n"
								+ "}\n"
				  )
				/*+ "atomic.AddUint64(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
						+ "." + "lin" + ")\n"*/

				+ ab.buildBody(this, curr, a, succ) + "\n"
				+ "}";
	}

	// "Base case" -- more specific versions should be overriden in action builders
  // FIXME: refactor action builders as interfaces and use generic parameter for kind
	@Override
	public String buildActionReturn(STActionBuilder ab, EState curr, EState succ)
	{
		String res = "";
		res += /*"return " + //makeCreateSuccStateChan(ab, curr, succ);
				makeCreateSuccStateChan(succ);*/
					makeReturnSuccStateChan(succ);
		return res;
	}

	protected String getSuccStateChanName(EState succ)
	{
		/*RPCoreEState s = (RPCoreEState) succ;
		String name = s.hasNested()
				//? (s.isTerminal() ? getStateChanName(s) : getIntermediaryStateChanName(s))  // HACK FIXME -- first call to getStateChanName (intermed name not made yet)
				? getIntermediaryStateChanName(s)
				: getStateChanName(s);  //ab.getReturnType(this, curr, succ)
		return name;*/
		return this.names.get(succ.id);
	}

	//protected String makeCreateSuccStateChan(STActionBuilder ab, EState curr, EState succ)
	@Deprecated
	protected String makeCreateSuccStateChan(EState succ)
	{
		String name = getSuccStateChanName(succ);
		return makeCreateSuccStateChan(succ, name);
	}
		
	@Deprecated
	protected String makeCreateSuccStateChan(EState succ, String name)
	{
		String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
		if (((GoJob) this.job).selectApi &&
				getStateKind(succ) == RPCoreEStateKind.CROSS_RECEIVE && succ.getActions().size() > 1)
		{
			// Needs to be here (not in action builder) -- build (hacked) return for all state kinds
			// FIXME: factor out with RPCoreSTSessionApiBuilder and RPCoreSTSelectStateBuilder#getPreamble
			return "newBranch" + name + "(" + sEp + ")";
		}
		else
		{
			/*String res = "&" + name + "{ " + RPCoreSTApiGenConstants.GO_SCHAN_ERROR + ": " + RPCoreSTApiGenConstants.GO_IO_METHOD_ERROR
					+ ", " + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": " + sEp;
			if (!succ.isTerminal())  // FIXME: terminal foreach state
			{
				res += ", " + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
								+ ": new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ")";  // FIXME: EndSocket LinearResource special case
			}
			res += " }";*/
			String res =
					  /*"succ := " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "._" + name + "\n"
					+ "succ." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + RPCoreSTApiGenConstants.GO_MPCHAN_ERR + " = err\n"
					+ "return succ\n"*/
					RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "._" + name;
			return res;
		}
	}
	
	protected String makeReturnSuccStateChan(EState succ)
	{
		String name = getSuccStateChanName(succ);
		return makeReturnSuccStateChan(succ, name);
	}
	
	protected String makeReturnSuccStateChan(EState succ, String name)
	{
		String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
		if (((GoJob) this.job).selectApi &&
				getStateKind(succ) == RPCoreEStateKind.CROSS_RECEIVE && succ.getActions().size() > 1)
		{
			// Needs to be here (not in action builder) -- build (hacked) return for all state kinds
			// FIXME: factor out with RPCoreSTSessionApiBuilder and RPCoreSTSelectStateBuilder#getPreamble
			return "return newBranch" + name + "(" + sEp + ")";
		}
		else
		{
			/*String res = "&" + name + "{ " + RPCoreSTApiGenConstants.GO_SCHAN_ERROR + ": " + RPCoreSTApiGenConstants.GO_IO_METHOD_ERROR
					+ ", " + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ": " + sEp;
			if (!succ.isTerminal())  // FIXME: terminal foreach state
			{
				res += ", " + RPCoreSTApiGenConstants.GO_SCHAN_LINEARRESOURCE
								+ ": new(" + RPCoreSTApiGenConstants.GO_LINEARRESOURCE_TYPE + ")";  // FIXME: EndSocket LinearResource special case
			}
			res += " }";*/
			String nextState = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "._" + name;
			String res = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".lin = "  // FIXME: sync
							+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".lin + 1\n"
					+ nextState + ".id = " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".lin\n";

			/*if (this.apigen.job.parForeach)
			{
				res += RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Thread = "  // FIXME: sync
								+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Thread + 1\n"
						+ nextState + ".Thread = " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Thread\n";
			}*/

			res += "return "+ nextState + "\n";
			return res;
		}
	}

	@Override
	public String getChannelName(STStateChanApiBuilder api, EAction a)  // Not used?
	{
		throw new RuntimeException("[rp-core] Shouldn't get in here: " + a);
		//return "s.ep.GetChan(s.ep.Proto.(*" + api.gpn.getSimpleName() + ")." + a.peer + ")";
	}

	// Super is used for both state chan type name (type decl and method receivers), and return type of actions
	// Now, only using for state chan type name -- return type hardcoded to "base" name (via this.names) of successor
	@Override
	public String getStateChanName(EState s)
	{
		/*String name = this.imedNames.get(s.id);
		if (name == null)
		{
			RPCoreEState rps = (RPCoreEState) s;
			name = makeSTStateName(rps);
			this.imedNames.put(rps.id, name);  // The "original" name, now used for foreach -- doing this way round for convenience of original state channel naming
			if (rps.hasNested() && !rps.isTerminal())
			{
				name = name + "_";  // FIXME
			}
			this.names.put(s.id, name);
		}*/
		if (!this.names.containsKey(s.id))  // Should not be needed, but do for debugging
		{
			throw new RuntimeException("[rp-core] Shouldn't get here: " + s);
		}
		String n = this.names.get(s.id);
		/*if (n.equals("Init"))
		{
			return n;
		}*/
		return ((RPCoreEState) s).hasNested() ? n + "_" : n;
	}

	/*public String getIntermediaryStateChanName(EState s)
	{
		/*if (!this.names.containsKey(s.id))
		{
			//throw new RuntimeException("Shouldn't get in here: " + s.id);   // No: e.g., buildActionReturn, may refer to "ahead" state before that state is built
			getStateChanName(s);  // HACK FIXME
		}
		return this.imedNames.get(s.id);* /
		return getStateChanName(s) + "_";
	}*/
	
	@Override
	protected String makeSTStateName(EState s)
	{
		/*RPCoreEState rps = (RPCoreEState) s;
		String name = s.id == this.graph.init.id  // Includes single (nested) state endpoint kinds (i.e, Init, not End)
				? "Init_" + s.id  // FIXME: factor out (makeInitStateName)
				: (s.isTerminal()
						? //makeEndStateName(this.apigen.proto.getSimpleName(), this.variant)
								"End" + (rps.hasNested() ? "_" + s.id : "")
						: //this.apigen.proto.getSimpleName() + "_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(this.actual) + "_"
						  "State"
								+ this.counter++);
		return name;*/
		throw new RuntimeException("[rp-core] Shouldn't get in here: " + s);
	}
	
	/*public static String makeEndStateName(GProtocolName simpname, RPRoleVariant r)
	{
		return //simpname + "_" + ParamCoreSTEndpointApiGenerator.getGeneratedActualRoleName(r) + "_" + 
				RPCoreSTApiGenConstants.GO_SCHAN_END_TYPE;
	}*/
	
	// Not variants -- just indexed roles (in EFSM actions) -- cf. ParamCoreSTEndpointApiGenerator#getGeneratedRoleVariantName
	public static String getGeneratedIndexedRoleName(RPIndexedRole r) 
	{
		//return r.toString().replaceAll("\\[", "_").replaceAll("\\]", "_").replaceAll("\\.", "_");
		if (r.intervals.size() > 1)
		{
			throw new RuntimeException("[rp-core] TODO: " + r);
		}
		RPInterval g = r.intervals.iterator().next();
		return r.getName() + "_" + RPCoreSTApiGenerator.getGeneratedNameLabel(g.start)
				+ (g.start.equals(g.end) ? "" : "to" + RPCoreSTApiGenerator.getGeneratedNameLabel(g.end));
	}
	
	// Expressions to be used in code -- cf. RPCoreSTApiGenerator.getGeneratedNameLabel
	public String generateIndexExpr(RPIndexExpr e)
	{
		if (e instanceof RPIndexInt)
		{
			return e.toGoString();
		}
		else if (e instanceof RPIndexIntPair)
		{
			return e.toGoString();
		}
		else if (e instanceof RPIndexVar)
		{
			String sEp = RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT;
			if (e instanceof RPForeachVar
					|| this.fvars.stream().anyMatch(p -> p.toString().equals(e.toString())))  // FIXME HACK -- foreach var occurrences inside foreach body are RPIndexVars
			{
				if (this.apigen.job.parForeach)
				{
					return sEp + ".Params[" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + ".Thread][\"" + e.toGoString() + "\"]";
				}
				else
				{
					return sEp + ".Params[\"" + e.toGoString() + "\"]";
				}
			}
			else
			{
				return sEp
						//+ "." + RPCoreSTApiGenConstants.GO_ENDPOINT_PARAMS + "[\"" + e + "\"]";
						+ "." + e.toGoString();
			}
		}
		else if (e instanceof RPBinIndexExpr)
		{
			RPBinIndexExpr b = (RPBinIndexExpr) e;
			// TODO: factor out
			switch (this.apigen.mode)
			{
				case Int:  return "(" + generateIndexExpr(b.left) + b.op.toString() + generateIndexExpr(b.right) + ")";
				case IntPair:
				{
					String op;
					switch (b.op)
					{
						case Add:  op = "Plus";  break;
						case Subt:  op = "Sub";  break;
						case Mult:
						default:  throw new RuntimeException("[rp-core] Shouldn't get in here: " + e);
					}
					 return "(" + generateIndexExpr(b.left) + "." + op + "(" + generateIndexExpr(b.right) + "))";
				}
				default:  throw new RuntimeException("Shouldn't get in here: " + this.apigen.mode);
			}
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + e);
		}
	}

	/*public String makeExtNameImport(DataType t)
	{
		String extName = getExtName(t);
		return extName.matches("(\\[\\])*(int|string|byte)")
				? ""
				: "import \"" + getExtSource(t) + "\"\n";
	}*/

	
	

	protected boolean isDelegType(PayloadElemType<?> t)
	{
		//return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get() instanceof RPCoreDelegDecl;  // FIXME: make a map
				// "Old style", when deleg state chan types were directly imported by user as regular Go types
		return t.isGDelegationType();  // CHECKME: distinguish RPCoreGDelegationType?
	}
	
	// Cf. getExtName
	protected String getPayloadElemTypeName(PayloadElemType<?> pet)
	{
		if (pet instanceof RPCoreGDelegationType)
		{
			//return ((RPCoreSTStateChanApiBuilder) api).getExtName((DataType) pet);
			RPCoreGDelegationType gdt = (RPCoreGDelegationType) pet;
			String name;
			
			GProtocolName root = gdt.getRoot();
			GProtocolName state = gdt.getState();
			RPRoleVariant v = gdt.getVariant();
			RPCoreAstFactory af = new RPCoreAstFactory();
			
			// Based on RPCoreCommandLine.paramCoreParseAndCheckWF  // FIXME: integrate? (e.g., process all protocols, not just target main -- then won't need to separately run CL first on delegated proto)
			GProtocolDecl gpd = (GProtocolDecl) this.job.getContext().getMainModule()
					.getProtocolDecl(root.getSimpleName());  // FIXME: delegated protocol may not be in main module
			try
			{
				RPCoreGType gt = new RPCoreGProtocolDeclTranslator(this.job, af).translate(gpd);
				//RPCoreLType lt = gt.project(af, v, smt2t);
				
				/* // FIXME: currently cannot determine state chan name of target state in delegated protocol because of decoupled state numbering 
				   // If we rebuild the target endpoint graph here, the state numbering won't match
				RPCoreEGraphBuilder builder = new RPCoreEGraphBuilder(job);
				EGraph g = builder.build(this.L0.get(r).get(ranges));*/
			}
			catch (RPCoreSyntaxException e)
			{
				throw new RuntimeException("Shouldn't get in here: ", e);
			}
			
			// FIXME: hardcoded to Init because of above FIXME
			if (!root.equals(state))
			{
				throw new RuntimeException("[rp-core] TODO: delegation of non-initial state of target protocol: " + state);
			}
			name = "Init";
			
			return "*" + getDelegatedChanPackageName(gdt) + "." + name;
		}
		else if (pet instanceof DataType)
		{
			return getExtName((DataType) pet);
		}
		else
		{
			throw new RuntimeException("[rp-core] TODO: " + pet);
		}
	}
	
	protected static String getDelegatedChanPackageName(RPCoreGDelegationType gdt)
	{
		GProtocolName proto = gdt.getGlobalProtocol();
		RPRoleVariant variant = gdt.getVariant();
		return RPCoreSTApiGenerator.getEndpointKindTypeName(proto.getSimpleName(), variant);  // N.B. doesn't use proto
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

