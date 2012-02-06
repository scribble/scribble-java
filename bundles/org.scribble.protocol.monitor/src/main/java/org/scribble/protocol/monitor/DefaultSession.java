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
    private static final int VERSION=2;
    
    private java.util.List<Integer> _nodeIndexes=new java.util.Vector<Integer>();
    private int _returnIndex=-1;
    private Session _parentConversation=null;
    private Session _mainConversation=null;
    private java.util.List<Session> _nestedConversations=new java.util.Vector<Session>();
    private java.util.List<Session> _interruptConversations=new java.util.Vector<Session>();
    private java.util.Map<String,Object> _state=new java.util.HashMap<String,Object>();

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
        _mainConversation = main;
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
        
        // Establish parent conversation relationship
        ret._parentConversation = this;
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public Session createInterruptConversation(Session main, int returnIndex) {
        DefaultSession ret=new DefaultSession(main, returnIndex);
        
        _nestedConversations.add(ret);
        main.getInterruptConversations().add(ret);
        
        // Establish parent conversation relationship
        ret._parentConversation = this;
        
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
    public Session getMainConversation() {
        return (_mainConversation);
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
    public void declareLink(String linkName) {
    	// Create a placeholder within the current session
    	setStateValue(linkName, null);
    }

    /**
     * This method returns the session that declares the named state.
     * 
     * @param name The name
     * @return The session, or null if the state is not declared in
     *            the scope of this session or its parents
     */
    protected DefaultSession getDeclaredSession(String name) {
    	if (_state.containsKey(name)) {
    		return (this);
    	} else if (getParentConversation() instanceof DefaultSession) {
    		return (((DefaultSession)getParentConversation()).getDeclaredSession(name));
    	}
    	
    	return (null);
    }
    
    /**
     * {@inheritDoc}
     */
    public Object getState(String name) {
    	DefaultSession sess=getDeclaredSession(name);
    	
    	if (sess != null) {
    		return (sess.getStateValue(name));
    	}
    	
    	return (null);
    }
    
    /**
     * This method returns the value associated with
     * the supplied state name.
     * 
     * @param name The name
     * @return The value
     */
    protected Object getStateValue(String name) {
    	return (_state.get(name));
    }
    
    /**
     * This method sets the value associated with
     * the supplied state name.
     * 
     * @param name The name
     * @param value The value
     */
    protected void setStateValue(String name, Object value) {
    	_state.put(name, (java.io.Serializable)value);
    }
    
    /**
     * {@inheritDoc}
     */
    public void setState(String name, Object value) {
    	DefaultSession sess=getDeclaredSession(name);
    	
    	if (sess == null) {
    		sess = this;
    	}
    	
    	sess.setStateValue(name, value);
    }
    
    /**
     * This method returns a map defining the current state snapshot
     * associated with the session. It is readonly, and therefore
     * modifications to the map will not be applied to the session.
     * 
     * @return The state
     */
    public java.util.Map<String,Object> getState() {
    	java.util.Map<String,Object> ret=new java.util.HashMap<String,Object>();

    	applyState(ret);
    	
    	return (ret);
    }
    
    /**
     * This method applies the session state to the supplied map.
     * 
     * @param state The aggregated session state
     */
    protected void applyState(java.util.Map<String,Object> state) {
    	// Apply parent state first, so child can overwrite if appropriate
    	
    	if (_parentConversation instanceof DefaultSession) {
    		((DefaultSession)_parentConversation).applyState(state);
    	}
    	
    	state.putAll(_state);
    }
    
    /**
     * {@inheritDoc}
     */
    public void readExternal(ObjectInput ois) throws IOException,
            ClassNotFoundException {
        int version=ois.readInt();
        
        int nodeIndexes=ois.readInt();
        for (int i=0; i < nodeIndexes; i++) {
            _nodeIndexes.add(ois.readInt());
        }
        
        _returnIndex = ois.readInt();
        
        _mainConversation=(Session)ois.readObject();
        
        int nestedSize=ois.readInt();
        for (int i=0; i < nestedSize; i++) {
            _nestedConversations.add((Session)ois.readObject());
        }
        
        int interruptSize=ois.readInt();
        for (int i=0; i < interruptSize; i++) {
            _interruptConversations.add((Session)ois.readObject());
        }
        
        if (version > 1) {
        	_parentConversation=(Session)ois.readObject();

            int stateSize=ois.readInt();
            for (int i=0; i < stateSize; i++) {
            	String key=ois.readUTF();
                _state.put(key, ois.readObject());
            }
            
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
        
        oos.writeObject(_mainConversation);
        
        oos.writeInt(_nestedConversations.size());
        for (Session session : _nestedConversations) {
            oos.writeObject(session);
        }
        
        oos.writeInt(_interruptConversations.size());
        for (Session session : _interruptConversations) {
            oos.writeObject(session);
        }
        
        oos.writeObject(_parentConversation);
        
        oos.writeInt(_state.size());
        for (String key : _state.keySet()) {
            oos.writeUTF(key);
            oos.writeObject(_state.get(key));
        }
    }
}
