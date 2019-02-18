package org.scribble.lang.local;

import java.util.List;
import java.util.stream.Collectors;

import org.scribble.lang.Choice;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.kind.Local;
import org.scribble.type.name.Role;

public class LChoice extends Choice<Local, LSeq> implements LType
{
	public LChoice(org.scribble.ast.Choice<Local> source, Role subj,
			List<LSeq> blocks)
	{
		super(source, subj, blocks);
	}
	
	@Override
	public LChoice reconstruct(org.scribble.ast.Choice<Local> source, Role subj,
			List<LSeq> blocks)
	{
		return new LChoice(source, subj, blocks);
	}

	@Override
	public LChoice substitute(Substitutions<Role> subs)
	{
		List<LSeq> blocks = this.blocks.stream().map(x -> x.substitute(subs))
				.collect(Collectors.toList());
		return reconstruct(getSource(), subs.apply(this.subj), blocks);
	}

	@Override
	public LChoice getInlined(STypeInliner i )//GTypeTranslator t, Deque<SubprotoSig> stack)
	{
		org.scribble.ast.local.LChoice source = getSource();  // CHECKME: or empty source?
		List<LSeq> blocks = this.blocks.stream().map(x -> x.getInlined(i))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}

	@Override
	public LChoice unfoldAllOnce(STypeUnfolder<Local> u)
	{
		org.scribble.ast.local.LChoice source = getSource();  // CHECKME: or empty source?
		List<LSeq> blocks = this.blocks.stream().map(x -> x.unfoldAllOnce(u))
				.collect(Collectors.toList());
		return reconstruct(source, this.subj, blocks);
	}
	
	@Override
	public void buildGraph(EGraphBuilderUtil2 b)
	{
		b.enterChoice();
		for (LSeq block : this.blocks)
		{
			List<LType> elems = block.getElements();
			LType first = elems.get(0);
			if (first instanceof LRecursion)  // CHECKME
			{
				EGraphBuilderUtil2 b1 = new EGraphBuilderUtil2(b);  
						// "Inherits" b.recvars, for continue edge building  (to "outer" recs)
				if (elems.size() == 1)
				{
					b1.setExit(b.getExit());
					first.buildGraph(b1);
					EGraph nested = b1.finalise();
					EState init = nested.init;
					//for (EAction a : first.getEnabling())
					for (EAction a : (Iterable<EAction>) 
							init.getAllActions().stream().distinct()::iterator)
							// Enabling actions
					{
						for (EState s : init.getSuccessors(a))
						{
							b.addEdge(b.getEntry(), a, s);
						}
					}
				}	
				else
				{
					first.buildGraph(b1);
					EGraph nested = b1.finalise();
					b.setEntry(nested.term);  // Must be non null
					LSeq tail = new LSeq(null, elems.subList(1, elems.size()));
					tail.buildGraph(b);
				}
			}
			else
			{
				b.pushChoiceBlock();  // CHECKME: still needed?  LContinue doesn't check isUnguardedInChoice any more
				block.buildGraph(b);
				b.popChoiceBlock();
			}
		}
		b.leaveChoice();
	}

	/*@Override
	public List<LSeq> getBlocks()
	{
		return this.blocks;
	}*/
	
	@Override
	public org.scribble.ast.local.LChoice getSource()
	{
		return (org.scribble.ast.local.LChoice) super.getSource();
	}

	@Override
	public int hashCode()
	{
		int hash = 3067;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof LChoice))
		{
			return false;
		}
		return super.equals(o);
	}

	@Override
	public boolean canEquals(Object o)
	{
		return o instanceof LChoice;
	}
}
