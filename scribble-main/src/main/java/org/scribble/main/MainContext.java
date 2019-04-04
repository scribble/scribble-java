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

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.Module;
import org.scribble.job.Job;
import org.scribble.job.JobConfig;
import org.scribble.job.ScribbleException;
import org.scribble.lang.context.ModuleContext;
import org.scribble.lang.context.ModuleContextMaker;
import org.scribble.main.resource.DirectoryResourceLocator;
import org.scribble.main.resource.InlineResource;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.ResourceLocator;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.endpoint.EModelFactoryImpl;
import org.scribble.model.global.SModelFactory;
import org.scribble.model.global.SModelFactoryImpl;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ScribbleAntlrWrapper;
import org.scribble.type.name.ModuleName;
import org.scribble.util.Pair;
import org.scribble.util.ScribParserException;

// Scribble tool context for main module
// MainContext takes ResourceLocator abstractly (e.g. DirectoryResourceLocator), but because abstract Resource itself works off paths, it takes mainpath (rather than something more abstract, e.g. URI, to identify the "main" resource)
// Resource and ResourceLocator should be made abstract from (file)paths (cf. use of toPath in ScribbleModuleLoader)
public class MainContext
{
	
	// HERE FIXME: refactor constructors/init
	
	
	// Only "manually" used here for loading main module (which should be factored out to front end) -- otherwise, only used within loader
	protected final ScribbleAntlrWrapper antlrParser = newAntlrParser();  // Not encapsulated inside ScribbleParser, because ScribbleParser's main function is to "parse" ANTLR CommonTrees into ModelNodes
	protected final AntlrToScribParser scribParser = newScribParser();

	protected final AstFactory af = newAstFactory();
	protected final EModelFactory ef = newEModelFactory();
	protected final SModelFactory sf = newSModelFactory();
	
	//protected final JScribbleApiGen jgen;  // No: API gen depends on the Job

	//public final boolean jUnit;
	private JobConfig config;

	private final ResourceLocator locator;  // Path -> Resource
	private final ScribModuleLoader loader;  // sesstype.ModuleName -> Pair<Resource, Module>

	protected Map<ModuleName, ModuleContext> mctxts;  // Full names (like this.parsed)

	// ModuleName keys are full module names -- parsed are the modules read from file, distinguished from the generated projection modules
	// Resource recorded for source path
	private final Map<ModuleName, Pair<Resource, Module>> parsed = new HashMap<>();
	
	// TODO: make Path abstract as e.g. URI -- locator is abstract but Path is coupled to concrete DirectoryResourceLocator
	private MainContext(boolean debug, ResourceLocator locator, boolean useOldWF,
			boolean noLiveness, boolean minEfsm, boolean fair,
			boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck,
			boolean noValidation, boolean spin)
			throws ScribParserException, ScribbleException
	{
		this.locator = locator; 
		this.loader = new ScribModuleLoader(this.locator, this.antlrParser);//, this.scribParser);
	}

	// Load main module from file system
	public MainContext(boolean debug, ResourceLocator locator, Path mainpath,
			boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair,
			boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck,
			boolean noValidation, boolean spin)
			throws ScribParserException, ScribbleException
	{
		this(debug, locator, useOldWF, noLiveness, minEfsm, fair,
				noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation,
				spin);

		// TODO: checking main module resource exists at specific location should be factored out to front-end (e.g. CommandLine) -- main module resource is specified at local front end level of abstraction, while MainContext uses abstract resource loading
		//Pair<Resource, Module> p = this.loader.loadMainModule(mainpath);
		Resource res = DirectoryResourceLocator.getResourceByFullPath(mainpath);  
				// TODO: hardcoded to DirectoryResourceLocator -- main module loading should be factored out to front end (e.g. CommandLine)
		Module mod = this.antlrParser.parseAntlrTree(res);  // Does del decoration
		
		// FIXME HERE: deprecate scribParser -- move ANTLR error checking out
		
		/*Module mod = (Module) atree;//this.scribParser.parse(atree, this.af);  // TODO: rename exceptions
		
		/*HERE del decorator
		move ast to parser?  and make core/lang*/

		//HERE: debug context building or name disamb (role node child lost)*/
		
		checkMainModuleName(mainpath, mod, noValidation);
		
		init(
				 debug, locator,  useOldWF,
			 noLiveness,  minEfsm,  fair,
			 noLocalChoiceSubjectCheck,  noAcceptCorrelationCheck,
			 noValidation,  spin,
				res, mod);
	}

	// For inline module arg
	public MainContext(boolean debug, ResourceLocator locator, String inline,
			boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair,
			boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck,
			boolean noValidation, boolean spin)
			throws ScribParserException, ScribbleException
	{
		this(debug, locator, useOldWF, noLiveness, minEfsm, fair,
				noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation,
				spin);

		Resource res = new InlineResource(inline);
		/*Module mod = (Module) this.scribParser
				.parse(this.antlrParser.parseAntlrTree(res), this.af);*/
		Module mod = (Module) this.antlrParser.parseAntlrTree(res);  // Does del decoration

		init(
				 debug, locator,  useOldWF,
			 noLiveness,  minEfsm,  fair,
			 noLocalChoiceSubjectCheck,  noAcceptCorrelationCheck,
			 noValidation,  spin,
				res, mod);
	}

	private void init(
			boolean debug, ResourceLocator locator,
			boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair,
			boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck,
			boolean noValidation, boolean spin,
			
			Resource res, Module mainmod)
			throws ScribParserException, ScribbleException
	{

		Pair<Resource, Module> p = new Pair<>(res, mainmod);
		ModuleName main = p.right.getFullModuleName();  
				// TODO CHECKME: main modname comes from the inlined moddecl -- check for issues if this clashes with an existing file system resource
		loadAllModules(p);  // Populates this.parsed

		this.config = new JobConfig(debug, main, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck, noAcceptCorrelationCheck, noValidation, spin, this.af, this.ef, this.sf);
		
		Map<ModuleName, Module> parsed = getParsedModules();
		Map<ModuleName, ModuleContext> map = new HashMap<>();
		ModuleContextMaker maker = new ModuleContextMaker();
		for (ModuleName fullname : this.parsed.keySet())
		{
			Pair<Resource, Module> e = this.parsed.get(fullname);
			map.put(fullname, //new ModuleContext(parsed, e.right));  
						maker.make(parsed, e.right));
					// CHECKME: how does this relate to the ModuleContextBuilder pass ?
		}
		this.mctxts = Collections.unmodifiableMap(map);
	}
	
	// A Scribble extension should override these "new" methods as appropriate.
	public Job newJob()
	{
		return new Job(this.getParsedModules(), this.mctxts, this.config);
	}
	
	protected ScribbleAntlrWrapper newAntlrParser()
	{
		return new ScribbleAntlrWrapper();
	}
	
	protected AntlrToScribParser newScribParser()
	{
		return new AntlrToScribParser();
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

	// Populates this.parsed
	private void loadAllModules(Pair<Resource, Module> module)
			throws ScribParserException, ScribbleException
	{
		this.parsed.put(module.right.getFullModuleName(), module);
		for (ImportDecl<?> id : module.right.getImportDeclChildren())
		{
			if (id.isImportModule())
			{
				ModuleName modname = 
						((ImportModule) id).getModuleNameNodeChild().toName();
				if (!this.parsed.containsKey(modname))
				{
					loadAllModules(this.loader.loadModule(modname, this.af));
				}
			}
		}
	}
	
	// Hacky? But not Scribble tool's job to check nested directory location of module fully corresponds to the fullname of module? Cf. Java classes
	private void checkMainModuleName(Path mainpath, Module main, boolean noValidation)
			throws ScribbleException
	{
		String path = mainpath.toString();  // FIXME: hack
		// FileSystems.getDefault().getSeparator() ?
		String tmp = path.substring((path.lastIndexOf(File.separator) == -1) 
				? 0
				: path.lastIndexOf(File.separator) + 1, path.lastIndexOf('.'));
		if (!noValidation)  // this.config not made yet
		{
			if (!tmp.equals(main.getFullModuleName().getSimpleName().toString()))
					// ModuleName.toString hack?
			{
				CommonTree source = main.getModuleDeclChild().getNameNodeChild()
						.getSource();
				throw new ScribbleException(source, "Simple module name at path " + path
						+ " mismatch: " + main.getFullModuleName());
			}
		}
	}
	
	private Map<ModuleName, Module> getParsedModules()
	{
		return this.parsed.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, e -> e.getValue().right));
	}
}
