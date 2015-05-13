package org.scribble2.model;

import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.NameNode;
import org.scribble2.model.name.simple.ParameterNode;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.sesstype.kind.SigKind;
import org.scribble2.sesstype.name.Name;
import org.scribble2.sesstype.name.Role;

//public class ParamDecl extends HeaderParamDecl<ParameterNode, Parameter> //implements HeaderParameterDecl// extends HeaderParameterDecl<ParameterNode>
public class ParamDecl<K extends Kind> extends HeaderParamDecl<Name<K>, K> //implements HeaderParameterDecl// extends HeaderParameterDecl<ParameterNode>
{
	//public enum Kind { TYPE, SIG }  // ROLE
	
	/*public static final Function<NameDecl<? extends PrimitiveNameNode>, ParameterDecl> toParameterDecl =
			(NameDecl<? extends PrimitiveNameNode> nd) -> (ParameterDecl) nd;*/
	
	//public final ParameterNode name;
	//public final Kind kind;
	public final K kind;

	//public ParamDecl(Kind kind, ParameterNode name)
	public ParamDecl(K kind, NameNode<Name<K>, K> name)
	{
		//super(t, kind, namenode);
		super(name);
		this.kind = kind;
		//this.name = namenode;
	}
	
	@Override
	protected ParamDecl<K> reconstruct(NameNode<Name<K>, K> namenode)
	{
		ModelDel del = del();
		ParamDecl<K> pd = new ParamDecl<>(this.kind, this.name);
		pd = (ParamDecl<K>) pd.del(del);
		return pd;
	}

	@Override
	protected ParamDecl<K> copy()
	{
		return new ParamDecl<>(this.kind, (ParameterNode<K>) this.name);
	}

	/*@Override
	public Parameter toName()
	{
		return ((ParameterNode) this.name).toName();
	}*/
	
	@Override
	//public ParamDecl project(Role self)
	public ParamDecl<K> project(Role self)
	{
		//ParameterNode<K> pn = new ParameterNode<>(this.kind, this.name.toString());  // FIXME: use factory?
		ParameterNode<K> pn = ModelFactoryImpl.FACTORY.ParameterNode(this.kind, this.name.toString());
		//return new ParameterDecl(this.kind, pn);
		return ModelFactoryImpl.FACTORY.ParameterDecl(this.kind, pn);
	}
	
	@Override
	public String getKeyword()
	{
		if (this.kind.equals(SigKind.KIND))
		{
			return Constants.SIG_KW;
		}
		else if (this.kind.equals(SigKind.KIND))
		{
			return Constants.SIG_KW;
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + this.kind);
		}
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
