package org.scribble.ext.go.parser.scribble;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.parser.antlr.ParamScribbleLexer;
import org.scribble.parser.antlr.ParamScribbleParser;
import org.scribble.parser.scribble.ScribbleAntlrWrapper;

public class RPScribbleAntlrWrapper extends ScribbleAntlrWrapper
{
	
	@Override
	protected Lexer getScribbleLexer(ANTLRStringStream ass)
	{
		return new ParamScribbleLexer(ass);
	}
	
	@Override
	protected CommonTree runScribbleParser(CommonTokenStream cts) throws RecognitionException
	{
		return (CommonTree) new ParamScribbleParser(cts).module().getTree();
	}
}
