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

import java.util.Collections;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Continue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LContinue extends Continue<Local> implements LSimpleInteractionNode
{
	public LContinue(CommonTree source, RecVarNode recvar)
	{
		super(source, recvar);
	}

	@Override
	protected LContinue copy()
	{
		return new LContinue(this.source, this.recvar);
	}
	
	@Override
	public LContinue clone()
	{
		RecVarNode rv = this.recvar.clone();
		return AstFactoryImpl.FACTORY.LContinue(this.source, rv);
	}

	@Override
	public LContinue reconstruct(RecVarNode recvar)
	{
		ScribDel del = del();
		LContinue lc = new LContinue(this.source, recvar);
		lc = (LContinue) lc.del(del);
		return lc;
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		//return new DummyProjectionRoleNode().toName();  // For e.g. rec X { 1() from A to B; choice at A { continue X; } or { 2() from A to B; } }
		return fixer.createRecVarRole(this.recvar.toName());  // Never used?
		//return null;
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LSimpleInteractionNode.super.getKind();
	}

	@Override
	public LInteractionNode merge(LInteractionNode ln) throws ScribbleException
	{
		if (!(ln instanceof LContinue) || !this.canMerge(ln))
		{
			throw new ScribbleException("Cannot merge " + this.getClass() + " and " + ln.getClass() + ": " + this + ", " + ln);
		}
		LContinue them = ((LContinue) ln);
		if (!this.recvar.equals(them.recvar))
		{
			throw new ScribbleException("Cannot merge choices for " + this.recvar + " and " + them.recvar + ": " + this + ", " + ln);
		}
		return clone();
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return ln instanceof LContinue;
	}

	@Override
	public Set<Message> getEnabling()
	{
		return Collections.emptySet();
	}
}
