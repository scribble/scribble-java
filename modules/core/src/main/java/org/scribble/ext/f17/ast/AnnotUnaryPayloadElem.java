package org.scribble.ext.f17.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.UnaryPayloadElem;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.f17.ast.name.simple.PayloadVarNode;
import org.scribble.ext.f17.del.AnnotUnaryPayloadElemDel;
import org.scribble.ext.f17.sesstype.name.AnnotPayloadType;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.PayloadTypeKind;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;

// FIXME: other parts of implementation hardcodes annotated payloads to DataTypeKind -- maybe hardcode here too (initially)
//		-- no: using this class to fully replace base UnaryPayloadElem<K>, which needs other K's
// FIXME: separate annotated payload elem subclass (fixed to DataTypeKind) from base payload elem
public class AnnotUnaryPayloadElem<K extends PayloadTypeKind> extends UnaryPayloadElem<K> implements AnnotPayloadElem<K>
{
	public final PayloadVarNode payvar;  // null for non-annotated  // FIXME: refactor properly
			// Or just use NonRoleParamNode? -- ties into SubprotocolVisitor, name substitution
			// Cf. AnnotGMessageTransfer (etc) using ScribAnnot
	
	public AnnotUnaryPayloadElem(CommonTree source, PayloadElemNameNode<K> name)
	{
		this(source, null, name);
	}

	public AnnotUnaryPayloadElem(CommonTree source, PayloadVarNode payvar, PayloadElemNameNode<K> name)
	{
		super(source, name);
		this.payvar = payvar;
	}
	
	@Override
	public boolean isAnnotated()
	{
		return this.payvar != null;
	}

	@Override
	public ScribAnnot getAnnotation()
	{
		throw new RuntimeException("Shouldn't get in here: " + this);  // FIXME: refactor properly
	}
	
	@Override
	public AnnotUnaryPayloadElem<K> project()
	{
		return this;
	}

	@Override
	protected AnnotUnaryPayloadElem<K> copy()
	{
		return new AnnotUnaryPayloadElem<>(this.source, this.payvar, this.name);
	}
	
	@Override
	public AnnotUnaryPayloadElem<K> clone()
	{
		PayloadVarNode payvar = isAnnotated() ? this.payvar.clone() : null;  // Discards del (cf. ScribNode.clone)
		PayloadElemNameNode<K> name = ScribUtil.checkNodeClassEquality(this.name, this.name.clone());  // Returns second arg
		return AnnotAstFactoryImpl.FACTORY.AnnotUnaryPayloadElem(this.source, payvar, name);
	}

	public AnnotUnaryPayloadElem<K> reconstruct(PayloadVarNode payvar, PayloadElemNameNode<K> name)
	{
		ScribDel del = del();
		AnnotUnaryPayloadElem<K> elem = new AnnotUnaryPayloadElem<>(this.source, payvar, name);
		elem = ScribNodeBase.del(elem, del);
		return elem;
	}

	@Override
	public AnnotUnaryPayloadElem<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		PayloadVarNode payvar = isAnnotated() ? (PayloadVarNode) visitChild(this.payvar, nv) : null;
		@SuppressWarnings("unchecked")
		PayloadElemNameNode<K> name = (PayloadElemNameNode<K>) visitChild(this.name, nv);  
		return reconstruct(payvar, name);
	}
	
	@Override
	public String toString()
	{
		return (isAnnotated() ? this.payvar + ":" : "") + this.name.toString();
	}

	@Override
	public PayloadType<K> toPayloadType()  // Currently can assume the only possible kind is DataTypeKind
	{
		return isAnnotated()
			? new AnnotPayloadType<>(this.name.toPayloadType(), this.payvar.toName(), 
					//((AnnotUnaryPayloadElemDel) del()).annot)
					((AnnotUnaryPayloadElemDel) del()).getAnnot())
			: this.name.toPayloadType();
	}
}
