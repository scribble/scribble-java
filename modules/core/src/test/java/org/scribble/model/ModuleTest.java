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
package org.scribble.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModuleTest {

	private static final String SINGLE_PART = "Single";
	private static final String MULTIPLE_PART = "Multiple.Parts";
	private static final String MULTIPLE_LAST_PART = "Parts";

	@Test
	public void testLastPartSinglePart() {
		Module module=new Module();
		
		module.setName(SINGLE_PART);
		
		if (!module.getLocalName().equals(SINGLE_PART)) {
			fail("Didn't return single part");
		}
	}

	@Test
	public void testLastPartMultipleParts() {
		Module module=new Module();
		
		module.setName(MULTIPLE_PART);
		
		if (!module.getLocalName().equals(MULTIPLE_LAST_PART)) {
			fail("Didn't return multiple last part");
		}
	}

}
