package org.scribble2.model;

import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.name.Role;

//public class ParameterDecl extends HeaderParameterDecl<ParameterNode, Parameter> //implements HeaderParameterDecl// extends HeaderParameterDecl<ParameterNode>
public class ParameterDecl<K extends Kind> extends HeaderParameterDecl<K> //implements HeaderParameterDecl// extends HeaderParameterDecl<ParameterNode>
{
	//public enum ParamDeclKind { TYPE, SIG }  // ROLE
	
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, ParameterDecl> toParameterDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (ParameterDecl) nd;*/
	
	////public final ParameterNode<K> name;
	//public final ParamDeclKind kind;
	
	public final K kind;

	//public ParameterDecl(ParamDeclKind kind, ParameterNode name)
	public ParameterDecl(K kind, ParameterNode<K> name)  // FIXME: restrict to TYPE/SIG?
	{
		//super(t, kind, namenode);
		super(name);
		this.kind = kind;
		//this.name = namenode;
	}
	
	@Override
	//protected ParameterDecl reconstruct(ParameterNode name)
	protected ParameterDecl<K> reconstruct(SimpleKindedNameNode<K> name)
	{
		ModelDelegate del = del();
		ParameterDecl<K> pd = new ParameterDecl<>(this.kind, (ParameterNode<K>) name);
		@SuppressWarnings("unchecked")
		ParameterDecl<K> tmp = (ParameterDecl<K>) pd.del(del);  // Hack? or OK since we just made pd
		return tmp;
	}

	@Override
	protected ParameterDecl<K> copy()
	{
		return new ParameterDecl<>(this.kind, (ParameterNode<K>) this.name);
	}

	/*@Override
	public Parameter toName()
	{
		return ((ParameterNode) this.name).toName();
	}*/
	
	@Override
	public ParameterDecl<K> project(Role self)
	{
		ParameterNode<K> pn = new ParameterNode<>(this.kind, this.name.toString());
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
