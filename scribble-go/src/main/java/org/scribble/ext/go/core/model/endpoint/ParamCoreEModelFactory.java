package org.scribble.ext.go.core.model.endpoint;

import org.scribble.ext.go.core.model.endpoint.action.ParamCoreEReceive;
import org.scribble.ext.go.core.model.endpoint.action.ParamCoreESend;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.type.Payload;
import org.scribble.type.name.MessageId;

public interface ParamCoreEModelFactory extends EModelFactory
{

	ParamCoreESend newParamCoreESend(ParamRole peer, MessageId<?> mid, Payload payload);
	ParamCoreEReceive newParamCoreEReceive(ParamRole peer, MessageId<?> mid, Payload payload);
}
