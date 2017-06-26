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
package org.scribble.parser.scribble;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.main.resource.Resource;
import org.scribble.parser.antlr.ScribbleLexer;
import org.scribble.parser.antlr.ScribbleParser;

// Resource -> ANTLR CommonTree
// Parses Resources into ANTLR CommonTrees
// Not encapsulated inside ScribbleParser, because ScribbleParser's main function is the higher-level operation of "parsing" CommonTrees into ScribNodes
public class AntlrParser
{
	public AntlrParser()
	{

	}
	
	// Scribble extensions should override these "new" methods
	protected Lexer newScribbleLexer(ANTLRStringStream ass)
	{
		return new ScribbleLexer(ass);
	}
	
	protected Parser newScribbleParser(CommonTokenStream cts)  // FIXME: need an interface for Scribble top-level module method (ANTLR "grammar actions"?)
	{
		return new ScribbleParser(cts);
	}

	public CommonTree parseAntlrTree(Resource res)
	{
		//try
		{
			String input = readInput(res);
			Lexer lex = newScribbleLexer(new ANTLRStringStream(input));
			Parser parser = newScribbleParser(new CommonTokenStream(lex));
			//return (CommonTree) parser.module().getTree();
			
			Class<? extends Parser> c = parser.getClass();
			try
			{
				Method m = c.getMethod("module");
				return (CommonTree) ((ParserRuleReturnScope) m.invoke(parser)).getTree();
			}
			catch (NoSuchMethodException nsme)
			{
				throw new RuntimeException("Supplied Parser has no \"module\" method.", nsme);
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException("Shouldn't get in here: ", e);
			}
			catch (IllegalArgumentException e)
			{
				throw new RuntimeException("Shouldn't get in here: ", e);
			}
			catch (InvocationTargetException e)
			{
				if (e.getCause() instanceof RecognitionException)
				{
					throw new RuntimeException(e);
				}
				throw new RuntimeException("Shouldn't get in here: ", e);
			}
		}
		/*catch (RecognitionException e)
		{
			throw new RuntimeException(e);
		}*/
	}
	
	/*public CommonTree parseAntlrTree(Resource res)
	{
		try
		{
			String input = readInput(res);
			ScribbleLexer lex = new ScribbleLexer(new ANTLRStringStream(input));
			ScribbleParser parser = new ScribbleParser(new CommonTokenStream(lex));
			return (CommonTree) parser.module().getTree();
		}
		catch (RecognitionException e)
		{
			throw new RuntimeException(e);
		}
	}*/
	
	protected static String readInput(Resource res)
	{
		return new String(readResource(res));
	}

	private static byte[] readResource(Resource res)
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
}
