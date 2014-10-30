package org.scribble2.foo.ast;

import org.antlr.runtime.Token;


// TODO: factor out stuff from ImportModule and ImportMember into here
public abstract class ImportDecl extends ScribbleASTBase//, ModuleMember //implements NameDeclaration 
{
	public ImportDecl(Token t)
	{
		super(t);
	}
	
	public abstract boolean isAliased();
}
