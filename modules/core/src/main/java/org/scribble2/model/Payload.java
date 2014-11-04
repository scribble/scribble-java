package org.scribble2.model;

import java.util.LinkedList;
import java.util.List;

public class Payload extends ModelNodeBase
{
	//public static final Payload EMPTY_PAYLOAD = new Payload(null, Collections.<PayloadElement> emptyList());

	public final List<PayloadElement> payloadelems;

	public Payload(List<PayloadElement> payloadelems)
	{
		this.payloadelems = new LinkedList<>(payloadelems);
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
		return this.payloadelems.isEmpty();
	}

	@Override
	public String toString()
	{
		String s = "(";
		if (!isEmpty())
		{
			s += this.payloadelems.get(0).toString();
			for (PayloadElement pe : this.payloadelems.subList(1, this.payloadelems.size()))
			{
				s += ", " + pe;
			}
		}
		return s + ")";
	}

	@Override
	protected Payload copy()
	{
		return new Payload(this.payloadelems);
	}
}
