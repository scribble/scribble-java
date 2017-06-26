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
package org.scribble.codegen.java.endpointapi;

import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.main.ScribbleException;

// Build a (top-level) type declaration for the API generation output
public abstract class StateChanTypeGen
{
	protected final StateChannelApiGenerator apigen;

	public StateChanTypeGen(StateChannelApiGenerator apigen)
	{
		this.apigen = apigen;
	}
	
	public abstract TypeBuilder generateType() throws ScribbleException;  // FIXME: APIGenerationException?
}
