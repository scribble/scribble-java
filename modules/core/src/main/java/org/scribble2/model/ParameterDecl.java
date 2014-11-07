package org.scribble2.model;

import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.sesstype.name.Parameter;

public class ParameterDecl extends ModelNodeBase implements HeaderParameterDecl// extends HeaderParameterDecl<ParameterNode>
{
	public enum Kind { TYPE, SIG }  // ROLE
	
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, ParameterDecl> toParameterDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (ParameterDecl) nd;*/
	
	public final ParameterNode name;
	public final Kind kind;

	public ParameterDecl(Kind kind, ParameterNode namenode)
	{
		//super(t, kind, namenode);
		//super(namenode);
		this.kind = kind;
		this.name = namenode;
	}

	@Override
	protected ParameterDecl copy()
	{
		return new ParameterDecl(this.kind, this.name);
	}

	@Override
	public Parameter toName()
	{
		return this.name.toName();
	}
	
	@Override
	public String toString()
	{
		return toName().toString();
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
