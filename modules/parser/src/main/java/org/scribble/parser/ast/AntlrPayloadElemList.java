package org.scribble.parser.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.PayloadElem;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.name.qualified.DataTypeNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.AntlrConstants.AntlrNodeType;
import org.scribble.parser.ast.name.AntlrAmbigName;
import org.scribble.parser.ast.name.AntlrQualifiedName;
import org.scribble.parser.util.Util;

// Cf. AntlrArgList
public class AntlrPayloadElemList
{
	public static PayloadElemList parsePayloadElemList(ScribbleParser parser, CommonTree ct)
	{
		List<PayloadElem> pes = new LinkedList<>();
		// As in AntlrNonRoleArgList, i.e. payloadelem (NonRoleArg) not directly parsed -- cf. rolarg and nonroleparamdecl, which are directly parsed (not consistent)
		for (CommonTree pe : getPayloadElements(ct))
		{
			//pes.add((PayloadElement) parser.parse(pe));
			AntlrNodeType type = Util.getAntlrNodeType(pe);
			if (type == AntlrNodeType.QUALIFIEDNAME)
			{
				DataTypeNameNode dt = AntlrQualifiedName.toDataTypeNameNode(pe);
				pes.add(AstFactoryImpl.FACTORY.PayloadElement(dt));
			}
			else if (type == AntlrNodeType.AMBIGUOUSNAME)
			{
				// As for PayloadElement: cannot syntactically distinguish between SimplePayloadTypeNode and ParameterNode
				AmbigNameNode an = AntlrAmbigName.toAmbigNameNode(pe);
				pes.add(AstFactoryImpl.FACTORY.PayloadElement(an));
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + pe);
			}
		}
		//return new Payload(pes);
		return AstFactoryImpl.FACTORY.PayloadElemList(pes);
	}

	public static final List<CommonTree> getPayloadElements(CommonTree ct)
	{
		if (ct.getChildCount() == 0)
		{
			return Collections.emptyList();
		}
		return Util.toCommonTreeList(ct.getChildren());
	}
}
