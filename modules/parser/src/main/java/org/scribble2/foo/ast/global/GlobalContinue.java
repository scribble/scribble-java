package org.scribble2.foo.ast.global;

import org.antlr.runtime.Token;
import org.scribble2.foo.ast.Continue;
import org.scribble2.foo.ast.name.simple.RecursionVarNode;

public class GlobalContinue extends Continue implements GlobalInteraction
{
	public GlobalContinue(Token t, RecursionVarNode recvar)
	{
		//this(t, recvar, null, null);
		super(t, recvar);
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
	protected GlobalContinue reconstruct(CommonTree ct, RecursionVarNode recvar, SimpleInteractionNodeContext sicontext, Env env)
	{
		return new GlobalContinue(ct, recvar, sicontext, env);
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