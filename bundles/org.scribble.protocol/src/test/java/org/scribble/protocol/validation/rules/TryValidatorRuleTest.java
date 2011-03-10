/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.validation.rules;

import java.text.MessageFormat;

import org.scribble.protocol.model.*;

public class TryValidatorRuleTest {

	@org.junit.Test
	public void testTryMissingCatchInteraction() {
		
		Protocol prot1=new Protocol();
		
		RoleList rlist1=new RoleList();
		prot1.getBlock().add(rlist1);
		
		Role r1=new Role();
		r1.setName("role1");
		rlist1.getRoles().add(r1);
		
		Role r2=new Role();
		r2.setName("role2");
		rlist1.getRoles().add(r2);
		
		Role r3=new Role();
		r3.setName("role3");
		
		Try t=new Try();
		prot1.getBlock().add(t);
		
		Catch c1=new Catch();
		t.getCatches().add(c1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(r1);
		i1.getToRoles().add(r2);
		
		c1.getInteractions().add(i1);
		
		Catch c2=new Catch();
		t.getCatches().add(c2);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		TryValidatorRule rule=new TryValidatorRule();
		rule.validate(t, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_CATCH_NO_INTERACTIONS"),
							(Object[])null)
		});
	}
	
	@org.junit.Test
	public void testTryInconsistentRolesSameCatch() {
		
		Protocol prot1=new Protocol();
		
		RoleList rlist1=new RoleList();
		prot1.getBlock().add(rlist1);
		
		Role r1=new Role();
		r1.setName("role1");
		rlist1.getRoles().add(r1);
		
		Role r2=new Role();
		r2.setName("role2");
		rlist1.getRoles().add(r2);
		
		Role r3=new Role();
		r3.setName("role3");
		
		Try t=new Try();
		prot1.getBlock().add(t);
		
		Catch c1=new Catch();
		t.getCatches().add(c1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(r1);
		i1.getToRoles().add(r2);
		
		c1.getInteractions().add(i1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(r2);
		i2.getToRoles().add(r1);
		
		c1.getInteractions().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		TryValidatorRule rule=new TryValidatorRule();
		rule.validate(t, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_CATCH_INCONSISTENT_ROLES"),
							(Object[])null)
		});
	}
	
	@org.junit.Test
	public void testTryInconsistentRolesDiffCatch() {
		
		Protocol prot1=new Protocol();
		
		RoleList rlist1=new RoleList();
		prot1.getBlock().add(rlist1);
		
		Role r1=new Role();
		r1.setName("role1");
		rlist1.getRoles().add(r1);
		
		Role r2=new Role();
		r2.setName("role2");
		rlist1.getRoles().add(r2);
		
		Role r3=new Role();
		r3.setName("role3");
		
		Try t=new Try();
		prot1.getBlock().add(t);
		
		Catch c1=new Catch();
		t.getCatches().add(c1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(r1);
		i1.getToRoles().add(r2);
		
		c1.getInteractions().add(i1);
		
		Catch c2=new Catch();
		t.getCatches().add(c2);
				
		Interaction i2=new Interaction();
		i2.setFromRole(r2);
		i2.getToRoles().add(r1);
		
		c2.getInteractions().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		TryValidatorRule rule=new TryValidatorRule();
		rule.validate(t, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_CATCH_INCONSISTENT_ROLES"),
							(Object[])null)
		});
	}
	
	@org.junit.Test
	public void testTryConsistentRolesNoFromRole() {
		
		Protocol prot1=new Protocol();
		
		RoleList rlist1=new RoleList();
		prot1.getBlock().add(rlist1);
		
		Role r1=new Role();
		r1.setName("role1");
		rlist1.getRoles().add(r1);
		
		Role r2=new Role();
		r2.setName("role2");
		rlist1.getRoles().add(r2);
		
		Role r3=new Role();
		r3.setName("role3");
		
		Try t=new Try();
		prot1.getBlock().add(t);
		
		Catch c1=new Catch();
		t.getCatches().add(c1);
		
		Interaction i1=new Interaction();
		//i1.setFromRole(r1);
		i1.getToRoles().add(r2);
		
		c1.getInteractions().add(i1);
		
		Catch c2=new Catch();
		t.getCatches().add(c2);
				
		Interaction i2=new Interaction();
		//i2.setFromRole(r2);
		i2.getToRoles().add(r2);
		
		c2.getInteractions().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		TryValidatorRule rule=new TryValidatorRule();
		rule.validate(t, logger);
		
		logger.verifyErrors(new String[]{});
	}
	
	@org.junit.Test
	public void testTryConsistentRolesNoToRoles() {
		
		Protocol prot1=new Protocol();
		
		RoleList rlist1=new RoleList();
		prot1.getBlock().add(rlist1);
		
		Role r1=new Role();
		r1.setName("role1");
		rlist1.getRoles().add(r1);
		
		Role r2=new Role();
		r2.setName("role2");
		rlist1.getRoles().add(r2);
		
		Role r3=new Role();
		r3.setName("role3");
		
		Try t=new Try();
		prot1.getBlock().add(t);
		
		Catch c1=new Catch();
		t.getCatches().add(c1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(r1);
		//i1.getToRoles().add(r2);
		
		c1.getInteractions().add(i1);
		
		Catch c2=new Catch();
		t.getCatches().add(c2);
				
		Interaction i2=new Interaction();
		i2.setFromRole(r1);
		//i2.getToRoles().add(r2);
		
		c2.getInteractions().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		TryValidatorRule rule=new TryValidatorRule();
		rule.validate(t, logger);
		
		logger.verifyErrors(new String[]{});
	}
	
	@org.junit.Test
	public void testTryConsistentRolesMultipleToRoles() {
		
		Protocol prot1=new Protocol();
		
		RoleList rlist1=new RoleList();
		prot1.getBlock().add(rlist1);
		
		Role r1=new Role();
		r1.setName("role1");
		rlist1.getRoles().add(r1);
		
		Role r2=new Role();
		r2.setName("role2");
		rlist1.getRoles().add(r2);
		
		Role r3=new Role();
		r3.setName("role3");
		
		Try t=new Try();
		prot1.getBlock().add(t);
		
		Catch c1=new Catch();
		t.getCatches().add(c1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(r1);
		i1.getToRoles().add(r2);
		i1.getToRoles().add(r3);
		
		c1.getInteractions().add(i1);
		
		Catch c2=new Catch();
		t.getCatches().add(c2);
				
		Interaction i2=new Interaction();
		i2.setFromRole(r1);
		i2.getToRoles().add(r3);
		i2.getToRoles().add(r2);
		
		c2.getInteractions().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		TryValidatorRule rule=new TryValidatorRule();
		rule.validate(t, logger);
		
		logger.verifyErrors(new String[]{});
	}
	
	@org.junit.Test
	public void testTryInconsistentRolesMultipleToRoles() {
		
		Protocol prot1=new Protocol();
		
		RoleList rlist1=new RoleList();
		prot1.getBlock().add(rlist1);
		
		Role r1=new Role();
		r1.setName("role1");
		rlist1.getRoles().add(r1);
		
		Role r2=new Role();
		r2.setName("role2");
		rlist1.getRoles().add(r2);
		
		Role r3=new Role();
		r3.setName("role3");
		
		Try t=new Try();
		prot1.getBlock().add(t);
		
		Catch c1=new Catch();
		t.getCatches().add(c1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(r1);
		i1.getToRoles().add(r2);
		i1.getToRoles().add(r3);
		
		c1.getInteractions().add(i1);
		
		Catch c2=new Catch();
		t.getCatches().add(c2);
				
		Interaction i2=new Interaction();
		i2.setFromRole(r1);
		i2.getToRoles().add(r3);
		
		c2.getInteractions().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		TryValidatorRule rule=new TryValidatorRule();
		rule.validate(t, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_CATCH_INCONSISTENT_ROLES"),
							(Object[])null)
		});
	}
}
