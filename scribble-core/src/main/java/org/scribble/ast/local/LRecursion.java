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

import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.Recursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.Message;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LRecursion extends Recursion<Local> implements LCompoundInteractionNode
{
	public LRecursion(CommonTree source, RecVarNode recvar, LProtocolBlock block)
	{
		super(source, recvar, block);
	}

	@Override
	protected LRecursion copy()
	{
		return new LRecursion(this.source, this.recvar, getBlock());
	}

	@Override
	public LRecursion reconstruct(RecVarNode recvar, ProtocolBlock<Local> block)
	{
		ScribDel del = del();
		LRecursion lr = new LRecursion(this.source, recvar, (LProtocolBlock) block);
		lr = (LRecursion) lr.del(del);
		return lr;
	}
	
	@Override
	public LRecursion clone(AstFactory af)
	{
		RecVarNode recvar = this.recvar.clone(af);
		LProtocolBlock block = getBlock().clone(af);
		return af.LRecursion(this.source, recvar, block);
	}
	
	@Override
	public LProtocolBlock getBlock()
	{
		return (LProtocolBlock) this.block;
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		//fixer.pushRec(this.recvar.toName());
		return getBlock().getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer);
	}

	@Override
	public LInteractionNode merge(AstFactory af, LInteractionNode ln) throws ScribbleException
	{
		if (!(ln instanceof LRecursion) || !this.canMerge(ln))
		{
			throw new ScribbleException("Cannot merge " + this.getClass() + " and " + ln.getClass() + ": " + this + ", " + ln);
		}
		LRecursion them = ((LRecursion) ln);
		if (!this.recvar.equals(them.recvar))
		{
			throw new ScribbleException("Cannot merge recursions for " + this.recvar + " and " + them.recvar + ": " + this + ", " + ln);
		}
		return af.LRecursion(this.source, this.recvar.clone(af), getBlock().merge(them.getBlock()));  // Not reconstruct: leave context building to post-projection passes
				// HACK: this source
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return ln instanceof LRecursion;
	}

	@Override
	public Set<Message> getEnabling()
	{
		return getBlock().getEnabling();
	}
}
