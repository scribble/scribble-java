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
package org.scribble.visit;

import org.scribble.ast.ProtocolDecl;
import org.scribble.main.Job;
import org.scribble.visit.env.DummyEnv;

public abstract class NoEnvUnfoldingVisitor extends UnfoldingVisitor<DummyEnv>
{
	public NoEnvUnfoldingVisitor(Job job)
	{
		super(job);
	}

	@Override
	protected final DummyEnv makeRootProtocolDeclEnv(ProtocolDecl<?> pd)
	{
		return DummyEnv.DUMMY;
	}
}
