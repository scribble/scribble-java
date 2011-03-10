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

	private java.util.List<Integer> m_nodeIndexes=new java.util.Vector<Integer>();
	private int m_returnIndex=-1;
	private Session m_mainConversation=null;
	private java.util.List<Session> m_nestedConversations=new java.util.Vector<Session>();
	private java.util.List<Session> m_catchConversations=new java.util.Vector<Session>();

	public DefaultSession() {
	}

	protected DefaultSession(int returnIndex) {
		m_returnIndex = returnIndex;
	}
	
	protected DefaultSession(Session main, int returnIndex) {
		m_mainConversation = main;
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
	
	public Session createCatchConversation(Session main, int returnIndex) {
		DefaultSession ret=new DefaultSession(main, returnIndex);
		
		m_nestedConversations.add(ret);
		main.getCatchConversations().add(ret);
		
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
	
	public Session getMainConversation() {
		return(m_mainConversation);
	}
	
	public java.util.List<Session> getCatchConversations() {
		return(m_catchConversations);
	}

	public void readExternal(ObjectInput arg0) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	public void writeExternal(ObjectOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
