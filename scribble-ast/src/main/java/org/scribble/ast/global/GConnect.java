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

import org.antlr.runtime.Token;
import org.scribble.ast.ConnectAction;
import org.scribble.core.type.kind.Global;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

// TODO: make GConnectionAction and factor this class with GWrap (cf. LConnectionAction)
public class GConnect extends ConnectAction<Global>
		implements GSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public GConnect(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GConnect(GConnect node)
	{
		super(node);
	}
	
	@Override
	public GConnect dupNode()
	{
		return new GConnect(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.GConnect(this);
	}

	@Override
	public String toString()
	{
		return (isUnitMessage() ? "" : getMessageNodeChild() + " ")
				+ Constants.CONNECT_KW + " " + getSourceChild() + " " + Constants.TO_KW
				+ " " + getDestinationChild() + ";";
	}
}
