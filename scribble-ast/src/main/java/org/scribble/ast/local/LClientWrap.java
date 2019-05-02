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

import org.antlr.runtime.Token;
import org.scribble.ast.BasicInteraction;
import org.scribble.ast.global.GWrap;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.core.type.kind.Local;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;
import org.scribble.util.ScribException;
import org.scribble.visit.AstVisitor;

// TODO: factor out base Wrap (cf. Disconnect)
public class LClientWrap extends BasicInteraction<Local> // Wrap, ConnectionAction?
		implements LSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LClientWrap(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LClientWrap(LClientWrap node)
	{
		super(node);
	}
	
	// TOOD: factor out a base
	public RoleNode getClientChild()
	{
		return (RoleNode) getChild(GWrap.CLIENT_CHILD_INDEX);  // Assuming local will inherit both sides from some base (cf. base ConnectAction)
	}

	public RoleNode getServerChild()
	{
		return (RoleNode) getChild(GWrap.SERVER_CHILD_INDEX);
	}

	// "add", not "set"
	public void addScribChildren(RoleNode client, RoleNode server)
	{
		// Cf. above getters and Scribble.g children order
		addChild(client);
		addChild(server);
	}
	
	@Override
	public LClientWrap dupNode()
	{
		return new LClientWrap(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.LClientWrap(this);
	}

	// TODO: factor out a base
	public LClientWrap reconstruct(RoleNode client, RoleNode server)
	{
		LClientWrap n = dupNode();
		n.addScribChildren(client, server);
		n.setDel(del());  // No copy
		return n;
	}

	// TODO: factor out a base
	@Override
	public LClientWrap visitChildren(AstVisitor nv) throws ScribException
	{
		RoleNode src = (RoleNode) visitChild(getClientChild(), nv);
		RoleNode dest = (RoleNode) visitChild(getClientChild(), nv);
		return reconstruct(src, dest);
	}

	@Override
	public String toString()
	{
		return Constants.WRAP_KW + " " + Constants.TO_KW + " " + getClientChild()
				+ ";";
	}
}
