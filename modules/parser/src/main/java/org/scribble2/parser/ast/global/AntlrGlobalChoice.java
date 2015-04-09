package org.scribble2.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GlobalChoice;
import org.scribble2.model.global.GlobalProtocolBlock;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;
import org.scribble2.parser.util.Util;

public class AntlrGlobalChoice
{
	public static final int SUBJECT_CHILD_INDEX = 0;
	public static final int BLOCK_CHILDREN_START_INDEX = 1;
	
	public static GlobalChoice parseGlobalChoice(AntlrModuleParser parser, CommonTree ct)
	{
		RoleNode subj = AntlrSimpleName.toRoleNode(getSubjectChild(ct));
		List<GlobalProtocolBlock> blocks = new LinkedList<>();
		for (CommonTree block : getBlockChildren(ct))
		{
			blocks.add((GlobalProtocolBlock) parser.parse(block));
		}
		//return new GlobalChoice(subj, blocks);
		return ModelFactoryImpl.FACTORY.GlobalChoice(subj, blocks);
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