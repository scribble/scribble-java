package org.scribble.ext.go.del.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDelBase;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.ast.global.RPGForeach;
import org.scribble.ext.go.del.RPForeachDel;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlineProtocolEnv;

public class RPGForeachDel extends RPForeachDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inlr, ScribNode visited) throws ScribbleException
	{
		RPGForeach fe = (RPGForeach) visited;
		List<RoleNode> subjs = fe.subjs.stream().map(r -> r.clone(inlr.job.af)).collect(Collectors.toList());
		GProtocolBlock block = (GProtocolBlock) ((InlineProtocolEnv) fe.block.del().env()).getTranslation();	
		RPGForeach inlined = ((RPAstFactory) inlr.job.af).RPGForeach(fe.getSource(), subjs, fe.params, fe.starts, fe.ends, block);
		inlr.pushEnv(inlr.popEnv().setTranslation(inlined));
		//return (GChoice) super.leaveProtocolInlining(parent, child, inl, gc);
		return ScribDelBase.popAndSetVisitorEnv(this, inlr, visited);
	}
}
