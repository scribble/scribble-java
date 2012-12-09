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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.scribble.protocol.monitor.model.MessageNode;
import org.scribble.protocol.monitor.model.MessageType;

/**
 * This class provides a default implementation of the monitor context.
 *
 */
public class DefaultMonitorContext implements MonitorContext {

    private static final Logger LOG=Logger.getLogger(DefaultMonitorContext.class.getName());

    /**
     * {@inheritDoc}
     */
    public Result validate(Session session, MessageNode mesgNode, Message mesg) {
        // Do direct comparison for now, but could also check for derived
        // types
        Result ret=Result.NOT_HANDLED;
        
        if (mesgNode.getMessageType().size() > 0) {
            
            if (mesgNode.getMessageType().size() == mesg.getTypes().size()) {
                ret = Result.VALID;
                
                // If message type defined on message node, then compare against it
                for (int i=0; i < mesgNode.getMessageType().size(); i++) {
                    MessageType mt=mesgNode.getMessageType().get(i);
                    
                    if (mt.getValue() == null || !mt.getValue().equals(mesg.getTypes().get(i))) {
                        ret = Result.NOT_HANDLED;
                        break;
                    }
                }
            } else if (LOG.isLoggable(Level.FINEST)) {
                LOG.finest("Number of message types different ("+mesgNode.getMessageType().size()+" : "
                            +mesg.getTypes().size()+")");
            }
        } else {
            // If not message types, then the operator names must be specified
            // and equivalent
            if (mesgNode.getOperator() != null
                    && mesg.getOperator() != null
                    && mesgNode.getOperator().equals(mesg.getOperator())) {
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
        
        if (LOG.isLoggable(Level.FINEST)) {
            LOG.finest("Session ("+session+") validate message '"+mesg+"' against node "
                        +mesgNode+" ret = "+ret);
        }
        
        return (ret);
    }

    /**
     * {@inheritDoc}
     */
	public Boolean evaluate(Session session, String expression) {
		Boolean ret=null;
		
		ret = evalOR(session, expression);
		
		return (ret);
	}
	
	protected Boolean evalOR(Session session, String expression) {
		Boolean ret=Boolean.FALSE;
		
		String[] ors=expression.split("or");
		
		for (String or : ors) {
			Boolean result=evalAND(session, or.trim());
			
			if (result == null) {
				ret = null;
			} else if (result != null && result.booleanValue()) {
				ret = Boolean.TRUE;
				break;
			}
		}
		
		return(ret);
	}
	
	protected Boolean evalAND(Session session, String expression) {
		Boolean ret=null;
		
		String[] ands=expression.split("and");
		
		if (ands.length > 0) {
			ret = Boolean.TRUE;
			
			for (String and : ands) {
				Object result=session.getState(and.trim());
				
				if (result == null) {
					ret = null;
					break;
				} else if (!(result instanceof Boolean)) {
					ret = Boolean.FALSE;
					break;
				} else if (!((Boolean)result).booleanValue()) {
					ret = Boolean.FALSE;
					break;
				}
			}
		}
		
		return(ret);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean fork(Session session, String linkName, String condition) {
		// TODO Implement use of condition
		
		session.setState(linkName, Boolean.TRUE);
		
		return true;
	}
}
