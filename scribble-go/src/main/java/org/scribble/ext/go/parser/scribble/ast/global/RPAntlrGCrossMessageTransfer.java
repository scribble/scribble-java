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
	public static final int SOURCE_NAME_CHILD_INDEX = 1;
	public static final int DESTINATION_NAME_CHILD_INDEX = 2;

	// New
	public static final int SOURCE_INDEXINTERVAL_CHILD_INDEX = 3;
	public static final int DESTINATION_INDEXINTERVAL_CHILD_INDEX = 4;

	public static RPGCrossMessageTransfer
			parseParamGCrossMessageTransfer(AntlrToScribParser parser, CommonTree root, RPAstFactory af) throws ScribParserException
	{
		MessageNode msg = AntlrGMessageTransfer.parseMessage(parser, getMessageChild(root), af);
		RoleNode srcName = AntlrSimpleName.toRoleNode(getSourceNameChild(root), af);
		RoleNode destName =  AntlrSimpleName.toRoleNode(getDestinationNameChild(root), af);

		CommonTree srcIndexInterval = getSourceIndexIntervalChild(root);
		CommonTree destIndexInterval = getDestinationIndexIntervalChild(root);
		RPIndexExpr srcStart = RPAntlrIndexExpr.parseParamIndexExpr(getParamIndexedRoleStartChild(srcIndexInterval), af);
		RPIndexExpr srcEnd = RPAntlrIndexExpr.parseParamIndexExpr(getParamIndexedRoleEndChild(srcIndexInterval), af);
		RPIndexExpr destStart = RPAntlrIndexExpr.parseParamIndexExpr(getParamIndexedRoleStartChild(destIndexInterval), af);
		RPIndexExpr destEnd = RPAntlrIndexExpr.parseParamIndexExpr(getParamIndexedRoleEndChild(destIndexInterval), af);
		return af.ParamGCrossMessageTransfer(root, srcName, msg, destName, srcStart, srcEnd, destStart, destEnd);
	}

	public static CommonTree getMessageChild(CommonTree root)
	{
		return (CommonTree) root.getChild(MESSAGE_CHILD_INDEX);
	}

	public static CommonTree getSourceNameChild(CommonTree root)
	{
		return (CommonTree) root.getChild(SOURCE_NAME_CHILD_INDEX);
	}

	public static CommonTree getDestinationNameChild(CommonTree root)
	{
		return (CommonTree) root.getChild(DESTINATION_NAME_CHILD_INDEX);
	}

	public static CommonTree getSourceIndexIntervalChild(CommonTree root)
	{
		return (CommonTree) root.getChild(SOURCE_INDEXINTERVAL_CHILD_INDEX);
	}

	public static CommonTree getDestinationIndexIntervalChild(CommonTree root)
	{
		return (CommonTree) root.getChild(DESTINATION_INDEXINTERVAL_CHILD_INDEX);
	}


	// "ParamIndexedRole" -- factor out?
	public static final int PARAMINDEXEDROLE_START_CHILD_INDEX = 0;
	public static final int PARAMINDEXEDROLE_END_CHILD_INDEX = 1;
	
	public static CommonTree getParamIndexedRoleStartChild(CommonTree root)
	{
		return (CommonTree) root.getChild(PARAMINDEXEDROLE_START_CHILD_INDEX);
	}

	public static CommonTree getParamIndexedRoleEndChild(CommonTree root)
	{
		return (CommonTree) root.getChild(PARAMINDEXEDROLE_END_CHILD_INDEX);
	}
}
