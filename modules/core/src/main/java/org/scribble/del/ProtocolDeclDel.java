package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.NameDisambiguator;

public abstract class ProtocolDeclDel<K extends ProtocolKind> extends ScribDelBase
//public abstract class ProtocolDeclDel extends ModelDelBase
//public abstract class ProtocolDeclDelegate<T> extends ModelDelegateBase  // T should be the subclass extending ProtocolDeclDelegate
{
	//private Map<Role, Map<ProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
	
	//ProtocolDeclContext pdcontext;
	//protected ProtocolDeclContext<? extends ProtocolName<K>, K> pdcontext;
	private ProtocolDeclContext<K> pdcontext;
	//protected ProtocolDeclContext<? extends ProtocolKind> pdcontext;

	protected ProtocolDeclDel()
	{
		//this(new HashMap<>());
	}
	
	/*protected ProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		this.dependencies = cloneDependencyMap(dependencies);
	}
	
	protected abstract ProtocolDeclDelegate reconstruct(Map<Role, Map<ProtocolName, Set<Role>>> dependencies);
	
	public ProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return reconstruct(dependencies);
	}*/
	
	//protected abstract ProtocolDeclDel<K> copy();
	protected abstract ProtocolDeclDel<K> copy();
	
	@Override
	//public ProtocolDecl<? extends ProtocolKind> leaveBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker, ModelNode visited) throws ScribbleException
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		disamb.clear();
		//return cast(visited);
		return visited;
	}
	
	/*@Override
	public void enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException
	{
		builder.clearProtocolDependencies();  // collect per protocoldecl all together, do not clear?
		
		Module main = (Module) parent;
		
		ProtocolDecl<? extends ProtocolKind> pd = cast(child);
		ProtocolName<? extends ProtocolKind> pn = pd.getFullProtocolName(main);
		for (Role role : pd.header.roledecls.getRoles())
		{
			builder.addProtocolDependency(role, pn, role);  // FIXME: is it needed to add self protocol decl?
		}
	}*/
	
	/*@Override
	//public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException
	public void enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException
	{
		builder.clearProtocolDependencies();  // collect per protocoldecl all together, do not clear?
		
		JobContext jc = builder.getJobContext();
		//Module main = jc.getMainModule();
		//Module main = proj.getModuleContext();
		Module main = (Module) parent;
		
		//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
		ProtocolDecl<? extends ProtocolKind>
			pd = cast(child);

		//ProtocolName<? extends ProtocolKind> pn = pd.getFullProtocolName(main);
		ProtocolName<? extends ProtocolKind> pn = pd.getFullProtocolName(main);
		for (Role role : pd.header.roledecls.getRoles())
		{
			/*if (pd.isGlobal())
			{
				builder.addProtocolDependency(role, (GProtocolName) pn, role);  // FIXME: is it needed to add self protocol decl?
			}
			else
			{
				builder.addProtocolDependency(role, (LProtocolName) pn, role);  // FIXME: is it needed to add self protocol decl?
			}* /
			builder.addProtocolDependency(role, pn, role);  // FIXME: is it needed to add self protocol decl?
		}

		//return proj;
	}
	
	//HERE: change context builder back to separate global/local deps; invert decldel to move enter/leave context building into subclasses and use a base setter here; global/local decl contexts should keep separate global/local dep fields (maybe? -- protocol dependencies are common, but otherwise global/local contexts are too different? contexts aren't ModelNodes)

	//protected abstract void setProtocolDeclContext(DependencyMap<? extends ProtocolName<K>> deps);
	protected abstract ProtocolDeclContext<K> newProtocolDeclContext(Map<Role, Map<ProtocolName<? extends ProtocolKind>, Set<Role>>> deps);
	
	@Override
	public
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> 
			ProtocolDecl<? extends ProtocolKind>
			leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		//System.out.println("c: " + proj.getProtocolDependencies());

		//return reconstruct(this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());
		//GlobalProtocolDeclDelegate del = setDependencies(proj.getProtocolDependencies());  // FIXME: should be a deep clone in principle
		ProtocolDecl<? extends ProtocolKind> pd = cast(visited);
		//ProtocolDeclDel<K> del = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		ProtocolDeclDel<K> del = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		//del.pdcontext = new ProtocolDeclContext(builder.getGlobalProtocolDependencies());
		/*if ((pd.isGlobal()))
		{
			//del.pdcontext = (ProtocolDeclContext<K>) new GProtocolDeclContext(builder.getGlobalProtocolDependencies());
			del.pdcontext = new GProtocolDeclContext(builder.getGlobalProtocolDependencies());
			//del.pdcontext newProtocolContextxt(builder.getGlobalProtocolDependencies());
		}
		else //if ((pd.isLocal()))
		{
			//del.pdcontext = (ProtocolDeclContext<K>) new LProtocolDeclContext(builder.getLocalProtocolDependencies());
			del.pdcontext = new LProtocolDeclContext(builder.getLocalProtocolDependencies());
		}* /
		del.pdcontext = del.newProtocolDeclContext(builder.getProtocolDependencies());
		//return cast(child.del(del));  // del setter needs to be done here (access to collected dependencies) -- envLeave uses this new del (including Env setting)
		//return cast(visited.del(del));  // del setter needs to be done here (access to collected dependencies) -- envLeave uses this new del (including Env setting)
		return cast(pd.del(del));
	}*/
	
	protected ProtocolDeclDel<K> setProtocolDeclContext(ProtocolDeclContext<K> pdcontext)
	{
		ProtocolDeclDel<K> copy = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		copy.pdcontext = pdcontext;
		return copy;
	}
	
	//public ProtocolDeclContext<? extends ProtocolName<? extends ProtocolKind>, ? extends ProtocolKind> getProtocolDeclContext()
	//public ProtocolDeclContext<K> getProtocolDeclContext()
	public ProtocolDeclContext<K> getProtocolDeclContext()
	{
		return this.pdcontext;
	}
	
	/*//public Map<Role, Map<GProtocolName, Set<Role>>> getProtocolDependencies()
	//protected Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> getProtocolDependencies()
	protected Map<Role, Map<? extends ProtocolName<? extends ProtocolKind>, Set<Role>>> getProtocolDependencies()
	{
		//return this.dependencies;
		return this.pdcontext.getDependencies();
	}*/
	
	/*private static
		//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
		ProtocolDecl<? extends ProtocolKind>
		cast(ModelNode child)
	{
		ProtocolDecl.class.cast(child);
		//if (ProtocolDecl.class.isAssignableFrom(child.getClass()))
		{
			@SuppressWarnings("unchecked")
			//AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
			ProtocolDecl<? extends ProtocolKind>
				//pd = (AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>) child;
				pd = (ProtocolDecl<? extends ProtocolKind>) child;
			return pd;
		}
		//throw new RuntimeException("Bad ProtocolDecl cast: " + child.getClass());
	}*/

	/*private static Map<Role, Map<ProtocolName, Set<Role>>> cloneDependencyMap(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		Map<Role, Map<ProtocolName, Set<Role>>> clone = new HashMap<>();
		for (Role role : dependencies.keySet())
		{
			Map<ProtocolName, Set<Role>> vals = dependencies.get(role);
			Map<ProtocolName, Set<Role>> tmp1 = clone.get(role);
			if (tmp1 == null)
			{
				tmp1 = new HashMap<>();
				clone.put(role, tmp1);
			}
			for (ProtocolName pn : vals.keySet())
			{
				Set<Role> tmp2 = tmp1.get(pn);
				if (tmp2 == null)
				{
					tmp2 = new HashSet<>();
					tmp1.put(pn, tmp2);
				}
				tmp2.addAll(vals.get(pn));
			}
		}
		return clone;
	}*/
}
