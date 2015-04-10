package org.scribble2.model.del;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble2.model.DataTypeDecl;
import org.scribble2.model.ImportDecl;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ModelNode;
import org.scribble2.model.Module;
import org.scribble2.model.ModuleDecl;
import org.scribble2.model.context.ModuleContext;
import org.scribble2.model.local.LocalProtocolDecl;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.Projector;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class ModuleDelegate extends ModelDelegateBase
{
	private ModuleContext context;
	
	/*//public final Module root;
	public final ModuleName root;

	// The modules and member names that are visible from this Module
	private final Map<ModuleName, ModuleName> modules;
	//private final Map<PayloadTypeOrParameter, PayloadTypeOrParameter> data;  // FIXME: refactor properly for sig members
	private final Map<PayloadType, PayloadType> types;
	private final Map<MessageSignatureName, MessageSignatureName> sigs;
	private final Map<ProtocolName, ProtocolName> globals;
	private final Map<ProtocolName, ProtocolName> locals;*/

	// Used by parser and projection
	//public ModuleDelegate(ModuleName root)
	public ModuleDelegate()
	{

	}

	/*// Used by ContextBuilder
	// ModuleContext is the root context
	protected ModuleDelegate(JobContext jcontext, Module root)
	{
	}*/

	/*@Override
	public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException
	{
		return builder;
	}*/

	//@Override
	private ModuleDelegate copy()
	{
		return new ModuleDelegate();
	}

	@Override
	//public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder)
	public void enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder)
	{
		//builder.setModuleContext(new ModuleContext(builder.job.getJobContext(), (Module) child));
		builder.setModuleContext(new ModuleContext((Module) child));
		//return builder;
	}

	// Maybe better to create on enter, so can be used during the context build pass (Context would need to be "cached" in the visitor to be accessed)
	@Override
	public ModelNode leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		//visited = visited.del(new ModuleDelegate(builder.getJobContext(), (Module) visited));
		ModuleDelegate del = copy();  // FIXME: should be a deep clone in principle
		//del.context = new ModuleContext(builder.getJobContext(), (Module) visited);
		del.context = builder.getModuleContext();
		return visited.del(del);
	}

	@Override
	public Module leaveProjection(ModelNode parent, ModelNode child, Projector proj, ModelNode visited) //throws ScribbleException
	{
		Module mod = (Module) visited;
		// .. store projections for each globalprotocoldecl in module and context and return new module with updated context
		JobContext jcontext = proj.getJobContext();
		//proj.getProjections()
		//NodeContextBuilder builder = new NodeContextBuilder(proj.job);

		jcontext.addProjections(proj.getProjections());
		
		return mod;
	}

	public Module createModuleForProjection(Projector proj, Module main, LocalProtocolDecl lpd, Map<ProtocolName, Set<Role>> dependencies)
	{
		//.. store projection module in context? do this earlier?
		//.. look up all projection sets from global protocol decls and store somewhere? in context?
		
		ModuleNameNode modname = Projector.makeProjectedModuleNameNodes(main.moddecl.fullmodname.toName(), lpd.header.name.toName());
		
		//.. factor out full name making, use also for do
		//.. record protocol dependencies in context

		//ModuleDecl moddecl = new ModuleDecl(modname);
		ModuleDecl moddecl = ModelFactoryImpl.FACTORY.ModuleDecl(modname);
		List<ImportDecl> imports = new LinkedList<>();  // Need names from do
		
		for (ProtocolName gpn : dependencies.keySet())
		{
			Set<Role> tmp = dependencies.get(gpn);
			for (Role role : tmp)
			{
				ProtocolNameNode targetfullname = Projector.makeProjectedProtocolNameNode(gpn, role);
				SimpleProtocolNameNode targetsimname = proj.makeProjectedLocalName(gpn.getSimpleName(), role);
				ModuleNameNode targetmodname = Projector.makeProjectedModuleNameNodes(main.getFullModuleName(), targetsimname.toName());
				if (!targetfullname.toName().getPrefix().equals(modname.toName()))
				{
					//imports.add(new ImportModule(targetmodname, null));
					imports.add(ModelFactoryImpl.FACTORY.ImportModule(targetmodname, null));
				}
			}
		}
		
		List<DataTypeDecl> data = new LinkedList<>(main.data);  // FIXME: copy  // FIXME: only project dependencies
		List<LocalProtocolDecl> protos = Arrays.asList(lpd);
		//return new Module(moddecl, imports, data, protos);
		return ModelFactoryImpl.FACTORY.Module(moddecl, imports, data, protos);
	}
	
	public ProtocolName getFullProtocolDeclName(ProtocolName visname)
	{
		/*// Global and local protocol names (all member names) are distinct by well-formedness
		if (this.globals.containsKey(visname))
		{
			return this.globals.get(visname);
		}
		else if (this.locals.containsKey(visname))
		{
			return this.locals.get(visname);
		}
		throw new RuntimeException("Protocol name not visible: " + visname);*/
		return this.context.getFullProtocolDeclName(visname);
	}
	
	@Override 
	public String toString()
	{
		//return "[modules=" + this.modules + ", types=" + this.types + ", sigs=" + this.sigs + ", globals=" + this.globals + ", locals=" + this.locals;
		return this.context.toString();
	}

	/*private ProtocolName getFullProtocolName(
			ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		ModuleName fullmodname = this.root.getFullModuleName();
		return new ProtocolName(fullmodname, pd.name.toString());
	}*/
}
