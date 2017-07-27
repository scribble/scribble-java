/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.ast.local;

import java.util.Set;

import org.scribble.ast.AstFactory;
import org.scribble.ast.InteractionNode;
import org.scribble.main.ScribbleException;
import org.scribble.type.Message;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;
import org.scribble.visit.context.ProjectedChoiceSubjectFixer;

// Alternatively to interface, use GlobalNode subclass with delegation to "super" base (e.g. Choice) classes
public interface LInteractionNode extends InteractionNode<Local>, LNode
{
	Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer);

	LInteractionNode merge(AstFactory af, LInteractionNode ln) throws ScribbleException;  // Merge currently does "nothing"; validation takes direct non-deterministic interpretation -- purpose of syntactic merge would be to convert non-det to "equivalent" safe det in certain sitations
	boolean canMerge(LInteractionNode ln);
	Set<Message> getEnabling();
}
