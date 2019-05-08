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

import org.antlr.runtime.Token;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class LAcc extends LConnectAction implements LSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LAcc(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LAcc(LAcc node)
	{
		super(node);
	}

	// CHECKME: factor out implementations with LRecv as default methods 
	@Override
	public RoleNode getSelfChild()
	{
		return getDestinationChild();  // CHECKME: don't use common src/dst pattern between global/local?
	}

	@Override
	public RoleNode getPeerChild()
	{
		return getSourceChild();
	}
	
	@Override
	public LAcc dupNode()
	{
		return new LAcc(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LAcc(this);
	}

	@Override
	public String toString()
	{
		return (isUnitMessage() ? "" : getMessageNodeChild() + " ")  // TODO: deprecate ommitted "()" special case
				+ Constants.ACCEPT_KW + " " + getSourceChild() + ";";
	}
}
