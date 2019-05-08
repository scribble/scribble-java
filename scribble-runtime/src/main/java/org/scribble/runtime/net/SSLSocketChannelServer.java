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
package org.scribble.runtime.net;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

import org.scribble.runtime.session.SessionEndpoint;

// FIXME:
public class SSLSocketChannelServer extends ScribServerSocket
{
	private ServerSocketChannel ss;
	
	public SSLSocketChannelServer(int port) throws IOException
	{
		super(port);
		throw new RuntimeException("TODO");
		/*this.ss = ServerSocketChannel.open();
		this.ss.socket().bind(new InetSocketAddress(port));*/
	}

	@Override
	//public synchronized SocketChannelEndpoint accept(MPSTEndpoint<?, ?> se) throws IOException
	public synchronized SocketChannelEndpoint accept(SessionEndpoint<?, ?> se) throws IOException
	{
		return new SocketChannelEndpoint(se, ss.accept());
	}

	@Override
	public void close()
	{
		try
		{
			this.ss.close();
		}
		catch (IOException e)
		{
			// FIXME
			e.printStackTrace();
		}
	}
}
