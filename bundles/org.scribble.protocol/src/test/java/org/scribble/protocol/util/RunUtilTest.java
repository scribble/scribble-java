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
package org.scribble.protocol.util;

import static org.junit.Assert.*;

import org.scribble.protocol.model.*;

public class RunUtilTest {

	private static final String SUBPROTOCOL = "subprotocol";

	@org.junit.Test
	public void testGetInnerProtocol() {
		ProtocolModel pm=new ProtocolModel();
		
		Protocol p=new Protocol();
		pm.setProtocol(p);
		
		Run act=new Run();
		ProtocolReference pref=new ProtocolReference();
		pref.setName(SUBPROTOCOL);
		act.setProtocolReference(pref);
		
		p.getBlock().add(act);

		Protocol subp=new Protocol();
		subp.setName(SUBPROTOCOL);
		
		p.getBlock().add(subp);
		
		Protocol result=RunUtil.getInnerProtocol(p, pref);
		
		if (result != subp) {
			fail("Inner protocol not returned");
		}
	}

	@org.junit.Test
	public void testGetInnerProtocolLocated() {
		ProtocolModel pm=new ProtocolModel();
		
		Protocol p=new Protocol();
		pm.setProtocol(p);
		
		Role p1=new Role();
		p1.setName("p1");
		p.setRole(p1);
		
		Run act=new Run();
		ProtocolReference pref=new ProtocolReference();
		pref.setName(SUBPROTOCOL);
		pref.setRole(p1);
		act.setProtocolReference(pref);
		
		p.getBlock().add(act);

		Protocol subp=new Protocol();
		subp.setName(SUBPROTOCOL);
		subp.setRole(p1);
		
		p.getBlock().add(subp);
		
		Protocol result=RunUtil.getInnerProtocol(p, pref);
		
		if (result != subp) {
			fail("Inner protocol not returned");
		}
	}
}
