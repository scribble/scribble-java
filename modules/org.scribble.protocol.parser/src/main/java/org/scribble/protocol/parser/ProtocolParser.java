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

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.scribble.protocol.model.Module;
import org.scribble.protocol.parser.IssueLogger;
import org.scribble.protocol.parser.ProtocolParser;
import org.scribble.protocol.parser.antlr.ProtocolTreeAdaptor;
import org.scribble.protocol.parser.antlr.ScribbleProtocolLexer;
import org.scribble.protocol.parser.antlr.ScribbleProtocolParser;

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
     * 
     */
    public Module parse(java.io.InputStream is, ResourceLocator locator, IssueLogger logger)
                            throws IOException {
        Module ret=null;
        
        try {
            byte[] b=new byte[is.available()];
            is.read(b);
            
            is.close();
            
            String document=new String(b);
            
            ScribbleProtocolLexer lex = new ScribbleProtocolLexer(new ANTLRStringStream(document));
            CommonTokenStream tokens = new CommonTokenStream(lex);
               
            ScribbleProtocolParser parser = new ScribbleProtocolParser(tokens);

            ProtocolTreeAdaptor adaptor=new ProtocolTreeAdaptor(logger);
            adaptor.setParser(parser);
            
            parser.setDocument(document);
            parser.setTreeAdaptor(adaptor);
            
            parser.setIssueLogger(logger);

            parser.module();
            
            if (!parser.isErrorOccurred()) {
                ret = adaptor.getModule();
            }
            
        } catch (Exception e)  {
            e.printStackTrace();
        }
        
        return (ret);
    }

}
