package org.scribble2.model.visit.env;

import org.scribble2.model.del.ModuleDelegate;
import org.scribble2.model.visit.JobContext;

// Integrate with NodeContext? (e.g. ast.context and visit.env)
// Immutable
public abstract class Env
{
	private final JobContext jcontext;  // Can be obtained from ModuleContext
	private final ModuleDelegate mcontext;

	//private final Env root;  // Can be obtained by following parent chain
	//private Env parent;
	////private final Node node;
	
	// FIXME: duplicated with Visitor fields?  Visitor jcontext is from Job and mcontext is from SubprotocolVisitor
	public Env(JobContext jcontext, ModuleDelegate mcontext)//, Module mod)
	{
		//this(jcontext, mcontext, this, null);
		//this.root = this;
		//this.node = mod;
		this.jcontext = jcontext;
		this.mcontext = mcontext;
		/*this.root = this;
		this.parent = null;*/
	}
	
	// Maybe go back to copy constructor with push/pop methods
	//public Env(IEnv env, Node node)
	/*public Env(Env parent)
	{
		this(parent.getJobContext(), parent.getModuleContext(), parent.getRoot(), parent);
		//this.node = node;
	}*/
	
	/*protected Env(JobContext jcontext, ModuleDelegate mcontext, Env root, Env parent)
	{
		this.jcontext = jcontext;
		this.mcontext = mcontext;
		this.root = root;
		this.parent = parent;
	}*/
	
	protected abstract Env copy();  // Shallow copy (FIXME: factor out shallow copy interface)

	// Default push for entering a compound interaction context (e.g. used in CompoundInteractionDelegate)
	public abstract Env push();

	//public abstract WellFormedChoiceEnv merge(List<? extends Env> children)

	/*public Env push()
	{
		Env copy = copy();
		copy.parent = this;
		//this.node = node;
		return copy;
	}
	
	public Env pop()
	{
		return this.parent;
	}*/

	/*// If this single merge routine cannot apply to all compound statements then have to delegate to specific statements
	//public VisitorEnv popWithMerge(List<GlobalProtocolBlock> blocks)
	public BaseEnv popWithMerge(Node n, List<BaseEnv> envs)
	{
		List<RolesEnv> rios = new LinkedList<>();
		List<OperatorEnv> ops = new LinkedList<>();
		List<ReachabilityEnv> reachs = new LinkedList<>();
		List<ProtocolDeclEnv> pdes = new LinkedList<>();
		List<ScopeEnv> ses = new LinkedList<>();
		for (BaseEnv env : envs)
		{
			rios.add(env.roles);
			ops.add(env.ops);
			reachs.add(env.reach);
			pdes.add(env.decls);
			ses.add(env.scopes);
		}
		this.parent.roles.mergeRolesEnvs(rios);
		this.parent.ops.mergeOpEnvs(ops);
		this.parent.reach.mergeReachabilityEnvs(n, reachs);
		this.parent.decls.mergeProtocolDeclEnvs(pdes);
		this.parent.scopes.mergeScopeEnvs(n, ses);
		return this.parent;
	}

	private List<BaseEnv> getBlockEnvs(List<? extends ProtocolBlock> blocks)
	{
		List<BaseEnv> envs = new LinkedList<>();
		for (ProtocolBlock block : blocks)
		{
			envs.add(block.getEnv());
		}
		return envs;
	}
	
	public BaseEnv pushProtocolDecl(ProtocolDecl pd) throws ScribbleException
	{
		BaseEnv env = push();
		/*ProtocolName fmn = pd.getFullProtocolName(this);
		ProtocolSignature sig = pd.getProtocolSignature(fmn, env);
		SubprotocolSignature sub = sig.toSubprotocolSignature(pd.rdl, pd.pdl);
		env.stack.pushDo(sub);  // Factor out with pushSubprotocol?*
		return env;
	}
	
	public BaseEnv popProtocolDecl(ProtocolDecl pd)
	{
		//this.stack.pop();  // Would implicitly be done by done anyway
		return pop();
	}
	
	// FIXME: move following routines into Nodes (only have generic push/pop here) -- originally here for field access
	public BaseEnv pushChoice(Choice cho)
	{
		BaseEnv env = push();
		env.roles.disableAll();
		env.roles.enableRole(Role.EMPTY_ROLE, cho.subj.toName(), RolesEnv.DEFAULT_ENABLING_OP);
		
		env.roles.enterEnablingContext();
		
		env.scopes.clearSeen();
		
		return env;
	}

	public BaseEnv popChoice(Choice cho)
	{
		return popWithMerge(cho, getBlockEnvs(cho.blocks));
	}
	
	public BaseEnv pushParallel(Parallel par)
	{
		BaseEnv env = push();
		env.ops.clearAll();
		env.labs.disable();
		env.stack.disable();
		
		env.roles.enterNonEnablingContext();
		
		env.scopes.clearSeen();
		
		return env;
	}
	
	public BaseEnv popParallel(Parallel par)
	{
		return popWithMerge(par, getBlockEnvs(par.blocks));  // parent has the original recursion labs
	}
	
	public BaseEnv pushInterruptible(Interruptible intt)
	{
		BaseEnv env = push();
		env.ops.clearAll();  // Using ops to determine "involved roles" (in GlobalInterrupt) -- maybe should use roles for this instead
		env.labs.disable();
		env.scopes.enterScope(intt.scope.toName());
		env.stack.disable();
		
		env.roles.enterNonEnablingContext();
		
		return env;
	}

	public BaseEnv popInterruptible(Interruptible intt)
	{
		List<BaseEnv> envs = new LinkedList<>();
		envs.add(this);
		BaseEnv env = popWithMerge(intt, envs);
		//env.scopes.leaveScope();  // scope has been implicitly popped
		return env;
	}

	public BaseEnv pushDo(Do doo) throws ScribbleException
	{
		ProtocolName fmn = doo.getFullTargetProtocolName(this);
		ProtocolSignature sig = doo.getTargetProtocolDecl(this).getProtocolSignature(fmn);
		BaseEnv env = pushSubprotocol(sig, doo.ril, doo.ail);
		env.scopes.enterScope(doo.scope.toName());
		if (!env.decls.isRecorded(fmn))
		{
			env.decls.enter(fmn, env);
		}
		return env;
	}

	public BaseEnv popDo(Do doo) throws ScribbleException
	{
		ProtocolName pn = doo.getFullTargetProtocolName(this);
		if (this.decls.isRecording(pn))
		{
			this.decls.leave(pn, this);
		}
		this.scopes.leaveScope();
		return popSubprotocol();
	}

	private BaseEnv pushSubprotocol(ProtocolSignature sig, RoleInstantiationList ril, ArgumentInstantiationList ail) throws ScribbleException
	{
		BaseEnv env = new BaseEnv(this);
		//ProtocolSignature sig = env.getGlobalProtocolDecl(pn).getProtocolSignature(env);
		SubprotocolSignature sub = sig.toSubprotocolSignature(env, ril, ail);
		env.stack.pushDo(sub);
		return env;
	}
	
	private BaseEnv popSubprotocol()
	{
		this.stack.pop();
		return this;
	}
	
	public boolean isPayloadTypeDeclared(PayloadType mn)
	{
		return this.visibility.isPayloadTypeVisible(mn);
	}
	
	//public Name getFullMemberName(Name mn)
	public ProtocolName getFullGlobalProtocolName(ProtocolName mn) throws ScribbleException
	{
		return this.visibility.getFullGlobalProtocolDeclName(mn);
	}

	public ProtocolName getFullLocalProtocolName(ProtocolName mn) throws ScribbleException
	{
		return this.visibility.getFullLocalProtocolDeclName(mn);
	}
	
	public GlobalProtocolDecl getGlobalProtocolDecl(ProtocolName mn) throws ScribbleException
	{
		ProtocolName fmn = getFullGlobalProtocolName(mn);
		Module module = this.job.getModule(fmn.getModule());
		return module.getGlobalProtocolDecl(fmn.getSimpleName());
	}

	public LocalProtocolDecl getLocalProtocolDecl(ProtocolName mn) throws ScribbleException
	{
		ProtocolName fmn = getFullLocalProtocolName(mn);
		Module module = this.job.getModule(fmn.getModule());
		return module.getLocalProtocolDecl(fmn.getSimpleName());
	}*/
	
	/*public GlobalProtocolBlock getGlobalProtocolBody(ProtocolName mn)
	{
		ProtocolName fmn = getFullGlobalProtocolName(mn);
		Module module = this.job.getModule(fmn.getModule());
		GlobalProtocolDecl gpd = module.getGlobalProtocolDecl(fmn.getSimpleName());
		return gpd.gpd.block;
	}*/
		
	//@Override
	public JobContext getJobContext() 
	{
		return this.jcontext;
	}
		
	//@Override
	public ModuleDelegate getModuleDelegate() 
	{
		return this.mcontext;
	}

	/*public Env getProtocolDeclEnv()
	{
		return this.root;
	}

	//@Override
	public Env getParent()
	{
		return this.parent;
	}*/

	/*@Override
	public Node getNode()
	{
		return this.node;
	}*/
}
