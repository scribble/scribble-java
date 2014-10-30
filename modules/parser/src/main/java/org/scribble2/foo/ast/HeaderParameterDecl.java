package org.scribble2.foo.ast;

import org.antlr.runtime.Token;
import org.scribble2.foo.ast.name.simple.SimpleNameNode;


// Names that are declared in a protocol header (roles and parameters -- not the protocol name though)
public class HeaderParameterDecl<T extends SimpleNameNode> extends ScribbleASTBase //implements NameDeclaration
{
	//public final Kind kind;
	public final T name;

	//public NameDecl(Token t, Kind kind, T namenode)
	public HeaderParameterDecl(Token t, T namenode)
	{
		super(t);
		//this.kind = kind;
		this.name = namenode;
	}
	
	/*@Override
	public NameDecl disambiguate(PayloadTypeOrParameterDisambiguator disamb) throws ScribbleException
	{
		disamb.getEnv().params.addParameter(this.name.toName(), this.kind);
		return (NameDecl) super.disambiguate(disamb);
	}*/

	/*@Override
	public NameDecl<T> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		T name = visitChildWithClassCheck(this, this.name, nv);
		return new NameDecl<T>(this.ct, this.kind, name);
	}*/

	@Override
	public String toString()
	{
		//return this.kind + " " + this.name;
		return this.name.toString();
	}
}
