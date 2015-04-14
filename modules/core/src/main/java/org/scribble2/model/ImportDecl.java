package org.scribble2.model;



// TODO: factor out stuff from ImportModule and ImportMember into here
public abstract class ImportDecl extends ModelNodeBase//, ModuleMember //implements NameDeclaration 
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
