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

public class RecursionValidatorRuleTest {

	@org.junit.Test
	public void testRecursionWithValidLabel() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getRoles().add(part2);
		
		RecBlock r1=new RecBlock();
		prot1.getBlock().add(r1);
		
		r1.setLabel("transaction");
		
		Recursion rec=new Recursion();
		rec.setLabel("transaction");
		
		r1.getBlock().add(rec);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		RecursionValidatorRule rule=new RecursionValidatorRule();
		rule.validate(null, rec, logger);
		
		logger.verifyErrors(new String[]{
		});
	}

	@org.junit.Test
	public void testRecursionWithNoEnclosingLabelBlock() {
		
		Protocol prot1=new Protocol();
		
		Introduces plist1=new Introduces();
		prot1.getBlock().add(plist1);
		
		Role part1=new Role();
		part1.setName("part1");
		plist1.getRoles().add(part1);
		
		Role part2=new Role();
		part2.setName("part2");
		plist1.getRoles().add(part2);
		
		RecBlock r1=new RecBlock();
		prot1.getBlock().add(r1);
		
		r1.setLabel("notransaction");
		
		Recursion rec=new Recursion();
		rec.setLabel("transaction");
		
		r1.getBlock().add(rec);
		
		TestScribbleLogger logger=new TestScribbleLogger();

		RecursionValidatorRule rule=new RecursionValidatorRule();
		rule.validate(null, rec, logger);
		
		logger.verifyErrors(new String[]{
				MessageFormat.format(
						java.util.PropertyResourceBundle.getBundle(
						"org.scribble.protocol.Messages").getString("_NO_ENCLOSING_RECUR"),
							rec.getLabel())
		});
	}
}
