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
package org.scribble.parser.scribble.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.AntlrToScribParserUtil;
import org.scribble.parser.scribble.ScribbleAntlrConstants.AntlrNodeType;
import org.scribble.parser.scribble.ast.name.AntlrAmbigName;
import org.scribble.parser.scribble.ast.name.AntlrQualifiedName;
import org.scribble.util.ScribParserException;

public class AntlrNonRoleArgList
{
	// Similar to AntlrPayloadElemList
	public static NonRoleArgList parseNonRoleArgList(AntlrToScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		//List<NonRoleArg> as = getArgumentChildren(ct).stream().map((a) -> parseNonRoleArg(parser, a)).collect(Collectors.toList());
		List<NonRoleArg> as = new LinkedList<>();
		for (CommonTree a : getArgumentChildren(ct))
		{
			as.add(parseNonRoleArg(parser, a, af));
		}
		return af.NonRoleArgList(ct, as);
	}

	// Not in own class because not called by ScribbleParser -- called directly from above
	private static NonRoleArg parseNonRoleArg(AntlrToScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		AntlrNodeType type = AntlrToScribParserUtil.getAntlrNodeType(ct);
		if (type == AntlrNodeType.MESSAGESIGNATURE)
		{
			NonRoleArgNode arg = (NonRoleArgNode) parser.parse(ct, af);
			return af.NonRoleArg(ct, arg);
		}
		else
		{
			/* // Parser isn't working to distinguish simple from qualified names (similarly to PayloadElemList)
			if (type == AntlrNodeType.AMBIGUOUSNAME)
			{
				return AstFactoryImpl.FACTORY.NonRoleArg(AntlrAmbigName.toAmbigNameNode(ct));
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + type);
			}*/
			if (type == AntlrNodeType.QUALIFIEDNAME)
			{
				if (ct.getChildCount() > 1)
				{
					DataTypeNode dt = AntlrQualifiedName.toDataTypeNameNode(ct, af);
					return af.NonRoleArg(ct, dt);
				}
				else
				{
					// Similarly to NonRoleArg: cannot syntactically distinguish right now between SimplePayloadTypeNode and ParameterNode
					AmbigNameNode an = AntlrAmbigName.toAmbigNameNode(ct, af);
					return af.NonRoleArg(ct, an);
				}
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + ct);
			}
		}
	}

	public static List<CommonTree> getArgumentChildren(CommonTree ct)
	{
		return (ct.getChildCount() == 0)
				? Collections.emptyList()
				: AntlrToScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
