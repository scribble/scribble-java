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

public class TypesUtilTest {

	//private static final String ORDER_FORMAT = "xsdtype";
	private static final String ORDER_TYPE = "{http://www.example.org/types}Order";
	private static final String ORDER = "Order";

	@org.junit.Test
	public void testGetTypeImport() {
		ProtocolModel pm=new ProtocolModel();
		TypeImportList imp1=new TypeImportList();
		imp1.setFormat("xml");
		
		TypeImport t1=new TypeImport();
		t1.setName(ORDER);
		imp1.getTypeImports().add(t1);
		
		DataType dt=new DataType();
		dt.setDetails(ORDER_TYPE);
		t1.setDataType(dt);
		
		pm.getImports().add(imp1);
		
		Protocol p=new Protocol();
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		TypeReference tref1=new TypeReference();
		tref1.setName(ORDER);
		ms1.getTypeReferences().add(tref1);
		i1.setMessageSignature(ms1);
		
		p.getBlock().add(i1);
		
		TypeImport ti=TypesUtil.getTypeImport(tref1);
		
		if (ti == null) {
			fail("Failed to get TypeImport for Order");
		}
		
		if (ti.getDataType() == null) {
			fail("No data type");
		}
		
		if (ti.getDataType().getDetails().equals(ORDER_TYPE) == false) {
			fail("Data type detail not as expected: "+ti.getDataType().getDetails());
		}
	}
	
	@org.junit.Test
	public void testIsConcreteTypesDefinedTrue() {	
		ProtocolModel pm=new ProtocolModel();
		TypeImportList imp1=new TypeImportList();
		imp1.setFormat("xml");
		
		TypeImport t1=new TypeImport();
		t1.setName(ORDER);
		imp1.getTypeImports().add(t1);
		
		DataType dt=new DataType();
		dt.setDetails(ORDER_TYPE);
		t1.setDataType(dt);
		
		pm.getImports().add(imp1);
		
		if (TypesUtil.isConcreteTypesDefined(pm) == false) {
			fail("Concrete types defined");
		}
	}
	
	@org.junit.Test
	public void testIsConcreteTypesDefinedFalse() {	
		ProtocolModel pm=new ProtocolModel();
		TypeImportList imp1=new TypeImportList();
		imp1.setFormat("xml");
		
		TypeImport t1=new TypeImport();
		t1.setName(ORDER);
		imp1.getTypeImports().add(t1);
		
		pm.getImports().add(imp1);
		
		if (TypesUtil.isConcreteTypesDefined(pm) == true) {
			fail("Concrete types defined");
		}
	}
}
