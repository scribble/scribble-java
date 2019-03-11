package org.scribble.lang.global;

import java.util.Map;
import java.util.Set;

import org.scribble.job.ScribbleException;
import org.scribble.lang.SType;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.lang.local.LType;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;

public interface GType extends SType<Global>
{
	@Override
	GType substitute(Substitutions<Role> subs);

	@Override
	GType getInlined(STypeInliner i);//, Deque<SubprotoSig> stack);

	@Override
	SType<Global> unfoldAllOnce(STypeUnfolder<Global> u);  // Not GType return, o/w need to override again in GDo
	
	LType project(Role self);
	
	// Pre: use on inlined or later (unsupported for Do, also Protocol)
	// enabled treated immutably
	// Returns enabled post visiting
	Set<Role> checkRoleEnabling(Set<Role> enabled) throws ScribbleException; 
	
	// Pre: use on inlined or later (unsupported for Do, also Protocol)
	// Pre: checkRoleEnabling
	// enablers: enabled -> enabler -- treated immutably
	// Returns enablers post visiting
	Map<Role, Role> checkExtChoiceConsistency(Map<Role, Role> enablers)
			throws ScribbleException;
}

