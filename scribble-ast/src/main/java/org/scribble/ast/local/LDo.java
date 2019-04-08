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
import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Do;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.core.lang.context.ModuleContext;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.LProtocolName;
import org.scribble.lang.LangContext;

public class LDo extends Do<Local> implements LSimpleSessionNode
{
	// ScribTreeAdaptor#create constructor
	public LDo(Token t)
	{
		super(t);
	}

	// Tree#dupNode constructor
	public LDo(LDo node)
	{
		super(node);
	}

	@Override
	public LProtocolNameNode getProtocolNameNode()
	{
		return (LProtocolNameNode) getChild(2);
	}
	
	@Override
	public LDo dupNode()
	{
		return new LDo(this);
	}

	@Override
	public LProtocolName getTargetProtocolDeclFullName(ModuleContext mcontext)
	{
		return (LProtocolName) super.getTargetProtocolDeclFullName(mcontext);
	}

	@Override
	public LProtocolDecl getTargetProtocolDecl(LangContext jcontext,
			ModuleContext mcontext)
	{
		return (LProtocolDecl) super.getTargetProtocolDecl(jcontext, mcontext);
	}
	
	
	
	
	
	
	

	
	
	
	
	
	public LDo(CommonTree source, RoleArgList roleinstans, NonRoleArgList arginstans, LProtocolNameNode proto)
	{
		super(source, roleinstans, arginstans, proto);
	}

	/*@Override
	protected LDo copy()
	{
		return new LDo(this.source, this.roles, this.args, getProtocolNameNode());
	}
	
	@Override
	public LDo clone(AstFactory af)
	{
		RoleArgList roles = this.roles.clone(af);
		NonRoleArgList args = this.args.clone(af);
		LProtocolNameNode proto = this.getProtocolNameNode().clone(af);
		return af.LDo(this.source, roles, args, proto);
	}
	
	@Override
	public LDo reconstruct(RoleArgList roles, NonRoleArgList args, ProtocolNameNode<Local> proto)
	{
		ScribDel del = del();
		LDo ld = new LDo(this.source, roles, args, (LProtocolNameNode) proto);
		ld = (LDo) ld.del(del);
		return ld;
	}*/
}
