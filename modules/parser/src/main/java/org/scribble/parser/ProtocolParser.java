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
package org.scribble.parser;

import java.io.IOException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.scribble.context.ModuleCache;
import org.scribble.context.ModuleLoader;
import org.scribble.logging.IssueLogger;
import org.scribble.model.Module;
import org.scribble.parser.ProtocolParser;
import org.scribble.parser.antlr.ProtocolTreeAdaptor;
import org.scribble.parser.antlr.ScribbleLexer;
import org.scribble.parser.antlr.ScribbleParser;
import org.scribble.resources.Resource;

/**
 * This class provides the ANTLR implementation of the Protocol Parser
 * interface.
 *
 */
public class ProtocolParser {
	
    /**
     * Default constructor.
     */
    public ProtocolParser() {
    }

    /**
     * This method parses the scribble protocol contained in the supplied
     * resource. The resource locator is used to access other resources,
     * and the logger reports information, warnings and errors.
     * 
     * @param resource The resource
     * @param loader The module locator
     * @param logger The logger
     * @return The module, or null if an error occurred
     * @throws IOException Failed to retrieve protocol from input stream
     */
    public Module parse(Resource resource, final ModuleLoader loader, final IssueLogger logger)
                            throws IOException {
    	return (parse(resource, loader, new ModuleCache(), logger));
    }
    
    /**
     * This method parses the scribble protocol contained in the supplied
     * resource. The resource locator is used to access other resources,
     * and the logger reports information, warnings and errors.
     * 
     * @param resource The resource
     * @param loader The module locator
     * @param cache The cache of parsed modules
     * @param logger The logger
     * @return The module, or null if an error occurred
     * @throws IOException Failed to retrieve protocol from input stream
     */
    protected Module parse(Resource resource, ModuleLoader loader, ModuleCache cache, IssueLogger logger)
                            throws IOException {
        Module ret=null;
        
        try {
        	java.io.InputStream is=resource.getInputStream();
        	
            byte[] b=new byte[is.available()];
            is.read(b);
            
            is.close();
            
            String document=new String(b);
            
            ScribbleLexer lex = new ScribbleLexer(new ANTLRStringStream(document));
            CommonTokenStream tokens = new CommonTokenStream(lex);
               
            ScribbleParser parser = new ScribbleParser(tokens);

            ProtocolTreeAdaptor adaptor=new ProtocolTreeAdaptor();
            adaptor.setParser(parser);
            
            parser.setDocument(document);
            parser.setTreeAdaptor(adaptor);
            
            parser.setLogger(logger);

            parser.module();
            
            if (!parser.isErrorOccurred()) {
                ret = adaptor.getModule();
                
                // Add the module to the cache, in case it directly or
                // indirectly references itself, therefore avoiding reparsing
                cache.register(ret);
            }
            
        } catch (Exception e)  {
            e.printStackTrace();
        }
        
        return (ret);
    }

}
