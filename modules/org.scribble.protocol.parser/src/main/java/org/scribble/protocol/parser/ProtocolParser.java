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
package org.scribble.protocol.parser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.parser.ParserLogger;
import org.scribble.protocol.parser.ProtocolParser;
import org.scribble.protocol.parser.antlr.ProtocolTreeAdaptor;
import org.scribble.protocol.parser.antlr.ScribbleLexer;
import org.scribble.protocol.parser.antlr.ScribbleParser;
import org.scribble.protocol.validation.ComponentLoader;
import org.scribble.protocol.validation.DefaultValidationContext;
import org.scribble.protocol.validation.ProtocolValidator;
import org.scribble.protocol.validation.ValidationLogger;

/**
 * This class provides the ANTLR implementation of the Protocol Parser
 * interface.
 *
 */
public class ProtocolParser {
	
	private static final Logger LOG=Logger.getLogger(ProtocolParser.class.getName());

    /**
     * Default constructor.
     */
    public ProtocolParser() {
    }

    /**
     * This method parses the scribble protocol contained in the supplied
     * input stream. The resource locator is used to access other resources,
     * and the logger reports information, warnings and errors.
     * 
     * @param is The input stream
     * @param locator The resource locator
     * @param logger The logger
     * @return The module, or null if an error occurred
     * @throws IOException Failed to retrieve protocol from input stream
     */
    public Module parse(java.io.InputStream is, final ResourceLocator locator, final ParserLogger logger)
                            throws IOException {
        Module ret=null;
        
        try {
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
            
            parser.setParserLogger(logger);

            parser.module();
            
            if (!parser.isErrorOccurred()) {
                ret = adaptor.getModule();
            	
            	// Validate
                ProtocolValidator pv=new ProtocolValidator();
                
                DefaultValidationContext context=new DefaultValidationContext(ret, new ComponentLoader() {

					public Module loadModule(String module) {
						Module ret=null;
						
						java.io.InputStream is=locator.getModule(module);
						
						if (is != null) {
							try {
								ret = parse(is, locator, logger);
							} catch (Exception e) {
								LOG.log(Level.SEVERE, "Failed to parse imported module '"+module+"'", e);
							} finally {
								try {
									is.close();
								} catch (Exception e) {
									LOG.log(Level.SEVERE, "Failed to close input stream", e);
								}
							}
						}
						
						return (ret);
					}
                	
                });
                
                pv.validate(context, ret, new ValidationLogger() {

					public void error(String issue, ModelObject mobj) {
						logger.error(issue, mobj.getProperties());
					}

					public void warning(String issue, ModelObject mobj) {
						logger.warning(issue, mobj.getProperties());
					}

					public void info(String issue, ModelObject mobj) {
						logger.info(issue, mobj.getProperties());
					}
                	
                });
            }
            
        } catch (Exception e)  {
            e.printStackTrace();
        }
        
        return (ret);
    }

}
