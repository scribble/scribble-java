package org.scribble2.model.del;

import org.scribble2.model.ModelNode;
import org.scribble2.model.ParamDecl;
import org.scribble2.model.visit.NameDisambiguator;
import org.scribble2.util.ScribbleException;

public class ParamDeclDel extends ModelDelBase
{
	@Override
	public NameDisambiguator enterDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb) throws ScribbleException
	{
		disamb.addParameter(((ParamDecl) child).toName());
		return disamb;
	}
}
