package org.scribble.ext.f17.model.endpoint;

import java.util.Set;

import org.scribble.model.endpoint.EGraphBuilderUtil;
import org.scribble.sesstype.name.RecVar;

public class F17EGraphBuilderUtil extends EGraphBuilderUtil
{
	public F17EGraphBuilderUtil()
	{
		super();
	}
	
	@Override
	public F17EState newState(Set<RecVar> labs)
	{
		return new F17EState();
	}
}
