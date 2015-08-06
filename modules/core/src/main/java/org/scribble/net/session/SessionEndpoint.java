package org.scribble.net.session;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.ScribMessageFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.sesstype.name.Role;

// FIXME: factor out between role-endpoint based socket and channel-endpoint sockets
public class SessionEndpoint
{
	public final Buff<?> gc = new Buff<>();

	public final Session sess;
	public final Role self;
	//public final int port;  // Session abstraction maps role and abstract port to concrete port
	public final ScribMessageFormatter smf;
	
	//public ScribServerSocket ss;
	private Map<Role, ScribServerSocket> ss = new HashMap<>();

	private boolean complete = false;
	
	Map<Role, BinaryChannelEndpoint> chans = new HashMap<>();
	
	protected Set<Role> getPeers()
	{
		return this.chans.keySet();
	}
	
	private boolean init = false;
	//boolean closed = false;
	
	private final ScribInputSelector sel;
	
	public synchronized void init()
	{
		this.init = true;
		notify();
	}
	
	protected synchronized void waitForInit() throws InterruptedException
	{
		while(!this.init)
		{
			wait();
		}
	}
	
	public SessionEndpoint(Session sess, Role self, ScribMessageFormatter smf) throws IOException
	{
		this.sess = sess;
		this.self = self;
		//this.port = port;
		this.smf = smf;
		
		this.sel = new ScribInputSelector(this);
		this.sel.start();
	}

	// FIXME: generalise roles and server socks for endpoints playing multiple roles
	public SessionEndpoint(Session sess, Role self, ScribServerSocket ss, ScribMessageFormatter smf) throws IOException, ScribbleRuntimeException
	{
		this(sess, self, smf);
		register(self, ss);
	}
	
	public synchronized void register(Role self, ScribServerSocket ss) throws IOException, ScribbleRuntimeException
	{
		this.sel.pause();
		ServerSocketChannel c = ss.getServerSocketChannel();
		/*c.configureBlocking(false);
		SelectionKey key = c.register(this.sel, SelectionKey.OP_ACCEPT);
		key.attach(self);
		ss.setRegistered();
		this.ss = ss;*/
		//resumeSelector();
		this.ss.put(self, ss);
		this.sel.unpause();
	}

	public synchronized void register(Role peer, String host, int port) throws IOException
	{
		throw new RuntimeException("TODO: " + host + ", " + port);
	}
	
	public synchronized void register(Role peer, AbstractSelectableChannel c) throws IOException
	{
		c.configureBlocking(false);
		this.sel.pause();
		SelectionKey key = this.sel.register(c);
		key.attach(peer);
		this.chans.put(peer, new SocketChannelEndpoint(this, (SocketChannel) c));
		this.sel.unpause();
	}

	public BinaryChannelEndpoint getChannelEndpoint(Role role)
	{
		if (!this.chans.containsKey(role))
		{
			throw new RuntimeScribbleException(this.self + " is not connected to: " + role);
		}
		return this.chans.get(role);
	}
	
	public void setCompleted()
	{
		this.complete = true;	
	}
	
	public boolean isCompleted()
	{
		return this.complete;
	}
	
	public void close()
	{
		this.sel.close();
		this.ss.values().stream().forEach((ss) -> ss.unbind());
	}

	public ServerSocketChannel getServerSocket()
	{
		ScribServerSocket ss = this.ss.get(this.self);
		if (ss == null)
		{
			throw new RuntimeScribbleException("No server registered.");
		}
		return ss.getServerSocketChannel();
	}
	 
	/*public final Session sess;
	//public final Principal self;
	public final Role self;
	public final ScribMessageFormatter smf;
	
	private boolean complete = false;

	private final Map<Role, SocketEndpoint> socks = new HashMap<>();   // Includes SelfSocketEndpoint
	private final EndpointInputQueues queues = new EndpointInputQueues();

	//protected final LocalProtocolDecl root;
	//public final Monitor monitor;

	protected SessionEndpoint(Session sess, Role self, ScribMessageFormatter smf) //throws ScribbleException, IOException
	{
		this.sess = sess;
		this.self = self;
		this.smf = smf;

		/*ProtocolName lpn = Projector.makeProjectedProtocolName(sess.proto, this.self.role);
		this.root = (LocalProtocolDecl) projections.get(lpn.getPrefix()).protos.get(0);* /
		//this.monitor = createMonitor(sess.impath, sess.source, sess.proto, self);
	}
	
	public void setCompleted()
	{
		this.complete = true;	
	}
	
	public boolean isCompleted()
	{
		return this.complete;
	}

	// Only for remote endpoints (self SocketEndpoint is done in above constructor; but not recorded in role-principal map)
	public void register(Role peer, SocketWrapper sw) //throws IOException
	{
		this.socks.put(peer, new SocketEndpoint(this, peer, sw));
	}
	
	/*public Set<Role> getRemoteRoles()
	{
		return this.remroles.keySet();
	}
	
	public Principal getRemotePrincipal(Role role)
	{
		return this.remroles.get(role);
	}* /
	
	public SocketEndpoint getSocketEndpoint(Role role)
	{
		if (!this.socks.containsKey(role))
		{
			throw new RuntimeScribbleException(this.self + " is not connected to: " + role);
		}
		return this.socks.get(role);
	}
	
	public EndpointInputQueues getInputQueues()
	{
		return this.queues;
	}
	
	public void close()
	{
		for (SocketEndpoint se : this.socks.values())
		{
			try
			{
				se.close();
			}
			catch (IOException e)
			{
				// FIXME:
			}
		}		
	}

	/* // proto is fullname
	private static Monitor createMonitor(List<String> impath, String source, ProtocolName proto, Role self) throws ScribbleException, IOException
	{
		// FIXME: API (strings vs non-strings, simple vs full names)
		Job job = Tool.getWellFormedJob(impath, source);
		Map<ModuleName, Module> projections = Tool.getProjections(job, proto.getSimpleName().toString(), self.toString());
		Map<ProtocolName, ProtocolState> graphs = Tool.buildGraphs(job, projections);

		ProtocolName lpn = Projector.makeProjectedProtocolName(proto, self);
		System.out.println("[DEBUG] Projected graph: " + graphs.get(lpn).toDot());
		
		return new Monitor(graphs, lpn);
	}*/
	
	/*public Set<Role> getRemoteRoles()
	{
		return this.remroles.keySet();
	}
	
	public Principal getRemotePrincipal(Role role)
	{
		return this.remroles.get(role);
	}*/
}
