package org.scribble.ext.go.core.ast.global;

import java.util.Set;

import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.ParamCoreType;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamActualRole;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.type.kind.Global;


public interface ParamCoreGType extends ParamCoreType<Global>
{
	
	// FIXME: clarify, Role subj is used as "role name"
	//ParamCoreLType project(ParamCoreAstFactory af, Role subj, Set<ParamRange> ranges) throws ParamCoreSyntaxException;
	ParamCoreLType project(ParamCoreAstFactory af, ParamActualRole subj) throws ParamCoreSyntaxException;
	
	Set<ParamRole> getParamRoles();
}
