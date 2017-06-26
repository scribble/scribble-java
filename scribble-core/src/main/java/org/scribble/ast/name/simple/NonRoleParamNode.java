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
package org.scribble.ast.name.simple;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.MessageNode;
import org.scribble.ast.NonRoleArgNode;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.sesstype.Arg;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.kind.Kind;
import org.scribble.sesstype.kind.NonRoleParamKind;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.Name;
import org.scribble.sesstype.name.PayloadType;
import org.scribble.visit.Substitutor;

// An unambiguous kinded parameter (ambiguous parameters handled by disambiguation) that isn't a role -- e.g. DataType/MessageSigName param
//public class NonRoleParamNode<K extends NonRoleParamKind> extends SimpleNameNode<K> implements MessageNode, PayloadElemNameNode
//public class NonRoleParamNode<K extends NonRoleParamKind> extends SimpleNameNode<K> implements MessageNode, PayloadElemNameNode<PayloadTypeKind>
public class NonRoleParamNode<K extends NonRoleParamKind> extends SimpleNameNode<K> implements MessageNode, PayloadElemNameNode<DataTypeKind>  // As a payload, can only be a DataType (so hardcode)
{
	public final K kind;
	
	public NonRoleParamNode(CommonTree source, K kind, String identifier)
	{
		super(source, identifier);
		this.kind = kind;
	}
	
	@Override
	public MessageNode project(AstFactory af)  // MessageSigName params
	{
		return this;
	}

	@Override
	protected NonRoleParamNode<K> copy()
	{
		return new NonRoleParamNode<>(this.source, this.kind, getIdentifier());
	}
	
	@Override
	public NonRoleParamNode<K> clone(AstFactory af)
	{
		return af.NonRoleParamNode(this.source, this.kind, getIdentifier());
	}
	
	@Override
	public NonRoleArgNode substituteNames(Substitutor subs)
	{
		Arg<K> arg = toArg();
		NonRoleArgNode an;
		if (this.kind.equals(SigKind.KIND) || this.kind.equals(DataTypeKind.KIND))
		//if (this.kind instanceof NonRoleParamKind)  // Would additionally include other payloadtype kinds 
		{
			an = subs.getArgumentSubstitution(arg);  // getArgumentSubstitution returns a clone
		}
		else
		{
			throw new RuntimeException("TODO: " + this);
		}
		// Effectively a reconstruct: use the dels/envs made by the subprotocolvisitor cloning, cf. RoleNode
		an = (NonRoleArgNode) an.del(del());
		return an;
	}
	
	@Override
	public Name<K> toName()
	{
		String id = getIdentifier();
		if (this.kind.equals(SigKind.KIND))
		{
			return Kind.castName(this.kind, new MessageSigName(id));
		}
		else if (this.kind.equals(DataTypeKind.KIND))
		{
			return Kind.castName(this.kind, new DataType(id));
		}
		else
		{
			throw new RuntimeException("Shouldn't get in here: " + this.kind);
		}
	}

	@Override
	public boolean isParamNode()
	{
		return true;
	}

	@Override
	public Arg<K> toArg()
	{
		Arg<? extends Kind> arg;
		if (this.kind.equals(DataTypeKind.KIND))  // FIXME: as a payload kind, currently hardcorded to data type kinds (protocol payloads not supported)
		{
			arg = toPayloadType();
		}
		else if (this.kind.equals(SigKind.KIND))
		{
			 arg = toMessage();
		}
		else
		{
			throw new RuntimeException("Shouldn't get here: " + this);
		}
		@SuppressWarnings("unchecked")
		Arg<K> tmp = (Arg<K>) arg;
		return tmp;
	}

	@Override
	public Message toMessage()
	{
		if (!this.kind.equals(SigKind.KIND))
		{
			throw new RuntimeException("Not a sig kind parameter: " + this);
		}
		return (Message) toName();
	}

	@Override
	//public PayloadType<? extends PayloadTypeKind> toPayloadType()
	public PayloadType<DataTypeKind> toPayloadType()  // Currently can assume the only possible kind for NonRoleParamNode is DataTypeKind
	//public PayloadType<? extends PayloadTypeKind> toPayloadType()
	{
		if (this.kind.equals(DataTypeKind.KIND))  // As a payload, NonRoleParamNode can only be a DataType
		{
			return (DataType) toName();
		}
		/*else if (this.kind.equals(Local.KIND))  // Protocol params not supported
		{
			return (Local) toName();
		}*/
		throw new RuntimeException("Not a payload kind parameter: " + this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof NonRoleParamNode))
		{
			return false;
		}
		NonRoleParamNode<? extends NonRoleParamKind> n = (NonRoleParamNode<?>) o;
		return n.canEqual(this) && this.kind.equals(n.kind) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof NonRoleParamNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 317;
		hash = 31 * super.hashCode();
		return hash;
	}
}
