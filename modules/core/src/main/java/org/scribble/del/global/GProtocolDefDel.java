package org.scribble.del.global;

import java.util.Arrays;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ProtocolDef;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ProtocolDefDel;
import org.scribble.del.ScribDelBase;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.RecVarKind;
import org.scribble.visit.Projector;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ProjectionEnv;

public class GProtocolDefDel extends ProtocolDefDel
{
	public GProtocolDefDel()
	{

	}

	@Override
	protected GProtocolDefDel copy()
	{
		GProtocolDefDel copy = new GProtocolDefDel();
		copy.inlined = this.inlined;
		return copy;
	}

	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		SubprotocolSig subsig = inl.peekStack();
		GProtocolDef gpd = (GProtocolDef) visited;
		GProtocolBlock block = (GProtocolBlock) ((InlineProtocolEnv) gpd.block.del().env()).getTranslation();	
		RecVarNode recvar = (RecVarNode) AstFactoryImpl.FACTORY.SimpleNameNode(RecVarKind.KIND, inl.getRecVar(subsig).toString());
		GRecursion rec = AstFactoryImpl.FACTORY.GRecursion(recvar, block);
		GInteractionSeq gis = AstFactoryImpl.FACTORY.GInteractionSeq(Arrays.asList(rec));
		GProtocolDef inlined = AstFactoryImpl.FACTORY.GProtocolDef(AstFactoryImpl.FACTORY.GProtocolBlock(gis));
		inl.pushEnv(inl.popEnv().setTranslation(inlined));
		GProtocolDefDel copy = setInlinedProtocolDef(inlined);
		return (GProtocolDef) ScribDelBase.popAndSetVisitorEnv(this, inl, (GProtocolDef) gpd.del(copy));
	}

	@Override
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		//pushVisitorEnv(parent, child, proj);
		ScribDelBase.pushVisitorEnv(this, proj);
	}

	@Override
	public GProtocolDef leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GProtocolDef gpd = (GProtocolDef) visited;
		LProtocolBlock block = (LProtocolBlock) ((ProjectionEnv) gpd.block.del().env()).getProjection();	
		LProtocolDef projection = AstFactoryImpl.FACTORY.LProtocolDef(block);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GProtocolDef) ScribDelBase.popAndSetVisitorEnv(this, proj, gpd);
	}
	
	@Override
	public GProtocolDef getInlinedProtocolDef()
	{
		return (GProtocolDef) super.getInlinedProtocolDef();
	}

	@Override
	public GProtocolDefDel setInlinedProtocolDef(ProtocolDef<?> inlined)
	{
		return (GProtocolDefDel) super.setInlinedProtocolDef(inlined);
	}
}
