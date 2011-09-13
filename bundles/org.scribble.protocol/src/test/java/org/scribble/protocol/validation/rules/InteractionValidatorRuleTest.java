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

public class InteractionValidatorRuleTest {

	@org.junit.Test
	public void testInteractionFromUnknownRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getIntroducedRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getIntroducedRoles().add(part2);
		
		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		Role part3=new Role();
		part3.setName("part3");
		
		i1.setFromRole(part3);
		i1.getToRoles().add(part2);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNKNOWN_ROLE"),
							part3.getName())
		});
	}

	@org.junit.Test
	public void testInteractionToUnknownRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getIntroducedRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getIntroducedRoles().add(part2);
		
		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		Role part3=new Role();
		part3.setName("part3");
		
		i1.setFromRole(part1);
		i1.getToRoles().add(part3);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNKNOWN_ROLE"),
							part3.getName())
		});
	}

	@org.junit.Test
	public void testInteractionToRequiredRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getIntroducedRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getIntroducedRoles().add(part2);
		
		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		i1.setFromRole(part1);
		//i1.setToParticipant(part2);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_INTERACTION_ROLE"),
							"to")
		});
	}

	@org.junit.Test
	public void testInteractionFromRequiredRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getIntroducedRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getIntroducedRoles().add(part2);
		
		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		//i1.setFromParticipant(part1);
		i1.getToRoles().add(part2);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_INTERACTION_ROLE"),
							"from")
		});
	}
	
	@org.junit.Test
	public void testInteractionToLocatedRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getIntroducedRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getIntroducedRoles().add(part2);
		
		prot1.setLocatedRole(part2);

		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		i1.setFromRole(part1);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
		});
	}

	@org.junit.Test
	public void testInteractionRequiredFromRoleWithLocatedRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getIntroducedRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getIntroducedRoles().add(part2);
		
		prot1.setLocatedRole(part2);

		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		i1.getToRoles().add(part2);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_INTERACTION_ROLE"),
							"from")
		});
	}

	@org.junit.Test
	public void testInteractionRequiredToRoleWithLocatedRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getIntroducedRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getIntroducedRoles().add(part2);
		
		prot1.setLocatedRole(part2);

		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		i1.setFromRole(part2);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_INTERACTION_ROLE"),
							"to")
		});
	}
	
	@org.junit.Test
	public void testInteractionFromLocatedRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getIntroducedRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getIntroducedRoles().add(part2);
		
		prot1.setLocatedRole(part1);
		
		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		i1.getToRoles().add(part2);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
		});
	}
	
	@org.junit.Test
	public void testInteractionUnrelatedToLocatedRole() {
		
		Protocol prot1=new Protocol();
		
		Introduces rlist1=new Introduces();
		prot1.getBlock().add(rlist1);
		
		Role role1=new Role();
		role1.setName("role1");
		rlist1.getIntroducedRoles().add(role1);
		
		Role role2=new Role();
		role2.setName("role2");
		rlist1.getIntroducedRoles().add(role2);
		
		Role role3=new Role();
		role3.setName("role3");
		prot1.setLocatedRole(role3);
		
		Interaction i1=new Interaction();
		prot1.getBlock().add(i1);
		
		i1.setFromRole(role1);
		i1.getToRoles().add(role2);
				
		TestScribbleLogger logger=new TestScribbleLogger();

		InteractionValidatorRule rule=new InteractionValidatorRule();
		rule.validate(null, i1, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNRELATED_TO_LOCATED_ROLE"),
							role3.getName())
		});
	}
}
