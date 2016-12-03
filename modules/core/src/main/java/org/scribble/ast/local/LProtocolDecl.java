package org.scribble.ast.local;

import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ProtocolHeader;
import org.scribble.ast.ScribNodeBase;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.ModuleName;
import org.scribble.sesstype.name.Role;

public class LProtocolDecl extends ProtocolDecl<Local> implements LNode
{
	public LProtocolDecl(CommonTree source, List<Modifiers> modifiers, LProtocolHeader header, LProtocolDef def)
	{
		super(source, modifiers, header, def);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LProtocolDecl(this.source, this.modifiers, getHeader(), getDef());
	}
	
	@Override
	public LProtocolDecl clone()
	{
		LProtocolHeader header = getHeader().clone();
		LProtocolDef def = getDef().clone();
		return AstFactoryImpl.FACTORY.LProtocolDecl(this.source, this.modifiers, header, def);
	}
	
	@Override
	public LProtocolDecl reconstruct(ProtocolHeader<Local> header, ProtocolDef<Local> def)
	{
		ScribDel del = del();
		LProtocolDecl lpd = new LProtocolDecl(this.source, this.modifiers, (LProtocolHeader) header, (LProtocolDef) def);
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
	public LProtocolName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new LProtocolName(fullmodname, this.header.getDeclName());
	}
	
	public Role getSelfRole()
	{
		return getHeader().getSelfRole();
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public boolean isLocal()
	{
		return LNode.super.isLocal();
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LNode.super.getKind();
	}
}
