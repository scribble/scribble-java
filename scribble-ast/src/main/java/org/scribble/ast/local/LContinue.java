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
import org.scribble.ast.Continue;
import org.scribble.core.type.kind.Local;
import org.scribble.del.DelFactory;

public class LContinue extends Continue<Local> implements LSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LContinue(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected LContinue(LContinue node)
	{
		super(node);
	}
	
	@Override
	public LContinue dupNode()
	{
		return new LContinue(this);
	}

	@Override
	public void decorateDel(DelFactory df)
	{
		df.LContinue(this);
	}
}
