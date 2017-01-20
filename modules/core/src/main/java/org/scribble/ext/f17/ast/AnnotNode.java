package org.scribble.ext.f17.ast;

public interface AnnotNode
{
	default boolean isAnnotated()
	{
		return false;
	}

	ScribAnnot getAnnotation();
}
