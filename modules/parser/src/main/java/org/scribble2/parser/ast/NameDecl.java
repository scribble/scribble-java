package scribble2.ast;

import org.antlr.runtime.tree.CommonTree;

import scribble2.visit.NodeVisitor;
import scribble2.ast.name.PrimitiveNameNode;
import scribble2.main.ScribbleException;
import scribble2.sesstype.name.Kind;

public class NameDecl<T extends PrimitiveNameNode> extends AbstractNode //implements NameDeclaration
{
	public final Kind kind;
	public final T name;

	public NameDecl(CommonTree ct, Kind kind, T namenode)
	{
		super(ct);
		this.kind = kind;
		this.name = namenode;
	}
	
	/*@Override
	public NameDecl disambiguate(PayloadTypeOrParameterDisambiguator disamb) throws ScribbleException
	{
		disamb.getEnv().params.addParameter(this.name.toName(), this.kind);
		return (NameDecl) super.disambiguate(disamb);
	}*/

	@Override
	public NameDecl<T> visitChildren(NodeVisitor nv) throws ScribbleException
	{
		T name = visitChildWithClassCheck(this, this.name, nv);
		return new NameDecl<T>(this.ct, this.kind, name);
	}

	@Override
	public String toString()
	{
		return this.kind + " " + this.name;
	}
}
