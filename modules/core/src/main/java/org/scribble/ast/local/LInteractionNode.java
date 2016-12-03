package org.scribble.ast.local;

import java.util.Set;

import org.scribble.ast.InteractionNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface LInteractionNode extends InteractionNode<Local>, LNode
{
	Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer);

	LInteractionNode merge(LInteractionNode ln) throws ScribbleException;  // Merge currently does "nothing"; validation takes direct non-deterministic interpretation -- purpose of syntactic merge would be to convert non-det to "equivalent" safe det in certain sitations
	boolean canMerge(LInteractionNode ln);
	Set<Message> getEnabling();
}
