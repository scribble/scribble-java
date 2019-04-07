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
package org.scribble.visit;

import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ScribNode;
import org.scribble.del.ProtocolDefDel;
import org.scribble.lang.Lang;
import org.scribble.util.ScribException;
import org.scribble.visit.env.Env;

public abstract class InlinedProtocolVisitor<T extends Env<?>> extends EnvVisitor<T>
{
	public InlinedProtocolVisitor(Lang lang)
	{
		super(lang);
	}

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribException
	{
		enter(parent, child);
		ScribNode visited = visitInlinedProtocol(parent, child);
		return leave(parent, child, visited);
	}

	protected ScribNode visitInlinedProtocol(ScribNode parent, ScribNode child) throws ScribException
	{
		if (child instanceof ProtocolDef)
		{
			return visitOverrideForProtocolDef(parent, (ProtocolDef<?>) child);	// parent is InteractionSequence
		}
		else
		{
			return child.visitChildren(this);
					// The base (super) behaviour (could factor it out in ModelVisitor as its own visitor method) -- not super.visit because that does enter/exit
		}
	}
	
	// N.B. results of visiting inlined version are stored back to inlined field, but original AST is unaffected -- so any Env/Del or AST updates to inlined version do not reflect back onto original AST -- a motivation for the original SubprotocolVisitor approach
	private ScribNode visitOverrideForProtocolDef(ScribNode parent, ProtocolDef<?> pd) throws ScribException
	{
		ProtocolDef<?> inlined = ((ProtocolDefDel) pd.del()).getInlinedProtocolDef();
		if (inlined == null)
		{
			throw new ScribException("InlineProtocolVisitor error: " + pd);  // E.g. -fsm when inconsistent choice subjects
				// FIXME: shouldn't occur any more?
		}
		
		/*if (this instanceof EndpointGraphBuilder)
		{
			System.out.println("\nBuilding graph from: " + inlined + "\n");
		}*/
		
		ProtocolDef<?> visited = (ProtocolDef<?>) inlined.visitChildren(this);
		ProtocolDefDel del = (ProtocolDefDel) pd.del();
		return pd.del(del.setInlinedProtocolDef(visited));
	}

	@Override
	protected final void envEnter(ScribNode parent, ScribNode child) throws ScribException
	{
		super.envEnter(parent, child);
		inlinedEnter(parent, child);
	}

	@Override
	protected final ScribNode envLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribException
	{
		ScribNode n = inlinedLeave(parent, child, visited);
		return super.envLeave(parent, child, n);
	}

	protected void inlinedEnter(ScribNode parent, ScribNode child) throws ScribException
	{

	}

	protected ScribNode inlinedLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribException
	{
		return visited;
	}
}
