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

	private static final int VERSION=1;
	
	private java.util.List<Integer> m_nodeIndexes=new java.util.Vector<Integer>();
	private int m_returnIndex=-1;
	private Session m_parentConversation=null;
	private java.util.List<Session> m_nestedConversations=new java.util.Vector<Session>();
	private java.util.List<Session> m_interruptConversations=new java.util.Vector<Session>();

	public DefaultSession() {
	}

	protected DefaultSession(int returnIndex) {
		m_returnIndex = returnIndex;
	}
	
	protected DefaultSession(Session main, int returnIndex) {
		m_parentConversation = main;
		m_returnIndex = returnIndex;
	}
	
	public void addNodeIndex(int index) {
		m_nodeIndexes.add(index);
	}
	
	public void removeNodeIndexAt(int pos) {
		m_nodeIndexes.remove(pos);
	}
	
	public int getNumberOfNodeIndexes() {
		return(m_nodeIndexes.size());
	}
	
	public int getNodeIndexAt(int pos) {
		return(m_nodeIndexes.get(pos));
	}
	
	public boolean isFinished() {
		return(m_nodeIndexes.size() == 0 && m_nestedConversations.size() == 0);
	}

	// Nested Conversation Management
	
	public Session createNestedConversation(int returnIndex) {
		DefaultSession ret=new DefaultSession(returnIndex);
		
		m_nestedConversations.add(ret);
		
		return(ret);
	}
	
	public Session createInterruptConversation(Session main, int returnIndex) {
		DefaultSession ret=new DefaultSession(main, returnIndex);
		
		m_nestedConversations.add(ret);
		main.getInterruptConversations().add(ret);
		
		return(ret);
	}
	
	public int getReturnIndex() {
		return(m_returnIndex);
	}
	
	public void setReturnIndex(int returnIndex) {
		m_returnIndex = returnIndex;
	}
	
	public java.util.List<Session> getNestedConversations() {
		return(m_nestedConversations);
	}
	
	public void removeNestedConversation(Session context) {
		m_nestedConversations.remove(context);
	}
	
	public Session getParentConversation() {
		return(m_parentConversation);
	}
	
	public java.util.List<Session> getInterruptConversations() {
		return(m_interruptConversations);
	}

	public void readExternal(ObjectInput ois) throws IOException,
			ClassNotFoundException {
		@SuppressWarnings("unused")
		int version=ois.readInt();
		
		int nodeIndexes=ois.readInt();
		for (int i=0; i < nodeIndexes; i++) {
			m_nodeIndexes.add(ois.readInt());
		}
		
		m_returnIndex = ois.readInt();
		
		m_parentConversation=(Session)ois.readObject();
		
		int nestedSize=ois.readInt();
		for (int i=0; i < nestedSize; i++) {
			m_nestedConversations.add((Session)ois.readObject());
		}
		
		int interruptSize=ois.readInt();
		for (int i=0; i < interruptSize; i++) {
			m_interruptConversations.add((Session)ois.readObject());
		}
	}

	public void writeExternal(ObjectOutput oos) throws IOException {
		oos.writeInt(VERSION);
		
		oos.writeInt(m_nodeIndexes.size());
		for (int index : m_nodeIndexes) {
			oos.writeInt(index);
		}
		
		oos.writeInt(m_returnIndex);
		
		oos.writeObject(m_parentConversation);
		
		oos.writeInt(m_nestedConversations.size());
		for (Session session : m_nestedConversations) {
			oos.writeObject(session);
		}
		
		oos.writeInt(m_interruptConversations.size());
		for (Session session : m_interruptConversations) {
			oos.writeObject(session);
		}
	}
}
