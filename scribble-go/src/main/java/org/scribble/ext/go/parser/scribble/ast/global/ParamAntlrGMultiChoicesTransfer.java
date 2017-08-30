package org.scribble.ext.go.parser.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.ast.global.ParamGMultiChoicesTransfer;
import org.scribble.ext.go.parser.scribble.ast.index.ParamAntlrIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.ext.go.type.index.ParamIndexVar;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.global.AntlrGMessageTransfer;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class ParamAntlrGMultiChoicesTransfer
{
	// Same as original
	public static final int MESSAGE_CHILD_INDEX = 0;
	public static final int SOURCE_CHILD_INDEX = 1;
	public static final int DESTINATION_CHILD_INDEX = 2;
	
	// New
	public static final int INDEX_VAR_CHILD_INDEX = 3;
	public static final int DEST_START_CHILD_INDEX = 4;
	public static final int DEST_END_CHILD_INDEX = 5;

	public static ParamGMultiChoicesTransfer
			parseParamGMultiChoicesTransfer(AntlrToScribParser parser, CommonTree root, ParamAstFactory af) throws ScribParserException
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(root), af);
		MessageNode msg = AntlrGMessageTransfer.parseMessage(parser, getMessageChild(root), af);
		RoleNode dest =  AntlrSimpleName.toRoleNode(getDestChild(root), af);
		ParamIndexVar var = (ParamIndexVar) ParamAntlrIndexExpr.parseParamIndexExpr(getIndexVarChild(root), af);
		ParamIndexExpr destStart = ParamAntlrIndexExpr.parseParamIndexExpr(getDestRangeStartChild(root), af);
		ParamIndexExpr destEnd = ParamAntlrIndexExpr.parseParamIndexExpr(getDestRangeEndChild(root), af);
		return af.ParamGMultiChoicesTransfer(root, src, msg, dest, var, destStart, destEnd);
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
	
	public static CommonTree getIndexVarChild(CommonTree root)
	{
		return (CommonTree) root.getChild(INDEX_VAR_CHILD_INDEX);
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
