package org.scribble.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.global.GInterrupt;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;
import org.scribble.parser.util.ScribParserUtil;
import org.scribble.util.ScribParserException;


public class AntlrGInterrupt
{
	public static final int SOURCE_CHILD_INDEX = 0;
	public static final int MESSAGE_CHILDREN_START_INDEX = 1;

	public static GInterrupt parseGInterrupt(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct));
		/*List<MessageNode> msgs =
				getMessageChildren(ct).stream().map((msg) -> AntlrGMessageTransfer.parseMessage(parser, msg)).collect(Collectors.toList());*/
		List<MessageNode> msgs = new LinkedList<>();
		for (CommonTree msg : getMessageChildren(ct))
		{
			msgs.add(AntlrGMessageTransfer.parseMessage(parser, msg));
		}
		//return new GInterrupt(src, msgs);  // Destination roles set by later pass
		return null;
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static List<CommonTree> getMessageChildren(CommonTree ct)
	{
		return ScribParserUtil.toCommonTreeList(ct.getChildren().subList(MESSAGE_CHILDREN_START_INDEX, ct.getChildCount()));
	}
}
