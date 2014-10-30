package scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;

import scribble2.ast.ArgumentInstantiationList;
import scribble2.ast.RoleInstantiationList;
import scribble2.ast.global.GlobalDo;
import scribble2.ast.name.ProtocolNameNodes;
import scribble2.ast.name.ScopeNode;
import scribble2.parser.AntlrTreeParser;
import scribble2.parser.ast.AntlrQualifiedName;
import scribble2.parser.ast.AntlrSimpleName;

public class AntlrGlobalDo
{
	public static final int SCOPE_CHILD_INDEX = 0;
	public static final int MEMBERNAME_CHILD_INDEX = 1;
	public static final int ARGUMENTLIST_CHILD_INDEX = 2;
	public static final int ROLEINSTANTIATIONLIST_CHILD_INDEX = 3;

	public static GlobalDo parseGlobalDo(AntlrModuleParser parser, CommonTree ct)
	{
		RoleInstantiationList ril = (RoleInstantiationList) parser.parse(getRoleInstantiationListChild(ct));
		ArgumentInstantiationList al = (ArgumentInstantiationList) parser.parse(getArgumentInstantiationListChild(ct));
		ProtocolNameNodes pnn = AntlrQualifiedName.toProtocolNameNodes(getProtocolNameChild(ct));
		if (!isScoped(ct))
		{
			return new GlobalDo(ct, ril, al, pnn);
		}
		ScopeNode scope = AntlrSimpleName.toScopeNode(getScopeChild(ct));
		return new GlobalDo(ct, scope, ril, al, pnn);
	}
	
	public static boolean isScoped(CommonTree ct)
	{
		return AntlrSimpleName.toScopeNode(ct) != null;
	}

	public static CommonTree getScopeChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(SCOPE_CHILD_INDEX);
	}

	public static CommonTree getProtocolNameChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(MEMBERNAME_CHILD_INDEX);
	}

	public static CommonTree getArgumentInstantiationListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ARGUMENTLIST_CHILD_INDEX);
	}

	public static CommonTree getRoleInstantiationListChild(CommonTree ct)
	{
		return (CommonTree) ct.getChild(ROLEINSTANTIATIONLIST_CHILD_INDEX);
	}
}
