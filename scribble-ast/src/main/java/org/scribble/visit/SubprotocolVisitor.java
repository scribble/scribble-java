/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.visit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScopedNode;
import org.scribble.ast.ScribNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.lang.SubprotoSig;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.NonRoleArgKind;
import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.Name;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.name.Scope;
import org.scribble.core.type.session.Arg;
import org.scribble.lang.Lang;
import org.scribble.lang.LangContext;
import org.scribble.util.ScribException;
import org.scribble.visit.env.Env;

public abstract class SubprotocolVisitor<T extends Env<?>> extends EnvVisitor<T>
{
	protected List<SubprotoSig> stack = new LinkedList<>();
	
	// name in the current protocoldecl scope -> the original name node in the root protocol decl
	protected Stack<Map<Role, RoleNode>> rolemaps = new Stack<>();
	protected Stack<Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode>> argmaps = new Stack<>();
	
	private Scope scope = null;

	public SubprotocolVisitor(Lang job)
	{
		super(job);
	}
	
	// Pushes a subprotocol signature (i.e. on root entry)
	protected void enterRootProtocolDecl(ProtocolDecl<?> pd)
	{
		ProtocolHeader<?> hdr = pd.getHeaderChild();
		RoleDeclList rds = hdr.getRoleDeclListChild();
		NonRoleParamDeclList pds = hdr.getParamDeclListChild();
		
		Map<Role, RoleNode> rolemap = makeRootRoleSubsMap(rds);
		Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> argmap = makeRootNonRoleSubsMap(
				pds);
		this.rolemaps.push(rolemap);
		this.argmaps.push(argmap);
		
		ModuleContext mcontext = getModuleContext();
		ProtocolName<?> fullname = mcontext
				.getVisibleProtocolDeclFullName(hdr.getDeclName());
		List<Role> roleargs = rds.getRoles();
		List<Arg<? extends NonRoleParamKind>> nonroleargs =
				pds.getParamDeclChildren().stream().map(param -> paramDeclToArg(param))
						.collect(Collectors.toList());
		pushSubprotocolSig(fullname, roleargs, nonroleargs);
	}

	// Most subclasses will override visitForSubprotocols (e.g. ReachabilityChecker, FsmConstructor), but sometimes still want to change whole visit pattern (e.g. Projector)
	@Override
	public ScribNode visit(ScribNode parent, ScribNode child)
			throws ScribException
	{
		enter(parent, child);
		ScribNode visited = visitForSubprotocols(parent, child);
		return leave(parent, child, visited);
	}

	// Subclasses can override this to disable subprotocol visiting
	protected ScribNode visitForSubprotocols(ScribNode parent, ScribNode child)
			throws ScribException
	{
		if (child instanceof Do)
		{
			return visitOverrideForDo(parent, (Do<?>) child);	// parent is InteractionSequence
		}
		else
		{
			return child.visitChildren(this); 
					// The base (super) behaviour (could factor it out in ModelVisitor as its own visitor method)
		}
	}
	
	// The Do node itself is no longer visited -- FIXME: projection needs to visit it -- no: that's in enter/leave, visited means "visit children"
	protected Do<?> visitOverrideForDo(ScribNode parent, Do<?> doo)
			throws ScribException
	{
		if (!isCycle())
		{
			LangContext jc = this.job.getContext();
			ModuleContext mc = getModuleContext();
			ProtocolDecl<? extends ProtocolKind> pd = 
					doo.getTargetProtocolDecl(jc, mc);
			// Target is cloned: fresh dels and envs, which will be discarded
			ScribNode seq = applySubstitutions(pd.getDefChild().getBlockChild()
					.getInteractSeqChild().clone()); //this.job.af));
					// Visit the seq? -- or visit the interactions in the seq directly?
					// Visit seq/interactions under current environment
			seq.accept(this);  // Result from visiting subprotocol body is discarded
		}
		return doo;
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
			return n.accept(
					new Substitutor(this.job, this.rolemaps.peek(), this.argmaps.peek()));
		}
		catch (ScribException e)
		{
			throw new RuntimeException("Shouldn't get in here: " + n);
		}
	}

	@Override
	protected final void envEnter(ScribNode parent, ScribNode child)
			throws ScribException
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
	protected final ScribNode envLeave(ScribNode parent, ScribNode child,
			ScribNode visited) throws ScribException
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
	protected void envLeaveProtocolDeclOverride(ScribNode parent, ScribNode child,
			ScribNode visited) throws ScribException
	{
		leaveSubprotocol();
	}

	protected void subprotocolEnter(ScribNode parent, ScribNode child)
			throws ScribException
	{

	}

	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child,
			ScribNode visited) throws ScribException
	{
		return visited;
	}

	private void enterSubprotocol(Do<?> doo)
	{
		ProtocolName<?> fullname = 
				getModuleContext().checkProtocolDeclDependencyFullName(
						doo.getProtocolNameNode().toName());
			// namedisamb should already have converted proto to the fullname -- in order for inlining to work
		List<Arg<? extends NonRoleParamKind>> cast = doo.getNonRoleListChild()
				.getParamKindArgs();
				// namedisamb should also ensure no ambigs in args
		pushSubprotocolSig(fullname, doo.getRoleListChild().getRoles(), cast);
		pushNameMaps(fullname, doo);
	}

	// Also used for leaving root protocoldecl, for convenience
	private void leaveSubprotocol()
	{
		this.stack.remove(this.stack.size() - 1);
		this.rolemaps.pop();
		this.argmaps.pop();
	}
	
	private void pushSubprotocolSig(ProtocolName<?> fullname, List<Role> roleargs,
			List<Arg<? extends NonRoleParamKind>> nonroleargs)
	{
		SubprotoSig subsig = new SubprotoSig(fullname, roleargs, nonroleargs);
		this.stack.add(subsig);
	}
	
	private void pushNameMaps(ProtocolName<?> fullname, Do<?> doo)
	{
		ProtocolDecl<?> pd = this.job.getContext().getModule(fullname.getPrefix())
				.getProtocolDeclChild(fullname.getSimpleName());
		ProtocolHeader<?> hdr = pd.getHeaderChild();
		this.rolemaps.push(makeRoleSubsMap(this.rolemaps.get(0),
				doo.getRoleListChild(), hdr.getRoleDeclListChild()));
		this.argmaps.push(makeNonRoleSubsMap(this.argmaps.get(0),
				doo.getNonRoleListChild(), hdr.getParamDeclListChild()));
	}
	
	public boolean isRootedCycle()
	{
		return this.stack.size() > 1
				&& this.stack.get(0).equals(this.stack.get(this.stack.size() - 1));
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
			SubprotoSig last = this.stack.get(size - 1);
			for (int i = size - 2; i >= 0; i--)
			{
				if (this.stack.get(i).equals(last))  // TODO CHECKME: doesn't support recursive scoped subprotocols, i.e. cycle detection not general enough?
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	public List<SubprotoSig> getStack()
	{
		return this.stack;
	}

	public boolean isStackEmpty()
	{
		return this.stack.isEmpty();
	}

	public SubprotoSig peekStack()
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

	protected static Map<Role, RoleNode> makeRootRoleSubsMap(RoleDeclList rdl)
	{
		return rdl.getParamDeclChildren().stream().collect(Collectors
				.toMap(r -> r.getDeclName(), r -> (RoleNode) r.getNameNodeChild()));
	}
	
	protected static Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> makeRootNonRoleSubsMap(
			NonRoleParamDeclList pdl)
	{
		return pdl.getParamDeclChildren().stream()
				.collect(Collectors.toMap(p -> (Arg<?>) p.getDeclName(),
						p -> (NonRoleArgNode) p.getNameNodeChild()));
	}

	protected static Map<Role, RoleNode> makeRoleSubsMap(Map<Role, RoleNode> root,
			RoleArgList ral, RoleDeclList rdl)
	{
		Iterator<Role> roleargs = ral.getRoles().iterator();
		return rdl.getRoles().stream()
				.collect(Collectors.toMap(r -> r, r -> root.get(roleargs.next())));
	}

	protected static Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> makeNonRoleSubsMap(
			Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> root,
			NonRoleArgList nral, NonRoleParamDeclList nrpdl)
	{
		Map<Arg<? extends NonRoleArgKind>, NonRoleArgNode> newmap = new HashMap<>();

		// Using arg and argnode views of the arglist
		Iterator<Arg<? extends NonRoleArgKind>> nonroleargs = nral.getArguments()
				.iterator();
		Iterator<NonRoleArgNode> nonroleargnodes = nral.getArgumentNodes()
				.iterator();
		for (Name<? extends NonRoleParamKind> param : nrpdl.getParameters())
		{
			Arg<?> arg = nonroleargs.next();
			NonRoleArgNode argnode;
			if (root.containsKey(arg))  // A root param propagated through to here as an arg: get the root argnode
			{
				argnode = root.get(arg);
				nonroleargnodes.next();
			}
			else
			{
				argnode = nonroleargnodes.next();  // The argnode correspoding to the current arg
			}
			newmap.put((Arg<?>) param, argnode);
		}

		return newmap;
	}
	
	private static Arg<NonRoleParamKind> paramDeclToArg(
			NonRoleParamDecl<NonRoleParamKind> pd)
	{
		Name<NonRoleParamKind> n = pd.getDeclName();
		if (!(n instanceof Arg))
		{
			throw new RuntimeException("Shouldn't get in here: " + n);
		}
		@SuppressWarnings("unchecked")
		Arg<NonRoleParamKind> tmp = (Arg<NonRoleParamKind>) n;
		return tmp;
	}
}
