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
import org.scribble.ast.DisconnectAction;
import org.scribble.core.type.kind.Global;
import org.scribble.del.DelFactory;

public class GDisconnect extends DisconnectAction<Global>
		implements GSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public GDisconnect(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public GDisconnect(GDisconnect node)
	{
		super(node);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.GDisconnect(this);
	}
	
	@Override
	public GDisconnect dupNode()
	{
		return new GDisconnect(this);
	}
}
