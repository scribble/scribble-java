package org.scribble.ast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;

// Cf. DoArgList, but here we don't need as much abstraction (cf. RoleArgList, NonRoleArgList)
public class PayloadElemList extends ScribNodeBase
{
	//private final List<PayloadElem> elems;  // FIXME: parameterise on Kind (cf. sesstypes)
	private final List<PayloadElem<?>> elems;

	//public PayloadElemList(List<PayloadElem> elems)
	public PayloadElemList(List<PayloadElem<?>> elems)
	{
		this.elems = new LinkedList<>(elems);
	}
	
	protected PayloadElemList project()
	{
		return AstFactoryImpl.FACTORY.PayloadElemList(this.elems.stream().map((pe) -> pe.project()).collect(Collectors.toList()));
	}
	
	@Override
	protected PayloadElemList copy()
	{
		return new PayloadElemList(this.elems);
	}
	
	@Override
	public PayloadElemList clone()
	{
		//List<PayloadElem> elems = ScribUtil.cloneList(this.elems);
		List<PayloadElem<?>> elems = ScribUtil.cloneList(this.elems);
		return AstFactoryImpl.FACTORY.PayloadElemList(elems);
	}

	//protected PayloadElemList reconstruct(List<PayloadElem> elems)
	protected PayloadElemList reconstruct(List<PayloadElem<?>> elems)
	{
		ScribDel del = del();
		PayloadElemList pel = new PayloadElemList(elems);
		pel = (PayloadElemList) pel.del(del);
		return pel;
	}
	
	@Override
	public PayloadElemList visitChildren(AstVisitor nv) throws ScribbleException
	{
		//List<PayloadElem> elems = visitChildListWithClassEqualityCheck(this, this.elems, nv);
		List<PayloadElem<?>> elems = visitChildListWithClassEqualityCheck(this, this.elems, nv);
		return reconstruct(elems);
	}
	
	//public List<PayloadElem> getElements()
	public List<PayloadElem<?>> getElements()
	{
		return Collections.unmodifiableList(this.elems);
	}

	public Payload toPayload()
	{
		//List<PayloadType<?>> pts = this.elems.stream().map((pe) -> pe.name.toPayloadType()).collect(Collectors.toList());
		List<PayloadType<?>> pts = this.elems.stream().map((pe) -> pe.toPayloadType()).collect(Collectors.toList());
		return new Payload(pts);
	}

	public boolean isEmpty()
	{
		return this.elems.isEmpty();
	}

	@Override
	public String toString()
	{
		return "(" + this.elems.stream().map((pe) -> pe.toString()).collect(Collectors.joining(", " )) + ")";
	}
}
