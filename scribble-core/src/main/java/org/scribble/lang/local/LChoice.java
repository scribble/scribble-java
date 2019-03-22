package org.scribble.lang.local;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.job.ScribbleException;
import org.scribble.lang.Choice;
import org.scribble.lang.STypeInliner;
import org.scribble.lang.STypeUnfolder;
import org.scribble.lang.Substitutions;
import org.scribble.model.endpoint.EGraphBuilderUtil2;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;
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
	public boolean isSingleCont()
	{
		return this.blocks.stream().allMatch(x -> x.isSingleCont());
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
			if (first instanceof LRecursion)  // CHECKME: do this here?  refactor into builderutil?
			{
				//EGraphBuilderUtil2 b1 = new EGraphBuilderUtil2(b);  
						// "Inherits" b.recvars, for continue edge building (to "outer" recs)
				//EGraph nested;
				EState entry = b.getEntry();
				EState exit = b.getExit();

				EState nestedEntry = b.newState(Collections.emptySet());
				b.setEntry(nestedEntry);
				if (elems.size() == 1)
				{
					/*b1.setExit(b.getExit());
					first.buildGraph(b1);
					nested = b1.finalise();*/
					first.buildGraph(b);
				}	
				else
				{
					//.. HERE reuse existing b for nested (set entry/exit), and fix module imports and FQ names 

					/*first.buildGraph(b1);
					nested = b1.finalise();*/
					EState nestedExit = b.newState(Collections.emptySet());
					b.setExit(nestedExit);
					first.buildGraph(b);

					b.setEntry(nestedExit);  // Must be non null
					b.setExit(exit);
					LSeq tail = new LSeq(null, elems.subList(1, elems.size()));
					tail.buildGraph(b);
				}
				//EState init = nested.init;
				EState init = nestedEntry;
				////for (EAction a : first.getEnabling())
				for (EAction a : (Iterable<EAction>) 
						init.getAllActions().stream().distinct()::iterator)
						// Enabling actions
				{
					for (EState s : init.getSuccessors(a))
					{
						b.addEdge(entry, a, s);
					}
				}
				
				b.setEntry(entry);
				b.setExit(exit);
			}
			else if (first instanceof LContinue)
			{
				// Cannot treat choice-unguarded-continue in "a single pass" because may not have built all recursion enacting edges yet 
				// (Single-pass building would be sensitive to order of choice block visiting)
				LContinue cont = (LContinue) first;  // First and only element
				b.addContinueEdge(b.getEntry(), cont.recvar); 
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

	@Override
	public ReachabilityEnv checkReachability(ReachabilityEnv env)
			throws ScribbleException
	{
		List<ReachabilityEnv> blocks = new LinkedList<>();
		for (LSeq block : this.blocks)
		{
			blocks.add(block.checkReachability(env));
		}
		boolean postcont = blocks.stream().allMatch(x -> x.postcont);  // i.e., no exits
		Set<RecVar> recvars = blocks.stream().flatMap(x -> x.recvars.stream())
				.collect(Collectors.toSet());
		return new ReachabilityEnv(postcont, recvars);
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
