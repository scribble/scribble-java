/*
 * Copyright 2009-11 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble2.parser;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.context.DefaultModuleLoader;
import org.scribble.context.ModuleLoader;
import org.scribble.model.Module;
import org.scribble.parser.antlr.Scribble2Lexer;
import org.scribble.parser.antlr.Scribble2Parser;
import org.scribble.resources.ResourceLocator;
import org.scribble.resources.Resource;
import org.scribble.resources.IResourceLocator;
import org.scribble2.parser.util.Pair;
import org.scribble2.sesstype.name.ModuleName;

// loading = file input + parsing (i.e. path -> Module; cf. ModuleName -> Module)
public class ScribbleModuleLoader extends DefaultModuleLoader implements ModuleLoader
{
	//private static final Logger LOG=Logger.getLogger(ModuleLoader.class.getName());

	private ResourceLocator locator;
	//private ProtocolParser parser = null;
	private AntlrParser antlr;
	private ScribbleParser parser;
	//private IssueLogger _logger=null;
	
	public ScribbleModuleLoader(ResourceLocator locator, AntlrParser antlr, ScribbleParser parser)
	{
		this.locator = locator;
		this.antlr = antlr;
		this.parser = parser;
	}

	@Override
	public Module loadModule(String module)
	{
		/*Module mod = super.loadModule(module);
		if (mod == null)
		{
			// ...convert module name string to ModuleName...
			return loadScribbleModule(name);
		}*/
		throw new RuntimeException("Shouldn't get in here.");
	}

  // FIXME: old/new Modules incompatible
	//@Override
	public void registerModule(org.scribble2.model.Module module)
	{
		//super.registerModule(module);
	}

	//public Module loadModule(String module)
	//public Pair<Resource, org.scribble2.model.Module> loadScribbleModule(String path)
	public Pair<Resource, org.scribble2.model.Module> loadScribbleModule(ModuleName mod)
	{
		//Module ret=super.loadModule(module);
		Resource res = this.locator.getResource(mod.toPath());
		org.scribble2.model.Module parsed = parseModuleFromResource(res);
		checkModuleName(mod, res, parsed);
		registerModule(parsed);
		return new Pair<>(res, parsed);
	}

	private static void checkModuleName(ModuleName mn, Resource res, org.scribble2.model.Module mod)
	{
		if (!mn.equals(mod.getFullModuleName()))
		{
			throw new RuntimeException("Invalid module name " + mod.getFullModuleName() + " at path: " + res.getPath());
		}
	}
	
	/*public Pair<Resource, org.scribble2.model.Module> loadMainModule(String path)
	{
		Resource res = this.locator.getResourceByFullPath(path);
		Pair<Resource, org.scribble2.model.Module> p = new Pair<>(res, parseModuleFromResource(res));
		registerModule(p.right);
		return p;
	}*/
	
	private org.scribble2.model.Module parseModuleFromResource(Resource res) // throws ScribbleException
	{
		org.scribble2.model.Module module = (org.scribble2.model.Module) this.parser.parse(this.antlr.parseAntlrTree(res));

		// FIXME: check loaded module name correct

		return module;
	}
}
