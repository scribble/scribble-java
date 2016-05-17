package org.scribble.model.global;

public class GModel
{
	public final GModelState init;
	public final GModelState term;
	
	public GModel(GModelState init, GModelState term)
	{
		this.init = init;
		this.term = term;
	}
	
	@Override
	public String toString()
	{
		return this.init.toDot();
	}
}
