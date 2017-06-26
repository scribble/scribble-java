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

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.Session;
import org.scribble.runtime.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class AcceptSocket<S extends Session, R extends Role> extends LinearSocket<S, R>
{
	//protected AcceptSocket(MPSTEndpoint<S, R> ep)
	protected AcceptSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}

	/*public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		use();
		this.ep.register(role, ss.accept());
	}*/

	protected void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		use();
		//this.se.accept(ss, role);  // FIXME: csat
		MPSTEndpoint.accept(this.se, ss, role);
	}
}
