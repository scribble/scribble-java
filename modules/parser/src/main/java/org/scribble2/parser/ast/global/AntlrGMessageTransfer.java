package org.scribble2.parser.ast.global;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.MessageNode;
import org.scribble2.model.MessageSigNode;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.global.GMessageTransfer;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.parser.AntlrConstants;
import org.scribble2.parser.AntlrConstants.AntlrNodeType;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrAmbigName;
import org.scribble2.parser.ast.name.AntlrQualifiedName;
import org.scribble2.parser.ast.name.AntlrSimpleName;
import org.scribble2.parser.util.Util;


public class AntlrGMessageTransfer
{
	public static final int MESSAGE_CHILD_INDEX = 0;
	public static final int SOURCE_CHILD_INDEX = 1;
	public static final int DESTINATION_CHILDREN_START_INDEX = 2;

	public static GMessageTransfer parseGMessageTransfer(ScribbleParser parser, CommonTree ct)
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct));
		MessageNode msg = parseMessage(parser, getMessageChild(ct));
		List<RoleNode> dests = new LinkedList<>();
		for (CommonTree dest : getDestChildren(ct))
		{
			dests.add(AntlrSimpleName.toRoleNode(dest));
		}
		//return new GlobalMessageTransfer(src, msg, dests);
		return ModelFactoryImpl.FACTORY.GMessageTransfer(src, msg, dests);
	}

	protected static MessageNode parseMessage(ScribbleParser parser, CommonTree ct)
	{
		/*switch (Util.getAntlrNodeType(ct))
		{
			case MESSAGESIGNATURE:
			{
				return (MessageSignatureNode) parser.parse(ct);
			}
			default:
			{
				// Must be a parameter
				return AntlrSimpleName.toParameterNode(ct, Kind.SIG);
			}
		}*/
		/*String type = ct.getToken().getText();  // Hacky? cf. Util.getAntlrNodeType (parameter nodes have no "node type")
		if (type.equals(AntlrConstants.MESSAGESIGNATURE_NODE_TYPE))*/
		AntlrNodeType type = Util.getAntlrNodeType(ct);
		if (type == AntlrNodeType.MESSAGESIGNATURE)
		{
			return (MessageSigNode) parser.parse(ct);
		}
		// Duplicated from AntlrPayloadElement parse
		/*else if (type.equals(AntlrConstants.QUALIFIEDNAME_NODE_TYPE))  // member name
		{
			MessageSignatureNameNode name = AntlrQualifiedName.toMessageSignatureNameNodes(ct);
			if (name.getElementCount() == 1)  // HACK: parser returns the simple name case as a qualified name
			{
				//String tmp = AntlrQualifiedName.getElements(child)[0];
				String tmp = name.getElements()[0];
				return new AmbiguousNameNode(tmp);
			}
			else
			{
				return name;
			}
		}*/
		else //if (type.equals(AntlrConstants.AMBIGUOUSNAME_NODE_TYPE))
		//if (Util.getAntlrNodeType(ct) == AntlrNodeType.AMBIGUOUSNAME)
		{
			// Duplicated from AntlrPayloadElement parse
			if (ct.getChildCount() == 1)
			{
				//return AntlrSimpleName.toAmbiguousNameNode(ct);  // parametername or simple messagesignaturename
				return AntlrAmbigName.toAmbigNameNode(ct);
			}
			else
			{
				return AntlrQualifiedName.toMessageSigNameNode(ct);
			}
		}
		/*//return AntlrSimpleName.toParameterNode(ct, Kind.SIG);
		//return AntlrSimpleName.toSimpleMessageSignatureNameNode(ct);
		return new AmbiguousNameNode(type);*/
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static CommonTree getMessageChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MESSAGE_CHILD_INDEX);
	}

	public static List<CommonTree> getDestChildren(CommonTree ct)
	{
		return Util.toCommonTreeList(ct.getChildren().subList(DESTINATION_CHILDREN_START_INDEX, ct.getChildCount()));
	}
}
