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
package org.scribble.runtime.session;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

import org.scribble.core.type.name.Role;
import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.message.ScribMessageFormatter;
import org.scribble.runtime.net.BinaryChannelEndpoint;
import org.scribble.runtime.net.ScribServerSocket;

// FIXME: factor out between role-endpoint based socket and channel-endpoint sockets
//.. initiator and joiner endpoints
public class MPSTEndpoint<S extends Session, R extends Role> extends SessionEndpoint<S, R>
{
	public MPSTEndpoint(S sess, R self, ScribMessageFormatter smf) throws IOException, ScribRuntimeException
	{
		super(sess, self, smf);
	}

	public void request(Role role, Callable<? extends BinaryChannelEndpoint> cons, String host, int port) throws ScribRuntimeException, UnknownHostException, IOException
	{
		MPSTEndpoint.request(this, role, cons, host, port);
	}

	// HACK FIXME: refactor (location, protected, etc)
	public static void request(SessionEndpoint<?, ?> se, Role role, Callable<? extends BinaryChannelEndpoint> cons, String host, int port) throws ScribRuntimeException, UnknownHostException, IOException
	{
		// Can connect unlimited, as long as not already used via init
		//if (this.init)
		if (se.init)
		{
			//throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
			throw new ScribRuntimeException("Socket already initialised: " + se.getClass());
		}
		//if (this.chans.containsKey(role))
		if (se.chans.containsKey(role))
		{
			throw new ScribRuntimeException("Already connected to: " + role);
		}
		try
		{
			BinaryChannelEndpoint c = cons.call();
			/*c.initClient(this, host, port);
			register(role, c);*/
			c.initClient(se, host, port);
			se.register(role, c);
		}
		catch (Exception e)
		{
			if (e instanceof IOException)
			{
				throw (IOException) e;
			}
			throw new IOException(e);
		}
	}

	public void accept(ScribServerSocket ss, Role role) throws IOException, ScribRuntimeException
	{
		accept(this, ss, role);
	}

	//public static void accept(SessionEndpoint<?, ?> se, ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	public static void accept(SessionEndpoint<?, ?> se, ScribServerSocket ss, Role role) throws IOException, ScribRuntimeException
	{
		//if (this.init)
		if (se.init)
		{
			//throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
			throw new ScribRuntimeException("Socket already initialised: " + se.getClass());
		}
		//if (this.chans.containsKey(role))
		if (se.chans.containsKey(role))
		{
			throw new ScribRuntimeException("Already connected to: " + role);
		}
		//register(role, ss.accept(this));  // FIXME: serv map in SessionEndpoint not currently used
		se.register(role, ss.accept(se));  // FIXME: serv map in SessionEndpoint not currently used
	}
	
	public void disconnect(Role role) throws IOException, ScribRuntimeException
	{
		MPSTEndpoint.disconnect(this, role);
	}

	public static void disconnect(SessionEndpoint<?, ?> se, Role role) throws IOException, ScribRuntimeException
	{
		//if (!this.chans.containsKey(role))
		if (!se.chans.containsKey(role))
		{
			throw new ScribRuntimeException("Not connected to: " + role);
		}
		//deregister(role);
		se.deregister(role);
	}
}
