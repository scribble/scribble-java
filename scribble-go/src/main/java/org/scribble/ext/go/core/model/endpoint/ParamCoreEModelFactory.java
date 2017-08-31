package org.scribble.ext.go.core.model.endpoint;

import java.util.List;

import org.scribble.ext.go.core.model.endpoint.action.ParamCoreECrossReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreECrossSend;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEDotReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEDotSend;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEMultiChoicesReceive;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.ext.go.type.index.ParamIndexExpr;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;

public interface ParamCoreEModelFactory extends EModelFactory
{

	ParamCoreECrossSend newParamCoreECrossSend(ParamRole peer, MessageId<?> mid, Payload payload);
	ParamCoreECrossReceive newParamCoreECrossReceive(ParamRole peer, MessageId<?> mid, Payload payload);
	ParamCoreEDotSend newParamCoreEDotSend(ParamRole peer, ParamIndexExpr offset, MessageId<?> mid, Payload payload);
	ParamCoreEDotReceive newParamCoreEDotReceive(ParamRole peer, ParamIndexExpr offset, MessageId<?> mid, Payload payload);
	ParamCoreEMultiChoicesReceive newParamCoreEMultiChoicesReceive(ParamRole peer,//MessageId<?> mid, Payload payload)
			List<MessageId<?>> mids, List<Payload> pays);
}
