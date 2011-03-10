/*
 * Copyright 2009-10 www.scribble.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.protocol.monitor;

import org.scribble.protocol.monitor.model.MessageNode;
import org.scribble.protocol.monitor.model.MessageType;

public class DefaultMonitorContext implements MonitorContext {

	/**
	 * This method determines whether the supplied message is valid
	 * in respect of the supplied message node.
	 * 
	 * @param mesgNode The message node
	 * @param mesg The message to be validated
	 * @return Whether the message is valid
	 */
	public Result validate(MessageNode mesgNode, Message mesg) {
		// Do direct comparison for now, but could also check for derived
		// types
		Result ret=Result.NOT_HANDLED;
		
		if (mesgNode.getMessageType().size() > 0) {
			
			if (mesgNode.getMessageType().size() == mesg.getTypes().size()) {
				ret = Result.VALID;
				
				// If message type defined on message node, then compare against it
				for (int i=0; i < mesgNode.getMessageType().size(); i++) {
					MessageType mt=mesgNode.getMessageType().get(i);
					
					if (mt.getValue() == null || mt.getValue().equals(mesg.getTypes().get(i)) == false) {
						ret = Result.NOT_HANDLED;
						break;
					}
				}
			}
		} else {
			// If not message types, then the operator names must be specified
			// and equivalent
			if (mesgNode.getOperator() != null &&
					mesg.getOperator() != null &&
					mesgNode.getOperator().equals(mesg.getOperator())) {
				ret = Result.VALID;
			}
		}
		
		// TODO: Could check for match of operator name, in which case indicating
		// the message type is mismatched - but issue is the same operator could
		// be used with different message types in the model - in which case
		// classifying the match as invalid, due to mismatching message type with
		// a particular operator, could then prevent the subsequent match against
		// another message node with the same operator name and correct message
		// type.
		
		return(ret);
	}
}
