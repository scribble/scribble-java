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
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.scribble.main.ScribbleException;
import org.scribble.main.ScribbleRuntimeException;
import org.scribble.type.name.GProtocolName;
import org.scribble.type.name.Role;

public abstract class Session
{
	private static final Map<Integer, Session> sessions = new HashMap<Integer, Session>();

	public final int id;

	public final List<String> impath;
	public final String modpath;
	public final GProtocolName proto;
	
	//private final Map<Role, MPSTEndpoint<?, ?>> endpoints = new HashMap<>();  // Only for local endpoints
	private final Map<Role, SessionEndpoint<?, ?>> endpoints = new HashMap<>();  // Only for local endpoints

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

	/*// Client side
	//public SessionEndpointOld toEndpoint(Principal p) throws ScribbleException, IOException
	public <R extends Role> SessionEndpoint<R> project(R role, ScribMessageFormatter smf) throws ScribbleRuntimeException
	{
		if (this.endpoints.containsKey(role))
		{
			throw new ScribbleRuntimeException("Session endpoint already created for: " + role);
		}
		try
		{
			SessionEndpoint<R> ep = new SessionEndpoint<>(this, role, smf);
			this.endpoints.put(role, ep);
			return ep;
		}
		catch (IOException e)
		{
			throw new ScribbleRuntimeException(e);
		}
	}*/
	//protected <P extends Session, R extends Role> SessionEndpoint<P, R> project(R role, ScribMessageFormatter smf) throws ScribbleRuntimeException, IOException
	//protected void project(MPSTEndpoint<?, ?> se) throws ScribbleRuntimeException, IOException
	protected void project(SessionEndpoint<?, ?> se) throws ScribbleRuntimeException, IOException
	{
		if (!getRoles().contains(se.self))
		{
			throw new ScribbleRuntimeException("Invalid role: " + se.self);
		}
		if (this.endpoints.containsKey(se.self))
		{
			throw new ScribbleRuntimeException("Session endpoint already created for: " + se.self);
		}
		//SessionEndpoint<P, R> ep = new SessionEndpoint<>(this, role, smf);
		this.endpoints.put(se.self, se);
		//return ep;
	}

	/*// Server side
	protected <R extends Role> SessionEndpoint<R> project(R role, ScribMessageFormatter smf, ScribServerSocket ss) throws ScribbleRuntimeException, IOException
	{
		// FIXME: check valid role
		if (this.endpoints.containsKey(role))
		{
			throw new ScribbleRuntimeException("Session endpoint already created for: " + role);
		}
		SessionEndpoint<R> ep = (ss == null)
				? new SessionEndpoint<>(this, role, smf)
				: new SessionEndpoint<>(this, role, smf, ss);
		this.endpoints.put(role, ep);
		return ep;
	}*/

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

	//public SessionEndpoint getEndpoint(Role role) throws ScribbleException
	public <S extends Session, R extends Role> MPSTEndpoint<S, R> getEndpoint(R role) throws ScribbleException
	{
		if (!this.endpoints.containsKey(role))
		{
			throw new ScribbleException("No endpoint for: " + role);
		}
		@SuppressWarnings("unchecked")
		MPSTEndpoint<S, R> cast = (MPSTEndpoint<S, R>) this.endpoints.get(role);  // FIXME:
		return cast;
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
	
	public abstract List<Role> getRoles();
}
