package org.scribble.ext.go.core.model.endpoint;

import java.util.List;

import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossSend;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEDotReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEDotSend;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEMultiChoicesReceive;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.model.endpoint.EModelFactoryImpl;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.Role;

public class RPCoreEModelFactoryImpl extends EModelFactoryImpl implements RPCoreEModelFactory
{

	... // FIXME: new nested EState

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
	public RPCoreECrossSend newParamCoreECrossSend(RPIndexedRole peer, MessageId<?> mid, Payload payload)
	{
		return new RPCoreECrossSend(this, peer, mid, payload);
	}

	@Override
	public RPCoreECrossReceive newParamCoreECrossReceive(RPIndexedRole peer, MessageId<?> mid, Payload payload)
	{
		return new RPCoreECrossReceive(this, peer, mid, payload);
	}

	@Override
	public RPCoreEDotSend newParamCoreEDotSend(RPIndexedRole peer, RPIndexExpr offset, MessageId<?> mid, Payload payload)
	{
		return new RPCoreEDotSend(this, peer, offset, mid, payload);
	}

	@Override
	public RPCoreEDotReceive newParamCoreEDotReceive(RPIndexedRole peer, RPIndexExpr offset, MessageId<?> mid, Payload payload)
	{
		return new RPCoreEDotReceive(this, peer, offset, mid, payload);
	}

	@Override
	public RPCoreEMultiChoicesReceive newParamCoreEMultiChoicesReceive(RPIndexedRole peer,//MessageId<?> mid, Payload payload)
			List<MessageId<?>> mids, List<Payload> pays)
	{
		return new RPCoreEMultiChoicesReceive(this, peer, mids, pays);
	}
}
