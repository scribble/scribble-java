package org.scribble.net.session;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.security.GeneralSecurityException;
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
//.. initiator and joiner endpoints
public class SessionEndpoint<R extends Role>
{
	public final Buff<?> gc = new Buff<>();

	public final Session sess;
	public final Role self;
	public final ScribMessageFormatter smf;

	private boolean complete = false;
	private boolean closed = false;
	
	protected final Map<Role, ScribServerSocket> servs = new HashMap<>();
	protected final Map<Role, BinaryChannelEndpoint> chans = new HashMap<>();
	
	private final ScribInputSelector sel;
	
	public ScribInputSelector getSelector()
	{
		return this.sel;
	}
	
	public SessionEndpoint(Session sess, Role self, ScribMessageFormatter smf) throws IOException
	{
		this.sess = sess;
		this.self = self;
		this.smf = smf;
		
		this.sel = new ScribInputSelector(this);
		this.sel.start();
	}

	// FIXME: generalise roles and server socks for endpoints playing multiple roles
	public SessionEndpoint(Session sess, Role self, ScribMessageFormatter smf, ScribServerSocket ss) throws IOException, ScribbleRuntimeException
	{
		this(sess, self, smf);
		register(self, ss);
	}
	
	// FIXME: allowing multiple serversocks to be registered, but it just overrides the self entry (already set by constructor) -- server socks not identified with peer (or protocol state)
	// -- and InitSocket accept always uses the self server sock
	//public synchronized void register(Role self, ScribServerSocket ss) throws IOException, ScribbleRuntimeException
	protected synchronized void register(Role self, ScribServerSocket ss) throws IOException, ScribbleRuntimeException
	{
		this.sel.pause();
		this.servs.put(self, ss);
		this.sel.unpause();
	}

	public synchronized void register(Role peer, String host, int port) throws IOException
	{
		throw new RuntimeException("TODO: " + host + ", " + port);
	}
	
	public synchronized void register(Role peer, BinaryChannelEndpoint c) throws IOException
	{
		this.sel.pause();
		SelectionKey key = this.sel.register(c.getSelectableChannel());
		key.attach(peer);
		this.chans.put(peer, c);
		this.sel.unpause();
	}

	// w is uninitialised (need to use wrapChannel)
	public synchronized void reregister(Role peer, BinaryChannelWrapper w) throws IOException, GeneralSecurityException
	{
		/*this.sel.pause();
		SelectionKey old = getChannelEndpoint(peer).getSelectableChannel().keyFor(this.sel.getSelector());
		if (old == null)
		{
			throw new RuntimeException("Endpoint not yet registered for: " + peer);
		}
		old.cancel();
		SelectionKey key = this.sel.register(c.getSelectableChannel());
		key.attach(peer);
		this.chans.put(peer, c);
		this.sel.unpause();*/
		
		this.sel.pause();
		// FIXME: consume all pending messages/futures first? or ok to leave data in existing bb -- or only permit in states where bb is empty?
		// Underlying selectable channel is the same, so no need to cancel key and re-reg -- OK to assume in general?
		w.wrapChannel(getChannelEndpoint(peer));
		w.clientHandshake();
		this.chans.put(peer, w);
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
	
	public synchronized void close()
	{
		if (!this.closed)
		{
			this.closed = true;
			this.sel.close();
			this.servs.values().stream().forEach((ss) -> ss.unbind());
		}
	}

	public ScribServerSocket getSelfServerSocket()
	{
		ScribServerSocket ss = this.servs.get(this.self);
		if (ss == null)
		{
			throw new RuntimeScribbleException("No server registered.");
		}
		return ss;
	}
	
	protected Set<Role> getPeers()
	{
		return this.chans.keySet();
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
