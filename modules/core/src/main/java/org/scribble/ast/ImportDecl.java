package org.scribble.ast;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.sesstype.kind.ImportKind;
import org.scribble.sesstype.name.Name;

// TODO: factor out stuff from ImportModule and ImportMember into here, e.g. alias/name, reconstruct
public abstract class ImportDecl<K extends ImportKind> extends ScribNodeBase//, ModuleMember //implements NameDeclaration 
{
	protected ImportDecl(CommonTree source)
	{
		super(source);
	}
	
	public abstract boolean isAliased();
	public abstract Name<K> getAlias();
	//public abstract Name<K> getVisibleName();
	
	public boolean isImportModule()
	{
		return false;
	}
}
