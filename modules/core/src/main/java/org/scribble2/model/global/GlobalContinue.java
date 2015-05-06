package org.scribble2.model.global;

import org.scribble2.model.Continue;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.SimpleKindedNameNode;
import org.scribble2.sesstype.kind.RecursionVarKind;

public class GlobalContinue extends Continue implements SimpleGlobalInteractionNode
{
	//public GlobalContinue(RecursionVarNode recvar)
	public GlobalContinue(SimpleKindedNameNode<RecursionVarKind> recvar)
	{
		//this(t, recvar, null, null);
		super(recvar);
	}

	@Override
	//protected GlobalContinue reconstruct(RecursionVarNode recvar)
	protected GlobalContinue reconstruct(SimpleKindedNameNode<RecursionVarKind> recvar)
	{
		ModelDelegate del = del();
		GlobalContinue gc = new GlobalContinue(recvar);//, sicontext, env);
		gc = (GlobalContinue) gc.del(del);
		return gc;
	}

	@Override
	protected GlobalContinue copy()
	{
		return new GlobalContinue(this.recvar);
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
