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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import org.scribble.core.type.name.Role;
import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.handlers.ScribBranch;
import org.scribble.runtime.handlers.ScribOutputEvent;
import org.scribble.runtime.handlers.ScribSigMessage;
import org.scribble.runtime.handlers.states.ScribEndState;
import org.scribble.runtime.handlers.states.ScribInputState;
import org.scribble.runtime.handlers.states.ScribOutputState;
import org.scribble.runtime.handlers.states.ScribState;
import org.scribble.runtime.message.ScribMessage;
import org.scribble.runtime.message.ScribMessageFormatter;

public class CBEndpoint<S extends Session, R extends Role, D> extends MPSTEndpoint<S, R>
{
	protected final ScribState init;
	protected final Map<ScribOutputState, Function<D, ? extends ScribOutputEvent>> outputs = new HashMap<>();
	protected final Map<ScribInputState, ScribBranch<D>> inputs = new HashMap<>();
	
	protected final D data;
	
	protected final Map<String, ScribState> states = new HashMap<>();  // Bypass problem of mutual references and static field initialisation (class loading)

	public CBEndpoint(S sess, R self, ScribMessageFormatter smf, ScribState init, D data) throws IOException, ScribRuntimeException
	{
		super(sess, self, smf);
		this.init = init;
		this.data = data;
	}
	
	public Future<Void> run() throws ScribRuntimeException
	{
		Object[] res = new Object[1];

		// FIXME: integrate with selector (or make Executor)
		new Thread()
		{
			private final CBEndpoint<S, R, D> edep;

			{
				this.edep = CBEndpoint.this;
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
							// FIXME: make ScribOutputEvent an interface and add to bounds of output icallback (to preclude dynamic cast check) -- also make generated ..._Message an interface 
							ScribOutputEvent m = (ScribOutputEvent) this.edep.outputs.get(curr).apply(this.edep.data);  // FIXME: state object
							/*getChannelEndpoint(m.getPeer()).write(new ScribMessage(m.getOp(), m.getPayload().toArray(new Object[0])));  // FIXME: ScribEvent has extra Role
							curr = this.edep.states.get(((ScribOutputState) curr).succs.get(m.getOp()));*/
							if (m instanceof ScribSigMessage)
							{
								getChannelEndpoint(m.getPeer()).write(((ScribSigMessage) m).getSig());
							}
							else
							{
								getChannelEndpoint(m.getPeer()).write(new ScribMessage(m.getOp(), m.getPayload()));  // API gen extends ScribMessage, but no guarantee about user-defined types -- can only rely on ScribOutputEvent i/f
							}
							curr = this.edep.states.get(((ScribOutputState) curr).succs.get(m.getOp()));
						}
						else if (curr instanceof ScribInputState)
						{
							try  // cf. ReceiveSocket#readScribMessage
							{
								ScribMessage m = getChannelEndpoint(((ScribInputState) curr).peer).getFuture().get();
								this.edep.inputs.get(curr).dispatch(this.edep.data, m);
								curr = this.edep.states.get(((ScribInputState) curr).succs.get(m.op));
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
							throw new RuntimeException("Shouldn't get in here: " + curr);
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