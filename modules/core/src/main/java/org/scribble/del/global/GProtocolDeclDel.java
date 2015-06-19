package org.scribble.del.global;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.Module;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.context.GProtocolDeclContext;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.del.ModuleDel;
import org.scribble.del.ProtocolDeclDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.ModelAction;
import org.scribble.model.global.ModelState;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.DependencyMap;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.JobContext;
import org.scribble.visit.ModelBuilder;
import org.scribble.visit.Projector;
import org.scribble.visit.env.ModelEnv;
import org.scribble.visit.env.ProjectionEnv;

public class GProtocolDeclDel extends ProtocolDeclDel<Global>
//public class GProtocolDeclDel extends ProtocolDeclDel
{
	public GProtocolDeclDel()
	{

	}

	@Override
	public void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException
	{
		builder.clearProtocolDependencies();  // collect per protocoldecl all together, do not clear?
		
		Module main = (Module) parent;
		
		GProtocolDecl gpd = (GProtocolDecl) child;
		GProtocolName gpn = gpd.getFullMemberName(main);
		for (Role role : gpd.header.roledecls.getRoles())
		{
			builder.addProtocolDependency(role, gpn, role);  // FIXME: is it needed to add self protocol decl?
		}
	}
	
	@Override
	public GProtocolDecl leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		//System.out.println("c: " + proj.getProtocolDependencies());

		GProtocolDecl gpd = (GProtocolDecl) visited;
		/*GProtocolDeclDel del = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		del.setProtocolDeclContext(new GProtocolDeclContext(builder.getGlobalProtocolDependencies()));*/
		GProtocolDeclContext gcontext = new GProtocolDeclContext(builder.getGlobalProtocolDependencyMap());
		GProtocolDeclDel del = (GProtocolDeclDel) setProtocolDeclContext(gcontext);
		return (GProtocolDecl) gpd.del(del);
	}

	/*@Override
	//protected DependencyMap<? extends ProtocolName<? extends ProtocolKind>> newDependencyMap()
	protected DependencyMap<GProtocolName> newDependencyMap()
	{
		return new DependencyMap<GProtocolName>();
	}*/

	//public Map<Role, Map<GProtocolName, Set<Role>>> getGlobalProtocolDependencies()
	//public DependencyMap<GProtocolName, Global> getGlobalProtocolDependencies()
	public Map<GProtocolName, Set<Role>> getGlobalProtocolDependencies(Role self)
	{
		//GProtocolDeclContext pcontext = getProtocolDeclContext();
		//Map<Role, Map<? extends ProtocolName<Global>, Set<Role>>> deps = pcontext.getDependencies();
		//DependencyMap<GProtocolName, Global> deps = pcontext.getDependencies();
		DependencyMap<GProtocolName> deps = getProtocolDeclContext().getDependencyMap();
		//return cast(deps);
		//return deps;
		return deps.getDependencies().get(self);
	}
	
	@Override
	public GProtocolDeclContext getProtocolDeclContext()
	{
		return (GProtocolDeclContext) super.getProtocolDeclContext();
	}
	
	/*private static Map<Role, Map<GProtocolName, Set<Role>>> cast(Map<Role, Map<? extends ProtocolName<Global>, Set<Role>>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((r) -> r, (r) -> castAux(map.get(r))));
	}

	private static Map<GProtocolName, Set<Role>> castAux(Map<? extends ProtocolName<Global>, Set<Role>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((k) -> (GProtocolName) k, (k) -> map.get(k)));
	}*/
	
	/*protected GlobalProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		super(dependencies);
	}

	@Override
	protected GlobalProtocolDeclDelegate reconstruct(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return new GlobalProtocolDeclDelegate(dependencies);
	}

	@Override
	public GlobalProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return (GlobalProtocolDeclDelegate) super.setDependencies(dependencies);
	}*/

	@Override
	protected GProtocolDeclDel copy()
	{
		return new GProtocolDeclDel();
	}

	/*@Override
	public Projector enterProjection(ModelNode parent, ModelNode child, Projector proj) throws ScribbleException
	{
		/*JobContext jc = proj.getJobContext();
		Module main = jc.getMainModule();

		ProtocolName gpn = ((GlobalProtocolDecl) child).getFullProtocolName(main);
		Role self = proj.peekSelf();

		//proj.addProtocolDependency(gpn, self, gpn, self);
		proj.addProtocolDependency(gpn, self);* /

		return proj;
	}*/
	
	@Override
	public GProtocolDecl leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		JobContext jc = proj.getJobContext();
		//Module main = jc.getMainModule();
		
		//FIXME: it's the "root" we need, not the main
		Module root = jc.getModule(proj.getModuleContext().root);
		
		GProtocolName gpn = ((GProtocolDecl) child).getFullMemberName(root);
		
		Role self = proj.peekSelf();
		GProtocolDecl gpd = (GProtocolDecl) visited;

		LProtocolDecl lpd = project(proj, gpd);
		//Map<ProtocolName, Set<Role>> deps = proj.getProtocolDependencies();
		//Map<GProtocolName, Set<Role>> deps = ((GProtocolDeclDel) gpd.del()).getProtocolDependencies().get(self);
		//Map<GProtocolName, Set<Role>> deps = ((GProtocolDeclDel) gpd.del()).getGlobalProtocolDependencies().get(self);
		Map<GProtocolName, Set<Role>> deps = ((GProtocolDeclDel) gpd.del()).getGlobalProtocolDependencies(self);  // FIXME: hard coupled del to GProtocolDeclDel -- OK: because this is localised here (i.e. GProtocolDeclDel)
		
		//Module projected = projectIntoModule(proj, gpd);
		Module projected = ((ModuleDel) root.del()).createModuleForProjection(proj, root, lpd, deps);  // FIXME: projection should always use the main module?
		// store projections in projector? in context? do earlier with context building? (but subprotocol pattern not available there)* /
		//dependencies.put(self, deps);

		proj.addProjection(gpn, self, projected);
		
		//System.out.println("P: " + self + ":\n" + projected + "\n");
		
		//.. add all dependencies to projection set (needs to be done after all modules have been visited) .. FIXME: dependencies need to be the sigs for the argument positions, not just the name -- or maybe ok to not, just be conservative
		//.. maybe just record all projections in one big store for reachability checking, projection set for a specific global protocol can be worked out form dependencies?
		
		//proj.popSelf();

		return gpd;
	}

	/*// Projector uses this to "override" the base SubprotocolVisitor visitChildrenInSubprotocols pattern
	public GlobalProtocolDecl visitOverrideForProjection(Projector proj, Module parent, GlobalProtocolDecl child) throws ScribbleException
	{
			//ModelNode visited = child.visitChildrenInSubprotocols(spv);  visitForProjection
		//ModuleDelegate md = getModuleDelegate();
		/*JobContext jc = proj.getJobContext();
		Module main = jc.getMainModule();

		ProtocolName gpn = child.getFullProtocolName(main);* /
		Map<Role, Map<ProtocolName, Set<Role>>> dependencies = new HashMap<>();
		for (Role self : child.header.roledecls.getRoles())
		{
			//..HERE:   // FIXME: make into a enter/leave or push/pop pattern

			proj.pushSelf(self);
			//proj.clearProtocolDependencies();

			proj = (Projector) proj.enter(parent, child);
			//GlobalProtocolDecl gpd = (GlobalProtocolDecl)
			GlobalProtocolDecl visited = child.visitChildrenInSubprotocols(proj);  // enter/leave around visitChildren for this GlobalProtocolDecl done above -- cf. SubprotocolVisitor.visit
			visited = leave(parent, child, proj, visited);
			// projection will not change original global protocol
			
			dependencies.put(self, proj.getProtocolDependencies());
			
			//System.out.println("P: " + self + ":\n" + projected + "\n");
			
			//.. add all dependencies to projection set (needs to be done after all modules have been visited) .. FIXME: dependencies need to be the sigs for the argument positions, not just the name -- or maybe ok to not, just be conservative
			//.. maybe just record all projections in one big store for reachability checking, projection set for a specific global protocol can be worked out form dependencies?
			
			proj.popSelf();
		}
		//return this;
		//ProtocolDeclContext pdcontext = new ProtocolDeclContext(dependencies);
		GlobalProtocolDeclDelegate del = ((GlobalProtocolDeclDelegate) child.del()).setDependencies(dependencies);  // FIXME: move to leaveProjection in GlobalProtocolDecl
			
		//System.out.println("c: " + this.name + ", " + pdcontext.getProtocolDependencies());

		//return reconstruct(this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());
		return (GlobalProtocolDecl) child.del(del);  // del setter needs to be done here (access to collected dependencies) -- envLeave uses this new del (including Env setting)
		
		// projected modules added to context delegate in (main) module delegate leaveProjection
	}*/
	
	private LProtocolDecl project(Projector proj, GProtocolDecl gpd)
	{
		Role self = proj.peekSelf();
		//Module projected = projectToModule(proj, (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.del().getEnv()).getProjection());
		LProtocolDef def = (LProtocolDef) ((ProjectionEnv) gpd.def.del().env()).getProjection();
		//SimpleProtocolNameNode pn = proj.makeProjectedLocalName(gpd.header.name.toName(), self);
		//SimpleProtocolNameNode pn = Projector.makeProjectedLocalName(gpd.header.name.toCompoundName(), self);
		//SimpleProtocolNameNode pn = Projector.makeProjectedLocalName(gpd.header.getDeclName(), self);
		LProtocolNameNode pn = Projector.makeProjectedLocalName(((GProtocolHeader) gpd.header).getDeclName(), self);
		
		// FIXME: move to delegate? -- maybe fully integrate into projection pass
		RoleDeclList roledecls = gpd.header.roledecls.project(self);
		NonRoleParamDeclList paramdecls = gpd.header.paramdecls.project(self);//peekSelf());
		LProtocolHeader lph = AstFactoryImpl.FACTORY.LProtocolHeader(pn, roledecls, paramdecls);
		//LocalProtocolDefinition def = (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.del().getEnv()).getProjection();
		LProtocolDecl projected = AstFactoryImpl.FACTORY.LProtocolDecl(lph, def);

		////pdcontext = new ProtocolDeclContext(getContext(), self, proj.getProtocolDependencies());  // FIXME: move dependency building to after initial context building (or integrate into it?)
		//Map<ProtocolName, Set<Role>> deps = proj.getProtocolDependencies();
		//dependencies.put(self, deps);
		return projected;
	}

	/*@Override
	protected GProtocolDeclContext newProtocolDeclContext(Map<Role, Map<ProtocolName<? extends ProtocolKind>, Set<Role>>> deps)
	{
		return new GProtocolDeclContext(new DependencyMap<>(cast(deps)));
		//return new GProtocolDeclContext(cast(deps));
	}*/

	/*private static Map<Role, Map<GProtocolName, Set<Role>>> cast(Map<Role, Map<ProtocolName<? extends ProtocolKind>, Set<Role>>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((r) -> r, (r) -> castAux(map.get(r))));
	}

	private static Map<GProtocolName, Set<Role>> castAux(Map<ProtocolName<? extends ProtocolKind>, Set<Role>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((k) -> (GProtocolName) k, (k) -> map.get(k)));
	}*/

	@Override
	public GProtocolDecl leaveModelBuilding(ScribNode parent, ScribNode child, ModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		GProtocolDecl gpd = (GProtocolDecl) visited;
		System.out.println("1: " + ((ModelEnv) gpd.def.block.del().env()).getActions());
		System.out.println("2: " + parseModel(((ModelEnv) gpd.def.block.del().env()).getActions()).toDot());
		return gpd;
	}
	
	private static ModelState parseModel(Set<ModelAction> as)
	{
		ModelState root = new ModelState();
		Set<ModelAction> eligible = as.stream().filter((a) -> a.getDependencies().isEmpty()).collect(Collectors.toSet());
		Set<ModelAction> rest = new HashSet<>(as);
		rest.removeAll(eligible);
		parseModel(rest, root, eligible);
		return root;
	}

	private static void parseModel(Set<ModelAction> rest, ModelState curr, Set<ModelAction> eligible)
	{
		for (ModelAction e : eligible)
		{
			ModelState next = new ModelState();
			curr.addEdge(e, next);
			Set<ModelAction> etmp = new HashSet<>(eligible);
			etmp.remove(e);
			Set<ModelAction> rtmp = new HashSet<>(rest);
			for (ModelAction r : rest)
			{
				//if (eligible.containsAll(r.getDependencies()))
				Set<ModelAction> tmp =  new HashSet<>(etmp);
				tmp.addAll(rtmp);
				tmp.retainAll(r.getDependencies());
				if (tmp.isEmpty())
				{
					etmp.add(r);
					rtmp.remove(r);
				}
			}
			parseModel(rtmp, next, etmp);
		}
	}
}