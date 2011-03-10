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

public class DefaultMessage implements Message {
	
	private String m_operator=null;
	private java.util.List<String> m_types=new java.util.Vector<String>();

	public DefaultMessage() {
	}
	
	public String getOperator() {
		return(m_operator);
	}
	
	public void setOperator(String op) {
		m_operator = op;
	}
	
	public java.util.List<String> getTypes() {
		return(m_types);
	}
	
	public String toString() {
		StringBuffer ret=new StringBuffer();
		
		if (m_operator != null && m_operator.trim().length() > 0) {
			ret.append(m_operator);
			ret.append('(');
		}
		
		for (int i=0; i < getTypes().size(); i++) {
			if (i > 0) {
				ret.append(',');
			}
			ret.append(getTypes().get(i));
		}
		
		if (m_operator != null && m_operator.trim().length() > 0) {
			ret.append(')');
		}
		
		return(ret.toString());
	}
	
	public boolean equals(Object obj) {
		boolean ret=false;
		
		if (obj instanceof DefaultMessage) {
			DefaultMessage other=(DefaultMessage)obj;
			
			if ((m_operator == null && other.m_operator == null) ||
					(m_operator != null && other.m_operator != null &&
							m_operator.equals(other.m_operator))) {
				
				// TODO: Need to validate parameter types
				// Could also have parameter types as Java types
				ret = true;
			}
		}
		
		return(ret);
	}
}
