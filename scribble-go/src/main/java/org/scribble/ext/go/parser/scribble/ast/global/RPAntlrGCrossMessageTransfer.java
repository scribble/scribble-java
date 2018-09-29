package org.scribble.ext.go.parser.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.ast.global.RPGCrossMessageTransfer;
import org.scribble.ext.go.parser.scribble.ast.index.RPAntlrIndexExpr;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.global.AntlrGMessageTransfer;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class RPAntlrGCrossMessageTransfer
{
	// Same as original
	public static final int MESSAGE_CHILD_INDEX = 0;
	public static final int SOURCE_CHILD_INDEX = 1;
	public static final int DESTINATION_CHILD_INDEX = 2;

	public static RPGCrossMessageTransfer
			parseParamGCrossMessageTransfer(AntlrToScribParser parser, CommonTree root, RPAstFactory af) throws ScribParserException
	{
		MessageNode msg = AntlrGMessageTransfer.parseMessage(parser, getMessageChild(root), af);
		CommonTree src = getSourceChild(root);
		CommonTree dest =  getDestChild(root);
		
		RoleNode srcName = AntlrSimpleName.toRoleNode(getParamIndexedRoleNameChild(src), af);
		RPIndexExpr srcStart = RPAntlrIndexExpr.parseParamIndexExpr(getParamIndexedRoleStartChild(src), af);
		RPIndexExpr srcEnd = RPAntlrIndexExpr.parseParamIndexExpr(getParamIndexedRoleEndChild(src), af);
		RoleNode destName =  AntlrSimpleName.toRoleNode(getParamIndexedRoleNameChild(dest), af);
		RPIndexExpr destStart = RPAntlrIndexExpr.parseParamIndexExpr(getParamIndexedRoleStartChild(dest), af);
		RPIndexExpr destEnd = RPAntlrIndexExpr.parseParamIndexExpr(getParamIndexedRoleEndChild(dest), af);
		return af.ParamGCrossMessageTransfer(root, srcName, msg, destName, srcStart, srcEnd, destStart, destEnd);
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


	// "ParamIndexedRole" -- factor out?
	public static final int PARAMINDEXEDROLE_NAME_CHILD_INDEX = 0;
	public static final int PARAMINDEXEDROLE_START_CHILD_INDEX = 1;
	public static final int PARAMINDEXEDROLE_END_CHILD_INDEX = 2;

	public static CommonTree getParamIndexedRoleNameChild(CommonTree root)
	{
		return (CommonTree) root.getChild(PARAMINDEXEDROLE_NAME_CHILD_INDEX);
	}
	
	public static CommonTree getParamIndexedRoleStartChild(CommonTree root)
	{
		return (CommonTree) root.getChild(PARAMINDEXEDROLE_START_CHILD_INDEX);
	}

	public static CommonTree getParamIndexedRoleEndChild(CommonTree root)
	{
		return (CommonTree) root.getChild(PARAMINDEXEDROLE_END_CHILD_INDEX);
	}
}
