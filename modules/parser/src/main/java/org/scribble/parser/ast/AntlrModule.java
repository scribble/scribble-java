package org.scribble.parser.ast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.ProtocolDecl;
import org.scribble.parser.AntlrConstants.AntlrNodeType;
import org.scribble.parser.ScribbleParser;
import org.scribble.parser.util.Util;
import org.scribble.sesstype.kind.ImportKind;
import org.scribble.sesstype.kind.NonProtocolKind;
import org.scribble.sesstype.kind.ProtocolKind;

public class AntlrModule
{
	public static final int MODULEDECL_CHILD_INDEX = 0;

	public static Module parseModule(ScribbleParser parser, CommonTree ct)
	{
		ModuleDecl md = (ModuleDecl) parser.parse(getModuleDeclChild(ct));
		List<ImportDecl<? extends ImportKind>> ids = new LinkedList<>();
		List<NonProtocolDecl<? extends NonProtocolKind>> ptds = new LinkedList<>();
		List<ProtocolDecl<? extends ProtocolKind>> pds = new LinkedList<>();
		for (CommonTree id : getImportDeclChildren(ct))
		{
			@SuppressWarnings("unchecked")
			ImportDecl<? extends ImportKind> tmp = (ImportDecl<? extends ImportKind>) parser.parse(id);
			ids.add(tmp);
		}
		for (CommonTree ptd : getDataTypeDeclChildren(ct))
		{
			@SuppressWarnings("unchecked")
			NonProtocolDecl<? extends NonProtocolKind> tmp = (NonProtocolDecl<? extends NonProtocolKind>) parser.parse(ptd);
			ptds.add(tmp);
		}
		for (CommonTree pd : getProtocolDeclChildren(ct))
		{
			@SuppressWarnings("unchecked")
			ProtocolDecl<? extends ProtocolKind> tmp = (ProtocolDecl<? extends ProtocolKind>) parser.parse(pd);
			pds.add(tmp);
		}
		return AstFactoryImpl.FACTORY.Module(md, ids, ptds, pds);
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
		List<CommonTree> children = Util.toCommonTreeList(ct.getChildren());
		return children.subList(1, children.size()).stream()
				.filter((c) -> tmp.contains(Util.getAntlrNodeType(c))).collect(Collectors.toList());
	}
}
