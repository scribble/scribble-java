package org.scribble.f17.ast.local;

import org.scribble.f17.ast.F17AstFactory;
import org.scribble.f17.ast.global.F17GChoice;
import org.scribble.f17.ast.global.F17GRec;
import org.scribble.f17.ast.global.F17GRecVar;
import org.scribble.f17.ast.global.F17GType;
import org.scribble.f17.ast.global.action.F17GAction;
import org.scribble.f17.ast.global.action.F17GMessageTransfer;
import org.scribble.f17.ast.local.action.F17LAction;
import org.scribble.f17.main.F17Exception;
import org.scribble.sesstype.name.Role;


public class F17Projector
{
	private final F17AstFactory factory = new F17AstFactory();

	public F17Projector()
	{

	}

	// merge is for projection of "delegation payload types"
	public F17LType translate(F17GType gt, Role r) throws F17Exception
	{
		if (gt instanceof F17GChoice)
		{
			
		}
		else if (gt instanceof F17GRec)
		{
			
		}
		else if (gt instanceof F17GRecVar)
		{
			
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + gt);
		}
		return null;
	}

	public F17LAction translate(F17GAction gt, Role r) throws F17Exception
	{
		if (gt instanceof F17GMessageTransfer)
		{
			
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + gt);
		}
		return null;
	}
}
