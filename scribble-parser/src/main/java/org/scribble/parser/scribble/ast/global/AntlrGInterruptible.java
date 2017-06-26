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

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.global.GInterruptible;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ScribParserUtil;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

public class AntlrGInterruptible
{
	public static final int SCOPE_CHILD_INDEX = 0;
	public static final int BLOCK_CHILD_INDEX = 1;
	public static final int INTERRUPT_CHILDREN_START_INDEX = 2;

	public static GInterruptible parseGInterruptible(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		/*GProtocolBlock block = (GProtocolBlock) parser.parse(getBlockChild(ct));
		/*List<GInterrupt> interrs = 
			getInterruptChildren(ct).stream().map((interr) -> (GInterrupt) parser.parse(interr)).collect(Collectors.toList());
		* /
		List<GInterrupt> interrs = new LinkedList<>();
		for (CommonTree interr : getInterruptChildren(ct))
		{
			interrs.add((GInterrupt) parser.parse(interr));
		}
		if (isScopeImplicit(ct))
		{
			//return new GInterruptible(block, interrs);
			return null;
		}
		ScopeNode scope = AntlrSimpleName.toScopeNode(getScopeChild(ct));
		//return new GInterruptible(scope, block, interrs);*/
		return null;
	}
	
	public static boolean isScopeImplicit(CommonTree ct)
	{
		return AntlrSimpleName.toScopeNode(ct) == null;
	}

	public static CommonTree getScopeChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SCOPE_CHILD_INDEX);
	}

	public static CommonTree getBlockChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(BLOCK_CHILD_INDEX);
	}

	public static List<CommonTree> getInterruptChildren(CommonTree ct)
	{
		List<?> children = ct.getChildren();
		return ScribParserUtil.toCommonTreeList(children.subList(INTERRUPT_CHILDREN_START_INDEX, children.size()));
	}
}
