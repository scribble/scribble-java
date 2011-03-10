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

public class InteractionUtilTest {

	@org.junit.Test
	public void testGetInitialInteractionsSimple() {
		ProtocolModel pm=new ProtocolModel();
		
		Protocol p=new Protocol();
		pm.setProtocol(p);

		Interaction i1=new Interaction();
		p.getBlock().add(i1);
		
		Interaction i2=new Interaction();
		p.getBlock().add(i2);
		
		java.util.List<ModelObject> results=InteractionUtil.getInitialInteractions(pm);
		
		if (results.size() != 1) {
			fail("Should only be 1 result: "+results.size());
		}
		
		if (results.get(0) != i1) {
			fail("Interaction not as expected");
		}
	}
	
	@org.junit.Test
	public void testGetInitialInteractionsChoice() {
		ProtocolModel pm=new ProtocolModel();
		
		Protocol p=new Protocol();
		pm.setProtocol(p);

		Choice choice=new Choice();
		choice.setFromRole(new Role());
		p.getBlock().add(choice);
		
		When wb1=new When();
		choice.getWhens().add(wb1);
		
		Interaction i1=new Interaction();
		wb1.getBlock().add(i1);
		
		When wb2=new When();
		choice.getWhens().add(wb2);
		
		Interaction i2=new Interaction();
		wb2.getBlock().add(i2);
		
		java.util.List<ModelObject> results=InteractionUtil.getInitialInteractions(pm);
		
		if (results.size() != 2) {
			fail("Should be 2 results: "+results.size());
		}
		
		if (results.get(0) != wb1) {
			fail("Interaction 1 not as expected");
		}
		
		if (results.get(1) != wb2) {
			fail("Interaction 2 not as expected");
		}
	}
	
	@org.junit.Test
	public void testGetInitialInteractionsEmbeddedChoice() {
		ProtocolModel pm=new ProtocolModel();
		
		Protocol p=new Protocol();
		pm.setProtocol(p);

		Choice choice1=new Choice();
		choice1.setToRole(new Role());
		choice1.setFromRole(new Role());
		p.getBlock().add(choice1);
		
		When wb1=new When();
		choice1.getWhens().add(wb1);
		
		Choice choice2=new Choice();
		choice2.setFromRole(new Role());
		wb1.getBlock().add(choice2);
		
		When wb1_1=new When();
		choice2.getWhens().add(wb1_1);
		
		Interaction i1=new Interaction();
		wb1_1.getBlock().add(i1);
		
		When wb1_2=new When();
		choice2.getWhens().add(wb1_2);
		
		Interaction i2=new Interaction();
		wb1_2.getBlock().add(i2);
		
		When wb2=new When();
		choice1.getWhens().add(wb2);
		
		Interaction i3=new Interaction();
		wb2.getBlock().add(i3);
		
		java.util.List<ModelObject> results=InteractionUtil.getInitialInteractions(pm);
		
		if (results.size() != 3) {
			fail("Should be 3 results: "+results.size());
		}
		
		if (results.get(0) != wb1_1) {
			fail("Interaction 1 not as expected");
		}
		
		if (results.get(1) != wb1_2) {
			fail("Interaction 2 not as expected");
		}
		
		if (results.get(2) != i3) {
			fail("Interaction 3 not as expected");
		}
	}
	
	@org.junit.Test
	public void testIsInitialInteractionSimple() {
		ProtocolModel pm=new ProtocolModel();
		
		Protocol p=new Protocol();
		pm.setProtocol(p);

		Interaction i1=new Interaction();
		p.getBlock().add(i1);
		
		Interaction i2=new Interaction();
		p.getBlock().add(i2);
		
		if (InteractionUtil.isInitialInteraction(pm, i1) == false) {
			fail("Interaction 1 not as expected");
		}
		
		if (InteractionUtil.isInitialInteraction(pm, i2)) {
			fail("Interaction 2 is NOT initial");
		}
	}
	
	@org.junit.Test
	public void testIsInitialInteractionsEmbeddedChoice() {
		ProtocolModel pm=new ProtocolModel();
		
		Protocol p=new Protocol();
		pm.setProtocol(p);

		Choice choice1=new Choice();
		choice1.setToRole(new Role());
		choice1.setFromRole(new Role());
		p.getBlock().add(choice1);
		
		When wb1=new When();
		choice1.getWhens().add(wb1);
		
		Choice choice2=new Choice();
		choice2.setFromRole(new Role());
		wb1.getBlock().add(choice2);
		
		When wb1_1=new When();
		choice2.getWhens().add(wb1_1);
		
		Interaction i1=new Interaction();
		wb1_1.getBlock().add(i1);
		
		When wb1_2=new When();
		choice2.getWhens().add(wb1_2);
		
		Interaction i2=new Interaction();
		wb1_2.getBlock().add(i2);
		
		When wb2=new When();
		choice1.getWhens().add(wb2);
		
		Interaction i3=new Interaction();
		wb2.getBlock().add(i3);
		
		Interaction i4=new Interaction();
		p.getBlock().add(i4);
		
		if (InteractionUtil.isInitialInteraction(pm, wb1_1) == false) {
			fail("Interaction 1 not as expected");
		}
		
		if (InteractionUtil.isInitialInteraction(pm, wb1_2) == false) {
			fail("Interaction 2 not as expected");
		}
		
		if (InteractionUtil.isInitialInteraction(pm, i3) == false) {
			fail("Interaction 3 not as expected");
		}
		
		if (InteractionUtil.isInitialInteraction(pm, i4)) {
			fail("Interaction 4 is NOT initial");
		}
	}
}
