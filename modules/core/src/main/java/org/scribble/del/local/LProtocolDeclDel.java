package org.scribble.del.local;

import org.scribble.ast.ScribNode;
import org.scribble.ast.Module;
import org.scribble.ast.context.LProtocolDeclContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.visit.ContextBuilder;
import org.scribble.ast.visit.FsmConstructor;
import org.scribble.ast.visit.JobContext;
import org.scribble.del.ProtocolDeclDel;
import org.scribble.model.local.ScribbleFsm;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribbleException;

public class LProtocolDeclDel extends ProtocolDeclDel<Local>
//public class LProtocolDeclDel extends ProtocolDeclDel
{
	public LProtocolDeclDel()
	{

	}

	@Override
	public void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder) throws ScribbleException
	{
		builder.clearProtocolDependencies();  // collect per protocoldecl all together, do not clear?
		
		Module main = (Module) parent;
		
		LProtocolDecl lpd = (LProtocolDecl) child;
		LProtocolName lpn = lpd.getFullProtocolName(main);
		for (Role role : lpd.header.roledecls.getRoles())
		{
			builder.addProtocolDependency(role, lpn, role);  // FIXME: is it needed to add self protocol decl?
		}
	}
	
	@Override
	public LProtocolDecl leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		//System.out.println("c: " + proj.getProtocolDependencies());

		LProtocolDecl gpd = (LProtocolDecl) visited;
		/*LProtocolDeclDel del = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		del.setProtocolDeclContext(new LProtocolDeclContext(builder.getLocalProtocolDependencies()));*/
		LProtocolDeclContext gcontext = new LProtocolDeclContext(builder.getLocalProtocolDependencyMap());
		LProtocolDeclDel del = (LProtocolDeclDel) setProtocolDeclContext(gcontext);
		return (LProtocolDecl) gpd.del(del);
	}

	@Override
	public void enterFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor conv)// throws ScribbleException
	{
		//pushVisitorEnv(parent, child, conv);
		conv.builder.reset();
	}

	@Override
	public ScribNode leaveFsmConstruction(ScribNode parent, ScribNode child, FsmConstructor conv, ScribNode visited)// throws ScribbleException
	{
		LProtocolDecl lpd = (LProtocolDecl) visited;
		//ScribbleFsm f = ((FsmBuildingEnv) lpd.def.del().env()).getFsm();
		//store graphs/fsms in jobcontext; bit like projections, but store in jc here directly, don't wait for ModuleDel
		ScribbleFsm fsm = new ScribbleFsm(conv.builder.getEntry(), conv.builder.getExit());
		JobContext jc = conv.getJobContext();
		jc.addFsm(lpd.getFullProtocolName((Module) parent), fsm);
		return visited;
	}
	
	@Override
	public LProtocolDeclContext getProtocolDeclContext()
	{
		return (LProtocolDeclContext) super.getProtocolDeclContext();
	}
	
	/*@Override
	//protected DependencyMap<? extends ProtocolName<? extends ProtocolKind>> newDependencyMap()
	protected DependencyMap<LProtocolName> newDependencyMap()
	{
		return new DependencyMap<LProtocolName>();
	}*/

	/*protected LocalProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		super(dependencies);
	}

	@Override
	protected LocalProtocolDeclDelegate reconstruct(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return new LocalProtocolDeclDelegate(dependencies);
	}*/

	@Override
	protected LProtocolDeclDel copy()
	{
		return new LProtocolDeclDel();
	}

	/*@Override
	public LocalProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return (LocalProtocolDeclDelegate) super.setDependencies(dependencies);
	}*/

	/*@Override
	protected LProtocolDeclContext newProtocolDeclContext(Map<Role, Map<ProtocolName<? extends ProtocolKind>, Set<Role>>> deps)
	{
		return new LProtocolDeclContext(new DependencyMap<>(cast(deps)));
	}

	private static Map<Role, Map<LProtocolName, Set<Role>>> cast(Map<Role, Map<ProtocolName<? extends ProtocolKind>, Set<Role>>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((r) -> r, (r) -> castAux(map.get(r))));
	}

	private static Map<LProtocolName, Set<Role>> castAux(Map<ProtocolName<? extends ProtocolKind>, Set<Role>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((k) -> (LProtocolName) k, (k) -> map.get(k)));
	}*/
}
