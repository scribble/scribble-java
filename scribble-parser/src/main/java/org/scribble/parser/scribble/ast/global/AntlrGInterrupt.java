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
import org.scribble.ast.global.GInterrupt;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ScribParserUtil;
import org.scribble.util.ScribParserException;


public class AntlrGInterrupt
{
	public static final int SOURCE_CHILD_INDEX = 0;
	public static final int MESSAGE_CHILDREN_START_INDEX = 1;

	public static GInterrupt parseGInterrupt(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		/*RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct));
		/*List<MessageNode> msgs =
				getMessageChildren(ct).stream().map((msg) -> AntlrGMessageTransfer.parseMessage(parser, msg)).collect(Collectors.toList());
		* /
		List<MessageNode> msgs = new LinkedList<>();
		for (CommonTree msg : getMessageChildren(ct))
		{
			msgs.add(AntlrGMessageTransfer.parseMessage(parser, msg));
		}
		//return new GInterrupt(src, msgs);  // Destination roles set by later pass*/
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
