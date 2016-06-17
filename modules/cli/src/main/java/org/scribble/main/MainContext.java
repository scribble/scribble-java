package org.scribble.main;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.Module;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.parser.AntlrParser;
import org.scribble.parser.ScribModuleLoader;
import org.scribble.parser.ScribParser;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.util.Pair;
import org.scribble.util.ScribParserException;
import org.scribble.visit.Job;


// Scribble tool context for main module
// FIXME: should be in core org.scribble.main, but currently here due to Maven dependency restrictions
// MainContext takes ResourceLocator abstractly (e.g. DirectoryResourceLocator), but because abstract Resource itself works off paths, it takes mainpath (rather than something more abstract, e.g. URI, to identify the "main" resource)
// Resource and ResourceLocator should be made abstract from (file)paths (cf. use of toPath in ScribbleModuleLoader)
public class MainContext
{
	//public final boolean jUnit;
	public final boolean debug;
	public final boolean useOldWF;
	public final boolean noLiveness;
	public final boolean minEfsm;
	public final boolean fair;
	
	public final ModuleName main;

	// Only "manually" used here for loading main module (which should be factored out to front end) -- otherwise, only used within loader
	private final AntlrParser antlrParser;  // Not encapsulated inside ScribbleParser, because ScribbleParser's main function is to "parse" ANTLR CommonTrees into ModelNodes
	private final ScribParser scribParser;

	private final ResourceLocator locator;  // Path -> Resource
	private final ScribModuleLoader loader;  // sesstype.ModuleName -> Pair<Resource, Module>

	// ModuleName keys are full module names -- parsed are the modules read from file, distinguished from the generated projection modules
	// Resource recorded for source path
	private final Map<ModuleName, Pair<Resource, Module>> parsed = new HashMap<>();
	
	// FIXME: make Path abstract as e.g. URI -- locator is abstract but Path is coupled to concrete DirectoryResourceLocator
	//public MainContext(boolean jUnit, boolean debug, ResourceLocator locator, Path mainpath, boolean useOldWF, boolean noLiveness)
	public MainContext(boolean debug, ResourceLocator locator, Path mainpath, boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair) throws ScribParserException
	{
		//this.jUnit = jUnit;
		this.debug = debug;
		this.useOldWF = useOldWF;
		this.noLiveness = noLiveness;
		this.minEfsm = minEfsm;
		this.fair = fair;

		this.antlrParser = new AntlrParser();
		this.scribParser = new ScribParser();
		this.locator = locator; 
		this.loader = new ScribModuleLoader(this.locator, this.antlrParser, this.scribParser);

		Pair<Resource, Module> p = loadMainModule(mainpath);
		this.main = p.right.getFullModuleName();
		
		loadAllModules(p);
	}
	
	public Map<ModuleName, Module> getParsedModules()
	{
		return this.parsed.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue().right));
	}
	
	// FIXME: checking main module resource exists at specific location should be factored out to front-end (e.g. CommandLine) -- main module resource is specified at local front end level of abstraction, while MainContext uses abstract resource loading
	private Pair<Resource, Module> loadMainModule(Path mainpath) throws ScribParserException
	{
		//Pair<Resource, Module> p = this.loader.loadMainModule(mainpath);
		Resource res = DirectoryResourceLocator.getResourceByFullPath(mainpath);  // FIXME: hardcoded to DirectoryResourceLocator -- main module loading should be factored out to front end (e.g. CommandLine)
		Module mod = (Module) this.scribParser.parse(this.antlrParser.parseAntlrTree(res));
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

	private void loadAllModules(Pair<Resource, Module> module) throws ScribParserException
	{
		this.parsed.put(module.right.getFullModuleName(), module);
		for (ImportDecl<?> id : module.right.getImportDecls())
		{
			if (id.isImportModule())
			{
				ModuleName modname = ((ImportModule) id).modname.toName();
				if (!this.parsed.containsKey(modname))
				{
					loadAllModules(this.loader.loadModule(modname));
				}
			}
		}
	}
	
	public Job newJob()
	{
		return new Job(this.debug, this.getParsedModules(), this.main, this.useOldWF, this.noLiveness, this.minEfsm, this.fair);
	}
}
