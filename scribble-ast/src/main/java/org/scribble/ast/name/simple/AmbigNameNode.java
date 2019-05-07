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
import org.scribble.ast.MsgNode;
import org.scribble.ast.name.PayElemNameNode;
import org.scribble.core.type.kind.AmbigKind;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.kind.NonRoleArgKind;
import org.scribble.core.type.name.AmbigName;
import org.scribble.core.type.name.PayElemType;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.Msg;
import org.scribble.del.DelFactory;

// Primitive payload type, MessageSigName or parameter names only: if name is parsed as a CompoundNameNodes, it must be a payload type (not ambiguous in this case)
public class AmbigNameNode extends SimpleNameNode<AmbigKind>
		implements MsgNode, PayElemNameNode<DataKind>  // FIXME: currently hardcoded to DataTypeKind for payload elems ?
{
	// Scribble.g, IDENTIFIER<...Node>[$IDENTIFIER]
	// N.B. ttype (an "imaginary node" type) is discarded, t is a ScribbleParser.ID token type
	public AmbigNameNode(int ttype, Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	protected AmbigNameNode(AmbigNameNode node)
	{
		super(node);
	}
	
	@Override
	public AmbigNameNode dupNode()
	{
		return new AmbigNameNode(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.AmbigNameNode(this);
	}
	
	@Override
	public Arg<? extends NonRoleArgKind> toArg()
	{
		throw new RuntimeException(
				"Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public Msg toMsg()
	{
		throw new RuntimeException(
				"Ambiguous name node not disambiguated: " + this);
	}

	@Override
	public PayElemType<DataKind> toPayloadType()  // As a payload elem, currently hardcoded to expect only DataTypeKind (protocol payloads not supported)
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
		return ((AmbigNameNode) o).canEquals(this) && super.equals(o);
	}
	
	@Override
	public boolean canEquals(Object o)
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
}
