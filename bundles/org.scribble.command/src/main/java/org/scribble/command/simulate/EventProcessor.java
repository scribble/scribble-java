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
package org.scribble.command.simulate;

public class EventProcessor {

	public EventProcessor() {
	}
	
	public void initialize(java.io.InputStream events) throws Exception {
		byte[] b=new byte[events.available()];
		
		events.read(b);
		
		String buf=new String(b);
		
		String[] lines=buf.split("[\r\n]");
		
		for (int i=0; i < lines.length; i++) {
			String line=lines[i].trim();
			
			if (line.length() > 0) {
				String[] cols=line.split(",");
				Event evt=null;
				
				for (int j=0; j < cols.length; j++) {
					
					if (j == 0) {
						if (cols[j].equals("sendMessage")) {
							evt = new SendMessage();
						} else if (cols[j].equals("receiveMessage")) {
							evt = new ReceiveMessage();
						} else if (cols[j].equals("sendDecision")) {
							evt = new SendDecision();
						} else if (cols[j].equals("receiveDecision")) {
							evt = new ReceiveDecision();
						} else if (cols[j].equals("sendChoice")) {
							evt = new SendChoice();
						} else if (cols[j].equals("receiveChoice")) {
							evt = new ReceiveChoice();
						} else {
							System.err.println("Unknown event type '"+cols[j]+"'");
						}
					} else if (evt != null) {
						evt.setColumn(j, cols[j]);
					}
				}
				
				if (evt != null) {
					m_events.add(evt);
				}
			}
		}
	}
	
	public java.util.List<Event> getEvents() {
		return(m_events);
	}
	
	private java.util.List<Event> m_events=new java.util.Vector<Event>();
}
