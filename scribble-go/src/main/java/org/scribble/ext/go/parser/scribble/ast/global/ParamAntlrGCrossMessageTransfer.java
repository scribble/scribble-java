package org.scribble.ext.go.parser.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.ast.global.ParamGCrossMessageTransfer;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.global.AntlrGMessageTransfer;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class ParamAntlrGCrossMessageTransfer
{
	// Same as original
	public static final int MESSAGE_CHILD_INDEX = 0;
	public static final int SOURCE_CHILD_INDEX = 1;
	public static final int DESTINATION_CHILD_INDEX = 2;
	
	public static final int SOURCE_START_CHILD_INDEX = 3;
	public static final int SOURCE_END_CHILD_INDEX = 4;
	public static final int DEST_START_CHILD_INDEX = 5;
	public static final int DEST_END_CHILD_INDEX = 6;

	public static ParamGCrossMessageTransfer
			parseParamGCrossMessageTransfer(AntlrToScribParser parser, CommonTree root, ParamAstFactory af) throws ScribParserException
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(root), af);
		MessageNode msg = AntlrGMessageTransfer.parseMessage(parser, getMessageChild(root), af);
		RoleNode dest =  AntlrSimpleName.toRoleNode(getDestChild(root), af);
		int sourceStart = getSourceStartIndex(root);
		int sourceEnd = getSourceEndIndex(root);
		int destStart = getDestStartIndex(root);
		int destEnd = getDestEndIndex(root);
		return af.ParamGCrossMessageTransfer(root, src, msg, dest, sourceStart, sourceEnd, destStart, destEnd);
	}

	public static CommonTree getMessageChild(CommonTree root)
	{
		return (CommonTree) root.getChild(MESSAGE_CHILD_INDEX);
	}

	public static CommonTree getSourceChild(CommonTree root)
	{
		return (CommonTree) root.getChild(SOURCE_CHILD_INDEX);
	}

	public static CommonTree getDestChild(CommonTree root)
	{
		return (CommonTree) root.getChild(DESTINATION_CHILD_INDEX);
	}

	public static int getSourceStartIndex(CommonTree root)
	{
		return Integer.parseInt(root.getChild(SOURCE_START_CHILD_INDEX).getText());
	}

	public static int getSourceEndIndex(CommonTree root)
	{
		return Integer.parseInt(root.getChild(SOURCE_END_CHILD_INDEX).getText());
	}

	public static int getDestStartIndex(CommonTree root)
	{
		return Integer.parseInt(root.getChild(DEST_START_CHILD_INDEX).getText());
	}

	public static int getDestEndIndex(CommonTree root)
	{
		return Integer.parseInt(root.getChild(DEST_END_CHILD_INDEX).getText());
	}
}
