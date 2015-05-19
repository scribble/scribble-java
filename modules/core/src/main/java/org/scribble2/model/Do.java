package org.scribble2.model;

import java.util.Iterator;

import org.scribble2.model.context.ModuleContext;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public abstract class Do<K extends ProtocolKind> extends SimpleInteractionNode<K> //implements ScopedNode
{
	//public final ScopeNode scope;
	public final RoleArgumentList roles;
	public final ArgumentList args;
	public final ProtocolNameNode<K> proto;  // Maybe use an "Ambiguous" version until names resolved -- is a visible protocol, but not necessarily a simple or full member name

	//protected Do(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto)
	protected Do(RoleArgumentList roleinstans, ArgumentList arginstans, ProtocolNameNode<K> proto)
	{
		//this.scope = scope;
		this.roles = roleinstans;
		this.args = arginstans;
		this.proto = proto;
	}

	//protected abstract Do reconstruct(ScopeNode scope, RoleInstantiationList roleinstans, ArgumentInstantiationList arginstans, ProtocolNameNode proto);//, SimpleInteractionNodeContext sicontext, Env env);
	protected abstract Do<K> reconstruct(RoleArgumentList roleinstans, ArgumentList arginstans, ProtocolNameNode<K> proto);//, SimpleInteractionNodeContext sicontext, Env env);

	@Override
	public Do<K> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//ScopeNode scope = isScoped() ? (ScopeNode) visitChild(this.scope, nv) : null;
		RoleArgumentList ril = (RoleArgumentList) visitChild(this.roles, nv);
		ArgumentList al = (ArgumentList) visitChild(this.args, nv);
		ProtocolNameNode<K> proto = visitChildWithClassCheck(this, this.proto, nv);
		//return reconstruct(scope, ril, al, proto);//, getContext(), getEnv());
		return reconstruct(ril, al, proto);//, getContext(), getEnv());
	}

	/*@Override
	public Do visitChildrenInSubprotocols(SubprotocolVisitor spv) throws ScribbleException
	{
		/*Do doo = visitChildren(spv);  // Should we do this or not?
		ModuleContext mcontext = ((CompoundInteractionContext) spv.peekContext()).getModuleContext();
		ProtocolName fullname = mcontext.getFullProtocolDeclName(this.proto.toName());
		//spv.enterSubprotocol(fullname, this.ril.getRoles(), this.ail.getArguments());* /
		if (!spv.isCycle())
		//if (spv.isCycle() != -1)
		{
			//ModuleDelegate mcontext = spv.getModuleContext();
			/*JobContext jcontext = spv.getJobContext();
			ModuleDelegate mcontext = (ModuleDelegate) jcontext.getMainModule().del();* /
			ModuleContext mcontext = spv.getModuleContext();
			ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
					//pd = spv.job.getContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());* /
					pd = getTargetProtocolDecl(spv.getJobContext(), mcontext);

			//... do wf-choice env building and checking
			//... scopes (all messages/operators are scoped?)
			
			//Node substituted = pd.def.block.visit(spv.getSubstitutor());  // block does an env push/pop without merge to parent
			ModelNode seq = spv.applySubstitutions(pd.def.block.seq);
			seq.accept(spv);  // Return is discarded
		}
		return this;
	}*/
	
	/*//private ProtocolName getTargetFullProtocolName(ModuleDelegate mcontext)
	public ProtocolName<K> getTargetFullProtocolName(ModuleContext mcontext)
	{
		return mcontext.getFullProtocolDeclName(this.proto.toName());
	}*/
	public abstract ProtocolName<K> getTargetFullProtocolName(ModuleContext mcontext);
	
	public
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
			ProtocolDecl<? extends ProtocolKind>
			//getTargetProtocolDecl(JobContext jcontext, ModuleDelegate mcontext)
			getTargetProtocolDecl(JobContext jcontext, ModuleContext mcontext)
	{
		ProtocolName<K> fullname = getTargetFullProtocolName(mcontext);
		return jcontext.getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
	}
	//public abstract ProtocolDecl getTargetProtocolDecl(JobContext jcontext, ModuleContext mcontext);
	
	public Role getTargetRoleParameter(JobContext jcontext, ModuleContext mcontext, Role role)
	{
		Iterator<Role> args = this.roles.getRoles().iterator();
		Iterator<Role> params = getTargetProtocolDecl(jcontext, mcontext).header.roledecls.getRoles().iterator();
		while (args.hasNext())
		{
			Role arg = args.next();
			Role param = params.next();
			if (arg.equals(role))
			{
				return param;
			}
		}
		throw new RuntimeException("Not an argument role: " + role);
	}
	
	/*@Override
	public boolean isEmptyScope()
	{
		return this.scope == null;
	}

	@Override
	//public Scope getScope()
	public SimpleName getScopeElement()
	{
		return this.scope.toName();
	}
	
	public boolean isScoped()
	{
		return this.scope != null;
	}*/

	@Override
	public String toString()
	{
		String s = Constants.DO_KW + " ";
		//if (!hasEmptyScopeNode())
		/*if (isScoped())
		{
			s += this.scope + ":";
		}*/
		return s + this.proto + this.args + this.roles + ";";
	}

	/*@Override
	public Do leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		builder.addProtocolDependency(getTargetFullProtocolName(builder.getModuleContext()));
		return this;
	}

	@Override
	public WellFormedChoiceChecker enterWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		checker.getEnv().enter(this, checker);
		return checker;
	}

	@Override
	public Do leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		/* // Factor out
		ModuleContext mcontext = ((CompoundInteractionContext) checker.peekContext()).getModuleContext();
		ProtocolName fullname = mcontext.getFullProtocolDeclName(doo.proto.toName());
		ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
				pd = checker.job.getContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());* /

		checker.getEnv().leave(this, checker);
		return this;
	}

	@Override
	public Do leaveReachabilityCheck(ReachabilityChecker checker) throws ScribbleException
	{
		checker.getEnv().leave(this, checker);
		return this;
	}
	
	/*@Override 
	public Do insertScopes(ScopeInserter si) throws ScribbleException
	{
		ScopeNode scope = this.scope;
		if (hasEmptyScopeNode())
		{
			//scope = si.createFreshScopeName();  // No: forces implementation level to use scopes for subprotocols -- yes: needed
			scope = si.createEmptyScopeName();
		}
		RoleInstantiationList ril = (RoleInstantiationList) si.visit(this.ril);
		ArgumentInstantiationList al = (ArgumentInstantiationList) si.visit(this.ail);
		return new Do(this.ct, scope, ril, al, this.cn);
	}
	
	@Override
	public Env enter(EnvVisitor nv) throws ScribbleException
	{
		Env env = super.enter(nv);
		ProtocolDecl pd = getTargetProtocolDecl(env);
		// FIXME: move bound roles etc check to an earlier pass before any substition passes
		if (pd.rdl.length() != this.ril.length())  // Also checked in RoleInstantitionList well-formedness, but need some guards here for pushDo (substitution/protocolsig)
		{
			throw new ScribbleException("Bad number of role arguments: " + this);
		}
		if (pd.pdl.length() != this.ail.length())
		{
			throw new ScribbleException("Bad number of parameter arguments: " + this);
		}

		// Duplicated form GlobalInterruptible
		if (nv instanceof WellFormednessChecker)  // Hacky
		{
			Scope scope = this.scope.toName();
			if (env.scopes.seenScope(scope))
			{
				throw new ScribbleException("Duplicate scope: " + scope);
			}
		}
		
		/* // FIXME: HACK  could branch-roles checking into a later pass
		//env.roles.hack.remove(getFullTargetProtocolName(env));
		ProtocolName pn = getFullTargetProtocolName(env);
		if (!env.roles.hack1.contains(pn))
		{
			env.roles.hack1.add(pn);
			env.roles.hack2.put(pn, new RolesEnv(env));
		}*
		/*ProtocolName pn = getFullTargetProtocolName(env);  // FIXME:  move after push -- actually move into pushDo
		if (!env.decls.isRecorded(pn))
		{
			env.decls.enter(pn, env);
		}*


		return env.pushDo(this);
	}
	
	@Override
	public Do leave(EnvVisitor nv) throws ScribbleException
	{
		Do gd = (Do) super.leave(nv);
		
		
		/* // HACK
		Env env = nv.getEnv();
		ProtocolName pn = getFullTargetProtocolName(env);
		if (env.roles.hack1.contains(pn))
		{
			env.roles.hack1.remove(pn);
		}
		if (env.stack.isCycle())
		{
			RolesEnv tmp = env.roles.hack2.get(pn);
			for (Role role : tmp.getEnabled())
			{
				if (!env.roles.isRoleEnabled(role))
				{
					for (Operator op : tmp.getEnabling(role))
					{
						env.roles.enableRole(role, op);
					}
				}
			}
		}*/
		//Env env = new Env(nv.getEnv());  // FIXME: move into popDo
		/*Env env = nv.getEnv();
		ProtocolName pn = getFullTargetProtocolName(env);
		if (env.decls.isRecording(pn))
		{
			env.decls.leave(pn, env);
		}*

		
		nv.setEnv(nv.getEnv().popDo(gd));
		return gd;
	}

	public Do visitTarget(EnvVisitor nv) throws ScribbleException
	{
		Env env = nv.getEnv();
		if (!env.stack.isCycle())
		{
			ProtocolName pn = getFullTargetProtocolName(env);
			ModuleName fmn = pn.getModule();

			ProtocolDecl pd = getTargetProtocolDecl(env);
			Substitutor subs = new Substitutor(env.job, this.ril, pd.rdl, this.ail, pd.pdl);
			Node substituted = subs.visit(pd.getBody());

			// FIXME: factor out visibility manipulation
			Visibility vis = new Visibility(env.job, env.job.getModule(fmn));
			Env tmp = new Env(env, vis);
			nv.setEnv(tmp);
			nv.visit(substituted);  // Throws away resulting node of visited subprotocols -- only changes to the root are saved back to job
			//env = new Env(tmp, env.visibility);
			env = new Env(nv.getEnv(), env.visibility);
			nv.setEnv(env);
		}
		return this;
	}
	
	@Override
	public Do substitute(Substitutor subs) throws ScribbleException
	{
		RoleInstantiationList ril = (RoleInstantiationList) subs.visit(this.ril);
		ArgumentInstantiationList ail = (ArgumentInstantiationList) subs.visit(this.ail);
		return new Do(this.ct, this.scope, ril, ail, this.cn);
	}*/

	/*// Make public
	private boolean hasEmptyScope()
	{
		return isScopeNodeEmpty() || this.scope.toName().equals(Scope.EMPTY_SCOPE);
	}*/
	
	/*public ProtocolName getFullTargetProtocolName(Env env) throws ScribbleException
	{
		//return new ProtocolName(this.cn.compileName());
		throw new RuntimeException("To be overridden: " + this.cn.compileName());
	}(/
	
	public ProtocolDecl getTargetProtocolDecl(Env env) throws ScribbleException
	{
		throw new RuntimeException("Should be overridden: " + getFullTargetProtocolName(env));
		//throw OperationNotSupportedException("Should be overridden: " + this);
	}*/
}
