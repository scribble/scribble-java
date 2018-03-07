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
package org.scribble.runtime.net.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.ScribMessage;
import org.scribble.runtime.net.ScribMessageFormatter;
import org.scribble.runtime.net.state.ScribBranch;
import org.scribble.runtime.net.state.ScribEndState;
import org.scribble.runtime.net.state.ScribHandlerMessage;
import org.scribble.runtime.net.state.ScribInputState;
import org.scribble.runtime.net.state.ScribOutputState;
import org.scribble.runtime.net.state.ScribState;
import org.scribble.type.name.Role;

public class EventDrivenEndpoint<S extends Session, R extends Role> extends MPSTEndpoint<S, R>
{
	protected final ScribState init;
	protected final Map<ScribOutputState, Function<Object, ? extends ScribHandlerMessage>> outputs = new HashMap<>();
	protected final Map<ScribInputState, ScribBranch> inputs = new HashMap<>();

	public EventDrivenEndpoint(S sess, R self, ScribMessageFormatter smf, ScribState init) throws IOException, ScribbleRuntimeException
	{
		super(sess, self, smf);
		this.init = init;
	}
	
	/*public void register()
	{
		
	}*/
	
	public Future<Void> run() throws ScribbleRuntimeException
	{
		// FIXME: make linear
		
		Object[] res = new Object[1];

		new Thread()
		{
			private final EventDrivenEndpoint<S, R> edep;

			{
				this.edep = EventDrivenEndpoint.this;
			}

			public void run()
			{
				try
				{
					ScribState curr = this.edep.init;
					while (!(curr instanceof ScribEndState))
					{
						if (curr instanceof ScribOutputState)
						{
							ScribHandlerMessage m = this.edep.outputs.get(curr).apply(null);  // FIXME: state object
							getChannelEndpoint(m.getPeer()).write(new ScribMessage(m.getOp(), m.getPayload().toArray(new Object[0])));  // FIXME: ScribEvent has extra Role
							curr = ((ScribOutputState) curr).succs.get(m.getOp());
						}
						else if (curr instanceof ScribInputState)
						{
							try  // cf. ReceiveSocket#readScribMessage
							{
								ScribMessage m = getChannelEndpoint(((ScribInputState) curr).peer).getFuture().get();
								this.edep.inputs.get(curr).dispatch(m);  // FIXME: state object and received payloads -- null op OK?
								curr = ((ScribInputState) curr).succs.get(m.op);
							}
							catch (InterruptedException e)
							{
								//throw new IOException(e);
								res[0] = new IOException(e);
								break;
							}
							catch (ExecutionException e)
							{	
								//throw new IOException(e);
								res[0] = new IOException(e);
								break;
							}
						}
						else
						{
							throw new RuntimeException("Shouldn't get in here: ");
						}
					}
				}
				catch (IOException e)
				{
					res[0] = e;
				}
				
				if (res[0] == null)
				{
					res[0] = 0;
				}
				synchronized(res)
				{
					res.notify();
				}
				setCompleted();
			}
		}.start();
		
		return new EventDrivenEndpointFuture(res);
	}
}

class EventDrivenEndpointFuture implements Future<Void>
{
	private final Object[] res;

	protected EventDrivenEndpointFuture(Object[] res)
	{
		this.res = res;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		throw new RuntimeException("TODO");
	}

	@Override
	public Void get() throws InterruptedException, ExecutionException
	{
		while (this.res[0] == null)
		{
			synchronized (this.res)
			{
				this.res.wait();
			}
		}
		if (this.res[0] instanceof IOException)
		{
			throw new ExecutionException((IOException) this.res[0]);
		}
		return null;
	}

	@Override
	public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
	{
		throw new RuntimeException("TODO");
	}

	@Override
	public boolean isCancelled()
	{
		throw new RuntimeException("TODO");
	}

	@Override
	public boolean isDone()
	{
		return this.res[0] != null;
	}
}