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
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Message;
import org.scribble.util.Constants;
import org.scribble.util.RuntimeScribException;
import org.scribble.util.ScribException;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

public class LAccept extends LConnectionAction implements LSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LAccept(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LAccept(LAccept node)
	{
		super(node);
	}
	
	@Override
	public LAccept dupNode()
	{
		return new LAccept(this);
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		RoleNode src = getSourceChild();
		fixer.setChoiceSubject(src.toName());
		return src.toName();
	}

	@Override
	public LSessionNode merge(AstFactory af, LSessionNode ln)
			throws ScribException
	{
		throw new RuntimeScribException("Invalid merge on LAccept: " + this);
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
				+ Constants.ACCEPT_KW + " " + getSourceChild() + ";";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	public LAccept(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest)
	//public LAccept(RoleNode src, RoleNode dest)
	{
		super(source, src, msg, dest);
		//super(src, dest);
	}

	/*@Override
	protected ScribNodeBase copy()
	{
		return new LAccept(this.source, this.src, this.msg, this.dest);
		//return new LAccept(this.src, this.dest);
	}
	
	@Override
	public LAccept clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = this.dest.clone(af);
		return af.LAccept(this.source, src, msg, dest);
		//return AstFactoryImpl.FACTORY.LAccept(src, dest);
	}*/

	/*@Override
	public LAccept reconstruct(RoleNode src, MessageNode msg, RoleNode dest)
	{
		ScribDel del = del();
		LAccept lr = new LAccept(this.source, src, msg, dest);
		//LAccept lr = new LAccept(src, dest);
		lr = (LAccept) lr.del(del);
		return lr;
	}*/
}
