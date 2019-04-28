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
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.SigName;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class SigParamDecl extends NonRoleParamDecl<SigKind>
{
	// ScribTreeAdaptor#create constructor
	public SigParamDecl(Token t)
	{
		super(t, SigKind.KIND);
	}

	// Tree#dupNode constructor
	public SigParamDecl(SigParamDecl node)
	{
		super(node);
	}
	
	@Override
	public SigParamNode getNameNodeChild()
	{
		return (SigParamNode) getRawNameNodeChild();  // CHECKME: make Type/Sig(Param)Node?
	}
	
	@Override
	public SigParamDecl dupNode()
	{
		return new SigParamDecl(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.SigParamDecl(this);
	}

	@Override
	//public MemberName<SigKind> getDeclName()
	public SigName getDeclName()
	{
		return (SigName) getNameNodeChild().toName();
	}
	
	@Override
	public String getKeyword()
	{
		return Constants.SIG_KW;
	}
}
