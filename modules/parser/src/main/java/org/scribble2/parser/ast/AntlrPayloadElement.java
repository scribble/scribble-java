package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.PayloadElement;
import org.scribble2.model.name.AmbiguousNameNode;
import org.scribble2.model.name.PayloadElementNameNode;
import org.scribble2.model.name.qualified.PayloadTypeNameNode;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.util.Util;

public class AntlrPayloadElement
{
	public static final int TYPE_CHILD_INDEX = 0;

	public static PayloadElement parsePayloadElement(AntlrModuleParser parser, CommonTree ct)
	{
		CommonTree child = getTypeChild(ct);
		PayloadElementNameNode ptpn;
		switch (Util.getAntlrNodeType(child))
		{
			case QUALIFIEDNAME:
			{
				PayloadTypeNameNode name = AntlrQualifiedName.toPayloadTypeNameNodes(child);
				if (name.getElementCount() == 1)  // HACK: parser returns the simple name case as a qualified name
				{
					//String tmp = AntlrQualifiedName.getElements(child)[0];
					String tmp = name.getElements()[0];
					//ptpn = new AmbiguousNameNode(child, tmp);
					ptpn = new AmbiguousNameNode(tmp);
				}
				else
				{
					ptpn = name;
				}
				break;
			}
			default:  // FIXME: never gets in here (above case in parser grammar subsumes this case)
			{
				ptpn = AntlrSimpleName.toAmbiguousNameNode(child);
			}
		}
		return new PayloadElement(ptpn);
	}

	public static CommonTree getTypeChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(TYPE_CHILD_INDEX);
	}
}
