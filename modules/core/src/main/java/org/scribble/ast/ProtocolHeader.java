package org.scribble.ast;

import org.scribble.ast.name.NameNode;
import org.scribble.ast.name.qualified.ProtocolNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.visit.AstVisitor;

// TODO: parameterize on global/local name node and role decl list (i.e. self roles)
public abstract class ProtocolHeader<K extends ProtocolKind> extends NameDeclNode<K> implements ProtocolKindNode<K>
{
	public final RoleDeclList roledecls;
	public final NonRoleParamDeclList paramdecls;

	protected ProtocolHeader(NameNode<K> name, RoleDeclList roledecls, NonRoleParamDeclList paramdecls)
	{
		super(name);
		this.roledecls = roledecls;
		this.paramdecls = paramdecls;
	}
	
	public abstract ProtocolHeader<K> reconstruct(ProtocolNameNode<K> name, RoleDeclList rdl, NonRoleParamDeclList pdl);
	
	@Override
	public ProtocolHeader<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleDeclList rdl = (RoleDeclList) visitChild(this.roledecls, nv);
		NonRoleParamDeclList pdl = (NonRoleParamDeclList) visitChild(this.paramdecls, nv);
		return reconstruct((ProtocolNameNode<K>) this.name, rdl, pdl);
	}

	public boolean isParameterDeclListEmpty()
	{
		return this.paramdecls.isEmpty();
	}
	
	public abstract ProtocolNameNode<K> getNameNode();
	
	@Override
	public ProtocolName<K> getDeclName()
	{
		return (ProtocolName<K>) super.getDeclName();
	}

	@Override
	public String toString()
	{
		String s = Constants.PROTOCOL_KW + " " + this.name;
		if (!isParameterDeclListEmpty())
		{
			s += this.paramdecls;
		}
		return s + this.roledecls;
	}
}
