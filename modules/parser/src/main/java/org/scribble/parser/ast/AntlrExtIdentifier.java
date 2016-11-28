package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;

public class AntlrExtIdentifier
{
	public static final String getName(CommonTree ct)
	{
		String text = ct.getText();
		return text.substring(1, text.length() - 1);
	}
}
