package org.scribble.ast;

import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.PayloadTypeKind;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;

// Cf. DoArg, wrapper for a (unary) name node of potentially unknown kind (needs disamb)
// PayloadTypeKind is DataType or Local, but Local has its own special subclass (and protocol params not allowed), so this should implicitly be for DataType only
// AST hierarchy requires unary and delegation (binary pair) payloads to be structurally distinguished
//public class DataTypeElem extends PayloadElem<DataTypeKind>
public class UnaryPayloadElem<K extends PayloadTypeKind> extends ScribNodeBase implements PayloadElem<K>//extends PayloadElem
{
	//public final PayloadElemNameNode<DataTypeKind> name;
	//public final DataTypeNode data; 
	public final PayloadElemNameNode<K> name;   // (Ambig.) DataTypeNode or parameter

	//public DataTypeElem(PayloadElemNameNode<DataTypeKind> name)
	//public UnaryPayloadlem(DataTypeNode data)
	//public UnaryPayloadElem(PayloadElemNameNode name)
	public UnaryPayloadElem(PayloadElemNameNode<K> name)
	{
		//super(name);
		//this.data = data;
		this.name = name;
	}
	
	@Override
	public UnaryPayloadElem<K> project()
	{
		return this;
	}

	@Override
	protected UnaryPayloadElem<K> copy()
	{
		//return new UnaryPayloadElem(this.data);
		return new UnaryPayloadElem<>(this.name);
	}
	
	@Override
	public UnaryPayloadElem<K> clone()
	{
		//PayloadElemNameNode<DataTypeKind> name = (PayloadElemNameNode<DataTypeKind>) this.data.clone();  // FIXME: make a DataTypeNameNode
		//PayloadElemNameNode<K> name = (PayloadElemNameNode<K>) this.name.clone();
		PayloadElemNameNode<K> name = ScribUtil.checkNodeClassEquality(this.name, this.name.clone());
		return AstFactoryImpl.FACTORY.UnaryPayloadElem(name);
	}

	//public DataTypeElem reconstruct(PayloadElemNameNode<DataTypeKind> name)
	//public UnaryPayloadElem reconstruct(DataTypeNode name)
	public UnaryPayloadElem<K> reconstruct(PayloadElemNameNode<K> name)
	{
		ScribDel del = del();
		UnaryPayloadElem<K> elem = new UnaryPayloadElem<>(name);
		elem = ScribNodeBase.del(elem, del);
		return elem;
	}

	@Override
	public UnaryPayloadElem<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		//PayloadElemNameNode<DataTypeKind> name = (PayloadElemNameNode<DataTypeKind>) visitChild(this.data, nv);
		//DataTypeNode name = (PayloadElemNameNode<DataTypeKind>) visitChild(this.data, nv);
		PayloadElemNameNode<K> name = (PayloadElemNameNode<K>) visitChild(this.name, nv);  // FIXME: probably need to record an explicit kind token, for "cast checking"
		//PayloadElemNameNode<K> name = (PayloadElemNameNode<K>) visitChildWithClassEqualityCheck(this, this.name, nv);  // No: can be initially Ambig
		return reconstruct(name);
	}
	
	@Override
	public String toString()
	{
		//return this.data.toString();
		return this.name.toString();
	}

	@Override
	//public PayloadType<DataTypeKind> toPayloadType()  // Currently can assume the only possible kind is DataTypeKind
	//public PayloadType<? extends PayloadTypeKind> toPayloadType()  // Currently can assume the only possible kind is DataTypeKind
	public PayloadType<K> toPayloadType()  // Currently can assume the only possible kind is DataTypeKind
	{
		//return this.data.toPayloadType();
		return this.name.toPayloadType();
	}
}
