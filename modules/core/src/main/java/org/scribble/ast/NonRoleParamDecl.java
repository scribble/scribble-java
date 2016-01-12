package org.scribble.ast;

import org.scribble.ast.name.simple.NonRoleParamNode;
import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.Role;

public class NonRoleParamDecl<K extends NonRoleParamKind> extends HeaderParamDecl<K>
{
	public final K kind;

	public NonRoleParamDecl(K kind, NonRoleParamNode<K> name)
	{
		super(name);
		this.kind = kind;
	}
	
	@Override
	public NonRoleParamDecl<K> clone()
	{
		NonRoleParamNode<K> param = (NonRoleParamNode<K>) this.name.clone();
		return AstFactoryImpl.FACTORY.NonRoleParamDecl(this.kind, param);
	}
	
	@Override
	public NonRoleParamDecl<K> reconstruct(SimpleNameNode<K> name)
	{
		ScribDel del = del();
		NonRoleParamDecl<K> pd = new NonRoleParamDecl<>(this.kind, (NonRoleParamNode<K>) name);
		@SuppressWarnings("unchecked")
		NonRoleParamDecl<K> tmp = (NonRoleParamDecl<K>) pd.del(del);
		return tmp;
	}

	@Override
	protected NonRoleParamDecl<K> copy()
	{
		return new NonRoleParamDecl<>(this.kind, (NonRoleParamNode<K>) this.name);
	}

	@Override
	public NonRoleParamDecl<K> project(Role self)
	{
		NonRoleParamNode<K> pn = AstFactoryImpl.FACTORY.NonRoleParamNode(this.kind, this.name.toString());
		return AstFactoryImpl.FACTORY.NonRoleParamDecl(this.kind, pn);
	}
	
	@Override
	public String getKeyword()
	{
		if (this.kind.equals(SigKind.KIND))
		{
			return Constants.SIG_KW;
		}
		else if (this.kind.equals(DataTypeKind.KIND))
		{
			return Constants.TYPE_KW;
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + this.kind);
		}
	}
}
