package org.scribble.ext.f17.del;

import org.scribble.ast.ScribNode;
import org.scribble.del.ScribDelBase;
import org.scribble.ext.f17.ast.AnnotUnaryPayloadElem;
import org.scribble.ext.f17.ast.ScribAnnot;
import org.scribble.ext.f17.sesstype.name.PayloadVar;
import org.scribble.main.ScribbleException;
import org.scribble.visit.wf.NameDisambiguator;

public class AnnotUnaryPayloadElemDel extends ScribDelBase
{
	private ScribAnnot annot;  // FIXME: refactor properly

	public void setAnnot(ScribAnnot annot)
	{
		this.annot = annot;
	}
	
	public ScribAnnot getAnnot()
	{
		return annot;
	}
	
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{
		// N.B. payload params counted as protodecl params, so cannot shadow those
		AnnotUnaryPayloadElem<?> pe = (AnnotUnaryPayloadElem<?>) child;
		PayloadVar pv = pe.payvar.toName();
		//if (disamb.isBoundParameter(pv))
		if (disamb.isBoundPayloadVar(pv))
		{
			throw new ScribbleException("[f17] Duplicate payload var: " + pv);  // FIXME: probably doesn't check across protodecls
		}
		//disamb.addParameter(pv, DataTypeKind.KIND);  // Doing this way also raises name subsitution (SubprotocolVisitor)
		disamb.addPayloadVar(pv);
	}
}
