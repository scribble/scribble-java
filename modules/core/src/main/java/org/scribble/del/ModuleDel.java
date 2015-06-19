package org.scribble.del;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.ast.ImportDecl;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.ast.local.LProtocolHeader;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ContextBuilder;
import org.scribble.visit.JobContext;
import org.scribble.visit.Projector;

public class ModuleDel extends ScribDelBase
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
	public ModuleDel()
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
	private ModuleDel copy()
	{
		return new ModuleDel();
	}

	@Override
	//public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder)
	public void enterContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder)
	{
		builder.setModuleContext(new ModuleContext(builder.getJobContext(), (Module) child));
		//return builder;
	}

	// Maybe better to create on enter, so can be used during the context build pass (Context would need to be "cached" in the visitor to be accessed)
	@Override
	public Module leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		//visited = visited.del(new ModuleDelegate(builder.getJobContext(), (Module) visited));
		ModuleDel del = copy();  // FIXME: should be a deep clone in principle
		//del.context = new ModuleContext(builder.getJobContext(), (Module) visited);
		del.context = builder.getModuleContext();
		return (Module) visited.del(del);
	}

	@Override
	public Module leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) //throws ScribbleException
	{
		Module mod = (Module) visited;
		// .. store projections for each globalprotocoldecl in module and context and return new module with updated context
		JobContext jcontext = proj.getJobContext();
		//proj.getProjections()
		//NodeContextBuilder builder = new NodeContextBuilder(proj.job);

		jcontext.addProjections(proj.getProjections());
		
		return mod;
	}

	public Module createModuleForProjection(Projector proj, Module root, LProtocolDecl lpd, Map<GProtocolName, Set<Role>> dependencies)
	{
		//.. store projection module in context? do this earlier?
		//.. look up all projection sets from global protocol decls and store somewhere? in context?

		//ModuleNameNode modname = Projector.makeProjectedModuleNameNodes(main.moddecl.fullmodname.toName(), lpd.header.name.toName());
		//ModuleNameNode modname = Projector.makeProjectedModuleNameNodes(root.moddecl.fullmodname.toName(), lpd.header.name.toCompoundName());
		//ModuleNameNode modname = Projector.makeProjectedModuleNameNodes(root.moddecl.fullmodname.toName(), lpd.header.name.toName());
		ModuleNameNode modname = Projector.makeProjectedModuleNameNodes(root.moddecl.getFullModuleName(), ((LProtocolHeader) lpd.header).getDeclName());
		
		//.. factor out full name making, use also for do
		//.. record protocol dependencies in context

		//ModuleDecl moddecl = new ModuleDecl(modname);
		ModuleDecl moddecl = AstFactoryImpl.FACTORY.ModuleDecl(modname);
		List<ImportDecl<? extends Kind>> imports = new LinkedList<>();  // Need names from do
		
		for (GProtocolName gpn : dependencies.keySet())
		{
			Set<Role> tmp = dependencies.get(gpn);
			for (Role role : tmp)
			{
				LProtocolNameNode targetfullname = Projector.makeProjectedProtocolNameNode(gpn, role);
				//SimpleProtocolNameNode targetsimname = Projector.makeProjectedLocalName(gpn.getSimpleName(), role);
				LProtocolNameNode targetsimname = Projector.makeProjectedLocalName(gpn.getSimpleName(), role);

				//ModuleNameNode targetmodname = Projector.makeProjectedModuleNameNodes(main.getFullModuleName(), targetsimname.toName());
				//ModuleNameNode targetmodname = Projector.makeProjectedModuleNameNodes(root.getFullModuleName(), targetsimname.toCompoundName());
				//ModuleNameNode targetmodname = Projector.makeProjectedModuleNameNodes(gpn.getPrefix(), targetsimname.toCompoundName());
				ModuleNameNode targetmodname = Projector.makeProjectedModuleNameNodes(gpn.getPrefix(), targetsimname.toName());

				if (!targetfullname.toName().getPrefix().equals(modname.toName()))
				{
					//imports.add(new ImportModule(targetmodname, null));
					imports.add(AstFactoryImpl.FACTORY.ImportModule(targetmodname, null));
				}
			}
		}
		
		List<NonProtocolDecl<? extends Kind>> data = new LinkedList<>(root.data);  // FIXME: copy  // FIXME: only project dependencies
		//List<LocalProtocolDecl> protos = Arrays.asList(lpd);
		List<ProtocolDecl<? extends ProtocolKind>> protos = Arrays.asList(lpd);
		//return new Module(moddecl, imports, data, protos);
		return AstFactoryImpl.FACTORY.Module(moddecl, imports, data, protos);
	}
	
	/*public ProtocolName getFullProtocolDeclName(ProtocolName visname)
	{
		/* // Global and local protocol names (all member names) are distinct by well-formedness
		if (this.globals.containsKey(visname))
		{
			return this.globals.get(visname);
		}
		else if (this.locals.containsKey(visname))
		{
			return this.locals.get(visname);
		}
		throw new RuntimeException("Protocol name not visible: " + visname);* /
		return this.context.getFullProtocolDeclName(visname);
	}*/
	
	@Override 
	public String toString()
	{
		//return "[modules=" + this.modules + ", types=" + this.types + ", sigs=" + this.sigs + ", globals=" + this.globals + ", locals=" + this.locals;
		return (this.context == null) ? null : this.context.toString();  // null in and before context building
	}

	/*private ProtocolName getFullProtocolName(
			ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		ModuleName fullmodname = this.root.getFullModuleName();
		return new ProtocolName(fullmodname, pd.name.toString());
	}*/
	
	public ModuleContext getModuleContext()
	{
		return this.context;
	}
}
