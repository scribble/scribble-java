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
package org.scribble.net.session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// FIXME: should be IO wrapper: should abstract sockets, shared memory, RMI, AMQP, etc etc
@Deprecated
public class SocketWrapper
{
	protected final Socket sock;
	protected final DataOutputStream dos;
	protected final DataInputStream dis;
	
	public SocketWrapper(Socket s) throws IOException
	{
		this.sock = s;
		this.dos = new DataOutputStream(s.getOutputStream());
		this.dis = new DataInputStream(s.getInputStream());
	}

	public void close() throws IOException
	{
		try
		{
			this.dis.close();
		}
		finally
		{
			try
			{
				this.dos.close();
			}
			finally
			{
				this.sock.close();
			}
		}
	}
	
	public Socket getSocket()
	{
		return this.sock;
	}
}
