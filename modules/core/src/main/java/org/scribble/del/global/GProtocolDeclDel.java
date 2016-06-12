package org.scribble.del.global;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Module;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.DependencyMap;
import org.scribble.ast.context.global.GProtocolDeclContext;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.del.ModuleDel;
import org.scribble.del.ProtocolDeclDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.JobContext;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDeclContextBuilder;
import org.scribble.visit.RoleCollector;
import org.scribble.visit.env.ProjectionEnv;

public class GProtocolDeclDel extends ProtocolDeclDel<Global>
{
	public GProtocolDeclDel()
	{

	}

	@Override
	protected GProtocolDeclDel copy()
	{
		return new GProtocolDeclDel();
	}

	@Override
	protected void addSelfDependency(ProtocolDeclContextBuilder builder, ProtocolName<?> proto, Role role)
	{
		builder.addGlobalProtocolDependency(role, (GProtocolName) proto, role);
	}
	
	@Override
	public GProtocolDecl
			leaveProtocolDeclContextBuilding(ScribNode parent, ScribNode child, ProtocolDeclContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		GProtocolDecl gpd = (GProtocolDecl) visited;
		GProtocolDeclContext gcontext = new GProtocolDeclContext(builder.getGlobalProtocolDependencyMap());
		GProtocolDeclDel del = (GProtocolDeclDel) setProtocolDeclContext(gcontext);
		return (GProtocolDecl) gpd.del(del);
	}

	@Override
	public ScribNode leaveRoleCollection(ScribNode parent, ScribNode child, RoleCollector coll, ScribNode visited) throws ScribbleException
	{
		GProtocolDecl gpd = (GProtocolDecl) visited;

		// Need to do here (e.g. RoleDeclList too early, def not visited yet)
		// Currently only done for global, local does roledecl fixing after role collection -- should separate this check to a later pass after context building
		// Maybe relax to check only occs.size() > 1
		List<Role> decls = gpd.header.roledecls.getRoles();
		Set<Role> occs = coll.getNames();
		if (occs.size() != decls.size()) 
		{
			decls.removeAll(occs);
			throw new ScribbleException("Unused role decl(s) in " + gpd.header.name + ": " + decls);
		}

		return super.leaveRoleCollection(parent, child, coll, gpd);
	}

	@Override
	public GProtocolDecl
			leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		JobContext jc = proj.getJobContext();
		Module root = jc.getModule(proj.getModuleContext().root);
		GProtocolDecl gpd = (GProtocolDecl) visited;
		Role self = proj.peekSelf();
		LProtocolDecl lpd = project(proj, gpd);
		Map<GProtocolName, Set<Role>> deps = ((GProtocolDeclDel) gpd.del()).getGlobalProtocolDependencies(self);
		Module projected = ((ModuleDel) root.del()).createModuleForProjection(proj, root, lpd, deps);
		
		/*if (lpd.getHeader().name.toString().endsWith("_C"))
		{
			System.out.println("ZZZ:\n" + projected);
		}*/
		
		proj.addProjection(gpd.getFullMemberName(root), self, projected);
		return gpd;
	}
	
	private LProtocolDecl project(Projector proj, GProtocolDecl gpd) throws ScribbleException
	{
		Role self = proj.peekSelf();
		LProtocolDef def = (LProtocolDef) ((ProjectionEnv) gpd.def.del().env()).getProjection();
		LProtocolNameNode pn = Projector.makeProjectedSimpleNameNode(gpd.getHeader().getDeclName(), self);
		
		// Move to delegates? -- maybe fully integrate into projection pass
		RoleDeclList roledecls = gpd.header.roledecls.project(self);
		NonRoleParamDeclList paramdecls = gpd.header.paramdecls.project(self);
		LProtocolHeader lph = AstFactoryImpl.FACTORY.LProtocolHeader(pn, roledecls, paramdecls);
		GProtocolName gpn = gpd.getFullMemberName(proj.getJobContext().getModule(proj.getModuleContext().root));
		LProtocolDecl projected = AstFactoryImpl.FACTORY.LProjectionDecl(gpn, proj.peekSelf(), lph, def);
		return projected;
	}

	/*// Cf. LProtocolDeclDel enter/leaveEndpointGraphBuilding
	@Override
	public void enterModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder graph)
	{
		graph.builder.reset();
	}

	@Override
	public GProtocolDecl leaveModelBuilding(ScribNode parent, ScribNode child, GlobalModelBuilder builder, ScribNode visited) throws ScribbleException
	{
		GProtocolDecl gpd = (GProtocolDecl) visited;
		/*System.out.println("1a: " + ((ModelEnv) gpd.def.block.del().env()).getActions());
		System.out.println("1b: " + parseModel(((ModelEnv) gpd.def.block.del().env()).getActions()).toDot());* /
		GModel model = new GModel(builder.builder.getEntry(), builder.builder.getExit());
		JobContext jc = builder.getJobContext();
		jc.addGlobalModel(gpd.getFullMemberName((Module) parent), null);// model);
		//builder.getJob().debugPrintln("\n[DEBUG] Global model " + gpd.getFullMemberName((Module) parent) + ":\n" + model);
		return gpd;
	}*/
	
	/*private static GModelState parseModel(Set<GModelAction> as)
	{
		/*GModelState root = new GModelState();
		Set<GModelAction> eligible = as.stream().filter((a) -> a.getDependencies().isEmpty()).collect(Collectors.toSet());
		Set<GModelAction> rest = new HashSet<>(as);
		rest.removeAll(eligible);
		parseModel(rest, root, eligible);
		return root;* /
		throw new RuntimeException("TODO: ");
	}*/

	/*private static void parseModel(Set<GModelAction> rest, GModelState curr, Set<GModelAction> eligible)
	{
		for (GModelAction e : eligible)
		{
			GModelState next = new GModelState();
			curr.addEdge(e, next);
			Set<GModelAction> etmp = new HashSet<>(eligible);
			etmp.remove(e);
			Set<GModelAction> rtmp = new HashSet<>(rest);
			for (GModelAction r : rest)
			{
				//if (eligible.containsAll(r.getDependencies()))
				Set<GModelAction> tmp =  new HashSet<>(etmp);
				tmp.addAll(rtmp);
				tmp.retainAll(r.getDependencies());
				if (tmp.isEmpty())
				{
					etmp.add(r);
					rtmp.remove(r);
				}
			}
			parseModel(rtmp, next, etmp);
		}* /
		throw new RuntimeException("TODO: ");
	}*/

	public Map<GProtocolName, Set<Role>> getGlobalProtocolDependencies(Role self)
	{
		DependencyMap<GProtocolName> deps = getProtocolDeclContext().getDependencyMap();
		return deps.getDependencies().get(self);
	}
	
	@Override
	public GProtocolDeclContext getProtocolDeclContext()
	{
		return (GProtocolDeclContext) super.getProtocolDeclContext();
	}
}

