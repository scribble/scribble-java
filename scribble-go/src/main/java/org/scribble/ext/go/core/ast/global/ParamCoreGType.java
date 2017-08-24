package org.scribble.ext.go.core.ast.global;

import java.util.Set;

import org.scribble.ext.go.core.ast.ParamCoreAstFactory;
import org.scribble.ext.go.core.ast.ParamCoreSyntaxException;
import org.scribble.ext.go.core.ast.ParamCoreType;
import org.scribble.ext.go.core.ast.ParamRole;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.type.name.Role;


public interface ParamCoreGType extends ParamCoreType
{
	
	ParamCoreLType project(ParamCoreAstFactory af, Role subj) throws ParamCoreSyntaxException;
	
	Set<ParamRole> getParamRoles();
}
