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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.job.RuntimeScribbleException;
import org.scribble.job.ScribbleException;
import org.scribble.type.Message;
import org.scribble.type.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LRequest extends LConnectionAction implements LSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LRequest(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LRequest(LRequest node)
	{
		super(node);
	}
	
	@Override
	public LRequest dupNode()
	{
		return new LRequest(this);
	}

	// Could make a LMessageTransfer to factor this out with LReceive
	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return getSourceChild().toName();
		//throw new RuntimeException("TODO: " + this);
	}

	@Override
	public LSessionNode merge(AstFactory af, LSessionNode ln)
			throws ScribbleException
	{
		throw new RuntimeScribbleException("Invalid merge on LRequest: " + this);
	}

	@Override
	public boolean canMerge(LSessionNode ln)
	{
		return false;
	}

	@Override
	public Set<Message> getEnabling()
	{
		return Collections.emptySet();
	}

	@Override
	public String toString()
	{
		return (isUnitMessage() ? "" : getMessageNodeChild() + " ")
				+ Constants.CONNECT_KW + " " + getDestinationChild().toString() + ";";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public LRequest(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public LConnect(RoleNode src, RoleNode dest)
	{
		super(source, src, msg, dest);
		//super(src, dest);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new LRequest(this.source, this.src, this.msg, this.dest);
		//return new LConnect(this.src, this.dest);
	}
	
	@Override
	public LRequest clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = this.dest.clone(af);
		return af.LConnect(this.source, src, msg, dest);
		//return AstFactoryImpl.FACTORY.LConnect(src, dest);
	}

	@Override
	public LRequest reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	//public LConnect reconstruct(RoleNode src, RoleNode dest)
	{
		ScribDel del = del();
		LRequest ls = new LRequest(this.source, src, msg, dest);
		//LConnect ls = new LConnect(src, dest);
		ls = (LRequest) ls.del(del);
		return ls;
	}*/
}
