package org.scribble.ast.global;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ProtocolHeader;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.ModuleName;

public class GProtocolDecl extends ProtocolDecl<Global> implements GNode
{
	public GProtocolDecl(GProtocolHeader header, GProtocolDef def)  // Refactor all G/L concrete Node classes to not use generic parameters, but the corresponding concrete classes, like this
	{
		super(header, def);
	}

	@Override
	protected GProtocolDecl copy()
	{
		return new GProtocolDecl((GProtocolHeader) this.header, (GProtocolDef) this.def);
	}

	@Override
	protected GProtocolDecl reconstruct(ProtocolHeader<Global> header, ProtocolDef<Global> def)
	{
		
		ScribDel del = del();
		GProtocolDecl gpd = new GProtocolDecl((GProtocolHeader) header, (GProtocolDef) def);
		gpd = (GProtocolDecl) gpd.del(del);  // FIXME: does another shallow copy
		return gpd;
	}

	@Override
	public boolean isGlobal()
	{
		return true;
	}

	@Override
	public GProtocolName getFullProtocolName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new GProtocolName(fullmodname, this.header.getDeclName());
	}
}
