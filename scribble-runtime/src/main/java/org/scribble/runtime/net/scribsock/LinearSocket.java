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
import java.util.concurrent.Callable;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.session.BinaryChannelWrapper;
import org.scribble.runtime.net.session.Session;
import org.scribble.runtime.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

// Not AutoClosable -- leave that to InitSocket
public abstract class LinearSocket<S extends Session, R extends Role> extends ScribSocket<S, R>
{
	private boolean used = false;
	
	//protected LinearSocket(MPSTEndpoint<S, R> ep)
	protected LinearSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}
	
	protected boolean isUsed()
	{
		return this.used;
	}
	
	//protected synchronized void use() throws ScribbleRuntimeException
	protected void use() throws ScribbleRuntimeException
	{
		if (this.used)
		{
			throw new ScribbleRuntimeException("Linear socket resource already used: " + this.getClass());
		}
		this.used = true;
	}
	
	//protected  // FIXME: generate API operation
	public void wrapClient(Role peer, Callable<? extends BinaryChannelWrapper> cons) throws IOException, ScribbleRuntimeException 
	{
		//use();  // FIXME: should be use for proper API operation
		if (this.used)
		{
			throw new ScribbleRuntimeException("Linear socket resource already used: " + this.getClass());
		}
		try
		{
			this.se.reregister(peer, cons.call());
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

	// FIXME: refactor
	// FIXME: State supertype of T
	public static <T> T wrapClient(T s, Role peer, Callable<? extends BinaryChannelWrapper> cons) throws IOException, ScribbleRuntimeException 
	{
		((LinearSocket<?, ?>) s).wrapClient(peer, cons);
		return s;
	}

	protected void wrapServer() 
	{
		throw new RuntimeException("TODO");
	}

	/*// Only triggered by autoclose or explicit close, i.e. not called directly by user
	protected synchronized void close() throws ScribbleRuntimeException
	{
		if (!this.used)
		{
			this.ep.close();
			throw new ScribbleRuntimeException("Socket resource not used: " + this.getClass());
		}
	}*/

	/*// FIXME: factor out transport parameter
	// FIXME: close old socket -- handle old receive thread (consider messsages still arriving -- maybe only support reconnect for states where this is not possible)
	// FIXME: synch new receive thread with old one if messages are allowed to still arrive on old one
	public void reconnect(Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		// Can connect unlimited, as long as not already used via init
		if (this.used)
		{
			throw new ScribbleRuntimeException("Linear socket resource already used: " + this.getClass());
		}
		SSLSocketFactory fact = (SSLSocketFactory) SSLSocketFactory.getDefault();
		Socket s1 = this.ep.getSocketEndpoint(role).getSocketWrapper().getSocket();  // FIXME: check already connected
		SSLSocket s2 = (SSLSocket) fact.createSocket(s1, s1.getInetAddress().getHostAddress(), s1.getPort(), true);
		this.ep.register(role, new SocketWrapper(s2));  // Replaces old SocketEndpoint  // FIXME: tidy up
	}*/
}
