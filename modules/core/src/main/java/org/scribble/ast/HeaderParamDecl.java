package org.scribble.ast;

import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ParamKind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.AstVisitor;

// Names that are declared in a protocol header (roles and parameters -- not the protocol name though)
// RoleKind or (NonRole)ParamKind
public abstract class HeaderParamDecl<K extends ParamKind> extends NameDeclNode<K>
{
	protected HeaderParamDecl(SimpleNameNode<K> name)
	{
		super(name);
	}

	public abstract HeaderParamDecl<K> reconstruct(SimpleNameNode<K> name);
	
	@Override
	public HeaderParamDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		SimpleNameNode<K> name = visitChildWithClassEqualityCheck(this, (SimpleNameNode<K>) this.name, nv);
		return reconstruct(name);
	}
	
	public abstract HeaderParamDecl<K> project(Role self);  // Move to delegate?

	public abstract String getKeyword();
	
	@Override
	public String toString()
	{
		return getKeyword() + " " + getDeclName().toString();
	}
}
