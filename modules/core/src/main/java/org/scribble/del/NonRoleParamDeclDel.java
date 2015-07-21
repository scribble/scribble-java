package org.scribble.del;

import org.scribble.ast.NonRoleParamDecl;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.NameDisambiguator;

public class NonRoleParamDeclDel extends ScribDelBase
{
	@Override
	public void enterDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb) throws ScribbleException
	{
		NonRoleParamDecl<?> pd = (NonRoleParamDecl<?>) child;
		disamb.addParameter(pd.getDeclName(), pd.kind);
	}
}
