package org.scribble.ast.local;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Choice;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
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
	public LChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Local>> blocks)
	{
		ScribDel del = del();
		LChoice lc = new LChoice(subj, castBlocks(blocks));
		lc = (LChoice) lc.del(del);
		return lc;
	}

	@Override
	public List<LProtocolBlock> getBlocks()
	{
		return castBlocks(super.getBlocks());
	}

	@Override
	public Role inferLocalChoiceSubject(ProjectedChoiceSubjectFixer fixer)
	{
		return getBlocks().get(0).getInteractionSeq().getInteractions().get(0).inferLocalChoiceSubject(fixer);
	}

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Local getKind()
	{
		return LCompoundInteractionNode.super.getKind();
	}
	
	private static List<LProtocolBlock> castBlocks(List<? extends ProtocolBlock<Local>> blocks)
	{
		return blocks.stream().map((b) -> (LProtocolBlock) b).collect(Collectors.toList());
	}
	
	@Override
	public LChoice merge(LInteractionNode ln) throws ScribbleException
	{
		if (!(ln instanceof LChoice) || !this.canMerge(ln))
		{
			throw new ScribbleException("Cannot merge " + this.getClass() + " and " + ln.getClass() + ": " + this + ", " + ln);
		}
		LChoice them = ((LChoice) ln);
		/*if (!this.subj.toName().equals(them.subj.toName()))  // NO: pointless, always DummyProjectionRoleNode at this point -- maybe unnecessary?
		{
			throw new ScribbleException("Cannot merge choices for " + this.subj + " and " + them.subj + ": " + this + ", " + ln);
		}*/
		List<LProtocolBlock> blocks = new LinkedList<>();
		// For now assume all labels distinct by WFChoiceCheck -- for more general merge need to use getEnabling and check if overlapping labels have the same cases (need an equals for ScribNodes)
		getBlocks().forEach((b) -> blocks.add(b.clone()));
		them.getBlocks().forEach((b) -> blocks.add(b.clone()));
		return AstFactoryImpl.FACTORY.LChoice(this.subj, blocks);  // Not reconstruct: leave context building to post-projection passes 
	}

	@Override
	public boolean canMerge(LInteractionNode ln)
	{
		return ln instanceof LChoice;
	}
	
	@Override
	public Set<Message> getEnabling()
	{
		return getBlocks().stream().flatMap((b) -> b.getEnabling().stream()).collect(Collectors.toSet());
	}
}
