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
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.type.kind.DataTypeKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.ModuleName;

public class DataTypeDecl extends NonProtocolDecl<DataTypeKind>
{
	// ScribTreeAdaptor#create constructor
	public DataTypeDecl(Token payload)
	{
		super(payload);
	}

	// Tree#dupNode constructor
	protected DataTypeDecl(DataTypeDecl node)
	{
		super(node);
	}

	// Cf. CommonTree#dupNode
	@Override
	public DataTypeDecl dupNode()
	{
		return new DataTypeDecl(this);
	}

	@Override
	public DataTypeNode getNameNodeChild()
	{
		return (DataTypeNode) getRawNameNodeChild();
	}
	
	@Override
	public boolean isDataTypeDecl()
	{
		return true;
	}

  // Simple name (ModuleDecl is the only NameDeclNode that uses qualified names)
	@Override
	public DataType getDeclName()
	{
		return getNameNodeChild().toName();
	}

	@Override
	public DataType getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new DataType(fullmodname, getDeclName());
	}

	@Override
	public String toString()
	{
		return Constants.TYPE_KW + " <" + getSchemaNodeChild() + "> "
				+ getExtNameNodeChild() + " " 
				+ Constants.FROM_KW + " " + getExtSourceNodeChild() + " " 
				+ Constants.AS_KW + " " + getDeclName()
				+ ";";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

	public DataTypeDecl(CommonTree source, String schema, String extName, String extSource, DataTypeNode name)
	{
		super(source, schema, extName, extSource, name);
	}
	
	/*@Override
	protected ScribNodeBase copy()
	{
		return new DataTypeDecl(this.source, this.schema, this.extName, this.extSource, getNameNode());
	}
	
	@Override
	public DataTypeDecl clone(AstFactory af)
	{
		DataTypeNode name = (DataTypeNode) this.name.clone(af);
		return af.DataTypeDecl(this.source, this.schema, this.extName, this.extSource, name);
	}*/
}
