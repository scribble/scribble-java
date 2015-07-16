package org.scribble.ast;

import org.scribble.sesstype.kind.ModuleMemberKind;
import org.scribble.sesstype.name.MemberName;

// Not an abstract class, NonProtocolDecl extends NameDeclNode (whereas ProtocolDecl uses ProtocolHeader for that)
public interface ModuleMember
{
	MemberName<? extends ModuleMemberKind> getFullMemberName(Module mod);  // Should not use ModuleContext -- i.e. works before ModuleContext is built (indeed, context building uses this)
}
