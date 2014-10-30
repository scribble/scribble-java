package scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;

import scribble2.ast.ArgumentInstantiation;
import scribble2.ast.ArgumentNode;
import scribble2.parser.AntlrTreeParser;
import scribble2.util.Util;

public class AntlrArgumentInstantiation
{
	public static final int ARG_CHILD_INDEX = 0;

	public static ArgumentInstantiation parseArgumentInstantiation(AntlrModuleParser parser, CommonTree ct)
	{
		ArgumentNode arg = parseArgument(parser, getArgChild(ct));
		return new ArgumentInstantiation(ct, arg);
	}

	// Similar to AntlrGlobalMessageTransfer.parseMessage
	protected static ArgumentNode parseArgument(AntlrModuleParser parser, CommonTree ct)
	{
		switch (Util.getAntlrNodeType(ct))
		{
			case MESSAGESIGNATURE:
			{
				return AntlrMessageSignature.parseMessageSignature(parser, ct);
			}
			case QUALIFIEDNAME:
			{
				return AntlrQualifiedName.toPayloadTypeNameNodes(ct);
			}
			default: // As for PayloadElement: cannot syntactically distinguish between SimplePayloadTypeNode and ParameterNode
			{
				return AntlrSimpleName.toAmbiguousNameNode(ct);
			}
		}
	}

	public static CommonTree getArgChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARG_CHILD_INDEX);
	}
}
