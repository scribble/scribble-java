package org.scribble2.foo.ast;

import org.antlr.runtime.Token;
import org.scribble2.foo.ast.name.simple.ParameterNode;

public class ParameterDecl extends HeaderParameterDecl<ParameterNode> //implements NameDeclaration
{
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, ParameterDecl> toParameterDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (ParameterDecl) nd;*/

	//public ParameterDecl(Toke t, Kind kind, ParameterNode namenode)
	public ParameterDecl(Token t, ParameterNode namenode)
	{
		//super(t, kind, namenode);
		super(t, namenode);
	}
	
	/*@Override
	public NameDisambiguator enterDisambiguation(NameDisambiguator disamb) throws ScribbleException
	{
		disamb.addParameter(this.name.toName());
		return disamb;
	}

	// Not doing anything except cloning
	@Override
	public ParameterDecl leaveProjection(Projector proj)  // Redundant now
	{
		ParameterDecl projection = project(proj.peekSelf());
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	public ParameterDecl project(Role self)
	{
		ParameterNode pn = new ParameterNode(null, this.name.toString(), this.kind);
		return new ParameterDecl(null, this.kind, pn);
	}

	@Override
	public ParameterDecl visitChildren(NodeVisitor nv) throws ScribbleException
	{
		HeaderParameterDecl<? extends PrimitiveNameNode> nd = super.visitChildren(nv);
		return new ParameterDecl(nd.ct, nd.kind, (ParameterNode) nd.name);
	}*/
}
