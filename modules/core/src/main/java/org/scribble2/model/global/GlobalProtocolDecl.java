package org.scribble2.model.global;

import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.sesstype.kind.Global;

//public class GlobalProtocolDecl extends ProtocolDecl<GlobalInteraction>
//public class GlobalProtocolDecl extends AbstractProtocolDecl<GlobalProtocolHeader, GlobalProtocolDefinition> implements GlobalNode
public class GlobalProtocolDecl extends ProtocolDecl<Global> implements GlobalNode
{
	//public GlobalProtocolDecl(CommonTree ct, ProtocolNode name, RoleDeclList rdl, ParameterDeclList pdl, ProtocolDefinition<GlobalInteraction> def)
	//public GlobalProtocolDecl(CommonTree ct, SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls, GlobalProtocolDefinition def)
	//public GlobalProtocolDecl(GlobalProtocolHeader header, GlobalProtocolDefinition def)
	public GlobalProtocolDecl(ProtocolHeader header, ProtocolDefinition<Global> def)
	{
		//this(t, name, roledecls, paramdecls, def, null, null);
		super(header, def);
	}

	@Override
	protected GlobalProtocolDecl copy()
	{
		//GlobalProtocolDecl gpd = ModelFactoryModelFactoryImpl.FACTORY.GlobalProtocolDecl(header, def);  // No: don't want del
		return new GlobalProtocolDecl(this.header, this.def);
	}

	@Override
	//protected GlobalProtocolDecl reconstruct(GlobalProtocolHeader header, GlobalProtocolDefinition def)//, ProtocolDeclContext pdcontext, Env env)
	protected GlobalProtocolDecl reconstruct(ProtocolHeader header, ProtocolDefinition<Global> def)//, ProtocolDeclContext pdcontext, Env env)
	{
		
		ModelDelegate del = del();
		//GlobalProtocolDecl gpd = ModelFactoryImpl.FACTORY.GlobalProtocolDecl(header, def);  // No: don't want del
		GlobalProtocolDecl gpd = new GlobalProtocolDecl(header, def);
		gpd = (GlobalProtocolDecl) gpd.del(del);  // FIXME: does another shallow copy
		return gpd;
	}

	@Override
	public boolean isGlobal()
	{
		return true;
	}

	/*@Override
	public GlobalProtocolDecl leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		ProtocolDecl<GlobalProtocolDefinition> pd = super.leaveContextBuilding(builder);
		//return new GlobalProtocolDecl(pd.ct, pd.name, pd.roledecls, pd.paramdecls, pd.def, pd.getContext());
		return reconstruct(pd.ct, pd.name, pd.roledecls, pd.paramdecls, pd.def, pd.getContext(), getEnv());
	}* /
	
	@Override
	public GlobalProtocolDecl visitForProjection(Projector proj) throws ScribbleException
	{
		Module mod = proj.getModuleContext().root;
		ProtocolName gpn = getFullProtocolName(mod);
		Map<Role, Map<ProtocolName, Set<Role>>> dependencies = new HashMap<>();
		for (Role self : this.roledecls.getRoles())
		{
			proj.pushSelf(self);
			//proj.addProtocolDependency(gpn, self, gpn, self);
			proj.clearProtocolDependencies();
			proj.addProtocolDependency(gpn, self);
			GlobalProtocolDecl gpd = (GlobalProtocolDecl) this.visitChildrenInSubprotocols(proj);
			
			SimpleProtocolNameNode pn = proj.makeProjectedLocalName(this.name.toName(), self);
			//RoleDeclList roledecls = (RoleDeclList) ((ProjectionEnv) gpd.roledecls.getEnv()).getProjection();
			RoleDeclList roledecls = gpd.roledecls.project(proj.peekSelf());
			//ParameterDeclList paramdecls = (ParameterDeclList) ((ProjectionEnv) gpd.paramdecls.getEnv()).getProjection();
			ParameterDeclList paramdecls = gpd.paramdecls.project(proj.peekSelf());
			LocalProtocolDefinition def = (LocalProtocolDefinition) ((ProjectionEnv) gpd.def.getEnv()).getProjection();
			LocalProtocolDecl lpd = new LocalProtocolDecl(null, pn, roledecls, paramdecls, def);

			//pdcontext = new ProtocolDeclContext(getContext(), self, proj.getProtocolDependencies());  // FIXME: move dependency building to after initial context building (or integrate into it?)
			Map<ProtocolName, Set<Role>> deps = proj.getProtocolDependencies();
			dependencies.put(self, deps);

			Module projected = mod.makeProjectedModule(proj, lpd, deps);
			// store projections in projector? in context? do earlier with context building? (but subprotocol pattern not available there)* /
			proj.addProjection(gpn, self, projected);
			
			//.. add all dependencies to projection set (needs to be done after all modules have been visited) .. FIXME: dependencies need to be the sigs for the argument positions, not just the name -- or maybe ok to not, just be conservative
			//.. maybe just record all projections in one big store for reachability checking, projection set for a specific global protocol can be worked out form dependencies?
			
			proj.popSelf();
		}
		//return this;
		ProtocolDeclContext pdcontext = new ProtocolDeclContext(dependencies);
			
		//System.out.println("c: " + this.name + ", " + pdcontext.getProtocolDependencies());

		return reconstruct(this.ct, this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());
	}
	
	/*@Override
	public GlobalProtocolDecl disambiguate(PayloadTypeOrParameterDisambiguator disamb) throws ScribbleException
	{
		Env env = disamb.getEnv();
		disamb.setEnv(env.push());
		GlobalProtocolDecl gpd = (GlobalProtocolDecl) super.disambiguate(disamb);
		disamb.setEnv(env);
		return gpd;
	}
	
	@Override 
	public GlobalProtocolDecl leave(EnvVisitor nv) throws ScribbleException
	{
		ProtocolDecl pd = (ProtocolDecl) super.leave(nv);
		return new GlobalProtocolDecl(pd.ct, pd.name, pd.rdl, pd.pdl, (GlobalProtocolDefinition) pd.def);
	}
	
	@Override 
	public GlobalProtocolDecl checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		GlobalProtocolDecl gpd = (GlobalProtocolDecl) super.checkWellFormedness(wfc);
		return gpd;
	}
	
	@Override 
	public GlobalProtocolDecl checkReachability(ReachabilityChecker rc) throws ScribbleException
	{
		Env env = rc.getEnv();
		ProtocolName fpn = getFullProtocolName(env);
		for (RoleDecl rd : this.rdl.rds)
		{
			Module lpd = rc.job.getProjection(fpn, rd.name.toName());
			try
			{
				Job job = new Job(rc.job.importPath, lpd, rc.job);
				Env env2 = new Env(job, lpd);
				ReachabilityChecker tmp = new ReachabilityChecker(job, env2);
				tmp.visit(lpd);
			}
			catch (IOException | RecognitionException e)
			{
				throw new ScribbleException(e);
			}
		}
		return this;
	}*/
	
	/*@Override
	public GlobalProtocolBlock getBody()
	{
		return (GlobalProtocolBlock) super.getBody();
	}*/
	
	/*@Override
	public ProtocolName getFullProtocolName(Env env) throws ScribbleException
	{
		return env.getFullGlobalProtocolName(this.name.toName());
	}*/

	/*@Override
	public String toString()
	{
		return AntlrConstants.GLOBAL_KW + " " + super.toString();
	}*/
}
