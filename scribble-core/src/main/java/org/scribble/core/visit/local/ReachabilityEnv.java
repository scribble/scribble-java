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
package org.scribble.core.visit.local;

import java.util.Collections;
import java.util.Set;

import org.scribble.core.type.name.RecVar;

public class ReachabilityEnv
{
	public final boolean postcont;  // For checking bad sequencing of unreachable code: false after a continue; true if choice has an exit (false inherited for all other constructs)
	public final Set<RecVar> recvars;  // For checking "reachable code" also satisfies tail recursion (e.g., after choice-with-exit)

	public ReachabilityEnv(boolean postcont, Set<RecVar> recvars)
	{
		this.postcont = postcont;
		this.recvars = Collections.unmodifiableSet(recvars);
	}

	public boolean isSeqable()
	{
		return !this.postcont && this.recvars.isEmpty();
	}
}
