package org.scribble.parser.ast;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.parser.AntlrConstants.AntlrNodeType;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrAmbigName;
import org.scribble.parser.ast.name.AntlrQualifiedName;
import org.scribble.parser.util.ScribParserUtil;

public class AntlrPayloadElemList
{
	// Cf. AntlrNonRoleArgList
	public static PayloadElemList parsePayloadElemList(ScribParser parser, CommonTree ct)
	{
		// As in AntlrNonRoleArgList, i.e. payloadelem (NonRoleArg) not directly parsed -- cf. rolearg and nonroleparamdecl, which are directly parsed (not consistent), due to amibgious names
		List<PayloadElem> pes = getPayloadElements(ct).stream().map((pe) -> parsePayloadElem(pe)).collect(Collectors.toList());
		return AstFactoryImpl.FACTORY.PayloadElemList(pes);
	}

	private static PayloadElem parsePayloadElem(CommonTree ct)
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
		if (type == AntlrNodeType.QUALIFIEDNAME)
		{
			if (ct.getChildCount() > 1)
			{
				DataTypeNode dt = AntlrQualifiedName.toDataTypeNameNode(ct);
				return AstFactoryImpl.FACTORY.PayloadElem(dt);
			}
			else
			{
				// Similarly to NonRoleArg: cannot syntactically distinguish right now between SimplePayloadTypeNode and ParameterNode
				AmbigNameNode an = AntlrAmbigName.toAmbigNameNode(ct);
				return AstFactoryImpl.FACTORY.PayloadElem(an);
			}
		}
		else if (type == AntlrNodeType.PAYLOADHACK)  // FIXME HACK
		{
			AmbigNameNode an = AntlrAmbigName.toAmbigNameNode(ct);
			return AstFactoryImpl.FACTORY.PayloadElem(an);
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
