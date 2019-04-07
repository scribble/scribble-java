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
package org.scribble.main;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.ImportModule;
import org.scribble.ast.Module;
import org.scribble.core.model.endpoint.EModelFactoryImpl;
import org.scribble.core.model.global.SModelFactoryImpl;
import org.scribble.core.type.name.ModuleName;
import org.scribble.lang.Lang;
import org.scribble.lang.LangConfig;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.loader.ScribModuleLoader;
import org.scribble.main.resource.locator.ResourceLocator;
import org.scribble.parser.scribble.ScribAntlrWrapper;
import org.scribble.util.Pair;
import org.scribble.util.ScribException;
import org.scribble.util.ScribParserException;

/**
 * Main loads the main module, and then all other (transitively) imported
 * modules. Its main purpose is to offer the newLang factory method.
 * 
 * Main takes an abstract ResourceLocator, e.g. DirectoryResourceLocator -- (for
 * non-inline main modules)...
 * ...but because Resource is primarily based on file paths, it takes mainpath
 * (rather than something more abstract, e.g., URI) to identify the "main"
 * resource.
 * 
 * TODO: Resource and ResourceLocator should be made more abstract from (file)
 * paths (cf. use of modname.toPath in ScribModuleLoader).
 */
public class Main
{
	//private final ResourceLocator locator;  // Path -> Resource
	private final ScribModuleLoader loader;  // ModuleName -> Pair<Resource, Module>
	
	public final LangConfig config;

	// Keys are full names -- parsed are the modules read from file, distinguished from the generated projection modules
	// Resource recorded for source path
	private final Map<ModuleName, Pair<Resource, Module>> parsed 
			= new HashMap<>();
	
	// A Scribble extension should override newAntlr/Lang as appropriate
	protected ScribAntlrWrapper newAntlr()
	{
		return new ScribAntlrWrapper();
	}
	
	// A Scribble extension should override newAntlr/Lang as appropriate
	public Lang newLang() throws ScribException
	{
		return new Lang(getParsedModules(), this.config);
	}

	// Load main module from file system
	// Load other modules via locator -- CHECKME: a bit inconsistent w.r.t. main?
	// TODO: make Path abstract as e.g. URI -- locator is abstract but Path is coupled to concrete DirectoryResourceLocator
	public Main(ResourceLocator locator, Path mainpath, 
			boolean debug, boolean useOldWF, boolean noLiveness,
			boolean minEfsm, boolean fair, boolean noLocalChoiceSubjectCheck,
			boolean noAcceptCorrelationCheck, boolean noValidation, boolean spin)
			throws ScribException, ScribParserException
	{
		this(new Pair<>(locator, null), mainpath, 
				debug, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck,
				noAcceptCorrelationCheck, noValidation, spin);
	}

	// Load an inline module arg -- module imports not allowed (currently no ResourceLocator)
	public Main(String inline,
			boolean debug, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck,
			boolean noAcceptCorrelationCheck, boolean noValidation, boolean spin)
			throws ScribException, ScribParserException
	{
		this(new Pair<>(null, inline), null, 
				debug, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck,
				noAcceptCorrelationCheck, noValidation, spin);
	}

	private Main(Pair<ResourceLocator, String> hack, Path mainpath, 
			boolean debug, boolean useOldWF, boolean noLiveness, boolean minEfsm,
			boolean fair, boolean noLocalChoiceSubjectCheck,
			boolean noAcceptCorrelationCheck, boolean noValidation, boolean spin)
			throws ScribException, ScribParserException
	{
		Pair<Resource, Module> main;
		if (hack.right == null)
		{
			this.loader = new ScribModuleLoader(hack.left, newAntlr());
			main = this.loader.loadMainModule(mainpath);
		}
		else
		{
			//this.locator = null;
			this.loader = new ScribModuleLoader(null, newAntlr());  // CHECKME: null locator OK?
			main = this.loader.loadMainModule(hack.right);
			if (main.right.getImportDeclChildren().stream()
					.anyMatch(x -> x.isImportModule()))
			{
				throw new ScribException(
						"Module imports not permitted in inline main modules.");
						// Because (currently) null locator
			}
		}

		loadAllModuleImports(main, 
				debug, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck,
				noAcceptCorrelationCheck, noValidation, spin);

		// CHECKME(?): main modname comes from the inlined mod decl -- check for issues if this clashes with an existing file system resource
		// FIXME: wrap flags in Map and move Config construction to Lang
		this.config = newLangConfig(main.right.getFullModuleName(),
				debug, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck,
				noAcceptCorrelationCheck, noValidation, spin);
	}
	
	//...FIXME: replace bools by a Map -- needed for subclassing -- pass Map to Lang instead of making config here?
	public LangConfig newLangConfig(ModuleName mainFullname,
			boolean debug, boolean useOldWF, boolean noLiveness,
			boolean minEfsm, boolean fair, boolean noLocalChoiceSubjectCheck,
			boolean noAcceptCorrelationCheck, boolean noValidation, boolean spin)
	{
		return new LangConfig(mainFullname,
				debug, useOldWF, noLiveness, minEfsm, fair, noLocalChoiceSubjectCheck,
				noAcceptCorrelationCheck, noValidation, spin,
				new AstFactoryImpl(), new EModelFactoryImpl(), new SModelFactoryImpl());
				// CHECKME: combine E/SModelFactory?
	}
	
	// Pre: main Module loaded by this.loader
	// Given the already loaded main, populates this.parsed and this.modcs
	// Does so by transitively loading all module imports
	private void loadAllModuleImports(Pair<Resource, Module> main, 
			boolean debug, boolean useOldWF, boolean noLiveness, boolean minEfsm, boolean fair,
			boolean noLocalChoiceSubjectCheck, boolean noAcceptCorrelationCheck,
			boolean noValidation, boolean spin)
			throws ScribParserException, ScribException
	{
		loadAllAux(main);  // Populates this.parsed
	}

	// Recursively records m in this.parsed and loads all module imports of m
	private void loadAllAux(Pair<Resource, Module> m)
			throws ScribParserException, ScribException
	{
		this.parsed.put(m.right.getFullModuleName(), m);
		for (ImportDecl<?> id : m.right.getImportDeclChildren())
		{
			if (id.isImportModule())
			{
				ModuleName fullname = 
						((ImportModule) id).getModuleNameNodeChild().toName();
				if (!this.parsed.containsKey(fullname))
				{
					loadAllAux(this.loader.loadModule(fullname));
				}
			}
		}
	}
	
	public Map<ModuleName, Module> getParsedModules()
	{
		return this.parsed.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, e -> e.getValue().right));
	}
}
