package org.scribble2.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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
import org.scribble2.parser.AntlrParser;
import org.scribble2.parser.ScribbleModuleLoader;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.util.Pair;
import org.scribble2.sesstype.name.ModuleName;
import org.scribble2.util.ScribbleException;

public class MainContext
{
	public final ModuleName main;

	private final ResourceLocator locator;
	private final AntlrParser aparser;
	private final ScribbleParser sparser;
	private final ScribbleModuleLoader loader;

	// ModuleName keys are full module names -- currently the modules read from file, distinguished from the generated projection modules
	private final Map<ModuleName, Pair<Resource, Module>> parsed = new HashMap<>();
	
	//public MainContext(List<Path> importPath, String mainpath) throws IOException, ScribbleException
	public MainContext(List<Path> importPath, Path mainpath)
	{
		this.locator = new DirectoryResourceLocator(importPath);
		this.aparser = new AntlrParser();
		this.sparser = new ScribbleParser();
		this.loader = new ScribbleModuleLoader(this.locator, this.aparser, this.sparser);

		Pair<Resource, Module> p = loadMainModule(mainpath);

		this.main = p.right.getFullModuleName();
		
		loadAllModules(p);
	}
	
	private Pair<Resource, Module> loadMainModule(Path mainpath)
	{
		//Pair<Resource, Module> p = this.loader.loadMainModule(mainpath);
		Resource res = ResourceLocator.getResourceByFullPath(mainpath);
		Module mod = (Module) this.sparser.parse(this.aparser.parseAntlrTree(res));
		checkMainModuleName(mainpath, mod);
		return new Pair<>(res, mod);
	}
	
	// Hacky? But not Scribble tool's job to check nested directory location of module fully corresponds to the fullname of module? Cf. Java classes
	private void checkMainModuleName(Path mainpath, Module main)
	{
		String path = mainpath.toString();  // FIXME: hack
		// FileSystems.getDefault().getSeparator() ?
		String tmp = path.substring((path.lastIndexOf(File.separator) == -1) ? 0 : path.lastIndexOf(File.separator) + 1, path.lastIndexOf('.'));
		if (!tmp.equals(main.getFullModuleName().getSimpleName().toString()))  // ModuleName.toString hack?
		{
			throw new RuntimeException("Simple module name at path " + path + " mismatch: " + main.getFullModuleName());
		}
	}

	private void loadAllModules(Pair<Resource, Module> module)
	{
		this.parsed.put(module.right.getFullModuleName(), module);
		for (ImportDecl id : module.right.imports)
		{
			if (id.isImportModule())
			{
				ModuleName modname = ((ImportModule) id).modname.toName();
				if (!this.parsed.containsKey(modname))
				{
					loadAllModules(this.loader.loadScribbleModule(modname));
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
