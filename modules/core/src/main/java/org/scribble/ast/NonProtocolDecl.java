package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
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
	public final String extSource;

	public NonProtocolDecl(CommonTree source, String schema, String extName, String extSource, MemberNameNode<K> name)
	{
		super(source, name);
		this.schema = schema;
		this.extName = extName;
		this.extSource = extSource;
	}
	
	public abstract NonProtocolDecl<K> reconstruct(String schema, String extName, String source, MemberNameNode<K> name);

	@Override
	public NonProtocolDecl<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		MemberNameNode<K> name = (MemberNameNode<K>) visitChildWithClassEqualityCheck(this, this.name, nv);
		return reconstruct(this.schema, this.extName, this.extSource, name);
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
