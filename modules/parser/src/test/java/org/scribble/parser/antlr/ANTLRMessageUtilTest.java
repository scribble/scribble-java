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
package org.scribble.parser.antlr;

import static org.junit.Assert.*;

import org.junit.Test;
import org.scribble.model.ModelObject;
import org.scribble.parser.antlr.ANTLRMessageUtil;

public class ANTLRMessageUtilTest {

	@Test
	public void testGetProperties() {
		String doc="package Test;\n\nglobal protocol Hello (role Buyer, role Seller) {\n	buy(Order) from Buyer to Seller;"
				+"\n\n	choice at Seller {\n		confirmed(Receipt) from Seller to Buyer;\n	} or {\n" +
				"		rejected(OutOfStock) from Seller toy Buyer;\n	}\n}";
		
		String mesg="line 9:35 missing TOKW at 'toy'";
		
		java.util.Map<String, Object> props=ANTLRMessageUtil.getProperties(mesg, doc);
		
		if (props == null) {
			fail("Failed to get properties");
		}
		
		if (!props.containsKey(ModelObject.START_POSITION)) {
			fail("No start position");
		}
		
		if (!props.containsKey(ModelObject.END_POSITION)) {
			fail("No end position");
		}
		
		Integer start=(Integer)props.get(ModelObject.START_POSITION);
		
		if (start.intValue() != 206) {
			fail("Start should be 206, but was: "+start.intValue());
		}
		
		Integer end=(Integer)props.get(ModelObject.END_POSITION);
		
		if (end.intValue() != 209) {
			fail("End should be 209, but was: "+end.intValue());
		}
	}

}
