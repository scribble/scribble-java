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

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.BinaryChannelEndpoint;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.session.Session;
import org.scribble.type.name.Role;

// Establishing transport connections handled in here and wrapped up in SocketWrapper
@Deprecated
public abstract class InitSocket<S extends Session, R extends Role> extends LinearSocket<S, R> implements AutoCloseable
{
	protected InitSocket(MPSTEndpoint<S, R> se)
	{
		super(se);
	}

	public void connect(Callable<? extends BinaryChannelEndpoint> cons, Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		// Can connect unlimited, as long as not already used via init
		if (isUsed())
		{
			throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
		}
		try
		{
			BinaryChannelEndpoint c = cons.call();
			c.initClient(se, host, port);
			this.se.register(role, c);
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

	public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		//accept(null, role);
		if (isUsed())
		{
			throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
		}
		this.se.register(role, ss.accept(this.se));  // FIXME: serv map in SessionEndpoint not currently used
	}

	@Deprecated
	public void accept(Role role) throws IOException, ScribbleRuntimeException
	{
		if (isUsed())
		{
			throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
		}
		this.se.register(role, this.se.getSelfServerSocket().accept(this.se));
	}
	
	@Override
	public void close() throws ScribbleRuntimeException
	{
		try
		{
			this.se.close();
		}
		finally
		{
			if (!this.se.isCompleted())  // Subsumes use -- must be used for sess to be completed
			{
				throw new ScribbleRuntimeException("Session not completed: " + this.se.self);
			}
		}
	}
}
