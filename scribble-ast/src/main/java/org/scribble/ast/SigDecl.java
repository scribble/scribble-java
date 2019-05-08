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
import org.scribble.ast.name.qualified.SigNameNode;
import org.scribble.core.type.kind.SigKind;
import org.scribble.core.type.name.ModuleName;
import org.scribble.core.type.name.SigName;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class SigDecl extends NonProtoDecl<SigKind>
{
	// ScribTreeAdaptor#create constructor
	public SigDecl(Token payload)
	{
		super(payload);
	}

	// Tree#dupNode constructor
	protected SigDecl(SigDecl node)
	{
		super(node);
	}
	
	@Override
	public SigNameNode getNameNodeChild()
	{
		return (SigNameNode) getRawNameNodeChild();
	}

	// Cf. CommonTree#dupNode
	@Override
	public SigDecl dupNode()
	{
		return new SigDecl(this);
	}

	@Override
	public void decorateDel(DelFactory df)
	{
		df.SigDecl(this);
	}
	
	@Override
	public boolean isSigDecl()
	{
		return true;
	}

	@Override
	public SigName getDeclName()
	{
		return getNameNodeChild().toName();
	}

	@Override
	public SigName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new SigName(fullmodname, getDeclName());
	}

	@Override
	public String toString()
	{
		return Constants.SIG_KW + " <" + getSchemaChild() + "> "
				+ getExtNameChild() + " " 
				+ Constants.FROM_KW + " " + getExtSourceChild() + " " 
				+ Constants.AS_KW + " " + getDeclName()
				+ ";";
	}
}
