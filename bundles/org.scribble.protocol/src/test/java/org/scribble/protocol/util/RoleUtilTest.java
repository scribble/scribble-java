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
		
		Introduces plist1=new Introduces();
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
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getRoles().add(part2);
		
		Choice choice=new Choice();
		prot1.getBlock().add(choice);
		
		Block wb1=new Block();
		choice.getBlocks().add(wb1);
		
		Introduces plist2=new Introduces();
		wb1.add(plist2);
		
		Role part3=new Role();
		part3.setName("part3");
		plist2.getRoles().add(part3);
		
		Role part4=new Role();
		part4.setName("part4");
		plist2.getRoles().add(part4);
				
		Block wb2=new Block();
		choice.getBlocks().add(wb2);
		
		Introduces plist3=new Introduces();
		wb2.add(plist3);
		
		Role part5=new Role();
		part5.setName("part5");
		plist3.getRoles().add(part5);
		
		Role part6=new Role();
		part6.setName("part6");
		plist3.getRoles().add(part6);
		
		Interaction i1=new Interaction();
		wb2.add(i1);
		
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
	
	@org.junit.Test
	public void testGetEnclosingBlockSingleActivities() {
		Protocol p=new Protocol();
		
		Role r0=new Role();
		r0.setName("r0");
		
		Interaction i0=new Interaction();
		i0.setFromRole(r0);
		p.getBlock().add(i0);
		
		Role r1=new Role();
		r1.setName("r1");
		
		Role r2=new Role();
		r2.setName("r2");
		
		Parallel par=new Parallel();
		p.getBlock().add(par);
		
		Block parb1=new Block();
		par.getBlocks().add(parb1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(r1);
		
		parb1.add(i1);
		
		Block parb2=new Block();
		par.getBlocks().add(parb2);
		
		Choice choice1=new Choice();
		parb2.add(choice1);
		
		Block wb1=new Block();
		choice1.getBlocks().add(wb1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(r2);
		
		wb1.add(i2);
		
		// Check results
		Block b0=RoleUtil.getEnclosingBlock(p, r0);
		
		if (b0 == null) {
			fail("b0 is null");
		}
		
		if (b0 != p.getBlock()) {
			fail("b0 not protocol block");
		}
		
		Block b1=RoleUtil.getEnclosingBlock(p, r1);
		
		if (b1 == null) {
			fail("b1 is null");
		}
		
		if (b1 != parb1) {
			fail("b1 not parallel block");
		}
		
		Block b2=RoleUtil.getEnclosingBlock(p, r2);
		
		if (b2 == null) {
			fail("b2 is null");
		}
		
		if (b2 != wb1) {
			fail("b2 not when block");
		}
	}
	
	@org.junit.Test
	public void testGetEnclosingBlockMultiActivities() {
		Protocol p=new Protocol();
		
		Role r0=new Role();
		r0.setName("r0");
		
		Interaction i0=new Interaction();
		i0.setFromRole(r0);
		p.getBlock().add(i0);
		
		Role r1=new Role();
		r1.setName("r1");
		
		Role r2=new Role();
		r2.setName("r2");
		
		Parallel par=new Parallel();
		p.getBlock().add(par);
		
		Block parb1=new Block();
		par.getBlocks().add(parb1);
		
		Interaction i11=new Interaction();
		i11.setFromRole(r1);
		
		parb1.add(i11);
		
		Block parb2=new Block();
		par.getBlocks().add(parb2);
		
		Interaction i12=new Interaction();
		i12.setFromRole(r1);
		
		parb2.add(i12);
		
		Choice choice1=new Choice();
		parb2.add(choice1);
		
		Block wb1=new Block();
		choice1.getBlocks().add(wb1);
		
		Interaction i21=new Interaction();
		i21.setFromRole(r2);
		
		wb1.add(i21);
		
		Block wb2=new Block();
		choice1.getBlocks().add(wb2);
		
		Interaction i22=new Interaction();
		i22.setFromRole(r2);
		
		wb2.add(i22);
		
		// Check results
		Block b0=RoleUtil.getEnclosingBlock(p, r0);
		
		if (b0 == null) {
			fail("b0 is null");
		}
		
		if (b0 != p.getBlock()) {
			fail("b0 not protocol block");
		}
		
		Block b1=RoleUtil.getEnclosingBlock(p, r1);
		
		if (b1 == null) {
			fail("b1 is null");
		}
		
		if (b1 != p.getBlock()) {
			fail("b1 not parallel parent block (i.e. protocol block)");
		}
		
		Block b2=RoleUtil.getEnclosingBlock(p, r2);
		
		if (b2 == null) {
			fail("b2 is null");
		}
		
		if (b2 != parb2) {
			fail("b2 not parallel block containing choice");
		}
	}
}
