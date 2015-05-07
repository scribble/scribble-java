package org.scribble2.model;

import org.scribble2.sesstype.kind.ScopeKind;
import org.scribble2.sesstype.name.Name;


// FIXME: move to types? uniform with Named
public interface ScopedNode
{
	boolean isEmptyScope();  // false for interruptible (can be implicit but not empty)

	//Scope getScope();
	//String getScopeElement();

	// SimpleName for Scope "elements" (Scope is a compound name with prefix)
	//SimpleName getScopeElement();  // Distinguish simple and compound scope names? (as name kinds)
	Name<ScopeKind> getScopeElement();  // Distinguish simple and compound scope names? (as name kinds)
}
