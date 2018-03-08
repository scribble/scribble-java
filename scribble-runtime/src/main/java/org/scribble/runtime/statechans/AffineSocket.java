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
package org.scribble.runtime.statechans;

import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.session.Session;
import org.scribble.type.name.Role;

@Deprecated
public abstract class AffineSocket<S extends Session, R extends Role> extends LinearSocket<S, R>
{
	protected AffineSocket(MPSTEndpoint<S, R> ep)
	{
		super(ep);
	}

	/*@Override
	protected void close() throws ScribbleRuntimeException
	{
		this.ep.close();  // No used check
	}*/
}
