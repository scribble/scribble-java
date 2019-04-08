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
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.util.Constants;

public class LSend extends LMessageTransfer
		implements LSimpleSessionNode  // Explicitly needed here for getKind
{
	// ScribTreeAdaptor#create constructor
	public LSend(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LSend(LSend node)
	{
		super(node);
	}
	
	@Override
	public LSend dupNode()
	{
		return new LSend(this);
	}

	@Override
	public String toString()
	{
		return getMessageNodeChild() + " "
				+ Constants.TO_KW + " " + getDestinationChildren().stream()
						.map(dest -> dest.toString()).collect(Collectors.joining(", "))
				+ ";";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public LSend(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(source, src, msg, dests);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new LSend(this.source, this.src, this.msg, getDestinationChildren());
	}
	
	@Override
	public LSend clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		List<RoleNode> dests = ScribUtil.cloneList(af, getDestinationChildren());
		return af.LSend(this.source, src, msg, dests);
	}

	@Override
	public LSend reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		LSend ls = new LSend(this.source, src, msg, dests);
		ls = (LSend) ls.del(del);
		return ls;
	}*/
}
