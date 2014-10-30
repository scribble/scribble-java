package org.scribble2.model;


@Deprecated
public interface ScopedNode
{
	boolean isEmptyScope();  // false for interruptible (can be implicit but not empty)

	//Scope getScope();
	//String getScopeElement();

	//SimpleName getScopeElement();  // Distinguish simple and compound scope names? (as name kinds)
}
