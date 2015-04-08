package org.scribble2.model.del.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.Module;
import org.scribble2.model.ParameterDeclList;
import org.scribble2.model.RoleDeclList;
import org.scribble2.model.del.ModuleDelegate;
import org.scribble2.model.del.ProtocolDeclDelegate;
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.local.LocalProtocolDecl;
import org.scribble2.model.local.LocalProtocolDefinition;
import org.scribble2.model.local.LocalProtocolHeader;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.Projector;
import org.scribble2.model.visit.env.ProjectionEnv;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class GlobalProtocolDeclDelegate extends ProtocolDeclDelegate
{
	public GlobalProtocolDeclDelegate()
	{

	}
	
	protected GlobalProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
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
	}

	// Projector uses this to "override" the base SubprotocolVisitor visitChildrenInSubprotocols pattern
	public GlobalProtocolDecl visitForProjection(Projector proj, GlobalProtocolDecl child) throws ScribbleException
	{
		//ModuleDelegate md = getModuleDelegate();
		JobContext jc = proj.getJobContext();
		Module main = jc.getMainModule();

		ProtocolName gpn = child.getFullProtocolName(main);
		Map<Role, Map<ProtocolName, Set<Role>>> dependencies = new HashMap<>();
		for (Role self : child.header.roledecls.getRoles())
		{
			proj.pushSelf(self);
			//proj.addProtocolDependency(gpn, self, gpn, self);
			proj.clearProtocolDependencies();  // FIXME: make into a enter/leave or push/pop pattern
			proj.addProtocolDependency(gpn, self);
			GlobalProtocolDecl gpd = (GlobalProtocolDecl) child.visitChildrenInSubprotocols(proj);  // enter/leave around visitChildren for this GlobalProtocolDecl done above -- cf. SubprotocolVisitor.visit
			
			LocalProtocolDecl lpd = project(proj, gpd);
			Map<ProtocolName, Set<Role>> deps = proj.getProtocolDependencies();
			//Module projected = projectIntoModule(proj, gpd);
			Module projected = ((ModuleDelegate) main.del()).createModuleForProjection(proj, main, lpd, deps);  // FIXME: projection should always use the main module?
			// store projections in projector? in context? do earlier with context building? (but subprotocol pattern not available there)* /
			dependencies.put(self, deps);

			proj.addProjection(gpn, self, projected);
			
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
	}
	
	private LocalProtocolDecl project(Projector proj, GlobalProtocolDecl gpd)
	{
		Role self = proj.peekSelf();
		//Module projected = projectToModule(proj, (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.del().getEnv()).getProjection());
		LocalProtocolDefinition def = (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.del().getEnv()).getProjection();
		SimpleProtocolNameNode pn = proj.makeProjectedLocalName(gpd.header.name.toName(), self);
		RoleDeclList roledecls = gpd.header.roledecls.project(self);
		ParameterDeclList paramdecls = gpd.header.paramdecls.project(self);//peekSelf());
		LocalProtocolHeader lph = ModelFactoryImpl.FACTORY.LocalProtocolHeader(pn, roledecls, paramdecls);
		//LocalProtocolDefinition def = (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.del().getEnv()).getProjection();
		LocalProtocolDecl projected = ModelFactoryImpl.FACTORY.LocalProtocolDecl(lph, def);

		////pdcontext = new ProtocolDeclContext(getContext(), self, proj.getProtocolDependencies());  // FIXME: move dependency building to after initial context building (or integrate into it?)
		//Map<ProtocolName, Set<Role>> deps = proj.getProtocolDependencies();
		//dependencies.put(self, deps);
		return projected;
	}
}
