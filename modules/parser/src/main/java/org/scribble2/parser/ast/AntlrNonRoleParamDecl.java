package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.NonRoleParamDecl;
import org.scribble2.model.name.simple.NonRoleParamNode;
import org.scribble2.parser.AntlrConstants;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrSimpleName;
import org.scribble2.sesstype.kind.DataTypeKind;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.SigKind;

public class AntlrNonRoleParamDecl
{
	public static final int KIND_CHILD_INDEX = 0;
	public static final int NAME_CHILD_INDEX = 1;

	public static NonRoleParamDecl<? extends Kind> parseNonRoleParamDecl(ScribbleParser parser, CommonTree ct)
	{
		Kind kind = parseKind(getKindChild(ct));
		/*//ParameterNode name = AntlrSimpleName.toParameterNode(getNameChild(ct), kind);
		ParameterNode<? extends Kind> name = AntlrSimpleName.toParameterNode(kind, getNameChild(ct));
		//return new ParameterDecl(kind, name);
		return ModelFactoryImpl.FACTORY.ParameterDecl(kind, name);
		//return ModelFactoryImpl.FACTORY.ParameterDecl(name);*/
		if (kind.equals(SigKind.KIND))
		{
			NonRoleParamNode<SigKind> name = AntlrSimpleName.toParamNode(SigKind.KIND, getNameChild(ct));
			return ModelFactoryImpl.FACTORY.ParamDecl(SigKind.KIND, name);
		}
		else if (kind.equals(DataTypeKind.KIND))
		{
			NonRoleParamNode<DataTypeKind> name = AntlrSimpleName.toParamNode(DataTypeKind.KIND, getNameChild(ct));
			return ModelFactoryImpl.FACTORY.ParamDecl(DataTypeKind.KIND, name);
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
				//return Kind.SIG;
				return SigKind.KIND;
			}
			case AntlrConstants.KIND_PAYLOADTYPE:
			{
				//return Kind.TYPE;
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