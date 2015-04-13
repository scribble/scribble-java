package org.scribble2.model;

import org.scribble2.model.name.simple.SimpleNameNode;
import org.scribble2.model.visit.ModelVisitor;
import org.scribble2.sesstype.name.SimpleName;
import org.scribble2.util.ScribbleException;



// Names that are declared in a protocol header (roles and parameters -- not the protocol name though)
//public interface HeaderParameterDecl extends ModelNode, NameDecl //implements NameDeclaration
public abstract class HeaderParameterDecl<T extends SimpleNameNode<T2>, T2 extends SimpleName> extends NameDeclNode<T, T2> //implements NameDeclaration
{
	public final T name;

	protected HeaderParameterDecl(T name)
	{
		this.name = name;
	}

	protected abstract HeaderParameterDecl<T, T2> reconstruct(T namenode);
	
	@Override
	public HeaderParameterDecl<T, T2> visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//SimpleNameNode name = (SimpleNameNode) visitChild(this.name, nv);
		T name = visitChildWithClassCheck(this, this.name, nv);
		return reconstruct(name);
	}

	@Override
	//public SimpleName toName()
	public T2 toName()
	{
		return this.name.toName();
	}
	
	public abstract String getKeyword();
	
	@Override
	public String toString()
	{
		//return toName().toString();  // FIXME: need declaration kind keyword
		return getKeyword() + " " + toName().toString();
	}
}

/*public class HeaderParameterDecl<T extends SimpleNameNode> extends ModelNodeBase //implements NameDeclaration
{
	//public final Kind kind;
	public final T name;

	//public NameDecl(Token t, Kind kind, T namenode)
	public HeaderParameterDecl(T namenode)
	{
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
	}* /

	@Override
	public String toString()
	{
		//return this.kind + " " + this.name;
		return this.name.toString();
	}
}*/
