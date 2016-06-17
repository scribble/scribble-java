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
		super(DUMMY_PROJECTION_ROLE);
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
