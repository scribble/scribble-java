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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.type.Payload;
import org.scribble.type.name.PayloadElemType;
import org.scribble.util.ScribUtil;
import org.scribble.visit.AstVisitor;

// Cf. DoArgList, but here we don't need as much abstraction (cf. RoleArgList, NonRoleArgList)
public class PayloadElemList extends ScribNodeBase
{
	// ScribTreeAdaptor#create constructor
	public PayloadElemList(Token t)
	{
		super(t);
		this.elems = null;
	}

	// Tree#dupNode constructor
	public PayloadElemList(PayloadElemList node)
	{
		super(node);
		this.elems = null;
	}
	
	public PayloadElemList dupNode()
	{
		return new PayloadElemList(this);
	}
	
	public List<PayloadElem<?>> getElementChildren()
	{
		return ((List<?>) getChildren()).stream().map(x -> (PayloadElem<?>) x)
				.collect(Collectors.toList());
	}

	protected PayloadElemList reconstruct(List<PayloadElem<?>> elems)
	{
		PayloadElemList pay = dupNode();
		pay.addChildren(elems);
		ScribDel del = del();
		pay.setDel(del);  // No copy
		return pay;
	}
	
	@Override
	public PayloadElemList visitChildren(AstVisitor nv) throws ScribbleException
	{
		List<PayloadElem<?>> elems = 
				visitChildListWithClassEqualityCheck(this, getElementChildren(), nv);
		return reconstruct(elems);
	}
	
	protected PayloadElemList project(AstFactory af)
	{
		return af.PayloadElemList(this.source, getElementChildren().stream()
				.map(pe -> pe.project(af)).collect(Collectors.toList()));
	}

	public Payload toPayload()
	{
		List<PayloadElemType<?>> pts = getElementChildren().stream()
				.map(pe -> pe.toPayloadType()).collect(Collectors.toList());
		return new Payload(pts);
	}

	public boolean isEmpty()
	{
		return getChildCount() == 0;
	}

	@Override
	public String toString()
	{
		return "(" + getElementChildren().stream().map(pe -> pe.toString())
				.collect(Collectors.joining(", ")) + ")";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//private final List<PayloadElem> elems;  // FIXME: parameterise on Kind (cf. sesstypes)
	private final List<PayloadElem<?>> elems;

	//public PayloadElemList(List<PayloadElem> elems)
	public PayloadElemList(CommonTree source, List<PayloadElem<?>> elems)
	{
		super(source);
		this.elems = new LinkedList<>(elems);
	}
	
	/*@Override
	protected PayloadElemList copy()
	{
		return new PayloadElemList(this.source, this.elems);
	}
	
	@Override
	public PayloadElemList clone(AstFactory af)
	{
		//List<PayloadElem> elems = ScribUtil.cloneList(this.elems);
		List<PayloadElem<?>> elems = ScribUtil.cloneList(af, this.elems);
		return af.PayloadElemList(this.source, elems);
	}*/
}
