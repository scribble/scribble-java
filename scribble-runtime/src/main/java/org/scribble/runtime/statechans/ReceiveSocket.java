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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.message.ScribMessage;
import org.scribble.runtime.session.Session;
import org.scribble.runtime.session.SessionEndpoint;
import org.scribble.type.name.Role;

public abstract class ReceiveSocket<S extends Session, R extends Role> extends LinearSocket<S, R>
{
	private CompletableFuture<ScribMessage> fut;

	protected ReceiveSocket(SessionEndpoint<S, R> se)
	{
		super(se);
	}

	protected ScribMessage readScribMessage(Role peer) throws ClassNotFoundException, IOException, ScribbleRuntimeException
	{
		try
		{
			//ScribMessage m = this.ep.smf.readMessage(this.ep.getSocketEndpoint(role).dis);
			ScribMessage m = getFuture(peer).get();

			//System.out.println("Read: " + m);

			return m;
		}
		catch (InterruptedException e)
		{
			throw new IOException(e);
		}
		catch (ExecutionException e)
		{	
			// The Future converts the RuntimeScribbleException wrapper for the underlying IOException into an ExecutionException
			throw new IOException(e);
		}
	}
	
	protected boolean isDone(Role peer)
	{
		//return !this.ep.getInputQueues().isEmpty(peer);
		return (this.fut == null) || (this.fut.isDone());
	}
	
	protected CompletableFuture<ScribMessage> getFuture(Role peer) throws ScribbleRuntimeException
	{
		use();
		//this.fut = this.ep.getInputQueues().getFuture(peer);
		return this.se.getChannelEndpoint(peer).getFuture();
	}
}
