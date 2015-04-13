package org.scribble2.model;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.sesstype.name.Parameter;
import org.scribble2.sesstype.name.Role;

public class ParameterDecl extends HeaderParameterDecl<ParameterNode, Parameter> //implements HeaderParameterDecl// extends HeaderParameterDecl<ParameterNode>
{
	public enum Kind { TYPE, SIG }  // ROLE
	
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, ParameterDecl> toParameterDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (ParameterDecl) nd;*/
	
	//public final ParameterNode name;
	public final Kind kind;

	public ParameterDecl(Kind kind, ParameterNode name)
	{
		//super(t, kind, namenode);
		super(name);
		this.kind = kind;
		//this.name = namenode;
	}
	
	@Override
	protected ParameterDecl reconstruct(ParameterNode name)
	{
		ModelDelegate del = del();
		ParameterDecl pd = new ParameterDecl(this.kind, (ParameterNode) name);
		pd = (ParameterDecl) pd.del(del);
		return pd;
	}

	@Override
	protected ParameterDecl copy()
	{
		return new ParameterDecl(this.kind, (ParameterNode) this.name);
	}

	/*@Override
	public Parameter toName()
	{
		return ((ParameterNode) this.name).toName();
	}*/
	
	public ParameterDecl project(Role self)
	{
		ParameterNode pn = new ParameterNode(this.name.toString());
		//return new ParameterDecl(this.kind, pn);
		return ModelFactoryImpl.FACTORY.ParameterDecl(this.kind, pn);
	}
	
	@Override
	public String getKeyword()
	{
		//return Constants....;
		throw new RuntimeException("TODO");
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

	@Override
	public ParameterDecl visitChildren(NodeVisitor nv) throws ScribbleException
	{
		HeaderParameterDecl<? extends PrimitiveNameNode> nd = super.visitChildren(nv);
		return new ParameterDecl(nd.ct, nd.kind, (ParameterNode) nd.name);
	}*/
}
