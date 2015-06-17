package org.scribble.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ModelFactoryImpl;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.ast.name.AntlrSimpleName;
import org.scribble.parser.util.Util;
import org.scribble.sesstype.kind.Global;

public class AntlrGChoice
{
	public static final int SUBJECT_CHILD_INDEX = 0;
	public static final int BLOCK_CHILDREN_START_INDEX = 1;
	
	public static GChoice parseGChoice(ScribbleParser parser, CommonTree ct)
	{
		RoleNode subj = AntlrSimpleName.toRoleNode(getSubjectChild(ct));
		List<ProtocolBlock<Global>> blocks = new LinkedList<>();
		for (CommonTree block : getBlockChildren(ct))
		{
			blocks.add((GProtocolBlock) parser.parse(block));
		}
		//return new GlobalChoice(subj, blocks);
		return ModelFactoryImpl.FACTORY.GChoice(subj, blocks);
	}

	public static CommonTree getSubjectChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SUBJECT_CHILD_INDEX);
	}

	public static List<CommonTree> getBlockChildren(CommonTree ct)
	{
		List<? extends Object> children = ct.getChildren();
		return Util.toCommonTreeList(children.subList(BLOCK_CHILDREN_START_INDEX, children.size()));
	}
}
