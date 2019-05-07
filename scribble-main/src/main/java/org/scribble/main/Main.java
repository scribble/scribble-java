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
import org.scribble.core.job.CoreArgs;
import org.scribble.core.type.name.ModuleName;
import org.scribble.del.DelFactory;
import org.scribble.del.DelFactoryImpl;
import org.scribble.job.Job;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.loader.ScribModuleLoader;
import org.scribble.main.resource.locator.ResourceLocator;
import org.scribble.parser.ScribAntlrWrapper;
import org.scribble.util.Pair;
import org.scribble.util.ScribException;
import org.scribble.util.ScribParserException;

/**
 * Main loads the main module, and then all other (transitively) imported
 * modules. Its main purpose is to offer the newJob factory method.
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
	public final ModuleName main;
	public final Map<CoreArgs, Boolean> args;

	private final ScribAntlrWrapper antlr;
	//private final ResourceLocator locator;  // Path -> Resource
	private final ScribModuleLoader loader;  // ModuleName -> Pair<Resource, Module>

	// Keys are full names -- parsed are the modules read from file, distinguished from the generated projection modules
	// Resource recorded for source path
	private final Map<ModuleName, Pair<Resource, Module>> parsed 
			= new HashMap<>();

	// Load main module from file system
	// Load other modules via locator -- CHECKME: a bit inconsistent w.r.t. main?
	// TODO: make Path abstract as e.g. URI -- locator is abstract but Path is coupled to concrete DirectoryResourceLocator
	public Main(ResourceLocator locator, Path mainpath,
			Map<CoreArgs, Boolean> args) throws ScribException, ScribParserException
	{
		this(new Pair<>(locator, null), mainpath, args);
	}

	// Load an inline module arg -- module imports not allowed (currently no ResourceLocator)
	public Main(String inline, Map<CoreArgs, Boolean> args)
			throws ScribException, ScribParserException
	{
		this(new Pair<>(null, inline), null, args);
	}

	// Pre: hack.left == null xor hack.right == null
	// Hack to "unify" the constructors (to satisfy final field init more conveniently)
	private Main(Pair<ResourceLocator, String> hack, Path mainpath,
			Map<CoreArgs, Boolean> args) throws ScribException, ScribParserException
	{
		this.antlr = newAntlr();

		// Set this.loader and load main
		Pair<Resource, Module> main;
		if (hack.right == null)
		{
			//this.locator = hack.left;
			this.loader = new ScribModuleLoader(hack.left, this.antlr);
			main = this.loader.loadMainModule(mainpath);
		}
		else
		{
			//this.locator = null;
			this.loader = new ScribModuleLoader(null, this.antlr);  // CHECKME: null locator OK?
			main = this.loader.loadMainModule(hack.right);
			if (main.right.getImportDeclChildren().stream()
					.anyMatch(x -> x.isImportModule()))
			{
				throw new ScribException(
						"Module imports not permitted in inline main modules.");
						// Because (currently) null locator
			}
		}

		this.main = main.right.getFullModuleName();
		this.args = Collections.unmodifiableMap(args);
		loadAllModuleImports(main);
	}
	
	// A Scribble extension should override newAntlr/Job as appropriate
	protected ScribAntlrWrapper newAntlr()
	{
		DelFactory df = newDelFactory();
		return new ScribAntlrWrapper(df);
	}
	
	// N.B. not used by antlr generated parser itself (cf. ScribTreeAdaptor, takes source tokens and sets dels manually)
	// Here (cf. Job) because it takes antlr for token creation (and lives in scribble-parser)
	protected AstFactory newAstFactory(ScribAntlrWrapper antlr)
	{
		return new AstFactoryImpl(antlr);
	}
	
	// Here (cf. Job) because df used by this.antlr and this.af (and lives in scribble-parser)
	protected DelFactory newDelFactory()
	{
		return new DelFactoryImpl();
	}
	
	// For a Scribble extension, override newJob(parsed, args, mainFullname, AstFactory)
	public final Job newJob() throws ScribException
	{
		AstFactory af = newAstFactory(this.antlr);  
		return newJob(getParsedModules(), this.args, this.main, af, this.antlr.df);
	}

	// A Scribble extension should override newAntlr/Job as appropriate
	protected Job newJob(Map<ModuleName, Module> parsed,
			Map<CoreArgs, Boolean> args, ModuleName mainFullname, AstFactory af,
			DelFactory df) throws ScribException
	{
				// Was previously made inside Job, but AstFactoryImpl now lives in scribble-parser, to access ScribbleParser constants
		return new Job(mainFullname, args, parsed, af, df);
	}
	
	// Pre: main Module loaded by this.loader
	// Given the already loaded main, populates this.parsed and this.modcs
	// Does so by transitively loading all module imports
	private void loadAllModuleImports(Pair<Resource, Module> main)
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
