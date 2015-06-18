package org.scribble.ast;

import org.scribble.ast.name.qualified.MemberNameNode;
import org.scribble.ast.visit.AstVisitor;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.MemberName;

// FIXME: rename to something better
public abstract class NonProtocolDecl<K extends Kind> extends NameDeclNode<K>
{
	public final String schema;
	public final String extName;
	public final String source;

	public NonProtocolDecl(String schema, String extName, String source, MemberNameNode<K> name)
	{
		super(name);
		this.schema = schema;
		this.extName = extName;
		this.source = source;
	}
	
	public boolean isDataTypeDecl()
	{
		return false;
	}

	public boolean isMessageSigDecl()
	{
		return false;
	}
	
	protected abstract NonProtocolDecl<K> reconstruct(String schema, String extName, String source, MemberNameNode<K> name);

	@Override
	public NonProtocolDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		MemberNameNode<K> name = (MemberNameNode<K>) visitChildWithClassCheck(this, this.name, nv);
		return reconstruct(this.schema, this.extName, this.source, name);
	}

	@Override
	public MemberName<K> getDeclName()
	{
		return (MemberName<K>) super.getDeclName();  // Simple name -- not consistent with ModuleDecl
	}
}
