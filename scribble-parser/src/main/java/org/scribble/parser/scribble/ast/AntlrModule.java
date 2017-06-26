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
package org.scribble.parser.scribble.ast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.ProtocolDecl;
import org.scribble.parser.scribble.ScribParser;
import org.scribble.parser.scribble.ScribParserUtil;
import org.scribble.parser.scribble.AntlrConstants.AntlrNodeType;
import org.scribble.util.ScribParserException;

public class AntlrModule
{
	public static final int MODULEDECL_CHILD_INDEX = 0;

	public static Module parseModule(ScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		ModuleDecl md = (ModuleDecl) parser.parse(getModuleDeclChild(ct), af);
		List<ImportDecl<?>> ids = new LinkedList<>();
		List<NonProtocolDecl<?>> ptds = new LinkedList<>();
		List<ProtocolDecl<?>> pds = new LinkedList<>();
		for (CommonTree id : getImportDeclChildren(ct))
		{
			ImportDecl<?> tmp = (ImportDecl<?>) parser.parse(id, af);
			ids.add(tmp);
		}
		for (CommonTree ptd : getDataTypeDeclChildren(ct))
		{
			NonProtocolDecl<?> tmp = (NonProtocolDecl<?>) parser.parse(ptd, af);
			ptds.add(tmp);
		}
		for (CommonTree pd : getProtocolDeclChildren(ct))
		{
			ProtocolDecl<?> tmp = (ProtocolDecl<?>) parser.parse(pd, af);
			pds.add(tmp);
		}
		return af.Module(ct, md, ids, ptds, pds);
	}

	public static CommonTree getModuleDeclChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULEDECL_CHILD_INDEX);
	}
	
	public static List<CommonTree> getImportDeclChildren(CommonTree ct)
	{
		return filterChildren(ct, AntlrNodeType.IMPORTMODULE, AntlrNodeType.IMPORTMEMBER);
	}

	public static List<CommonTree> getDataTypeDeclChildren(CommonTree ct)
	{
		return filterChildren(ct, AntlrNodeType.PAYLOADTYPEDECL, AntlrNodeType.MESSAGESIGNATUREDECL);
	}
	
	public static List<CommonTree> getProtocolDeclChildren(CommonTree ct)
	{
		return filterChildren(ct, AntlrNodeType.GLOBALPROTOCOLDECL, AntlrNodeType.LOCALPROTOCOLDECL);
	}

	private static List<CommonTree> filterChildren(CommonTree ct, AntlrNodeType... types)
	{
		List<AntlrNodeType> tmp = Arrays.asList(types);
		List<CommonTree> children = ScribParserUtil.toCommonTreeList(ct.getChildren());
		return children.subList(1, children.size()).stream()
				.filter((c) -> tmp.contains(ScribParserUtil.getAntlrNodeType(c))).collect(Collectors.toList());
	}
}
