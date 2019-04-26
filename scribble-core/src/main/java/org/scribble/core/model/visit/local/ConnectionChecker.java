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
package org.scribble.core.model.visit.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.type.name.Role;
import org.scribble.util.ScribException;

// Not reusable
@Deprecated
public class ConnectionChecker extends EStateVisitor
{
	private static final int CONNECTED = 1;
	private static final int UNCONNECTED = -1;
	private static final int AMBIG = 0;
	
	private final Map<Role, Integer> conns;
	
	public ConnectionChecker(Set<Role> roles, Role self, boolean implicit)
	{
		this.conns = roles.stream().collect(Collectors.toMap(
				x -> x, 
				x -> implicit ? CONNECTED : UNCONNECTED
		));
	}
	
	protected ConnectionChecker(ConnectionChecker parent)
	{
		this.conns = new HashMap<>(parent.conns);
	}

	@Override
	public void visitAccept(EState s) throws ScribException
	{
		super.visitOutput(s);  // Does setSeen
		List<EAction> as = s.getActions();
		Role peer = as.get(0).peer;  // Pre: consistent ext choice subjs
		if (this.conns.get(peer) != UNCONNECTED)
		{
			throw new ScribException("");
		}
	}

	@Override
	public void visitOutput(EState s) throws ScribException
	{
		super.visitOutput(s);  // Does setSeen
	}

	@Override
	public void visitPolyInput(EState s) throws ScribException
	{
		super.visitOutput(s);  // Does setSeen
	}

	@Override
	public void visitServerWrap(EState s) throws ScribException
	{
		super.visitOutput(s);  // Does setSeen
	}

	public void visitTerminal(EState s) throws ScribException
	{
		setSeen(s);
	}

	public void visitUnaryInput(EState s) throws ScribException
	{
		setSeen(s);
	}
}	