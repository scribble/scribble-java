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

import org.scribble.protocol.monitor.model.Description;
import org.scribble.protocol.monitor.model.Fork;
import org.scribble.protocol.monitor.model.Join;
import org.scribble.protocol.monitor.model.LinkDeclaration;
import org.scribble.protocol.monitor.model.MessageNode;
import org.scribble.protocol.monitor.model.Parallel;
import org.scribble.protocol.monitor.model.Scope;
import org.scribble.protocol.monitor.model.SendMessage;
import org.scribble.protocol.monitor.model.ReceiveMessage;
import org.scribble.protocol.monitor.model.Node;
import org.scribble.protocol.monitor.model.Path;
import org.scribble.protocol.monitor.model.Do;

// NOTES:
// All top level event processes have the same structure:
// (1) They process all node indexes associated with the context to check for match
// (2) If match not found, then process any nested conversations
// (3) If nested conversation returns match, then check if it is finished, and if so remove from parent context
// Outside scope of monitor, the environment should check that when a match is found, whether the associated
// context is finished, to remove the top level conversation instance monitoring.

/**
 * This class provides a default implementation of the protocol monitor.
 *
 */
public class DefaultProtocolMonitor implements ProtocolMonitor {
    
    private static final Logger LOG=Logger.getLogger(DefaultProtocolMonitor.class.getName());
    
    /**
     * Default constructor.
     */
    public DefaultProtocolMonitor() {
    }

    /**
     * This method creates a new session (conversation instance) and initializes
     * it based on the supplied description.
     * 
     * @param context The monitor context
     * @param protocol The protocol description
     * @param sessionClass The session implenentation class to instantiate
     * @return The created and initialized session
     */
    public Session createSession(MonitorContext context, Description protocol,
                            Class<? extends Session> sessionClass) {
        Session ret=null;
        
        try {
            ret = sessionClass.newInstance();
            
            // Initialize the session with the protocol descriptions
            if (protocol.getNode().size() > 0) {
                addNodeToConversation(context, protocol, ret, 0);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Failed to create and initialize session '"
                        +sessionClass+"'", e);
        }
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public Result messageSent(MonitorContext context, Description protocol,
                        Session session, Message mesg) {
        Result ret=Result.NOT_HANDLED;
    
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("messageSent start: session="+session+" mesg="+mesg);
        }
        
        if (session == null) {
        	return(new Result(false, "Cannot monitor message due to no session instance"));
        }
        
        // Check if context has state that is waiting for a send message
        for (int i=0; ret == Result.NOT_HANDLED && i < session.getNumberOfNodeIndexes(); i++) {
            ret = checkForSendMessage(context, protocol,
                        i, session.getNodeIndexAt(i), session, mesg);
        }
        
        for (int i=0; ret == Result.NOT_HANDLED && i < session.getNestedConversations().size(); i++) {
            Session nested=session.getNestedConversations().get(i);
            
            ret = messageSent(context, protocol, nested, mesg);
            
            if (ret .isValid()) {
            
                // If nested conversation has a 'main' conversation, then check that it
                // has been terminated
                // TODO: Issue is efficiency of doing this check for each message?
                if (nested.getMainConversation() != null) {
                    Session main=nested.getMainConversation();
                    
                    if (session.getNestedConversations().remove(main)) {
                        
                        // Terminate other catch blocks
                        for (Session cc : main.getInterruptConversations()) {
                            if (cc != nested) {
                                session.getNestedConversations().remove(cc);
                            }
                        }
                    }
                }
                
                // If matched, then check if nested conversation has finished
                if (nested.isFinished()) {
                    nestedConversationFinished(context, protocol, session, nested);
                }
            }
        }
        
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("messageSent end: session="+session+" mesg="+mesg+" ret="+ret);
        }
        
        return (ret);
    }
    
    /**
     * This method checks whether the send message is valid.
     * 
     * @param context The monitor context
     * @param protocol The protocol
     * @param pos The position of the node in the conversation
     * @param nodeIndex The nodex index in the protocol
     * @param conv The conversation
     * @param mesg The message
     * @return The result
     */
    protected Result checkForSendMessage(MonitorContext context, Description protocol,
            int pos, int nodeIndex, Session conv, Message mesg) {
        Result ret=Result.NOT_HANDLED;
        
        Node node=protocol.getNode().get(nodeIndex);
        
        if (node instanceof SendMessage) {
            ret = checkMessage(context, conv, (SendMessage)node, mesg);
            
            if (ret.isValid()) {
                // Remove processed node
                conv.removeNodeIndexAt(pos);
                
                // Add next node, if not end
                if (node.getNextIndex() != -1) {
                    addNodeToConversation(context, protocol, conv, node.getNextIndex());
                }
            }
            
        } else if (node instanceof org.scribble.protocol.monitor.model.Choice) {
            // Check if lookahead for the indexes associated with each
            // choice path
            for (int j=0; ret == Result.NOT_HANDLED
                    && j < ((org.scribble.protocol.monitor.model.Choice)node).getPath().size(); j++) {
                
                Path cn=((org.scribble.protocol.monitor.model.Choice)node).getPath().get(j);
                
                if (cn.getNextIndex() != -1) {
                    ret = checkForSendMessage(context, protocol,
                                pos, cn.getNextIndex(), conv, mesg);
                }
            }
            
        } else if (node instanceof org.scribble.protocol.monitor.model.Decision) {
            
            if (((org.scribble.protocol.monitor.model.Decision)node).getInnerIndex() != -1) {
                ret = checkForSendMessage(context, protocol,
                            pos, ((org.scribble.protocol.monitor.model.Decision)node).getInnerIndex(),
                            conv, mesg);
            }
            
            if (ret == Result.NOT_HANDLED && node.getNextIndex() != -1) {
                ret = checkForSendMessage(context, protocol,
                            pos, node.getNextIndex(), conv, mesg);                
            }
        } else if (node instanceof Join) {
        	Join join=(Join)node;
        	
        	Boolean result=context.evaluate(conv, join.getExpression());
        	
            if (result != null && result.booleanValue()) {

                // Add next node, if not end
                if (node.getNextIndex() != -1) {
                    ret = checkForSendMessage(context, protocol,
                            pos, node.getNextIndex(), conv, mesg);
                    
                    if (!ret.isValid()) {
                    	// Remove node and add subsequent nodes - this makes sure
                    	// the join is not processed again next time
                        conv.removeNodeIndexAt(pos);
                        addNodeToConversation(context, protocol, conv, node.getNextIndex());
                    }
                } else {
                    // Remove processed node
                    conv.removeNodeIndexAt(pos);
                }
            }
        }

        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("checkForSendMessage: pos="+pos+" nodeIndex="+nodeIndex
                    +" node="+node+" conv="+conv+" mesg="+mesg+" ret="+ret);
        }
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public Result messageReceived(MonitorContext context, Description protocol,
                    Session session, Message mesg) {
        Result ret=Result.NOT_HANDLED;
        
        if (session == null) {
        	return(new Result(false, "Cannot monitor message due to no session instance"));
        }
        
        // Check if context has state that is waiting for a send message
        for (int i=0; ret == Result.NOT_HANDLED && i < session.getNumberOfNodeIndexes(); i++) {
            ret = checkForReceiveMessage(context, protocol,
                        i, session.getNodeIndexAt(i), session, mesg);
        }
        
        for (int i=0; ret == Result.NOT_HANDLED && i < session.getNestedConversations().size(); i++) {
            Session nested=session.getNestedConversations().get(i);
            
            ret = messageReceived(context, protocol, nested, mesg);
            
            if (ret.isValid()) {
                
                // If nested conversation has a 'main' conversation, then check that it
                // has been terminated
                // TODO: Issue is efficiency of doing this check for each message?
                if (nested.getMainConversation() != null) {
                    Session main=nested.getMainConversation();
                    
                    if (session.getNestedConversations().remove(main)) {
                        
                        // Terminate other interrupt blocks
                        for (Session cc : main.getInterruptConversations()) {
                            if (cc != nested) {
                                session.getNestedConversations().remove(cc);
                            }
                        }
                    }
                }
                
                // If matched, then check if nested conversation has finished
                if (nested.isFinished()) {
                    nestedConversationFinished(context, protocol, session, nested);
                }                
            }
        }
        
        return (ret);
    }
    
    /**
     * This method checks whether the receive message is valid.
     * 
     * @param context The monitor context
     * @param protocol The protocol
     * @param pos The position of the node in the conversation
     * @param nodeIndex The nodex index in the protocol
     * @param conv The conversation
     * @param mesg The message
     * @return The result
     */
    protected Result checkForReceiveMessage(MonitorContext context, Description protocol,
                int pos, int nodeIndex, Session conv, Message mesg) {
        Result ret=Result.NOT_HANDLED;
        
        Node node=protocol.getNode().get(nodeIndex);
        
        if (node instanceof ReceiveMessage) {
            ret = checkMessage(context, conv, (ReceiveMessage)node, mesg);
            
            if (ret.isValid()) {
                // Remove processed node
                conv.removeNodeIndexAt(pos);
                
                // Add next node, if not end
                if (node.getNextIndex() != -1) {
                    addNodeToConversation(context, protocol, conv, node.getNextIndex());
                }
            }
            
        } else if (node instanceof org.scribble.protocol.monitor.model.Choice) {
            // Check if lookahead for the indexes associated with each
            // choice path
            for (int j=0; ret == Result.NOT_HANDLED 
                    && j < ((org.scribble.protocol.monitor.model.Choice)node).getPath().size(); j++) {
                
                Path cn=((org.scribble.protocol.monitor.model.Choice)node).getPath().get(j);
                
                if (cn.getNextIndex() != -1) {
                    ret = checkForReceiveMessage(context, protocol, 
                                pos, cn.getNextIndex(), conv, mesg);
                }
            }
            
        } else if (node instanceof org.scribble.protocol.monitor.model.Decision) {
            
            if (((org.scribble.protocol.monitor.model.Decision)node).getInnerIndex() != -1) {
                ret = checkForReceiveMessage(context, protocol, 
                            pos, ((org.scribble.protocol.monitor.model.Decision)node).getInnerIndex(),
                                conv, mesg);
            }
            
            if (ret == Result.NOT_HANDLED && node.getNextIndex() != -1) {
                ret = checkForReceiveMessage(context, protocol, 
                        pos, node.getNextIndex(), conv, mesg);                
            }
        }

        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("checkForReceiveMessage: pos="+pos+" nodeIndex="+nodeIndex
                    +" node="+node+" conv="+conv+" mesg="+mesg+" ret="+ret);
        }
        
        return (ret);
    }
    
    /**
     * This method checks the message.
     * 
     * @param context Monitor context
     * @param conv Conversation
     * @param node The message node
     * @param sig The message signature
     * @return The result
     */
    protected Result checkMessage(MonitorContext context, Session conv, MessageNode node,
                                Message sig) {
        Result ret=Result.NOT_HANDLED;
        
        if ((sig.getOperator() == null
                || node.getOperator() == null
                || node.getOperator().equals(sig.getOperator()))) {
            ret = context.validate(conv, node, sig);
        }
        
        return (ret);
    }
    
    /**
     * This method adds a node to the conversation.
     * 
     * @param context The context
     * @param protocol The protocol
     * @param session The conversation
     * @param nodeIndex The node index
     */
    protected void addNodeToConversation(MonitorContext context, Description protocol,
                    Session session, int nodeIndex) {
        
        // Check if specified node is a 'Run' node type
        Node node=protocol.getNode().get(nodeIndex);

        if (node.getClass() == Scope.class) {
            initScope(context, protocol, session, (Scope)node);
            
        } else if (node.getClass() == Do.class) {
            Session nested=initScope(context, protocol, session, (Do)node);
            
            // TODO: Need to register catch blocks against the nested context in the
            // parent context
            for (Path path : ((Do)node).getPath()) {
                
                Session interruptScope=session.createInterruptConversation(nested, node.getNextIndex());
                interruptScope.addNodeIndex(path.getNextIndex());
            }
            
        } else if (node.getClass() == org.scribble.protocol.monitor.model.Call.class) {
            
            if (((org.scribble.protocol.monitor.model.Call)node).getCallIndex() != -1) {
                addNodeToConversation(context, protocol, session,
                        ((org.scribble.protocol.monitor.model.Call)node).getCallIndex());
            }
            
            if (node.getNextIndex() != -1) {
                addNodeToConversation(context, protocol, session, node.getNextIndex());
            }

        } else if (node.getClass() == org.scribble.protocol.monitor.model.Parallel.class) {
            
            if (node.getNextIndex() != -1) {
                Session nestedContext=session.createNestedConversation(node.getNextIndex());
                
                Parallel pnode=(Parallel)node;
                
                // TODO: Check what happens if no activities in the parallel paths?
                
                for (Path path : pnode.getPath()) {
                    if (path.getNextIndex() != -1) {
                        addNodeToConversation(context, protocol, nestedContext, path.getNextIndex());
                    }
                }
            }
        } else if (node.getClass() == org.scribble.protocol.monitor.model.LinkDeclaration.class) {
        	LinkDeclaration linkDecl=(LinkDeclaration)node;
        	
        	session.declareLink(linkDecl.getName());
        	
            if (node.getNextIndex() != -1) {
                addNodeToConversation(context, protocol, session, node.getNextIndex());
            }
       	
        } else if (node.getClass() == Fork.class) {
        	Fork fork=(Fork)node;
        	
        	if (context.fork(session, fork.getLinkName(), fork.getCondition())) {
            	
                if (node.getNextIndex() != -1) {
                    addNodeToConversation(context, protocol, session, node.getNextIndex());
                }        		
        	} else {
        		// Block until the fork condition can be evaluated
                session.addNodeIndex(nodeIndex);        		
        	}
          	
        } else if (node.getClass() == Join.class) {
        	Join join=(Join)node;
        	
        	Boolean result=context.evaluate(session, join.getExpression());
        	
            if (result == null) {
        		// Block until the join expression can be evaluated
                session.addNodeIndex(nodeIndex);
                
            } else if (result.booleanValue() && node.getNextIndex() != -1) {
                addNodeToConversation(context, protocol, session, node.getNextIndex());
            }        		
           	
        } else {
            session.addNodeIndex(nodeIndex);
        }
    }

    /**
     * This method initializes the supplied scope within the supplied context. This involves
     * creating a nested context, which is subsequently returned.
     * 
     * @param context The context
     * @param protocol The protocol description
     * @param conv The current context
     * @param scope The scope to be initialized
     * @return The new nested context, or null if failed
     */
    protected Session initScope(MonitorContext context, Description protocol, Session conv, Scope scope) {
        Session nestedContext=null;
        
        // Check if internal or external run
        if (scope.getInnerIndex() != -1) {
            // Internal
            nestedContext = conv.createNestedConversation(scope.getNextIndex());
            
            addNodeToConversation(context, protocol, nestedContext, scope.getInnerIndex());
            
        } else {
            // External - protocol name is in the 'name' field
            System.err.println("EXTERNAL PROTOCOL MONITORING NOT CURRENTLY SUPPORTED");
        }
        
        return (nestedContext);
    }
    
    /**
     * This method is called when a nested conversation finishes.
     * 
     * @param context The context
     * @param protocol The protocol
     * @param conv The conversation
     * @param nested The nested conversation
     */
    protected void nestedConversationFinished(MonitorContext context, Description protocol,
                    Session conv, Session nested) {
        // Check if nested conversation has a return index
        if (nested.getReturnIndex() != -1) {
            addNodeToConversation(context, protocol, conv, nested.getReturnIndex());
        }
        
        // Remove nested conversation from parent context
        conv.removeNestedConversation(nested);        
        
        // Cancel dependent contexts
        // TODO: Note this implies immediate cancellation of catch blocks. If
        // we want to delay until subsequent activity, or explicit control message,
        // then will need to change. Issue with subsequent activity is that it
        // may not be within the containing context.
        for (Session cc : nested.getInterruptConversations()) {
            conv.removeNestedConversation(cc);
        }
    }    
}
