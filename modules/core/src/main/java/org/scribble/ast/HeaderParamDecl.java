package org.scribble.ast;

import org.scribble.ast.name.simple.SimpleNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.AstVisitor;


// Names that are declared in a protocol header (roles and parameters -- not the protocol name though)
public abstract class HeaderParamDecl<K extends Kind> extends NameDeclNode<K>
{
	protected HeaderParamDecl(SimpleNameNode<K> name)
	{
		super(name);
	}

	protected abstract HeaderParamDecl<K> reconstruct(SimpleNameNode<K> name);
	
	@Override
	public HeaderParamDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		SimpleNameNode<K> name = visitChildWithClassCheck(this, (SimpleNameNode<K>) this.name, nv);
		return reconstruct(name);
	}
	
	public abstract HeaderParamDecl<K> project(Role self);  // FIXME: move to delegate

	public abstract String getKeyword();
	
	@Override
	public String toString()
	{
		return getKeyword() + " " + getDeclName().toString();
	}
}
