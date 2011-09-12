/*
 * Copyright 2009-11 www.scribble.org
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

public class ProtocolValidatorRuleTest {

	@org.junit.Test
	public void testUnconnectedIntroduces() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		
		Introduces intros=new Introduces();
		intros.setIntroducer(roleB);
		intros.getRoles().add(roleC);
		p.getBlock().add(intros);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
						roleB.getName())
		});
	}

	@org.junit.Test
	public void testConnectedSimpleSequence() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		Role roleD=new Role("D");
		
		Introduces intros=new Introduces();
		intros.setIntroducer(roleA);
		intros.getRoles().add(roleB);
		p.getBlock().add(intros);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(roleB);
		i2.getToRoles().add(roleC);
		p.getBlock().add(i2);
		
		Interaction i3=new Interaction();
		i3.setFromRole(roleC);
		i3.getToRoles().add(roleD);
		p.getBlock().add(i3);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
		});
	}

	@org.junit.Test
	public void testUnconnectedSimpleSequence() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		Role roleD=new Role("D");
		
		Introduces intros=new Introduces();
		intros.setIntroducer(roleA);
		intros.getRoles().add(roleB);
		p.getBlock().add(intros);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(roleC);
		i2.getToRoles().add(roleD);
		p.getBlock().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
						roleC.getName())
		});
	}

	@org.junit.Test
	public void testUnconnectedChoice() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		
		Introduces intros1=new Introduces();
		intros1.setIntroducer(roleA);
		intros1.getRoles().add(roleB);
		p.getBlock().add(intros1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		Choice choice=new Choice();
		choice.setRole(roleC);
		p.getBlock().add(choice);
		
		Block b1=new Block();
		choice.getBlocks().add(b1);
		
		Block b2=new Block();
		choice.getBlocks().add(b2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
						roleC.getName())
		});
	}

	@org.junit.Test
	public void testUnconnectedChoicePath() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		
		Introduces intros1=new Introduces();
		intros1.setIntroducer(roleA);
		intros1.getRoles().add(roleB);
		p.getBlock().add(intros1);
		
		Introduces intros2=new Introduces();
		intros2.setIntroducer(roleB);
		intros2.getRoles().add(roleC);
		p.getBlock().add(intros2);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		Choice choice=new Choice();
		choice.setRole(roleA);
		p.getBlock().add(choice);
		
		Block b1=new Block();
		choice.getBlocks().add(b1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(roleB);
		i2.getToRoles().add(roleC);
		b1.add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
						roleB.getName())
		});
	}

	@org.junit.Test
	public void testConnectedChoicePath() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		
		Introduces intros1=new Introduces();
		intros1.setIntroducer(roleA);
		intros1.getRoles().add(roleB);
		p.getBlock().add(intros1);
		
		Introduces intros2=new Introduces();
		intros2.setIntroducer(roleB);
		intros2.getRoles().add(roleC);
		p.getBlock().add(intros2);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		// Make choice at B instead
		Choice choice=new Choice();
		choice.setRole(roleB);
		p.getBlock().add(choice);
		
		Block b1=new Block();
		choice.getBlocks().add(b1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(roleB);
		i2.getToRoles().add(roleC);
		b1.add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
		});
	}

	@org.junit.Test
	public void testUnconnectedRepeat() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		
		Introduces intros1=new Introduces();
		intros1.setIntroducer(roleA);
		intros1.getRoles().add(roleB);
		p.getBlock().add(intros1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		Repeat repeat=new Repeat();
		repeat.getRoles().add(roleC);
		p.getBlock().add(repeat);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
						roleC.getName())
		});
	}

	@org.junit.Test
	public void testUnconnectedRepeatBody() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		
		Introduces intros1=new Introduces();
		intros1.setIntroducer(roleA);
		intros1.getRoles().add(roleB);
		p.getBlock().add(intros1);
		
		Introduces intros2=new Introduces();
		intros2.setIntroducer(roleB);
		intros2.getRoles().add(roleC);
		p.getBlock().add(intros2);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		Repeat repeat=new Repeat();
		repeat.getRoles().add(roleA);
		p.getBlock().add(repeat);
		
		Interaction i2=new Interaction();
		i2.setFromRole(roleB);
		i2.getToRoles().add(roleC);
		repeat.getBlock().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
						roleB.getName())
		});
	}

	@org.junit.Test
	public void testConnectedRepeatBody() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		
		Introduces intros1=new Introduces();
		intros1.setIntroducer(roleA);
		intros1.getRoles().add(roleB);
		p.getBlock().add(intros1);
		
		Introduces intros2=new Introduces();
		intros2.setIntroducer(roleB);
		intros2.getRoles().add(roleC);
		p.getBlock().add(intros2);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		// Make decision at B instead
		Repeat repeat=new Repeat();
		repeat.getRoles().add(roleB);
		p.getBlock().add(repeat);
		
		Interaction i2=new Interaction();
		i2.setFromRole(roleB);
		i2.getToRoles().add(roleC);
		repeat.getBlock().add(i2);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
		});
	}

	@org.junit.Test
	public void testUnconnectedParallel() {
		Protocol p=new Protocol();
		ParameterDefinition pd=new ParameterDefinition();
		pd.setName("A");
		p.getParameterDefinitions().add(pd);
		
		Role roleA=new Role("A");
		Role roleB=new Role("B");
		Role roleC=new Role("C");
		Role roleD=new Role("D");
		
		Introduces intros1=new Introduces();
		intros1.setIntroducer(roleA);
		intros1.getRoles().add(roleB);
		p.getBlock().add(intros1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(roleA);
		i1.getToRoles().add(roleB);
		p.getBlock().add(i1);
		
		Parallel parallel=new Parallel();
		p.getBlock().add(parallel);
		
		Block b1=new Block();
		parallel.getBlocks().add(b1);
		
		Block b2=new Block();
		parallel.getBlocks().add(b2);
		
		Interaction i2=new Interaction();
		i2.setFromRole(roleB);
		i2.getToRoles().add(roleC);
		b1.add(i2);
		
		// Not connected, because this concurrent path is not connected to
		// the interaction sentences preceding the parallel - the other parallel
		// paths don't count
		Interaction i3=new Interaction();
		i3.setFromRole(roleC);
		i3.getToRoles().add(roleD);
		b2.add(i3);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		ProtocolValidatorRule rule=new ProtocolValidatorRule();
		rule.validate(null, p, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_UNCONNECTED_ROLE"),
						roleC.getName())
		});
	}
}
