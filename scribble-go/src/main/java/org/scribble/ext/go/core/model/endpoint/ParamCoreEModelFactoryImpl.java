package org.scribble.ext.go.core.model.endpoint;

import org.scribble.ext.go.core.model.endpoint.action.ParamCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreECrossSend;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEDotReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEDotSend;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEMultiChoicesReceive;
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
	public ParamCoreECrossSend newParamCoreECrossSend(ParamRole peer, MessageId<?> mid, Payload payload)
	{
		return new ParamCoreECrossSend(this, peer, mid, payload);
	}

	@Override
	public ParamCoreECrossReceive newParamCoreECrossReceive(ParamRole peer, MessageId<?> mid, Payload payload)
	{
		return new ParamCoreECrossReceive(this, peer, mid, payload);
	}

	@Override
	public ParamCoreEDotSend newParamCoreEDotSend(ParamRole peer, MessageId<?> mid, Payload payload)
	{
		return new ParamCoreEDotSend(this, peer, mid, payload);
	}

	@Override
	public ParamCoreEDotReceive newParamCoreEDotReceive(ParamRole peer, MessageId<?> mid, Payload payload)
	{
		return new ParamCoreEDotReceive(this, peer, mid, payload);
	}

	@Override
	public ParamCoreEMultiChoicesReceive newParamCoreEMultiChoicesReceive(ParamRole peer, MessageId<?> mid, Payload payload)
	{
		return new ParamCoreEMultiChoicesReceive(this, peer, mid, payload);
	}
}
