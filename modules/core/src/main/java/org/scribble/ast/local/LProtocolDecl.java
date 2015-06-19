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

public class LProtocolDecl extends ProtocolDecl<Local> implements LNode
{
	public LProtocolDecl(LProtocolHeader header, LProtocolDef def)
	{
		super(header, def);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LProtocolDecl(getHeader(), getDef());
	}
	
	@Override
	protected LProtocolDecl reconstruct(ProtocolHeader<Local> header, ProtocolDef<Local> def)
	{
		ScribDel del = del();
		LProtocolDecl lpd = new LProtocolDecl((LProtocolHeader) header, (LProtocolDef) def);
		lpd = (LProtocolDecl) lpd.del(del);
		return lpd;
	}

	@Override
	public LProtocolHeader getHeader()
	{
		return (LProtocolHeader) this.header;
	}

	@Override
	public LProtocolDef getDef()
	{
		return (LProtocolDef) this.def;
	}

	@Override
	public LProtocolName getFullProtocolName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new LProtocolName(fullmodname, this.header.getDeclName());
	}
	
	@Override
	public boolean isLocal()
	{
		return true;
	}
	
	public Role getSelfRole()
	{
		return getHeader().getSelfRole();
	}
}
