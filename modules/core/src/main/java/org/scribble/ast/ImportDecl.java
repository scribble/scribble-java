package org.scribble.ast;

import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.name.Name;



// TODO: factor out stuff from ImportModule and ImportMember into here
public abstract class ImportDecl<K extends Kind> extends ScribNodeBase//, ModuleMember //implements NameDeclaration 
{
	protected ImportDecl()
	{

	}
	
	public abstract boolean isAliased();
	public abstract Name<K> getAlias();
	//public abstract Name<K> getVisibleName();
	
	public boolean isImportModule()
	{
		return false;
	}
}
