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
package org.scribble.trace.junit;

import static org.junit.Assert.*;

import org.scribble.common.resources.DirectoryResourceLocator;

public class JUnitSimulatorTest {

	@org.junit.Test
    public void testSimulate() {
		JUnitSimulator sim=new JUnitSimulator();
		
		java.net.URL url=ClassLoader.getSystemResource("scribble");
		java.io.File f=new java.io.File(url.getFile());
		
		String path=f.getParentFile().getAbsolutePath();
		
		String xmlFile=System.getProperty("java.io.tmpdir")+java.io.File.separator+"traceResult"+
							System.currentTimeMillis()+".xml";
		
		//System.out.println("XMLFILE="+xmlFile);
		
		sim.setResourceLocator(new DirectoryResourceLocator(path));

		try {
			sim.simulate(path, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to simulate with junit results");
		}
		
		// Check results file
		org.w3c.dom.Document doc=null;
		
		try {
			java.io.File resultFile=new java.io.File(xmlFile);
			
			java.io.InputStream is=new java.io.FileInputStream(resultFile);
			
			javax.xml.transform.stream.StreamSource source=new javax.xml.transform.stream.StreamSource(is);
			javax.xml.transform.dom.DOMResult result=new javax.xml.transform.dom.DOMResult();
			
			javax.xml.transform.Transformer transformer=
					javax.xml.transform.TransformerFactory.newInstance().newTransformer();
			
			transformer.transform(source, result);
			
			is.close();
			
			doc = (org.w3c.dom.Document)result.getNode();
			
			// Remove file
			if (!resultFile.delete()) {
				fail("Failed to delete result file");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to check results");
		}
		
		if (doc.getDocumentElement().getChildNodes().getLength() != 14) {
			fail("Expecting 14 testcases, but got: "+doc.getDocumentElement().getChildNodes().getLength());			
		}
		
		for (int i=0; i < doc.getDocumentElement().getChildNodes().getLength(); i++) {
			org.w3c.dom.Node node=doc.getDocumentElement().getChildNodes().item(i);
			
			if (node.getNodeName().equals("testsuite")) {
				org.w3c.dom.Element elem=(org.w3c.dom.Element)node;
				
				for (int j=0; j < elem.getChildNodes().getLength(); j++) {
					org.w3c.dom.Element testcase=(org.w3c.dom.Element)elem.getChildNodes().item(j);
					
					if (testcase.getChildNodes().getLength() != 0 &&
							!elem.getAttribute("name").equals("RequestResponse@Buyer-3:1")) {
						fail("Test case '"+testcase.getAttribute("name")+"', in testsuite '"+
								elem.getAttribute("name")+
								"' should not have a failure or error");
					}
				}
			}
		}
    }
}
