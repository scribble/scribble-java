package org.scribble.visit.env;

import org.scribble.model.local.ScribFsm;

@Deprecated
public class FsmBuildingEnv extends Env
{
	private ScribFsm fsm;

	public FsmBuildingEnv()
	{

	}

	public FsmBuildingEnv setFsm(ScribFsm fsm)
	{
		FsmBuildingEnv copy = copy();
		copy.fsm = fsm;
		return copy;
	}
	
	public ScribFsm getFsm()
	{
		return this.fsm;
	}

	@Override
	public FsmBuildingEnv copy()
	{
		FsmBuildingEnv copy = new FsmBuildingEnv();
		copy.fsm = this.fsm;
		return copy;
	}

	@Override
	public String toString()
	{
		return "fsm=" + this.fsm;
	}

	@Override
	public FsmBuildingEnv enterContext()
	{
		return copy();
	}
}
