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

import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.DisconnectAction;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.job.RuntimeScribbleException;
import org.scribble.job.ScribbleException;
import org.scribble.type.Message;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

// "left" of LDisconnect is used for self
public class LDisconnect extends DisconnectAction<Local>
		implements LSimpleInteractionNode
{
	// ScribTreeAdaptor#create constructor
	public LDisconnect(Token t)
	{
		super(t);
		this.self = null;
		this.peer = null;
	}

	// Tree#dupNode constructor
	public LDisconnect(LDisconnect node)
	{
		super(node);
		this.self = null;
		this.peer = null;
	}
	
	public RoleNode getSelfChild()
	{
		return getLeftChild();
	}

	public RoleNode getPeerChild()
	{
		return getRightChild();
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		RoleNode src = getSelfChild();
		fixer.setChoiceSubject(src.toName());
		return src.toName();
	}
	
	@Override
	public LDisconnect dupNode()
	{
		return new LDisconnect(this);
	}

	@Override
	public LInteractionNode merge(AstFactory af, LInteractionNode ln)
			throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LDisconnect: " + this);
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return false;
	}

	@Override
	public Set<Message> getEnabling()
	{
		Set<Message> enab = new HashSet<>();
		//enab.add(this.msg.toMessage());  // Return empty to skip over this in LInteractionSeq
		return enab;
	}

	@Override
	public String toString()
	{
		return Constants.DISCONNECT_KW + " " + getPeerChild() + ";";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final RoleNode self;  // super.src
	private final RoleNode peer;  // super.dest
	
	public LDisconnect(CommonTree source, MessageSigNode unit, RoleNode self, RoleNode peer)
	{
		//super(source, self, GDisconnect.UNIT_MESSAGE_SIG_NODE, peer);
		super(source, self, peer);
		this.self = self;
		this.peer = peer;
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new LDisconnect(this.source, (MessageSigNode) this.msg, this.self, this.peer);
	}
	
	@Override
	public LDisconnect clone(AstFactory af)
	{
		RoleNode self = this.self.clone(af);
		RoleNode peer = this.peer.clone(af);
		return af.LDisconnect(this.source, self, peer);
	}*/

	/*@Override
	public LDisconnect reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public LDisconnect reconstruct(RoleNode self, RoleNode peer)
	{
		ScribDel del = del();
		LDisconnect lr = new LDisconnect(this.source, (MessageSigNode) this.msg, this.self, this.peer);
		lr = (LDisconnect) lr.del(del);
		return lr;
	}*/
}
