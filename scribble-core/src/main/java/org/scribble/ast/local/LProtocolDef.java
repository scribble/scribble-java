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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDef;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;

public class LProtocolDef extends ProtocolDef<Local> implements LNode
{
	public LProtocolDef(CommonTree source, LProtocolBlock block)
	{
		super(source, block);
	}

	@Override
	protected LProtocolDef copy()
	{
		return new LProtocolDef(this.source, getBlock());
	}
	
	@Override
	public LProtocolDef clone(AstFactory af)
	{
		LProtocolBlock block = getBlock().clone(af);
		return af.LProtocolDef(this.source, block);
	}

	@Override
	public LProtocolDef reconstruct(ProtocolBlock<Local> block)
	{
		ScribDel del = del();
		LProtocolDef lpd = new LProtocolDef(this.source, (LProtocolBlock) block);
		lpd = (LProtocolDef) lpd.del(del);
		return lpd;
	}
	
	@Override
	public LProtocolBlock getBlock()
	{
		return (LProtocolBlock) this.block;
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LNode.super.getKind();
	}
}
