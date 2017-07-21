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
import org.scribble.ast.UnaryPayloadElem;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.PayloadElemType;
import org.scribble.visit.AstVisitor;

// N.B. extends UnaryPayloadElem, not DelegationElem
public class LDelegationElem extends UnaryPayloadElem<Local>
{
  // Currently no potential for ambiguity because only generated, not parsed
	public LDelegationElem(CommonTree source, LProtocolNameNode proto)
	{
		super(source, proto);
	}

	@Override
	public boolean isLocalDelegationElem()
	{
		return true;
	}

	@Override
	protected LDelegationElem copy()
	{
		return new LDelegationElem(this.source, (LProtocolNameNode) this.name);
	}
	
	@Override
	public LDelegationElem clone(AstFactory af)
	{
		LProtocolNameNode name = (LProtocolNameNode) this.name.clone(af);
		return af.LDelegationElem(this.source, name);
	}

	public LDelegationElem reconstruct(LProtocolNameNode proto)
	{
		ScribDel del = del();
		LDelegationElem elem = new LDelegationElem(this.source, proto);
		elem = (LDelegationElem) elem.del(del);
		return elem;
	}

	@Override
	public LDelegationElem visitChildren(AstVisitor nv) throws ScribbleException
	{
		LProtocolNameNode name = (LProtocolNameNode) visitChild(this.name, nv);
		return reconstruct(name);
	}
	
	/*@Override
	public String toString()
	{
		return this.proto + "@" + this.role;
	}*/

	@Override
	//public PayloadType<? extends PayloadTypeKind> toPayloadType()
	public PayloadElemType<Local> toPayloadType()
	{
		return this.name.toPayloadType();
	}
}
