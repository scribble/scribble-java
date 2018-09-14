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
package org.scribble.ext.go.parser.scribble.ast;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.AntlrToScribParserUtil;
import org.scribble.parser.scribble.ast.AntlrPayloadElemList;
import org.scribble.parser.scribble.ast.name.AntlrQualifiedName;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

// Duplicated from AntlrPayloadElemList
public class RPAntlrPayloadElemList
{
	// Cf. AntlrNonRoleArgList
	public static PayloadElemList parsePayloadElemList(AntlrToScribParser parser, CommonTree ct, AstFactory af)
	{
		List<PayloadElem<?>> pes = getPayloadElements(ct).stream().map(pe -> parsePayloadElem(pe, af)).collect(Collectors.toList());
		return af.PayloadElemList(ct, pes);
	}

	//private static PayloadElem parsePayloadElem(CommonTree ct)
	public static PayloadElem<?> parsePayloadElem(CommonTree ct, AstFactory af)
	{
		String type = ct.getToken().getText();  // FIXME from AntlrToScribParser#getAntlrNodeType
		
		System.out.println("BBB: " + type);
		
		switch (type)
		{
			case "PARAM_DELEGATION":
			{
				// Duplicated from AntlrPayloadElemList.parsePayloadElem
				RoleNode rn = AntlrSimpleName.toRoleNode((CommonTree) ct.getChild(0), af);  // FIXME: factor out constants
				GProtocolNameNode root = AntlrQualifiedName.toGProtocolNameNode((CommonTree) ct.getChild(1), af);
				CommonTree tmp = (CommonTree) ct.getChild(2);
				GProtocolNameNode state = (tmp != null) 
						? AntlrQualifiedName.toGProtocolNameNode((CommonTree) ct.getChild(1), af) 
						: root;
				return ((RPAstFactory) af).RPGDelegationElem(ct, root, state, rn);
			}

			default: return AntlrPayloadElemList.parsePayloadElem(ct, af);
		}
	}

	public static final List<CommonTree> getPayloadElements(CommonTree ct)
	{
		return (ct.getChildCount() == 0)
				? Collections.emptyList()
				: AntlrToScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
