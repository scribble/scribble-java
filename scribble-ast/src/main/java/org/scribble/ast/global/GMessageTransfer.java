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
package org.scribble.ast.global;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.Global;
import org.scribble.util.Constants;

public class GMessageTransfer extends MessageTransfer<Global>
		implements GSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public GMessageTransfer(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GMessageTransfer(GMessageTransfer node)
	{
		super(node);
	}
	
	@Override
	public GMessageTransfer dupNode()
	{
		return new GMessageTransfer(this);
	}

	@Override
	public String toString()
	{
		return getMessageNodeChild() + " " + Constants.FROM_KW
				+ " " + getSourceChild() + " " + Constants.TO_KW
				+ " "
				+ getDestinationChildren().stream().map(x -> x.toString())
						.collect(Collectors.joining(", "))
				+ ";";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public GMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(source, src, msg, dests);
	}

	/*@Override
	protected GMessageTransfer copy()
	{
		return new GMessageTransfer(this.source, this.src, this.msg, getDestinationChildren());
	}
	
	@Override
	public GMessageTransfer clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		List<RoleNode> dests = ScribUtil.cloneList(af, getDestinationChildren());
		return af.GMessageTransfer(this.source, src, msg, dests);
	}*/

	/*@Override
	public GMessageTransfer reconstruct(RoleNode src, MessageNode msg,
			List<RoleNode> dests)
	{
		ScribDel del = del();
		GMessageTransfer gmt = new GMessageTransfer(this.source, src, msg, dests);
		gmt = (GMessageTransfer) gmt.del(del);
		return gmt;
	}*/
}
