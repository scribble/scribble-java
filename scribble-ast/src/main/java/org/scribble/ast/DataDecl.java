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
import org.scribble.ast.name.qualified.DataNameNode;
import org.scribble.core.type.kind.DataKind;
import org.scribble.core.type.name.DataName;
import org.scribble.core.type.name.ModuleName;
import org.scribble.del.DelFactory;
import org.scribble.util.Constants;

public class DataDecl extends NonProtoDecl<DataKind>
{
	// ScribTreeAdaptor#create constructor
	public DataDecl(Token payload)
	{
		super(payload);
	}

	// Tree#dupNode constructor
	protected DataDecl(DataDecl node)
	{
		super(node);
	}

	@Override
	public DataNameNode getNameNodeChild()
	{
		return (DataNameNode) getRawNameNodeChild();
	}

	// Cf. CommonTree#dupNode
	@Override
	public DataDecl dupNode()
	{
		return new DataDecl(this);
	}
	
	@Override
	public void decorateDel(DelFactory df)
	{
		df.DataDecl(this);
	}
	
	@Override
	public boolean isDataDecl()
	{
		return true;
	}

  // Simple name (ModuleDecl is the only NameDeclNode that uses qualified names)
	@Override
	public DataName getDeclName()
	{
		return getNameNodeChild().toName();
	}

	@Override
	public DataName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new DataName(fullmodname, getDeclName());
	}

	@Override
	public String toString()
	{
		return Constants.DATA_KW + " <" + getSchemaChild() + "> "
				+ getExtNameChild() + " " 
				+ Constants.FROM_KW + " " + getExtSourceChild() + " " 
				+ Constants.AS_KW + " " + getDeclName()
				+ ";";
	}
}
