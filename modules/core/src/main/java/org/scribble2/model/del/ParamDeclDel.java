package org.scribble2.model.del;

import org.scribble2.model.ModelNode;
import org.scribble2.model.NonRoleParamDecl;
import org.scribble2.model.visit.NameDisambiguator;
import org.scribble2.sesstype.kind.Kind;
import org.scribble2.util.ScribbleException;

public class ParamDeclDel extends ModelDelBase
{
	@Override
	public void enterDisambiguation(ModelNode parent, ModelNode child, NameDisambiguator disamb) throws ScribbleException
	{
		NonRoleParamDecl<? extends Kind> pd = cast(child);
		disamb.addParameter(pd.getDeclName(), pd.kind);
		//return disamb;
	}
	
	private static NonRoleParamDecl<? extends Kind> cast(ModelNode child)
	{
		NonRoleParamDecl.class.cast(child);
		@SuppressWarnings("unchecked")
		NonRoleParamDecl<? extends Kind> tmp = (NonRoleParamDecl<? extends Kind>) child;
		return tmp;
	}

	/*@Override
	public void enterBoundNamesCheck(ModelNode parent, ModelNode child, BoundNameChecker checker) throws ScribbleException
	{
		ParamDecl<? extends Kind> pd = (ParamDecl<? extends Kind>) child;
		checker.addParameter(pd.toName());
	}*/
}
