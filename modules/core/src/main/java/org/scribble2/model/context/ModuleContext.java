package org.scribble2.model.context;

import java.util.HashMap;
import java.util.Map;

import org.scribble2.model.DataTypeDecl;
import org.scribble2.model.ImportDecl;
import org.scribble2.model.ImportModule;
import org.scribble2.model.MessageSigDecl;
import org.scribble2.model.Module;
import org.scribble2.model.NonProtocolDecl;
import org.scribble2.model.global.GProtocolDecl;
import org.scribble2.model.local.LProtocolDecl;
import org.scribble2.model.visit.JobContext;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.kind.SigKind;
import org.scribble2.sesstype.name.DataType;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.sesstype.name.MessageSigName;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.ProtocolName;

public class ModuleContext
{
	//public final Module root;
	public final ModuleName root;

	// The modules and member names that are visible from this Module -- mapped to "cannonical" (fully qualified) names
	private final Map<ModuleName, ModuleName> modules;
	//private final Map<PayloadTypeOrParameter, PayloadTypeOrParameter> data;  // FIXME: refactor properly for sig members
	private final Map<DataType, DataType> data;
	private final Map<MessageSigName, MessageSigName> sigs;
	private final Map<GProtocolName, GProtocolName> globals;
	private final Map<LProtocolName, LProtocolName> locals;

	// Made by ContextBuilder
	// ModuleContext is the root context
	public ModuleContext(JobContext jcontext, Module root)
	{
		ModuleName fullmodname = root.getFullModuleName(); 

		this.root = fullmodname;
		
		this.modules = new HashMap<>();
		//this.data = new HashMap<>();
		this.data = new HashMap<>();
		this.sigs = new HashMap<>();
		this.globals = new HashMap<>();
		this.locals = new HashMap<>();
		
		addModule(root, fullmodname);
		addLocalMembers(root);//, fullmodname);
		
		addImportedModules(jcontext, jcontext.getModule(this.root));
		//addMembers();
		
		//System.out.println("1: " + this);
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
		for (GProtocolDecl gpd : mod.getGlobalProtocolDecls())
		{
			GProtocolName fullname = gpd.getFullProtocolName(mod);
			//ProtocolName visname = new ProtocolName(vismodname, gpd.header.name.toString());
			GProtocolName visname = new GProtocolName(vismodname, gpd.header.getDeclName());
			this.globals.put(visname, fullname);
		}
		for (LProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			LProtocolName fullname = lpd.getFullProtocolName(mod);
			//ProtocolName fullname = getFullProtocolName(lpd);
			//ProtocolName visname = new ProtocolName(vismodname, lpd.header.name.toString());
			LProtocolName visname = new LProtocolName(vismodname, lpd.header.getDeclName());
			this.locals.put(visname, fullname);
		}
	}
	
	private void addLocalMembers(Module m)//, ModuleName fullmodname)
	{
		ModuleName fullmodname = m.getFullModuleName();
		ModuleName simplemodname = fullmodname.getSimpleName();
		for (NonProtocolDecl<? extends Kind> dtd : m.data)
		{
			if (dtd.isDataTypeDecl())
			{
				DataType simplename = ((DataTypeDecl) dtd).getDeclName();
				DataType fullname = new DataType(fullmodname, simplename);
				DataType selfname = new DataType(simplemodname, simplename);
				this.data.put(simplename, fullname);
				this.data.put(selfname, fullname);
			}
			else if (dtd.isMessageSigDecl())
			{
				MessageSigName simplename = ((MessageSigDecl) dtd).getDeclName();
				//MessageSigName fullname = ((MessageSigDecl) dtd).getFullMessageSignatureName();  // FIXME: compound full sig name
				MessageSigName fullname = new MessageSigName(fullmodname, simplename);
				MessageSigName selfname = new MessageSigName(simplemodname, simplename);
				this.sigs.put(simplename, fullname);
				this.sigs.put(selfname, fullname);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + dtd);
			}
		}
		for (GProtocolDecl gpd : m.getGlobalProtocolDecls())
		{
			//ProtocolName simplename = gpd.header.name.toName();
			/*ProtocolName simplename = gpd.header.name.toCompoundName();
			ProtocolName fullname = new ProtocolName(fullmodname, simplename.toString());
			ProtocolName selfname = new ProtocolName(simplemodname, simplename.toString());*/
			GProtocolName simplename = (GProtocolName) gpd.header.getDeclName();
			GProtocolName fullname = new GProtocolName(fullmodname, simplename);
			GProtocolName selfname = new GProtocolName(simplemodname, simplename);
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
	
	// Could move to ImportModule but would need a defensive copy setter, or cache info in builder and create on leave
	private void addImportedModules(JobContext jcontext, Module mod)
	{
		//Module mod = jcontext.getModule(this.root);  // Not the same as Job main module
		for (ImportDecl id : mod.imports)
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullmodname = im.modname.toName();
				if (!this.modules.keySet().contains(fullmodname))
				{
					ModuleName modname = (im.isAliased()) ? im.getModuleNameAlias() : fullmodname;
					Module m = jcontext.getModule(fullmodname);
					addModule(m, modname);
					addImportedModules(jcontext, m);
				}
			}
			else
			{
				throw new RuntimeException("TODO: " + id);
			}
		}
	}

	/*// Separate the above into adders
	public void addLocalProtocolDecl(LocalProtocolDecl lpd)
	{
		this.locals.put(lpd., value);
	}*/

	/*public boolean isModuleVisible(ModuleName modname)
	{
		return this.modules.keySet().contains(modname);
	}*/

	public boolean isDataTypeVisible(DataType typename)
	{
		return this.data.keySet().contains(typename);
	}

	public boolean isMessageSigNameVisible(Name<? extends SigKind> signame)
	{
		return this.sigs.containsKey(signame);
	}

	/*public boolean isGlobalProtocolVisible(ProtocolName proto)
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
	
	/*//public ProtocolNamegetFullProtocolDeclName(ProtocolName visname)
	public <K extends ProtocolKind> ProtocolName<K> getFullProtocolDeclName(ProtocolName<K> visname)
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
		//throw new RuntimeException("Protocol name not visible: " + visname);
		throw new RuntimeException("Protocol name not visible: " + visname + ", " + this.globals + ", " + this.locals);
	}*/
	public <K extends ProtocolKind> ProtocolName<K> getFullProtocolName(ProtocolName<K> visname)
	{
		try
		{
			return (ProtocolName<K>) getFullGlobalProtocolName((GProtocolName) visname);  // FIXME: hack
		}
		catch (Exception x)
		{
			return (ProtocolName<K>) getFullLocalProtocolName((LProtocolName) visname);
		}
	}

	public GProtocolName getFullGlobalProtocolName(GProtocolName visname)
	{
		if (!this.globals.containsKey(visname))
		{
			throw new RuntimeException("Global protocol name not visible: " + visname + ", " + this.globals);
		}
		return this.globals.get(visname);
	}

	public LProtocolName getFullLocalProtocolName(LProtocolName visname)
	{
		if (!this.locals.containsKey(visname))
		{
			throw new RuntimeException("Local protocol name not visible: " + visname + ", " + this.locals);
		}
		return this.locals.get(visname);
	}
	
	@Override 
	public String toString()
	{
		return "[modules=" + this.modules + ", types=" + this.data + ", sigs=" + this.sigs + ", globals=" + this.globals + ", locals=" + this.locals;
	}

	/*private ProtocolName getFullProtocolName(
			ProtocolDecl<? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>> pd)
	{
		ModuleName fullmodname = this.root.getFullModuleName();
		return new ProtocolName(fullmodname, pd.name.toString());
	}*/

}
