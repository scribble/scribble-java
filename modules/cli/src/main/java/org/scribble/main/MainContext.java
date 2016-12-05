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
import org.scribble.main.resource.InlineResource;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.parser.AntlrParser;
import org.scribble.parser.ScribModuleLoader;
import org.scribble.parser.ScribParser;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.util.Pair;
import org.scribble.util.ScribParserException;


// N.B. the same package is declared in core

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
	public final boolean noLocalChoiceSubjectCheck;
	public final boolean noAcceptCorrelationCheck;
	public final boolean noValidation;

	// Only "manually" used here for loading main module (which should be factored out to front end) -- otherwise, only used within loader
	private final AntlrParser antlrParser = new AntlrParser();  // Not encapsulated inside ScribbleParser, because ScribbleParser's main function is to "parse" ANTLR CommonTrees into ModelNodes
	private final ScribParser scribParser = new ScribParser();

	private final ResourceLocator locator;  // Path -> Resource
	private final ScribModuleLoader loader;  // sesstype.ModuleName -> Pair<Resource, Module>

	private ModuleName main;

	// ModuleName keys are full module names -- parsed are the modules read from file, distinguished from the generated projection modules
	// Resource recorded for source path
	private final Map<ModuleName, Pair<Resource, Module>> parsed = new HashMap<>();
	
	// FIXME: make Path abstract as e.g. URI -- locator is abstract but Path is coupled to concrete DirectoryResourceLocator
	protected MainContext(boolean debug, ResourceLocator locator, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck, boolean noValidation)
					throws ScribParserException, ScribbleException
	{
		//this.jUnit = jUnit;
		this.debug = debug;
		this.useOldWF = useOldWF;
		this.noLiveness = noLiveness;
		this.minEfsm = minEfsm;
		this.fair = fair;
		this.noLocalChoiceSubjectCheck = noLocalChoiceSubjectCheck;
		this.noAcceptCorrelationCheck = noAcceptCorrelationCheck;
		this.noValidation = noValidation;

		this.locator = locator; 
		this.loader = new ScribModuleLoader(this.locator, this.antlrParser, this.scribParser);
	}

	public MainContext(boolean debug, ResourceLocator locator, String inline, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck, boolean noValidation)
					throws ScribParserException, ScribbleException
	{
		this(debug, locator, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation);

		Resource res = new InlineResource(inline);
		Module mod = (Module) this.scribParser.parse(this.antlrParser.parseAntlrTree(res));

		init(res, mod);
	}

	// Load main module from file system
	public MainContext(boolean debug, ResourceLocator locator, Path mainpath, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck, boolean noValidation)
					throws ScribParserException, ScribbleException
	{
		this(debug, locator, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation);

		// FIXME: checking main module resource exists at specific location should be factored out to front-end (e.g. CommandLine) -- main module resource is specified at local front end level of abstraction, while MainContext uses abstract resource loading
		//Pair<Resource, Module> p = this.loader.loadMainModule(mainpath);
		Resource res = DirectoryResourceLocator.getResourceByFullPath(mainpath);  // FIXME: hardcoded to DirectoryResourceLocator -- main module loading should be factored out to front end (e.g. CommandLine)
		Module mod = (Module) this.scribParser.parse(this.antlrParser.parseAntlrTree(res));  // FIXME: rename exceptions
		checkMainModuleName(mainpath, mod);
		
		init(res, mod);
	}

	private void init(Resource res, Module mainmod) throws ScribParserException, ScribbleException
	{
		Pair<Resource, Module> p = new Pair<>(res, mainmod);
		this.main = p.right.getFullModuleName();  // FIXME: main modname comes from the inlined moddecl -- check for issues if this clashes with an existing file system resource
		loadAllModules(p);
	}

	private void loadAllModules(Pair<Resource, Module> module) throws ScribParserException, ScribbleException
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
	
	public Map<ModuleName, Module> getParsedModules()
	{
		return this.parsed.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue().right));
	}
	
	public Job newJob()
	{
		return new Job(this.debug, this.getParsedModules(), this.main, this.useOldWF, this.noLiveness, this.minEfsm, this.fair,
				this.noLocalChoiceSubjectCheck, this.noAcceptCorrelationCheck, this.noValidation);
	}
	
	// Hacky? But not Scribble tool's job to check nested directory location of module fully corresponds to the fullname of module? Cf. Java classes
	private void checkMainModuleName(Path mainpath, Module main) throws ScribbleException
	{
		String path = mainpath.toString();  // FIXME: hack
		// FileSystems.getDefault().getSeparator() ?
		String tmp = path.substring((path.lastIndexOf(File.separator) == -1) ? 0 : path.lastIndexOf(File.separator) + 1, path.lastIndexOf('.'));
		if (!this.noValidation)
		{
			if (!tmp.equals(main.getFullModuleName().getSimpleName().toString()))  // ModuleName.toString hack?
			{
				throw new ScribbleException(main.moddecl.name.getSource(), "Simple module name at path " + path + " mismatch: " + main.getFullModuleName());
			}
		}
	}
}
