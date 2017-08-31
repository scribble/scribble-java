package org.scribble.ext.go.parser.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.ast.global.ParamGDotMessageTransfer;
import org.scribble.ext.go.parser.scribble.ast.index.ParamAntlrIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.global.AntlrGMessageTransfer;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

// Duplicated from ParamAntlrGCrossMessageTransfer
public class ParamAntlrGDotMessageTransfer
{
	// Same as original
	public static final int MESSAGE_CHILD_INDEX = 0;
	public static final int SOURCE_CHILD_INDEX = 1;
	public static final int DESTINATION_CHILD_INDEX = 2;
	
	// New
	public static final int SOURCE_START_CHILD_INDEX = 3;
	public static final int SOURCE_END_CHILD_INDEX = 4;
	public static final int DEST_START_CHILD_INDEX = 5;
	public static final int DEST_END_CHILD_INDEX = 6;

	public static ParamGDotMessageTransfer
			parseParamGDotMessageTransfer(AntlrToScribParser parser, CommonTree root, ParamAstFactory af) throws ScribParserException
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(root), af);
		MessageNode msg = AntlrGMessageTransfer.parseMessage(parser, getMessageChild(root), af);
		RoleNode dest =  AntlrSimpleName.toRoleNode(getDestChild(root), af);
		/*ParamRoleParamNode sourceStart = ParamAntlrSimpleName.toParamRoleParamNode(getSourceRangeStartChild(root), af);
		ParamRoleParamNode sourceEnd = ParamAntlrSimpleName.toParamRoleParamNode(getSourceRangeEndChild(root), af);
		ParamRoleParamNode destStart = ParamAntlrSimpleName.toParamRoleParamNode(getDestRangeStartChild(root), af);
		ParamRoleParamNode destEnd = ParamAntlrSimpleName.toParamRoleParamNode(getDestRangeEndChild(root), af);*/
		ParamIndexExpr sourceStart = ParamAntlrIndexExpr.parseParamIndexExpr(getSourceRangeStartChild(root), af);
		ParamIndexExpr sourceEnd = ParamAntlrIndexExpr.parseParamIndexExpr(getSourceRangeEndChild(root), af);
		ParamIndexExpr destStart = ParamAntlrIndexExpr.parseParamIndexExpr(getDestRangeStartChild(root), af);
		ParamIndexExpr destEnd = ParamAntlrIndexExpr.parseParamIndexExpr(getDestRangeEndChild(root), af);
		return af.ParamGDotMessageTransfer(root, src, msg, dest, sourceStart, sourceEnd, destStart, destEnd);
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
	
	public static CommonTree getSourceRangeStartChild(CommonTree root)
	{
		return (CommonTree) root.getChild(SOURCE_START_CHILD_INDEX);
	}

	public static CommonTree getSourceRangeEndChild(CommonTree root)
	{
		return (CommonTree) root.getChild(SOURCE_END_CHILD_INDEX);
	}

	public static CommonTree getDestRangeStartChild(CommonTree root)
	{
		return (CommonTree) root.getChild(DEST_START_CHILD_INDEX);
	}

	public static CommonTree getDestRangeEndChild(CommonTree root)
	{
		return (CommonTree) root.getChild(DEST_END_CHILD_INDEX);
	}
}
