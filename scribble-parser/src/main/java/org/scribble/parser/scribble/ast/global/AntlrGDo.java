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
package org.scribble.parser.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.global.GDo;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ast.name.AntlrQualifiedName;
import org.scribble.util.ScribParserException;

public class AntlrGDo
{
	public static final int MEMBERNAME_CHILD_INDEX = 0;
	public static final int ARGUMENTLIST_CHILD_INDEX = 1;
	public static final int ROLEINSTANTIATIONLIST_CHILD_INDEX = 2;

	public static GDo parseGDo(AntlrToScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		RoleArgList ril = (RoleArgList) parser.parse(getRoleArgListChild(ct), af);
		NonRoleArgList al = (NonRoleArgList) parser.parse(getNonRoleArgListChild(ct), af);
		GProtocolNameNode pnn = AntlrQualifiedName.toGProtocolNameNode(getProtocolNameChild(ct), af);
		return af.GDo(ct, ril, al, pnn);
	}
	
	public static CommonTree getProtocolNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MEMBERNAME_CHILD_INDEX);
	}

	public static CommonTree getNonRoleArgListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARGUMENTLIST_CHILD_INDEX);
	}

	public static CommonTree getRoleArgListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ROLEINSTANTIATIONLIST_CHILD_INDEX);
	}
}
