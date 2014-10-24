package scribble2.ast;

import org.antlr.runtime.tree.CommonTree;

import scribble2.ast.name.ParameterNode;
import scribble2.ast.name.PrimitiveNameNode;
import scribble2.main.ScribbleException;
import scribble2.sesstype.name.Kind;
import scribble2.sesstype.name.Role;
import scribble2.visit.NameDisambiguator;
import scribble2.visit.NodeVisitor;
import scribble2.visit.Projector;
import scribble2.visit.env.ProjectionEnv;

public class ParameterDecl extends NameDecl<ParameterNode> //implements NameDeclaration
{
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, ParameterDecl> toParameterDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (ParameterDecl) nd;*/

	public ParameterDecl(CommonTree ct, Kind kind, ParameterNode namenode)
	{
		super(ct, kind, namenode);
	}
	
	@Override
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
		NameDecl<? extends PrimitiveNameNode> nd = super.visitChildren(nv);
		return new ParameterDecl(nd.ct, nd.kind, (ParameterNode) nd.name);
	}
}
