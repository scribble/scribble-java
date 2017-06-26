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

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.MemberNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.DataTypeKind;
import org.scribble.sesstype.name.DataType;
import org.scribble.sesstype.name.ModuleName;

public class DataTypeDecl extends NonProtocolDecl<DataTypeKind>
{
	public DataTypeDecl(CommonTree source, String schema, String extName, String extSource, DataTypeNode name)
	{
		super(source, schema, extName, extSource, name);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new DataTypeDecl(this.source, this.schema, this.extName, this.extSource, getNameNode());
	}
	
	@Override
	public DataTypeDecl clone(AstFactory af)
	{
		DataTypeNode name = (DataTypeNode) this.name.clone(af);
		return af.DataTypeDecl(this.source, this.schema, this.extName, this.extSource, name);
	}

	@Override
	public DataTypeDecl reconstruct(String schema, String extName, String extSource, MemberNameNode<DataTypeKind> name)
	{
		ScribDel del = del();
		DataTypeDecl dtd = new DataTypeDecl(this.source, schema, extName, extSource, (DataTypeNode) name);
		dtd = (DataTypeDecl) dtd.del(del);
		return dtd;
	}
	
	@Override
	public boolean isDataTypeDecl()
	{
		return true;
	}

	@Override
	public DataTypeNode getNameNode()
	{
		return (DataTypeNode) super.getNameNode();
	}

	@Override
	public DataType getDeclName()
	{
		return (DataType) super.getDeclName();
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
		return Constants.TYPE_KW + " <" + this.schema + "> " + this.extName
				+ " " + Constants.FROM_KW + " " + this.extSource + " "
				+ Constants.AS_KW + " " + this.name + ";";
	}
}
