package org.scribble.del.global;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
import org.scribble.main.Job;
import org.scribble.main.JobContext;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EFSM;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.global.SBuffers;
import org.scribble.model.global.SConfig;
import org.scribble.model.global.SGraph;
import org.scribble.model.global.SModel;
import org.scribble.model.global.SState;
import org.scribble.model.global.actions.SAction;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.Projector;
import org.scribble.visit.context.ProtocolDeclContextBuilder;
import org.scribble.visit.context.env.ProjectionEnv;
import org.scribble.visit.util.RoleCollector;
import org.scribble.visit.validation.GProtocolValidator;

public class GProtocolDeclDel extends ProtocolDeclDel<Global>
{
	public GProtocolDeclDel()
	{

	}
	
	@Override
	public GProtocolDeclContext getProtocolDeclContext()
	{
		return (GProtocolDeclContext) super.getProtocolDeclContext();
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
		JobContext jc = proj.job.getContext();
		Module root = jc.getModule(proj.getModuleContext().root);
		GProtocolDecl gpd = (GProtocolDecl) visited;
		Role self = proj.peekSelf();
		LProtocolDecl lpd = project(proj, gpd);
		Map<GProtocolName, Set<Role>> deps = ((GProtocolDeclDel) gpd.del()).getGlobalProtocolDependencies(self);
		Module projected = ((ModuleDel) root.del()).createModuleForProjection(proj, root, lpd, deps);
		proj.addProjection(gpd.getFullMemberName(root), self, projected);
		return gpd;
	}

	private Map<GProtocolName, Set<Role>> getGlobalProtocolDependencies(Role self)
	{
		DependencyMap<GProtocolName> deps = getProtocolDeclContext().getDependencyMap();
		return deps.getDependencies().get(self);
	}
	
	// FIXME: project modifiers?
	private LProtocolDecl project(Projector proj, GProtocolDecl gpd) throws ScribbleException
	{
		Role self = proj.peekSelf();
		LProtocolDef def = (LProtocolDef) ((ProjectionEnv) gpd.def.del().env()).getProjection();
		LProtocolNameNode pn = Projector.makeProjectedSimpleNameNode(gpd.getHeader().getDeclName(), self);
		
		// Move to delegates? -- maybe fully integrate into projection pass
		RoleDeclList roledecls = gpd.header.roledecls.project(self);
		NonRoleParamDeclList paramdecls = gpd.header.paramdecls.project(self);
		LProtocolHeader lph = AstFactoryImpl.FACTORY.LProtocolHeader(pn, roledecls, paramdecls);
		GProtocolName gpn = gpd.getFullMemberName(proj.job.getContext().getModule(proj.getModuleContext().root));
		LProtocolDecl projected = AstFactoryImpl.FACTORY.LProjectionDecl(gpd.modifiers, gpn, proj.peekSelf(), lph, def);
		return projected;
	}
	
	@Override
	public void enterValidation(ScribNode parent, ScribNode child, GProtocolValidator checker) throws ScribbleException
	{
		GProtocolDecl gpd = (GProtocolDecl) child;
		if (gpd.isAuxModifier())
		{
			return;
		}

		GProtocolName fullname = gpd.getFullMemberName((Module) parent);
		checker.job.debugPrintln("(" + fullname + ") Build and check \"fair\" output choices: ");
		buildAndCheck(checker.job, gpd, fullname, true);
		if (!checker.job.fair)
		{
			checker.job.debugPrintln("(" + fullname + ") Build and check \"unfair\" output choices: ");
			buildAndCheck(checker.job, gpd, fullname, false);
		}
	}

	private GProtocolDecl buildAndCheck(Job job, GProtocolDecl gpd, GProtocolName fullname, boolean fair) throws ScribbleException
	{
		JobContext jc = job.getContext();

		SGraph graph;
		if (fair)
		{
			graph = jc.getGlobalModel(fullname);
			if (graph == null)  // FIXME: factor into JobContext
			{
				Map<Role, EFSM> egraphs = getEndpointFSMs(job, fullname, gpd, fair);
				graph = buildGlobalModel(job, fullname, gpd, egraphs);
				jc.addGlobalModel(fullname, graph);
			}
		}
		else
		{
			graph = jc.getUnfairGlobalModel(fullname);
			if (graph == null)
			{
				Map<Role, EFSM> egraphs = getEndpointFSMs(job, fullname, gpd, fair);
				graph = buildGlobalModel(job, fullname, gpd, egraphs);
				jc.addUnfairGlobalModel(fullname, graph);
			}
		}

		new SModel(graph).validate(job);
		
		return gpd;
	}

	private Map<Role, EFSM> getEndpointFSMs(Job job, GProtocolName fullname, GProtocolDecl gpd, boolean fair) throws ScribbleException
	{
		JobContext jc = job.getContext();
		Map<Role, EFSM> egraphs = new HashMap<>();
		
		for (Role self : gpd.header.roledecls.getRoles())
		{
			EGraph graph = fair ? jc.getEndpointGraph(fullname, self) : jc.getUnfairEndpointGraph(fullname, self);
			job.debugPrintln("(" + fullname + ") EFSM (fair=" + fair + ") for " + self + ":\n" + graph.init.toDot());
			
			egraphs.put(self, graph.toFsm());
		}
		return egraphs;
	}

	

	
	// FIXME: factor out via a Util -- but not Visitor, it's not an AST algorithm
	private SGraph buildGlobalModel(Job job, GProtocolName fullname, GProtocolDecl gpd, Map<Role, EFSM> egraphs) throws ScribbleException
	{
		SBuffers b0 = new SBuffers(egraphs.keySet(), !gpd.modifiers.contains(GProtocolDecl.Modifiers.EXPLICIT));
		SConfig c0 = new SConfig(egraphs, b0);
		SState init = new SState(c0);

		Map<Integer, SState> seen = new HashMap<>();
		LinkedHashSet<SState> todo = new LinkedHashSet<>();
		todo.add(init);

		// FIXME: factor out model building and integrate with getAllNodes (seen == all)
		int count = 0;
		while (!todo.isEmpty())
		{
			Iterator<SState> i = todo.iterator();
			SState curr = i.next();
			i.remove();
			seen.put(curr.id, curr);

			if (job.debug)
			{
				count++;
				if (count % 50 == 0)
				{
					job.debugPrintln("(" + fullname + ") Building global states: " + count);
				}
			}
			
			Map<Role, List<EAction>> fireable = curr.getFireable();

			//job.debugPrintln("Acceptable at (" + curr.id + "): " + acceptable);

			for (Role r : fireable.keySet())
			{
				List<EAction> fireable_r = fireable.get(r);
				
				// Hacky?  // FIXME: factor out and make more robust (e.g. for new state kinds) -- e.g. "hasPayload" in IOAction
				//EndpointState currstate = curr.config.states.get(r);
				EFSM currfsm = curr.config.states.get(r);
				EStateKind k = currfsm.getStateKind();
				if (k == EStateKind.OUTPUT)
				{
					for (EAction a : fireable_r)  // Connect implicitly has no payload (also accept, so skip)
					{
						if (fireable_r.stream().anyMatch((x) ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribbleException("Bad non-deterministic action payloads: " + fireable_r);
						}
					}
				}
				else if (k == EStateKind.UNARY_INPUT || k == EStateKind.POLY_INPUT || k == EStateKind.ACCEPT)
				{
					for (EAction a : fireable_r)
					{
						if (currfsm.getAllFireable().stream().anyMatch((x) ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribbleException("Bad non-deterministic action payloads: " + currfsm.getAllFireable());
						}
					}
				}
			}  // Need to do all action payload checking before next building step, because doing sync actions will also remove peer's actions from takeable set

			for (Role r : fireable.keySet())
			{
				List<EAction> fireable_r = fireable.get(r);
				
				for (EAction a : fireable_r)
				{
					if (a.isSend() || a.isReceive() || a.isDisconnect())
					{
						getNextStates(todo, seen, curr, a.toGlobal(r), curr.fire(r, a));
					}
					else if (a.isAccept() || a.isConnect())
					{	
						List<EAction> as = fireable.get(a.peer);
						EAction d = a.toDual(r);
						if (as != null && as.contains(d))
						{
							as.remove(d);  // Removes one occurrence
							//getNextStates(seen, todo, curr.sync(r, a, a.peer, d));
							SAction g = (a.isConnect()) ? a.toGlobal(r) : d.toGlobal(a.peer);
							getNextStates(todo, seen, curr, g, curr.sync(r, a, a.peer, d));
						}
					}
					else if (a.isWrapClient() || a.isWrapServer())
					{
						List<EAction> as = fireable.get(a.peer);
						EAction w = a.toDual(r);
						if (as != null && as.contains(w))
						{
							as.remove(w);  // Removes one occurrence
							SAction g = (a.isConnect()) ? a.toGlobal(r) : w.toGlobal(a.peer);
							getNextStates(todo, seen, curr, g, curr.sync(r, a, a.peer, w));
						}
					}
					else
					{
						throw new RuntimeException("Shouldn't get in here: " + a);
					}
				}
			}
		}

		job.debugPrintln("(" + fullname + ") Building global model..\n" + init.toDot() + "\n(" + fullname + ") Built global model (" + count + " states)");

		return new SGraph(fullname, seen, init);
	}

	private void getNextStates(LinkedHashSet<SState> todo, Map<Integer, SState> seen, SState curr, SAction a, List<SConfig> nexts)
	{
		for (SConfig next : nexts)
		{
			SState news = new SState(next);
			SState succ = null; 
			//if (seen.contains(succ))  // FIXME: make a WFModel builder
			/*if (seen.containsValue(succ))
			{
				for (WFState tmp : seen)
				{
					if (tmp.equals(succ))
					{
						succ = tmp;
					}
				}
			}*/
			for (SState tmp : seen.values())  // Key point: checking "semantically" if model state already created
			{
				if (tmp.equals(news))
				{
					succ = tmp;
				}
			}
			if (succ == null)
			{
				for (SState tmp : todo)  // If state created but not "seen" yet, then it will be "todo"
				{
					if (tmp.equals(news))
					{
						succ = tmp;
					}
				}
			}
			if (succ == null)
			{
				succ = news;
				todo.add(succ);
			}
			//curr.addEdge(a.toGlobal(r), succ);
			curr.addEdge(a, succ);  // FIXME: make a Builder util, cf. EGraphBuilderUtil
			//if (!seen.contains(succ) && !todo.contains(succ))
			/*if (!seen.containsKey(succ.id) && !todo.contains(succ))
			{
				todo.add(succ);
			}*/
		}
	}
}

