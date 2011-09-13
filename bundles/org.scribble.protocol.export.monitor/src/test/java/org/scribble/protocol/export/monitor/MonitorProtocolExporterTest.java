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
package org.scribble.protocol.export.monitor;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.scribble.common.logging.CachedJournal;
import org.scribble.protocol.model.*;
import org.scribble.protocol.monitor.util.MonitorModelUtil;

public class MonitorProtocolExporterTest {
	
	private static final String SUBPROTOCOL = "subprotocol";
	private static final String ORDER = "Order";
	private static final String ORDER_TYPE = "Order";

	@org.junit.Test
	public void testSimpleInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
		
		TypeImportList imp1=new TypeImportList();
		TypeImport t1=new TypeImport();
		t1.setName(ORDER_TYPE);
		imp1.getTypeImports().add(t1);
		pm.getImports().add(imp1);
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		TypeReference tref1=new TypeReference();
		tref1.setName(ORDER);
		ms1.getTypeReferences().add(tref1);
		
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleInteraction");
	}

	@org.junit.Test
	public void testSimpleInteractionWithAnnotation() {
		
		ProtocolModel pm=new ProtocolModel();
		
		TypeImportList imp1=new TypeImportList();
		TypeImport t1=new TypeImport();
		t1.setName(ORDER_TYPE);
		imp1.getTypeImports().add(t1);
		pm.getImports().add(imp1);
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		TypeReference tref1=new TypeReference();
		tref1.setName(ORDER);
		ms1.getTypeReferences().add(tref1);
		
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		org.scribble.common.model.Annotation a1=
					new org.scribble.common.model.DefaultAnnotation("a1", " My Annotation ");
		i1.getAnnotations().add(a1);
		
		p.getBlock().add(i1);
		
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleInteractionWithAnnotation");
	}

	@org.junit.Test
	public void testSimpleSequence() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Interaction i2=new Interaction();
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		i2.setMessageSignature(ms2);
		i2.setFromRole(p2);
		
		p.getBlock().add(i2);
		
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
		
		if (pd.getNode().size() != 2) {
			fail("Expected 2 nodes: "+pd.getNode().size());
		}
		
		org.scribble.protocol.monitor.model.Node pn1=pd.getNode().get(0);
		org.scribble.protocol.monitor.model.Node pn2=pd.getNode().get(1);
		
		if (pn1.getNextIndex() != 1) {
			fail("Node 1 next index incorrect");
		}
		
		if ((pn1 instanceof org.scribble.protocol.monitor.model.SendMessage) == false) {
			fail("Node 1 wrong node type");
		}
		
		if (pn2.getNextIndex() != -1) {
			fail("Node 2 next index incorrect");
		}
		
		if ((pn2 instanceof org.scribble.protocol.monitor.model.ReceiveMessage) == false) {
			fail("Node 2 wrong node type");
		}
	}

	@org.junit.Test
	public void testSimpleChoiceFollowedByInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Choice c1=new Choice();
		c1.setRole(p2);
		
		p.getBlock().add(c1);
		
		Block wb1=new Block();
		
		Interaction iwb1=new Interaction();
		
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		
		TypeReference tr2=new TypeReference();
		tr2.setName("M2");
		ms2.getTypeReferences().add(tr2);
		
		iwb1.setMessageSignature(ms2);
		iwb1.setFromRole(p2);
		
		wb1.add(iwb1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		wb1.add(i3);
		
		c1.getBlocks().add(wb1);
		
		Block wb2=new Block();
		
		Interaction iwb2=new Interaction();
		
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("op4");
		iwb2.setMessageSignature(ms4);
		
		iwb2.setFromRole(p2);
		wb2.add(iwb2);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("op5");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		wb2.add(i5);

		c1.getBlocks().add(wb2);

		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleChoiceFollowedByInteraction");
	}	

	@org.junit.Test
	public void testSimpleDirectedChoiceFollowedByInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		DirectedChoice c1=new DirectedChoice();
		c1.setFromRole(p2);
		
		p.getBlock().add(c1);
		
		OnMessage wb1=new OnMessage();
		
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		
		TypeReference tr2=new TypeReference();
		tr2.setName("M2");
		ms2.getTypeReferences().add(tr2);
		
		wb1.setMessageSignature(ms2);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		wb1.getBlock().add(i3);
		
		c1.getOnMessages().add(wb1);
		
		OnMessage wb2=new OnMessage();
		
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("op4");
		wb2.setMessageSignature(ms4);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("op5");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		wb2.getBlock().add(i5);

		c1.getOnMessages().add(wb2);

		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleDirectedChoiceFollowedByInteraction");
	}	

	@org.junit.Test
	public void testSimpleChoiceFollowedByInteractionWithAnnotation() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Choice c1=new Choice();
		c1.setRole(p2);
		
		p.getBlock().add(c1);
		
		Block wb1=new Block();
		
		Interaction iwb1=new Interaction();
		
		org.scribble.common.model.Annotation a0=
			new org.scribble.common.model.DefaultAnnotation("a0", " My Annotation0 ");
		iwb1.getAnnotations().add(a0);

		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		
		TypeReference tr2=new TypeReference();
		tr2.setName("M2");
		ms2.getTypeReferences().add(tr2);
		
		iwb1.setMessageSignature(ms2);
		iwb1.setFromRole(p2);
		wb1.add(iwb1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		wb1.add(i3);
		
		org.scribble.common.model.Annotation a1=
			new org.scribble.common.model.DefaultAnnotation("a1", " My Annotation ");
		wb1.getAnnotations().add(a1);

		c1.getBlocks().add(wb1);
		
		Block wb2=new Block();
		
		Interaction iwb2=new Interaction();
		
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("op4");
		iwb2.setMessageSignature(ms4);
		
		iwb2.setFromRole(p2);
		wb2.add(iwb2);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("op5");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		wb2.add(i5);

		org.scribble.common.model.Annotation a2=
			new org.scribble.common.model.DefaultAnnotation("a2", " My Annotation2 ");
		wb2.getAnnotations().add(a2);

		c1.getBlocks().add(wb2);

		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleChoiceFollowedByInteractionWithAnnotation");
	}	

	@org.junit.Test
	public void testSimpleChoiceFollowedByNoActivity() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Choice c1=new Choice();
		c1.setRole(p2);
		
		p.getBlock().add(c1);
		
		Block wb1=new Block();
		
		Interaction iwb1=new Interaction();
		
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		
		TypeReference tr2=new TypeReference();
		tr2.setName("M2");
		ms2.getTypeReferences().add(tr2);
		
		iwb1.setMessageSignature(ms2);
		iwb1.setFromRole(p2);
		
		wb1.add(iwb1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		wb1.add(i3);
		
		c1.getBlocks().add(wb1);
		
		Block wb2=new Block();
		
		Interaction iwb2=new Interaction();
		
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("op4");
		iwb2.setMessageSignature(ms4);
		
		iwb2.setFromRole(p2);
		wb2.add(iwb2);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("op5");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		wb2.add(i5);

		c1.getBlocks().add(wb2);

		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleChoiceFollowedByNoActivity");
	}
	
	@org.junit.Test
	public void testSimpleDirectedChoiceFollowedByNoActivity() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		DirectedChoice c1=new DirectedChoice();
		c1.setFromRole(p2);
		
		p.getBlock().add(c1);
		
		OnMessage wb1=new OnMessage();
		
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		
		TypeReference tr2=new TypeReference();
		tr2.setName("M2");
		ms2.getTypeReferences().add(tr2);
		
		wb1.setMessageSignature(ms2);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		wb1.getBlock().add(i3);
		
		c1.getOnMessages().add(wb1);
		
		OnMessage wb2=new OnMessage();
		
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("op4");
		wb2.setMessageSignature(ms4);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("op5");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		wb2.getBlock().add(i5);

		c1.getOnMessages().add(wb2);

		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleDirectedChoiceFollowedByNoActivity");
	}
	
	@org.junit.Test
	public void testSimpleChoiceWithEmptyPathFollowedByInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Choice c1=new Choice();
		c1.setRole(p2);
		
		p.getBlock().add(c1);
		
		Block wb1=new Block();
		
		Interaction iwb1=new Interaction();
		
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		
		TypeReference tr2=new TypeReference();
		tr2.setName("M2");
		ms2.getTypeReferences().add(tr2);
		
		iwb1.setMessageSignature(ms2);
		iwb1.setFromRole(p2);
		wb1.add(iwb1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		wb1.add(i3);
		
		c1.getBlocks().add(wb1);
		
		Block wb2=new Block();
		
		c1.getBlocks().add(wb2);
		
		Interaction iwb2=new Interaction();
		
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("op4");
		
		TypeReference tr4=new TypeReference();
		tr4.setName("M4");
		ms4.getTypeReferences().add(tr4);
		
		iwb2.setMessageSignature(ms4);
		iwb2.setFromRole(p2);
		p.getBlock().add(iwb2);

		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleChoiceWithEmptyPathFollowedByInteraction");
	}
	
	@org.junit.Test
	public void testSimpleRepeatFollowedByInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Repeat r1=new Repeat();
		r1.getRoles().add(p2);
		
		p.getBlock().add(r1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.setFromRole(p2);
		
		r1.getBlock().add(i3);
		
		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleRepeatFollowedByInteraction");
	}

	@org.junit.Test
	public void testSimpleRepeatFollowedByNoActivity() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Repeat r1=new Repeat();
		r1.getRoles().add(p2);
		
		p.getBlock().add(r1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.setFromRole(p2);
		
		r1.getBlock().add(i3);
		
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleRepeatFollowedByNoActivity");
	}

	@org.junit.Test
	public void testSimpleRunFollowedByInteraction() {
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Run r1=new Run();
		ProtocolReference pref=new ProtocolReference();
		pref.setName(SUBPROTOCOL);
		pref.setRole(p1);
		r1.setProtocolReference(pref);
		
		p.getBlock().add(r1);
		
		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
		
		Protocol subp=new Protocol();
		subp.setName(SUBPROTOCOL);
		subp.setRole(p1);
		p.getNestedProtocols().add(subp);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.setFromRole(p2);
		
		subp.getBlock().add(i3);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleRunFollowedByInteraction");
	}

	@org.junit.Test
	public void testSimpleRunFollowedByNoActivity() {
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Run r1=new Run();
		ProtocolReference pref=new ProtocolReference();
		pref.setName(SUBPROTOCOL);
		pref.setRole(p1);
		r1.setProtocolReference(pref);
		
		p.getBlock().add(r1);
		
		Protocol subp=new Protocol();
		subp.setName(SUBPROTOCOL);
		subp.setRole(p1);
		p.getNestedProtocols().add(subp);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.setFromRole(p2);
		
		subp.getBlock().add(i3);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleRunFollowedByNoActivity");
	}

	@org.junit.Test
	public void testSimpleRecursionFollowedByInteraction() {
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		RecBlock r1=new RecBlock();
		r1.setLabel("transaction");
		
		p.getBlock().add(r1);
		
		Choice c1=new Choice();
		c1.setRole(p2);
		
		r1.getBlock().add(c1);
		
		Block wb1=new Block();
		
		Interaction iwb1=new Interaction();
		
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		
		TypeReference tr2=new TypeReference();
		tr2.setName("M2");
		ms2.getTypeReferences().add(tr2);
		
		iwb1.setMessageSignature(ms2);
		iwb1.setFromRole(p2);
		wb1.add(iwb1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		wb1.add(i3);
		
		c1.getBlocks().add(wb1);
		
		Block wb2=new Block();
		
		Interaction iwb2=new Interaction();
		
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("op4");
		iwb2.setMessageSignature(ms4);
		
		iwb2.setFromRole(p2);
		wb2.add(iwb2);

		Recursion rec=new Recursion();
		rec.setLabel("transaction");
		
		wb2.add(rec);
		
		c1.getBlocks().add(wb2);
		
		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleRecursionFollowedByInteraction");
	}


	@org.junit.Test
	public void testSimpleRecursionWithInnerInteractionFollowedByInteraction() {
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		RecBlock r1=new RecBlock();
		r1.setLabel("transaction");
		
		p.getBlock().add(r1);
		
		Choice c1=new Choice();
		c1.setRole(p2);
		
		r1.getBlock().add(c1);
		
		Block wb1=new Block();
		
		Interaction iwb1=new Interaction();
		
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("op2");
		
		TypeReference tr2=new TypeReference();
		tr2.setName("M2");
		ms2.getTypeReferences().add(tr2);
		
		iwb1.setMessageSignature(ms2);
		iwb1.setFromRole(p2);
		wb1.add(iwb1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		wb1.add(i3);
		
		c1.getBlocks().add(wb1);
		
		Block wb2=new Block();
		
		Interaction iwb2=new Interaction();
		
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("op4");
		iwb2.setMessageSignature(ms4);
		iwb2.setFromRole(p2);
		
		wb2.add(iwb2);

		Recursion rec=new Recursion();
		rec.setLabel("transaction");
		
		wb2.add(rec);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("op5");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);

		wb2.add(i5);

		c1.getBlocks().add(wb2);
		
		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleRecursionWithInnerInteractionFollowedByInteraction");
	}

	@org.junit.Test
	public void testSimpleParallelFollowedByInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Parallel par1=new Parallel();
		
		p.getBlock().add(par1);
		
		Block b1=new Block();
		par1.getBlocks().add(b1);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		b1.add(i3);
		
		Block b2=new Block();
		par1.getBlocks().add(b2);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("op5");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		b2.add(i5);


		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleParallelFollowedByInteraction");
	}	

	@org.junit.Test
	public void testSimpleUnorderedFollowedByInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("op1");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Unordered unord=new Unordered();
		
		p.getBlock().add(unord);
		
		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("op3");
		i3.setMessageSignature(ms3);
		i3.getToRoles().add(p2);
		
		unord.getBlock().add(i3);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("op5");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		unord.getBlock().add(i5);


		Interaction i6=new Interaction();
		MessageSignature ms6=new MessageSignature();
		ms6.setOperation("op6");
		i6.setMessageSignature(ms6);
		i6.setFromRole(p2);
		
		p.getBlock().add(i6);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleUnorderedFollowedByInteraction");
	}	

	@org.junit.Test
	public void testSimpleDoInterruptFollowedByInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("start");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Do t=new Do();
		
		p.getBlock().add(t);
				
		Interaction i2=new Interaction();
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("order");
		i2.setMessageSignature(ms2);
		i2.getToRoles().add(p2);
		
		t.getBlock().add(i2);

		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("confirm");
		i3.setMessageSignature(ms3);
		i3.setFromRole(p2);
		
		t.getBlock().add(i3);
		
		Interrupt cb=new Interrupt();
		t.getInterrupts().add(cb);

		Interaction i4=new Interaction();
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("cancel");
		i4.setMessageSignature(ms4);
		i4.getToRoles().add(p2);
		cb.getBlock().add(i4);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("finish");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		p.getBlock().add(i5);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleDoInterruptFollowedByInteraction");
	}

	@org.junit.Test
	public void testSimpleDoInterruptMultipleFollowedByInteraction() {
		
		ProtocolModel pm=new ProtocolModel();
	
		Role p1=new Role();
		p1.setName("p1");
		
		Role p2=new Role();
		p2.setName("p2");
		
		Protocol p=new Protocol();
		p.setRole(p1);
		pm.setProtocol(p);
		
		Interaction i1=new Interaction();
		MessageSignature ms1=new MessageSignature();
		ms1.setOperation("start");
		i1.setMessageSignature(ms1);
		i1.getToRoles().add(p2);
		
		p.getBlock().add(i1);
		
		Do t=new Do();
		
		p.getBlock().add(t);
				
		Interaction i2=new Interaction();
		MessageSignature ms2=new MessageSignature();
		ms2.setOperation("order");
		i2.setMessageSignature(ms2);
		i2.getToRoles().add(p2);
		
		t.getBlock().add(i2);

		Interaction i3=new Interaction();
		MessageSignature ms3=new MessageSignature();
		ms3.setOperation("confirm");
		i3.setMessageSignature(ms3);
		i3.setFromRole(p2);
		
		t.getBlock().add(i3);
		
		Interrupt cb1=new Interrupt();
		t.getInterrupts().add(cb1);

		Interaction i4=new Interaction();
		MessageSignature ms4=new MessageSignature();
		ms4.setOperation("cancel");
		i4.setMessageSignature(ms4);
		i4.getToRoles().add(p2);
		cb1.getBlock().add(i4);
		
		Interrupt cb2=new Interrupt();
		t.getInterrupts().add(cb2);

		Interaction i4x=new Interaction();
		MessageSignature ms4x=new MessageSignature();
		ms4x.setOperation("abort");
		i4x.setMessageSignature(ms4x);
		i4x.getToRoles().add(p2);
		cb2.getBlock().add(i4x);
		
		Interaction i4y=new Interaction();
		MessageSignature ms4y=new MessageSignature();
		ms4y.setOperation("ack");
		i4y.setMessageSignature(ms4y);
		i4y.setFromRole(p2);
		
		cb2.getBlock().add(i4y);
		
		Interaction i5=new Interaction();
		MessageSignature ms5=new MessageSignature();
		ms5.setOperation("finish");
		i5.setMessageSignature(ms5);
		i5.getToRoles().add(p2);
		
		p.getBlock().add(i5);
				
		MonitorProtocolExporter exporter=new MonitorProtocolExporter();
		
		CachedJournal journal=new CachedJournal();
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		
		exporter.export(pm, journal, os);
		
		if (journal.getIssues().size() > 0) {
			fail("Export should not have raised any issues");
		}
		
		org.scribble.protocol.monitor.model.Description pd=null;
		
		try {
			pd = org.scribble.protocol.monitor.util.MonitorModelUtil.deserialize(new java.io.ByteArrayInputStream(os.toByteArray()));
		} catch(Exception e) {
			e.printStackTrace();
			fail("Failed to parse protocol monitor description: "+e);
		}
	
		validateMonitor(pd, "SimpleDoInterruptMultipleFollowedByInteraction");
	}

	protected void validateMonitor(org.scribble.protocol.monitor.model.Description pd, String filename) {
		java.io.ByteArrayOutputStream os=new java.io.ByteArrayOutputStream();
		String pdtext=null;
		
		try {
			MonitorModelUtil.serialize(pd, os);

			pdtext = new String(os.toByteArray());
			
			os.close();
		} catch(Exception e) {
			fail("Failed to serialize description associated with filename '"+filename+"'");
		}
		
		java.io.InputStream is=
			MonitorProtocolExporterTest.class.getResourceAsStream("/monitor/results/"+filename+".txt");
	
		if (is == null) {
			fail("Failed to load protocol '"+filename+"'");
		}
		
		byte[] b=null;
		
		try {
			b = new byte[is.available()];
			
			is.read(b);
			
			is.close();
		} catch(Exception e) {
			fail("Failed to load result '"+filename+": "+e);
		}
		
		String result=new String(b);
		
		if (result.equals(pdtext) == false) {
			System.out.println("["+filename+"]="+pdtext);
			fail("Monitor result incorrect: was expecting (len="+result.length()+") ["+result+
					"] but got (len="+pdtext.length()+") ["+pdtext+"]");
		}
 	}
}
