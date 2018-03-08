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

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.session.Session;
import org.scribble.runtime.session.SessionEndpoint;
import org.scribble.type.name.Role;

public abstract class EndSocket<S extends Session, R extends Role> extends ScribSocket<S, R>
{
	//public EndSocket(SessionEndpoint<S, R> se, boolean dummy)
	//protected EndSocket(MPSTEndpoint<S, R> se)
	protected EndSocket(SessionEndpoint<S, R> se)
	{
		super(se);
	}

	public void end() throws ScribbleRuntimeException
	{
		/*super.use();
		this.se.setCompleted();*/
		/*if (!this.se.isCompleted())  // Disabled for user implementations of the Handle interface -- No: unnecessary -- and if EndSocket has been returned, then session must be complete anyway...
		{
			throw new ScribbleRuntimeException("Session not completed: " + this.se);
		}*/
		this.se.setCompleted();
		this.se.close();
		//this.closed = true;
	}
	
	/*@Override
	public void close()
	{
		if (!this.closed)
		{
			this.ep.close();
		}
	}*/
}
