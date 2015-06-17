package org.scribble.ast;



// TODO: factor out stuff from ImportModule and ImportMember into here
public abstract class ImportDecl extends ScribNodeBase//, ModuleMember //implements NameDeclaration 
{
	protected ImportDecl()
	{

	}
	
	public abstract boolean isAliased();
	
	public boolean isImportModule()
	{
		return false;
	}
}
