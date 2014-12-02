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
package org.scribble.monitor.rest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribble.monitor.model.Annotation;
import org.scribble.monitor.model.Receive;
import org.scribble.monitor.model.Send;
import org.scribble.monitor.model.SessionType;
import org.scribble.monitor.model.Parameter;
import org.scribble.monitor.runtime.DefaultMonitor;
import org.scribble.monitor.rest.RESTMessage;
import org.scribble.monitor.runtime.DefaultMonitorContext;
import org.scribble.monitor.runtime.Monitor;
import org.scribble.monitor.runtime.SessionInstance;

public class RESTMonitorTest {

	private static final String ROLE1 = "Role1";

	private static final String OP1 = "op1";
	private static final String OP2 = "op2";
	
	private static final String TYPE1 = "type1";
	private static final String TYPE2 = "type2";
	private static final String TYPE3 = "type3";

	@Test
	public void testGetRequestResponse() {
		Monitor monitor=new DefaultMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		((DefaultMonitor)monitor).setMonitorContext(context);
		
		context.register(new RESTMessageComparatorFactory());
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		
		Parameter t1=new Parameter("f", TYPE1);
		Annotation t1ann=new Annotation();
		t1ann.setName("REST");
		t1ann.addProperty("query", "field");
		t1.getAnnotations().add(t1ann);
		
		Parameter t2=new Parameter("c", TYPE2);
		Annotation t2ann=new Annotation();
		t2ann.setName("REST");
		t2ann.addProperty("path", "customer");
		t2.getAnnotations().add(t2ann);
		
		s1.getParameters().add(t1);
		s1.getParameters().add(t2);
		s1.setToRole(ROLE1);
		s1.setNext(1);
		
		Annotation s1ann=new Annotation();
		s1ann.setName("REST");
		s1ann.addProperty("method", RESTMessage.METHOD_GET);
		s1ann.addProperty("path", "/customer/{customer}/address");
		
		s1.getAnnotations().add(s1ann);
		
		type.getNodes().add(s1);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		
		Parameter t3=new Parameter("v", TYPE3);

		r1.getParameters().add(t3);
		r1.setFromRole(ROLE1);
		
		Annotation r1ann=new Annotation();
		r1ann.setName("REST");
		r1ann.addProperty("method", RESTMessage.METHOD_GET);
		r1ann.addProperty("path", "/customer/{customer}/address");
		
		r1.getAnnotations().add(r1ann);
		
		type.getNodes().add(r1);
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		RESTMessage m1=new RESTMessage();
		m1.setMethod(RESTMessage.METHOD_GET);
		m1.getPath().add("customer");
		m1.getPath().add("fred");
		m1.getPath().add("address");
		m1.getQueryParameters().put("field","postcode");
		
		RESTMessage badm2=new RESTMessage();
		badm2.setMethod(RESTMessage.METHOD_GET);
		badm2.getPath().add("customer");
		badm2.getPath().add("fred");
		badm2.getPath().add("notaddress");
		badm2.setContent("{ \"id\":\"12345\" }");
		
		RESTMessage m2=new RESTMessage();
		m2.setMethod(RESTMessage.METHOD_GET);
		m2.getPath().add("customer");
		m2.getPath().add("fred");
		m2.getPath().add("address");
		m2.setContent("{ \"id\":\"12345\" }");
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message not expected");
		}
		
		if (monitor.received(type, instance, badm2, null)) {
			fail("Received message should NOT be expected");
		}
		
		if (instance.hasCompleted()) {
			fail("Session should NOT be completed");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

	@Test
	public void testPostRequestResponse() {
		Monitor monitor=new DefaultMonitor();
		
		DefaultMonitorContext context=new DefaultMonitorContext();
		((DefaultMonitor)monitor).setMonitorContext(context);
		
		context.register(new RESTMessageComparatorFactory());
		
		SessionType type=new SessionType();
		
		Send s1=new Send();
		s1.setOperator(OP1);
		
		// First parameter will represent the POST content
		Parameter t1=new Parameter("v", TYPE1);
		
		Parameter t2=new Parameter("c", TYPE2);
		Annotation t2ann=new Annotation();
		t2ann.setName("REST");
		t2ann.addProperty("path", "customer");
		t2.getAnnotations().add(t2ann);
		
		s1.getParameters().add(t1);
		s1.getParameters().add(t2);
		s1.setToRole(ROLE1);
		s1.setNext(1);
		
		Annotation s1ann=new Annotation();
		s1ann.setName("REST");
		s1ann.addProperty("method", RESTMessage.METHOD_POST);
		s1ann.addProperty("path", "/customer/{customer}/invoice");
		
		s1.getAnnotations().add(s1ann);
		
		type.getNodes().add(s1);
		
		Receive r1=new Receive();
		r1.setOperator(OP2);
		
		Parameter t3=new Parameter("r", TYPE3);
		
		r1.getParameters().add(t3);
		r1.setFromRole(ROLE1);
		
		Annotation r1ann=new Annotation();
		r1ann.setName("REST");
		r1ann.addProperty("method", RESTMessage.METHOD_POST);
		r1ann.addProperty("path", "/customer/{customer}/invoice");
		
		r1.getAnnotations().add(r1ann);
		
		type.getNodes().add(r1);
		
		SessionInstance instance=new SessionInstance();
		
		monitor.initializeInstance(type, instance);
		
		RESTMessage m1=new RESTMessage();
		m1.setMethod(RESTMessage.METHOD_POST);
		m1.getPath().add("customer");
		m1.getPath().add("fred");
		m1.getPath().add("invoice");
		m1.setContent("{ \"id\":\"12345\" }");
		
		RESTMessage badm2=new RESTMessage();
		badm2.setMethod(RESTMessage.METHOD_POST);
		badm2.getPath().add("customer");
		badm2.getPath().add("fred");
		badm2.getPath().add("notinvoice");
		badm2.setContent("{ \"id\":\"54321\" }");
		
		RESTMessage m2=new RESTMessage();
		m2.setMethod(RESTMessage.METHOD_POST);
		m2.getPath().add("customer");
		m2.getPath().add("fred");
		m2.getPath().add("invoice");
		m2.setContent("{ \"id\":\"54321\" }");
		
		if (!monitor.sent(type, instance, m1, null)) {
			fail("Sent message not expected");
		}
		
		if (monitor.received(type, instance, badm2, null)) {
			fail("Received message should NOT be expected");
		}
		
		if (!monitor.received(type, instance, m2, null)) {
			fail("Received message not expected");
		}
		
		if (!instance.hasCompleted()) {
			fail("Session hasn't completed");
		}
	}

}
