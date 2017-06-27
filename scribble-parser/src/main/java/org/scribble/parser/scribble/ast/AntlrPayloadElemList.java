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
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ScribParserUtil;
import org.scribble.parser.scribble.AntlrConstants.AntlrNodeType;
import org.scribble.parser.scribble.ast.name.AntlrAmbigName;
import org.scribble.parser.scribble.ast.name.AntlrQualifiedName;
import org.scribble.parser.scribble.ast.name.AntlrSimpleName;

public class AntlrPayloadElemList
{
	// Cf. AntlrNonRoleArgList
	public static PayloadElemList parsePayloadElemList(ScribParser parser, CommonTree ct, AstFactory af)
	{
		// As in AntlrNonRoleArgList, i.e. payloadelem (NonRoleArg) not directly parsed -- cf. rolearg and nonroleparamdecl, which are directly parsed (not consistent), due to amibgious names
		//List<PayloadElem> pes = getPayloadElements(ct).stream().map((pe) -> parsePayloadElem(pe)).collect(Collectors.toList());
		List<PayloadElem<?>> pes = getPayloadElements(ct).stream().map(pe -> parsePayloadElem(pe, af)).collect(Collectors.toList());
		return af.PayloadElemList(ct, pes);
	}

	//private static PayloadElem parsePayloadElem(CommonTree ct)
	public static PayloadElem<?> parsePayloadElem(CommonTree ct, AstFactory af)
	{
		AntlrNodeType type = ScribParserUtil.getAntlrNodeType(ct);
		/*// Parser isn't working to distinguish simple from qualified names (cf. Scribble.g payloadelement)
		if (type == AntlrNodeType.QUALIFIEDNAME)
		{
			DataTypeNameNode dt = AntlrQualifiedName.toDataTypeNameNode(ct);
			return AstFactoryImpl.FACTORY.PayloadElement(dt);
		}
		else if (type == AntlrNodeType.AMBIGUOUSNAME)
		{
			// Similarly to NonRoleArg: cannot syntactically distinguish right now between SimplePayloadTypeNode and ParameterNode
			AmbigNameNode an = AntlrAmbigName.toAmbigNameNode(ct);
			return AstFactoryImpl.FACTORY.PayloadElement(an);
		}*/
		if (type == AntlrNodeType.DELEGATION)
		{
			RoleNode rn = AntlrSimpleName.toRoleNode((CommonTree) ct.getChild(0), af);
			//GProtocolNameNode gpnn = AntlrQualifiedName.toGProtocolNameNode((CommonTree) ct.getChild(1));  // FIXME:
			GProtocolNameNode gpnn = AntlrQualifiedName.toGProtocolNameNode((CommonTree) ct.getChild(1), af);
			return af.GDelegationElem(ct, gpnn, rn);
			//throw new RuntimeException("Shouldn't get in here: " + ct);
		}
		else if (type == AntlrNodeType.QUALIFIEDNAME)
		{
			if (ct.getChildCount() > 1)
			{
				DataTypeNode dt = AntlrQualifiedName.toDataTypeNameNode(ct, af);
				//return AstFactoryImpl.FACTORY.PayloadElem(dt);
				return af.UnaryPayloadElem(ct, dt);  // return and dt share same ct in this case
			}
			else
			{
				// Similarly to NonRoleArg: cannot syntactically distinguish right now between SimplePayloadTypeNode and ParameterNode
				AmbigNameNode an = AntlrAmbigName.toAmbigNameNode(ct, af);
				return af.UnaryPayloadElem(ct, an);
			}
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + ct);
		}
	}

	public static final List<CommonTree> getPayloadElements(CommonTree ct)
	{
		return (ct.getChildCount() == 0)
				? Collections.emptyList()
				: ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
