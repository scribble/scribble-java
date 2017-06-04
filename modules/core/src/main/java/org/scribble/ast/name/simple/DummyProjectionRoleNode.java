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
package org.scribble.ast.name.simple;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Substitutor;

// For local choice subjects
public class DummyProjectionRoleNode extends RoleNode
{
	public static final String DUMMY_PROJECTION_ROLE = "__DUMMY_ROLE";

	public DummyProjectionRoleNode()
	{
		super(null, DUMMY_PROJECTION_ROLE);
	}

	@Override
	protected DummyProjectionRoleNode copy()
	{
		return new DummyProjectionRoleNode();
	}
	
	@Override
	public DummyProjectionRoleNode clone()
	{
		return AstFactoryImpl.FACTORY.DummyProjectionRoleNode();
	}
	
	@Override
	public DummyProjectionRoleNode substituteNames(Substitutor subs)
	{
		//throw new RuntimeException("Shouldn't get in here: " + this);
		return reconstruct(null);  // HACK: for ProjectedSubprotocolPruner, but maybe useful for others
	}

	@Override
	protected DummyProjectionRoleNode reconstruct(String identifier)
	{
		ScribDel del = del();
		DummyProjectionRoleNode rn = new DummyProjectionRoleNode();
		rn = (DummyProjectionRoleNode) rn.del(del);
		return rn;
	}
	
	@Override
	public Role toName()
	{
		return new Role(getIdentifier());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof DummyProjectionRoleNode))
		{
			return false;
		}
		return ((DummyProjectionRoleNode) o).canEqual(this) && super.equals(o);
	}
	
	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof DummyProjectionRoleNode;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 359;
		hash = 31 * super.hashCode();
		return hash;
	}
}
