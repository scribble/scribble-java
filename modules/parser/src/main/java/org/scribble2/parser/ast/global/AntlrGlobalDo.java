package org.scribble2.parser.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble2.model.ArgumentInstantiationList;
import org.scribble2.model.ModelFactoryImpl;
import org.scribble2.model.RoleInstantiationList;
import org.scribble2.model.global.GlobalDo;
import org.scribble2.model.name.qualified.ProtocolNameNode;
import org.scribble2.model.name.simple.ScopeNode;
import org.scribble2.parser.AntlrModuleParser;
import org.scribble2.parser.ast.name.AntlrQualifiedName;
import org.scribble2.parser.ast.name.AntlrSimpleName;

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
		ProtocolNameNode pnn = AntlrQualifiedName.toProtocolNameNode(getProtocolNameChild(ct));
		if (!isScoped(ct))
		{
			//return new GlobalDo(ril, al, pnn);
			return ModelFactoryImpl.FACTORY.GlobalDo(ril, al, pnn);
		}
		ScopeNode scope = AntlrSimpleName.toScopeNode(getScopeChild(ct));
		//return new GlobalDo(scope, ril, al, pnn);
		return ModelFactoryImpl.FACTORY.GlobalDo(scope, ril, al, pnn);
	}
	
	public static boolean isScoped(CommonTree ct)
	{
		return AntlrSimpleName.toScopeNode(getScopeChild(ct)) != null;  // No scope recorded as null
		//return !AntlrSimpleName.toScopeNode(getScopeChild(ct)).toName().equals("NO_SCOPE");  // FIXME: constants
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
