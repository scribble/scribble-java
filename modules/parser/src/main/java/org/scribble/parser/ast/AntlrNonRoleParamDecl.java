package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.parser.AntlrConstants;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrSimpleName;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.kind.SigKind;

public class AntlrNonRoleParamDecl
{
	public static final int KIND_CHILD_INDEX = 0;
	public static final int NAME_CHILD_INDEX = 1;

	public static NonRoleParamDecl<? extends NonRoleParamKind> parseNonRoleParamDecl(ScribParser parser, CommonTree ct)
	{
		Kind kind = parseKind(getKindChild(ct));
		if (kind.equals(SigKind.KIND))
		{
			NonRoleParamNode<SigKind> name = AntlrSimpleName.toParamNode(SigKind.KIND, getNameChild(ct));
			return AstFactoryImpl.FACTORY.NonRoleParamDecl(SigKind.KIND, name);
		}
		else if (kind.equals(DataTypeKind.KIND))
		{
			NonRoleParamNode<DataTypeKind> name = AntlrSimpleName.toParamNode(DataTypeKind.KIND, getNameChild(ct));
			return AstFactoryImpl.FACTORY.NonRoleParamDecl(DataTypeKind.KIND, name);
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + kind);
		}
	}

	private static Kind parseKind(CommonTree ct)
	{
		String kind = ct.getText();
		switch (kind)
		{
			case AntlrConstants.KIND_MESSAGESIGNATURE:
			{
				return SigKind.KIND;
			}
			case AntlrConstants.KIND_PAYLOADTYPE:
			{
				return DataTypeKind.KIND;
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
