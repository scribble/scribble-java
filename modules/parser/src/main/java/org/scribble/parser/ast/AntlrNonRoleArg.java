package org.scribble.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.NonRoleArg;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.ast.name.AntlrQualifiedName;
import org.scribble.parser.util.Util;

@Deprecated
public class AntlrNonRoleArg
{
	public static final int ARG_CHILD_INDEX = 0;

	public static NonRoleArg parseNonRoleArg(ScribbleParser parser, CommonTree ct)
	{
		NonRoleArgNode arg = parseArgument(parser, getArgChild(ct));
		//return new ArgumentInstantiation(ct, arg);
		return AstFactoryImpl.FACTORY.NonRoleArg(arg);
	}

	// Similar to AntlrGlobalMessageTransfer.parseMessage
	protected static NonRoleArgNode parseArgument(ScribbleParser parser, CommonTree ct)
	{
		//try
		{
			switch (Util.getAntlrNodeType(ct))
			{
				case MESSAGESIGNATURE:
				{
					return AntlrMessageSig.parseMessageSig(parser, ct);
				}
				case QUALIFIEDNAME:
				{
					return AntlrQualifiedName.toDataTypeNameNode(ct);
				}
				default:
				{
					throw new RuntimeException("Shouldn't get in here: " + ct);
				}
			}
		}
		/*catch (Exception e)  // HACK
		{
			System.out.println("a: " + ct);
			
			// As for PayloadElement: cannot syntactically distinguish between SimplePayloadTypeNode and ParameterNode
			return AntlrAmbiguousName.toAmbiguousNameNode(ct);
		}*/
	}

	public static CommonTree getArgChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARG_CHILD_INDEX);
	}
}
