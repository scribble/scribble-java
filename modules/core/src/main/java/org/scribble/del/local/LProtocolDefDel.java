package org.scribble.del.local;

import java.util.Arrays;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ScribNode;
import org.scribble.ast.local.LInteractionSeq;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.local.LRecursion;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ProtocolDefDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlineProtocolEnv;

public class LProtocolDefDel extends ProtocolDefDel
{
	public LProtocolDefDel()
	{

	}

	@Override
	protected LProtocolDefDel copy()
	{
		LProtocolDefDel copy = new LProtocolDefDel();
		copy.inlined = this.inlined;
		return copy;
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		SubprotocolSig subsig = inl.peekStack();
		LProtocolDef lpd = (LProtocolDef) visited;
		LProtocolBlock block = (LProtocolBlock) ((InlineProtocolEnv) lpd.block.del().env()).getTranslation();	
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, inl.getSubprotocolRecVar(subsig).toString());
		LRecursion rec = AstFactoryImpl.FACTORY.LRecursion(recvar, block);
		LInteractionSeq lis = AstFactoryImpl.FACTORY.LInteractionSeq(Arrays.asList(rec));
		LProtocolDef inlined = AstFactoryImpl.FACTORY.LProtocolDef(AstFactoryImpl.FACTORY.LProtocolBlock(lis));
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		LProtocolDefDel copy = setInlinedProtocolDef(inlined);
		return (LProtocolDef) ScribDelBase.popAndSetVisitorEnv(this, inl, (LProtocolDef) lpd.del(copy));
	}
	
	@Override
	public LProtocolDef getInlinedProtocolDef()
	{
		return (LProtocolDef) super.getInlinedProtocolDef();
	}

	@Override
	public LProtocolDefDel setInlinedProtocolDef(ProtocolDef<? extends ProtocolKind> inlined)
	{
		return (LProtocolDefDel) super.setInlinedProtocolDef(inlined);
	}
}
