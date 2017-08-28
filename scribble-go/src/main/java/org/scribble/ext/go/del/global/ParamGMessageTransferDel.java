package org.scribble.ext.go.del.global;

import java.util.List;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.del.global.GMessageTransferDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.name.Role;
import org.scribble.visit.wf.NameDisambiguator;

public class ParamGMessageTransferDel extends GMessageTransferDel
{
	public ParamGMessageTransferDel()
	{
		
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode parent, ScribNode child, NameDisambiguator disamb, ScribNode visited) throws ScribbleException
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		Role src = gmt.src.toName();
		List<Role> dests = gmt.getDestinationRoles();
		if (dests.contains(src))
		{
			// FIXME TODO
			//throw new ScribbleException(gmt.getSource(), "[TODO] Self connections not supported: " + gmt);  // Would currently be subsumed by unconnected check
		}
		return gmt;
	}
}
