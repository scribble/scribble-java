package org.scribble.lang.global;

import java.util.stream.Collectors;

import org.scribble.ast.ScribNode;
import org.scribble.del.global.GDel;
import org.scribble.job.Job;
import org.scribble.job.ScribbleException;
import org.scribble.type.SubprotoSig;
import org.scribble.type.name.ModuleName;
import org.scribble.type.name.RecVar;
import org.scribble.visit.SimpleVisitor;

// CHECKME: move to visit package?
public class GTypeTranslator extends SimpleVisitor<GType>
{
	public GTypeTranslator(Job job, ModuleName mod)
	{
		super(job, mod);
	}

	@Override
	public GType visit(ScribNode n) throws ScribbleException
	{
		return ((GDel) n.del()).translate(n, this);
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

	public RecVar makeRecVar(SubprotoSig sig, RecVar rv)
	{
		return new RecVar(makeRecVar(sig).toString() + "__" + rv);
	}
}
