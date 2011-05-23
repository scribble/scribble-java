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
package org.scribble.protocol;


import static org.junit.Assert.*;

import java.io.IOException;

import org.scribble.common.logging.CachedJournal;
import org.scribble.common.logging.Journal;
import org.scribble.common.resource.Content;
import org.scribble.common.resource.DefaultResourceLocator;
import org.scribble.protocol.model.ProtocolImport;
import org.scribble.protocol.model.ProtocolImportList;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.parser.DefaultProtocolParserManager;
import org.scribble.protocol.parser.ProtocolParserManager;

public class DefaultProtocolContextTest {

	private static final String CONTENT_PROPERTY = "content";

	@org.junit.Test
	public void testGetProtocolModelSameFolder() {
		String refFile="Ref1.spr";
		
		java.net.URL url=ClassLoader.getSystemResource("context");
		
		if (url == null) {
			fail("Couldn't find base directory");
		}
		
		ProtocolParserManager parserManager=new TestProtocolParserManager();
		
		DefaultProtocolContext context=new DefaultProtocolContext(parserManager,
							new DefaultResourceLocator(new java.io.File(url.getFile())));
		
		ProtocolImportList imports=new ProtocolImportList();
		
		ProtocolImport pi=new ProtocolImport();
		pi.setName("Ref1");
		pi.setLocation(refFile);
		
		imports.getProtocolImports().add(pi);
		
		CachedJournal journal=new CachedJournal();
		
		ProtocolModel pm=context.getProtocolModel(pi, journal);
		
		if (journal.hasErrors()) {
			fail("Journal has reported errors");
		}
		
		if (pm == null) {
			fail("Protocol model not returned");
		}
		
		if (pm.getProperties().get(CONTENT_PROPERTY).equals(pi.getName()) == false) {
			fail("Protocol model content property not as expected '"+pm.getProperties().get(CONTENT_PROPERTY)+
					"', expecting: "+pi.getName());
		}
	}
	
	@org.junit.Test
	public void testGetProtocolModelRelativeFolder() {
		String refFile="../subref/Ref2.spr";
		
		java.net.URL url=ClassLoader.getSystemResource("context/submain");
		
		if (url == null) {
			fail("Couldn't find base directory");
		}
		
		ProtocolParserManager parserManager=new TestProtocolParserManager();
		
		DefaultProtocolContext context=new DefaultProtocolContext(parserManager,
							new DefaultResourceLocator(new java.io.File(url.getFile())));
		
		ProtocolImportList imports=new ProtocolImportList();
		
		ProtocolImport pi=new ProtocolImport();
		pi.setName("Ref2");
		pi.setLocation(refFile);
		
		imports.getProtocolImports().add(pi);
		
		CachedJournal journal=new CachedJournal();
		
		ProtocolModel pm=context.getProtocolModel(pi, journal);
		
		if (journal.hasErrors()) {
			fail("Journal has reported errors");
		}
		
		if (pm == null) {
			fail("Protocol model not returned");
		}
		
		if (pm.getProperties().get(CONTENT_PROPERTY).equals(pi.getName()) == false) {
			fail("Protocol model content property not as expected '"+pm.getProperties().get(CONTENT_PROPERTY)+
					"', expecting: "+pi.getName());
		}
	}

	@org.junit.Test
	@org.junit.Ignore
	public void testGetProtocolModelScribbleURL() {
		ProtocolParserManager parserManager=new TestProtocolParserManager();
		
		DefaultProtocolContext context=new DefaultProtocolContext(parserManager,
							new DefaultResourceLocator(null));
		
		ProtocolImportList imports=new ProtocolImportList();
		
		ProtocolImport pi=new ProtocolImport();
		pi.setName("Ref1");
		pi.setLocation("http://www.scribble.org");
		
		imports.getProtocolImports().add(pi);
		
		CachedJournal journal=new CachedJournal();
		
		ProtocolModel pm=context.getProtocolModel(pi, journal);
		
		if (journal.hasErrors()) {
			fail("Journal has reported errors");
		}
		
		if (pm == null) {
			fail("Protocol model not returned");
		}
		
		if (((String)pm.getProperties().get(CONTENT_PROPERTY)).
						contains("Scribble - JBoss Community") == false) {
			fail("Protocol model content property did not contain scribble home page");
		}
	}
	
	public class TestProtocolParserManager extends DefaultProtocolParserManager {

		@Override
		public ProtocolModel parse(Content content, Journal journal,
							ProtocolContext context) throws IOException {
			java.io.InputStream is=content.getInputStream();
			byte[] b=new byte[is.available()];
			
			is.read(b);
			
			is.close();
			
			ProtocolModel ret=new ProtocolModel();
			ret.getProperties().put(CONTENT_PROPERTY, new String(b));
			
			return(ret);
		}
		
	}
}
