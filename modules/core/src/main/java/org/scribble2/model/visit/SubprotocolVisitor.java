package org.scribble2.model.visit;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.scribble2.model.ArgumentNode;
import org.scribble2.model.Do;
import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Module;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.ScopedNode;
import org.scribble2.model.del.ModuleDelegate;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.ScopedSubprotocolSignature;
import org.scribble2.sesstype.name.Parameter;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.sesstype.name.Scope;
import org.scribble2.util.ScribbleException;

public abstract class SubprotocolVisitor extends ModelVisitor
{
	private ModuleDelegate mcontext;  // Factor up to ModelVisitor? (will be null before context building)
	
	private List<ScopedSubprotocolSignature> stack = new LinkedList<>();
	
	// name in the current protocoldecl scope -> the original name node in the root protocol decl
	private Stack<Map<Role, RoleNode>> rolemaps = new Stack<>();
	private Stack<Map<Argument, ArgumentNode>> argmaps = new Stack<>();
	
	private Scope scope = null;

	public SubprotocolVisitor(Job job)
	{
		super(job);
	}
	
	//.. do arguments for subprotocol sigs .. substitutions ..

	// Doesn't push a subprotocol signature; only records the roles/args
	// proto is fullname
	public void enterRootProtocolDecl(ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		//ProtocolName fullname = pd.getFullProtocolName(getModuleContext().root);
		/*List<Role> roleparams = pd.roledecls.getRoles();
		List<Parameter> argparams = pd.paramdecls.getParameters();
		Map<Role, Role> rolemap = roleparams.stream().collect(Collectors.toMap((r) -> r, (r) -> r));
		Map<Argument, Argument> argmap = argparams.stream().collect(Collectors.toMap((a) -> a, (a) -> a));*/

		Map<Role, RoleNode> rolemap = pd.header.roledecls.decls.stream().collect(Collectors.toMap((r) -> r.toName(), (r) -> r.name));
		Map<Argument, ArgumentNode> argmap = pd.header.paramdecls.decls.stream().collect(Collectors.toMap((p) -> p.toName(), (p) -> p.name));
		this.rolemaps.push(rolemap);
		this.argmaps.push(argmap);

		//System.out.println("\n0: " + Arrays.asList(fullname.getElements()));
	}

	@Override
	//protected final SubprotocolVisitor enter(ModelNode parent, ModelNode child) throws ScribbleException
	protected final void enter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//SubprotocolVisitor spv = (SubprotocolVisitor) super.enter(parent, child);
		super.enter(parent, child);

		if (child instanceof Module)  // Factor out?
		{
			this.mcontext = (ModuleDelegate) ((Module) child).del();
		}
		if (child instanceof ProtocolDecl)
		{
			//spv.setScope(Scope.ROOT_SCOPE);
			setScope(Scope.ROOT_SCOPE);
		}
		if (child instanceof ScopedNode)
		{
			ScopedNode sn = (ScopedNode) child;
			if(!sn.isEmptyScope())
			{
				//spv.setScope(new Scope(spv.getScope(), sn.getScopeElement()));
				setScope(new Scope(getScope(), sn.getScopeElement()));
			}
		}
		if (child instanceof Do)
		{
			//spv.enterSubprotocol((Do) child);  // Scope already pushed
			enterSubprotocol((Do) child);  // Scope already pushed
		}
		//return spv.subprotocolEnter(parent, child);
		subprotocolEnter(parent, child);
	}

	@Override
	//protected final ModelNode leave(ModelNode parent, ModelNode child, ModelVisitor nv, ModelNode visited) throws ScribbleException
	protected final ModelNode leave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		//ModelNode n = super.leave(parent, child, nv, visited);
		//ModelNode n = subprotocolLeave(parent, child, (SubprotocolVisitor) nv, visited);
		ModelNode n = subprotocolLeave(parent, child, visited);
		if (child instanceof Do)  // child or visited/n?
		{
			leaveSubprotocol();
		}
		if (child instanceof ScopedNode && !((ScopedNode) child).isEmptyScope())
		{
			//this.scope.remove(this.scope.size() - 1);
			setScope(getScope().getPrefix());
		}
		//return subprotocolLeave(parent, child, nv, n);
		//return n;
		return super.leave(parent, child, n);
	}

	@Override
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		/*SubprotocolVisitor spv = (SubprotocolVisitor) enter(parent, child);
		ModelNode visited = child.visitChildrenInSubprotocols(spv);
		return leave(parent, child, spv, visited);*/
		enter(parent, child);
		ModelNode visited = child.visitChildrenInSubprotocols(this);
		return leave(parent, child, visited);
	}

	//protected SubprotocolVisitor subprotocolEnter(ModelNode parent, ModelNode child) throws ScribbleException
	protected void subprotocolEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//return this;
	}

	//protected ModelNode subprotocolLeave(ModelNode parent, ModelNode child, SubprotocolVisitor nv, ModelNode visited) throws ScribbleException
	protected ModelNode subprotocolLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		return visited;
	}

	// proto is full name
	//public void enterSubprotocol(ProtocolName proto, List<Role> roleargs, List<Argument> argargs)
	private void enterSubprotocol(Do doo)
	{
		//ModuleContext mcontext = ((CompoundInteractionContext) peekContext()).getModuleContext();
		ModuleDelegate mcontext = getModuleDelegate();
		ProtocolName fullname = mcontext.getFullProtocolDeclName(doo.proto.toName());
		List<Role> roleargs = doo.roleinstans.getRoles();
		List<Argument> argargs = doo.arginstans.getArguments();
		
		pushSubprotocolSignature(fullname, roleargs, argargs);
		//pushSubprotocolSignature(fullname, roleargs, argargs);*/
		pushNameMaps(fullname, roleargs, argargs);
	}
	
	private void pushSubprotocolSignature(ProtocolName fullname, List<Role> roleargs, List<Argument> argargs)
	{
		Map<Role, RoleNode> rolemap = rolemaps.peek();
		Map<Argument, ArgumentNode> argmap = argmaps.peek();
		List<Role> roles = roleargs.stream().map((r) -> rolemap.get(r).toName()).collect(Collectors.toList());
		List<Argument> args = argargs.stream().map((a) -> argmap.get(a).toArgument()).collect(Collectors.toList());
		ScopedSubprotocolSignature ssubsig = new ScopedSubprotocolSignature(getScope(), fullname, roles, args);
		//ScopedSubprotocolSignature ssubsig = getSubprotocolSignature(fullname, roleargs, argargs);
		this.stack.add(ssubsig);
	}
	
	private void pushNameMaps(ProtocolName fullname, List<Role> roleargs, List<Argument> argargs)
	{
		ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
				pd = getJobContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
		List<Role> roleparams = pd.header.roledecls.getRoles();
		List<Parameter> argparams = pd.header.paramdecls.getParameters();
		
		Map<Role, RoleNode> rolemap = rolemaps.peek();
		Map<Argument, ArgumentNode> argmap = argmaps.peek();
		Iterator<Role> roleargiter = roleargs.iterator();
		Iterator<Argument> argargiter = argargs.iterator();
		Map<Role, RoleNode> newrolemap = roleparams.stream().collect(Collectors.toMap((r) -> r, (r) -> rolemap.get(roleargiter.next())));
		Map<Argument, ArgumentNode> newargmap = argparams.stream().collect(Collectors.toMap((a) -> a, (a) -> argmap.get(argargiter.next())));
		this.rolemaps.push(newrolemap);
		this.argmaps.push(newargmap);
	}

	private void leaveSubprotocol()
	{
		this.stack.remove(this.stack.size() - 1);
		this.rolemaps.pop();
		this.argmaps.pop();
	}
	
	public boolean isCycle()
	//public int isCycle()
	{
		return getCycleEntryIndex() != -1;
	}

	public int getCycleEntryIndex()
	{
		int size = this.stack.size();
		if (size > 1)
		{
			ScopedSubprotocolSignature last = this.stack.get(size - 1);
			for (int i = size - 2; i >= 0; i--)
			{
				if (this.stack.get(i).sig.equals(last.sig))
				{
					return i;
				}
			}
		}
		//throw new RuntimeException("No subprotocol cycle: " + this.stack);
		return -1;
	}
	
	/*public Substitutor getSubstitutor()
	{
		return new Substitutor(this.job, this.rolemaps.peek(), this.argmaps.peek());
	}*/
	
	protected boolean overrideSubstitution()
	{
		return false;
	}
	
	public ModelNode applySubstitutions(ModelNode n)
	{
		if (overrideSubstitution())
		{
			return n;
		}
		try
		{
			return n.accept(new Substitutor(getJob(), this.rolemaps.peek(), this.argmaps.peek()));
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + n);
		}
	}
	
	public List<ScopedSubprotocolSignature> getStack()
	{
		return this.stack;
	}

	public boolean isStackEmpty()
	{
		return this.stack.isEmpty();
	}

	// FIXME: returning scoped sigs, from which sometimes just the unscoped part is needed (e.g. WF choice checking), is a tricky interface
	public ScopedSubprotocolSignature peekStack()
	{
		return this.stack.get(this.stack.size() - 1);
	}
	
	/*public <T extends Node> T substitute(T n)
	{
		Node substituted = n.substitute(this.rolemaps.peek(), this.argmaps.peek());
		if (substituted.getClass() != n.getClass())
		{
			throw new RuntimeException("Substitution went wrong: " + substituted.getClass() + ", " + n.getClass());
		}
		@SuppressWarnings("unchecked")
		T t = (T) substituted;
		return t;
	}*/
	
	public Scope getScope()
	{
		return this.scope;
	}

	protected void setScope(Scope scope)
	{
		this.scope = scope;
	}
	
	//protected ModuleDelegate getModuleDelegate()
	public ModuleDelegate getModuleDelegate()
	{
		return this.mcontext;
	}
}
