package org.scribble2.model.del;

import org.scribble2.model.ModelNode;
import org.scribble2.model.ParamDecl;
import org.scribble2.model.visit.BoundNameChecker;
import org.scribble2.model.visit.NameDisambiguator;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.util.ScribbleException;

public class ParamDeclDel extends ModelDelBase
{
	@Override
	public void enterDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb) throws ScribbleException
	{
		ParamDecl<? extends Kind> pd = (ParamDecl<? extends Kind>) child;
		disamb.addParameter(pd.toName(), pd.kind);
		//return disamb;
	}

	@Override
	public void enterBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker) throws ScribbleException
	{
		ParamDecl<? extends Kind> pd = (ParamDecl<? extends Kind>) child;
		checker.addParameter(pd.toName());
	}
}
