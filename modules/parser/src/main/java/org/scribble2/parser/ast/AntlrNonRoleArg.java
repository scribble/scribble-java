package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ArgNode;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.NonRoleArg;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrQualifiedName;
import org.scribble2.parser.util.Util;

@Deprecated
public class AntlrNonRoleArg
{
	public static final int ARG_CHILD_INDEX = 0;

	public static NonRoleArg parseNonRoleArg(ScribbleParser parser, CommonTree ct)
	{
		ArgNode arg = parseArgument(parser, getArgChild(ct));
		//return new ArgumentInstantiation(ct, arg);
		return ModelFactoryImpl.FACTORY.NonRoleArg(arg);
	}

	// Similar to AntlrGlobalMessageTransfer.parseMessage
	protected static ArgNode parseArgument(ScribbleParser parser, CommonTree ct)
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
