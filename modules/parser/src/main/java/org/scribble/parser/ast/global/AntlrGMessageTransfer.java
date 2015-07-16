package org.scribble.parser.ast.global;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.AntlrConstants.AntlrNodeType;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrAmbigName;
import org.scribble.parser.ast.name.AntlrQualifiedName;
import org.scribble.parser.ast.name.AntlrSimpleName;
import org.scribble.parser.util.ScribParserUtil;

public class AntlrGMessageTransfer
{
	public static final int MESSAGE_CHILD_INDEX = 0;
	public static final int SOURCE_CHILD_INDEX = 1;
	public static final int DESTINATION_CHILDREN_START_INDEX = 2;

	public static GMessageTransfer parseGMessageTransfer(ScribParser parser, CommonTree ct)
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct));
		MessageNode msg = parseMessage(parser, getMessageChild(ct));
		List<RoleNode> dests = 
			getDestChildren(ct).stream().map((d) -> AntlrSimpleName.toRoleNode(d)).collect(Collectors.toList());
		return AstFactoryImpl.FACTORY.GMessageTransfer(src, msg, dests);
	}

	protected static MessageNode parseMessage(ScribParser parser, CommonTree ct)
	{
		AntlrNodeType type = ScribParserUtil.getAntlrNodeType(ct);
		if (type == AntlrNodeType.MESSAGESIGNATURE)
		{
			return (MessageSigNode) parser.parse(ct);
		}
		else //if (type.equals(AntlrConstants.AMBIGUOUSNAME_NODE_TYPE))
		{
			return (ct.getChildCount() == 1)
				? AntlrAmbigName.toAmbigNameNode(ct)  // parametername or simple messagesignaturename
				: AntlrQualifiedName.toMessageSigNameNode(ct);
		}
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static CommonTree getMessageChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MESSAGE_CHILD_INDEX);
	}

	public static List<CommonTree> getDestChildren(CommonTree ct)
	{
		return ScribParserUtil.toCommonTreeList(ct.getChildren().subList(DESTINATION_CHILDREN_START_INDEX, ct.getChildCount()));
	}
}
