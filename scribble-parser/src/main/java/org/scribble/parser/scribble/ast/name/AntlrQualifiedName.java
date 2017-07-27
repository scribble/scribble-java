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
package org.scribble.parser.scribble.ast.name;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.LProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.type.kind.DataTypeKind;
import org.scribble.type.kind.Global;
import org.scribble.type.kind.Local;
import org.scribble.type.kind.ModuleKind;
import org.scribble.type.kind.SigKind;

public class AntlrQualifiedName
{
	protected static String[] getElements(CommonTree ct)
	{
		int count = ct.getChildCount();
		String[] names = new String[count];
		for (int i = 0; i < count; i++)
		{
			names[i] = AntlrSimpleName.getName((CommonTree) ct.getChild(i));
		}
		return names;
	}
	
	public static ModuleNameNode toModuleNameNode(CommonTree ct, AstFactory af)
	{
		return (ModuleNameNode) af.QualifiedNameNode(ct, ModuleKind.KIND, getElements(ct));
	}

	public static DataTypeNode toDataTypeNameNode(CommonTree ct, AstFactory af)
	{
		return (DataTypeNode) af.QualifiedNameNode(ct, DataTypeKind.KIND, getElements(ct));
	}

	public static MessageSigNameNode toMessageSigNameNode(CommonTree ct, AstFactory af)
	{
		return (MessageSigNameNode) af.QualifiedNameNode(ct, SigKind.KIND, getElements(ct));
	}

	public static GProtocolNameNode toGProtocolNameNode(CommonTree ct, AstFactory af)
	{
		return (GProtocolNameNode) af.QualifiedNameNode(ct, Global.KIND, getElements(ct));
	}

	public static LProtocolNameNode toLProtocolNameNode(CommonTree ct, AstFactory af)
	{
		return (LProtocolNameNode) af.QualifiedNameNode(ct, Local.KIND, getElements(ct));
	}
}
