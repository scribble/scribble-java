package org.scribble.parser;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.parser.antlr.Scribble2Lexer;
import org.scribble.parser.antlr.Scribble2Parser;
import org.scribble.resources.Resource;



// Parses Resources into ANTLR Trees
// Not encapsulated inside ScribbleParser, because ScribbleParser's main function is to "parse" CommonTrees into ModelNodes
public class AntlrParser
{
	//public static final AntlrParser parser = new AntlrParser();
	
	public AntlrParser()
	{
	}
	
	public CommonTree parseAntlrTree(Resource res)
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
			return (CommonTree) parser.module().getTree();
		}
		catch (RecognitionException e)
		{
			throw new RuntimeException(e);
		}
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
