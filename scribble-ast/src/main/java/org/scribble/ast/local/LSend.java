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
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class LSend extends LMsgTransfer
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
	public RoleNode getSelfChild()
	{
		return getSourceChild();
	}

	@Override
	public RoleNode getPeerChild()
	{
		List<RoleNode> dsts = getDestinationChildren();
		if (dsts.size() > 1)
		{
			throw new RuntimeException("[TODO] Multi-send: " + this);
		}
		return dsts.get(0);
	}
	
	@Override
	public LSend dupNode()
	{
		return new LSend(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LSend(this);
	}

	@Override
	public String toString()
	{
		return getMessageNodeChild() + " " + Constants.TO_KW
				+ " "
				+ getDestinationChildren().stream().map(x -> x.toString())
						.collect(Collectors.joining(", "))
				+ ";";
	}
}
