package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.visit.AstVisitor;

public class PayloadElemList extends ScribNodeBase
{
	public final List<PayloadElem> elems;  // FIXME: parameterise on Kind (cf. sesstypes)

	public PayloadElemList(List<PayloadElem> payloadelems)
	{
		this.elems = new LinkedList<>(payloadelems);
	}
	
	@Override
	protected PayloadElemList copy()
	{
		return new PayloadElemList(this.elems);
	}

	protected PayloadElemList reconstruct(List<PayloadElem> elems)
	{
		ScribDel del = del();
		PayloadElemList pel = new PayloadElemList(elems);
		pel = (PayloadElemList) pel.del(del);
		return pel;
	}
	
	@Override
	public PayloadElemList visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<PayloadElem> elems = visitChildListWithClassCheck(this, this.elems, nv);
		return reconstruct(elems);
	}

	public Payload toPayload()
	{
		List<PayloadType<? extends Kind>> pts = this.elems.stream().map((pe) -> pe.name.toPayloadType()).collect(Collectors.toList());
		return new Payload(pts);
	}

	public boolean isEmpty()
	{
		return this.elems.isEmpty();
	}

	@Override
	public String toString()
	{
		String s = "(";
		if (!isEmpty())
		{
			s += this.elems.get(0).toString();
			for (PayloadElem pe : this.elems.subList(1, this.elems.size()))
			{
				s += ", " + pe;
			}
		}
		return s + ")";
	}
}