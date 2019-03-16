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

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.type.kind.SigKind;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.ModuleName;

public class MessageSigNameDecl extends NonProtocolDecl<SigKind>
{
	// ScribTreeAdaptor#create constructor
	public MessageSigNameDecl(Token payload)
	{
		super(payload);
	}

	// Tree#dupNode constructor
	protected MessageSigNameDecl(MessageSigNameDecl node)
	{
		super(node);
	}

	// Cf. CommonTree#dupNode
	@Override
	public MessageSigNameDecl dupNode()
	{
		return new MessageSigNameDecl(this);
	}
	
	@Override
	public MessageSigNameNode getNameNodeChild()
	{
		return (MessageSigNameNode) getRawNameNodeChild();
	}
	
	@Override
	public boolean isMessageSigNameDecl()
	{
		return true;
	}

	@Override
	public MessageSigName getDeclName()
	{
		return getNameNodeChild().toName();
	}

	@Override
	public MessageSigName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new MessageSigName(fullmodname, getDeclName());
	}

	@Override
	public String toString()
	{
		return Constants.SIG_KW + " <" + getSchemaNodeChild() + "> "
				+ getExtNameNodeChild() + " " 
				+ Constants.FROM_KW + " " + getExtSourceNodeChild() + " " 
				+ Constants.AS_KW + " " + getDeclName()
				+ ";";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public MessageSigNameDecl(CommonTree source, String schema, String extName, String extSource, MessageSigNameNode name)
	{
		super(source, schema, extName, extSource, name);
	}

	/*@Override
	protected MessageSigNameDecl copy()
	{
		return new MessageSigNameDecl(this.source, this.schema, this.extName, this.extSource, getNameNode());
	}
	
	@Override
	public MessageSigNameDecl clone(AstFactory af)
	{
		MessageSigNameNode name = (MessageSigNameNode) this.name.clone(af);
		return af.MessageSigNameDecl(this.source, this.schema, this.extName, this.extSource, name);
	}*/
}
