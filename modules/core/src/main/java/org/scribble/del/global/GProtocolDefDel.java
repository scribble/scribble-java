package org.scribble.del.global;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.ast.local.LProtocolDef;
import org.scribble.del.ProtocolDefDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.Projector;
import org.scribble.visit.env.InlineProtocolEnv;
import org.scribble.visit.env.ProjectionEnv;

public class GProtocolDefDel extends ProtocolDefDel
{
	private GProtocolDef inlined = null;
	
	public GProtocolDefDel()
	{

	}
	
	public GProtocolDef getInlinedGProtocolDef()
	{
		return this.inlined;
	}

	protected GProtocolDefDel copy()
	{
		GProtocolDefDel copy = new GProtocolDefDel();
		copy.inlined = this.inlined;
		return copy;
	}

	@Override
	public void enterProjection(ScribNode parent, ScribNode child, Projector proj) throws ScribbleException
	{
		pushVisitorEnv(parent, child, proj);
	}

	@Override
	public GProtocolDef leaveProjection(ScribNode parent, ScribNode child, Projector proj, ScribNode visited) throws ScribbleException
	{
		GProtocolDef gpd = (GProtocolDef) visited;
		LProtocolBlock block = (LProtocolBlock) ((ProjectionEnv) gpd.block.del().env()).getProjection();	
		LProtocolDef projection = AstFactoryImpl.FACTORY.LProtocolDefinition(block);
		proj.pushEnv(proj.popEnv().setProjection(projection));
		return (GProtocolDef) popAndSetVisitorEnv(parent, child, proj, gpd);
	}

	@Override
	public void enterInlineProtocolTranslation(ScribNode parent, ScribNode child, ProtocolDefInliner builder) throws ScribbleException
	{
		pushVisitorEnv(parent, child, builder);
	}

	@Override
	public ScribNode leaveInlineProtocolTranslation(ScribNode parent, ScribNode child, ProtocolDefInliner builder, ScribNode visited) throws ScribbleException
	{
		GProtocolDef gpd = (GProtocolDef) visited;
		GProtocolBlock block = (GProtocolBlock) ((InlineProtocolEnv) gpd.block.del().env()).getTranslation();	
		GProtocolDef inlined = AstFactoryImpl.FACTORY.GProtocolDefinition(block);
		builder.pushEnv(builder.popEnv().setTranslation(inlined));

		GProtocolDefDel copy = (GProtocolDefDel) copy();
		copy.inlined = inlined;

		System.out.println("1: " + inlined);
		
		return (GProtocolDef) popAndSetVisitorEnv(parent, child, builder, (GProtocolDef) gpd.del(copy));
	}
}
