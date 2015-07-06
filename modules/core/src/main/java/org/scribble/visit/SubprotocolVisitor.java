package org.scribble.visit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScopedNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.NonRoleArgKind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.sesstype.name.Scope;
import org.scribble.visit.env.Env;


// FIXME: factor out role/argmap access methods with OffsetSubprotocolVisitor
public abstract class SubprotocolVisitor<T extends Env> extends EnvVisitor<T>
{
	protected List<SubprotocolSig> stack = new LinkedList<>();
	
	// name in the current protocoldecl scope -> the original name node in the root protocol decl
	protected Stack<Map<Role, RoleNode>> rolemaps = new Stack<>();
	protected Stack<Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode>> argmaps = new Stack<>();
	
	private Scope scope = null;

	public SubprotocolVisitor(Job job)
	{
		super(job);
	}
	
	// Pushes a subprotocol signature
	protected void enterRootProtocolDecl(ProtocolDecl<? extends ProtocolKind> pd)
	{
		Map<Role, RoleNode> rolemap =
				pd.header.roledecls.getRoleDecls().stream()
						.collect(Collectors.toMap((r) -> r.getDeclName(), (r) -> (RoleNode) r.name));
		Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> argmap =
				pd.header.paramdecls.getParamDecls().stream()
						.collect(Collectors.toMap((p) -> (Arg<?>) p.getDeclName(), (p) -> (NonRoleArgNode) p.name));
		this.rolemaps.push(rolemap);
		this.argmaps.push(argmap);
		
		ModuleContext mcontext = getModuleContext();
		ProtocolName<? extends ProtocolKind> fullname = mcontext.getFullProtocolDeclName(pd.header.getDeclName());
		List<Role> roleargs = pd.header.roledecls.getRoles();
		List<Arg<? extends NonRoleArgKind>> argargs =
				pd.header.paramdecls.getParamDecls().stream().map((param) -> paramDeclToArg(param)).collect(Collectors.toList());
		pushSubprotocolSig(fullname, roleargs, argargs);
	}
	
	private static Arg<? extends NonRoleArgKind> paramDeclToArg(NonRoleParamDecl<NonRoleParamKind> pd)
	{
		Name<NonRoleParamKind> n = pd.getDeclName();
		if (!(n instanceof Arg))
		{
			throw new RuntimeException("Shouldn't get in here: " + n);
		}
		@SuppressWarnings("unchecked")
		Arg<? extends NonRoleArgKind> tmp = (Arg<? extends NonRoleArgKind>) n;
		return tmp;
	}

	// Most subclasses will override visitForSubprotocols (e.g. ReachabilityChecker, FsmConstructor), but sometimes still want to change whole visit pattern (e.g. Projector)
	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		enter(parent, child);
		ScribNode visited = visitForSubprotocols(parent, child);
		return leave(parent, child, visited);
	}

	// Subclasses can override this to disable subprotocol visiting
	protected ScribNode visitForSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Do)
		{
			return visitOverrideForDo(parent, (Do<?>) child);	// parent is InteractionSequence
		}
		else
		{
			return child.visitChildren(this);  // The base (super) behaviour (could factor it out in ModelVisitor as its own visitor method)
		}
	}
	
	// The Do node itself is no longer visited -- FIXME: projection needs to visit it -- no: that's in enter/leave, visited means "visit children"
	private Do<? extends ProtocolKind> visitOverrideForDo(ScribNode parent, Do<? extends ProtocolKind> doo) throws ScribbleException
	{
		if (!isCycle())
		{
			ModuleContext mcontext = getModuleContext();
			ProtocolDecl<? extends ProtocolKind> pd = doo.getTargetProtocolDecl(getJobContext(), mcontext);
			// Target is cloned: fresh dels and envs, which will be discarded
			ScribNode seq = applySubstitutions(pd.def.block.seq.clone());  // Visit the seq? -- or visit the interactions in the seq directly? ()
			seq.accept(this);  // Result from visiting subprotocol body is discarded
		}
		return doo;
	}

	@Override
	protected final void envEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.envEnter(parent, child);

		if (child instanceof ProtocolDecl)
		{
			setScope(Scope.ROOT_SCOPE);
			enterRootProtocolDecl((ProtocolDecl<?>) child);
		}
		if (child instanceof ScopedNode)
		{
			ScopedNode sn = (ScopedNode) child;
			if(!sn.isEmptyScope())
			{
				setScope(new Scope(getScope(), sn.getScopeElement()));
			}
		}
		if (child instanceof Do)
		{
			enterSubprotocol((Do<?>) child);  // Scope already pushed
		}
		subprotocolEnter(parent, child);
	}

	@Override
	protected final ScribNode envLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		ScribNode n = subprotocolLeave(parent, child, visited);
		if (child instanceof ProtocolDecl)
		{
			envLeaveProtocolDeclOverride(parent, child, visited);
		}
		if (child instanceof Do)  // child or visited/n?
		{
			leaveSubprotocol();
		}
		if (child instanceof ScopedNode && !((ScopedNode) child).isEmptyScope())
		{
			setScope(getScope().getPrefix());
		}
		return super.envLeave(parent, child, n);
	}
	
	// Hack for OffsetSubprotocolVisitor
	protected void envLeaveProtocolDeclOverride(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		leaveSubprotocol();
	}

	protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{

	}

	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		return visited;
	}

	// proto is full name
	private void enterSubprotocol(Do<? extends ProtocolKind> doo)
	{
		ModuleContext mcontext = getModuleContext();
		ProtocolName<? extends ProtocolKind> fullname = mcontext.getFullProtocolDeclName(doo.proto.toName());
		List<Role> roleargs = doo.roles.getRoles();
		List<Arg<? extends NonRoleArgKind>> argargs = doo.args.getArguments();
		pushSubprotocolSig(fullname, roleargs, argargs);
		pushNameMaps(fullname, doo, roleargs, argargs);
	}
	
	private void pushSubprotocolSig(ProtocolName<? extends ProtocolKind> fullname, List<Role> roleargs, List<Arg<? extends NonRoleArgKind>> argargs)
	{
		List<Role> roles = new LinkedList<>(roleargs);
		List<Arg<? extends Kind>> args = new LinkedList<>(argargs);
		SubprotocolSig ssubsig = new SubprotocolSig(fullname, roles, args);
		this.stack.add(ssubsig);
	}
	
	// FIXME: doo param hack
	private void pushNameMaps(ProtocolName<? extends ProtocolKind> fullname, Do<? extends ProtocolKind> doo, List<Role> roleargs, List<Arg<? extends NonRoleArgKind>> argargs)
	{
		ProtocolDecl<? extends ProtocolKind>
				pd = getJobContext().getModule(fullname.getPrefix()).getProtocolDecl(fullname.getSimpleName());
		List<Role> roleparams = pd.header.roledecls.getRoles();
		List<Name<NonRoleParamKind>> argparams = pd.header.paramdecls.getParameters();
		
		Iterator<Role> roleargiter = roleargs.iterator();
		Iterator<Arg<? extends NonRoleArgKind>> argargiter = argargs.iterator();
		Map<Role, RoleNode> newrolemap =
				roleparams.stream()
				.collect(Collectors.toMap((r) -> r, (r) -> this.rolemaps.get(0).get(roleargiter.next())));
		Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> newargmap = new HashMap<>();
		Iterator<NonRoleArgNode> foo = doo.args.getArgumentNodes().iterator();
		for (Name<NonRoleParamKind> p : argparams)
		{
			Arg<? extends Kind> tmp = argargiter.next();
			NonRoleArgNode a;
			if (this.argmaps.get(0).containsKey(tmp))
			{
				a = this.argmaps.get(0).get(tmp);
				foo.next();
			}
			else
			{
				a = foo.next();
			}
			newargmap.put((Arg<?>) p, a);
		}
		this.rolemaps.push(newrolemap);
		this.argmaps.push(newargmap);
	}

	protected void leaveSubprotocol()
	{
		this.stack.remove(this.stack.size() - 1);
		this.rolemaps.pop();
		this.argmaps.pop();
	}
	
	public boolean isCycle()
	{
		return getCycleEntryIndex() != -1;
	}

	public int getCycleEntryIndex()
	{
		int size = this.stack.size();
		if (size > 1)
		{
			SubprotocolSig last = this.stack.get(size - 1);
			for (int i = size - 2; i >= 0; i--)
			{
				if (this.stack.get(i).equals(last))  // FIXME: doesn't support recursive scoped subprotocols
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	protected boolean overrideSubstitution()
	{
		return false;
	}
	
	protected ScribNode applySubstitutions(ScribNode n)
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
	
	public List<SubprotocolSig> getStack()
	{
		return this.stack;
	}

	public boolean isStackEmpty()
	{
		return this.stack.isEmpty();
	}

	// FIXME: returning scoped sigs, from which sometimes just the unscoped part is needed (e.g. WF choice checking), is a tricky interface
	public SubprotocolSig peekStack()
	{
		return this.stack.get(this.stack.size() - 1);
	}
	
	public Scope getScope()
	{
		return this.scope;
	}

	protected void setScope(Scope scope)
	{
		this.scope = scope;
	}
}
