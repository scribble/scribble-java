package org.scribble.ast.global;

import org.scribble.ast.AstFactoryImpl;
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
	public GProtocolDecl(GProtocolHeader header, GProtocolDef def)
	{
		super(header, def);
	}

	@Override
	protected GProtocolDecl copy()
	{
		return new GProtocolDecl(getHeader(), getDef());
	}
	
	@Override
	public GProtocolDecl clone()
	{
		GProtocolHeader header = getHeader().clone();
		GProtocolDef def = getDef().clone();
		return AstFactoryImpl.FACTORY.GProtocolDecl(header, def);
	}

	@Override
	public GProtocolDecl reconstruct(ProtocolHeader<Global> header, ProtocolDef<Global> def)
	{
		
		ScribDel del = del();
		GProtocolDecl gpd = new GProtocolDecl((GProtocolHeader) header, (GProtocolDef) def);
		gpd = (GProtocolDecl) gpd.del(del);  // FIXME: does another shallow copy
		return gpd;
	}

	@Override
	public GProtocolHeader getHeader()
	{
		return (GProtocolHeader) this.header;
	}

	@Override
	public GProtocolDef getDef()
	{
		return (GProtocolDef) this.def;
	}

	@Override
	public GProtocolName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new GProtocolName(fullmodname, this.header.getDeclName());
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public boolean isGlobal()
	{
		return GNode.super.isGlobal();
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GNode.super.getKind();
	}
}
