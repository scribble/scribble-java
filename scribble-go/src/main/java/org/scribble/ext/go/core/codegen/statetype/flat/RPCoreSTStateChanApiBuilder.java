package org.scribble.ext.go.core.codegen.statetype.flat;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenConstants;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator;
import org.scribble.ext.go.core.codegen.statetype.RPCoreSTApiGenerator.Mode;
import org.scribble.ext.go.core.model.endpoint.RPCoreEState;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossSend;
import org.scribble.ext.go.core.type.RPFamily;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.core.type.name.RPCoreGDelegationType;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPBinIndexExpr;
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

	

	//.. refactor schan name making into parent api gen
	//.. refactor import generation


// Following org.scribble.ext.go.codegen.statetype.go.GoSTStateChanAPIBuilder
// CHECKME: can this be factored out between "flat" and "nested"? -- take state/action builders as params
public class RPCoreSTStateChanApiBuilder extends STStateChanApiBuilder
{
	public final RPCoreSTApiGenerator parent;
	public final RPFamily family;
	public final RPRoleVariant variant;  // variant.getName().equals(this.role)
	
	public final RPCoreSTForeachEntryStateBuilder feb 
			= new RPCoreSTForeachEntryStateBuilder();
	
	private final Set<DataTypeDecl> dtds; // FIXME: use "main.getDataTypeDecl((DataType) pt);" instead -- cf. OutputSocketGenerator#addSendOpParams
	private final Set<MessageSigNameDecl> msnds;

	protected final Set<RPIndexVar> fvars = new HashSet<>();
	//private final List<EGraph> todo = new LinkedList<>();  // HACK FIXME: to preserve "order" of state building -- cf. fvars hack for var scope

	// N.B. the base EGraph class will probably be replaced by a more specific (and more helpful) rp-core class later
	// Pre: variant.getName().equals(this.role)
	public RPCoreSTStateChanApiBuilder(RPCoreSTApiGenerator apigen,
			RPFamily family, RPRoleVariant variant, EGraph graph,
			Map<Integer, String> names)
	{
		this(apigen, family, variant, graph, names, Collections.emptySet());
	}

	private RPCoreSTStateChanApiBuilder(RPCoreSTApiGenerator apigen,
			RPFamily family, RPRoleVariant variant, EGraph graph,
			Map<Integer, String> names, Set<RPIndexVar> fvars)
	{
		super(apigen.job, apigen.proto, //apigen.self, 
				variant.getName(),
				graph,
				
				// FIXME: refactor to parameterise on flat/nested builders
				// Similarly for -parforeach?
				
				new RPCoreSTOutputStateBuilder(
						null, //new RPCoreSTSplitActionBuilder(),  // TODO
						new RPCoreSTSendActionBuilder()),
				new RPCoreSTReceiveStateBuilder(
						null, //new RPCoreSTReduceActionBuilder(),  // TODO
						new RPCoreSTReceiveActionBuilder()),

				// Select-based branch, or type switch branch by default
				(apigen.job.selectApi)
						? new RPCoreSTSelectStateBuilder(new RPCoreSTSelectActionBuilder())
						: new RPCoreSTBranchStateBuilder(new RPCoreSTBranchActionBuilder()),
				(apigen.job.selectApi)
						? null
						: new RPCoreSTCaseBuilder(new RPCoreSTCaseActionBuilder()),

				new RPCoreSTEndStateBuilder());

		this.parent = apigen;
		this.family = family;
		this.variant = variant;
		
		Module mod = apigen.job.getContext().getModule(this.parent.proto.getPrefix());
		this.dtds = mod.getNonProtocolDecls().stream()
				.filter(d -> (d instanceof DataTypeDecl)).map(d -> ((DataTypeDecl) d)).collect(Collectors.toSet());
		this.msnds = mod.getNonProtocolDecls().stream()
				.filter(d -> (d instanceof MessageSigNameDecl)).map(d -> ((MessageSigNameDecl) d)).collect(Collectors.toSet());

		this.fvars.addAll(fvars);
		
		this.names.putAll(names);  
			// Names already pre-allocated; not using the "on-demand" name creation of the base framework
			// TODO: refactor base framework
	}
	
	@Override
	public Map<String, String>  // filepath -> source
			build()
	{
		Map<String, String> res = new HashMap<>();
		Set<EState> states = new LinkedHashSet<>();
		states.add(this.graph.init);
		states.addAll(MState.getReachableStates(this.graph.init));  // Top-level states -- does not include any Foreach nested states
		
		// Always make an "End" (including for non-terminating EFSMs)
		// "End" is a "true End", i.e., no further methods (I/O or Foreach) -- thus, a nesting terminal has "reversed End" and "End_x" naming
		// And "End" is reused for every "true End" (e.g., for every nested End)
		// TODO: factor out with RPCoreSTEndStateBuilder  // FIXME: RPCoreSTEndStateBuilder currently unused
		// TODO: factor out with getStateChanPremable?
		String epkindTypeName = this.parent.namegen.getEndpointKindTypeName(this.variant); 
		String end = "package " + this.parent.namegen.getEndpointKindPackageName(this.variant) + "\n"
				+ "\n"
				+ "type End struct {\n"
				+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " error\n"
				+ "id uint64\n"
				+ RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *" + epkindTypeName + "\n" 
				+ (this.parent.job.parForeach ? "Thread int\n" : "")  // TODO: use default Thread field for non -parforeach
				+ "}\n";
		res.put(getStateChannelFilePath("End"), end);  // TODO: factor out constant
		
		for (EState s : (Iterable<EState>) 
				states.stream().sorted(MState.COMPARATOR)::iterator)
		{
			String schanTypeName = getStateChanName(s);
			switch (RPCoreSTStateChanApiBuilder.getStateKind(s))
			{
				case CROSS_SEND: 
				{
					res.put(getStateChannelFilePath(schanTypeName), this.ob.build(this, s));
					break;
				}
				case CROSS_RECEIVE: 
				{
					if (s.getActions().size() > 1)
					{
						if (((GoJob) this.job).selectApi)  // Select-based branch
						{
							//res.put(getStateChannelFilePath(getStateChanName(s)), this.bb.build(this, s));
							throw new RuntimeException("[rp-core] TODO: -select");
						}
						else // Type-switch based branch by default
						{
							res.put(getStateChannelFilePath(schanTypeName),
									this.bb.build(this, s));
							res.put(
									getStateChannelFilePath(
											this.cb.getCaseStateChanName(this, s)),
									this.cb.build(this, s));
						}
					}
					else
					{
						res.put(getStateChannelFilePath(schanTypeName), this.rb.build(this, s));
					}
					break;
				}
				case TERMINAL: break;  // If terminal has nested, handled below
				default: throw new RuntimeException("[rp-core] Shouldn't get in here: " + s);
			}

			// Generate APIs for nested EFSMs
			RPCoreEState s1 = (RPCoreEState) s;
			if (s1.hasNested())
			{
				Map<String, String> tmp = new HashMap<>();
				String fib = this.feb.build(this, s1);
				tmp.put(getStateChannelFilePath(this.names.get(s1.id)), fib);
				EGraph nested = new EGraph(s1.getNested(), MState.getTerminal(s1));
				Set<RPIndexVar> tmp2 = new HashSet<>(this.fvars);
				tmp2.add(s1.getParam());  // CHECKME: state visiting order(?)  not guaranteed (w.r.t. lexical var scope)
				RPCoreSTStateChanApiBuilder nestedBuilder = new RPCoreSTStateChanApiBuilder(
						this.parent, this.family, this.variant, nested, this.names, tmp2);
				tmp.putAll(nestedBuilder.build());  // Includes another "End", but doesn't matter
				res.putAll(tmp);
			}
		}
		
		return res;
	}
	
	// Returns path of target *file* as an offset to -d
	// -- cf. packpath, "absolute" Go import path (github.com/...) -- would coincide if protocol full name (i.e., module) used "github.com/..."
	@Override
	public String getStateChannelFilePath(String schanTypeName)
	{
		if (schanTypeName.startsWith("_"))  // Cannot use "_" prefix, ignored by Go
				// CHECKME: what is "_" prefix used for?
		{
			schanTypeName = "$" + schanTypeName.substring(1);
		}
		boolean isCommonEndpointKind = this.parent.isCommonEndpointKind(variant);
		return this.parent.namegen.getStateChannelDirPath(this.family,
					this.variant, isCommonEndpointKind)
				+ "/" + schanTypeName + ".go";
	}
	
	// Factored out here from state-specific builders
	public String getStateChanPremable(EState s)
	{
		String packDecl;
		String msgImports;
		String sessPackImport = "";
		
		packDecl = "package " + this.parent.namegen.getEndpointKindPackageName(this.variant) + "\n";
				
		// Not needed by select-branch or Case objects  // CHECKME: refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.OUTPUT
				|| s.getStateKind() == EStateKind.UNARY_INPUT
				|| (s.getStateKind() == EStateKind.POLY_INPUT
						&& !this.parent.job.selectApi))
		{
			msgImports = makeMessageImports(s, true);
		}
		else
		{
			msgImports = "";
		}

		// Input state session-package (and other) imports
		// CHECKME: still needed? -- refactor back into state-specific builders?
		if (s.getStateKind() == EStateKind.UNARY_INPUT
				|| s.getStateKind() == EStateKind.POLY_INPUT)
		{
			switch (this.parent.mode)
			{
				case Int:
				{
					sessPackImport = "import \""
							+ RPCoreSTApiGenConstants.INT_RUNTIME_SESSION_PACKAGE + "\"\n";
					break;
				}
				case IntPair:
				{
					sessPackImport = "import \""
							+ RPCoreSTApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE
							+ "\"\n";
					break;
				}
				default:
					throw new RuntimeException(
							"Shouldn't get in here:" + this.parent.mode);
			}

			// CHECKME: all still needed?
			sessPackImport += "import \"sync/atomic\"\n";
			sessPackImport += "import \"reflect\"\n";
			sessPackImport += "import \"sort\"\n";
			sessPackImport += "\n";
			sessPackImport += "var _ = session2.NewMPChan\n";
			sessPackImport += "var _ = atomic.AddUint64\n";
			sessPackImport += "var _ = reflect.TypeOf\n";
			sessPackImport += "var _ = sort.Sort\n";
		}

		// Output state session package import
		if (s.getStateKind() == EStateKind.OUTPUT)
		{
			if (this.parent.mode == Mode.IntPair)
			{
				sessPackImport = "import \"" + RPCoreSTApiGenConstants.INTPAIR_RUNTIME_SESSION_PACKAGE + "\"\n"
						+ "\n"
						+ "var _ = session2.XY\n";  // For nested states that only use foreach vars (so no session2.XY)
			}
			else if (this.parent.job.parForeach)
			{
				sessPackImport = "import \"" + RPCoreSTApiGenConstants.INT_RUNTIME_SESSION_PACKAGE + "\"\n";
			}
			else  // e.g., Mode.Int, not -parforeach
			{
				// No session-package import (empty string)
			}
		}

		String res = packDecl
				+ "\n"
				+ msgImports 
				+ sessPackImport;
		return res;
	}
	
	// State channel type decl
	// Factored out here from state-specific builders
	public String makeStateChannelType(RPCoreEState s)
	{
		String schanTypeName = this.getStateChanName(s);
		String epkindTypeName = this.parent.namegen.getEndpointKindTypeName(this.variant); 

		String res = "type " + schanTypeName + " struct {\n"
				+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " error\n";

		if (this.parent.job.parForeach)
		{
			res += RPCoreSTApiGenConstants.SCHAN_RES_FIELD + " *"
					+ RPCoreSTApiGenConstants.LINEARRESOURCE_TYPE + "\n";
		}
		else
		{
			res += "id uint64\n";
		}

		res += RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + " *" + epkindTypeName
				+ "\n";

		if (this.parent.job.parForeach)  // TODO: make a default Thread int for non -parforeach
		{
			res += "Thread int\n";
		}

		res += "}\n";
		
		return res;
	}

	public String makeMessageImports(EState s, boolean pays)
	{
		String res = "";
		if (pays)
		{
			for (String extSource : (Iterable<String>) 
					s.getAllActions().stream()
							.filter(a -> a.mid.isOp())
							.flatMap(a -> a.payload.elems.stream()
									.filter(p -> !(p instanceof GDelegationType))
									.filter(p -> !getExtName((DataType) p)
											.matches("(\\[\\])*(int|string|byte)"))
									.filter(p -> !getExtName((DataType) p)
											.matches("(\\*)*(int|string|byte)")))
							.map(p -> getExtSource((DataType) p))
							.distinct()::iterator)
			{
				res += "import \"" + extSource + "\"\n";
			}
			for (PayloadElemType<?> pet : (Iterable<PayloadElemType<?>>) 
					s.getAllActions().stream()
							.filter(a -> a.mid.isOp()).flatMap(a -> a.payload.elems.stream()
							.filter(p -> (p instanceof RPCoreGDelegationType)))
					.distinct()::iterator)
			{
				res += "import \"" + this.parent.packpath + "/"
						+ ((RPCoreGDelegationType) pet).getGlobalProtocol().getSimpleName()
								// *pet* gpn name (i.e., for state chan type being delegated)
								// cf. *this*.gpn.toString() in getStateChannelFilePath
						+ "/" + getDelegatedChanPackageName((RPCoreGDelegationType) pet)
						+ "\"\n";
			}
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
		String res = "func (" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER
				+ " *" + ab.getStateChanType(this, curr, a) + ") " + ab.getActionName(this, a) + "(" 
					+ ab.buildArgs(this, a)
					+ ") *" //+ ab.getReturnType(this, curr, succ)  // No: uses getStateChanName, which returns intermed name for nested states
					+ getSuccStateChanName(succ)
					+ " {\n"

				  // FIXME: currently redundant for case objects (cf. branch action err handling)
				+ "if " + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + " != nil {\n" + "panic("
					+ RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.SCHAN_ERR_FIELD + ")\n"				+ "}\n";

		if (this.parent.job.parForeach)
		{
			res += RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.SCHAN_RES_FIELD + "."
					+ RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n";
		}
		else
		{
			// HACK FIXME: pre-create case objects
			res += ((ab instanceof RPCoreSTCaseActionBuilder)
					? RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
							+ RPCoreSTApiGenConstants.SCHAN_RES_FIELD + "."
							+ RPCoreSTApiGenConstants.LINEARRESOURCE_USE + "()\n"
					: "if " + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
								+ "id != "  // Not using atomic.LoadUint64 on id for now
								//+ "atomic.LoadUint64(&" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + "." + "lin)"
								+ RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD + "." + "lin"
								+ " {\n"
							+ "panic(\"Linear resource already used\")\n" // + reflect.TypeOf(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "))\n"
								+ "}\n"
				  );
				/*+ "atomic.AddUint64(" + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT
						+ "." + "lin" + ")\n"*/
		}

		res += ab.buildBody(this, curr, a, succ) + "\n"
				+ "}";

		return res;
	}

	// "Base case" -- more specific versions should be overriden in action builders
  // TODO: refactor action builders as interfaces and use generic parameter for kind
	@Override
	public String buildActionReturn(STActionBuilder ab, EState curr, EState succ)
	{
		return makeReturnSuccStateChan(succ);
	}

	public String getSuccStateChanName(EState succ)
	{
		//return this.names.get(succ.id);
		return getStateChanBaseName(succ.id);  // Returns "base" name, cf. getStateChanName
	}

	public String makeReturnSuccStateChan(EState succ)
	{
		String name = getSuccStateChanName(succ);
		if (((GoJob) this.job).selectApi
				&& getStateKind(succ) == RPCoreEStateKind.CROSS_RECEIVE
				&& succ.getActions().size() > 1)
		{
			/*String sEp = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
					+ RPCoreSTApiGenConstants.SCHAN_EPT_FIELD;
			// Needs to be here (not in action builder) -- build (hacked) return for all state kinds
			// TODO: factor out with RPCoreSTSessionApiBuilder and RPCoreSTSelectStateBuilder#getPreamble
			return "return newBranch" + name + "(" + sEp + ")";*/
			throw new RuntimeException("[rp-core] TODO: -select");
		}
		else
		{
			return makeReturnSuccStateChan(name);
		}
	}
	
	// Parameterised on "name" (not state) for foreach intermed state building
	protected String makeReturnSuccStateChan(String succName)
	{
		String sEp = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "."
				+ RPCoreSTApiGenConstants.SCHAN_EPT_FIELD;
		{
			String nextState;
			if (this.parent.job.parForeach)
			{
				nextState = "succ := "
						+ this.parent.makeStateChanInstance(succName, sEp,
								RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + ".Thread");
				return nextState + "\n"
						+ "return succ\n";
			}
			else
			{
				nextState = sEp + "._" + succName;
				String res = sEp + ".lin = " + sEp + ".lin + 1\n" // TODO: sync
						+ nextState + ".id = " + sEp + ".lin\n"
						+ "return "+ nextState + "\n";
				return res;
			}

			/*if (this.apigen.job.parForeach)
			{
				res += RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Thread = "  // FIXME: sync
								+ RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Thread + 1\n"
						+ nextState + ".Thread = " + RPCoreSTApiGenConstants.GO_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.GO_SCHAN_ENDPOINT + ".Thread\n";
			}*/
		}
	}

	@Override
	public String getChannelName(STStateChanApiBuilder api, EAction a)  // Not currently used?
	{
		throw new RuntimeException("[rp-core] Shouldn't get in here: " + a);
	}

	// super version is used for both statechan type name (type decl and method receivers), and return type of actions
	// Here, only using for state chantype name -- return type hardcoded to "base" name (via this.names) of successor
	@Override
	public String getStateChanName(EState s)
	{
		if (!this.names.containsKey(s.id))  // Should not be needed, but do for debugging
		{
			throw new RuntimeException("[rp-core] Shouldn't get here: " + s);
		}
		String n = this.names.get(s.id);
		return ((RPCoreEState) s).hasNested() ? n + "_" : n;  // TODO: factor out intermed naming with RPCoreSTForeachEntryStateBuilder
				// If nesting, generate outer I/O actions on intermed statechan (built via "base" action builders with state name mangling)
	}

	// this.names records "base" names -- cf. getStateChanName, implicitly adds "_" suffix for intermed state naming
	// Additional foreach "entry" state uses the "base" name
	protected String getStateChanBaseName(int id)  // Take id instead of state to distinguish from getStateChanName
	{
		return this.names.get(id);
	}
	
	@Override
	protected String makeSTStateName(EState s)
	{
		throw new RuntimeException("[rp-core] Shouldn't get in here: " + s);
	}

	public boolean isDelegType(PayloadElemType<?> t)
	{
		//return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get() instanceof RPCoreDelegDecl;  // FIXME: make a map
				// "Old style", when deleg state chan types were directly imported by user as regular Go types
		return t.isGDelegationType();  // CHECKME: distinguish RPCoreGDelegationType?
	}
	
	// Cf. getExtName
	// TODO: refactor into namegen ?
	public String getPayloadElemTypeName(PayloadElemType<?> pet)
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
			
			// Based on RPCoreCommandLine.paramCoreParseAndCheckWF  // CHECKME: integrate? (e.g., process all protocols, not just target main -- then won't need to separately run CL first on delegated proto)
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
	
	// TODO: refactor into namegen ?
	protected String getDelegatedChanPackageName(RPCoreGDelegationType gdt)
	{
		//GProtocolName proto = gdt.getGlobalProtocol();
		RPRoleVariant variant = gdt.getVariant();
		return this.parent.namegen.getEndpointKindTypeName(variant);
	}
	
	// TODO: refactor into namegen
	protected String getExtName(DataType t)
	{
		return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get().extName;
	}
	
	protected String getExtSource(DataType t)
	{
		return this.dtds.stream().filter(x -> x.getDeclName().equals(t)).findAny().get().extSource;  // FIXME: make a map
	}

	public String getExtName(MessageSigName n)
	{
		return this.msnds.stream().filter(x -> x.getDeclName().equals(n)).findAny().get().extName;
	}

	protected String getExtSource(MessageSigName n)
	{
		return this.msnds.stream().filter(x -> x.getDeclName().equals(n)).findAny().get().extSource; 
	}
	
	
	
	
	// TODO: rename cross/dot
	// CHECKME: currently redundant?  (no dot/multichoices) -- but maybe needed again for pipe/pair
	protected enum RPCoreEStateKind { CROSS_SEND, CROSS_RECEIVE, DOT_SEND, DOT_RECEIVE, MULTICHOICES_RECEIVE, TERMINAL }
	
	// FIXME: refactor into RPCoreEState?
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

	

	
	// Expressions to be used in code -- cf. RPCoreSTApiGenerator.getGeneratedNameLabel
	// TODO: refactor into RPIndexExpr -- cf. toGoString ?
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
			String sEp = RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + "." + RPCoreSTApiGenConstants.SCHAN_EPT_FIELD;
			//if (e instanceof RPForeachVar ||
			if (
					this.fvars.stream().anyMatch(p -> p.toString().equals(e.toString())))  // FIXME HACK -- foreach var occurrences inside foreach body are RPIndexVars
			{
				if (this.parent.job.parForeach)
				{
					return sEp + ".Params[" + RPCoreSTApiGenConstants.API_IO_METHOD_RECEIVER + ".Thread][\"" + e.toGoString() + "\"]";
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
			switch (this.parent.mode)
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
				default:  throw new RuntimeException("Shouldn't get in here: " + this.parent.mode);
			}
		}
		else
		{
			throw new RuntimeException("[rp-core] Shouldn't get in here: " + e);
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

