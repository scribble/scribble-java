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

/**
 * This interface represents a session being monitored, which relates
 * to a protocol evaluating events that belong to a conversation
 * instance.
 *
 */
public interface Session {

    /**
     * This method adds a node index to the session.
     * 
     * @param index The index
     */
    public void addNodeIndex(int index);
    
    /**
     * This method removes a node index to the session.
     * 
     * @param index The index
     */
    public void removeNodeIndexAt(int index);
    
    /**
     * This method returns the number of node indexes associated
     * with the session.
     * 
     * @return The number of node indexes
     */
    public int getNumberOfNodeIndexes();
    
    /**
     * This method returns the node index at the specified position.
     * 
     * @param pos The position
     * @return The node index, or -1 if not found
     */
    public int getNodeIndexAt(int pos);
    
    /**
     * This method indicates whether the session has finished.
     * 
     * @return Whether the session has finished
     */
    public boolean isFinished();
    
    /**
     * This method creates a nested conversation that should return
     * to the supplied index when it completes.
     * 
     * @param returnIndex The return index
     * @return The nested session
     */
    public Session createNestedConversation(int returnIndex);
    
    /**
     * This method creates a nested conversation, associated with an
     * interrupt scope, that should return to the supplied index
     * when it completes.
     * 
     * @param main The main session
     * @param returnIndex The return index
     * @return The session
     */
    public Session createInterruptConversation(Session main, int returnIndex);
    
    /**
     * This method retrieves the 'return' index associated with a
     * nested session.
     * 
     * @return The return index, or -1 if not set
     */
    public int getReturnIndex();
    
    /**
     * This method returns the list of nested conversations.
     * 
     * @return The list of nested conversations
     */
    public java.util.List<Session> getNestedConversations();
    
    /**
     * This method returns the parent conversation.
     * 
     * @return The parent conversation
     */
    public Session getParentConversation();
    
    /**
     * This method returns the list of interrupt conversations.
     * 
     * @return The interrupt conversations
     */
    public java.util.List<Session> getInterruptConversations();
    
    /**
     * This method removes the nested session.
     * 
     * @param session The session to be removed
     */
    public void removeNestedConversation(Session session);
    
}
