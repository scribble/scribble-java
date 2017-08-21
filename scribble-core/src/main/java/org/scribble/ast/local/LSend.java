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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.type.Message;
import org.scribble.type.name.Role;
import org.scribble.util.ScribUtil;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LSend extends LMessageTransfer
		implements LSimpleInteractionNode  // Explicitly needed here for getKind
{
	public LSend(CommonTree source, RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(source, src, msg, dests);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LSend(this.source, this.src, this.msg, getDestinations());
	}
	
	@Override
	public LSend clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		List<RoleNode> dests = ScribUtil.cloneList(af, getDestinations());
		return af.LSend(this.source, src, msg, dests);
	}

	@Override
	public LSend reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ScribDel del = del();
		LSend ls = new LSend(this.source, src, msg, dests);
		ls = (LSend) ls.del(del);
		return ls;
	}

	// Could make a LMessageTransfer to factor this out with LReceive
	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return this.src.toName();
	}

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.TO_KW + " "
					+ getDestinations().stream().map((dest) -> dest.toString()).collect(Collectors.joining(", ")) + ";";
	}

	@Override
	public LInteractionNode merge(AstFactory af, LInteractionNode ln) throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LSend: " + this);
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
