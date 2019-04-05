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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.PayloadElemNameNode;
import org.scribble.core.type.kind.AmbigKind;
import org.scribble.core.type.kind.DataTypeKind;
import org.scribble.core.type.kind.NonRoleArgKind;
import org.scribble.core.type.name.AmbigName;
import org.scribble.core.type.name.PayloadElemType;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.Message;

// Primitive payload type, MessageSigName or parameter names only: if name is parsed as a CompoundNameNodes, it must be a payload type (not ambiguous in this case)
// No counterpart needed for MessageNode because MessageSignature values can be syntactically distinguished from sig parameters
//public class AmbigNameNode extends SimpleNameNode<AmbigKind> implements MessageNode, PayloadElemNameNode
//public class AmbigNameNode extends SimpleNameNode<AmbigKind> implements MessageNode, PayloadElemNameNode<PayloadTypeKind>
public class AmbigNameNode extends SimpleNameNode<AmbigKind>
		implements MessageNode, PayloadElemNameNode<DataTypeKind>
		// Currently hardcoded to DataTypeKind for payload elems
{
	// ScribTreeAdaptor#create constructor
	public AmbigNameNode(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected AmbigNameNode(AmbigNameNode node)//, String id)
	{
		super(node);
	}
	
	@Override
	public AmbigNameNode dupNode()
	{
		return new AmbigNameNode(this);//, getIdentifier());
	}
	
	/*@Override
	public String getText()
	{
		//return getToken().getText();  // CHECKME: ambig nodes are now leafs -- NO: now separated from ID
		return super.getText();
	}*/
	
	@Override
	public MessageNode project(AstFactory af)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}
	
	@Override
	public Arg<? extends NonRoleArgKind> toArg()
	{
		throw new RuntimeException(
				"Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public Message toMessage()
	{
		throw new RuntimeException(
				"Ambiguous name node not disambiguated: " + this);
	}

	@Override
	//public PayloadType<AmbigKind> toPayloadType()
	public PayloadElemType<DataTypeKind> toPayloadType()  // As a payload elem, currently hardcoded to expect only DataTypeKind (protocol payloads not supported)
	//public PayloadType<PayloadTypeKind> toPayloadType()
	{
		throw new RuntimeException(
				"Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public AmbigName toName()
	{
		return new AmbigName(getText());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof AmbigNameNode))
		{
			return false;
		}
		return ((AmbigNameNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof AmbigNameNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 3659;
		hash = 31 * super.hashCode();
		return hash;
	}
	
	
	
	
	
	
	

	public AmbigNameNode(CommonTree source, String id)
	{
		super(source, id);
	}

	/*@Override
	protected AmbigNameNode copy()
	{
		return new AmbigNameNode(this.source, getIdentifier());
	}
	
	@Override
	public AmbigNameNode clone(AstFactory af)
	{
		return (AmbigNameNode) af.AmbiguousNameNode(this.source, getIdentifier());
	}*/
}
