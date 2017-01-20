package org.scribble.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ext.f17.ast.AnnotString;
import org.scribble.ext.f17.ast.ScribAnnot;
import org.scribble.parser.AntlrConstants.AntlrNodeType;
import org.scribble.parser.ScribParser;
import org.scribble.parser.ast.name.AntlrAmbigName;
import org.scribble.parser.ast.name.AntlrQualifiedName;
import org.scribble.parser.ast.name.AntlrSimpleName;
import org.scribble.parser.util.ScribParserUtil;
import org.scribble.util.ScribParserException;

// Factor with AntlrGMessageTransfer?
public class AntlrGConnect
{
	public static final int MESSAGE_CHILD_INDEX = 2;
	public static final int SOURCE_CHILD_INDEX = 0;
	public static final int DESTINATION_CHILD_INDEX = 1;

	public static final int ANNOT_INDEX = 3;

	public static GConnect parseGConnect(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		RoleNode src = AntlrSimpleName.toRoleNode(getSourceChild(ct));
		MessageNode msg = parseMessage(parser, getMessageChild(ct));
		RoleNode dest = AntlrSimpleName.toRoleNode(getDestinationChild(ct));
		//return AstFactoryImpl.FACTORY.GConnect(ct, src, msg, dest);
		////return AstFactoryImpl.FACTORY.GConnect(src, dest);

		// Cf. AntlrGMessageTransfer
		ScribAnnot annot = isEmptyAnnot(ct) ? null : parseAnnot(getAnnotChild(ct));
		/*if (annot != null)
		{
			MessageSigNode msn = (MessageSigNode) msg;  // FIXME: refactor properly
			msn.payloads.getElements().stream()
					.filter((p) -> p instanceof AnnotUnaryPayloadElem<?>)
					//.forEach((p) -> ((AnnotUnaryPayloadElemDel) p.del()).annot = annot);
					.forEach((p) -> ((AnnotUnaryPayloadElemDel) p.del()).setAnnot(annot));  // Doesn't work: inlining pass discards dels
		}*/
		return AstFactoryImpl.FACTORY.GConnect(ct, src, msg, dest, annot);
	}

	protected static MessageNode parseMessage(ScribParser parser, CommonTree ct) throws ScribParserException
	{
		AntlrNodeType type = ScribParserUtil.getAntlrNodeType(ct);
		if (type == AntlrNodeType.MESSAGESIGNATURE)
		{
			return (MessageSigNode) parser.parse(ct);
		}
		else //if (type.equals(AntlrConstants.AMBIGUOUSNAME_NODE_TYPE))
		{
			return (ct.getChildCount() == 1)
				? AntlrAmbigName.toAmbigNameNode(ct)  // parametername or simple messagesignaturename
				: AntlrQualifiedName.toMessageSigNameNode(ct);
		}
	}

	public static CommonTree getMessageChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MESSAGE_CHILD_INDEX);
	}

	public static CommonTree getSourceChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SOURCE_CHILD_INDEX);
	}

	public static CommonTree getDestinationChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(DESTINATION_CHILD_INDEX);
	}
	

	// Duplicated from AntlrGMessageTransfer
	public static CommonTree getAnnotChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ANNOT_INDEX);
	}
	
	protected static ScribAnnot parseAnnot(CommonTree ct)
	{
		String val = ct.getText();
		val = val.substring(val.indexOf('\"')+1, val.lastIndexOf('\"'));
		val = val.trim();
		return new AnnotString(val);
	}
	
	protected static boolean isEmptyAnnot(CommonTree ct)
	{
		//return ct.getText().equals("EMPTYANNOT");  // FIXME: refactor properly
		return getAnnotChild(ct).getText().equals("EMPTY_ANNOT");
	}
}
