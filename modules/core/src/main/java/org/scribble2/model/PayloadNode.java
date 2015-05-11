package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble2.sesstype.Payload;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.PayloadType;

public class PayloadNode extends ModelNodeBase
{
	//public static final Payload EMPTY_PAYLOAD = new Payload(null, Collections.<PayloadElement> emptyList());

	public final List<PayloadElement> elems;  // FIXME: parameterise on Kind (cf. sesstypes)

	public PayloadNode(List<PayloadElement> payloadelems)
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
	}
	
	@Override
	public Payload visitChildren(NodeVisitor nv) throws ScribbleException
	{
		if (isEmpty())
		{
			return this;
		}
		List<PayloadElement> pes = visitChildListWithClassCheck(this, this.payloadelems, nv);
		return new Payload(this.ct, pes);
	}*/

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
			for (PayloadElement pe : this.elems.subList(1, this.elems.size()))
			{
				s += ", " + pe;
			}
		}
		return s + ")";
	}

	@Override
	protected PayloadNode copy()
	{
		return new PayloadNode(this.elems);
	}
}
