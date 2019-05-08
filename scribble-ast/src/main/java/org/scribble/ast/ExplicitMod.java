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
package org.scribble.ast;

import org.antlr.runtime.Token;
import org.scribble.del.DelFactory;

public class ExplicitMod extends ProtoModNode
{
	// ScribTreeAdaptor#create constructor
	public ExplicitMod(Token t)
	{
		super(t);
	}
	
	// Tree#dupNode constructor
	protected ExplicitMod(ExplicitMod node)
	{
		super(node);
	}

	@Override
	public boolean isExplicit()
	{
		return true;
	}
	
	@Override
	public ExplicitMod dupNode()
	{
		return new ExplicitMod(this);  // return this also OK, since no children
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.ExplicitMod(this);
	}
	
	@Override
	public String toString()
	{
		return "explicit";
	}
}
