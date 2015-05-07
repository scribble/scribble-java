package org.scribble2.model.visit;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ModelFactory;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Module;
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

// Uses visitor infrastructure to traverse AST and generate local nodes from global with original nodes unchanged (so does not use normal visitChildren pattern -- env used to pass the working projections)
// FIXME: uses envs but does not need to be a SubProtocolVisitor -- swap env and subprotocol visitors in hierarchy? Maybe not: e.g. GraphBuilder is a subprotocol visitor but not an env visitor
public class Projector extends EnvVisitor<ProjectionEnv>
{
	private Stack<Role> selfs = new Stack<>();  // Is a stack needed? roles only pushed from GlobalProtocolDecl, which should be only done once at the root?
	
	// first protocol name is full global protocol name, second is full local projection names
	private Map<ProtocolName, Map<Role, Module>> projections = new HashMap<>();
	
	/*// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	private Map<ProtocolName, Set<Role>> dependencies = new HashMap<>();*/
	
	// first protocol name is full root global protocol name, second is full global protocol dependency names (reflexive)
	//private Map<ProtocolName, Map<Role, Map<ProtocolName, Set<Role>>>> dependencies = new HashMap<>();

	public Projector(Job job)
	{
		super(job);
	}

	// Envs pushed for GlobalNode delegates (doesn't include interaction seqs)
	@Override
	protected ProjectionEnv makeRootProtocolDeclEnv(
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
			ProtocolDecl<? extends ProtocolKind> pd)
	{
		//return new ProjectionEnv(this.getJobContext(), getModuleContext(), null);
		//return new ProjectionEnv(this.getJobContext(), getModuleDelegate());
		return new ProjectionEnv();
	}

	@Override
	public ModelNode visit(ModelNode parent, ModelNode child) throws ScribbleException
	{
		// Hack to "override" SubprotocolVisitor visitChildrenInSubprotocols pattern
		// But avoids adding visitForProjection to every ModelNode
		// Or else need to build in a "visit override" mechanism into the parent visitors
		if (child instanceof GlobalProtocolDecl)
		{
			/*Projector proj = (Projector) enter(parent, child);
			//ModelNode visited = child.visitChildrenInSubprotocols(spv);  visitForProjection
			ModelNode visited = ((GlobalProtocolDeclDelegate) child.del()).visitForProjection(this, (GlobalProtocolDecl) child);
			return leave(parent, child, proj, visited);*/
			//return ((GlobalProtocolDeclDelegate) child.del()).visitOverrideForProjection(this, (Module) parent, (GlobalProtocolDecl) child);
			return visitOverrideForGlobalProtocolDecl((Module) parent, (GlobalProtocolDecl) child);
		}
		else
		{
			return super.visit(parent, child);
		}
	}
	
  // Important: projection should not follow the subprotocol visiting pattern for do's -- projection uses some name mangling, which isn't compatible with subprotocol visitor name maps
	@Override
	protected ModelNode visitForSubprotocols(ModelNode parent, ModelNode child) throws ScribbleException
	{
		return child.visitChildren(this);
	}

	// Projector uses this to "override" the base SubprotocolVisitor visitChildrenInSubprotocols pattern
	// Better to be in the visitor than in the del for visibility of visitor enter/leave -- also localises special visiting pattern inside the visitor, while keeping the del enter/leave methods uniform (e.g. GlobalProtocolDeclDelegate enter/leave relies on the same peekSelf API as for other nodes)
	private GlobalProtocolDecl visitOverrideForGlobalProtocolDecl(Module parent, GlobalProtocolDecl child) throws ScribbleException
	{
		//ModelNode visited = child.visitChildrenInSubprotocols(spv);  visitForProjection
		//ModuleDelegate md = getModuleDelegate();
		/*JobContext jc = proj.getJobContext();
		Module main = jc.getMainModule();

		ProtocolName gpn = child.getFullProtocolName(main);*/
		//Map<Role, Map<ProtocolName, Set<Role>>> deps = new HashMap<>();  // FIXME: factor dependency building out to a context pass
		for (Role self : child.header.roledecls.getRoles())
		{
			pushSelf(self);
			//clearProtocolDependencies();

			//Projector proj = (Projector) enter(parent, child);
			/*GlobalProtocolDecl gpd = (GlobalProtocolDecl)
			GlobalProtocolDecl visited = (GlobalProtocolDecl) child.visitChildrenInSubprotocols(proj);  // enter/leave around visitChildren for this GlobalProtocolDecl done above -- cf. SubprotocolVisitor.visit
			visited = (GlobalProtocolDecl) leave(parent, child, proj, visited);*/
			enter(parent, child);
			//GlobalProtocolDecl visited = (GlobalProtocolDecl) child.visitChildrenInSubprotocols(this);  // enter/leave around visitChildren for this GlobalProtocolDecl done above -- cf. SubprotocolVisitor.visit
			//GlobalProtocolDecl visited = (GlobalProtocolDecl) super.visitForSubprotocols(parent, child);  // enter/leave around visitChildren for this GlobalProtocolDecl done above -- cf. SubprotocolVisitor.visit
			GlobalProtocolDecl visited = (GlobalProtocolDecl) child.visitChildren(this);  // enter/leave around visitChildren for this GlobalProtocolDecl done above -- cf. SubprotocolVisitor.visit
			visited = (GlobalProtocolDecl) leave(parent, child, visited);
			// projection will not change original global protocol (visited discarded)
			
			//deps.put(self, proj.getProtocolDependencies());
			
			//System.out.println("P: " + self + ":\n" + projected + "\n");
			
			//.. add all dependencies to projection set (needs to be done after all modules have been visited) .. FIXME: dependencies need to be the sigs for the argument positions, not just the name -- or maybe ok to not, just be conservative
			//.. maybe just record all projections in one big store for reachability checking, projection set for a specific global protocol can be worked out form dependencies?
			
			popSelf();
		}
		/*//return this;
		//ProtocolDeclContext pdcontext = new ProtocolDeclContext(dependencies);
		GlobalProtocolDeclDelegate del = ((GlobalProtocolDeclDelegate) child.del()).setDependencies(deps);  // FIXME: move to leaveProjection in GlobalProtocolDecl

		//return reconstruct(this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());
		return (GlobalProtocolDecl) child.del(del);  // del setter needs to be done here (access to collected dependencies) -- envLeave uses this new del (including Env setting)
			
		// projected modules added to context delegate in (main) module delegate leaveProjection*/

		//System.out.println("c: " + child.header.name); 
		//System.out.println("c: " + deps);
		//System.out.println("c: " + ((GlobalProtocolDeclDelegate) child.del()).getProtocolDependencies());

		return child;
	}
	
	/*@Override
	protected boolean overrideSubstitution()
	{
		return true;
	}*/

	/*// FIXME: parent not used
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
			GlobalProtocolDecl gpd = (GlobalProtocolDecl) child.visitChildrenInSubprotocols(this);  // enter/leave around visitChildren for this GlobalProtocolDecl done above -- cf. SubprotocolVisitor.visit
			
			SimpleProtocolNameNode pn = makeProjectedLocalName(child.header.name.toName(), self);
			//RoleDeclList roledecls = (RoleDeclList) ((ProjectionEnv) gpd.roledecls.getEnv()).getProjection();
			RoleDeclList roledecls = gpd.header.roledecls.project(self);//peekSelf());
			//ParameterDeclList paramdecls = (ParameterDeclList) ((ProjectionEnv) gpd.paramdecls.getEnv()).getProjection();
			ParameterDeclList paramdecls = gpd.header.paramdecls.project(self);//peekSelf());
			//LocalProtocolHeader lph = new LocalProtocolHeader(pn, roledecls, paramdecls);
			LocalProtocolHeader lph = ModelFactoryImpl.FACTORY.LocalProtocolHeader(pn, roledecls, paramdecls);
			LocalProtocolDefinition def = (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.del().getEnv()).getProjection();
			//LocalProtocolDecl lpd = new LocalProtocolDecl(lph, def);
			LocalProtocolDecl lpd = ModelFactoryImpl.FACTORY.LocalProtocolDecl(lph, def);

			//pdcontext = new ProtocolDeclContext(getContext(), self, proj.getProtocolDependencies());  // FIXME: move dependency building to after initial context building (or integrate into it?)
			Map<ProtocolName, Set<Role>> deps = getProtocolDependencies();
			dependencies.put(self, deps);

			Module projected = mod.makeProjectedModule(this, lpd, deps);
			// store projections in projector? in context? do earlier with context building? (but subprotocol pattern not available there)* /
			addProjection(gpn, self, projected);
			
			//System.out.println("P: " + self + ":\n" + projected);
			
			//.. add all dependencies to projection set (needs to be done after all modules have been visited) .. FIXME: dependencies need to be the sigs for the argument positions, not just the name -- or maybe ok to not, just be conservative
			//.. maybe just record all projections in one big store for reachability checking, projection set for a specific global protocol can be worked out form dependencies?
			
			popSelf();
		}
		//return this;
		//ProtocolDeclContext pdcontext = new ProtocolDeclContext(dependencies);
		GlobalProtocolDeclDelegate del = ((GlobalProtocolDeclDelegate) child.del()).setDependencies(dependencies);  // FIXME: move to leaveProjection in GlobalProtocolDecl
			
		//System.out.println("c: " + this.name + ", " + pdcontext.getProtocolDependencies());

		//return reconstruct(this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());
		return (GlobalProtocolDecl) child.del(del);  // del setter needs to be done here (access to collected dependencies) -- envLeave uses this new del (including Env setting)
		
		// projected modules added to context delegate in (main) module leaveProjection
	}*/
	
	@Override
	//protected final Projector envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	protected final void envEnter(ModelNode parent, ModelNode child) throws ScribbleException
	{
		/*Projector proj = (Projector) super.envEnter(parent, child);
		return proj;*/
		//return child.enterProjection(this);
		/*Projector proj = (Projector) super.envEnter(parent, child);
		return (Projector) child.del().enterProjection(parent, child, proj);*/
		super.envEnter(parent, child);
		child.del().enterProjection(parent, child, this);
	}
	
	@Override
	//protected ModelNode envLeave(ModelNode parent, ModelNode child, EnvVisitor<ProjectionEnv> nv, ModelNode visited) throws ScribbleException
	protected ModelNode envLeave(ModelNode parent, ModelNode child, ModelNode visited) throws ScribbleException
	{
		/*ModelNode n = super.envLeave(parent, child, nv, visited);
		return n.leaveProjection((Projector) nv);*/
		/*visited = visited.del().leaveProjection(parent, child, (Projector) nv, visited);
		return super.envLeave(parent, child, nv, visited);*/
		visited = visited.del().leaveProjection(parent, child, this, visited);
		return super.envLeave(parent, child, visited);
	}
	
	// Simple projected protocol name for protocol decls -- Move into SimpleProtocolName?
	public static SimpleProtocolNameNode makeProjectedLocalName(ProtocolName simplename, Role role)
	{
		//return new SimpleProtocolNameNode(makeProjectedLocalNameAux(simplename.toString(), role.toString()));
		return (SimpleProtocolNameNode) ModelFactoryImpl.FACTORY.SimpleNameNode(ModelFactory.SIMPLE_NAME.PROTOCOL, makeProjectedLocalNameAux(simplename.toString(), role.toString()));
	}

	// Role is the target subprotocol parameter (not the current projector self -- actually the self just popped)
	public static ProtocolNameNode makeProjectedProtocolNameNode(ProtocolName fullname, Role role)
	{
		String simplename = makeProjectedLocalNameAux(fullname.getLastElement(), role.toString());
		String[] elems = fullname.getElements();
		String[] tmp = new String[elems.length];
		System.arraycopy(elems, 0, tmp, 0, elems.length - 2);
		tmp[tmp.length - 2] = makeProjectedModuleSimpleName(fullname.getPrefix().getSimpleName().toString(), simplename);
		tmp[tmp.length - 1] = simplename;
		//return new ProtocolNameNode(tmp);
		return (ProtocolNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.PROTOCOL, tmp);
	}

	/*public static ProtocolName makeProjectedProtocolName(ProtocolName fullname, Role role)
	{
		String simplename = makeProjectedLocalNameAux(fullname.getLastElement(), role.toString());
		ModuleName mn = makeProjectedModuleName(fullname.getPrefix(), new ProtocolName(simplename));
		return new ProtocolName(mn, simplename);
	}*/

	// Factor out with above
	// fullname is the un-projected name; localname is the already projected simple name
	public static ModuleNameNode makeProjectedModuleNameNodes(ModuleName fullname, ProtocolName localname)
	{
		String[] elems = fullname.getElements();
		String[] tmp = new String[elems.length];
		System.arraycopy(elems, 0, tmp, 0, elems.length);
		tmp[tmp.length - 1] = makeProjectedModuleSimpleName(fullname.getSimpleName().toString(), localname.toString());
		//return new ModuleNameNode(tmp);
		return (ModuleNameNode) ModelFactoryImpl.FACTORY.QualifiedNameNode(ModelFactory.QUALIFIED_NAME.MODULE, tmp);
	}

	/*public static ModuleName makeProjectedModuleName(ModuleName fullname, ProtocolName localname)
	{
		String tmp = makeProjectedModuleSimpleName(fullname.getSimpleName().toString(), localname.toString());
		return new ModuleName(fullname.getPrefix(), tmp);
	}*/

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
	
	/*public void clearProtocolDependencies()
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
	}*/
}
