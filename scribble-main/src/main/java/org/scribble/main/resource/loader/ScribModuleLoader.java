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
package org.scribble.main.resource.loader;

import java.io.File;
import java.nio.file.Path;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Module;
import org.scribble.core.type.name.ModuleName;
import org.scribble.main.resource.InlineResource;
import org.scribble.main.resource.Resource;
import org.scribble.main.resource.locator.DirectoryResourceLocator;
import org.scribble.main.resource.locator.ResourceLocator;
import org.scribble.parser.ScribAntlrWrapper;
import org.scribble.util.Pair;
import org.scribble.util.ScribException;
import org.scribble.util.ScribParserException;

// loading: ModuleName -> Module
//   ModuleName --> Path --ResourceLocator--> Resource --ScribAntlrWrapper--> Module
public class ScribModuleLoader extends DefaultModuleLoader
{
	private ResourceLocator locator;
	private ScribAntlrWrapper antlr;

	public ScribModuleLoader(ResourceLocator locator, ScribAntlrWrapper antlr)
	{
		this.locator = locator;
		this.antlr = antlr;
	}

	// CHECKME: check only called once?  at start?
	public Pair<Resource, Module> loadMainModule(Path mainpath)
			throws ScribException, ScribParserException
	{
		Resource res = DirectoryResourceLocator.getResourceByFullPath(mainpath);
				// Hardcoded to DirectoryResourceLocator
		Module m = this.antlr.parse(res.getInputStream());  // Does del decoration
		ScribModuleLoader.checkMainModuleName(mainpath, m);
		ModuleName fullname = m.getFullModuleName();
		Pair<Resource, Module> cached = super.loadModule(fullname);
		if (cached != null)
		{
			return cached;  // CHECKME: compare loaded against cached?
		}
		registerModule(res, m);
		return new Pair<>(res, m);
	}

	// CHECKME: check only called once?  at start?
	public Pair<Resource, Module> loadMainModule(String inline)
			throws ScribException, ScribParserException
	{
		Resource res = new InlineResource(inline);
		Module m = (Module) this.antlr.parse(res.getInputStream());  // Does del decoration
		registerModule(res, m);
		return new Pair<>(res, m);
	}

	// Used to load import modules (not the main module)
	@Override
	public Pair<Resource, Module> loadModule(ModuleName fullname)
			throws ScribException, ScribParserException
	{
		Pair<Resource, Module> cached = super.loadModule(fullname);
		if (cached != null)
		{
			return cached;
		}
		Resource res = this.locator.getResource(fullname.toPath());
		Module m = this.antlr.parse(res.getInputStream());  // Does del decoration
		ScribModuleLoader.checkModuleName(fullname, res, m);
		registerModule(res, m);
		return new Pair<>(res, m);
	}

	// Check module loaded from fullname.toPath has the expected mod decl
	private static void checkModuleName(ModuleName fullname, Resource res,
			Module m)
	{
		if (!fullname.equals(m.getFullModuleName()))
		{
			throw new RuntimeException("Invalid module name "
					+ m.getFullModuleName() + " at location: " + res.getLocation());
		}
	}
	
	// For main module loaded from a path, check only the simple module name against the last path element
	// Not Scribble's job to check nested directory location of module fully corresponds to the full name of module, cf. Java classes
	private static void checkMainModuleName(Path mainpath, Module main)
			throws ScribException
	{
		String path = mainpath.toString();  // FIXME: hack
		// FileSystems.getDefault().getSeparator() ?
		int last = path.lastIndexOf(File.separator);
		String tmp = path.substring((last == -1) 
				? 0
				: last + 1, path.lastIndexOf('.'));
		if (!tmp.equals(main.getFullModuleName().getSimpleName().toString()))
				// ModuleName.toString hack?
		{
			CommonTree source = main.getModuleDeclChild().getNameNodeChild()
					.getSource();
			throw new ScribException(source, "Simple module name at path " + path
					+ " mismatch: " + main.getFullModuleName());
		}
	}
}
