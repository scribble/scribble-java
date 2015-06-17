package org.scribble.ast.global;

import org.scribble.ast.Continue;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

public class GContinue extends Continue<Global> implements GSimpleInteractionNode
{
	public GContinue(RecVarNode recvar)
	{
		//this(t, recvar, null, null);
		super(recvar);
	}

	@Override
	protected GContinue reconstruct(RecVarNode recvar)
	{
		ScribDel del = del();
		GContinue gc = new GContinue(recvar);//, sicontext, env);
		gc = (GContinue) gc.del(del);
		return gc;
	}

	@Override
	protected GContinue copy()
	{
		return new GContinue(this.recvar);
	}

	/*protected GlobalContinue(CommonTree ct, RecursionVarNode recvar, SimpleInteractionNodeContext sicontext)
	{
		super(ct, recvar, sicontext);
	}*/

	/*protected GlobalContinue(CommonTree ct, RecursionVarNode recvar, SimpleInteractionNodeContext sicontext, Env env)
	{
		super(ct, recvar, sicontext, env);
	}
	
	@Override
	public GlobalContinue leaveProjection(Projector proj) //throws ScribbleException
	{
		RecursionVarNode recvar = new RecursionVarNode(null, this.recvar.toName().toString());
		LocalContinue projection = new LocalContinue(null, recvar);
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}

	/*@Override
	public GlobalContinue checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		RecursionLabel lab = this.lab.toName();
		if (!wfc.getEnv().labs.isEnabled(lab))
		{
			throw new ScribbleException("Bad continue label: " + lab);
		}
		return (GlobalContinue) super.checkWellFormedness(wfc);
	}
	
	@Override
	public LocalContinue project(Projector proj)
	{
		return new LocalContinue(null, this.lab);
	}*/
	
	/*@Override
	public GlobalContinue visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Continue cont = super.visitChildren(nv);
		return new GlobalContinue(cont.ct, cont.recvar, cont.getContext(), cont.getEnv());
	}*/
}
