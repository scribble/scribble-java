/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.parser.scribble.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ScribParserUtil;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class AntlrGChoice
{
	public static final int SUBJECT_CHILD_INDEX = 0;
	public static final int BLOCK_CHILDREN_START_INDEX = 1;
	
	public static GChoice parseGChoice(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		RoleNode subj = AntlrSimpleName.toRoleNode(getSubjectChild(ct), af);
		/*List<GProtocolBlock> blocks =
				getBlockChildren(ct).stream().map((b) -> (GProtocolBlock) parser.parse(b)).collect(Collectors.toList());*/
		List<GProtocolBlock> blocks = new LinkedList<>();
		for (CommonTree b : getBlockChildren(ct))
		{
			blocks.add((GProtocolBlock) parser.parse(b, af));
		}
		return af.GChoice(ct, subj, blocks);
	}

	public static CommonTree getSubjectChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SUBJECT_CHILD_INDEX);
	}

	public static List<CommonTree> getBlockChildren(CommonTree ct)
	{
		List<?> children = ct.getChildren();
		return ScribParserUtil.toCommonTreeList(children.subList(BLOCK_CHILDREN_START_INDEX, children.size()));
	}
}
