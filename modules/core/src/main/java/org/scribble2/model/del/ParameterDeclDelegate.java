package org.scribble2.model.del;

import org.scribble2.model.ModelNode;
import org.scribble2.model.ParameterDecl;
import org.scribble2.model.visit.NameDisambiguator;
import org.scribble2.util.ScribbleException;

public class ParameterDeclDelegate extends ModelDelegateBase
{
	@Override
	public NameDisambiguator enterDisambiguation(ModelNode n, NameDisambiguator disamb) throws ScribbleException
	{
		disamb.addParameter(((ParameterDecl) n).toName());
		return disamb;
	}

	/*@Override
	public ModelNode leaveDisambiguation(ModelNode n, NameDisambiguator disamb) throws ScribbleException
	{
		
	}*/
}
