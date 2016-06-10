package org.scribble.ast;

import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.visit.AstVisitor;

// Cf. DoArg, wrapper for a (unary) name node of potentially unknown kind (needs disamb) -- but no need for generic name node parameter, as currently only DataTypeKind expected
// AST hierarchy requires unary and delegation (binary pair) payloads to be structurally distinguished
//public class DataTypeElem extends PayloadElem<DataTypeKind>
public class UnaryPayloadElem extends ScribNodeBase implements PayloadElem//extends PayloadElem
{
	//public final PayloadElemNameNode<DataTypeKind> name;
	//public final DataTypeNode data; 
	public final PayloadElemNameNode name;   // (Ambig.) DataTypeNode or parameter

	//public DataTypeElem(PayloadElemNameNode<DataTypeKind> name)
	//public UnaryPayloadlem(DataTypeNode data)
	public UnaryPayloadElem(PayloadElemNameNode name)
	{
		//super(name);
		//this.data = data;
		this.name = name;
	}

	@Override
	protected UnaryPayloadElem copy()
	{
		//return new UnaryPayloadElem(this.data);
		return new UnaryPayloadElem(this.name);
	}
	
	@Override
	public UnaryPayloadElem clone()
	{
		//PayloadElemNameNode<DataTypeKind> name = (PayloadElemNameNode<DataTypeKind>) this.data.clone();  // FIXME: make a DataTypeNameNode
		PayloadElemNameNode name = (PayloadElemNameNode) this.name.clone();  // FIXME: make a DataTypeNameNode
		return AstFactoryImpl.FACTORY.UnaryPayloadElem(name);
	}

	//public DataTypeElem reconstruct(PayloadElemNameNode<DataTypeKind> name)
	//public UnaryPayloadElem reconstruct(DataTypeNode name)
	public UnaryPayloadElem reconstruct(PayloadElemNameNode name)
	{
		ScribDel del = del();
		UnaryPayloadElem elem = new UnaryPayloadElem(name);
		elem = (UnaryPayloadElem) elem.del(del);
		return elem;
	}

	@Override
	public UnaryPayloadElem visitChildren(AstVisitor nv) throws ScribbleException
	{
		//PayloadElemNameNode<DataTypeKind> name = (PayloadElemNameNode<DataTypeKind>) visitChild(this.data, nv);
		//DataTypeNode name = (PayloadElemNameNode<DataTypeKind>) visitChild(this.data, nv);
		PayloadElemNameNode name = (PayloadElemNameNode) visitChild(this.name, nv);
		return reconstruct(name);
	}
	
	@Override
	public String toString()
	{
		//return this.data.toString();
		return this.name.toString();
	}

	@Override
	public PayloadType<DataTypeKind> toPayloadType()  // Currently can assume the only possible kind is DataTypeKind
	{
		//return this.data.toPayloadType();
		return this.name.toPayloadType();
	}
}
