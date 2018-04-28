package org.scribble.ext.go.core.ast.global;

import java.util.Set;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.type.kind.Global;


public interface RPCoreGType extends RPCoreType<Global>
{
	
	boolean isWellFormed(GoJob job, GProtocolDecl gpd);

	// FIXME: clarify, Role subj is used as "role name"
	//ParamCoreLType project(ParamCoreAstFactory af, Role subj, Set<ParamRange> ranges) throws ParamCoreSyntaxException;
	RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj) throws RPCoreSyntaxException;
	
	Set<RPIndexedRole> getIndexedRoles();

	@Override
	RPCoreGType subs(RPCoreAstFactory af, RPCoreType<Global> old, RPCoreType<Global> neu);
}
