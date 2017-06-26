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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ScribParserUtil;
import org.scribble.parser.scribble.AntlrConstants.AntlrNodeType;
import org.scribble.parser.scribble.ast.name.AntlrAmbigName;
import org.scribble.parser.scribble.ast.name.AntlrQualifiedName;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;
import org.scribble.util.ScribParserException;

// Factor with AntlrGMessageTransfer?
public class AntlrGConnect
{
	public static final int ASSERTION_CHILD_INDEX = 0;
	public static final int MESSAGE_CHILD_INDEX = 3;
	public static final int SOURCE_CHILD_INDEX = 1;
	public static final int DESTINATION_CHILD_INDEX = 2;

	public static GConnect parseGConnect(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct), af);
		MessageNode msg = parseMessage(parser, getMessageChild(ct), af);
		RoleNode dest = AntlrSimpleName.toRoleNode(getDestinationChild(ct), af);
		return af.GConnect(ct, src, msg, dest);
		//return AstFactoryImpl.FACTORY.GConnect(src, dest);
	}

	protected static MessageNode parseMessage(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		AntlrNodeType type = ScribParserUtil.getAntlrNodeType(ct);
		if (type == AntlrNodeType.MESSAGESIGNATURE)
		{
			return (MessageSigNode) parser.parse(ct, af);
		}
		else //if (type.equals(AntlrConstants.AMBIGUOUSNAME_NODE_TYPE))
		{
			return (ct.getChildCount() == 1)
				? AntlrAmbigName.toAmbigNameNode(ct, af)  // parametername or simple messagesignaturename
				: AntlrQualifiedName.toMessageSigNameNode(ct, af);
		}
	}

	public static CommonTree getMessageChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MESSAGE_CHILD_INDEX);
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static CommonTree getDestinationChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(DESTINATION_CHILD_INDEX);
	}
}
