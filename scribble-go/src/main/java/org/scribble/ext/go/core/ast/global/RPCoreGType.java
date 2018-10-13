package org.scribble.ext.go.core.ast.global;

import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ext.go.core.ast.RPCoreAstFactory;
import org.scribble.ext.go.core.ast.RPCoreSyntaxException;
import org.scribble.ext.go.core.ast.RPCoreType;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.core.type.RPRoleVariant;
import org.scribble.ext.go.main.GoJob;
import org.scribble.ext.go.type.index.RPForeachVar;
import org.scribble.ext.go.util.Smt2Translator;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;


public interface RPCoreGType extends RPCoreType<Global>
{
	
	// context records nested foreach vars -- only accesses to top-level vars are valid
	boolean isWellFormed(GoJob job, Stack<Map<RPForeachVar, RPInterval>> context, GProtocolDecl gpd, Smt2Translator smt2t);

	// TODO: clarify, Role subj is used as "role name"
	//ParamCoreLType project(ParamCoreAstFactory af, Role subj, Set<ParamRange> ranges) throws ParamCoreSyntaxException;
	RPCoreLType project(RPCoreAstFactory af, RPRoleVariant subj) throws RPCoreSyntaxException;  // G proj r \vec{D}
	RPCoreLType project3(RPCoreAstFactory af, Set<Role> roles, Set<RPAnnotatedInterval> ivals, RPIndexedRole subj) throws RPCoreSyntaxException;  // G proj R \vec{C} r[z]
	
	Set<RPIndexedRole> getIndexedRoles();

	@Override
	RPCoreGType subs(RPCoreAstFactory af, RPCoreType<Global> old, RPCoreType<Global> neu);
}
