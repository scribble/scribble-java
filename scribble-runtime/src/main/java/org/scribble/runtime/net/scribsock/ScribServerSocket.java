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
import org.scribble.runtime.net.session.BinaryChannelEndpoint;
import org.scribble.runtime.net.session.SessionEndpoint;

public abstract class ScribServerSocket implements AutoCloseable
{
	public final int port;

	private boolean reg = false;
	
	public ScribServerSocket(int port) throws IOException
	{
		this.port = port;
	}
	
	//public abstract BinaryChannelEndpoint accept(MPSTEndpoint<?, ?> se) throws IOException;  // synchronize
	public abstract BinaryChannelEndpoint accept(SessionEndpoint<?, ?> se) throws IOException;  // synchronize
	
	public synchronized void bind() throws ScribbleRuntimeException
	{
		if (this.reg)
		{
			throw new ScribbleRuntimeException("Server socket already registered.");
		}
		this.reg = true;
	}
	
	public synchronized void unbind()
	{
		this.reg = false;
	}

	@Override
	public abstract void close();
}
