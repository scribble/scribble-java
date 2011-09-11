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
package org.scribble.protocol.parser.antlr;

import static org.junit.Assert.*;

import org.scribble.common.logging.CachedJournal;
import org.scribble.common.logging.CachedJournal.IssueDetails;
import org.scribble.common.logging.CachedJournal.IssueType;
import org.scribble.common.logging.Journal;
import org.scribble.common.model.Annotation;
import org.scribble.common.resource.ByteArrayContent;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.Visitor;
import org.scribble.protocol.parser.AnnotationProcessor;

public class ANTLRProtocolParserTest {

	@org.junit.Test
	public void testDetectCorrectPosition() {
		String first="import MT;\r\n\r\nprotocol Test {\r\n\t";
		String second="roleX";
		String third=" ";
		String fourth="A";
		//String fifth=";\r\n}\r\n";
		String protocol=first+second+third+fourth;
		
		ByteArrayContent content=new ByteArrayContent(protocol.getBytes());
		
		ANTLRProtocolParser parser=new ANTLRProtocolParser();
		
		CachedJournal journal=new CachedJournal();
		
		try {
			ProtocolModel pm=parser.parse(null, content, journal);
			
			if (pm != null) {
				fail("Returned protocol model should be null due to error");
			}
		} catch(Exception e) {
			fail("Failed to parse protocol");
		}
		
		if (journal.getIssues().size() != 1) {
			fail("Issue count not 1: "+journal.getIssues().size());
		}
		
		IssueDetails id=journal.getIssues().get(0);
		
		if (id.getIssueType() != IssueType.Error) {
			fail("Issue was not an error");
		}
		
		Integer lineProp=(Integer)id.getProperties().get(Journal.START_LINE);
		Integer colProp=(Integer)id.getProperties().get(Journal.START_COLUMN);
		Integer posProp=(Integer)id.getProperties().get(Journal.START_POSITION);
		Integer endposProp=(Integer)id.getProperties().get(Journal.END_POSITION);
		
		if (lineProp == null) {
			fail("Line not found");
		}
		
		if (lineProp.intValue() != 4) {
			fail("Line is not 4: "+lineProp);
		}
		
		if (colProp == null) {
			fail("Column not found");
		}
		
		if (colProp.intValue() != 1) {
			fail("Column is not 1: "+colProp);
		}
		
		if (posProp == null) {
			fail("Start Position not found");
		}
		
		if (posProp.intValue() != first.length()) { //+second.length()+third.length()) {
			fail("Start Position is not "+(first.length())+": "+posProp);
			//fail("Start Position is not "+(first.length()+second.length()+third.length())+": "+posProp);
		}

		if (endposProp == null) {
			fail("End Position not found");
		}
		
		//int endpos=first.length()+second.length()+third.length()+fourth.length()-1; // -1 because we want the last character
		int endpos=first.length()+second.length()-1; // -1 because we want the last character
		
		if (endposProp.intValue() != endpos) {
			fail("End Position is not "+endpos+": "+endposProp);
		}
	}
	
	@org.junit.Test
	public void testExtensionChoice() {
		String first="protocol Test {\r\n[[";
		String comment1=" Comment 1 ";
		String second="]]\r\n\tchoice at Buyer {\r\n[[";
		String comment2=" Comment 2 ";
		String third="]]\r\n\t\top1(M1) from Buyer to Seller;\r\n\t} or {\r\n[[";
		String comment3=" Comment 3 ";
		String fourth="]]\r\n\t\top2(M2) from Buyer to Seller;\r\n";
		String fifth="\t}\r\n";
		String sixth="}\r\n";
		String protocol=first+comment1+second+comment2+third+comment3+fourth+fifth+sixth;
		
		ByteArrayContent content=new ByteArrayContent(protocol.getBytes());
		
		ANTLRProtocolParser parser=new ANTLRProtocolParser();
		
		TestAnnotationProcessor ext=new TestAnnotationProcessor();
		parser.setAnnotationProcessor(ext);
		
		CachedJournal journal=new CachedJournal();
		
		try {
			ProtocolModel pm=parser.parse(null, content, journal);
			
			if (pm == null) {
				fail("Protocol Model should not be null");
			}
		} catch(Exception e) {
			fail("Failed to parse protocol");
		}
		
		if (journal.getIssues().size() != 0) {
			fail("No issues should have been reported: "+journal.getIssues().size());
		}

		if (ext.getAnnotations().size() != 3) {
			fail("Annotations expected: "+ext.getAnnotations().size());
		}
		
		/*
		if (ext.getModelObjects().size() != 3) {
			fail("ModelObjects expected: "+ext.getModelObjects().size());
		}
		*/
		
		if (ext.getAnnotations().get(0).equals(comment2) == false) {
			fail("Annotation 0 not expected");
		}
		
		if (ext.getAnnotations().get(1).equals(comment3) == false) {
			fail("Annotation 1 not expected");
		}
		
		if (ext.getAnnotations().get(2).equals(comment1) == false) {
			fail("Annotation 2 not expected");
		}
		
		/*
		if ((ext.getModelObjects().get(0) instanceof When) == false) {
			fail("ModelObject 0 not when");
		}
		
		if (((When)ext.getModelObjects().get(0)).getMessageSignature().getOperation().equals("op1") == false) {
			fail("ModelObject 0 not op1");
		}

		if ((ext.getModelObjects().get(1) instanceof When) == false) {
			fail("ModelObject 1 not when");
		}
		
		if (((When)ext.getModelObjects().get(1)).getMessageSignature().getOperation().equals("op2") == false) {
			fail("ModelObject 1 not op2");
		}
		
		if ((ext.getModelObjects().get(2) instanceof Choice) == false) {
			fail("ModelObject 2 not choice");
		}
		*/		
	}

	@org.junit.Test
	public void testProtocolAnnotation() {
		String first="[[";
		String comment1=" Comment 1 ";
		String second="]]\r\nimport Order;\r\n[[";
		String comment2=" Comment 2 ";
		String third="]]\r\nprotocol Test {\r\n";
		String fourth="}\r\n";
		String protocol=first+comment1+second+comment2+third+fourth;
		
		ByteArrayContent content=new ByteArrayContent(protocol.getBytes());
		
		ANTLRProtocolParser parser=new ANTLRProtocolParser();
		
		TestAnnotationProcessor ext=new TestAnnotationProcessor();
		parser.setAnnotationProcessor(ext);
		
		CachedJournal journal=new CachedJournal();
		
		try {
			ProtocolModel pm=parser.parse(null, content, journal);
			
			if (pm == null) {
				fail("Protocol Model should not be null");
			}
		} catch(Exception e) {
			fail("Failed to parse protocol");
		}

		if (journal.getIssues().size() != 0) {
			fail("No issues should have been reported: "+journal.getIssues().size());
		}

		if (ext.getAnnotations().size() != 2) {
			fail("2 Annotations expected: got "+ext.getAnnotations().size());
		}
		
		/*
		if (ext.getModelObjects().size() != 2) {
			fail("2 ModelObjects expected: got "+ext.getModelObjects().size());
		}
		*/
		
		if (ext.getAnnotations().get(0).equals(comment1) == false) {
			fail("Annotation 0 not expected");
		}
		
		/*
		if ((ext.getModelObjects().get(0) instanceof TypeImportList) == false) {
			fail("ModelObject 0 not TypeImportList");
		}
		*/
		
		if (ext.getAnnotations().get(1).equals(comment2) == false) {
			fail("Annotation 1 not expected");
		}
		
		/*
		if ((ext.getModelObjects().get(1) instanceof Protocol) == false) {
			fail("ModelObject 1 not Protocol");
		}
		*/
	}

	public class TestComment extends ModelObject {
		private String m_comment=null;
		
		public TestComment(String comment) {
			m_comment = comment;
		}
		
		public String getComment() {
			return(m_comment);
		}

		public void visit(Visitor visitor) {
		}
	}

	public class TestAnnotationProcessor implements AnnotationProcessor {

		private java.util.List<String> m_annotations=new java.util.Vector<String>();
		
		public Annotation getAnnotation(String annotation, java.util.Map<String, Object> properties,
								Journal journal) {
			m_annotations.add(annotation);
			return(null);
		}
		
		public java.util.List<String> getAnnotations() {
			return(m_annotations);
		}
	}
}
