package org.scribble.parser.ast;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.parser.AntlrConstants.AntlrNodeType;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrAmbigName;
import org.scribble.parser.ast.name.AntlrQualifiedName;
import org.scribble.parser.util.ScribParserUtil;

public class AntlrNonRoleArgList
{
	// Similar to AntlrPayloadElemList
	public static NonRoleArgList parseNonRoleArgList(ScribParser parser, CommonTree ct)
	{
		List<NonRoleArg> as = getArgumentChildren(ct).stream().map((a) -> parseNonRoleArg(parser, a)).collect(Collectors.toList());
		return AstFactoryImpl.FACTORY.NonRoleArgList(as);
	}

	// Not in own class because not called by ScribbleParser -- called directly from above
	private static NonRoleArg parseNonRoleArg(ScribParser parser, CommonTree ct)
	{
		AntlrNodeType type = ScribParserUtil.getAntlrNodeType(ct);
		if (type == AntlrNodeType.MESSAGESIGNATURE)
		{
			NonRoleArgNode arg = (NonRoleArgNode) parser.parse(ct);
			return AstFactoryImpl.FACTORY.NonRoleArg(arg);
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
					DataTypeNode dt = AntlrQualifiedName.toDataTypeNameNode(ct);
					return AstFactoryImpl.FACTORY.NonRoleArg(dt);
				}
				else
				{
					// Similarly to NonRoleArg: cannot syntactically distinguish right now between SimplePayloadTypeNode and ParameterNode
					AmbigNameNode an = AntlrAmbigName.toAmbigNameNode(ct);
					return AstFactoryImpl.FACTORY.NonRoleArg(an);
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
				: ScribParserUtil.toCommonTreeList(ct.getChildren());
	}
}
