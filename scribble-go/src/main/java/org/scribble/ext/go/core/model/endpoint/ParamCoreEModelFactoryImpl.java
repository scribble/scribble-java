package org.scribble.ext.go.core.model.endpoint;

import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreESend;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.model.endpoint.EModelFactoryImpl;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public class ParamCoreEModelFactoryImpl extends EModelFactoryImpl implements ParamCoreEModelFactory
{

	@Override
	public ESend newESend(Role peer, MessageId<?> mid, Payload payload)
	{
		throw new RuntimeException("[param-core] Shouldn't get in here: ");
	}

	@Override
	public EReceive newEReceive(Role peer, MessageId<?> mid, Payload payload)
	{
		throw new RuntimeException("[param-core] Shouldn't get in here: ");
	}

	@Override
	public ParamCoreESend newParamCoreESend(ParamRole peer, MessageId<?> mid, Payload payload)
	{
		return new ParamCoreESend(this, peer, mid, payload);
	}

	@Override
	public ParamCoreEReceive newParamCoreEReceive(ParamRole peer, MessageId<?> mid, Payload payload)
	{
		return new ParamCoreEReceive(this, peer, mid, payload);
	}
}
