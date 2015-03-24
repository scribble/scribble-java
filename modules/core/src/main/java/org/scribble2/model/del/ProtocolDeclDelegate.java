package org.scribble2.model.del;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble2.model.ModelNode;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class ProtocolDeclDelegate extends ModelDelegateBase
{
	//private final ModuleDelegate mcontext;
	
	private final Map<Role, Map<ProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root

	//public ProtocolDeclDelegate(ModuleDelegate parent, ProtocolDecl<? extends ProtocolDefinition<ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	//public ProtocolDeclDelegate(ModuleDelegate mcontext)
	public ProtocolDeclDelegate()
	{
		//this.mcontext = mcontext;
		this.dependencies = null;
	}
	
	public ProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		//this.dependencies = new HashSet<>(pdcontext.dependencies);
		super();
		//this.dependencies.putAll(dependencies);
		//merge(this.dependencies, dependencies);
		this.dependencies = clone(dependencies);
	}
	
	/*public ProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		ProtocolDeclDelegate del = new ProtocolDeclDelegate();
		//del.dependencies = clone(dependencies);
		del.dependencies.putAll(dependencies);  // Rely on defensive copy treatment
		return del;
	}*/

	private static Map<Role, Map<ProtocolName, Set<Role>>> clone(Map<Role, Map<ProtocolName, Set<Role>>> map)
	{
		Map<Role, Map<ProtocolName, Set<Role>>> clone = new HashMap<>();
		for (Role role : map.keySet())
		{
			Map<ProtocolName, Set<Role>> vals = map.get(role);
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
	}
	
	/*protected ProtocolDeclDelegate(ProtocolDeclDelegate pdcontext)
	{
		//this.mcontext = pdcontext.mcontext;
		//this.dependencies = new HashMap<>(pdcontext.dependencies);
		/*this.dependencies = new HashMap<>();
		if (pdcontext.dependencies != null)
		{
			//merge(this.dependencies, pdcontext.dependencies);
		}* /
		this(pdcontext.dependencies);
	}*/
	
	@Override
	public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException
	{
		/*builder.clearProtocolDependencies();
		//ProtocolDeclDelegate pdcontext = new ProtocolDeclDelegate();  // FIXME: use replaceProtocolDeclContext in builder? (here this initial value not used, set properly on leave)
		builder.pushContext(pdcontext);*/
		/*ProtocolName fpn = ((ProtocolDecl) child).getFullProtocolName((Module) parent);
		if (builder.isRootContext())
		{
			builder.pushRoot(fpn);
		}
		builder.addProtocolDependency(fpn);*/
		//builder.pushContext(new CompoundInteractionContext(pdcontext));  // Not needed: def block pushes a context
		return builder;
	}

	@Override
	/*public <T1 extends ProtocolHeader, T2 extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
			ProtocolDecl<T1, T2> leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException*/
	/*public ProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> 
			leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException*/
	public ModelNode leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		/*this.setContext(builder.popContext());
		return this;* /
		//CompoundInteractionContext cicontext = (CompoundInteractionContext)
		//builder.popContext();  // Associate to the def? -- not currently merged into the protocol decl context
		//return new ProtocolDecl<T>(this.ct, this.name, this.roledecls, this.paramdecls, this.def, (ProtocolDeclContext) builder.popContext());
		
		//ProtocolDeclContext pdcontext = new ProtocolDeclContext((ProtocolDeclContext) builder.popContext(), builder.getProtocolDependencies());
		ProtocolDeclDelegate pdcontext = (ProtocolDeclDelegate) builder.popContext();
		//return reconstruct(this.ct, this.name, this.roledecls, this.paramdecls, this.def, pdcontext, getEnv());*/
		
		//return (ProtocolDecl) visited.del(null);
		return visited;
	}

	/*private static Map<Role, Map<ProtocolName, Set<Role>>> clone(Map<Role, Map<ProtocolName, Set<Role>>> map)
	{
		Map<Role, Map<ProtocolName, Set<Role>>> clone = new HashMap<>();
		for (Role role : map.keySet())
		{
			Map<ProtocolName, Set<Role>> vals = map.get(role);
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
	
	/*private static void merge(Map<Role, Map<ProtocolName, Set<Role>>> map1, Map<Role, Map<ProtocolName, Set<Role>>> map2)
	{
		for (ProtocolName pn : map2.keySet())
		{
			Set<Role> tmp = map1.get(pn);
			if (tmp == null)
			{
				tmp = new HashSet<>();
				map1.put(pn, tmp);
			}
			tmp.addAll(map2.get(pn));
		}
	}*/
	
	public Map<Role, Map<ProtocolName, Set<Role>>> getProtocolDependencies()
	{
		return this.dependencies;
	}

	/*@Override 
	public String toString()
	{
		return "[modules=" + this.modules + ", types=" + this.types + ", globals=" + this.globals + ", locals=" + this.locals;
	}*/
	
	/*public ModuleDelegate getModuleDelegate()
	{
		return this.mcontext;
	}* /	
	
	...
	
	@Override
	public NameDisambiguator enterDisambiguation(ModelNode n, NameDisambiguator disamb) throws ScribbleException
	{
		// Could do in protocolheader enter/leave
		disamb.reset();
		return disamb;
	}*/
}
