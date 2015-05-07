package org.scribble2.model.visit;

import java.util.Stack;

import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNode;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.visit.env.Env;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.util.ScribbleException;

public abstract class EnvVisitor<T extends Env> extends SubprotocolVisitor
{
	private Stack<T> envs = new Stack<T>();
	//private Env env = null;  // Inconsistent setter pattern with NodeContextVisitor: there done implicitly, here done explicitly by setter
	
	//private Scope scope = null;
	
	public EnvVisitor(Job job)
	{
		super(job);
	}
	
	/*private boolean isNoEnvSet()
	{
		return this.env == null;
	}*/
	
	@Override
	//protected final EnvVisitor<T> subprotocolEnter(ModelNode parent, ModelNode child) throws ScribbleException
	protected final void subprotocolEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		////EnvVisitor<T> ev = (EnvVisitor) super.subprotocolEnter(parent, child);
		//EnvVisitor<T> ev = castEnvVisitor(super.subprotocolEnter(parent, child), this);
		super.subprotocolEnter(parent, child);
		if (child instanceof ProtocolDecl)  // Only the root ProtocolDecl is visited: subprotocols visit the body directly
		{
			//ev.setEnv(new Env(this.job.getContext(), (ModuleContext) peekContext()));
			//if (isNoEnvSet())

			@SuppressWarnings("unchecked")
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd = 
			ProtocolDecl<? extends ProtocolKind> pd = 
					//(AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>) child;
					(ProtocolDecl<? extends ProtocolKind>) child;
			//ev.setEnv(makeRootProtocolDeclEnv(pd));
			//ev.pushEnv(makeRootProtocolDeclEnv(pd));
			pushEnv(makeRootProtocolDeclEnv(pd));
			/*//ev.scope.add(Scope.ROOT_SCOPE);
			ev.setScope(Scope.ROOT_SCOPE);*/
		}
		/*if (child instanceof ScopedNode)
		{
			ScopedNode sn = (ScopedNode) child;
			if(!sn.isEmptyScope())
			{
				//ev.scope.add(sn.getScope());
				ev.setScope(new Scope(ev.getScope(), sn.getScopeElement()));
			}
		}*/
		
		//return ev.envEnter(parent, child);
		envEnter(parent, child);
	}
	
	// getProtocolDeclEnv
	protected abstract T makeRootProtocolDeclEnv(
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd);
			ProtocolDecl<? extends ProtocolKind> pd);

	//protected EnvVisitor<T> envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	protected void envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		
		//... HERE: push copy of parent Env onto visitor stack for use by visitor pass (del env-leave routine should pop and push back the final result)
		//... only if want an env for every node (unless restrict to specific nodes types -- e.g. interaction nodes) -- as opposed to only e.g. compound nodes, as done via del
		//... so either do base env management here or via del -- here, need to do instanceof; in delegates, need to duplicate base push/pop for each pass
		//... no: should be only compound interaction nodes, as typing environments
		
		//return this;
	}
	
	@Override
	//protected final ModelNode subprotocolLeave(ModelNode parent, ModelNode child, SubprotocolVisitor nv, ModelNode visited) throws ScribbleException
	protected final ModelNode subprotocolLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		//ModelNode n = envLeave(parent, child, (EnvVisitor) nv, visited); 
		//ModelNode n = envLeave(parent, child, castEnvVisitor(nv, this), visited); 
		//ModelNode n = envLeave(parent, child, this, visited); 
		ModelNode n = envLeave(parent, child, visited); 

		//ModelNode n = super.subprotocolLeave(parent, child, nv, visited);
		/*n = envLeave(parent, child, nv, n);
		if (n instanceof EnvNode)
		{
			((EnvNode) n).setEnv(this.env.copy());
		}
		return n;*/  // Not doing automatically for every Env -- has to be done explicitly for each EnvVisitor
		//if (isRootProtocolDeclEntry())
		if (n instanceof ProtocolDecl)  // Only the root ProtocolDecl is visited by SubprotocolVisitor (subprotocols visit the body directly)
		{
			//EnvVisitor ev = (EnvVisitor) nv;
			////ev.setEnv(ev.getEnv().getParent());
			////this.setEnv(ev.getEnv().getParent());
			this.envs.pop();
		}
		/*if (child instanceof ScopedNode && !((ScopedNode) child).isEmptyScope())
		{
			//this.scope.remove(this.scope.size() - 1);
			setScope(getScope().getPrefix());
		}*/

		//return super.subprotocolLeave(parent, child, nv, n);
		return super.subprotocolLeave(parent, child, n);
	}
	
	//protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor<T> nv, ModelNode visited) throws ScribbleException
	protected ModelNode envLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		/*if (hasEnv())
		{
			//setEnv(ev.peekEnv().copy());  // FIXME: need a deep copy for Env -- no: Env immutable
			((ModelDelegateBase) visited.del()).setEnv(peekEnv());  // no defensive copy of visited
		}*/

		return visited;
	}
	
	// Hack? e.g. for ModuleDecl
	public boolean hasEnv()
	{
		return !this.envs.isEmpty();
	}

	public T peekEnv()
	//public Env getEnv()
	{
		return this.envs.peek();
		//return this.env;
	}

	public T peekParentEnv()
	{
		return this.envs.get(this.envs.size() - 2);
	}
	
	//public void setEnv(Env env)
	public void pushEnv(T env)
	{
		//this.env = env;
		this.envs.push(env);
	}
	
	public T popEnv()
	{
		return this.envs.pop();
	}

	/*public void replaceEnv(Env env)
	{
		this.env = env;
	}*/
	
	/*public Scope getScope()
	{
		return this.scope;
	}

	protected void setScope(Scope scope)
	{
		this.scope = scope;
	}*/
	
	/*private static <T extends Env> EnvVisitor<T> castEnvVisitor(SubprotocolVisitor sv, EnvVisitor<T> ev)
	{
		if (sv.getClass() != ev.getClass())
		{
			throw new RuntimeException("SubprotocolVisitor to EnvVisitor cast error: " + sv.getClass() + ", " + ev.getClass());
		}
		@SuppressWarnings("unchecked")
		EnvVisitor<T> tmp = (EnvVisitor<T>) sv;
		return tmp;
	}*/
}
