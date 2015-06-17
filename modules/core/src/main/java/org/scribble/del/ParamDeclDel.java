package org.scribble.del;

import org.scribble.ast.ScribNode;
import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.visit.NameDisambiguator;
import org.scribble.sesstype.kind.Kind;
import org.scribble.util.ScribbleException;

public class ParamDeclDel extends ScribDelBase
{
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{
		NonRoleParamDecl<? extends Kind> pd = cast(child);
		disamb.addParameter(pd.getDeclName(), pd.kind);
		//return disamb;
	}
	
	private static NonRoleParamDecl<? extends Kind> cast(ScribNode child)
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
