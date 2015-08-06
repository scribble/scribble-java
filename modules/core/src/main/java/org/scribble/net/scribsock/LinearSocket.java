package org.scribble.net.scribsock;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;

// Not AutoClosable -- leave that to InitSocket
public abstract class LinearSocket extends ScribSocket
{
	private boolean used = false;
	
	protected LinearSocket(SessionEndpoint ep)
	{
		super(ep);
	}
	
	protected boolean isUsed()
	{
		return this.used;
	}
	
	//protected synchronized void use() throws ScribbleRuntimeException
	protected void use() throws ScribbleRuntimeException
	{
		if (this.used)
		{
			throw new ScribbleRuntimeException("Linear socket resource already used: " + this.getClass());
		}
		this.used = true;
	}

	/*// Only triggered by autoclose or explicit close, i.e. not called directly by user
	protected synchronized void close() throws ScribbleRuntimeException
	{
		if (!this.used)
		{
			this.ep.close();
			throw new ScribbleRuntimeException("Socket resource not used: " + this.getClass());
		}
	}*/

	/*// FIXME: factor out transport parameter
	// FIXME: close old socket -- handle old receive thread (consider messsages still arriving -- maybe only support reconnect for states where this is not possible)
	// FIXME: synch new receive thread with old one if messages are allowed to still arrive on old one
	public void reconnect(Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		// Can connect unlimited, as long as not already used via init
		if (this.used)
		{
			throw new ScribbleRuntimeException("Linear socket resource already used: " + this.getClass());
		}
		SSLSocketFactory fact = (SSLSocketFactory) SSLSocketFactory.getDefault();
		Socket s1 = this.ep.getSocketEndpoint(role).getSocketWrapper().getSocket();  // FIXME: check already connected
		SSLSocket s2 = (SSLSocket) fact.createSocket(s1, s1.getInetAddress().getHostAddress(), s1.getPort(), true);
		this.ep.register(role, new SocketWrapper(s2));  // Replaces old SocketEndpoint  // FIXME: tidy up
	}*/
}
