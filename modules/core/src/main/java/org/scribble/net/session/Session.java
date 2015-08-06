package org.scribble.net.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.scribble.main.ScribbleException;
import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ScribMessageFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class Session
{
	private static final Map<Integer, Session> sessions = new HashMap<Integer, Session>();

	public final int id;

	public final List<String> impath;
	public final String modpath;
	public final GProtocolName proto;
	
	private final Map<Role, SessionEndpoint> endpoints = new HashMap<>();  // Only for local endpoints

	public Session(int id, List<String> impath, String modpath, GProtocolName proto)
	{
		this.id = id;
		this.impath = impath;
		this.modpath = modpath;
		this.proto = proto;
		
		Session.sessions.put(id, this);
	}

	public Session(List<String> importPath, String source, GProtocolName proto)
	{
		this(getFreshId(), importPath, source, proto);
	}

	//public SessionEndpointOld toEndpoint(Principal p) throws ScribbleException, IOException
	public SessionEndpoint project(Role role, ScribMessageFormatter smf) throws ScribbleRuntimeException, IOException
	{
		if (this.endpoints.containsKey(role))
		{
			throw new ScribbleRuntimeException("Session endpoint already created for: " + role);
		}
		SessionEndpoint ep = new SessionEndpoint(this, role, smf);
		this.endpoints.put(role, ep);
		return ep;
	}

	public SessionEndpoint project(Role role, ScribServerSocket ss, ScribMessageFormatter smf) throws ScribbleRuntimeException, IOException
	{
		// FIXME: check valid role
		if (this.endpoints.containsKey(role))
		{
			throw new ScribbleRuntimeException("Session endpoint already created for: " + role);
		}
		SessionEndpoint ep = new SessionEndpoint(this, role, ss, smf);
		this.endpoints.put(role, ep);
		return ep;
	}

	/*public SessionEndpointOld project(Principal p) throws ScribbleException, IOException
	{
		if (this.endpoints.containsKey(p.role))
		{
			throw new ScribbleException("Session endpoint already created for: " + p.role);
		}
		SessionEndpointOld ep = new SessionEndpointOld(this, p);
		//this.endpoints.put(p.role, ep);
		return ep;
	}*/
	
	public boolean hasEndpoint(Role role)
	{
		return this.endpoints.containsKey(role);
	}

	public SessionEndpoint getEndpoint(Role role) throws ScribbleException
	{
		if (!this.endpoints.containsKey(role))
		{
			throw new ScribbleException("No endpoint for: " + role);
		}
		return this.endpoints.get(role);
	}
	
	/*public static boolean hasSession(int id)
	{
		return Session.sessions.containsKey(id);
	}*/

	public static Session getSession(int id) throws ScribbleException
	{
		if (!Session.sessions.containsKey(id))
		{
			throw new ScribbleException("No such session: " + id);
		}
		return Session.sessions.get(id);
	}
	
	private static int getFreshId()
	{
		Random random = new Random();
		int id = random.nextInt(10000);
		while (Session.sessions.keySet().contains(id))
		{
			id = random.nextInt(10000);
		}
		return id;
	}
	
	@Override
	protected void finalize()
	{
		Session.sessions.remove(this.id);
	}
}
