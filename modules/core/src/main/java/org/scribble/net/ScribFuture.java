package org.scribble.net;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.scribble.net.scribsock.ScribSocket;

public class ScribFuture<C extends ScribSocket> implements Future<ScribMessage>
{
	private CompletableFuture<ScribMessage> future;
	private C next;
	
	public ScribFuture(CompletableFuture<ScribMessage> future, C next)
	{
		this.future = future;
		this.next = next;
	}
	
	public C next()
	{
		// FIXME: future done?
		C tmp = next;
		this.next = null;
		return tmp;
	}

	public C next(Buff<ScribMessage> buff) throws InterruptedException, ExecutionException
	{
		buff.val = get();	
		return next();
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		return this.future.cancel(mayInterruptIfRunning);
	}

	@Override
	public ScribMessage get() throws InterruptedException, ExecutionException
	{
		return this.future.get();
	}

	@Override
	public ScribMessage get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException
	{
		return this.future.get(timeout, unit);
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
	}
}
