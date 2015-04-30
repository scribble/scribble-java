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
import org.scribble.context.ModuleLoader;
import org.scribble.model.Module;
import org.scribble.parser.antlr.Scribble2Lexer;
import org.scribble.parser.antlr.Scribble2Parser;
import org.scribble.resources.Resource;
import org.scribble.resources.ResourceLocator;
import org.scribble2.parser.util.Pair;

// loading = file input + parsing (i.e. path -> Module; cf. ModuleName -> Module)
public class ScribbleModuleLoader /*extends DefaultModuleLoader*/ implements ModuleLoader
{
	//private static final Logger LOG=Logger.getLogger(ModuleLoader.class.getName());

	private ResourceLocator locator = null;
	//private ProtocolParser parser = null;
	private ScribbleParser parser = null;
	//private IssueLogger _logger=null;
	
	public ScribbleModuleLoader(ResourceLocator locator, ScribbleParser parser)
	{
		this.locator = locator;
		this.parser = parser;
	}

	@Override
	public Module loadModule(String path)
	{
		throw new RuntimeException("Shouldn't get in here.");
	}

	//public Module loadModule(String module)
	public Pair<Resource, org.scribble2.model.Module> loadScribbleModule(String path)
	{
		//Module ret=super.loadModule(module);
		Resource res = this.locator.searchResourceOnImportPaths(path);
		return new Pair<>(res, parseModuleFromResource(res));
	}
	
	private byte[] readResource(Resource res)
	{
		try (InputStream is = res.getInputStream())
		{
			byte[] bs = new byte[is.available()];
			is.read(bs);
			return bs;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private org.scribble2.model.Module parseModuleFromResource(Resource res) // throws ScribbleException
	{
		try
		{
			// CharStream input = isFile ? new ANTLRFileStream(path) : new
			// ANTLRInputStream(System.in);
			/*CharStream input = new ANTLRFileStream(res.getPath());
			Scribble2Lexer lex = new Scribble2Lexer(input);  // FIXME: use Resource inputStream*/
			String input = new String(readResource(res));
			Scribble2Lexer lex = new Scribble2Lexer(new ANTLRStringStream(input));
			Scribble2Parser parser = new Scribble2Parser(new CommonTokenStream(lex));
			CommonTree ct = (CommonTree) parser.module().getTree();
			org.scribble2.model.Module module = (org.scribble2.model.Module) this.parser.parse(ct);

			// FIXME: check loaded module name correct

			return module;
		}
		catch (RecognitionException e)
		{
			// throw new ScribbleException(e);
			throw new RuntimeException(e);
		}
	}
}
