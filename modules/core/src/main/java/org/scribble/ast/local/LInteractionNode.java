package org.scribble.ast.local;

import java.util.Set;

import org.scribble.ast.InteractionNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface LInteractionNode extends InteractionNode<Local>, LNode
{
	Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer);

	LInteractionNode merge(LInteractionNode ln) throws ScribbleException;
	boolean canMerge(LInteractionNode ln);
	Set<Message> getEnabling();
}
