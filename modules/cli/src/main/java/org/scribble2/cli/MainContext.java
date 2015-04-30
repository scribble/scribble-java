package org.scribble2.cli;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.resources.DirectoryResourceLocator;
import org.scribble.resources.Resource;
import org.scribble.resources.ResourceLocator;
import org.scribble2.model.ImportDecl;
import org.scribble2.model.ImportModule;
import org.scribble2.model.Module;
import org.scribble2.parser.ScribbleModuleLoader;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.util.Pair;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.util.ScribbleException;

public class MainContext
{
	//private static final ScribbleParser PARSER = new ScribbleParser();

	public final ScribbleParser parser = new ScribbleParser();
	public final DirectoryResourceLocator locator;
	public final ScribbleModuleLoader loader;

	//public final CliJobContext clijcontext;  // Mutable (Visitor passes replace modules)
	
	// FIXME: path and other command line args should be job parameters stored here
	public final List<String> importPath;
	public final ModuleName main;
	
	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	/*private final Map<ModuleName, Module> parsed = new HashMap<>();
	private final Map<ModuleName, Resource> sources = new HashMap<>();*/
	
	private final Map<ModuleName, Pair<Resource, Module>> parsed = new HashMap<>();

	
	public MainContext(List<String> importPath, String mainpath) throws IOException, ScribbleException
	{
		this.importPath = importPath;

		this.locator = new DirectoryResourceLocator(importPath);
		this.loader = new ScribbleModuleLoader(this.locator, this.parser);

		Pair<Resource, Module> p = this.loader.loadMainModule(mainpath);

		this.main = p.right.getFullModuleName();
		
		loadAll(p);
	}

	private void loadAll(Pair<Resource, Module> module) throws ScribbleException
	{
		this.parsed.put(module.right.getFullModuleName(), module);
		for (ImportDecl id : module.right.imports)
		{
			if (id.isImportModule())
			{
				ModuleName modname = ((ImportModule) id).modname.toName();
				if (!this.parsed.containsKey(modname))
				{
					loadAll(this.loader.loadScribbleModule(modname.toPath()));
				}
			}
		}
	}
	
	public Map<ModuleName, Module> getModules()
	{
		return this.parsed.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue().right));
	}

	/*//private void initLoader(String paths)
	private void initLoader(List<String> paths)
	{
		this._locator = new DirectoryResourceLocator(paths);
		//_loader = new ProtocolModuleLoader(PARSER, _locator, LOGGER);
	}*/

	/*//protected Resource getResource(String moduleName) {
	public Resource getResource(String path) {
		//String relativePath = moduleName.replace('.', java.io.File.separatorChar) + ".scr";  // RAY
		return (this._locator.getResource(path));
	}*/
}

/*class ModuleLoader
{
	//private final ResourceLocator loader;
	//private final ScribbleParser parser;
	
	private final ScribbleModuleLoader loader;

	public ModuleLoader(ScribbleModuleLoader loader) throws ScribbleException
	{
		/*this.loader = locator;
		this.parser = parser;* /
		//this.loader = new ScribbleModuleLoader(locator, parser);
		this.loader = loader;
	}
	
	public Map<ModuleName, Pair<Resource, Module>> loadAll(String mainpath) throws ScribbleException
	{
		Map<ModuleName, Pair<Resource, Module>> modules = new HashMap<>();
		
		Pair<Resource, Module> mainmod = this.loader.loadScribbleModule(mainpath);  // FIXME: should be direct path location, not import path search
		
		modules.put(mainmod.right.getFullModuleName(), mainmod);

		importModules(modules, mainmod.right);
		
		return modules;
	}
	
	// Pre: modules.containsKey(module)
	private void importModules(Map<ModuleName, Pair<Resource, Module>> modules, Module module)
	{
		for (ImportDecl id : module.imports)
		{
			if (id.isImportModule())
			{
				ModuleName modname = ((ImportModule) id).modname.toName();
				if (!modules.containsKey(modname))
				{
					String path = modname.toPath();
					Pair<Resource, Module> next = this.loader.loadScribbleModule(path);
					modules.put(next.right.getFullModuleName(), next);
					importModules(modules, next.right);
				}
			}
		}
	}
}*/
