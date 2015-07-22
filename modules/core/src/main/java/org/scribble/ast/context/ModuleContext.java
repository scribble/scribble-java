package org.scribble.ast.context;

import java.util.HashMap;
import java.util.Map;

import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.Module;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolDecl;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.visit.JobContext;

// Context information specific to each module
public class ModuleContext
{
	public final ModuleName root;

	
	// FIXME: separate visible from "working set" -- for subprotocols, working set can be a superset of (directly) visible from current module
	
	
	// parent module fullname -> visible modules names -> fully qualified names
	// The modules and member names that are visible from this Module -- mapped to "cannonical" (fully qualified) names
	private final Map<ModuleName, ModuleName> modules;

	// visible names -> fully qualified names
	private final Map<DataType, DataType> data;
	private final Map<MessageSigName, MessageSigName> sigs;
	private final Map<GProtocolName, GProtocolName> globals;
	private final Map<LProtocolName, LProtocolName> locals;

	// Made by ContextBuilder
	// ModuleContext is the root context
	public ModuleContext(JobContext jcontext, Module root) throws ScribbleException
	{
		ModuleName fullmodname = root.getFullModuleName(); 

		this.root = fullmodname;
		this.modules = new HashMap<>();

		this.data = new HashMap<>();
		this.sigs = new HashMap<>();
		this.globals = new HashMap<>();
		this.locals = new HashMap<>();
		
		//addModule(root, root, fullmodname);
		addModule(root);
		addImportedModules(jcontext, jcontext.getModule(this.root), true);
		addInternalMembers(jcontext, root);
	}

	private void addModule(Module mod) throws ScribbleException
	{
		addModule(null, mod, mod.getFullModuleName());
	}

	// vismodname can be the full module name or the import alias
	private void addModule(Module parent, Module mod, ModuleName visname) throws ScribbleException
	{
		//this.modules.put(visname, mod.getFullModuleName());
		//addModule(parent.getFullModuleName(), visname, mod.getFullModuleName());
		addModule(null, visname, mod.getFullModuleName());
		for (NonProtocolDecl<?> npd : mod.getNonProtocolDecls())
		{
			if (npd.isDataTypeDecl())
			{
				DataTypeDecl dtd = (DataTypeDecl) npd;
				this.data.put(new DataType(visname, dtd.getDeclName()), dtd.getFullMemberName(mod));
			}
			else //if (npd.isMessageSigNameDecl())
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				this.sigs.put(new MessageSigName(visname, msnd.getDeclName()), msnd.getFullMemberName(mod));
			}
		}
		for (GProtocolDecl gpd : mod.getGlobalProtocolDecls())
		{
			this.globals.put(new GProtocolName(visname, gpd.getHeader().getDeclName()), gpd.getFullMemberName(mod));
		}
		for (LProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			this.locals.put(new LProtocolName(visname, lpd.getHeader().getDeclName()), lpd.getFullMemberName(mod));
		}
	}
	
	private void addInternalMembers(JobContext jcontext, Module mod) throws ScribbleException
	{
		/*ModuleName fullmodname = mod.getFullModuleName();
		ModuleName simplemodname = fullmodname.getSimpleName();
		for (NonProtocolDecl<?> npd : mod.getNonProtocolDecls())
		{
			if (npd.isDataTypeDecl())
			{
				DataTypeDecl dtd = (DataTypeDecl) npd;
				DataType fullname = dtd.getFullMemberName(mod);
				DataType simplename = dtd.getDeclName();
				DataType selfname = new DataType(simplemodname, simplename);
				this.data.put(simplename, fullname);
				//this.data.put(selfname, fullname);
			}
			else if (npd.isMessageSigNameDecl())
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				MessageSigName fullname = msnd.getFullMemberName(mod);
				MessageSigName simplename = msnd.getDeclName();
				MessageSigName selfname = new MessageSigName(simplemodname, simplename);
				this.sigs.put(simplename, fullname);
				//this.sigs.put(selfname, fullname);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + npd);
			}
		}
		for (GProtocolDecl gpd : mod.getGlobalProtocolDecls())
		{
			GProtocolName fullname = gpd.getFullMemberName(mod);
			GProtocolName simplename = gpd.getHeader().getDeclName();
			GProtocolName selfname = new GProtocolName(simplemodname, simplename);
			this.globals.put(simplename, fullname);
			//this.globals.put(selfname, fullname);
		}
		for (LProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			LProtocolName fullname = lpd.getFullMemberName(mod);
			LProtocolName simplename = lpd.getHeader().getDeclName();
			LProtocolName selfname = new LProtocolName(simplemodname, simplename);
			this.locals.put(simplename, fullname);
			//this.locals.put(selfname, fullname);
		}*/

		ModuleName simpname = mod.moddecl.getDeclName();
		if (!this.modules.containsKey(simpname))  // simpname different to fullname
		{
			addModule(null, simpname, mod.getFullModuleName());
		}
		
		for (ImportDecl<?> id : mod.getImportDecls())
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullmodname = im.modname.toName();
				ModuleName visname = (im.isAliased()) ? im.getAlias() : fullmodname;  // getVisibleName doesn't use fullname
				if (!this.modules.containsKey(visname))
				{
					Module imported = jcontext.getModule(fullmodname);
					addModule(null, imported, visname);  
				}
			}
			else
			{
				throw new RuntimeException("TODO: " + id);
			}
		}
		
		for (NonProtocolDecl<?> npd : mod.getNonProtocolDecls())
		{
			if (npd.isDataTypeDecl())
			{
				DataTypeDecl dtd = (DataTypeDecl) npd;
				this.data.put(new DataType(dtd.getDeclName().toString()), dtd.getFullMemberName(mod));
			}
			else //if ()
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				this.sigs.put(new MessageSigName(msnd.getDeclName().toString()), msnd.getFullMemberName(mod));
			}
		}
		for (GProtocolDecl gpd : mod.getGlobalProtocolDecls())
		{
			this.globals.put(new GProtocolName(gpd.getHeader().getDeclName().toString()), gpd.getFullMemberName(mod));
		}
		for (LProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			this.locals.put(new LProtocolName(lpd.getHeader().getDeclName().toString()), lpd.getFullMemberName(mod));
		}
	}
	
	// Could move to ImportModule but would need a defensive copy setter, or cache info in builder and create on leave
	private void addImportedModules(JobContext jcontext, Module parent, boolean recurse) throws ScribbleException
	{
		for (ImportDecl<?> id : parent.getImportDecls())
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullmodname = im.modname.toName();
				if (!this.modules.containsKey(fullmodname))
				{
							// FIXME: simple names should visible from local imports only, not transitively
							//.. FIXME: for modules, need per ("nested") module scoping of visible modules, for subprotocol visiting
							//.. FIXME: for non modules (i.e. members) don't need simple vis names other than for self module, others must be qualified or full -- only need to check bound and nonambiguous "locally" per module in disamb, later passes (including subprotocol) can just work by taking the used names
						Module mod = jcontext.getModule(fullmodname);
						addModule(mod);  
					if (recurse)
					{
						addImportedModules(jcontext, mod, recurse);   // Don't need transitive, any non
					}
				}
			}
			else
			{
				throw new RuntimeException("TODO: " + id);
			}
		}
	}

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
	}*/

	public <K extends ProtocolKind> ProtocolName<K> getFullProtocolDeclName(ProtocolName<K> visname)
	{
		ProtocolName<? extends ProtocolKind> pn = (visname.getKind().equals(Global.KIND))
				? getFullGProtocolDeclName((GProtocolName) visname)
				: getFullLProtocolDeclName((LProtocolName) visname);
		@SuppressWarnings("unchecked")
		ProtocolName<K> tmp = (ProtocolName<K>) pn;
		return tmp;
	}

	private GProtocolName getFullGProtocolDeclName(GProtocolName visname)
	{
		return getFullName(this.globals, visname);
	}

	private LProtocolName getFullLProtocolDeclName(LProtocolName visname)
	{
		return getFullName(this.locals, visname);
	}

	private static <T extends Name<K>, K extends Kind> T getFullName(Map<T, T> map, T visname)
	{
		if (!map.containsKey(visname))
		{
			throw new RuntimeException("Name not visible: " + visname);
		}
		return map.get(visname);
	}
	
	private void addModule(ModuleName parent, ModuleName visname, ModuleName fullname) throws ScribbleException
	{
		/*Map<ModuleName, ModuleName> map = this.modules.get(parent);
		if (map == null)
		{
			map = new HashMap<>();
			this.modules.put(parent, map);
		}*/
		Map<ModuleName, ModuleName> map = this.modules;
		if (map.containsKey(visname))  // Done here: NameDisambiguator is a later pass
		{
			throw new ScribbleException("Duplicate module name: " + visname);
		}
		map.put(visname, fullname);
	}
	
	@Override 
	public String toString()
	{
		return "[modules=" + this.modules + ", types=" + this.data + ", sigs=" + this.sigs + ", globals=" + this.globals + ", locals=" + this.locals;
	}
}
