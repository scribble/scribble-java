package scribble2.ast;

import org.antlr.runtime.tree.CommonTree;

import scribble2.ast.name.PayloadTypeOrParameterNode;
import scribble2.main.ScribbleException;
import scribble2.visit.NodeVisitor;
import scribble2.visit.Projector;
import scribble2.visit.env.ProjectionEnv;

public class PayloadElement extends AbstractNode
{
	public final PayloadTypeOrParameterNode type;

	public PayloadElement(CommonTree ct, PayloadTypeOrParameterNode type)
	{
		super(ct);
		this.type = type;
	}

	// Basically a copy without the AST
	@Override
	public PayloadElement leaveProjection(Projector proj) //throws ScribbleException
	{
		PayloadTypeOrParameterNode type = (PayloadTypeOrParameterNode) ((ProjectionEnv) this.type.getEnv()).getProjection();	
		PayloadElement projection = new PayloadElement(null, type);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	/*@Override
	public PayloadElement disambiguate(PayloadTypeOrParameterDisambiguator disamb) throws ScribbleException
	{
		PayloadElement pe = (PayloadElement) super.disambiguate(disamb);
		PayloadTypeOrParameterNode type;
		if (pe.type.isAmbiguousNode())
		{
			type = disamb.disambiguate((AmbiguousNameNode) pe.type);
		}
		else
		{
			type = pe.type;
		}
		return new PayloadElement(pe.ct, pe.annot, type);
	}

	@Override
	public PayloadElement substitute(Substitutor subs) throws ScribbleException
	{
		PayloadTypeOrParameterNode type;
		if (this.type.isParameterNode())
		{
			type = (PayloadTypeOrParameterNode) subs.substituteParameter(((ParameterNode) this.type).toName());
		}
		else
		{
			type = this.type;
		}
		//return new PayloadElement(this.ct, this.kind, this.annot, type);
		return new PayloadElement(this.ct, this.annot, type);
	}

	@Override
	public PayloadElement checkWellFormedness(WellFormednessChecker wfc)
	{
		// (PayloadElement) super.checkWellFormedness(wfc);
		Env env = wfc.getEnv();
		//Kind kind;
		if (this.type.isParameterNode())  // Similar to GlobalMessageTransfer
		{
			//Parameter param = ((ParameterNode) this.type).toPayloadTypeParameter();
			Parameter param = ((ParameterNode) this.type).toName();
			if (!env.params.isParameterDeclared(param))
			{
				throw new RuntimeException("Bad parameter: " + param);
			}
			ParameterDecl.Kind pdkind = env.params.getParameterKind(param);
			if (!pdkind.equals(ParameterDecl.Kind.TYPE))
			{
				throw new RuntimeException("PayloadElement type parameter should be of TYPE kind, not: " + pdkind);
			}
			//kind = Kind.PARAMETER;
		}
		else
		{
			PayloadType type = ((PayloadTypeNode) this.type).toName();
			if (!env.isPayloadTypeDeclared(type))
			{
				throw new RuntimeException("Bad PayloadElement type: " + type);
			}
			//kind = Kind.TYPE;
		}

		if (hasAnnotation())
		{
			Annotation annot = this.annot.toName();
			if (env.annots.isAnnotationDeclared(annot))
			{
				throw new RuntimeException("Duplicate annotation name: " + annot);
			}
			env.annots.addAnnot(annot);
		}

		//return new PayloadElement(this.ct, kind, this.annot, this.type);
		return new PayloadElement(this.ct, this.annot, this.type);
	}*/

	@Override
	public PayloadElement visitChildren(NodeVisitor nv) throws ScribbleException
	{
		PayloadTypeOrParameterNode type = (PayloadTypeOrParameterNode) visitChild((Node) this.type, nv);
		return new PayloadElement(this.ct, type);
	}
	
	@Override
	public String toString()
	{
		return this.type.toString();
	}
}
