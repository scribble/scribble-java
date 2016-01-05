package org.scribble.net;

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
