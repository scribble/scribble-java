package org.scribble.ext.go.parser.scribble.ast;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ImportDecl;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonProtocolDecl;
import org.scribble.ast.ProtocolDecl;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.AntlrToScribParserUtil;
import org.scribble.parser.scribble.ScribbleAntlrConstants;
import org.scribble.parser.scribble.ast.AntlrModule;
import org.scribble.util.ScribParserException;

public class ParamCoreAntlrModule
{
	// Duplicated from AntlrModule
	public static Module parseModule(AntlrToScribParser parser, CommonTree ct, AstFactory af) throws ScribParserException
	{
		ModuleDecl md = (ModuleDecl) parser.parse(AntlrModule.getModuleDeclChild(ct), af);
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

	// FIXME: like this because AntlrNodeType is not conveniently extendable -- need to bypass AntlrToScribParserUtil#getAntlrNodeType
	private static final Set<String> IMPORTDECLS = Stream.of(ScribbleAntlrConstants.IMPORTMODULE_NODE_TYPE,
				ScribbleAntlrConstants.IMPORTMEMBER_NODE_TYPE).collect(Collectors.toSet());
	private static final Set<String> NONPROTOCOLDECLS = Stream.of(ScribbleAntlrConstants.PAYLOADTYPEDECL_NODE_TYPE,
				ScribbleAntlrConstants.MESSAGESIGNATUREDECL_NODE_TYPE, "PARAM_DELEGDECL").collect(Collectors.toSet());  // FIXME: factor out constant
	private static final Set<String> PROTOCOLDECLS = Stream.of(ScribbleAntlrConstants.GLOBALPROTOCOLDECL_NODE_TYPE,
				ScribbleAntlrConstants.LOCALPROTOCOLDECL_NODE_TYPE).collect(Collectors.toSet());
	
	public static List<CommonTree> getImportDeclChildren(CommonTree ct)
	{
		//return filterChildren(ct, AntlrNodeType.IMPORTMODULE, AntlrNodeType.IMPORTMEMBER);
		List<CommonTree> children = AntlrToScribParserUtil.toCommonTreeList(ct.getChildren());
		return children.subList(1, children.size()).stream()
				.filter(c -> IMPORTDECLS.contains(c.getToken().getText()))
				.collect(Collectors.toList());
	}

	public static List<CommonTree> getDataTypeDeclChildren(CommonTree ct)
	{
		List<CommonTree> children = AntlrToScribParserUtil.toCommonTreeList(ct.getChildren());
		return children.subList(1, children.size()).stream()
				.filter(c -> NONPROTOCOLDECLS.contains(c.getToken().getText()))
				.collect(Collectors.toList());
	}
	
	public static List<CommonTree> getProtocolDeclChildren(CommonTree ct)
	{
		//return filterChildren(ct, AntlrNodeType.GLOBALPROTOCOLDECL, AntlrNodeType.LOCALPROTOCOLDECL);
		List<CommonTree> children = AntlrToScribParserUtil.toCommonTreeList(ct.getChildren());
		return children.subList(1, children.size()).stream()
				.filter(c -> PROTOCOLDECLS.contains(c.getToken().getText()))
				.collect(Collectors.toList());
	}
}
