package org.scribble2.model.local;

import org.scribble2.model.ProtocolDecl;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.ProtocolDef;
import org.scribble2.model.ProtocolHeader;
import org.scribble2.model.del.ModelDel;
import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.name.Role;

//public class LocalProtocolDecl extends AbstractProtocolDecl<LocalProtocolHeader, LocalProtocolDefinition> implements LocalNode
public class LProtocolDecl extends ProtocolDecl<Local> implements LocalNode
{
	//public LocalProtocolDecl(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls, LocalProtocolDefinition def)
	//public LocalProtocolDecl(LocalProtocolHeader header, LocalProtocolDefinition def)
	public LProtocolDecl(ProtocolHeader header, ProtocolDef<Local> def)
	{
		super(header, def);
	}
	
	/*@Override
	public GraphBuilder enterGraphBuilding(GraphBuilder builder)
	{
		ProtocolState root = new ProtocolState();
		ProtocolState term = new ProtocolState();
		builder.setEntry(root);
		builder.setExit(term);
		return builder;
	}
	
	@Override
	public LocalProtocolDecl leaveGraphBuilding(GraphBuilder builder)
	{
		builder.addGraph(getFullProtocolName(builder.getModuleContext().root), builder.getEntry());
		return this;
	}

	/*@Override
	public LocalProtocolDecl visitChildren(NodeVisitor nv) throws ScribbleException
	{
		ProtocolDecl<GlobalProtocolDefinition> pd = super.visitChildren(nv);
		return new LocalProtocolDecl(pd.ct, pd.name, pd.roledecllist, pd.paramdecllist, pd.def);
	}*/

	@Override
	public boolean isLocal()
	{
		return true;
	}
	
	public Role getSelfRole()
	{
		return ((LProtocolHeader) this.header).getSelfRole();  // FIXME: cast
	}
	
	/*@Override
	public ProtocolName getFullProtocolName(Env env) throws ScribbleException
	{
		return env.getFullGlobalProtocolName(this.name.toName());
	}*/

	@Override
	//protected LocalProtocolDecl reconstruct(LocalProtocolHeader header, LocalProtocolDefinition def)
	protected LProtocolDecl reconstruct(ProtocolHeader header, ProtocolDef<Local> def)
	{
		ModelDel del = del();
		LProtocolDecl lpd = new LProtocolDecl(header, def);
		lpd = (LProtocolDecl) lpd.del(del);
		return lpd;
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new LProtocolDecl(this.header, this.def);
	}
}
