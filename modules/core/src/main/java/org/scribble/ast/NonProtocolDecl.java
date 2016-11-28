package org.scribble.ast;

import org.scribble.ast.name.qualified.MemberNameNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.NonProtocolKind;
import org.scribble.sesstype.name.MemberName;
import org.scribble.visit.AstVisitor;

// Rename to something better
public abstract class NonProtocolDecl<K extends NonProtocolKind> extends NameDeclNode<K> implements ModuleMember
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
	
	public abstract NonProtocolDecl<K> reconstruct(String schema, String extName, String source, MemberNameNode<K> name);

	@Override
	public NonProtocolDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		MemberNameNode<K> name = (MemberNameNode<K>) visitChildWithClassEqualityCheck(this, this.name, nv);
		return reconstruct(this.schema, this.extName, this.source, name);
	}
	
	// Maybe should be moved to ModuleMember
	public boolean isDataTypeDecl()
	{
		return false;
	}

	public boolean isMessageSigNameDecl()
	{
		return false;
	}
	
	public MemberNameNode<K> getNameNode()
	{
		return (MemberNameNode<K>) this.name;
	}

	@Override
	public MemberName<K> getDeclName()
	{
		return (MemberName<K>) super.getDeclName();  // Simple name -- not consistent with ModuleDecl
	}
}
