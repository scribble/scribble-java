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
package org.scribble.ast.local;

import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.util.Constants;

public class LRecv extends LMessageTransfer
{
	// ScribTreeAdaptor#create constructor
	public LRecv(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LRecv(LRecv node)
	{
		super(node);
	}
	
	@Override
	public LRecv dupNode()
	{
		return new LRecv(this);
	}

	@Override
	public String toString()
	{
		return getMessageNodeChild() + " " + Constants.FROM_KW + " "
				+ getSourceChild() + ";";
	}

	
	
	
	
	
	
	
	
	public LRecv(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(source, src, msg, dests);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new LReceive(this.source, this.src, this.msg, getDestinationChildren());
	}
	
	@Override
	public LReceive clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		List<RoleNode> dests = ScribUtil.cloneList(af, getDestinationChildren());
		return af.LReceive(this.source, src, msg, dests);
	}

	@Override
	public LReceive reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		LReceive lr = new LReceive(this.source, src, msg, dests);
		lr = (LReceive) lr.del(del);
		return lr;
	}*/
}
