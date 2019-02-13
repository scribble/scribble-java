package org.scribble.lang;

import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.scribble.job.Job;
import org.scribble.type.SubprotoSig;
import org.scribble.type.name.RecVar;

public class STypeInliner
{
	public final Job job;
	
	private final Deque<SubprotoSig> stack = new LinkedList<>();

	public STypeInliner(Job job)
	{
		this.job = job;
	}

	public void pushSig(SubprotoSig sig)
	{
		if (this.stack.contains(sig))
		{
			throw new RuntimeException("Shouldn't get here: " + sig);
		}
		this.stack.push(sig);
	}

	public SubprotoSig peek()
	{
		return this.stack.peek();
	}

	public boolean hasSig(SubprotoSig sig)
	{
		return this.stack.contains(sig);
	}
	
	public void popSig()
	{
		this.stack.pop();
	}
	
	// sig is the for the current innermost proto
	public RecVar makeRecVar(SubprotoSig sig)
	{
		String lab = "__" + sig.fullname + "__"
				+ sig.roles.stream().map(x -> x.toString())
						.collect(Collectors.joining("_"))
				+ "__" + sig.args.stream().map(x -> x.toString())
						.collect(Collectors.joining("_"));
		return new RecVar(lab);
	}

	public RecVar makeRecVar(//SubprotoSig sig, 
			RecVar rv)
	{
		return new RecVar(makeRecVar(this.stack.peek()).toString() + "__" + rv);
	}
}
