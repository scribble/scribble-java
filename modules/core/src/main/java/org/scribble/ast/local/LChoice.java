package org.scribble.ast.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Choice;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Role;
import org.scribble.util.ScribUtil;
import org.scribble.visit.ProjectedChoiceSubjectFixer;

public class LChoice extends Choice<Local> implements LCompoundInteractionNode
{
	public LChoice(RoleNode subj, List<LProtocolBlock> blocks)
	{
		super(subj, blocks);
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new LChoice(this.subj, getBlocks());
	}
	
	@Override
	public LChoice clone()
	{
		RoleNode subj = this.subj.clone();
		List<LProtocolBlock> blocks = ScribUtil.cloneList(getBlocks());
		return AstFactoryImpl.FACTORY.LChoice(subj, blocks);
	}

	@Override
	protected LChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Local>> blocks)
	{
		ScribDel del = del();
		LChoice lc = new LChoice(subj, castBlocks(blocks));
		lc = (LChoice) lc.del(del);
		return lc;
	}

	@Override
	public List<LProtocolBlock> getBlocks()
	{
		return castBlocks(this.blocks);
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return getBlocks().get(0).getInteractionSeq().getActions().get(0).inferLocalChoiceSubject(fixer);
	}
	
	private static List<LProtocolBlock> castBlocks(List<? extends ProtocolBlock<Local>> blocks)
	{
		return blocks.stream().map((b) -> (LProtocolBlock) b).collect(Collectors.toList());
	}
}
