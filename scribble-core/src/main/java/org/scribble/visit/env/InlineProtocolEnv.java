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
package org.scribble.visit.env;

import org.scribble.ast.ScribNode;

public class InlineProtocolEnv extends Env<InlineProtocolEnv>
{
	private ScribNode inlined;
	
	public InlineProtocolEnv()
	{

	}

	@Override
	protected InlineProtocolEnv copy()
	{
		return new InlineProtocolEnv();
	}

	@Override
	public InlineProtocolEnv enterContext()
	{
		return copy();
	}

	public ScribNode getTranslation()
	{
		return this.inlined;
	}
	
	public InlineProtocolEnv setTranslation(ScribNode inlined)
	{
		InlineProtocolEnv copy = new InlineProtocolEnv();
		copy.inlined = inlined;
		return copy;
	}

	@Override
	public String toString()
	{
		return "inlined=" + this.inlined;
	}
}
