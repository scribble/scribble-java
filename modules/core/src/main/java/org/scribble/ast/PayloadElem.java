package org.scribble.ast;

import org.scribble.ast.del.ModelDel;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.ast.visit.ModelVisitor;
import org.scribble.util.ScribbleException;


// Not in grammar file -- but cf. DoArg (and PayloadElemList cf. DoArgList)
public class PayloadElem extends ModelNodeBase
{
	//public final AnnotationNameNode annot;
	public final PayloadElemNameNode name;

	public PayloadElem(PayloadElemNameNode name)
	{
		this.name = name;
	}

	/*// Basically a copy without the AST
	@Override
	public PayloadElement leaveProjection(Projector proj) //throws ScribbleException
	{
		PayloadTypeOrParameterNode type = (PayloadTypeOrParameterNode) ((ProjectionEnv) this.type.getEnv()).getProjection();	
		PayloadElement projection = new PayloadElement(null, type);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/

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
	
	protected PayloadElem reconstruct(PayloadElemNameNode name)
	{
		ModelDel del = del();
		PayloadElem elem = new PayloadElem(name);
		elem = (PayloadElem) elem.del(del);
		return elem;
	}

	@Override
	public PayloadElem visitChildren(ModelVisitor nv) throws ScribbleException
	{
		//PayloadTypeOrParameterNode type = (PayloadTypeOrParameterNode) visitChild((Node) this.type, nv);
		PayloadElemNameNode name = (PayloadElemNameNode) visitChild(this.name, nv);
		return reconstruct(name);
	}
	
	@Override
	public String toString()
	{
		return this.name.toString();
	}

	@Override
	protected PayloadElem copy()
	{
		return new PayloadElem(this.name);
	}
}
