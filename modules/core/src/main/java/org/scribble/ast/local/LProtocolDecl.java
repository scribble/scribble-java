package org.scribble.ast.local;

import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ProtocolHeader;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;

//public class LocalProtocolDecl extends AbstractProtocolDecl<LocalProtocolHeader, LocalProtocolDefinition> implements LocalNode
public class LProtocolDecl extends ProtocolDecl<Local> implements LocalNode
{
	//public LocalProtocolDecl(SimpleProtocolNameNode name, RoleDeclList roledecls, ParameterDeclList paramdecls, LocalProtocolDefinition def)
	//public LocalProtocolDecl(LocalProtocolHeader header, LocalProtocolDefinition def)
	public LProtocolDecl(ProtocolHeader<Local> header, ProtocolDef<Local> def)
	{
		super(header, def);
	}

	@Override
	public LProtocolName getFullProtocolName(Module mod)
	//public LProtocolName getFullProtocolName(ModuleContext mcontext)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new LProtocolName(fullmodname, this.header.getDeclName());
		//return mcontext.getFullLocalProtocolName(((LProtocolHeader) this.header).getDeclName());
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
	protected LProtocolDecl reconstruct(ProtocolHeader<Local> header, ProtocolDef<Local> def)
	{
		ScribDel del = del();
		LProtocolDecl lpd = new LProtocolDecl(header, def);
		lpd = (LProtocolDecl) lpd.del(del);
		return lpd;
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LProtocolDecl(this.header, this.def);
	}
}
