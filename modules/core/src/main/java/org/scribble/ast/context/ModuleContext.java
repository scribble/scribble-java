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

	// visible names -> fully qualified names
	// The modules and member names that are visible from this Module -- mapped to "cannonical" (fully qualified) names
	private final Map<ModuleName, ModuleName> modules;
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
		this.data = new HashMap<>();
		this.sigs = new HashMap<>();
		this.globals = new HashMap<>();
		this.locals = new HashMap<>();
		
		addModule(root, fullmodname);
		addLocalMembers(root);
		addImportedModules(jcontext, jcontext.getModule(this.root));
	}

	// vismodname can be the full module name or the import alias
	private void addModule(Module mod, ModuleName vismodname)
	{
		this.modules.put(vismodname, mod.getFullModuleName());
		for (NonProtocolDecl<? extends Kind> npd : mod.getNonProtocolDecls())
		{
			if (npd.isDataTypeDecl())
			{
				DataTypeDecl dtd = (DataTypeDecl) npd;
				this.data.put(new DataType(vismodname, dtd.getDeclName()), dtd.getFullMemberName(mod));
			}
			else //if (dtd instanceof MessageSignatureDecl)
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				this.sigs.put(msnd.getDeclName(), msnd.getFullMemberName(mod));
			}
		}
		for (GProtocolDecl gpd : mod.getGlobalProtocolDecls())
		{
			this.globals.put(new GProtocolName(vismodname, gpd.getHeader().getDeclName()), gpd.getFullMemberName(mod));
		}
		for (LProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			this.locals.put(new LProtocolName(vismodname, lpd.getHeader().getDeclName()), lpd.getFullMemberName(mod));
		}
	}
	
	private void addLocalMembers(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
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
				this.data.put(selfname, fullname);
			}
			else if (npd.isMessageSigDecl())
			{
				MessageSigNameDecl msnd = (MessageSigNameDecl) npd;
				MessageSigName fullname = msnd.getFullMemberName(mod);
				MessageSigName simplename = msnd.getDeclName();
				MessageSigName selfname = new MessageSigName(simplemodname, simplename);
				this.sigs.put(simplename, fullname);
				this.sigs.put(selfname, fullname);
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
			this.globals.put(selfname, fullname);
		}
		for (LProtocolDecl lpd : mod.getLocalProtocolDecls())
		{
			LProtocolName fullname = lpd.getFullMemberName(mod);
			LProtocolName simplename = lpd.getHeader().getDeclName();
			LProtocolName selfname = new LProtocolName(simplemodname, simplename);
			this.locals.put(simplename, fullname);
			this.locals.put(selfname, fullname);
		}
	}
	
	// Could move to ImportModule but would need a defensive copy setter, or cache info in builder and create on leave
	private void addImportedModules(JobContext jcontext, Module mod)
	{
		for (ImportDecl<?> id : mod.getImportDecls())
		{
			if (id.isImportModule())
			{
				ImportModule im = (ImportModule) id;
				ModuleName fullmodname = im.modname.toName();
				if (!this.modules.keySet().contains(fullmodname))
				{
					ModuleName modname = (im.isAliased()) ? im.getAlias() : fullmodname;  // visible name not sufficient?
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
	
	@Override 
	public String toString()
	{
		return "[modules=" + this.modules + ", types=" + this.data + ", sigs=" + this.sigs + ", globals=" + this.globals + ", locals=" + this.locals;
	}
}
