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
import org.scribble.ast.name.simple.DataParamNode;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.name.DataName;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class DataParamDecl extends NonRoleParamDecl<DataKind>
{
	// ScribTreeAdaptor#create constructor
	public DataParamDecl(Token t)
	{
		super(t, DataKind.KIND);
	}

	// Tree#dupNode constructor
	public DataParamDecl(DataParamDecl node)
	{
		super(node);
	}
	
	@Override
	public DataParamNode getNameNodeChild()
	{
		return (DataParamNode) getRawNameNodeChild();
	}

	@Override
	public DataParamDecl dupNode()
	{
		return new DataParamDecl(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.DataParamDecl(this);
	}

	@Override
	public DataName getDeclName()
	{
		return (DataName) getNameNodeChild().toName();
	}
	
	@Override
	public String getKeyword()
	{
		return Constants.DATA_KW;
	}
}
