/*
 * Copyright 2009 www.scribble.org
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
package org.scribble.trace.parser;

import java.io.IOException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.scribble.logging.IssueLogger;
import org.scribble.trace.model.Trace;
import org.scribble.trace.parser.antlr.TraceTreeAdaptor;
import org.scribble.trace.parser.antlr.ScribbleTraceLexer;
import org.scribble.trace.parser.antlr.ScribbleTraceParser;
import org.scribble.resources.Resource;

/**
 * This class provides the ANTLR implementation of the Protocol Parser
 * interface.
 *
 */
public class TraceParser {
	
    /**
     * Default constructor.
     */
    public TraceParser() {
    }

    /**
     * This method parses the scribble protocol contained in the supplied
     * resource. The resource locator is used to access other resources,
     * and the logger reports information, warnings and errors.
     * 
     * @param resource The resource
     * @param logger The logger
     * @return The module, or null if an error occurred
     * @throws IOException Failed to retrieve protocol from input stream
     */
    public Trace parse(Resource resource, final IssueLogger logger)
                            throws IOException {
    	Trace ret=null;
        
        try {
        	java.io.InputStream is=resource.getInputStream();
        	
            byte[] b=new byte[is.available()];
            is.read(b);
            
            is.close();
            
            String document=new String(b);
            
            ScribbleTraceLexer lex = new ScribbleTraceLexer(new ANTLRStringStream(document));
            CommonTokenStream tokens = new CommonTokenStream(lex);
               
            ScribbleTraceParser parser = new ScribbleTraceParser(tokens);

            TraceTreeAdaptor adaptor=new TraceTreeAdaptor();
            adaptor.setParser(parser);
            
            parser.setDocument(document);
            parser.setTreeAdaptor(adaptor);
            
            parser.setLogger(logger);

            parser.trace();
            
            if (!parser.isErrorOccurred()) {
                ret = adaptor.getTrace();
            }
            
        } catch (Exception e)  {
            e.printStackTrace();
        }
        
        return (ret);
    }

}
