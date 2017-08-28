package org.scribble.ext.go.del.global;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDelBase;
import org.scribble.del.global.GChoiceDel;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.ast.global.ParamGChoice;
import org.scribble.main.ScribbleException;
import org.scribble.visit.ProtocolDefInliner;
import org.scribble.visit.env.InlineProtocolEnv;

public class ParamGChoiceDel extends GChoiceDel
{
	@Override
	public ScribNode leaveProtocolInlining(ScribNode parent, ScribNode child, ProtocolDefInliner inl, ScribNode visited) throws ScribbleException
	{
		ParamGChoice gc = (ParamGChoice) visited;
		List<GProtocolBlock> blocks = gc.getBlocks().stream()
				.map(b -> (GProtocolBlock) ((InlineProtocolEnv) b.del().env()).getTranslation()).collect(Collectors.toList());	
		RoleNode subj = gc.subj.clone(inl.job.af);

		ParamGChoice inlined = ((ParamAstFactory) inl.job.af).ParamGChoice(gc.getSource(), subj, gc.expr, blocks);

		inl.pushEnv(inl.popEnv().setTranslation(inlined));

		//return (GChoice) super.leaveProtocolInlining(parent, child, inl, gc);
		return ScribDelBase.popAndSetVisitorEnv(this, inl, visited);
	}
}
