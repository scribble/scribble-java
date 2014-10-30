package org.scribble2.parser.ast;

import java.nio.file.WatchEvent.Kind;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.name.AmbiguousNameNode;
import org.scribble2.model.name.simple.OperatorNode;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.model.name.simple.ScopeNode;
import org.scribble2.model.name.simple.SimpleMessageSignatureNameNode;
import org.scribble2.model.name.simple.SimplePayloadTypeNode;
import org.scribble2.model.name.simple.SimpleProtocolNameNode;
import org.scribble2.parser.ast.global.AntlrGlobalInterruptible;

public class AntlrSimpleName
{
	protected static String getName(CommonTree ct)
	{
		return ct.getText();
	}
	
	public static SimpleProtocolNameNode toSimpleProtocolNameNode(CommonTree ct)
	{
		return new SimpleProtocolNameNode(AntlrSimpleName.getName(ct));
	}

	public static SimplePayloadTypeNode toSimplePayloadTypeNode(CommonTree ct)
	{
		return new SimplePayloadTypeNode(AntlrSimpleName.getName(ct));
	}

	public static SimpleMessageSignatureNameNode toSimpleMessageSignatureNameNode(CommonTree ct)
	{
		return new SimpleMessageSignatureNameNode(AntlrSimpleName.getName(ct));
	}

	public static RoleNode toRoleNode(CommonTree ct)
	{
		return new RoleNode(getName(ct));
	}

	/*public static ParameterNode toParameterNode(CommonTree ct)
	{
		return toParameterNode(ct, Kind.AMBIGUOUS);
	}*/

	//public static ParameterNode toParameterNode(CommonTree ct, Kind kind)
	public static ParameterNode toParameterNode(CommonTree ct)
	{
		//return new ParameterNode(getName(ct), kind);
		return new ParameterNode(getName(ct));
	}
	
	public static AmbiguousNameNode toAmbiguousNameNode(CommonTree ct)
	{
		return new AmbiguousNameNode(getName(ct));
	}

	public static OperatorNode toOperatorNode(CommonTree ct)
	{
		String op = getName(ct);
		if (op.equals(AntlrMessageSignature.ANTLR_EMPTY_OPERATOR))
		{
			return new OperatorNode(OperatorNode.EMPTY_OPERATOR_IDENTIFIER);
		}
		return new OperatorNode(op);
	}
	
	public static ScopeNode toScopeNode(CommonTree ct)
	{
		String scope = getName(ct);
		if (scope.equals(AntlrGlobalInterruptible.NO_SCOPE))
		{
			return null;
		}
		return new ScopeNode(scope);
	}
	
	public static RecursionVarNode toRecursionVarNode(CommonTree ct)
	{
		return new RecursionVarNode(getName(ct));
	}
}
