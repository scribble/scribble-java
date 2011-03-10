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

	public void addNodeIndex(int index);
	
	public void removeNodeIndexAt(int pos);
	
	public int getNumberOfNodeIndexes();
	
	public int getNodeIndexAt(int pos);
	
	public boolean isFinished();
	
	public Session createNestedConversation(int returnIndex);
	
	public Session createCatchConversation(Session main, int returnIndex);
	
	public int getReturnIndex();
	
	public java.util.List<Session> getNestedConversations();
	
	public Session getMainConversation();
	
	public java.util.List<Session> getCatchConversations();
	
	public void removeNestedConversation(Session context);
	
}
