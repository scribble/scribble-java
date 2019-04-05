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

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactory;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.Module;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.model.endpoint.EModelFactory;
import org.scribble.core.model.endpoint.EModelFactoryImpl;
import org.scribble.core.model.global.SModelFactory;
import org.scribble.core.model.global.SModelFactoryImpl;
import org.scribble.core.type.name.ModuleName;
import org.scribble.lang.Lang;
import org.scribble.lang.LangConfig;
import org.scribble.lang.context.ModuleContextMaker;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.loader.ScribModuleLoader;
import org.scribble.main.resource.locator.ResourceLocator;
import org.scribble.util.Pair;
import org.scribble.util.ScribException;
import org.scribble.util.ScribParserException;

// Scribble tool context for main module
// MainContext takes ResourceLocator abstractly (e.g. DirectoryResourceLocator), but because abstract Resource itself works off paths, it takes mainpath (rather than something more abstract, e.g. URI, to identify the "main" resource)
// Resource and ResourceLocator should be made abstract from (file)paths (cf. use of toPath in ScribbleModuleLoader)
public class Main
{
	//private final ResourceLocator locator;  // Path -> Resource
	private final ScribModuleLoader loader;  // ModuleName -> Pair<Resource, Module>
	
	private LangConfig config;
	protected Map<ModuleName, ModuleContext> modcs;  // Full names (like this.loaded)

	// Keys are full names -- loaded are the modules read from file, distinguished from the generated projection modules
	// Resource recorded for source path
	private final Map<ModuleName, Pair<Resource, Module>> loaded 
			= new HashMap<>();
	
	// A Scribble extension should override newLang/Antlr/Ast/ModelFactory as appropriate
	public Lang newLang()
	{
		return new Lang(getLoadedModules(), this.modcs, this.config);
	}
	
	// A Scribble extension should override newLang/Antlr/Ast/ModelFactory as appropriate
	protected ScribAntlrWrapper newAntlr()
	{
		return new ScribAntlrWrapper();
	}
	
	// A Scribble extension should override newLang/Antlr/Ast/ModelFactory as appropriate
	protected AstFactory newAstFactory()
	{
		return new AstFactoryImpl();
	}
	
	// A Scribble extension should override newLang/Antlr/Ast/ModelFactory as appropriate
	protected EModelFactory newEModelFactory()
	{
		return new EModelFactoryImpl();
	}
	
	// A Scribble extension should override newLang/Antlr/Ast/ModelFactory as appropriate
	// CHECKME: combine with newEModelFactory ?
	protected SModelFactory newSModelFactory()
	{
		return new SModelFactoryImpl();
	}

	// Load main module from file system
	public Main(ResourceLocator locator, 
			boolean debug, Path mainpath, boolean useOldWF, boolean noLiveness,
			boolean minEfsm, boolean fair, boolean noLocalChoiceSubjectCheck,
			boolean noAcceptCorrelationCheck, boolean noValidation, boolean spin)
			throws ScribException, ScribParserException
	{
		this(locator);
		Pair<Resource, Module> main = this.loader.loadMainModule(mainpath);
		init(main, 
				debug, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck,
				noAcceptCorrelationCheck, noValidation, spin);
	}

	// For inline module arg
	public Main(String inline,
			boolean debug, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck,
			boolean noAcceptCorrelationCheck, boolean noValidation, boolean spin)
			throws ScribException, ScribParserException
	{
		this(null);  // CHECKME: null locator
		Pair<Resource, Module> main = this.loader.loadMainModule(inline);
		if (main.right.getImportDeclChildren().stream()
				.anyMatch(x -> x.isImportModule()))
		{
			throw new ScribException(
					"Module imports not permitted in inline main modules.");
					// Because (currently) null locator
		}
		init(main, 
				debug, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck,
				noAcceptCorrelationCheck, noValidation, spin);
	}
	
	// TODO: make Path abstract as e.g. URI -- locator is abstract but Path is coupled to concrete DirectoryResourceLocator
	private Main(ResourceLocator locator)
			throws ScribParserException, ScribException
	{
		//this.locator = locator; 
		this.loader = new ScribModuleLoader(locator, newAntlr());
	}

	private void init(Pair<Resource, Module> main, 
			boolean debug, boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair,
			boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck,
			boolean noValidation, boolean spin)
			throws ScribParserException, ScribException
	{
		loadAllModuleImports(main);  // Populates this.loaded

		// CHECKME(?): main modname comes from the inlined mod decl -- check for issues if this clashes with an existing file system resource
		this.config = new LangConfig(main.right.getFullModuleName(),
				debug, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck,
				noAcceptCorrelationCheck, noValidation, spin,
				newAstFactory(), newEModelFactory(), newSModelFactory());

		// CHECKME: how does this relate to the ModuleContextBuilder pass?
		// Job.modcs seems unused?  Lang.modcs is used though, by old AST visitors -- basically old ModuleContextVisitor is redundant?
		// Job.modcs could be used, but disamb already done by Lang

		Map<ModuleName, Module> loaded = getLoadedModules();
		ModuleContextMaker maker = new ModuleContextMaker();
		Map<ModuleName, ModuleContext> modcs = new HashMap<>();
		for (ModuleName fullname : this.loaded.keySet())
		{
			Pair<Resource, Module> e = this.loaded.get(fullname);
			modcs.put(fullname, maker.make(loaded, e.right));  // throws ScribException
		}
		this.modcs = Collections.unmodifiableMap(modcs);
	}

	// Populates this.loaded
	private void loadAllModuleImports(Pair<Resource, Module> m)
			throws ScribParserException, ScribException
	{
		this.loaded.put(m.right.getFullModuleName(), m);
		for (ImportDecl<?> id : m.right.getImportDeclChildren())
		{
			if (id.isImportModule())
			{
				ModuleName fullname = 
						((ImportModule) id).getModuleNameNodeChild().toName();
				if (!this.loaded.containsKey(fullname))
				{
					loadAllModuleImports(this.loader.loadModule(fullname));
				}
			}
		}
	}
	
	private Map<ModuleName, Module> getLoadedModules()
	{
		return this.loaded.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, e -> e.getValue().right));
	}
}
