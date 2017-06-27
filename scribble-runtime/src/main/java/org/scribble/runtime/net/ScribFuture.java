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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// P is payload type as an array
public abstract class ScribFuture //implements Future<P>
{
	private CompletableFuture<ScribMessage> future;
	private ScribMessage m;
	
	public ScribFuture(CompletableFuture<ScribMessage> future)
	{
		this.future = future;
	}
	
	// Not synch: can return false while get is blocked
	public boolean isDone()
	{
		return this.m != null;
	}

	protected synchronized ScribMessage get() throws IOException
	{
		try
		{
			if (isDone())
			{
				return this.m;
			}
			this.m = this.future.get();

			//System.out.println("Got: " + m);
			
			return this.m;
		}
		catch (InterruptedException | ExecutionException e)
		{
			throw new IOException(e);
		}
	}
	
	public abstract ScribFuture sync() throws IOException; //ExecutionException, InterruptedException;  // sync returns the Future (cf. get returns the val)

	/*@Override
	public P get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException
	{
		ScribMessage m = this.future.get(timeout, unit);
		return (P) m.payload.clone()[0];  // FIXME: exactly one payload
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		return this.future.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled()
	{
		return this.future.isCancelled();
	}

	@Override
	public boolean isDone()
	{
		return this.future.isDone();
	}*/
}
