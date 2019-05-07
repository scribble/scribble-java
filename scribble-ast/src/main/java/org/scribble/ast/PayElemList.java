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
package org.scribble.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.scribble.core.type.name.PayElemType;
import org.scribble.core.type.session.Payload;
import org.scribble.del.DelFactory;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// Cf. DoArgList, but here we don't need as much abstraction (cf. RoleArgList, NonRoleArgList)
public class PayElemList extends ScribNodeBase
{
	// ScribTreeAdaptor#create constructor
	public PayElemList(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public PayElemList(PayElemList node)
	{
		super(node);
	}
	
	List<PayElem<?>> getElementChildren()
	{
		return getChildren().stream().map(x -> (PayElem<?>) x)
				.collect(Collectors.toList());
	}

	// "add", not "set"
	public void addScribChildren(List<PayElem<?>> elems)
	{
		// Cf. above getters and Scribble.g children order
		super.addChildren(elems);
	}
		
	@Override
	public PayElemList dupNode()
	{
		return new PayElemList(this);
	}

	@Override
	public void decorateDel(DelFactory df)
	{
		df.PayElemList(this);
	}

	protected PayElemList reconstruct(List<PayElem<?>> elems)
	{
		PayElemList dup = dupNode();
		dup.addScribChildren(elems);
		dup.setDel(del());  // No copy
		return dup;
	}
	
	@Override
	public PayElemList visitChildren(AstVisitor nv) throws ScribException
	{
		List<PayElem<?>> elems = 
				visitChildListWithClassEqualityCheck(this, getElementChildren(), nv);
		return reconstruct(elems);
	}

	public Payload toPayload()
	{
		List<PayElemType<?>> elems = getElementChildren().stream()
				.map(x -> x.toPayloadType()).collect(Collectors.toList());
		return new Payload(elems);
	}

	public boolean isEmpty()
	{
		return getChildCount() == 0;
	}

	@Override
	public String toString()
	{
		return "(" + getElementChildren().stream().map(x -> x.toString())
				.collect(Collectors.joining(", ")) + ")";
	}
}
