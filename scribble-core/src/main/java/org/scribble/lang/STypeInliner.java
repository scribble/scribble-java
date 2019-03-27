package org.scribble.lang;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.job.Job;
import org.scribble.type.SubprotoSig;
import org.scribble.type.name.RecVar;

public class STypeInliner
{
	public final Job job;
	
	private final Deque<SubprotoSig> stack = new LinkedList<>();

  // To handle recvar shadowing in nested recs
	private Map<RecVar, Deque<RecVar>> recvars = new HashMap<>();
	private Map<RecVar, Integer> counter = new HashMap<>();

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
	
	// For Protocol/Do -- sig is for the current innermost proto (but not implicitly peek() for G/LDo convenience)
	public RecVar getInlinedRecVar(SubprotoSig sig)
	{
		String lab = "__" + sig.fullname.toString().replaceAll("\\.", "_") + "__"
				+ sig.roles.stream().map(x -> x.toString())
						.collect(Collectors.joining("_"))
				+ "__" + sig.args.stream().map(x -> x.toString())
						.collect(Collectors.joining("_"));
		return new RecVar(lab);
	}
	
	public RecVar enterRec(//SubprotoSig sig, 
			RecVar rv)
	{
		String text = getInlinedRecVar(this.stack.peek()) + "__" + rv;
		if (this.counter.containsKey(rv))
		{
			int i = this.counter.get(rv) + 1;
			text += "_" + i;
			this.counter.put(rv, i);
		}
		else
		{
			this.counter.put(rv, 0);
		}
		RecVar res = new RecVar(text);
		Deque<RecVar> tmp = this.recvars.get(rv);
		if (tmp == null)
		{
			tmp = new LinkedList<>();
			this.recvars.put(rv, tmp);
		}
		tmp.push(res);
		return res;
	}
	
	// For Continue
	public RecVar getInlinedRecVar(RecVar rv)
	{
		return this.recvars.get(rv).peek();
	}
	
	public void exitRec(RecVar rv)
	{
		this.recvars.get(rv).pop();
	}
}
