package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.ParamDecl;
import org.scribble2.model.ParamDecl.Kind;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.parser.AntlrConstants;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;

public class AntlrParameterDecl
{
	public static final int KIND_CHILD_INDEX = 0;
	public static final int NAME_CHILD_INDEX = 1;

	public static ParamDecl parseParameterDecl(ScribbleParser parser, CommonTree ct)
	{
		Kind kind = parseKind(getKindChild(ct));
		//ParameterNode name = AntlrSimpleName.toParameterNode(getNameChild(ct), kind);
		ParameterNode name = AntlrSimpleName.toParameterNode(getNameChild(ct));
		//return new ParameterDecl(kind, name);
		return ModelFactoryImpl.FACTORY.ParameterDecl(kind, name);
	}

	private static Kind parseKind(CommonTree ct)
	{
		String kind = ct.getText();
		switch (kind)
		{
			case AntlrConstants.KIND_MESSAGESIGNATURE:
			{
				return Kind.SIG;
			}
			case AntlrConstants.KIND_PAYLOADTYPE:
			{
				return Kind.TYPE;
			}
			default:
			{
				throw new RuntimeException("Unknown parameter declaration kind: " + kind);
			}
		}
	}

	public static CommonTree getKindChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(KIND_CHILD_INDEX);
	}

	public static CommonTree getNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(NAME_CHILD_INDEX);
	}
}
