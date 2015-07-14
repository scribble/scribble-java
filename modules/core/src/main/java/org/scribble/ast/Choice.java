package org.scribble.ast;

import java.util.LinkedList;
import java.util.List;

import org.scribble.ast.name.simple.RoleNode;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.visit.AstVisitor;


public abstract class Choice<K extends ProtocolKind> extends CompoundInteractionNode<K>
{
	public final RoleNode subj;
	public final List<? extends ProtocolBlock<K>> blocks;  // Factor up? And specialise to singleton for Recursion/Interruptible? Maybe too artificial -- could separate unaryblocked and multiblocked compound ops?

	protected Choice(RoleNode subj, List<? extends ProtocolBlock<K>> blocks)
	{
		super();
		this.subj = subj;
		this.blocks = new LinkedList<>(blocks);
	}
	
	public abstract Choice<K> reconstruct(RoleNode subj, List<? extends ProtocolBlock<K>> blocks);
	
	@Override
	public Choice<K> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode subj = (RoleNode) visitChild(this.subj, nv);
		List<? extends ProtocolBlock<K>> blocks = visitChildListWithClassEqualityCheck(this, this.blocks, nv);
		return reconstruct(subj, blocks);
	}
	
	public abstract List<? extends ProtocolBlock<K>> getBlocks();
	
	@Override
	public String toString()
	{
		String s = Constants.CHOICE_KW + " " + Constants.AT_KW + " " + this.subj + " " + this.blocks.get(0);
		for (ProtocolBlock<K> block : this.blocks.subList(1, this.blocks.size()))
		{
			s += " " + Constants.OR_KW + " " + block;
		}
		return s;
	}
}
