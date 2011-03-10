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

public class RoleUtilTest {

	@org.junit.Test
	public void testGetRolesInScope1() {
		
		Protocol prot1=new Protocol();
		
		RoleList plist1=new RoleList();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getRoles().add(part2);
		
		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		java.util.Set<Role> result=RoleUtil.getRolesInScope(i1);
		
		if (result.size() != 2) {
			fail("Number of roles expected=2, but got: "+result.size());
		}
		
		if (result.contains(part1) == false) {
			fail("Role "+part1+" not in list");
		}
		
		if (result.contains(part2) == false) {
			fail("Role "+part2+" not in list");
		}
	}

	@org.junit.Test
	public void testGetRolesInScope2() {
		
		Protocol prot1=new Protocol();
		
		RoleList plist1=new RoleList();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getRoles().add(part2);
		
		Choice choice=new Choice();
		prot1.getBlock().add(choice);
		
		When wb1=new When();
		choice.getWhens().add(wb1);
		
		RoleList plist2=new RoleList();
		wb1.getBlock().add(plist2);
		
		Role part3=new Role();
		part3.setName("part3");
		plist2.getRoles().add(part3);
		
		Role part4=new Role();
		part4.setName("part4");
		plist2.getRoles().add(part4);
				
		When wb2=new When();
		choice.getWhens().add(wb2);
		
		RoleList plist3=new RoleList();
		wb2.getBlock().add(plist3);
		
		Role part5=new Role();
		part5.setName("part5");
		plist3.getRoles().add(part5);
		
		Role part6=new Role();
		part6.setName("part6");
		plist3.getRoles().add(part6);
		
		Interaction i1=new Interaction();
		wb2.getBlock().add(i1);
		
		java.util.Set<Role> result=RoleUtil.getRolesInScope(i1);
		
		if (result.size() != 4) {
			fail("Number of roles expected=4, but got: "+result.size());
		}
		
		if (result.contains(part1) == false) {
			fail("Role "+part1+" not in list");
		}
		
		if (result.contains(part2) == false) {
			fail("Role "+part2+" not in list");
		}
		
		if (result.contains(part5) == false) {
			fail("Role "+part5+" not in list");
		}
		
		if (result.contains(part6) == false) {
			fail("Role "+part6+" not in list");
		}
	}
}
