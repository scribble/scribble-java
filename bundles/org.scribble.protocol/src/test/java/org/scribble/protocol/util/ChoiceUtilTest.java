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

//import static org.junit.Assert.*;

import junit.framework.TestCase;

import org.scribble.protocol.model.*;

public class ChoiceUtilTest extends TestCase {

	//@org.junit.Test
	public void testGetLabels() {
		ProtocolModel pm=new ProtocolModel();
		
		TypeReference stringRef=new TypeReference();
		stringRef.setName("String");
		
		TypeReference intRef=new TypeReference();
		intRef.setName("Integer");
		
		Protocol p=new Protocol();
		pm.setProtocol(p);
		
		Choice choice=new Choice();
		p.getBlock().add(choice);
		
		When wb1=new When();
		
		MessageSignature msig1=new MessageSignature();
		msig1.setOperation("op1");
		msig1.getTypeReferences().add(stringRef);
		wb1.setMessageSignature(msig1);
		
		choice.getWhens().add(wb1);
		
		When wb2=new When();
		
		MessageSignature msig2=new MessageSignature();
		msig2.getTypeReferences().add(intRef);
		wb2.setMessageSignature(msig2);
		
		choice.getWhens().add(wb2);
		
		When wb3=new When();
		
		MessageSignature msig3=new MessageSignature();
		msig3.setOperation("op1");
		msig3.getTypeReferences().add(stringRef);
		wb3.setMessageSignature(msig3);
		
		choice.getWhens().add(wb3);

		java.util.List<String> result=ChoiceUtil.getLabels(pm);
		
		if (result.size() != 2) {
			fail("Should have only been two results");
		}
		
		if (result.get(0).equals(ChoiceUtil.getLabel(msig1)) == false) {
			fail("Label 0 incorrect");
		}
		
		if (result.get(1).equals(ChoiceUtil.getLabel(msig2)) == false) {
			fail("Label 1 incorrect");
		}
	}
}
