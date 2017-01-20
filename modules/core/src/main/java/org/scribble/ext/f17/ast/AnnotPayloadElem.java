package org.scribble.ext.f17.ast;

import org.scribble.ast.PayloadElem;
import org.scribble.sesstype.kind.PayloadTypeKind;

public interface AnnotPayloadElem<K extends PayloadTypeKind> extends PayloadElem<K>, AnnotNode
//public interface AnnotPayloadElem extends PayloadElem<DataTypeKind>, AnnotNode
{
	AnnotPayloadElem<K> project();  // Currently outside of visitor/env pattern (cf. MessageNode)
	//AnnotPayloadElem project();  // Currently outside of visitor/env pattern (cf. MessageNode)
}
