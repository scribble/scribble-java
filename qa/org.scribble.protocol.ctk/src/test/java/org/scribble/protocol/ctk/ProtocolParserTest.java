/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.protocol.ctk;

import static org.junit.Assert.*;

import org.scribble.common.model.DefaultAnnotation;
import org.scribble.protocol.model.*;

public class ProtocolParserTest {
	
	@org.junit.Test
	public void testAnnotation() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/Annotation.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		imp.getAnnotations().add(new DefaultAnnotation(" Annotation before import "));
		
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("SingleInteraction");
		protocol.getAnnotations().add(new DefaultAnnotation(" Annotation before top protocol "));
		
		RoleList rl=new RoleList();
		rl.getAnnotations().add(new DefaultAnnotation(" Annotation before role list "));
		
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Interaction interaction=new Interaction();
		interaction.getAnnotations().add(new DefaultAnnotation(" Annotation before interaction "));
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testBlockComment() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/BlockComment.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("SingleInteraction");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testLineComment() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/LineComment.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("SingleInteraction");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testSingleInteraction() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/SingleInteraction.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("SingleInteraction");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testSingleInteractionWithXSDImport() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/SingleInteractionXSDImport.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp1=new TypeImportList();
		imp1.setFormat("xml");
		imp1.setLocation("../schema/MySchema.xsd");
		
		TypeImport t1=new TypeImport();
		t1.setName("Order");
		DataType dt1=new DataType();
		dt1.setDetails("{http://www.acme.org/Purchasing}Order");
		t1.setDataType(dt1);
		imp1.getTypeImports().add(t1);
		
		TypeImport t2=new TypeImport();
		t2.setName("Quote");
		DataType dt2=new DataType();
		dt2.setDetails("{http://www.acme.org/Purchasing}Quote");
		t2.setDataType(dt2);
		imp1.getTypeImports().add(t2);
		
		expected.getImports().add(imp1);
		
		TypeImportList imp2=new TypeImportList();
		imp2.setFormat("java");
		imp2.setLocation("org.scribble");
		
		TypeImport t3=new TypeImport();
		t3.setName("Order");
		DataType dt3=new DataType();
		dt3.setDetails("Order");
		t3.setDataType(dt3);
		imp2.getTypeImports().add(t3);
		
		TypeImport t4=new TypeImport();
		t4.setName("Quote");
		DataType dt4=new DataType();
		dt4.setDetails("Quote");
		t4.setDataType(dt4);
		imp2.getTypeImports().add(t4);
		
		expected.getImports().add(imp2);

		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("SingleInteraction");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testSingleInteractionRPC() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/SingleInteractionRPC.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Customer");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("SingleInteractionRPC");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		
		ms.setOperation("submit");
		
		TypeReference tref1=new TypeReference();
		tref1.setName("Order");
		ms.getTypeReferences().add(tref1);
		
		TypeReference tref2=new TypeReference();
		tref2.setName("Customer");
		ms.getTypeReferences().add(tref2);

		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testMulticastInteraction() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/MulticastInteraction.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("MulticastInteraction");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		
		Role sellerA=new Role();
		sellerA.setName("SellerA");
		rl.getRoles().add(sellerA);
		
		Role sellerB=new Role();
		sellerB.setName("SellerB");
		rl.getRoles().add(sellerB);
		
		protocol.getBlock().add(rl);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(sellerA);
		interaction.getToRoles().add(sellerB);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testRaise() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/Raise.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("Raise");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);

		Raise raise=new Raise();
		
		TypeReference tref=new TypeReference();
		tref.setName("ExcType");
		raise.setType(tref);
		
		raise.getRoles().add(seller);
		
		protocol.getBlock().add(raise);
		
		CTKUtil.verify(model, expected);
	}

	@org.junit.Test
	public void testTryCatch() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/TryCatch.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("OutOfStock");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("OrderExpired");
		imp.getTypeImports().add(t);
		t=new TypeImport();
		t.setName("OrderCancelled");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Confirm");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Ack");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Finish");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("TryCatch");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);

		Try tryEscape=new Try();
		
		Block b=new Block();
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		b.add(interaction);
		
		interaction = new Interaction();
		
		ms = new MessageSignature();
		tref = new TypeReference();
		tref.setName("Confirm");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(seller);
		interaction.getToRoles().add(buyer);
		
		b.add(interaction);

		tryEscape.setBlock(b);

		Catch catchBlock=new Catch();
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("OutOfStock");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(seller);
		interaction.getToRoles().add(buyer);
		
		catchBlock.getInteractions().add(interaction);

		tryEscape.getCatches().add(catchBlock);
		
		catchBlock = new Catch();
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		ms.setOperation("expire");
		tref=new TypeReference();
		tref.setName("OrderExpired");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		catchBlock.getInteractions().add(interaction);

		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("OrderCancelled");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		catchBlock.getInteractions().add(interaction);
		
		interaction = new Interaction();
		
		ms = new MessageSignature();
		tref = new TypeReference();
		tref.setName("Ack");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(seller);
		interaction.getToRoles().add(buyer);
		
		catchBlock.getBlock().add(interaction);

		tryEscape.getCatches().add(catchBlock);
		
		protocol.getBlock().add(tryEscape);
		
		interaction = new Interaction();
		
		ms = new MessageSignature();
		tref = new TypeReference();
		tref.setName("Finish");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testChoice() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/Choice.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Cancel");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Invoice");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("SingleInteraction");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Choice choice=new Choice();
		
		choice.setFromRole(buyer);
		choice.setToRole(seller);

		When b1=new When();
		choice.getWhens().add(b1);
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		b1.setMessageSignature(ms);
		
		Interaction i1=new Interaction();
		i1.setFromRole(seller);
		i1.getToRoles().add(buyer);
		
		MessageSignature ms2=new MessageSignature();
		TypeReference tref2=new TypeReference();
		tref2.setName("Invoice");
		ms2.getTypeReferences().add(tref2);
		i1.setMessageSignature(ms2);
		
		b1.getBlock().add(i1);
		
		When b2=new When();
		choice.getWhens().add(b2);
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Cancel");
		ms.getTypeReferences().add(tref);
		b2.setMessageSignature(ms);
		
		protocol.getBlock().add(choice);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testParallel() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/Parallel.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("M1");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("M2");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("M3");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("Parallel");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Parallel parallel=new Parallel();
		
		Block b1=new Block();
		parallel.getBlocks().add(b1);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("M1");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		b1.add(interaction);
		
		Block b2=new Block();
		parallel.getBlocks().add(b2);
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("M2");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		b2.add(interaction);
		
		protocol.getBlock().add(parallel);
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("M3");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);

		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testRepeat() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/Repeat.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Finish");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("Repeat");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Repeat repeat=new Repeat();
		
		repeat.getRoles().add(buyer);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		repeat.getBlock().add(interaction);
		
		protocol.getBlock().add(repeat);
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Finish");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testUnordered() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/Unordered.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Cancel");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Finish");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("Unordered");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Unordered unordered=new Unordered();
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		unordered.getBlock().add(interaction);
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Cancel");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		unordered.getBlock().add(interaction);
		
		protocol.getBlock().add(unordered);
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Finish");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction);
		
		CTKUtil.verify(model, expected);
	}

	@org.junit.Test
	public void testRecur() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/Recur.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Finish");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Rejected");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Accepted");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Another");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("Recur");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		RecBlock lb=new RecBlock();
		
		lb.setLabel("Transaction");
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer);
		interaction.getToRoles().add(seller);
		
		lb.getBlock().add(interaction);
		
		Choice choice=new Choice();
		
		choice.setFromRole(seller);
		choice.setToRole(buyer);

		When b1=new When();
		choice.getWhens().add(b1);
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Rejected");
		ms.getTypeReferences().add(tref);
		ms.setOperation("tryAgain");
		b1.setMessageSignature(ms);
		
		Recursion recursion=new Recursion();
		recursion.setLabel("Transaction");

		b1.getBlock().add(recursion);
		
		When b2=new When();
		choice.getWhens().add(b2);
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Accepted");
		ms.getTypeReferences().add(tref);
		ms.setOperation("exit");
		b2.setMessageSignature(ms);
		
		lb.getBlock().add(choice);
		
		Interaction interaction2=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Another");
		ms.getTypeReferences().add(tref);
		interaction2.setMessageSignature(ms);
		interaction2.setFromRole(buyer);
		interaction2.getToRoles().add(seller);
		
		lb.getBlock().add(interaction2);
		
		protocol.getBlock().add(lb);
		
		Interaction interaction3=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Finish");
		ms.getTypeReferences().add(tref);
		interaction3.setMessageSignature(ms);
		interaction3.setFromRole(buyer);
		interaction3.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction3);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testOptional() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/Optional.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("Optional");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		
		Interaction interaction1=new Interaction();
		
		MessageSignature ms1=new MessageSignature();
		TypeReference tref1=new TypeReference();
		tref1.setName("Order");
		ms1.getTypeReferences().add(tref1);
		interaction1.setMessageSignature(ms1);
		interaction1.setFromRole(buyer);
		interaction1.getToRoles().add(seller);
		
		protocol.getBlock().add(interaction1);
		
		Optional optional=new Optional();
		
		optional.getRoles().add(buyer);
		
		Interaction interaction2=new Interaction();
		
		MessageSignature ms2=new MessageSignature();
		TypeReference tref2=new TypeReference();
		tref2.setName("Order");
		ms2.getTypeReferences().add(tref2);
		interaction2.setMessageSignature(ms2);
		interaction2.setFromRole(buyer);
		interaction2.getToRoles().add(seller);
		
		optional.getBlock().add(interaction2);
		
		protocol.getBlock().add(optional);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testIncludeProtocol() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/IncludeProtocol.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		ProtocolImportList pimp=new ProtocolImportList();
		ProtocolImport pi=new ProtocolImport();
		pi.setName("Sub");
		pi.setLocation("location");
		pimp.getProtocolImports().add(pi);
		expected.getImports().add(pimp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("IncludeProtocol");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		

		Include inc=new Include();
		
		ProtocolReference ref=new ProtocolReference();
		ref.setName("Sub");
		
		inc.setReference(ref);
		
		Parameter db1=new Parameter("Buyer");
		inc.getParameters().add(db1);
		
		Parameter db2=new Parameter("Seller");
		inc.getParameters().add(db2);
		
		protocol.getBlock().add(inc);
		
		CTKUtil.verify(model, expected);
	}
	
	@org.junit.Test
	public void testRunSubProtocol() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/RunSubProtocol.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Confirm");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("RunSubProtocol");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		

		Run run=new Run();
		
		ProtocolReference ref=new ProtocolReference();
		ref.setName("Sub");
		
		run.setProtocolReference(ref);
		
		Parameter db1=new Parameter("Buyer");
		run.getParameters().add(db1);
		
		Parameter db2=new Parameter("Seller");
		run.getParameters().add(db2);
		
		protocol.getBlock().add(run);
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Confirm");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(seller);
		interaction.getToRoles().add(buyer);
		
		protocol.getBlock().add(interaction);
		
		
		Protocol subprotocol=new Protocol();
		subprotocol.setName("Sub");
		
		Role subbuyer=new Role();
		subbuyer.setName("SubBuyer");
		Role subseller=new Role();
		subseller.setName("SubSeller");

		/*
		RoleList subpl=new RoleList();
		Role subbuyer=new Role();
		subbuyer.setName("SubBuyer");
		subpl.getRoles().add(subbuyer);
		Role subseller=new Role();
		subseller.setName("SubSeller");
		subpl.getRoles().add(subseller);
		
		subprotocol.getBlock().add(subpl);
		*/
		
		subprotocol.getParameterDefinitions().add(new ParameterDefinition("SubBuyer"));
		subprotocol.getParameterDefinitions().add(new ParameterDefinition("SubSeller"));
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(subbuyer);
		interaction.getToRoles().add(subseller);
		
		subprotocol.getBlock().add(interaction);
		
		protocol.getBlock().add(subprotocol);
		
		CTKUtil.verify(model, expected);
	}

	@org.junit.Test
	public void testRunInlineProtocol() {
		TestJournal logger=new TestJournal();
		
		ProtocolModel model=CTKUtil.getModel("tests/protocol/global/RunInlineProtocol.spr", logger);
		
		assertNotNull(model);
		
		assertTrue(logger.getErrorCount() == 0);
		
		// Build expected model
		ProtocolModel expected=new ProtocolModel();
		
		TypeImportList imp=new TypeImportList();
		TypeImport t=new TypeImport();
		t.setName("Order");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		imp=new TypeImportList();
		t=new TypeImport();
		t.setName("Confirm");
		imp.getTypeImports().add(t);
		expected.getImports().add(imp);
		
		Protocol protocol=new Protocol();
		expected.setProtocol(protocol);
		
		protocol.setName("RunInlineProtocol");
		
		RoleList rl=new RoleList();
		Role buyer=new Role();
		buyer.setName("Buyer");
		rl.getRoles().add(buyer);
		Role seller=new Role();
		seller.setName("Seller");
		rl.getRoles().add(seller);
		
		protocol.getBlock().add(rl);
		

		Run run=new Run();
		
		Parameter db1=new Parameter("Buyer");
		run.getParameters().add(db1);
		
		Parameter db2=new Parameter("Seller");
		run.getParameters().add(db2);
		
		protocol.getBlock().add(run);
				
		Block subprotocol=new Block();
		
		/*
		RoleList subpl=new RoleList();
		Role subbuyer=new Role();
		subbuyer.setName("SubBuyer");
		subpl.getRoles().add(subbuyer);
		Role subseller=new Role();
		subseller.setName("SubSeller");
		subpl.getRoles().add(subseller);
		
		subprotocol.add(subpl);
		*/
		
		Interaction interaction=new Interaction();
		
		MessageSignature ms=new MessageSignature();
		TypeReference tref=new TypeReference();
		tref.setName("Order");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(buyer); //subbuyer);
		interaction.getToRoles().add(seller); //subseller);
		
		subprotocol.add(interaction);
		
		run.setBlock(subprotocol);
		
		interaction=new Interaction();
		
		ms=new MessageSignature();
		tref=new TypeReference();
		tref.setName("Confirm");
		ms.getTypeReferences().add(tref);
		interaction.setMessageSignature(ms);
		interaction.setFromRole(seller);
		interaction.getToRoles().add(buyer);
		
		protocol.getBlock().add(interaction);

		CTKUtil.verify(model, expected);
	}
}
