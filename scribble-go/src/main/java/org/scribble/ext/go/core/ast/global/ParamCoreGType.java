package org.scribble.ext.go.core.ast.global;

import java.util.Set;

import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.ParamCoreType;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.type.name.Role;


public interface ParamCoreGType extends ParamCoreType
{
	
	// FIXME: clarify, Role subj is used as "role name"
	ParamCoreLType project(ParamCoreAstFactory af, Role subj, Set<ParamRange> ranges) throws ParamCoreSyntaxException;
	
	Set<ParamRole> getParamRoles();
}
