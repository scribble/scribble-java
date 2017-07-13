/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.main;  // N.B. the same package is declared in core
		// FIXME? should be in core org.scribble.main, but currently here due to Maven dependency restrictions

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactory;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.Module;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.InlineResource;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.endpoint.EModelFactoryImpl;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.SModelFactoryImpl;
import org.scribble.parser.scribble.AntlrParser;
import org.scribble.parser.scribble.ScribModuleLoader;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.util.Pair;
import org.scribble.util.ScribParserException;

// Scribble tool context for main module
// MainContext takes ResourceLocator abstractly (e.g. DirectoryResourceLocator), but because abstract Resource itself works off paths, it takes mainpath (rather than something more abstract, e.g. URI, to identify the "main" resource)
// Resource and ResourceLocator should be made abstract from (file)paths (cf. use of toPath in ScribbleModuleLoader)
public class MainContext
{

	// Only "manually" used here for loading main module (which should be factored out to front end) -- otherwise, only used within loader
	protected final AntlrParser antlrParser = newAntlrParser();  // Not encapsulated inside ScribbleParser, because ScribbleParser's main function is to "parse" ANTLR CommonTrees into ModelNodes
	protected final ScribParser scribParser = newScribParser();

	protected final AstFactory af = newAstFactory();
	protected final EModelFactory ef = newEModelFactory();
	protected final SModelFactory sf = newSModelFactory();
	
	//protected final JScribbleApiGen jgen;  // No: API gen depends on the Job

	//public final boolean jUnit;
	public final boolean debug;  // TODO: factor out (cf. CommandLine.newMainContext)
	public final boolean useOldWF;
	public final boolean noLiveness;
	public final boolean minEfsm;
	public final boolean fair;
	public final boolean noLocalChoiceSubjectCheck;
	public final boolean noAcceptCorrelationCheck;
	public final boolean noValidation;

	private final ResourceLocator locator;  // Path -> Resource
	private final ScribModuleLoader loader;  // sesstype.ModuleName -> Pair<Resource, Module>

	protected ModuleName main;

	// ModuleName keys are full module names -- parsed are the modules read from file, distinguished from the generated projection modules
	// Resource recorded for source path
	private final Map<ModuleName, Pair<Resource, Module>> parsed = new HashMap<>();
	
	// FIXME: make Path abstract as e.g. URI -- locator is abstract but Path is coupled to concrete DirectoryResourceLocator
	private MainContext(boolean debug, ResourceLocator locator, boolean useOldWF, boolean noLiveness, boolean minEfsm,
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

	// Load main module from file system
	public MainContext(boolean debug, ResourceLocator locator, Path mainpath, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck, boolean noValidation)
					throws ScribParserException, ScribbleException
	{
		this(debug, locator, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation);

		// FIXME: checking main module resource exists at specific location should be factored out to front-end (e.g. CommandLine) -- main module resource is specified at local front end level of abstraction, while MainContext uses abstract resource loading
		//Pair<Resource, Module> p = this.loader.loadMainModule(mainpath);
		Resource res = DirectoryResourceLocator.getResourceByFullPath(mainpath);  // FIXME: hardcoded to DirectoryResourceLocator -- main module loading should be factored out to front end (e.g. CommandLine)
		Module mod = (Module) this.scribParser.parse(this.antlrParser.parseAntlrTree(res), this.af);  // FIXME: rename exceptions
		checkMainModuleName(mainpath, mod);
		
		init(res, mod);
	}

	// For inline module arg
	public MainContext(boolean debug, ResourceLocator locator, String inline, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck, boolean noValidation)
					throws ScribParserException, ScribbleException
	{
		this(debug, locator, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation);

		Resource res = new InlineResource(inline);
		Module mod = (Module) this.scribParser.parse(this.antlrParser.parseAntlrTree(res), this.af);

		init(res, mod);
	}

	private void init(Resource res, Module mainmod) throws ScribParserException, ScribbleException
	{
		Pair<Resource, Module> p = new Pair<>(res, mainmod);
		this.main = p.right.getFullModuleName();  // FIXME: main modname comes from the inlined moddecl -- check for issues if this clashes with an existing file system resource
		loadAllModules(p);
	}
	
	// A Scribble extension should override these "new" methods as appropriate.
	public Job newJob()
	{
		return new Job(this.debug, this.getParsedModules(), this.main, this.useOldWF, this.noLiveness, this.minEfsm, this.fair,
				this.noLocalChoiceSubjectCheck, this.noAcceptCorrelationCheck, this.noValidation,
				this.af, this.ef, this.sf);
	}
	
	protected AntlrParser newAntlrParser()
	{
		return new AntlrParser();
	}
	
	protected ScribParser newScribParser()
	{
		return new ScribParser();
	}
	
	protected AstFactory newAstFactory()
	{
		return new AstFactoryImpl();
	}
	
	protected EModelFactory newEModelFactory()
	{
		return new EModelFactoryImpl();
	}
	
	protected SModelFactory newSModelFactory()
	{
		return new SModelFactoryImpl();
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
					loadAllModules(this.loader.loadModule(modname, af));
				}
			}
		}
	}
	
	public Map<ModuleName, Module> getParsedModules()
	{
		return this.parsed.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> e.getValue().right));
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
