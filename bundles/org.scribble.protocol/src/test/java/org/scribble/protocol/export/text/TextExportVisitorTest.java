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
package org.scribble.protocol.export.text;

import org.scribble.common.logging.CachedJournal;
import org.scribble.common.model.DefaultAnnotation;
import org.scribble.protocol.model.*;

import static org.junit.Assert.*;

public class TextExportVisitorTest {

	private static final String RECUR_LABEL_TRANSACTION = "Transaction";

	@org.junit.Test
	public void testImportTypeSingle() {
		
		TypeImportList elem=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("testname");
		elem.getTypeImports().add(t);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="import "+t.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testImportTypeSingleWithAnnotation() {
		
		TypeImportList elem=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("testname");
		elem.getTypeImports().add(t);
		
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
			"[["+annotation2.toString()+"]]\r\n"+
			"import "+t.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testImportTypeMultiple() {
		
		TypeImportList elem=new TypeImportList();
		TypeImport t1=new TypeImport();
		t1.setName("testname1");
		elem.getTypeImports().add(t1);
		
		TypeImport t2=new TypeImport();
		t2.setName("testname2");
		elem.getTypeImports().add(t2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="import "+t1.getName()+", "+t2.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testImportProtocolSingle() {
		
		ProtocolImportList elem=new ProtocolImportList();
		ProtocolImport t=new ProtocolImport();
		t.setName("testname");
		t.setLocation("testlocation");
		elem.getProtocolImports().add(t);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="import protocol "+t.getName()+" from \"testlocation\";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testImportProtocolSingleWithAnnotation() {
		
		ProtocolImportList elem=new ProtocolImportList();
		ProtocolImport t=new ProtocolImport();
		t.setName("testname");
		t.setLocation("testlocation");
		elem.getProtocolImports().add(t);
		
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"import protocol "+t.getName()+" from \"testlocation\";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testImportProtocolMultiple() {
		
		ProtocolImportList elem=new ProtocolImportList();
		ProtocolImport t1=new ProtocolImport();
		t1.setName("testname1");
		t1.setLocation("testlocation1");
		elem.getProtocolImports().add(t1);
		
		ProtocolImport t2=new ProtocolImport();
		t2.setName("testname2");
		t2.setLocation("testlocation1");
		elem.getProtocolImports().add(t2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="import protocol "+t1.getName()+" from \""+t1.getLocation()+
					"\", "+t2.getName()+" from \""+t2.getLocation()+"\";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testProtocol() {
		
		Protocol elem=new Protocol();
		elem.setName("test");
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="protocol "+elem.getName()+" {\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testProtocolWithAnnotation() {
		
		Protocol elem=new Protocol();
		elem.setName("test");
		
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"protocol "+elem.getName()+" {\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testProtocolLocated() {
		
		Protocol elem=new Protocol();
		elem.setName("test");
		
		Role p1=new Role();
		p1.setName("p1");
		
		elem.setLocatedRole(p1);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="protocol "+elem.getName()+" at "+elem.getLocatedRole().getName()+" {\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testRoleList() {
		
		Introduces elem=new Introduces();
		
		Role i1=new Role();
		i1.setName("I1");
		
		elem.setIntroducer(i1);
		
		Role p1=new Role();
		p1.setName("P1");
		
		elem.getIntroducedRoles().add(p1);
		
		Role p2=new Role();
		p2.setName("P2");
		
		elem.getIntroducedRoles().add(p2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected=i1.getName()+" introduces "+p1.getName()+", "+p2.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testRoleListWithAnnotation() {
		
		Introduces elem=new Introduces();
		
		Role i1=new Role();
		i1.setName("I1");
		
		elem.setIntroducer(i1);
		
		Role p1=new Role();
		p1.setName("P1");
		
		elem.getIntroducedRoles().add(p1);
		
		Role p2=new Role();
		p2.setName("P2");
		
		elem.getIntroducedRoles().add(p2);
		
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
					"[["+annotation2.toString()+"]]\r\n"+
					i1.getName()+" introduces "+p1.getName()+", "+p2.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
		
	@org.junit.Test
	public void testInteraction() {
		
		Interaction elem=new Interaction();
		
		Role p1=new Role();
		p1.setName("A");
		
		elem.setFromRole(p1);
		
		Role p2=new Role();
		p2.setName("B");
		
		elem.getToRoles().add(p2);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		elem.setMessageSignature(sig1);
	
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected=ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testInteractionWithAnnotation() {
		
		Interaction elem=new Interaction();
		
		Role p1=new Role();
		p1.setName("A");
		
		elem.setFromRole(p1);
		
		Role p2=new Role();
		p2.setName("B");
		
		elem.getToRoles().add(p2);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		elem.setMessageSignature(sig1);
	
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testInteractionSend() {
		
		Interaction elem=new Interaction();
		
		Role p1=new Role();
		p1.setName("A");
		
		elem.setFromRole(p1);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		elem.setMessageSignature(sig1);
	
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected=ref1.getName()+" from "+p1.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testInteractionReceive() {
		
		Interaction elem=new Interaction();
		
		Role p2=new Role();
		p2.setName("B");
		
		elem.getToRoles().add(p2);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		elem.setMessageSignature(sig1);
	
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected=ref1.getName()+" to "+p2.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testChoice() {
		
		Choice elem=new Choice();
		
		Role p1=new Role();
		p1.setName("A");
		
		elem.setRole(p1);
		
		Role p2=new Role();
		p2.setName("B");
		
		Interaction i1=new Interaction();
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		Block b1=new Block();
		b1.add(i1);
		elem.getPaths().add(b1);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		i1.setMessageSignature(sig1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(p1);
		i2.getToRoles().add(p2);
		
		Block b2=new Block();
		b2.add(i2);
		
		elem.getPaths().add(b2);
		
		TypeReference ref2=new TypeReference();
		ref2.setName("Type2");
		
		MessageSignature sig2=new MessageSignature();
		sig2.getTypeReferences().add(ref2);
		
		i2.setMessageSignature(sig2);
	
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="choice at "+p1.getName()+" {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n"+
				"} or {\r\n"+
				"\t"+ref2.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n"+
				"}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testChoiceWithAnnotation() {
		
		Choice elem=new Choice();
		
		Role p1=new Role();
		p1.setName("A");
		
		elem.setRole(p1);
		
		Role p2=new Role();
		p2.setName("B");
		
		Interaction i1=new Interaction();
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		Block b1=new Block();
		b1.add(i1);
		elem.getPaths().add(b1);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		i1.setMessageSignature(sig1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(p1);
		i2.getToRoles().add(p2);
		
		Block b2=new Block();
		b2.add(i2);
		
		elem.getPaths().add(b2);
		
		TypeReference ref2=new TypeReference();
		ref2.setName("Type2");
		
		MessageSignature sig2=new MessageSignature();
		sig2.getTypeReferences().add(ref2);
		
		i2.setMessageSignature(sig2);
	
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"choice at "+p1.getName()+" {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n"+
				"} or {\r\n"+
				"\t"+ref2.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n"+
				"}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testChoiceSend() {
		
		Choice elem=new Choice();
		
		Role p1=new Role();
		p1.setName("A");
		
		elem.setRole(p1);
		
		Interaction i1=new Interaction();
		i1.setFromRole(p1);
		
		Block b1=new Block();
		b1.add(i1);
		elem.getPaths().add(b1);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		i1.setMessageSignature(sig1);
		
		Interaction i2=new Interaction();
		i2.setFromRole(p1);
		
		Block b2=new Block();
		b2.add(i2);
		
		elem.getPaths().add(b2);
		
		TypeReference ref2=new TypeReference();
		ref2.setName("Type2");
		
		MessageSignature sig2=new MessageSignature();
		sig2.getTypeReferences().add(ref2);
		
		i2.setMessageSignature(sig2);
	
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="choice at "+p1.getName()+" {\r\n"+
		"\t"+ref1.getName()+" from "+p1.getName()+";\r\n"+
		"} or {\r\n"+
		"\t"+ref2.getName()+" from "+p1.getName()+";\r\n"+
		"}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testChoiceReceive() {
		
		Choice elem=new Choice();
		
		Role p2=new Role();
		p2.setName("B");
		
		Interaction i1=new Interaction();
		i1.getToRoles().add(p2);
		
		Block b1=new Block();
		b1.add(i1);
		elem.getPaths().add(b1);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		i1.setMessageSignature(sig1);
		
		Interaction i2=new Interaction();
		i2.getToRoles().add(p2);
		
		Block b2=new Block();
		b2.add(i2);
		
		elem.getPaths().add(b2);
		
		TypeReference ref2=new TypeReference();
		ref2.setName("Type2");
		
		MessageSignature sig2=new MessageSignature();
		sig2.getTypeReferences().add(ref2);
		
		i2.setMessageSignature(sig2);
	
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="choice {\r\n"+
		"\t"+ref1.getName()+" to "+p2.getName()+";\r\n"+
		"} or {\r\n"+
		"\t"+ref2.getName()+" to "+p2.getName()+";\r\n"+
		"}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRepeat() {
		
		Repeat elem=new Repeat();
		
		Role p1=new Role();
		p1.setName("A");
		
		elem.getRoles().add(p1);
		
		Role p2=new Role();
		p2.setName("B");
		
		elem.getRoles().add(p2);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		elem.getBlock().add(i1);
			
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="repeat at "+p1.getName()+","+p2.getName()+" {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRepeatWithAnnotation() {
		
		Repeat elem=new Repeat();
		
		Role p1=new Role();
		p1.setName("A");
		
		elem.getRoles().add(p1);
		
		Role p2=new Role();
		p2.setName("B");
		
		elem.getRoles().add(p2);
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		elem.getBlock().add(i1);
			
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"repeat at "+p1.getName()+","+p2.getName()+" {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testUnordered() {
		
		Unordered elem=new Unordered();
		
		Role p1=new Role();
		p1.setName("A");
		
		Role p2=new Role();
		p2.setName("B");
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		elem.getBlock().add(i1);
			
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="unordered {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testUnorderedWithAnnotation() {
		
		Unordered elem=new Unordered();
		
		Role p1=new Role();
		p1.setName("A");
		
		Role p2=new Role();
		p2.setName("B");
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		elem.getBlock().add(i1);
			
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"unordered {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRecur() {
		
		RecBlock elem=new RecBlock();
		elem.setLabel(RECUR_LABEL_TRANSACTION);
		
		Role p1=new Role();
		p1.setName("A");
		
		Role p2=new Role();
		p2.setName("B");
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		elem.getBlock().add(i1);
		
		Recursion recursion=new Recursion();
		recursion.setLabel(RECUR_LABEL_TRANSACTION);
		
		elem.getBlock().add(recursion);
			
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="rec "+elem.getLabel()+" {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n"+
				"\t"+elem.getLabel()+";\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}

	@org.junit.Test
	public void testRecurWithAnnotation() {
		
		RecBlock elem=new RecBlock();
		elem.setLabel(RECUR_LABEL_TRANSACTION);
		
		Role p1=new Role();
		p1.setName("A");
		
		Role p2=new Role();
		p2.setName("B");
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		elem.getBlock().add(i1);
		
		Recursion recursion=new Recursion();
		recursion.setLabel(RECUR_LABEL_TRANSACTION);
		
		elem.getBlock().add(recursion);
			
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"rec "+elem.getLabel()+" {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n"+
				"\t"+elem.getLabel()+";\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}

	@org.junit.Test
	public void testRunNoBinding() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		pref.setName("SubProtocol");
		
		elem.setProtocolReference(pref);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="run "+pref.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRunNoBindingWithAnnotation() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		pref.setName("SubProtocol");
		
		elem.setProtocolReference(pref);
		
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"run "+pref.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRunLocatedNoBinding() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		pref.setName("SubProtocol");
		
		Role p1=new Role();
		p1.setName("P1");
		
		pref.setRole(p1);
		
		elem.setProtocolReference(pref);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="run "+pref.getName()+" at "+pref.getRole().getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRunWithBinding() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		pref.setName("SubProtocol");
		
		elem.setProtocolReference(pref);
		
		Parameter db1=new Parameter();
		db1.setName("Parent1");
		//db1.setBoundName("Child1");
		
		elem.getParameters().add(db1);
		
		Parameter db2=new Parameter();
		db2.setName("Parent2");
		//db2.setBoundName("Child2");
		
		elem.getParameters().add(db2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="run "+pref.getName()+"("+db1.getName()+", "+
						db2.getName()+");\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRunLocatedWithBinding() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		pref.setName("SubProtocol");
		
		elem.setProtocolReference(pref);
		
		Role p1=new Role();
		p1.setName("P1");
		
		pref.setRole(p1);
		
		Parameter db1=new Parameter();
		db1.setName("Parent1");
		//db1.setBoundName("Child1");
		
		elem.getParameters().add(db1);
		
		Parameter db2=new Parameter();
		db2.setName("Parent2");
		//db2.setBoundName("Child2");
		
		elem.getParameters().add(db2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="run "+pref.getName()+" at "+pref.getRole().getName()+
						"("+db1.getName()+", "+db2.getName()+");\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRunInlineNoBinding() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		
		elem.setProtocolReference(pref);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="run "+pref.getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRunInlineLocatedNoBinding() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		
		Role p1=new Role();
		p1.setName("P1");
		
		pref.setRole(p1);
		
		elem.setProtocolReference(pref);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="run "+pref.getName()+" at "+pref.getRole().getName()+";\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRunInlineWithBinding() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		
		elem.setProtocolReference(pref);
		
		Parameter db1=new Parameter();
		db1.setName("Parent1");
		//db1.setBoundName("Child1");
		
		elem.getParameters().add(db1);
		
		Parameter db2=new Parameter();
		db2.setName("Parent2");
		//db2.setBoundName("Child2");
		
		elem.getParameters().add(db2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="run "+pref.getName()+"("+db1.getName()+", "+
						db2.getName()+");\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}
	
	@org.junit.Test
	public void testRunInlineLocatedWithBinding() {
		
		Run elem=new Run();
		
		ProtocolReference pref=new ProtocolReference();
		
		elem.setProtocolReference(pref);
		
		Role p1=new Role();
		p1.setName("P1");
		
		pref.setRole(p1);
		
		Parameter db1=new Parameter();
		db1.setName("Parent1");
		//db1.setBoundName("Child1");
		
		elem.getParameters().add(db1);
		
		Parameter db2=new Parameter();
		db2.setName("Parent2");
		//db2.setBoundName("Child2");
		
		elem.getParameters().add(db2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="run "+pref.getName()+" at "+pref.getRole().getName()+
						"("+db1.getName()+", "+db2.getName()+");\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}

	@org.junit.Test
	public void testParallel() {
		
		Parallel elem=new Parallel();
		
		Block b1=new Block();
		elem.getPaths().add(b1);
		
		Block b2=new Block();
		elem.getPaths().add(b2);

		Role p1=new Role();
		p1.setName("P1");
		
		Role p2=new Role();
		p2.setName("P2");
		
		Role p3=new Role();
		p3.setName("P3");
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		TypeReference ref2=new TypeReference();
		ref2.setName("Type2");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		MessageSignature sig2=new MessageSignature();
		sig2.getTypeReferences().add(ref2);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		b1.add(i1);
			
		Interaction i2=new Interaction();
		
		i2.setMessageSignature(sig2);
		i2.setFromRole(p1);
		i2.getToRoles().add(p3);
		
		b2.add(i2);
			
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="parallel {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n} and {\r\n"+
				"\t"+ref2.getName()+" from "+p1.getName()+" to "+p3.getName()+";\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}	
	
	@org.junit.Test
	public void testParallelWithAnnotation() {
		
		Parallel elem=new Parallel();
		
		Block b1=new Block();
		elem.getPaths().add(b1);
		
		Block b2=new Block();
		elem.getPaths().add(b2);

		Role p1=new Role();
		p1.setName("P1");
		
		Role p2=new Role();
		p2.setName("P2");
		
		Role p3=new Role();
		p3.setName("P3");
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		TypeReference ref2=new TypeReference();
		ref2.setName("Type2");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		MessageSignature sig2=new MessageSignature();
		sig2.getTypeReferences().add(ref2);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		b1.add(i1);
			
		Interaction i2=new Interaction();
		
		i2.setMessageSignature(sig2);
		i2.setFromRole(p1);
		i2.getToRoles().add(p3);
		
		b2.add(i2);
			
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"parallel {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n} and {\r\n"+
				"\t"+ref2.getName()+" from "+p1.getName()+" to "+p3.getName()+";\r\n}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}	
	
	@org.junit.Test
	public void testDoInterrupt() {
		
		Do elem=new Do();
		
		Interrupt c1=new Interrupt();
		
		Block b1=new Block();
		c1.setBlock(b1);
		
		elem.getInterrupts().add(c1);
		
		Interrupt c2=new Interrupt();
		
		Block b2=new Block();
		c2.setBlock(b2);
		
		elem.getInterrupts().add(c2);

		Role p1=new Role();
		p1.setName("P1");
		
		Role p2=new Role();
		p2.setName("P2");
		
		Role p3=new Role();
		p3.setName("P3");
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		TypeReference ref2=new TypeReference();
		ref2.setName("Type2");
		
		TypeReference ref3=new TypeReference();
		ref3.setName("Type3");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		MessageSignature sig2=new MessageSignature();
		sig2.getTypeReferences().add(ref2);
		
		MessageSignature sig3=new MessageSignature();
		sig3.getTypeReferences().add(ref3);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		c1.getBlock().add(i1);
			
		Interaction i2=new Interaction();
		
		i2.setMessageSignature(sig2);
		i2.setFromRole(p1);
		i2.getToRoles().add(p3);
		
		c2.getBlock().add(i2);
			
		Interaction i3=new Interaction();
		
		i3.setMessageSignature(sig3);
		i3.setFromRole(p1);
		i3.getToRoles().add(p3);
		
		c2.getBlock().add(i3);
			
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="do {\r\n"+
				"} interrupt {\r\n" +
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n"+
				"} interrupt {\r\n"+
				"\t"+ref2.getName()+" from "+p1.getName()+" to "+p3.getName()+";\r\n"+
				"\t"+ref3.getName()+" from "+p1.getName()+" to "+p3.getName()+";\r\n"+
				"}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}	

	@org.junit.Test
	public void testDoInterruptWithAnnotation() {
		
		Do elem=new Do();
		
		Interrupt c1=new Interrupt();
		
		Block b1=new Block();
		c1.setBlock(b1);
		
		elem.getInterrupts().add(c1);
		
		Interrupt c2=new Interrupt();
		
		Block b2=new Block();
		c2.setBlock(b2);
		
		elem.getInterrupts().add(c2);

		Role p1=new Role();
		p1.setName("P1");
		
		Role p2=new Role();
		p2.setName("P2");
		
		Role p3=new Role();
		p3.setName("P3");
		
		TypeReference ref1=new TypeReference();
		ref1.setName("Type1");
		
		TypeReference ref2=new TypeReference();
		ref2.setName("Type2");
		
		TypeReference ref3=new TypeReference();
		ref3.setName("Type3");
		
		MessageSignature sig1=new MessageSignature();
		sig1.getTypeReferences().add(ref1);
		
		MessageSignature sig2=new MessageSignature();
		sig2.getTypeReferences().add(ref2);
		
		MessageSignature sig3=new MessageSignature();
		sig3.getTypeReferences().add(ref3);
		
		Interaction i1=new Interaction();
		
		i1.setMessageSignature(sig1);
		i1.setFromRole(p1);
		i1.getToRoles().add(p2);
		
		c1.getBlock().add(i1);
			
		Interaction i2=new Interaction();
		
		i2.setMessageSignature(sig2);
		i2.setFromRole(p1);
		i2.getToRoles().add(p3);
		
		c2.getBlock().add(i2);
			
		Interaction i3=new Interaction();
		
		i3.setMessageSignature(sig3);
		i3.setFromRole(p1);
		i3.getToRoles().add(p3);
		
		c2.getBlock().add(i3);
			
		DefaultAnnotation annotation1=new DefaultAnnotation("annotation1");
		elem.getAnnotations().add(annotation1);
		
		DefaultAnnotation annotation2=new DefaultAnnotation("annotation2");
		elem.getAnnotations().add(annotation2);
		
		java.io.ByteArrayOutputStream baos=new java.io.ByteArrayOutputStream();
		
		Visitor visitor=new TextProtocolExporterVisitor(new CachedJournal(), baos);
		
		elem.visit(visitor);
		
		try {
			baos.close();
		} catch(Exception e) {
			fail("Failed to close stream");
		}
		
		String str=baos.toString();
		
		String expected="[["+annotation1.toString()+"]]\r\n"+
				"[["+annotation2.toString()+"]]\r\n"+
				"do {\r\n"+
				"} interrupt {\r\n"+
				"\t"+ref1.getName()+" from "+p1.getName()+" to "+p2.getName()+";\r\n"+
				"} interrupt {\r\n"+
				"\t"+ref2.getName()+" from "+p1.getName()+" to "+p3.getName()+";\r\n"+
				"\t"+ref3.getName()+" from "+p1.getName()+" to "+p3.getName()+";\r\n"+
				"}\r\n";
		
		if (str.equals(expected) == false) {
			fail("Expected:\r\n"+expected+"\r\nGot:\r\n"+str);
		}
	}	
}
