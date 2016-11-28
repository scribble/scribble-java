package org.scribble.del;

import java.util.List;

import org.scribble.ast.HeaderParamDecl;
import org.scribble.ast.HeaderParamDeclList;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.visit.wf.NameDisambiguator;

public abstract class HeaderParamDeclListDel extends ScribDelBase
{
	public HeaderParamDeclListDel()
	{

	}

	// Doing in leave allows the arguments to be individually checked first
	@Override
	public HeaderParamDeclList<?> leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		HeaderParamDeclList<?> pdl = (HeaderParamDeclList<?>) visited;
		List<? extends HeaderParamDecl<?>> decls = pdl.getDecls();  // grammar enforces RoleDeclList size > 0
		if (decls.size() != decls.stream().map((d) -> d.getDeclName()).distinct().count())
		{
			throw new ScribbleException("Duplicate header decls: " + pdl);
		}
		return pdl;
	}
}
