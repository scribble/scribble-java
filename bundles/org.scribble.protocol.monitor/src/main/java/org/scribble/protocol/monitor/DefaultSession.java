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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * This class represents the default implementation of the conversation
 * interface.
 *
 */
public class DefaultSession implements Session, java.io.Externalizable {

    /**
     * Serialization version.
     */
    private static final int VERSION=1;
    
    private java.util.List<Integer> _nodeIndexes=new java.util.Vector<Integer>();
    private int _returnIndex=-1;
    private Session _parentConversation=null;
    private java.util.List<Session> _nestedConversations=new java.util.Vector<Session>();
    private java.util.List<Session> _interruptConversations=new java.util.Vector<Session>();

    /**
     * Default constructor.
     */
    public DefaultSession() {
    }

    /**
     * This constructor is initialized with the return index for use when the
     * nested session completes.
     * 
     * @param returnIndex The return index
     */
    protected DefaultSession(int returnIndex) {
        _returnIndex = returnIndex;
    }
    
    /**
     * This constructor is initialized with the return index for use when the
     * nested session completes.
     * 
     * @param main The main session
     * @param returnIndex The return index
     */
    protected DefaultSession(Session main, int returnIndex) {
        _parentConversation = main;
        _returnIndex = returnIndex;
    }
    
    /**
     * {@inheritDoc}
     */
    public void addNodeIndex(int index) {
        _nodeIndexes.add(index);
    }
    
    /**
     * {@inheritDoc}
     */
    public void removeNodeIndexAt(int pos) {
        _nodeIndexes.remove(pos);
    }
    
    /**
     * {@inheritDoc}
     */
    public int getNumberOfNodeIndexes() {
        return (_nodeIndexes.size());
    }
    
    /**
     * {@inheritDoc}
     */
    public int getNodeIndexAt(int pos) {
        return (_nodeIndexes.get(pos));
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isFinished() {
        return (_nodeIndexes.size() == 0 && _nestedConversations.size() == 0);
    }

    // Nested Conversation Management
    
    /**
     * {@inheritDoc}
     */
    public Session createNestedConversation(int returnIndex) {
        DefaultSession ret=new DefaultSession(returnIndex);
        
        _nestedConversations.add(ret);
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public Session createInterruptConversation(Session main, int returnIndex) {
        DefaultSession ret=new DefaultSession(main, returnIndex);
        
        _nestedConversations.add(ret);
        main.getInterruptConversations().add(ret);
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public int getReturnIndex() {
        return (_returnIndex);
    }
    
    /**
     * {@inheritDoc}
     */
    public void setReturnIndex(int returnIndex) {
        _returnIndex = returnIndex;
    }
    
    /**
     * {@inheritDoc}
     */
    public java.util.List<Session> getNestedConversations() {
        return (_nestedConversations);
    }
    
    /**
     * {@inheritDoc}
     */
    public void removeNestedConversation(Session session) {
        _nestedConversations.remove(session);
    }
    
    /**
     * {@inheritDoc}
     */
    public Session getParentConversation() {
        return (_parentConversation);
    }
    
    /**
     * {@inheritDoc}
     */
    public java.util.List<Session> getInterruptConversations() {
        return (_interruptConversations);
    }

    /**
     * {@inheritDoc}
     */
    public void readExternal(ObjectInput ois) throws IOException,
            ClassNotFoundException {
        @SuppressWarnings("unused")
        int version=ois.readInt();
        
        int nodeIndexes=ois.readInt();
        for (int i=0; i < nodeIndexes; i++) {
            _nodeIndexes.add(ois.readInt());
        }
        
        _returnIndex = ois.readInt();
        
        _parentConversation=(Session)ois.readObject();
        
        int nestedSize=ois.readInt();
        for (int i=0; i < nestedSize; i++) {
            _nestedConversations.add((Session)ois.readObject());
        }
        
        int interruptSize=ois.readInt();
        for (int i=0; i < interruptSize; i++) {
            _interruptConversations.add((Session)ois.readObject());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void writeExternal(ObjectOutput oos) throws IOException {
        oos.writeInt(VERSION);
        
        oos.writeInt(_nodeIndexes.size());
        for (int index : _nodeIndexes) {
            oos.writeInt(index);
        }
        
        oos.writeInt(_returnIndex);
        
        oos.writeObject(_parentConversation);
        
        oos.writeInt(_nestedConversations.size());
        for (Session session : _nestedConversations) {
            oos.writeObject(session);
        }
        
        oos.writeInt(_interruptConversations.size());
        for (Session session : _interruptConversations) {
            oos.writeObject(session);
        }
    }
}
