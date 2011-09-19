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
package org.scribble.protocol.parser.antlr;

import java.io.IOException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.scribble.common.logging.Journal;
import org.scribble.common.resource.Content;
import org.scribble.protocol.ProtocolTools;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.parser.AnnotationProcessor;
import org.scribble.protocol.parser.ProtocolParser;

/**
 * This class provides the ANTLR implementation of the Protocol Parser
 * interface.
 *
 */
public class ANTLRProtocolParser implements ProtocolParser {

    private AnnotationProcessor _annotationProcessor=null;

    /**
     * Default constructor.
     */
    public ANTLRProtocolParser() {
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isSupported(Content content) {
        return (content.hasExtension(org.scribble.protocol.ProtocolDefinitions.PROTOCOL_TYPE));
    }

    /**
     * {@inheritDoc}
     */
    public ProtocolModel parse(ProtocolTools context, Content content, Journal journal)
                            throws IOException {
        ProtocolModel ret=null;
        
        try {
            java.io.InputStream is=content.getInputStream();
            
            byte[] b=new byte[is.available()];
            is.read(b);
            
            is.close();
            
            String document=new String(b);
            
            ScribbleProtocolLexer lex = new ScribbleProtocolLexer(new ANTLRStringStream(document));
               CommonTokenStream tokens = new CommonTokenStream(lex);
               
            ScribbleProtocolParser parser = new ScribbleProtocolParser(tokens);

            ProtocolTreeAdaptor adaptor=new ProtocolTreeAdaptor(_annotationProcessor, journal);
            adaptor.setParser(parser);
            
            parser.setDocument(document);
            parser.setTreeAdaptor(adaptor);
            
            parser.setJournal(journal);

            parser.description();
            
            if (!parser.isErrorOccurred()) {
                ret = adaptor.getProtocolModel();
            }
            
        } catch (Exception e)  {
            e.printStackTrace();
        }
        
        return (ret);
    }

    /**
     * This method sets an annotation processor.
     * 
     * @param ap The annotation processor
     */
    public void setAnnotationProcessor(AnnotationProcessor ap) {
        _annotationProcessor = ap;
    }
}
