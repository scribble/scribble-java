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

import org.scribble.sesstype.kind.PayloadTypeKind;
import org.scribble.sesstype.name.PayloadType;

// Not in grammar file -- but cf. DoArg (and PayloadElemList cf. DoArgList) -- i.e. need a wrapper for mixed and initially ambiguous name kinds
//public abstract class PayloadElem<K extends PayloadTypeKind> extends ScribNodeBase
//public abstract class PayloadElem<T extends PayloadElemNameNode<?>> extends ScribNodeBase -- problem is GProtocolNameNode child isn't a payload kind, and anyway there's also a role node child
//public abstract class PayloadElem extends ScribNodeBase
//public interface PayloadElem extends ScribNode
public interface PayloadElem<K extends PayloadTypeKind> extends ScribNode
{
	PayloadElem<K> project(AstFactory af);  // Currently outside of visitor/env pattern (cf. MessageNode)
	
	default boolean isGlobalDelegationElem()
	{
		return false;
	}

	default boolean isLocalDelegationElem()
	{
		return false;
	}

	/*public final PayloadElemNameNode<K> name;  // Doesn't work for DelegationElem (Global@Role), name is global but payloadelem is local -- similar reason why not a NameNode, delegation doesn't fit -- would work for direct LProtocolNameNode elems though

	public PayloadElem(PayloadElemNameNode<K> name)
	{
		this.name = name;
	}*/

	/*@Override
	protected PayloadElem copy()
	{
		return new PayloadElem(this.name);
	}
	
	@Override
	public PayloadElem clone()
	{
		PayloadElemNameNode name = (PayloadElemNameNode) this.name.clone();
		return AstFactoryImpl.FACTORY.PayloadElem(name);
	}*/

	/*public PayloadElem reconstruct(PayloadElemNameNode name)
	{
		ScribDel del = del();
		PayloadElem elem = new PayloadElem(name);
		elem = (PayloadElem) elem.del(del);
		return elem;
	}

	@Override
	public PayloadElem visitChildren(AstVisitor nv) throws ScribbleException
	{
		PayloadElemNameNode name = (PayloadElemNameNode) visitChild(this.name, nv);
		return reconstruct(name);
	}*/
	
	//public abstract PayloadType<?> toPayloadType();
	public PayloadType<? extends PayloadTypeKind> toPayloadType();  // Mainly a wrapper method for the wrapped NameNode
	
	/*public abstract Name<K> toName();  // Not deriving from Named/NameNode, delegation doesn't fit -- would need to make a special (Global@Role) name of Local kind
	
	@Override
	public String toString()
	{
		//return this.name.toString();
		return toName().toString();
	}*/
}
