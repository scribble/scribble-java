package org.scribble2.model.del;

import java.util.Arrays;
import java.util.HashMap;
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
import org.scribble2.model.global.GlobalProtocolDecl;
import org.scribble2.model.local.LocalProtocolDecl;
import org.scribble2.model.name.qualified.ModuleNameNode;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.model.visit.ContextBuilder;
import org.scribble2.model.visit.JobContext;
import org.scribble2.model.visit.Projector;
import org.scribble2.sesstype.name.MessageSignatureName;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.PayloadType;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleException;

public class ModuleDelegate extends ModelDelegateBase
{
	//public final Module root;
	public final ModuleName root;

	// The modules and member names that are visible from this Module
	private final Map<ModuleName, ModuleName> modules;
	//private final Map<PayloadTypeOrParameter, PayloadTypeOrParameter> data;  // FIXME: refactor properly for sig members
	private final Map<PayloadType, PayloadType> types;
	private final Map<MessageSignatureName, MessageSignatureName> sigs;
	private final Map<ProtocolName, ProtocolName> globals;
	private final Map<ProtocolName, ProtocolName> locals;

	public ModuleDelegate(ModuleName root)
	{
		this.root = root;

		this.modules = null;
		this.types = null;
		this.sigs = null;
		this.globals = null;
		this.locals = null;
	}

	// ModuleContext is the root context
	protected ModuleDelegate(JobContext jcontext, Module root)
	{
		ModuleName fullmodname = root.getFullModuleName(); 

		this.root = fullmodname;
		
		this.modules = new HashMap<>();
		//this.data = new HashMap<>();
		this.types = new HashMap<>();
		this.sigs = new HashMap<>();
		this.globals = new HashMap<>();
		this.locals = new HashMap<>();
		
		addModule(root, fullmodname);
		addLocalMembers(root, fullmodname);
		
		//addImportedModules(jcontext);
		//addMembers();
	}

	/*@Override
	public ContextBuilder enterContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder) throws ScribbleException
	{
		return builder;
	}*/

	@Override
	public ModelNode leaveContextBuilding(ModelNode parent, ModelNode child, ContextBuilder builder, ModelNode visited) throws ScribbleException
	{
		visited = visited.del(new ModuleDelegate(builder.getJobContext(), (Module) visited));
		return visited;
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

	/*protected ModuleContext(ModuleContext mcontext)
	{
		this.root = mcontext.root;
		this.modules = new HashMap<>(mcontext.modules);
		//this.data = new HashMap<>(mcontext.data);
		this.types = new HashMap<>(mcontext.types);
		this.sigs = new HashMap<>(mcontext.sigs);
		this.globals = new HashMap<>(mcontext.globals);
		this.locals = new HashMap<>(mcontext.locals);
	}
	
	@Override
	public ModuleContext copy()
	{
		return new ModuleContext(this);
	}*/
	
	// vismodname can be the full module name or the import alias
	private void addModule(Module mod, ModuleName vismodname)
	{
		this.modules.put(vismodname, mod.getFullModuleName());
		/*for (DataTypeDecl dtd : mod.data)
		{
			// FIXME: refactor properly
			if (dtd instanceof PayloadTypeDecl)
			{
				PayloadType fullname = ((PayloadTypeDecl) dtd).getFullPayloadTypeName();
				PayloadType visname = new PayloadType(vismodname, dtd.alias.toString());
				this.types.put(visname, fullname);
			}
			else //if (dtd instanceof MessageSignatureDecl)
			{
				// FIXME: compound full name for declared sig
				MessageSignatureName fullname = ((MessageSignatureDecl) dtd).getFullMessageSignatureName();
				MessageSignatureName visname = ((MessageSignatureDecl) dtd).getAliasName();
				this.sigs.put(visname, fullname);
			}
		}*/
		for (GlobalProtocolDecl gpd : mod.getGlobalProtocolDecls())
		{
			ProtocolName fullname = gpd.getFullProtocolName(mod);
			ProtocolName visname = new ProtocolName(vismodname, gpd.header.name.toString());
			this.globals.put(visname, fullname);
		}
		/*for (LocalProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			ProtocolName fullname = lpd.getFullProtocolName(mod);
			//ProtocolName fullname = getFullProtocolName(lpd);
			ProtocolName visname = new ProtocolName(vismodname, lpd.name.toString());
			this.locals.put(visname, fullname);
		}*/
	}
	
	private void addLocalMembers(Module m, ModuleName fullmodname)
	{
		ModuleName simplemodname = m.getFullModuleName().getSimpleName();
		/*for (DataTypeDecl dtd : m.data)
		{
			if (dtd instanceof PayloadTypeDecl)
			{
				//PayloadType simplename = ptd.alias.toName();
				PayloadType simplename = ((PayloadTypeDecl) dtd).getAliasName();
				PayloadType fullname = ((PayloadTypeDecl) dtd).getFullPayloadTypeName();
				PayloadType selfname = new PayloadType(simplemodname, simplename.toString());
				//this.data.put(simplename, fullname);
				//this.data.put(selfname, fullname);
				this.types.put(simplename, fullname);
				this.types.put(selfname, fullname);
			}
			else //if (dtd instanceof MessageSignatureDecl)
			{
				MessageSignatureName simplename = ((MessageSignatureDecl) dtd).getAliasName();
				MessageSignatureName fullname = ((MessageSignatureDecl) dtd).getFullMessageSignatureName();  // FIXME: compound full sig name
				MessageSignatureName selfname = new MessageSignatureName(simplemodname, simplename.toString());  // FIXME: hack
				this.sigs.put(simplename, fullname);
				this.sigs.put(selfname, fullname);
			}
		}*/
		for (GlobalProtocolDecl gpd : m.getGlobalProtocolDecls())
		{
			ProtocolName simplename = gpd.header.name.toName();
			ProtocolName fullname = new ProtocolName(fullmodname, simplename.toString());
			ProtocolName selfname = new ProtocolName(simplemodname, simplename.toString());
			this.globals.put(simplename, fullname);
			this.globals.put(selfname, fullname);
		}
		/*for (LocalProtocolDecl lpd : m.getLocalProtocolDecls())
		{
			ProtocolName simplename = lpd.name.toName();
			ProtocolName fullname = new ProtocolName(fullmodname, simplename.toString());
			ProtocolName selfname = new ProtocolName(simplemodname, simplename.toString());
			this.locals.put(simplename, fullname);
			this.locals.put(selfname, fullname);
		}*/
	}
	
	/*private void addImportedModules(JobContext jcontext)
	{
		for (ImportDecl id : this.root.imports)
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullmodname = im.modname.toName();
				if (!this.modules.keySet().contains(fullmodname))
				{
					ModuleName modname = (im.isAliased()) ? im.getAlias() : fullmodname;
					addModule(jcontext.getModule(fullmodname), modname);
				}
			}
			else
			{
				throw new RuntimeException("TODO: " + id);
			}
		}
	}*/

	/*// Separate the above into adders
	public void addLocalProtocolDecl(LocalProtocolDecl lpd)
	{
		this.locals.put(lpd., value);
	}* /

	public boolean isModuleVisible(ModuleName modname)
	{
		return this.modules.keySet().contains(modname);
	}

	public boolean isPayloadTypeVisible(PayloadType typename)
	{
		return this.types.keySet().contains(typename);
	}

	public boolean isMessageSignatureNameVisible(MessageSignatureName signame)
	{
		return this.sigs.keySet().contains(signame);
	}

	public boolean isGlobalProtocolVisible(ProtocolName proto)
	{
		return this.globals.keySet().contains(proto);
	}

	public boolean isLocalProtocolVisible(ProtocolName proto)
	{
		return this.locals.keySet().contains(proto);
	}
	
	public ModuleName getFullModuleName(ModuleName visname)
	{
		return ModuleContext.getFullName(this.modules, visname);
	}
	
	public PayloadType getFullPayloadTypeName(PayloadType visname)
	{
		return ModuleContext.getFullName(this.types, visname);
	}

	public MessageSignatureName getFullMessageSignatureName(MessageSignatureName visname)
	{
		return ModuleContext.getFullName(this.sigs, visname);
	}

	/*public ProtocolName getFullGlobalProtocolDeclName(ProtocolName visname)
	{
		return ModuleContext.getFullName(this.globals, visname);
	}

	public ProtocolName getFullLocalProtocolDeclName(ProtocolName visname)
	{
		return ModuleContext.getFullName(this.locals, visname);
	}* /

	private static <T extends Name> T getFullName(Map<T, T> map, T visname)
	{
		if (!map.containsKey(visname))
		{
			throw new RuntimeException("Name not visible: " + visname);
		}
		return map.get(visname);
	}*/
	
	public ProtocolName getFullProtocolDeclName(ProtocolName visname)
	{
		// Global and local protocol names (all member names) are distinct by well-formedness
		if (this.globals.containsKey(visname))
		{
			return this.globals.get(visname);
		}
		else if (this.locals.containsKey(visname))
		{
			return this.locals.get(visname);
		}
		throw new RuntimeException("Protocol name not visible: " + visname);
	}
	
	@Override 
	public String toString()
	{
		return "[modules=" + this.modules + ", types=" + this.types + ", sigs=" + this.sigs + ", globals=" + this.globals + ", locals=" + this.locals;
	}

	/*private ProtocolName getFullProtocolName(
			ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		ModuleName fullmodname = this.root.getFullModuleName();
		return new ProtocolName(fullmodname, pd.name.toString());
	}*/
}
