package org.scribble2.model.del;

import java.util.Map;
import java.util.Set;

import org.scribble2.model.InteractionNode;
import org.scribble2.model.InteractionSequence;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Module;
import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.AbstractProtocolDecl;
import org.scribble2.model.ProtocolDefinition;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.context.ProtocolDeclContext;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.JobContext;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public abstract class ProtocolDeclDelegate extends ModelDelegateBase
//public abstract class ProtocolDeclDelegate<T> extends ModelDelegateBase  // T should be the subclass extending ProtocolDeclDelegate
{
	//private Map<Role, Map<ProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
	
	ProtocolDeclContext pdcontext;

	protected ProtocolDeclDelegate()
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
	
	protected abstract ProtocolDeclDelegate copy();

	@Override
	//public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder proj) throws ScribbleException
	public void enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder proj) throws ScribbleException
	{
		proj.clearProtocolDependencies();  // collect per protocoldecl all together, do not clear?
		
		JobContext jc = proj.getJobContext();
		//Module main = jc.getMainModule();
		//Module main = proj.getModuleContext();
		Module main = (Module) parent;
		
		AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
			pd = cast(child);

		ProtocolName pn = pd.getFullProtocolName(main);

		for (Role role : pd.header.roledecls.getRoles())
		{
			proj.addProtocolDependency(role, pn, role);  // FIXME: is it needed to add self protocol decl?
		}

		//return proj;
	}
	
	@Override
	public AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> 
			leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		//System.out.println("c: " + proj.getProtocolDependencies());

		//return reconstruct(this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());
		//GlobalProtocolDeclDelegate del = setDependencies(proj.getProtocolDependencies());  // FIXME: should be a deep clone in principle
		ProtocolDeclDelegate del = copy();  // FIXME: should be a deep clone in principle -- but if any other children are immutable, they can be shared
		del.pdcontext = new ProtocolDeclContext(builder.getProtocolDependencies());
		return cast(child.del(del));  // del setter needs to be done here (access to collected dependencies) -- envLeave uses this new del (including Env setting)
	}
	
	public ProtocolDeclContext getProtocolDeclContext()
	{
		return this.pdcontext;
	}
	
	public Map<Role, Map<ProtocolName, Set<Role>>> getProtocolDependencies()
	{
		//return this.dependencies;
		return this.pdcontext.getDependencies();
	}
	
	private AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
		cast(ModelNode child)
	{
		AbstractProtocolDecl.class.cast(child);
		//if (ProtocolDecl.class.isAssignableFrom(child.getClass()))
		{
			@SuppressWarnings("unchecked")
			AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
				pd = (AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>) child;
			return pd;
		}
		//throw new RuntimeException("Bad ProtocolDecl cast: " + child.getClass());
	}

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
