package org.scribble.net;

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

	protected ScribMessage get() throws InterruptedException, ExecutionException
	{
		if (this.m != null)
		{
			return this.m;
		}
		this.m = this.future.get();
		return this.m;
	}
	
	public abstract ScribFuture sync() throws ExecutionException, InterruptedException;

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
