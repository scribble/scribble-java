package org.scribble.parser.ast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
		//List<AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>>
		List<ProtocolDecl<? extends ProtocolKind>>
				pds = new LinkedList<>();
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
			/*AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>
					tmp = (AbstractProtocolDecl<? extends ProtocolHeader, ? extends ProtocolDefinition<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>>>) parser.parse(pd);*/
			ProtocolDecl<? extends ProtocolKind> tmp = (ProtocolDecl<? extends ProtocolKind>) parser.parse(pd);
			pds.add(tmp);
		}

		//return new Module(md, ids, ptds, pds);
		return AstFactoryImpl.FACTORY.Module(md, ids, ptds, pds);
	}

	/*public static ModuleName getFullModuleName(CommonTree ct)
	{
		CommonTree modname = AntlrModuleDecl.getModuleNameChild(getModuleDeclChild(ct));
		return AntlrQualifiedName.toModuleNameNodes(modname).toName();
	}*/

	public static CommonTree getModuleDeclChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MODULEDECL_CHILD_INDEX);
	}
	
	public static List<CommonTree> getImportDeclChildren(CommonTree ct)
	{
		return filterChildren(ct, AntlrNodeType.IMPORTMODULE, AntlrNodeType.IMPORTMEMBER);
	}

	/*public static List<CommonTree> getImportModules(CommonTree ct)
	{
		return filterChildren(ct, AntlrConstants.IMPORT_MODULE_NODE_TYPE);
	}

	public static List<CommonTree> getImportMembers(CommonTree ct)
	{
		return filterChildren(ct, AntlrConstants.IMPORT_MODULE_NODE_TYPE);
	}*/

	/*public static List<CommonTree> getPayloadTypeDeclChildren(CommonTree ct)
	{
		return filterChildren(ct, AntlrNodeType.PAYLOADTYPEDECL);
	}*/

	public static List<CommonTree> getDataTypeDeclChildren(CommonTree ct)
	{
		return filterChildren(ct, AntlrNodeType.PAYLOADTYPEDECL, AntlrNodeType.MESSAGESIGNATUREDECL);
	}
	
	public static List<CommonTree> getProtocolDeclChildren(CommonTree ct)
	{
		return filterChildren(ct, AntlrNodeType.GLOBALPROTOCOLDECL, AntlrNodeType.LOCALPROTOCOLDECL);
	}

	/*public static List<CommonTree> getGlobalProtocolDecls(CommonTree ct)
	{
		return filterChildren(ct, AntlrConstants.GLOBAL_PROTOCOL_DECL_NODE_TYPE);
	}

	public static List<CommonTree> getLocalProtocolDecls(CommonTree ct)
	{
		return filterChildren(ct, AntlrConstants.LOCAL_PROTOCOL_DECL_NODE_TYPE);
	}*/

	private static List<CommonTree> filterChildren(CommonTree ct, AntlrNodeType... types)
	{
		List<AntlrNodeType> tmp = Arrays.asList(types);
		List<CommonTree> filtered = new LinkedList<>();
		List<CommonTree> children = Util.toCommonTreeList(ct.getChildren());
		for (CommonTree child : children.subList(1, children.size()))
		{
			if (tmp.contains(Util.getAntlrNodeType(child)))
			{
				filtered.add(child);
			}
		}
		return filtered;
	}
}
