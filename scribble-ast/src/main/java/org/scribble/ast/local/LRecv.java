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
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class LRecv extends LMsgTransfer
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
	public RoleNode getSelfChild()
	{
		List<RoleNode> dsts = getDestinationChildren();
		if (dsts.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + this);  // CHECKME: don't use common src/dst pattern between global/local?
		}
		return dsts.get(0);
	}

	@Override
	public RoleNode getPeerChild()
	{
		return getSourceChild();
	}
	
	@Override
	public LRecv dupNode()
	{
		return new LRecv(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LRecv(this);
	}

	@Override
	public String toString()
	{
		return getMessageNodeChild() + " " + Constants.FROM_KW + " "
				+ getSourceChild() + ";";
	}
}
