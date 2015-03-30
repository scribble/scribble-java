package org.scribble2.model.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Module;
import org.scribble2.model.ParameterDeclList;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.RoleDeclList;
import org.scribble2.model.del.ProtocolDeclDelegate;
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.local.LocalProtocolDecl;
import org.scribble2.model.local.LocalProtocolDefinition;
import org.scribble2.model.local.LocalProtocolHeader;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

// Uses visitor infrastructure to traverse AST and generate local nodes from global with original nodes unchanged (so does not use normal visitChildren pattern -- env used to pass the working projections)
// FIXME: uses envs but does not need to be a SubProtocolVisitor -- swap env and subprotocol visitors in hierarchy? Maybe not: e.g. GraphBuilder is a subprotocol visitor but not an env visitor
public class Projector extends EnvVisitor
{
	private Stack<Role> selfs = new Stack<>();
	
	// first protocol name is full global protocol name, second is full local projection names
	private Map<ProtocolName, Map<Role, Module>> projections = new HashMap<>();
	
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	private Map<ProtocolName, Set<Role>> dependencies = new HashMap<>();
	
	// first protocol name is full root global protocol name, second is full global protocol dependency names (reflexive)
	//private Map<ProtocolName, Map<Role, Map<ProtocolName, Set<Role>>>> dependencies = new HashMap<>();

	public Projector(Job job)
	{
		super(job);
	}

	// Envs pushed for GlobalNode delegates (doesn't include interaction seqs)
	@Override
	protected ProjectionEnv makeRootProtocolDeclEnv(
			ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		//return new ProjectionEnv(this.getJobContext(), getModuleContext(), null);
		return new ProjectionEnv(this.getJobContext(), getModuleDelegate());
	}

	@Override
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		if (child instanceof GlobalProtocolDecl)
		{
			Projector proj = (Projector) enter(parent, child);
			//ModelNode visited = child.visitChildrenInSubprotocols(spv);  visitForProjection
			ModelNode visited = visitForProjection((Module) parent, (GlobalProtocolDecl) child);
			return leave(parent, child, proj, visited);
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	private GlobalProtocolDecl visitForProjection(Module parent, GlobalProtocolDecl child) throws ScribbleException
	{
		//ModuleDelegate md = getModuleDelegate();
		JobContext jc = getJobContext();
		Module mod = jc.getMainModule();

		ProtocolName gpn = child.getFullProtocolName(mod);
		Map<Role, Map<ProtocolName, Set<Role>>> dependencies = new HashMap<>();
		for (Role self : child.header.roledecls.getRoles())
		{
			pushSelf(self);
			//proj.addProtocolDependency(gpn, self, gpn, self);
			clearProtocolDependencies();
			addProtocolDependency(gpn, self);
			GlobalProtocolDecl gpd = (GlobalProtocolDecl) child.visitChildrenInSubprotocols(this);  // cf. SubprotocolVisitor.visit
			
			SimpleProtocolNameNode pn = makeProjectedLocalName(child.header.name.toName(), self);
			//RoleDeclList roledecls = (RoleDeclList) ((ProjectionEnv) gpd.roledecls.getEnv()).getProjection();
			RoleDeclList roledecls = gpd.header.roledecls.project(self);//peekSelf());
			//ParameterDeclList paramdecls = (ParameterDeclList) ((ProjectionEnv) gpd.paramdecls.getEnv()).getProjection();
			ParameterDeclList paramdecls = gpd.header.paramdecls.project(self);//peekSelf());
			LocalProtocolHeader lph = new LocalProtocolHeader(pn, roledecls, paramdecls);
			LocalProtocolDefinition def = (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.del().getEnv()).getProjection();
			LocalProtocolDecl lpd = new LocalProtocolDecl(lph, def);

			//pdcontext = new ProtocolDeclContext(getContext(), self, proj.getProtocolDependencies());  // FIXME: move dependency building to after initial context building (or integrate into it?)
			Map<ProtocolName, Set<Role>> deps = getProtocolDependencies();
			dependencies.put(self, deps);

			Module projected = mod.makeProjectedModule(this, lpd, deps);
			// store projections in projector? in context? do earlier with context building? (but subprotocol pattern not available there)* /
			addProjection(gpn, self, projected);
			
			System.out.println(self + ":\n" + projected);
			
			//.. add all dependencies to projection set (needs to be done after all modules have been visited) .. FIXME: dependencies need to be the sigs for the argument positions, not just the name -- or maybe ok to not, just be conservative
			//.. maybe just record all projections in one big store for reachability checking, projection set for a specific global protocol can be worked out form dependencies?
			
			popSelf();
		}
		//return this;
		//ProtocolDeclContext pdcontext = new ProtocolDeclContext(dependencies);
		ProtocolDeclDelegate del = new ProtocolDeclDelegate(dependencies);  // FIXME: move to leaveProjection in GlobalProtocolDecl
			
		//System.out.println("c: " + this.name + ", " + pdcontext.getProtocolDependencies());

		//return reconstruct(this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());
		return (GlobalProtocolDecl) child.del(del);  // del setter needs to be done here (access to collected dependencies) -- envLeave uses this new del (including Env setting)
	}
	
	@Override
	protected final Projector envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		/*Projector proj = (Projector) super.envEnter(parent, child);
		return proj;*/
		//return child.enterProjection(this);
		Projector proj = (Projector) super.envEnter(parent, child);
		return (Projector) child.del().enterProjection(parent, child, proj);
	}
	
	@Override
	protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor nv, ModelNode visited) throws ScribbleException
	{
		/*ModelNode n = super.envLeave(parent, child, nv, visited);
		return n.leaveProjection((Projector) nv);*/
		visited = visited.del().leaveProjection(parent, child, (Projector) nv, visited);
		return super.envLeave(parent, child, nv, visited);
	}
	
	// Simple projected protocol name for protocol decls -- Move into SimpleProtocolName?
	public SimpleProtocolNameNode makeProjectedLocalName(ProtocolName simplename, Role role)
	{
		return new SimpleProtocolNameNode(makeProjectedLocalNameAux(simplename.toString(), role.toString()));
	}

	// Role is the target subprotocol parameter (not the current projector self -- actually the self just popped)
	public static ProtocolNameNode makeProjectedProtocolNameNodes(ProtocolName fullname, Role role)
	{
		String simplename = makeProjectedLocalNameAux(fullname.getLastElement(), role.toString());
		String[] elems = fullname.getElements();
		String[] tmp = new String[elems.length];
		System.arraycopy(elems, 0, tmp, 0, elems.length - 2);
		tmp[tmp.length - 2] = makeProjectedModuleSimpleName(fullname.getPrefix().getSimpleName().toString(), simplename);
		tmp[tmp.length - 1] = simplename;
		return new ProtocolNameNode(tmp);
	}

	public static ProtocolName makeProjectedProtocolName(ProtocolName fullname, Role role)
	{
		String simplename = makeProjectedLocalNameAux(fullname.getLastElement(), role.toString());
		ModuleName mn = makeProjectedModuleName(fullname.getPrefix(), new ProtocolName(simplename));
		return new ProtocolName(mn, simplename);
	}

	// Factor out with above
	// fullname is the un-projected name; localname is the already projected simple name
	public static ModuleNameNode makeProjectedModuleNameNodes(ModuleName fullname, ProtocolName localname)
	{
		String[] elems = fullname.getElements();
		String[] tmp = new String[elems.length];
		System.arraycopy(elems, 0, tmp, 0, elems.length);
		tmp[tmp.length - 1] = makeProjectedModuleSimpleName(fullname.getSimpleName().toString(), localname.toString());
		return new ModuleNameNode(tmp);
	}

	public static ModuleName makeProjectedModuleName(ModuleName fullname, ProtocolName localname)
	{
		String tmp = makeProjectedModuleSimpleName(fullname.getSimpleName().toString(), localname.toString());
		return new ModuleName(fullname.getPrefix(), tmp);
	}

	private static String makeProjectedLocalNameAux(String fullglobalname, String role)
	{
		//return fullglobalname + "_" + peekSelf().toString();
		return fullglobalname + "_" + role;
	}

	// simplemodname is un-projected; localname is the already projected simple local name
	private static String makeProjectedModuleSimpleName(String simplemodname, String localname)
	{
		return simplemodname + "_" + localname.toString();
	}

	/*@Override
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		Projector spv = (Projector) enter(parent, child);
		ModelNode visited = child.visitForProjection(spv);
		return leave(parent, child, spv, visited);
	}*/
	
	public void pushSelf(Role self)
	{
		this.selfs.push(self);
	}
	
	public Role peekSelf()
	{
		return this.selfs.peek();
	}
	
	public Role popSelf()
	{
		return this.selfs.pop();
	}
	
	// finally store projections in ProtocolDecl contexts
	
	/*@Override
	protected boolean overrideSubstitution()
	{
		return true;
	}*/
	
	// gpn is full global protocol name, mod is a module with the single local protocol projection
	public void addProjection(ProtocolName gpn, Role role, Module mod)
	{
		/*try  // Inconvenient: protocol decls not available/visible
		{
			NodeContextBuilder builder = new NodeContextBuilder(this.job);
			mod = (Module) mod.visit(builder);
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException(e);
		}*/
		
		Map<Role, Module> tmp = this.projections.get(gpn);
		if (tmp == null)
		{
			tmp = new HashMap<>();
			this.projections.put(gpn, tmp);
		}
		tmp.put(role, mod);

		//System.out.println("a: \n" + mod + "\n\n");
	}

	public Map<ProtocolName, Map<Role, Module>> getProjections()
	{
		return this.projections;
	}
	
	public void clearProtocolDependencies()
	{
		//this.dependencies.clear();  // clears the reference obtained from getProtocolDependencies  // maybe we/client should make a defensive copy
		this.dependencies = new HashMap<>();
	}

	public void addProtocolDependency(ProtocolName gpn, Role role)
	{
		Set<Role> tmp = this.dependencies.get(gpn);
		if (tmp == null)
		{
			tmp = new HashSet<>();
			this.dependencies.put(gpn, tmp);
		}
		tmp.add(role);
		
		//System.out.println("b: " + gpn + ", " + role);
	}

	public Map<ProtocolName, Set<Role>> getProtocolDependencies()
	{
		return this.dependencies;
	}
	
	@Override
	public ProjectionEnv peekEnv()
	{
		return (ProjectionEnv) super.peekEnv();
	}

	@Override
	public ProjectionEnv peekParentEnv()
	{
		return (ProjectionEnv) super.peekParentEnv();
	}
	
	@Override
	public ProjectionEnv popEnv()
	{
		return (ProjectionEnv) super.popEnv();
	}

	/*public void addProtocolDependency(ProtocolName root, Role self, ProtocolName dep, Role param)
	{
		Map<Role, Map<ProtocolName, Set<Role>>> tmp1 = this.dependencies.get(root);
		if (tmp1 == null)
		{
			tmp1 = new HashMap<>();
			this.dependencies.put(root, tmp1);
		}
		Map<ProtocolName, Set<Role>> tmp2 = tmp1.get(self);
		if (tmp2 == null)
		{
			tmp2 = new HashMap<>();
			tmp1.put(self, tmp2);
		}
		Set<Role> tmp3 = tmp2.get(dep);
		if (tmp3 == null)
		{
			tmp3 = new HashSet<>();
		}
		tmp3.add(param);
		
		System.out.println("b: " + root + ", " + self + ", " + dep + ", " + param);
	}
	
	public Map<ProtocolName, Set<Role>> getProtocolDependencies(ProtocolName root, Role self)
	{
		return this.dependencies.get(root).get(self);
	}*/
}
