/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble.monitor;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribble.monitor.model.Choice;
import org.scribble.monitor.model.Continue;
import org.scribble.monitor.model.Do;
import org.scribble.monitor.model.Interruptible;
import org.scribble.monitor.model.Parallel;
import org.scribble.monitor.model.Receive;
import org.scribble.monitor.model.Recursion;
import org.scribble.monitor.model.Send;
import org.scribble.monitor.model.SessionType;

public class DefaultMonitorTest {

	private static final String ROLE1 = "Role1";
	private static final String ROLE2 = "Role2";
	private static final String OP1 = "op1";
	private static final String OP2 = "op2";
	private static final String OP3 = "op3";
	private static final String OP4 = "op4";
	private static final String OP5 = "op5";

	private static final String TYPE1 = "type1";
	private static final String TYPE2 = "type2";
	private static final String TYPE3 = "type3";
	private static final String TYPE4 = "type4";
	private static final String TYPE5 = "type5";
	
	@Test
	public void testRequestResponse() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setToRole(ROLE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		r1.setFromRole(ROLE1);
		
		type.getNodes().add(r1);
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestResponseWithRoles() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setToRole(ROLE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		r1.setFromRole(ROLE1);
		
		type.getNodes().add(r1);
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		if (!monitor.sent(type, instance, m1, ROLE1)) {
			fail("Sent message not expected");
		}
		
		if (monitor.received(type, instance, m2, ROLE2)) {
			fail("Receive should be invalid, as from wrong role");
		}
		
		if (!monitor.received(type, instance, m2, ROLE1)) {
			fail("Received message not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestResponseParallel1() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Parallel par=new Parallel();
		par.getPathIndexes().add(2);
		par.getPathIndexes().add(3);
		
		type.getNodes().add(par);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (instance.hasCompleted()) {
			fail("Session shouldn't have completed");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestResponseParallel2() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Parallel par=new Parallel();
		par.getPathIndexes().add(2);
		par.getPathIndexes().add(3);
		
		type.getNodes().add(par);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (instance.hasCompleted()) {
			fail("Session shouldn't have completed");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestParallelFollowedBySend() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Parallel par=new Parallel();
		par.setNext(4);
		par.getPathIndexes().add(2);
		par.getPathIndexes().add(3);
		
		type.getNodes().add(par);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		Send s2=new Send();
		s2.setOperator(OP4);
		s2.getTypes().add(TYPE4);
		
		type.getNodes().add(s2);

		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (instance.hasCompleted()) {
			fail("Session shouldn't have completed");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (!monitor.sent(type, instance, m4, null)) {
			fail("Sent message 4 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	@Test
	public void testRequestResponseChoice1() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Choice ch=new Choice();
		ch.getPathIndexes().add(2);
		ch.getPathIndexes().add(3);
		
		type.getNodes().add(ch);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestResponseChoice2() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Choice ch=new Choice();
		ch.getPathIndexes().add(2);
		ch.getPathIndexes().add(3);
		
		type.getNodes().add(ch);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message not expected");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	@Test
	public void testRequestChoiceFollowedBySend() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Choice ch=new Choice();
		ch.setNext(4);
		ch.getPathIndexes().add(2);
		ch.getPathIndexes().add(3);
		
		type.getNodes().add(ch);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		Send s2=new Send();
		s2.setOperator(OP4);
		s2.getTypes().add(TYPE4);
		
		type.getNodes().add(s2);

		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP4);
		m3.getTypes().add(TYPE4);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.sent(type, instance, m3, null)) {
			fail("Sent message 4 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	protected SessionType getParallelChoiceFollowedBySend() {
		SessionType type=new SessionType();
		
		// 0
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		// 1
		Parallel par=new Parallel();
		par.setNext(6);
		par.getPathIndexes().add(2);
		par.getPathIndexes().add(3);
		
		type.getNodes().add(par);
		
		// 2
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		// 3
		Choice ch=new Choice();
		ch.getPathIndexes().add(4);
		ch.getPathIndexes().add(5);
		
		type.getNodes().add(ch);
		
		// 4
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		// 5
		Receive r3=new Receive();
		r3.setOperator(OP4);
		r3.getTypes().add(TYPE4);
		
		type.getNodes().add(r3);
		
		// 6
		Send s2=new Send();
		s2.setOperator(OP5);
		s2.getTypes().add(TYPE5);
		
		type.getNodes().add(s2);

		return (type);
	}
	
	@Test
	public void testRequestParallelChoiceFollowedBySend1() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getParallelChoiceFollowedBySend();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);
		
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (!monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestParallelChoiceFollowedBySend2() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getParallelChoiceFollowedBySend();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);
		
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.received(type, instance, m4, null)) {
			fail("Received message 4 not expected");
		}
		
		if (!monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestParallelChoiceFollowedBySend3() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getParallelChoiceFollowedBySend();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);
		
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}

		if (!monitor.received(type, instance, m4, null)) {
			fail("Received message 4 not expected");
		}

		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	@Test
	public void testRequestParallelChoiceFollowedBySend4() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getParallelChoiceFollowedBySend();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);
		
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}

		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}

		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	protected SessionType getChoiceParallelFollowedBySend() {
		SessionType type=new SessionType();
		
		// 0
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		// 1
		Choice ch=new Choice();
		ch.setNext(6);
		ch.getPathIndexes().add(2);
		ch.getPathIndexes().add(3);
		
		type.getNodes().add(ch);
		
		// 2
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		// 3
		Parallel par=new Parallel();
		par.getPathIndexes().add(4);
		par.getPathIndexes().add(5);
		
		type.getNodes().add(par);
		
		// 4
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		// 5
		Receive r3=new Receive();
		r3.setOperator(OP4);
		r3.getTypes().add(TYPE4);
		
		type.getNodes().add(r3);
		
		// 6
		Send s2=new Send();
		s2.setOperator(OP5);
		s2.getTypes().add(TYPE5);
		
		type.getNodes().add(s2);

		return (type);
	}
	
	@Test
	public void testRequestChoiceParallelFollowedBySend1() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getChoiceParallelFollowedBySend();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);
		
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m4, null)) {
			fail("Received message 4 not expected");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (!monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestChoiceParallelFollowedBySend2() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getChoiceParallelFollowedBySend();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);
		
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (!monitor.received(type, instance, m4, null)) {
			fail("Received message 4 not expected");
		}
		
		if (!monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testRequestChoiceParallelFollowedBySend3() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getChoiceParallelFollowedBySend();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);
		
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	@Test
	public void testDo() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Do d1=new Do();
		d1.setNext(3);
		d1.setProtocolIndex(2);
		
		type.getNodes().add(d1);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Send s2=new Send();
		s2.setOperator(OP3);
		s2.getTypes().add(TYPE3);
		
		type.getNodes().add(s2);

		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.sent(type, instance, m3, null)) {
			fail("Sent message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	@Test
	public void testRecursionChoiceContinue() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Recursion rec=new Recursion();
		rec.setNext(6);
		rec.setBlockIndex(2);
		
		type.getNodes().add(rec);
		
		Choice ch=new Choice();
		ch.getPathIndexes().add(3);
		ch.getPathIndexes().add(5);
		
		type.getNodes().add(ch);
		
		Receive r1=new Receive();
		r1.setNext(4);
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Continue con1=new Continue();
		con1.setNext(2);
		
		type.getNodes().add(con1);
		
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		Send s2=new Send();
		s2.setOperator(OP4);
		s2.getTypes().add(TYPE4);
		
		type.getNodes().add(s2);

		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP2);
		m3.getTypes().add(TYPE2);
		
		Message m4=new Message();
		m4.setOperator(OP2);
		m4.getTypes().add(TYPE2);

		Message m5=new Message();
		m5.setOperator(OP3);
		m5.getTypes().add(TYPE3);
		
		Message m6=new Message();
		m6.setOperator(OP4);
		m6.getTypes().add(TYPE4);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (!monitor.received(type, instance, m4, null)) {
			fail("Received message 4 not expected");
		}

		if (!monitor.received(type, instance, m5, null)) {
			fail("Received message 5 not expected");
		}
		
		if (!monitor.sent(type, instance, m6, null)) {
			fail("Sent message 6 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	@Test
	public void testRecursionParChoiceContinue() {
		Monitor monitor=new DefaultMonitor();
		
		/*
		// Do While Loop
		
		send op1(type1)
		rec X {
			par {
				choice A {
					// do again
					receive op2(type2)
					continue X;
				} or {
					// end actions
					receive op3(type3)
				}
			} and {
				// concurrent actions
				receive op4(type4)
			}
		}
		send op5(type5)
		*/
		
		SessionType type=new SessionType();
		
		// 0
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		// 1
		Recursion rec=new Recursion();
		rec.setNext(8);
		rec.setBlockIndex(2);
		
		type.getNodes().add(rec);
		
		// 2
		Parallel par=new Parallel();
		par.getPathIndexes().add(3);
		par.getPathIndexes().add(7);
		
		type.getNodes().add(par);
		
		// 3
		Choice ch=new Choice();
		ch.getPathIndexes().add(4);
		ch.getPathIndexes().add(6);
		
		type.getNodes().add(ch);
		
		// 4
		Receive r1=new Receive();
		r1.setNext(5);
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		// 5
		Continue con1=new Continue();
		con1.setNext(2);
		
		type.getNodes().add(con1);
		
		// 6
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		// 7
		Receive r3=new Receive();
		r3.setOperator(OP4);
		r3.getTypes().add(TYPE4);
		
		type.getNodes().add(r3);
		
		// 8
		Send s2=new Send();
		s2.setOperator(OP5);
		s2.getTypes().add(TYPE5);
		
		type.getNodes().add(s2);

		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP4);
		m2.getTypes().add(TYPE4);
		
		Message m3=new Message();
		m3.setOperator(OP2);
		m3.getTypes().add(TYPE2);
		
		Message m4=new Message();
		m4.setOperator(OP4);
		m4.getTypes().add(TYPE4);

		Message m5=new Message();
		m5.setOperator(OP3);
		m5.getTypes().add(TYPE3);
		
		Message m6=new Message();
		m6.setOperator(OP5);
		m6.getTypes().add(TYPE5);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.received(type, instance, m3, null)) {
			fail("Received message 3 not expected");
		}
		
		if (!monitor.received(type, instance, m4, null)) {
			fail("Received message 4 not expected");
		}

		if (!monitor.received(type, instance, m5, null)) {
			fail("Received message 5 not expected");
		}
		
		if (!monitor.sent(type, instance, m6, null)) {
			fail("Sent message 6 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	/*
	// While Loop
	rec X {
		choice A {
			end activities
		} or {
			par {
				continue X;
			} and {
				concurrent activities;
			}
		}
	*/

	protected SessionType getInterruptibleThrowSessionType() {
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setToRole(ROLE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		// 1
		Interruptible i1=new Interruptible();
		i1.setBlockIndex(2);
		
		i1.setThrows(3);
		i1.setNext(4);
		
		type.getNodes().add(i1);
		
		// 2
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		r1.setFromRole(ROLE1);
		
		type.getNodes().add(r1);
		
		// 3
		Send throw1=new Send();
		throw1.setOperator(OP5);
		throw1.getTypes().add(TYPE5);
		throw1.setToRole(ROLE1);
		
		type.getNodes().add(throw1);
		
		// 4
		Send s3=new Send();
		s3.setOperator(OP3);
		s3.getTypes().add(TYPE3);
		s3.setToRole(ROLE1);
		
		type.getNodes().add(s3);
		
		
		return (type);
	}
	
	/**
	 * This test will use the normal path with no interrupt.
	 */
	@Test
	public void testInterruptibleThrow1() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getInterruptibleThrowSessionType();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (!monitor.sent(type, instance, m3, null)) {
			fail("Sent message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	/**
	 * This test will use the thrown interrupt path with no normal response.
	 */
	@Test
	public void testInterruptibleThrow2() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getInterruptibleThrowSessionType();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		// The interrupt message
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 (thrown interrupt) not expected");
		}
		
		if (!monitor.sent(type, instance, m3, null)) {
			fail("Sent message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	/**
	 * This test will use the thrown interrupt path with normal response crossing.
	 */
	@Test
	public void testInterruptibleThrow3() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getInterruptibleThrowSessionType();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		// The interrupt message
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message 2 not expected");
		}
		
		if (monitor.sent(type, instance, m5, null)) {
			fail("Sent message 5 (thrown interrupt) should not be expected");
		}
		
		if (!monitor.sent(type, instance, m3, null)) {
			fail("Sent message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	protected SessionType getInterruptibleCatchSessionType() {
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setToRole(ROLE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		// 1
		Interruptible i1=new Interruptible();
		i1.setBlockIndex(2);
		
		i1.setCatches(3);
		i1.setNext(4);
		
		type.getNodes().add(i1);
		
		// 2
		Send s2=new Send();
		s2.setOperator(OP2);
		s2.getTypes().add(TYPE2);
		s2.setToRole(ROLE1);
		
		type.getNodes().add(s2);
		
		// 3
		Receive catch1=new Receive();
		catch1.setOperator(OP5);
		catch1.getTypes().add(TYPE5);
		catch1.setFromRole(ROLE1);
		
		type.getNodes().add(catch1);
		
		// 4
		Send s3=new Send();
		s3.setOperator(OP3);
		s3.getTypes().add(TYPE3);
		s3.setToRole(ROLE1);
		
		type.getNodes().add(s3);
		
		
		return (type);
	}
	
	/**
	 * This test will use the normal path with no interrupt.
	 */
	@Test
	public void testInterruptibleCatch1() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getInterruptibleCatchSessionType();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.sent(type, instance, m2, null)) {
			fail("Sent message 2 not expected");
		}
		
		if (!monitor.sent(type, instance, m3, null)) {
			fail("Sent message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	/**
	 * This test will use the catch interrupt path with no normal response.
	 */
	@Test
	public void testInterruptibleCatch2() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getInterruptibleCatchSessionType();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		// The interrupt message
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.received(type, instance, m5, null)) {
			fail("Received message 5 (caught interrupt) not expected");
		}
		
		if (!monitor.sent(type, instance, m3, null)) {
			fail("Sent message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}
	
	/**
	 * This test will use the caught interrupt path with normal response crossing.
	 */
	@Test
	public void testInterruptibleCatch3() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=getInterruptibleCatchSessionType();
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		// The interrupt message
		Message m5=new Message();
		m5.setOperator(OP5);
		m5.getTypes().add(TYPE5);
		
		Message m3=new Message();
		m3.setOperator(OP3);
		m3.getTypes().add(TYPE3);
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message 1 not expected");
		}
		
		if (!monitor.sent(type, instance, m2, null)) {
			fail("Sent message 2 not expected");
		}
		
		if (monitor.received(type, instance, m5, null)) {
			fail("Received message 5 (caught interrupt) should not be expected");
		}
		
		if (!monitor.sent(type, instance, m3, null)) {
			fail("Sent message 3 not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	@Test
	public void testPerformance() {
		Monitor monitor=new DefaultMonitor();
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		s1.getTypes().add(TYPE1);
		s1.setNext(1);
		
		type.getNodes().add(s1);
		
		Choice ch=new Choice();
		ch.setNext(4);
		ch.getPathIndexes().add(2);
		ch.getPathIndexes().add(3);
		
		type.getNodes().add(ch);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		r1.getTypes().add(TYPE2);
		
		type.getNodes().add(r1);
		
		Receive r2=new Receive();
		r2.setOperator(OP3);
		r2.getTypes().add(TYPE3);
		
		type.getNodes().add(r2);
		
		Send s2=new Send();
		s2.setOperator(OP4);
		s2.getTypes().add(TYPE4);
		
		type.getNodes().add(s2);

		Message m1=new Message();
		m1.setOperator(OP1);
		m1.getTypes().add(TYPE1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		m2.getTypes().add(TYPE2);
		
		Message m3=new Message();
		m3.setOperator(OP4);
		m3.getTypes().add(TYPE4);
		
		long startTime=System.currentTimeMillis();
		
		for (int i=0; i < 1000000; i++) {
			SessionInstance instance=new SessionInstance();
			
			monitor.initializeInstance(type, instance);
			
			if (!monitor.sent(type, instance, m1, null)) {
				fail("Sent message 1 not expected");
			}
			
			if (!monitor.received(type, instance, m2, null)) {
				fail("Received message 2 not expected");
			}
			
			if (!monitor.sent(type, instance, m3, null)) {
				fail("Sent message 4 not expected");
			}
		}
		
		System.out.println("TIME="+(System.currentTimeMillis()-startTime)+"ms");
	}

}
