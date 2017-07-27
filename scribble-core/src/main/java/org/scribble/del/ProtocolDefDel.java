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
package org.scribble.del;

import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ScribNode;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.context.RecRemover;
import org.scribble.visit.util.RecVarCollector;

public abstract class ProtocolDefDel extends ScribDelBase
{
	protected ProtocolDef<? extends ProtocolKind> inlined = null;

	protected abstract ProtocolDefDel copy();

	@Override
	public void enterProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl) throws ScribbleException
	{
		ScribDelBase.pushVisitorEnv(this, inl);
	}
	
	public ProtocolDef<?> getInlinedProtocolDef()
	{
		return this.inlined;
	}

	public ProtocolDefDel setInlinedProtocolDef(ProtocolDef<?> inlined)
	{
		ProtocolDefDel copy = copy();
		copy.inlined = inlined;
		return copy;
	}

	public void enterRecRemoval(ScribNode parent, ScribNode child, RecRemover rem)
	{
		super.enterRecRemoval(parent, child, rem);
		RecVarCollector rvc = new RecVarCollector(rem.job);
		try
		{
			this.inlined.accept(rvc);  // RecVarCollector not an InlinedProtocolVistor -- do simple visiting directly on inlined
		}
		catch (ScribbleException e)
		{
			throw new RuntimeException(e);
		}
		rem.setToRemove(rvc.getNames());
	}
}
