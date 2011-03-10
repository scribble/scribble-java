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
package org.scribble.protocol.ctk;

import org.scribble.command.simulate.Event;
import org.scribble.command.simulate.EventProcessor;
import org.scribble.common.logging.Journal;
import org.scribble.common.resource.DefaultResourceLocator;
import org.scribble.protocol.DefaultProtocolContext;
import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.model.*;
import org.scribble.protocol.monitor.ProtocolMonitor;
import org.scribble.protocol.monitor.ProtocolMonitorFactory;
import org.scribble.protocol.parser.DefaultProtocolParserManager;
import org.scribble.protocol.parser.ProtocolParser;
import org.scribble.protocol.parser.ProtocolParserManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;

import static org.junit.Assert.*;

public class CTKUtil {
	
	public static ProtocolParser getParser() {
		org.scribble.protocol.parser.ProtocolParser parser=null;
		
		try {
			String clsName=System.getProperty("scribble.protocol.parser");
			
			if (clsName == null) {
				clsName = "org.scribble.protocol.parser.antlr.ANTLRProtocolParser";
			}
			
			Class<?> cls=Class.forName(clsName);
			
			parser = (org.scribble.protocol.parser.ProtocolParser)
								cls.newInstance();

		} catch(Exception e) {
			fail("Failed to get Protocol parser: "+e);
		}
		
		return(parser);
	}

	public static ProtocolModel getModel(String filename, TestJournal logger) {
		ProtocolModel ret=null;
		
		java.io.InputStream is=
				ClassLoader.getSystemResourceAsStream(filename);

		if (is == null) {
			fail("Failed to load protocol '"+filename+"'");
		}
		
		org.scribble.protocol.parser.ProtocolParser parser=getParser();
		
		try {
			ret = parser.parse(is, logger, null);
		} catch(Exception e) {
			fail("Failed to parse protocol: "+e);
		}

        assertNotNull(ret);
        assertTrue(logger.getErrorCount() == 0);

		return(ret);
	}

	/**
	 * This method returns an instance of a protocol monitor.
	 * 
	 * @return The protocol monitor
	 */
	public static ProtocolMonitor getMonitor() {
		return(ProtocolMonitorFactory.createProtocolMonitor());
	}

	/**
	 * This method returns the list of monitoring events from the supplied filename.
	 * 
	 * @param filename The filename containing the list of monitoring events
	 * @return The list of monitoring events, or null if failed to load
	 */
	public static java.util.List<Event> getMonitorEvents(String filename) {
		java.util.List<Event> ret=null;
		
		java.io.InputStream is=
				ClassLoader.getSystemResourceAsStream(filename);
		
		if (is == null) {
			fail("Failed to load protocol '"+filename+"'");
		}
		
		EventProcessor eventProcessor=new EventProcessor();
		
		try {
			eventProcessor.initialize(is);
			
			ret = eventProcessor.getEvents();
			
			is.close();
		} catch(Exception e) {
			fail("Failed to load monitoring events: "+e);
		}
		
		return(ret);
	}

	/**
	 * This method validates a model against the expected model.
	 * 
	 * @param model The model constructed by the parser
	 * @param expected The expected model
	 */
	public static void verify(ProtocolModel model, ProtocolModel expected) {
		java.util.List<ModelObject> mlist=sequence(model);
		java.util.List<ModelObject> elist=sequence(expected);
		
		assertNotNull(mlist);
		assertNotNull(elist);
		
		int len=mlist.size();
		
		if (len > elist.size()) {
			len = elist.size();
		}
		
		for (int i=0; i < len; i++) {
			if (mlist.get(i).getClass() != elist.get(i).getClass()) {
				fail("Element ("+i+") mismatch class model="+
						mlist.get(i).getClass()+" expected="+
						elist.get(i).getClass());
			} else {
				Comparator<ModelObject> comparator=
					ComparatorUtil.getComparator(mlist.get(i).getClass());
				
				if (comparator == null) {
					fail("No comparator found for type: "+mlist.get(i).getClass());
				} else if (comparator.compare(mlist.get(i), elist.get(i)) != 0) {
					fail("Element ("+i+") did not match: "+mlist.get(i)+
							" expected="+elist.get(i));
				}
			}
		}
		
		assertTrue(mlist.size() == elist.size());
	}
	
	/**
	 * This method converts the model tree into a flat list of model
	 * objects which can be compared.
	 * 
	 * @param model The model
	 * @return The list of model objects
	 */
	protected static java.util.List<ModelObject> sequence(ProtocolModel model) {
		final java.util.List<ModelObject> ret=new java.util.Vector<ModelObject>();
		
		model.visit(new DefaultVisitor() {

			@Override
			public boolean start(Block elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(Choice elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(When elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(Parallel elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(Protocol elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(Repeat elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(RecBlock elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(Optional elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(Try elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public boolean start(Catch elem) {
				ret.add(elem);
				for (Interaction interaction : elem.getInteractions()) {
					ret.add(interaction);
				}
				return true;
			}

			@Override
			public boolean start(Run elem) {
				ret.add(elem);
				return true;
			}

			@Override
			public void accept(TypeImportList elem) {
				ret.add(elem);
			}

			@Override
			public void accept(ProtocolImportList elem) {
				ret.add(elem);
			}

			@Override
			public void accept(Interaction elem) {
				ret.add(elem);
			}

			@Override
			public void accept(RoleList elem) {
				ret.add(elem);
			}

			@Override
			public void accept(Raise elem) {
				ret.add(elem);
			}

			@Override
			public void accept(Recursion elem) {
				ret.add(elem);
			}

			@Override
			public void accept(Include elem) {
				ret.add(elem);
			}

			@Override
			public void accept(TypeImport elem) {
				ret.add(elem);
			}

			@Override
			public void accept(ProtocolImport elem) {
				ret.add(elem);
			}

		});
		
		return(ret);
	}
	
	public static org.scribble.protocol.ProtocolContext getProtocolContext(String baseDir) {
		final org.scribble.protocol.parser.ProtocolParser parser=getParser();
		
		ProtocolParserManager ppm=new DefaultProtocolParserManager() {

			@Override
			public ProtocolModel parse(String sourceType, InputStream is,
					Journal journal, ProtocolContext context)
					throws IOException {
				return(parser.parse(is, journal, context));
			}
			
		};
		
		java.net.URL url=ClassLoader.getSystemResource(baseDir);
		
		if (url == null) {
			fail("Failed to find base directory '"+baseDir+"'");
		}
		
		DefaultResourceLocator locator=new DefaultResourceLocator(new java.io.File(url.getPath()));
		
		DefaultProtocolContext ret=new DefaultProtocolContext(ppm, locator);
		
		return(ret);
	}
	
	public static ProtocolModel project(ProtocolModel model, Role role,
									Journal logger, ProtocolContext context) {
		ProtocolModel ret=null;
		
		org.scribble.protocol.projection.ProtocolProjector projector=null;
		
		try {
			String clsName=System.getProperty("scribble.protocol.projector");
			
			if (clsName == null) {
				clsName = "org.scribble.protocol.projection.impl.ProtocolProjectorImpl";
			}
			
			Class<?> cls=Class.forName(clsName);
			
			projector = (org.scribble.protocol.projection.ProtocolProjector)
								cls.newInstance();

		} catch(Exception e) {
			fail("Failed to get Protocol projector: "+e);
		}

		ret = projector.project(model, role, logger, context);
		
		return(ret);
	}

    public static void checkProjectsSuccessfully(String globalModelFile, String localModelFile, ProtocolContext context) {
        String projectAsRole = getProjectAsRole(localModelFile);

        TestJournal logger=new TestJournal();

        ProtocolModel model=getModel(globalModelFile, logger);
        ProtocolModel expected=getModel(localModelFile, logger);

        Role role=new Role(projectAsRole);
        ProtocolModel projected=project(model, role, logger, context);

        verify(projected, expected);
    }

    private static String getProjectAsRole(String localModelFile) {
        String roleDotSpr = localModelFile.substring(localModelFile.indexOf('@') + 1);
        return roleDotSpr.substring(0, roleDotSpr.indexOf('.'));
    }
}
