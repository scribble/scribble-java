package org.scribble.ast;

import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.AstVisitor;

// Not in grammar file -- but cf. DoArg (and PayloadElemList cf. DoArgList)
public class PayloadElem extends ScribNodeBase
{
	public final PayloadElemNameNode name;

	public PayloadElem(PayloadElemNameNode name)
	{
		this.name = name;
	}

	@Override
	protected PayloadElem copy()
	{
		return new PayloadElem(this.name);
	}
	
	@Override
	public PayloadElem clone()
	{
		PayloadElemNameNode name = (PayloadElemNameNode) this.name.clone();
		return AstFactoryImpl.FACTORY.PayloadElem(name);
	}

	public PayloadElem reconstruct(PayloadElemNameNode name)
	{
		ScribDel del = del();
		PayloadElem elem = new PayloadElem(name);
		elem = (PayloadElem) elem.del(del);
		return elem;
	}

	@Override
	public PayloadElem visitChildren(AstVisitor nv) throws ScribbleException
	{
		PayloadElemNameNode name = (PayloadElemNameNode) visitChild(this.name, nv);
		return reconstruct(name);
	}
	
	@Override
	public String toString()
	{
		return this.name.toString();
	}
}
