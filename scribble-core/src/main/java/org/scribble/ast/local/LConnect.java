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
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LConnect extends LConnectionAction implements LSimpleInteractionNode
{
	public LConnect(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public LConnect(RoleNode src, RoleNode dest)
	{
		super(source, src, msg, dest);
		//super(src, dest);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LConnect(this.source, this.src, this.msg, this.dest);
		//return new LConnect(this.src, this.dest);
	}
	
	@Override
	public LConnect clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = this.dest.clone(af);
		return af.LConnect(this.source, src, msg, dest);
		//return AstFactoryImpl.FACTORY.LConnect(src, dest);
	}

	@Override
	public LConnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public LConnect reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		LConnect ls = new LConnect(this.source, src, msg, dest);
		//LConnect ls = new LConnect(src, dest);
		ls = (LConnect) ls.del(del);
		return ls;
	}

	// Could make a LMessageTransfer to factor this out with LReceive
	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return this.src.toName();
		//throw new RuntimeException("TODO: " + this);
	}

	@Override
	public String toString()
	{
		return (isUnitMessage() ? "" : this.msg+ " ") + Constants.CONNECT_KW + " " + this.dest.toString() + ";";
	}

	@Override
	public LInteractionNode merge(AstFactory af, LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LConnect: " + this);
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return false;
	}

	@Override
	public Set<Message> getEnabling()
	{
		return Collections.emptySet();
	}

	/*// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LSimpleInteractionNode.super.getKind();
	}*/
}
