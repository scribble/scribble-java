package org.scribble2.model.visit.env;

import org.scribble2.fsm.ScribbleFSM;

public class FsmBuildingEnv extends Env
{
	private ScribbleFSM fsm;

	public FsmBuildingEnv()
	{

	}

	public FsmBuildingEnv setFsm(ScribbleFSM fsm)
	{
		FsmBuildingEnv copy = copy();
		copy.fsm = fsm;
		return copy;
	}
	
	public ScribbleFSM getFsm()
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
