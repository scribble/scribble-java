package org.scribble.ext.go.core.ast;


// ast here means "core syntax" of session types -- it does not link the actual Scribble source (cf. base ast classes)
public interface ParamCoreType
{
	boolean canEquals(Object o);
}
