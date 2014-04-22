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
package org.scribble.trace;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribble.monitor.Message;
import org.scribble.trace.model.MessageTransfer;
import org.scribble.trace.model.MonitorRoleSimulator;
import org.scribble.trace.model.Role;
import org.scribble.trace.model.Trace;
import org.scribble.trace.util.TraceUtil;

public class TraceUtilTest {

	private static final String ROLE_A = "RoleA";
	private static final String ROLE_B = "RoleB";
	
	private static final String V1_2 = "v1-2";
	private static final String V1_1 = "v1-1";
	private static final String T1_2 = "t1-2";
	private static final String T1_1 = "t1-1";
	private static final String OP1 = "op1";

	private static final String V2_2 = "v2-2";
	private static final String V2_1 = "v2-1";
	private static final String T2_2 = "t2-2";
	private static final String T2_1 = "t2-1";
	private static final String OP2 = "op2";

	@Test
	public void testSerializeTrace() {
		
		Message m1=new Message();
		m1.setOperator(OP1);
		
		java.util.List<String> types1=new java.util.ArrayList<String>();
		types1.add(T1_1);
		types1.add(T1_2);
		m1.setTypes(types1);
		
		java.util.List<Object> values1=new java.util.ArrayList<Object>();
		values1.add(V1_1);
		values1.add(V1_2);
		m1.setValues(values1);
		
		Message m2=new Message();
		m2.setOperator(OP2);
		
		java.util.List<String> types2=new java.util.ArrayList<String>();
		types2.add(T2_1);
		types2.add(T2_2);
		m2.setTypes(types2);
		
		java.util.List<Object> values2=new java.util.ArrayList<Object>();
		values2.add(V2_1);
		values2.add(V2_2);
		m2.setValues(values2);
		
		Trace t1=new Trace();
		
		MessageTransfer mt1=new MessageTransfer();
		mt1.setFromRole("A");
		mt1.getToRoles().add("B");
		mt1.setMessage(m1);
		t1.getSteps().add(mt1);
		
		MessageTransfer mt2=new MessageTransfer();
		mt2.setFromRole("C");
		mt2.getToRoles().add("D");
		mt2.getToRoles().add("E");
		mt2.setMessage(m2);
		t1.getSteps().add(mt2);
		
		Role role1=new Role();
		role1.setName(ROLE_A);
		t1.getRoles().add(role1);
		
		MonitorRoleSimulator mrs1=new MonitorRoleSimulator();
		mrs1.setProtocol("a.b.c.P1");
		role1.setSimulator(mrs1);
		
		Role role2=new Role();
		role2.setName(ROLE_B);
		t1.getRoles().add(role2);
		
		MonitorRoleSimulator mrs2=new MonitorRoleSimulator();
		mrs2.setProtocol("a.b.c.P2");
		role2.setSimulator(mrs2);
		
		
		byte[] ser=null;
		
		try {
			ser = TraceUtil.serializeTrace(t1);
		} catch (Exception e) {
			fail("Failed to serialize trace");
		}
		
		//System.out.println("SERIALISED="+new String(ser));
		
		Trace result=null;
		
		try {
			result = TraceUtil.deserializeTrace(ser);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to deserialize trace");
		}

		if (result == null) {
			fail("Null results");
		}
		
		if (result.getSteps().size() != 2) {
			fail("Expecting 2 steps: "+result.getSteps().size());
		}
		
		if (result.getRoles().size() != 2) {
			fail("Expecting 2 simulations: "+result.getRoles().size());
		}
	}
}
