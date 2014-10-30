package org.scribble2.model;



// TODO: factor out stuff from ImportModule and ImportMember into here
public abstract class ImportDecl extends ModelNodeBase//, ModuleMember //implements NameDeclaration 
{
	public ImportDecl()
	{
		super();
	}
	
	public abstract boolean isAliased();
}
