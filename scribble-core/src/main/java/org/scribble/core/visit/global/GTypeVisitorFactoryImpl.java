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
package org.scribble.core.visit.global;

import java.util.Map;
import java.util.Set;

import org.scribble.core.job.Core;
import org.scribble.core.type.name.Role;

public class GTypeVisitorFactoryImpl implements GTypeVisitorFactory
{

	@Override
	public GTypeInliner GTypeInliner(Core core)
	{
		return new GTypeInliner(core);
	}

	@Override
	public GTypeUnfolder GTypeUnfolder(Core core)
	{
		return new GTypeUnfolder(core);
	}

	@Override
	public RoleEnablingChecker RoleEnablingChecker(Set<Role> rs)
	{
		return new RoleEnablingChecker(rs);
	}

	@Override
	public ExtChoiceConsistencyChecker ExtChoiceConsistencyChecker(
			Map<Role, Role> enabled)
	{
		return new ExtChoiceConsistencyChecker(enabled);
	}

	@Override
	public ConnectionChecker ConnectionChecker(Set<Role> roles, boolean implicit)
	{
		return new ConnectionChecker(roles, implicit);
	}

	@Override
	public InlinedProjector InlinedProjector(Core core, Role self)
	{
		return new InlinedProjector(core, self);
	}

	// CHECKME: deprecate?
	@Override
	public SubprotoProjector Projector(Core core, Role self)
	{
		return new SubprotoProjector(core, self);
	}

}
