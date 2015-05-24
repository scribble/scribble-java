package org.scribble2.parser.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.PayloadElem;
import org.scribble2.model.name.PayloadElemNameNode;
import org.scribble2.parser.ScribbleParser;
import org.scribble2.parser.ast.name.AntlrAmbigName;
import org.scribble2.parser.ast.name.AntlrQualifiedName;
import org.scribble2.parser.util.Util;

@Deprecated
public class AntlrPayloadElem
{
	public static final int TYPE_CHILD_INDEX = 0;

	public static PayloadElem parsePayloadElement(ScribbleParser parser, CommonTree ct)
	{
		CommonTree child = getTypeChild(ct);
		PayloadElemNameNode ptpn;
		switch (Util.getAntlrNodeType(child))
		{
			/*case QUALIFIEDNAME:
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
			}*/
			case AMBIGUOUSNAME:
			{
				//if (child.getChildCount() == 0)
				if (child.getChildCount() == 1)
				{
					//ptpn = AntlrSimpleName.toAmbiguousNameNode(child);
					ptpn = AntlrAmbigName.toAmbigNameNode(child);
				}
				else
				{
					ptpn = AntlrQualifiedName.toDataTypeNameNode(child);
				}
				break;
			}
			default:  // FIXME: never gets in here (above case in parser grammar subsumes this case)
			{
				//ptpn = AntlrSimpleName.toAmbiguousNameNode(child);
				throw new RuntimeException("Shouldn't get in here: " + ct);
			}
		}
		//return new PayloadElement(ptpn);
		return ModelFactoryImpl.FACTORY.PayloadElement(ptpn);
	}

	public static CommonTree getTypeChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(TYPE_CHILD_INDEX);
	}
}
