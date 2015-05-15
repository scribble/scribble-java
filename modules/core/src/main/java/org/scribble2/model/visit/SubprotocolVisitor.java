package org.scribble2.model.visit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.scribble2.model.ArgumentNode;
import org.scribble2.model.Do;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Module;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ScopedNode;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.model.del.ModuleDel;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.Argument;
import org.scribble2.sesstype.SubprotocolSignature;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.sesstype.name.Scope;
import org.scribble2.util.ScribbleException;

public abstract class SubprotocolVisitor extends ModelVisitor
{
	//private ModuleDelegate mcontext;  // Factor up to ModelVisitor? (will be null before context building)
	private ModuleContext mcontext;  // The "root" module context (different than the front-end "main" module)  // Factor up to ModelVisitor? (will be null before context building)
	
	//private List<ScopedSubprotocolSignature> stack = new LinkedList<>();
	private List<SubprotocolSignature> stack = new LinkedList<>();
	
	// name in the current protocoldecl scope -> the original name node in the root protocol decl
	private Stack<Map<Role, RoleNode>> rolemaps = new Stack<>();
	private Stack<Map<Argument<? extends Kind>, ArgumentNode>> argmaps = new Stack<>();
	
	private Scope scope = null;

	public SubprotocolVisitor(Job job)
	{
		super(job);
	}
	
	//.. do arguments for subprotocol sigs .. substitutions ..

	// Doesn't push a subprotocol signature; only records the roles/args
	// proto is fullname
	//private void enterRootProtocolDecl(AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	private void enterRootProtocolDecl(ProtocolDecl<? extends ProtocolKind> pd)
	{
		//ProtocolName fullname = pd.getFullProtocolName(getModuleContext().root);
		/*List<Role> roleparams = pd.roledecls.getRoles();
		List<Parameter> argparams = pd.paramdecls.getParameters();
		Map<Role, Role> rolemap = roleparams.stream().collect(Collectors.toMap((r) -> r, (r) -> r));
		Map<Argument, Argument> argmap = argparams.stream().collect(Collectors.toMap((a) -> a, (a) -> a));*/

		Map<Role, RoleNode> rolemap = pd.header.roledecls.getRoleDecls().stream().collect(Collectors.toMap((r) -> r.getDeclName(), (r) -> (RoleNode) r.name));
		//Map<Argument, ArgumentNode> argmap = pd.header.paramdecls.decls.stream().collect(Collectors.toMap((p) -> (Argument) p.toName(), (p) -> (ArgumentNode) p.name));
		Map<Argument<? extends Kind>, ArgumentNode> argmap = pd.header.paramdecls.decls.stream().collect(Collectors.toMap((p) -> (Argument<? extends Kind>) p.getDeclName(), (p) -> (ArgumentNode) p.name));
		this.rolemaps.push(rolemap);
		this.argmaps.push(argmap);

		//System.out.println("\n0: " + Arrays.asList(fullname.getElements()));
	}

	@Override
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		/*SubprotocolVisitor spv = (SubprotocolVisitor) enter(parent, child);
		ModelNode visited = child.visitChildrenInSubprotocols(spv);
		return leave(parent, child, spv, visited);*/
		/*enter(parent, child);
		ModelNode visited = child.visitChildrenInSubprotocols(this);
		return leave(parent, child, visited);*/
		enter(parent, child);
		ModelNode visited = visitForSubprotocols(parent, child);
		return leave(parent, child, visited);
	}

	// Subclasses can override this to disable subprotocol visiting
	protected ModelNode visitForSubprotocols(ModelNode parent, ModelNode child) throws ScribbleException
	{
		if (child instanceof Do)
		{
			return visitOverrideForDo(parent, (Do<? extends ProtocolKind>) child);	// parent is InteractionSequence
		}
		else
		{
			return child.visitChildren(this);  // The base (super) behaviour (could factor it out in ModelVisitor as its own visitor method)
		}
	}
	
	// The Do node itself is no longer visited -- FIXME: projection needs to visit it -- no: that's in enter/leave, visited means "visit children"
	private Do<? extends ProtocolKind> visitOverrideForDo(ModelNode parent, Do<? extends ProtocolKind> doo) throws ScribbleException
	{
		if (!isCycle())
		//if (spv.isCycle() != -1)
		{
			//enter(parent, doo);  // need to enter/leave even if cycle, e.g. for projection
			
			ModuleContext mcontext = getModuleContext();
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
			ProtocolDecl<? extends ProtocolKind>
					//pd = spv.job.getContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());* /
					pd = doo.getTargetProtocolDecl(getJobContext(), mcontext);

			//... do wf-choice env building and checking
			//... scopes (all messages/operators are scoped?)
			
			ModelNode seq = applySubstitutions(pd.def.block.seq);  // Visit the seq? -- or visit the interactions in the seq directly? ()
			seq.accept(this);  // Result from visiting subprotocol body is discarded

			//leave(parent, doo, doo);  // the Do node itself was not visited; return from the subprotocol body is discarded
		}
		return doo;
	}

	@Override
	//protected final SubprotocolVisitor enter(ModelNode parent, ModelNode child) throws ScribbleException
	protected final void enter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		//SubprotocolVisitor spv = (SubprotocolVisitor) super.enter(parent, child);
		super.enter(parent, child);

		if (child instanceof Module)  // Factor out?
		{
			this.mcontext = ((ModuleDel) ((Module) child).del()).getModuleContext();
		}
		if (child instanceof ProtocolDecl)
		{
			//spv.setScope(Scope.ROOT_SCOPE);
			setScope(Scope.ROOT_SCOPE);

			enterRootProtocolDecl((ProtocolDecl<? extends ProtocolKind>) child);  // Doesn't push proto stack, just for root role/arg names
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
			enterSubprotocol((Do<? extends ProtocolKind>) child);  // Scope already pushed
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
		if (child instanceof ProtocolDecl)
		{
			this.rolemaps.pop();
			this.argmaps.pop();
		}
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
	private void enterSubprotocol(Do<? extends ProtocolKind> doo)
	{
		//ModuleContext mcontext = ((CompoundInteractionContext) peekContext()).getModuleContext();
		/*ModuleDelegate mcontext = getModuleDelegate().g;
		ProtocolName fullname = mcontext.getFullProtocolDeclName(doo.proto.toName());*/
		//ModuleDelegate mcontext = getModuleContext();
		ModuleContext mcontext = getModuleContext();
		ProtocolName<? extends ProtocolKind> fullname = mcontext.getFullProtocolName(doo.proto.toName());
		List<Role> roleargs = doo.roleinstans.getRoles();
		List<Argument<? extends Kind>> argargs = doo.arginstans.getArguments(getScope());
		pushSubprotocolSignature(fullname, roleargs, argargs);
		//pushSubprotocolSignature(fullname, roleargs, argargs);*/
		pushNameMaps(fullname, doo, roleargs, argargs);
	}
	
	private void pushSubprotocolSignature(ProtocolName<? extends ProtocolKind> fullname, List<Role> roleargs, List<Argument<? extends Kind>> argargs)
	{
		Map<Role, RoleNode> rolemap = rolemaps.peek();
		Map<Argument<? extends Kind>, ArgumentNode> argmap = argmaps.peek();
		/*List<Role> roles = roleargs.stream().map((r) -> rolemap.get(r).toName()).collect(Collectors.toList());
		List<Argument> args = argargs.stream().map((a) -> argmap.get(a).toArgument()).collect(Collectors.toList());*/
		List<Role> roles = new LinkedList<>(roleargs);
		List<Argument<? extends Kind>> args = new LinkedList<>(argargs);
		//ScopedSubprotocolSignature ssubsig = new ScopedSubprotocolSignature(getScope(), fullname, roles, args);
		//SubprotocolSignature ssubsig = new SubprotocolSignature(fullname, getScope(), roles, args);
		SubprotocolSignature ssubsig = new SubprotocolSignature(fullname, roles, args);
		//ScopedSubprotocolSignature ssubsig = getSubprotocolSignature(fullname, roleargs, argargs);
		this.stack.add(ssubsig);
	}
	
	// FIXME: doo param hack
	private void pushNameMaps(ProtocolName fullname, Do doo, List<Role> roleargs, List<Argument<? extends Kind>> argargs)
	{
		//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
		ProtocolDecl<? extends ProtocolKind>
				pd = getJobContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
		List<Role> roleparams = pd.header.roledecls.getRoles();
		//List<Parameter> argparams = pd.header.paramdecls.getParameters();
		List<Name<Kind>> argparams = pd.header.paramdecls.getParameters();
		
		Map<Role, RoleNode> rolemap = rolemaps.peek();
		Map<Argument<? extends Kind>, ArgumentNode> argmap = argmaps.peek();
		Iterator<Role> roleargiter = roleargs.iterator();
		Iterator<Argument<? extends Kind>> argargiter = argargs.iterator();
		/*Map<Role, RoleNode> newrolemap = roleparams.stream().collect(Collectors.toMap((r) -> r, (r) -> rolemap.get(roleargiter.next())));
		Map<Argument, ArgumentNode> newargmap = argparams.stream().collect(Collectors.toMap((a) -> a, (a) -> argmap.get(argargiter.next())));*/
		Map<Role, RoleNode> newrolemap = roleparams.stream().collect(Collectors.toMap((r) -> r, (r) -> this.rolemaps.get(0).get(roleargiter.next())));
		
		//..HERE: debug; and makeArgumentNode take kind parameter
		
		//Map<Argument<? extends Kind>, ArgumentNode> newargmap = argparams.stream().collect(Collectors.toMap((a) -> (Argument<? extends Kind>) a, (a) -> this.argmaps.get(0).get(argargiter.next())));
		Map<Argument<? extends Kind>, ArgumentNode> newargmap = new HashMap<>();
		Iterator<ArgumentNode> foo = doo.arginstans.getArgumentNodes().iterator();
		for (Name<Kind> p : argparams)
		{
			Argument<? extends Kind> tmp = argargiter.next();
			ArgumentNode a;
			if (this.argmaps.get(0).containsKey(tmp))
			{
				a = this.argmaps.get(0).get(tmp);
				foo.next();
			}
			else
			{
				a = foo.next();
			}
			newargmap.put((Argument<? extends Kind>) p, a);
		}
		
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
			//ScopedSubprotocolSignature last = this.stack.get(size - 1);
			SubprotocolSignature last = this.stack.get(size - 1);
			for (int i = size - 2; i >= 0; i--)
			{
				//if (this.stack.get(i).sig.equals(last.sig))
				if (this.stack.get(i).equals(last))  // FIXME: doesn't support recursive scoped subprotocols
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
	
	private ModelNode applySubstitutions(ModelNode n)
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
	
	//public List<ScopedSubprotocolSignature> getStack()
	public List<SubprotocolSignature> getStack()
	{
		return this.stack;
	}

	public boolean isStackEmpty()
	{
		return this.stack.isEmpty();
	}

	// FIXME: returning scoped sigs, from which sometimes just the unscoped part is needed (e.g. WF choice checking), is a tricky interface
	//public ScopedSubprotocolSignature peekStack()
	public SubprotocolSignature peekStack()
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
	/*public ModuleDelegate getModuleDelegate()
	{
		return this.mcontext;
	}*/

	public ModuleContext getModuleContext()
	{
		return this.mcontext;
	}
}
