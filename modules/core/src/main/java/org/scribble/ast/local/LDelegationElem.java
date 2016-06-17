package org.scribble.ast.local;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.UnaryPayloadElem;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.visit.AstVisitor;

// N.B. extends UnaryPayloadElem, not DelegationElem
public class LDelegationElem extends UnaryPayloadElem<Local>
{
  // Currently no potential for ambiguity because only generated, not parsed
	public LDelegationElem(LProtocolNameNode proto)
	{
		super(proto);
	}

	@Override
	public boolean isLocalDelegationElem()
	{
		return true;
	}

	@Override
	protected LDelegationElem copy()
	{
		return new LDelegationElem((LProtocolNameNode) this.name);
	}
	
	@Override
	public LDelegationElem clone()
	{
		LProtocolNameNode name = (LProtocolNameNode) this.name.clone();
		return AstFactoryImpl.FACTORY.LDelegationElem(name);
	}

	public LDelegationElem reconstruct(LProtocolNameNode proto)
	{
		ScribDel del = del();
		LDelegationElem elem = new LDelegationElem(proto);
		elem = (LDelegationElem) elem.del(del);
		return elem;
	}

	@Override
	public LDelegationElem visitChildren(AstVisitor nv) throws ScribbleException
	{
		LProtocolNameNode name = (LProtocolNameNode) visitChild(this.name, nv);
		return reconstruct(name);
	}
	
	/*@Override
	public String toString()
	{
		return this.proto + "@" + this.role;
	}*/

	@Override
	//public PayloadType<? extends PayloadTypeKind> toPayloadType()
	public PayloadType<Local> toPayloadType()
	{
		return this.name.toPayloadType();
	}
}
