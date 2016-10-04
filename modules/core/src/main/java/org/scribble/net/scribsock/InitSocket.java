package org.scribble.net.scribsock;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.BinaryChannelEndpoint;
import org.scribble.net.session.Session;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.sesstype.name.Role;

// Establishing transport connections handled in here and wrapped up in SocketWrapper
@Deprecated
public abstract class InitSocket<S extends Session, R extends Role> extends LinearSocket<S, R> implements AutoCloseable
{
	protected InitSocket(MPSTEndpoint<S, R> se)
	{
		super(se);
	}

	public void connect(Callable<? extends BinaryChannelEndpoint> cons, Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		// Can connect unlimited, as long as not already used via init
		if (isUsed())
		{
			throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
		}
		try
		{
			BinaryChannelEndpoint c = cons.call();
			c.initClient(se, host, port);
			this.se.register(role, c);
		}
		catch (Exception e)
		{
			if (e instanceof IOException)
			{
				throw (IOException) e;
			}
			throw new IOException(e);
		}
	}

	public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		//accept(null, role);
		if (isUsed())
		{
			throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
		}
		this.se.register(role, ss.accept(this.se));  // FIXME: serv map in SessionEndpoint not currently used
	}

	@Deprecated
	public void accept(Role role) throws IOException, ScribbleRuntimeException
	{
		if (isUsed())
		{
			throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
		}
		this.se.register(role, this.se.getSelfServerSocket().accept(this.se));
	}
	
	@Override
	public void close() throws ScribbleRuntimeException
	{
		try
		{
			this.se.close();
		}
		finally
		{
			if (!this.se.isCompleted())  // Subsumes use -- must be used for sess to be completed
			{
				throw new ScribbleRuntimeException("Session not completed: " + this.se.self);
			}
		}
	}
}
