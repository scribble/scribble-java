package org.scribble2.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.MessageNode;
import org.scribble2.model.global.GlobalInterrupt;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.ast.AntlrSimpleName;
import org.scribble2.parser.util.Util;


public class AntlrGlobalInterrupt
{
	public static final int SOURCE_CHILD_INDEX = 0;
	public static final int MESSAGE_CHILDREN_START_INDEX = 1;

	public static GlobalInterrupt parseGlobalInterrupt(AntlrModuleParser parser, CommonTree ct)
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct));
		List<MessageNode> msgs = new LinkedList<>();
		for (CommonTree msg : getMessageChildren(ct))
		{
			msgs.add(AntlrGlobalMessageTransfer.parseMessage(parser, msg));
		}
		//return new GlobalInterrupt(ct, src, msgs, Collections.<Role>emptyList());  // Destination roles set by later pass
		return new GlobalInterrupt(src, msgs);
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static List<CommonTree> getMessageChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren().subList(MESSAGE_CHILDREN_START_INDEX, ct.getChildCount()));
	}
}
