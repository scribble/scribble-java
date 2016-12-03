package org.scribble.ast.global;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;

public class GProtocolDef extends ProtocolDef<Global> implements GNode
{
	public GProtocolDef(CommonTree source, GProtocolBlock block)
	{
		super(source, block);
	}

	@Override
	protected GProtocolDef copy()
	{
		return new GProtocolDef(this.source, getBlock());
	}

	public LProtocolDef project(Role self, LProtocolBlock block)
	{
		LProtocolDef projection = AstFactoryImpl.FACTORY.LProtocolDef(this.source, block);
		return projection;
	}
	
	@Override
	public GProtocolDef clone()
	{
		GProtocolBlock block = getBlock().clone();
		return AstFactoryImpl.FACTORY.GProtocolDef(this.source, block);
	}

	@Override
	public GProtocolDef reconstruct(ProtocolBlock<Global> block)
	{
		ScribDel del = del();
		GProtocolDef gpd = new GProtocolDef(this.source, (GProtocolBlock) block);
		gpd = (GProtocolDef) gpd.del(del);
		return gpd;
	}

	@Override
	public GProtocolBlock getBlock()
	{
		return (GProtocolBlock) this.block;
	}
	
	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GNode.super.getKind();
	}
}
