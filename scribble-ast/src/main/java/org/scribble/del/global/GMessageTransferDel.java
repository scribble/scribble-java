/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.del.global;

import java.util.List;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Message;
import org.scribble.del.MessageTransferDel;
import org.scribble.util.ScribException;
import org.scribble.visit.GTypeTranslator;
import org.scribble.visit.wf.NameDisambiguator;

public class GMessageTransferDel extends MessageTransferDel
		implements GSimpleSessionNodeDel
{
	public GMessageTransferDel()
	{
		
	}

	@Override
	public ScribNode leaveDisambiguation(ScribNode child,
			NameDisambiguator disamb, ScribNode visited) throws ScribException
	{
		GMessageTransfer gmt = (GMessageTransfer) visited;
		Role src = gmt.getSourceChild().toName();
		List<Role> dests = gmt.getDestinationRoles();
		if (dests.contains(src))
		{
			throw new ScribException(gmt.getSource(),
					"[TODO] Self connections not supported: " + gmt);  // TODO: refactor to Job validation pass
						// CHECKME: subsumed by unconnected check ?
		}
		return gmt;
	}
	
	@Override
	public org.scribble.core.type.session.global.GMessageTransfer translate(ScribNode n,
			GTypeTranslator t) throws ScribException
	{
		GMessageTransfer source = (GMessageTransfer) n;
		Role src = source.getSourceChild().toName();
		List<RoleNode> ds = source.getDestinationChildren();
		if (ds.size() > 1)
		{
			throw new RuntimeException("[TODO] multiple destination roles: " + source);
		}
		Role dst = ds.get(0).toName();
		Message msg = source.getMessageNodeChild().toMessage();
		return new org.scribble.core.type.session.global.GMessageTransfer(source,
				src, msg, dst);
	}
}
