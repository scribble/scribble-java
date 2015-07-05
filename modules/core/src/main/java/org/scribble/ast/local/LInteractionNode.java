package org.scribble.ast.local;

import org.scribble.ast.InteractionNode;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ProjectedChoiceSubjectFixer;


// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface LInteractionNode extends InteractionNode<Local>, LNode
{
	Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer);
}
