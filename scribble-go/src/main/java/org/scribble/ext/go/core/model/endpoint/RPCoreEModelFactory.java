package org.scribble.ext.go.core.model.endpoint;

import java.util.List;
import java.util.Set;

import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreECrossSend;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEDotReceive;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEDotSend;
import org.scribble.ext.go.core.model.endpoint.action.RPCoreEMultiChoicesReceive;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.ext.go.type.index.RPIndexExpr;
import org.scribble.ext.go.type.index.RPIndexVar;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;
import org.scribble.type.name.RecVar;

public interface RPCoreEModelFactory extends EModelFactory
{
	@Override
	RPCoreEState newEState(Set<RecVar> labs);

	RPCoreEState newRPCoreEState(Set<RecVar> labs, RPIndexVar param, RPInterval interval, RPCoreEState nested);

	RPCoreECrossSend newParamCoreECrossSend(RPIndexedRole peer, MessageId<?> mid, Payload payload);
	RPCoreECrossReceive newParamCoreECrossReceive(RPIndexedRole peer, MessageId<?> mid, Payload payload);
	RPCoreEDotSend newParamCoreEDotSend(RPIndexedRole peer, RPIndexExpr offset, MessageId<?> mid, Payload payload);
	RPCoreEDotReceive newParamCoreEDotReceive(RPIndexedRole peer, RPIndexExpr offset, MessageId<?> mid, Payload payload);
	RPCoreEMultiChoicesReceive newParamCoreEMultiChoicesReceive(RPIndexedRole peer,//MessageId<?> mid, Payload payload)
			List<MessageId<?>> mids, List<Payload> pays);
}
