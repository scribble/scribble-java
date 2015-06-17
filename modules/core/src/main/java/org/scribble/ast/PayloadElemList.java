package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.visit.ModelVisitor;
import org.scribble.del.ModelDel;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.util.ScribbleException;

public class PayloadElemList extends ModelNodeBase
{
	//public static final Payload EMPTY_PAYLOAD = new Payload(null, Collections.<PayloadElement> emptyList());

	public final List<PayloadElem> elems;  // FIXME: parameterise on Kind (cf. sesstypes)

	public PayloadElemList(List<PayloadElem> payloadelems)
	{
		this.elems = new LinkedList<>(payloadelems);
	}
	
	public Payload toPayload()
	{
		List<PayloadType<? extends Kind>> pts = this.elems.stream().map((pe) -> pe.name.toPayloadType()).collect(Collectors.toList());
		return new Payload(pts);
	}

	/*// Basically a copy without the AST
	@Override
	public Payload leaveProjection(Projector proj) //throws ScribbleException
	{
		List<PayloadElement> payloadelems = 
				this.payloadelems.stream().map((pe) -> (PayloadElement) ((ProjectionEnv) pe.getEnv()).getProjection()).collect(Collectors.toList());	
		Payload projection = new Payload(null, payloadelems);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/

	protected PayloadElemList reconstruct(List<PayloadElem> elems)
	{
		ModelDel del = del();
		PayloadElemList pel = new PayloadElemList(elems);
		pel = (PayloadElemList) pel.del(del);
		return pel;
	}
	
	@Override
	public PayloadElemList visitChildren(ModelVisitor nv) throws ScribbleException
	{
		List<PayloadElem> elems = visitChildListWithClassCheck(this, this.elems, nv);
		return reconstruct(elems);
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

	@Override
	protected PayloadElemList copy()
	{
		return new PayloadElemList(this.elems);
	}
}
