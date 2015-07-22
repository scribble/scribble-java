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

	/*// parent module fullname -> visible modules names -> fully qualified names
	// The modules and member names that are visible from this Module -- mapped to "cannonical" (fully qualified) names
	private final Map<ModuleName, ModuleName> modules;

	// visible names -> fully qualified names
	private final Map<DataType, DataType> data;
	private final Map<MessageSigName, MessageSigName> sigs;
	private final Map<GProtocolName, GProtocolName> globals;
	private final Map<LProtocolName, LProtocolName> locals;*/
	
	private final ScribNames deps = new ScribNames();  // All transitive dependencies of this module: all names fully qualified
	private final ScribNames visible = new ScribNames();  // Directly visible names from this module

	// Made by ModuleContextBuilder
	// ModuleContext is the root context
	public ModuleContext(JobContext jcontext, Module root) throws ScribbleException
	{
		ModuleName fullmodname = root.getFullModuleName(); 

		this.root = fullmodname;
		
		addModule(this.deps, root, root.getFullModuleName());
		addImportedModules(this.deps, jcontext, jcontext.getModule(this.root));

		addModule(this.visible, root, root.getFullModuleName());
		if (!root.getFullModuleName().equals(root.moddecl.getDeclName()))
		{
			addModule(this.visible, root, root.moddecl.getDeclName());
		}
		//addImportedModules(this.visible, jcontext, jcontext.getModule(this.root));
		addInternalMembers(jcontext, root);
		
		//System.out.println("a: " + this);
	}

	private void addInternalMembers(JobContext jcontext, Module mod) throws ScribbleException
	{
		ModuleName simpname = mod.moddecl.getDeclName();
		if (!this.visible.modules.containsKey(simpname))  // simpname different to fullname
		{
			addModule(this.visible, simpname, mod.getFullModuleName());
		}
		
		for (ImportDecl<?> id : mod.getImportDecls())
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullmodname = im.modname.toName();
				ModuleName visname = (im.isAliased()) ? im.getAlias() : fullmodname;  // getVisibleName doesn't use fullname
				if (!this.visible.modules.containsKey(visname))
				{
					Module imported = jcontext.getModule(fullmodname);
					addModule(this.visible, imported, visname);  
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
				this.visible.data.put(new DataType(dtd.getDeclName().toString()), dtd.getFullMemberName(mod));
			}
			else //if ()
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				this.visible.sigs.put(new MessageSigName(msnd.getDeclName().toString()), msnd.getFullMemberName(mod));
			}
		}
		for (GProtocolDecl gpd : mod.getGlobalProtocolDecls())
		{
			this.visible.globals.put(new GProtocolName(gpd.getHeader().getDeclName().toString()), gpd.getFullMemberName(mod));
		}
		for (LProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			this.visible.locals.put(new LProtocolName(lpd.getHeader().getDeclName().toString()), lpd.getFullMemberName(mod));
		}
	}
	
	// Could move to ImportModule but would need a defensive copy setter, or cache info in builder and create on leave
	private static void addImportedModules(ScribNames names, JobContext jcontext, Module parent) throws ScribbleException
	{
		for (ImportDecl<?> id : parent.getImportDecls())
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullmodname = im.modname.toName();
				if (!names.modules.containsKey(fullmodname))
				{
					// FIXME: simple names should visible from local imports only, not transitively
					//.. FIXME: for modules, need per ("nested") module scoping of visible modules, for subprotocol visiting
					//.. FIXME: for non modules (i.e. members) don't need simple vis names other than for self module, others must be qualified or full -- only need to check bound and nonambiguous "locally" per module in disamb, later passes (including subprotocol) can just work by taking the used names
					Module mod = jcontext.getModule(fullmodname);
					addModule(names, mod, fullmodname);  
					addImportedModules(names, jcontext, mod);   // Don't need transitive, any non
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

	public boolean isDataTypeReachable(DataType typename)
	{
		return this.deps.data.keySet().contains(typename);
	}

	public boolean isMessageSigNameReachable(Name<? extends SigKind> signame)
	{
		return this.deps.sigs.containsKey(signame);
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
	
	public <K extends ProtocolKind> ProtocolName<K> getFullProtocolDeclNameFromVisible(ProtocolName<K> visname)
	{
		ProtocolName<? extends ProtocolKind> pn = (visname.getKind().equals(Global.KIND))
				? getFullGProtocolDeclName(this.visible, (GProtocolName) visname)
				: getFullLProtocolDeclName(this.visible, (LProtocolName) visname);
		@SuppressWarnings("unchecked")
		ProtocolName<K> tmp = (ProtocolName<K>) pn;
		return tmp;
	}

	// Redundant: proto should already be full name, by namedisamb
	public <K extends ProtocolKind> ProtocolName<K> getFullProtocolDeclNameFromDeps(ProtocolName<K> proto)
	{
		ProtocolName<? extends ProtocolKind> pn = (proto.getKind().equals(Global.KIND))
				? getFullGProtocolDeclName(this.deps, (GProtocolName) proto)
				: getFullLProtocolDeclName(this.deps, (LProtocolName) proto);
		@SuppressWarnings("unchecked")
		ProtocolName<K> tmp = (ProtocolName<K>) pn;
		return tmp;
	}

	private GProtocolName getFullGProtocolDeclName(ScribNames names, GProtocolName visname)
	{
		return getFullName(names.globals, visname);
	}

	private LProtocolName getFullLProtocolDeclName(ScribNames names, LProtocolName visname)
	{
		return getFullName(names.locals, visname);
	}

	private static <T extends Name<K>, K extends Kind> T getFullName(Map<T, T> map, T visname)
	{
		if (!map.containsKey(visname))
		{
			throw new RuntimeException("Name not reachable/visible: " + visname);
		}
		return map.get(visname);
	}
	
	public DataType getFullDataTypeFromVisible(DataType visname)
	{
		return getFullName(this.visible.data, visname);
	}
	
	public MessageSigName getFullMessageSigNameFromVisible(MessageSigName visname)
	{
		return getFullName(this.visible.sigs, visname);
	}

	public boolean isDataTypeVisible(DataType typename)
	{
		return this.visible.data.keySet().contains(typename);
	}

	public boolean isMessageSigNameVisible(Name<? extends SigKind> signame)
	{
		return this.visible.sigs.containsKey(signame);
	}

	// Register mod under name modname, modname could be the full module name or an import alias
	private static void addModule(ScribNames names, Module mod, ModuleName modname) throws ScribbleException
	{
		addModule(names, modname, mod.getFullModuleName());
		for (NonProtocolDecl<?> npd : mod.getNonProtocolDecls())
		{
			if (npd.isDataTypeDecl())
			{
				DataTypeDecl dtd = (DataTypeDecl) npd;
				names.data.put(new DataType(modname, dtd.getDeclName()), dtd.getFullMemberName(mod));
			}
			else //if (npd.isMessageSigNameDecl())
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				names.sigs.put(new MessageSigName(modname, msnd.getDeclName()), msnd.getFullMemberName(mod));
			}
		}
		for (GProtocolDecl gpd : mod.getGlobalProtocolDecls())
		{
			names.globals.put(new GProtocolName(modname, gpd.getHeader().getDeclName()), gpd.getFullMemberName(mod));
		}
		for (LProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			names.locals.put(new LProtocolName(modname, lpd.getHeader().getDeclName()), lpd.getFullMemberName(mod));
		}
	}
	
	private static void addModule(ScribNames names, ModuleName modname, ModuleName fullname) throws ScribbleException
	{
		/*if (names.modules.containsKey(modname))  // Checked here: NameDisambiguator is a later pass
		{
			throw new ScribbleException("Duplicate module name: " + modname);
		}*/
		names.modules.put(modname, fullname);
	}
	
	@Override 
	public String toString()
	{
		return "deps=" + this.deps.toString() + ", visible=" + this.visible.toString();
	}
}

class ScribNames
{
	// names -> fully qualified names
	protected final Map<ModuleName, ModuleName> modules = new HashMap<>();
	protected final Map<DataType, DataType> data = new HashMap<>();
	protected final Map<MessageSigName, MessageSigName> sigs = new HashMap<>();
	protected final Map<GProtocolName, GProtocolName> globals = new HashMap<>();
	protected final Map<LProtocolName, LProtocolName> locals = new HashMap<>();
	
	@Override
	public String toString()
	{
		return "[modules=" + this.modules + ", types=" + this.data + ", sigs=" + this.sigs + ", globals=" + this.globals + ", locals=" + this.locals;
	}
}	
