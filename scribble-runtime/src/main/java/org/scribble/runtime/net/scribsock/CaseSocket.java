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
package org.scribble.runtime.net.scribsock;

import org.scribble.runtime.net.session.Session;
import org.scribble.runtime.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

// For "receiving" the payloads after a branch is done
public abstract class CaseSocket<S extends Session, R extends Role> extends LinearSocket<S, R>  // No I/O induced by this socket itself (i.e. not a ReceiveSocket)
{
	//protected CaseSocket(MPSTEndpoint<S, R> ep)
	protected CaseSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}
}
