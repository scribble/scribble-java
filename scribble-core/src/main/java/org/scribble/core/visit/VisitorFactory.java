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
package org.scribble.core.visit;

import java.util.Map;
import java.util.Set;

import org.scribble.core.job.Core;
import org.scribble.core.type.name.Role;
import org.scribble.core.visit.global.ConnectionChecker;
import org.scribble.core.visit.global.ExtChoiceConsistencyChecker;
import org.scribble.core.visit.global.GTypeInliner;
import org.scribble.core.visit.global.GTypeUnfolder;
import org.scribble.core.visit.global.InlinedProjector;
import org.scribble.core.visit.global.Projector;
import org.scribble.core.visit.global.RoleEnablingChecker;
import org.scribble.core.visit.local.InlinedExtChoiceSubjFixer;
import org.scribble.core.visit.local.ReachabilityChecker;

public interface VisitorFactory
{

	GTypeInliner GTypeInliner(Core core);

	GTypeUnfolder GTypeUnfolder(Core core);

	RoleEnablingChecker RoleEnablingChecker(Set<Role> rs);

	ExtChoiceConsistencyChecker ExtChoiceConsistencyChecker(
			Map<Role, Role> enabled);

	ConnectionChecker ConnectionChecker(Set<Role> rs, boolean implicit);

	InlinedProjector InlinedProjector(Core core, Role self);

	InlinedExtChoiceSubjFixer InlinedExtChoiceSubjFixer();

	ReachabilityChecker ReachabilityChecker();

	// CHECKME: deprecate?
	Projector Projector(Core core, Role self);

}
