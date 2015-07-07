package org.scribble.del.local;

import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class LProjectionDeclDel extends LProtocolDeclDel
{
	// Maybe better to store in context, but more convenient to pass to here via factory (than infer in context building) -- could alternatively store in projected module
	private final GProtocolName fullname;
	private final Role self;

	public LProjectionDeclDel(GProtocolName fullname, Role self)
	{
		this.fullname = fullname;
		this.self = self;
	}
	
	public GProtocolName getSourceProtocol()
	{
		return this.fullname;
	}
	
	// Redundant with SelfRoleDecl in header
	public Role getSelfRole()
	{
		return this.self;
	}
	
	@Override
	protected LProtocolDeclDel copy()
	{
		return new LProjectionDeclDel(this.fullname, this.self);
	}
	
	/*@Override
	public LProtocolDecl leaveContextBuilding(ScribNode parent, ScribNode child, ContextBuilder builder, ScribNode visited) throws ScribbleException
	{
		LProtocolDecl lpd = (LProtocolDecl) visited;
		LProtocolDeclContext gcontext = new LProjectionDeclContext(builder.getLocalProtocolDependencyMap(), );
		LProjectionDeclDel del = (LProjectionDeclDel) setProtocolDeclContext(gcontext);
		return (LProtocolDecl) lpd.del(del);
	}
	
	@Override
	public LProjectionDeclContext getProtocolDeclContext()
	{
		return (LProjectionDeclContext) super.getProtocolDeclContext();
	}*/
}
