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
package org.scribble2.parser;

import java.io.IOException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.scribble.parser.antlr.Scribble2Lexer;
import org.scribble.parser.antlr.Scribble2Parser;
import org.scribble.resources.Resource;

/**
 * This class provides the ANTLR implementation of the Protocol Parser
 * interface.
 *
 */
public class ModuleParser {
	
    /**
     * Default constructor.
     */
    public ModuleParser() {
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
    //public Module parse(Resource resource, final ModuleLoader loader, final IssueLogger logger)
    public CommonTree parse(Resource resource) throws IOException {
        //Module ret=null;
        CommonTree ret=null;
        
        try {
        	java.io.InputStream is=resource.getInputStream();
        	
            byte[] b=new byte[is.available()];
            is.read(b);
            
            is.close();
            
            String document=new String(b);
            
            Scribble2Lexer lex = new Scribble2Lexer(new ANTLRStringStream(document));
            CommonTokenStream tokens = new CommonTokenStream(lex);
               
            Scribble2Parser parser = new Scribble2Parser(tokens);

            /*ProtocolTreeAdaptor adaptor=new ProtocolTreeAdaptor();
            adaptor.setParser(parser);*/
            ScribbleASTAdaptor adaptor = new ScribbleASTAdaptor();
            adaptor.setParser(parser);
            
            parser.setDocument(document);
            parser.setTreeAdaptor(adaptor);
            
            //parser.setLogger(logger);

            CommonTree mod = (CommonTree) parser.module().getTree();
            
            if (!parser.isErrorOccurred()) {
                //ret = adaptor.getModule();
            	ret = mod;
            }
            
        } catch (Exception e)  {
            e.printStackTrace();
        }
        
        return (ret);
    }

}
