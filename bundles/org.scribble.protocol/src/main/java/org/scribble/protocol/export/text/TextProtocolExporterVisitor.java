/*
 * Copyright 2009-10 www.scribble.org
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
package org.scribble.protocol.export.text;

import org.scribble.common.logging.Journal;
import org.scribble.common.model.Annotation;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Visitor;
import org.scribble.protocol.model.Parallel;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.OnMessage;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.Interrupt;
import org.scribble.protocol.model.Do;
import org.scribble.protocol.model.Introduces;
import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.ParameterDefinition;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.model.ProtocolImport;
import org.scribble.protocol.model.RecBlock;
import org.scribble.protocol.model.ProtocolImportList;
import org.scribble.protocol.model.Recursion;
import org.scribble.protocol.model.Repeat;
import org.scribble.protocol.model.DirectedChoice;
import org.scribble.protocol.model.End;
import org.scribble.protocol.model.Inline;
import org.scribble.protocol.model.Run;
import org.scribble.protocol.model.TypeImport;
import org.scribble.protocol.model.TypeImportList;
import org.scribble.protocol.model.Unordered;

/**
 * This class provides a protocol visitor used to export a text
 * based representation of a protocol.
 *
 */
public class TextProtocolExporterVisitor implements Visitor {
    
    private java.io.OutputStream _outputStream=null;
    private Journal _journal=null;
    private int _indent=0;
    private Exception _exception=null;
        
    /**
     * This is the visitor for the text based protocol export.
     * 
     * @param journal The journal
     * @param os The output stream
     */
    public TextProtocolExporterVisitor(Journal journal, java.io.OutputStream os) {
        _journal = journal;
        _outputStream = os;
    }
    
    /**
     * This method indicates the start of a
     * block.
     * 
     * @param elem The block
     * @return Whether to process the contents
     */
    public boolean start(Block elem) {
        
        // Only add the 'and' keyword if the parent is a Parallel construct
        // and the block being processed is not the first block
        if (elem.getParent() instanceof Parallel
                && ((Parallel) elem.getParent()).getPaths().indexOf(elem) > 0) {
            output(" and");
        } else if (elem.getParent() instanceof Choice
                && ((Choice) elem.getParent()).getPaths().indexOf(elem) > 0) {
            output(" or");
        }
        
        if (!(elem.getParent() instanceof OnMessage)) {
            output(" {\r\n");
        }
        
        _indent++;
        
        return (true);
    }
            
    /**
     * This method indicates the end of a
     * block.
     * 
     * @param elem The block
     */
    public void end(Block elem) {
        // If parent is not a Protocol, then perform end of block functonality
        if (!(elem.getParent() instanceof Protocol)) {
            endOfBlock(elem);
        }
    }
        
    protected void endOfBlock(Block elem) {
        _indent--;
        
        indent();
        
        if (!(elem.getParent() instanceof OnMessage)) {
            output("}");
        }
        
        // Place newline after close bracket, unless the parent element is a
        // multipath construct AND the block is not the final block
        if (isEndOfBlock(elem)) {
            output("\r\n");
        }
    }
    
    /**
     * This method determines if this is the last block.
     * 
     * @param elem The block
     * @return Whether the element is the last block
     */
    protected boolean isEndOfBlock(Block elem) {
        boolean ret=true;

        if (elem.getParent() instanceof Parallel) {
            ret = ((Parallel) elem.getParent()).getPaths().indexOf(elem)
                    == ((Parallel) elem.getParent()).getPaths().size()-1;
        } else if (elem.getParent() instanceof Choice) {
            ret = ((Choice) elem.getParent()).getPaths().indexOf(elem)
                    == ((Choice) elem.getParent()).getPaths().size()-1;
        } else if (elem.getParent() instanceof Interrupt) {
            Interrupt c=(Interrupt) elem.getParent();
            
            if (c.getParent() instanceof Do) {
                Do te=(Do) c.getParent();
                
                ret = te.getInterrupts().indexOf(c) == te.getInterrupts().size()-1;
            }
        } else if (elem.getParent() instanceof Do) {
            ret = ((Do) elem.getParent()).getInterrupts().size() == 0;
        }
                
        return (ret);
    }
    
    /**
     * This method visits an import component.
     * 
     * @param elem The import
     */
    public void accept(TypeImportList elem) {        
        for (Annotation annotation : elem.getAnnotations()) {
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        output("import ");
        
        boolean first=true;
        
        if (elem.getFormat() != null) {
            output(elem.getFormat()+" ");
        }
        
        for (TypeImport t : elem.getTypeImports()) {
            if (!first) {
                output(", ");
            }
            
            first = false;
            
            if (t.getDataType() != null && t.getDataType().getDetails() != null) {
                output("\""+t.getDataType().getDetails()+"\" as ");
            }
            
            output(t.getName());
        }
        
        if (elem.getLocation() != null) {
            output(" from \""+elem.getLocation()+"\"");
        }
        
        output(";\r\n");
    }
    
    
    /**
     * This method visits an import component.
     * 
     * @param elem The protocol import list
     */
    public void accept(ProtocolImportList elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        output("import protocol ");
        
        boolean first=true;
        for (ProtocolImport t : elem.getProtocolImports()) {
            if (!first) {
                output(", ");
            }
            
            first = false;
            output(t.getName());
            
            output(" from \""+t.getLocation()+"\"");
        }
        
        output(";\r\n");
    }
    
    /**
     * This method visits the role list.
     * 
     * @param elem The role list
     */
    public void accept(Introduces elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        output(elem.getIntroducer().getName());
        
        output(" introduces ");
        
        for (int i=0; i < elem.getIntroducedRoles().size(); i++) {
            if (i > 0) {
                output(", ");
            }
            output(elem.getIntroducedRoles().get(i).getName());
        }
        
        output(";\r\n");
    }
    
    /**
     * This method visits an interaction component.
     * 
     * @param elem The interaction
     */
    public void accept(Interaction elem) {
        
        if (!(elem.getParent() instanceof Interrupt)) {
            for (Annotation annotation : elem.getAnnotations()) {
                indent();
                output("[["+annotation.toString()+"]]\r\n");
            }

            indent();
            
            outputInteraction(elem);
            
            output(";\r\n");
        }
    }
    
    /**
     * This method outputs the information about the supplied
     * interaction.
     * 
     * @param elem The interaction
     */
    protected void outputInteraction(Interaction elem) {

        outputMessageSignature(elem.getMessageSignature());
        
        if (elem.getFromRole() != null) {
            output(" from "+elem.getFromRole().getName());
        }
        
        if (elem.getToRoles().size() > 0) {
            output(" to ");
            
            for (int i=0; i < elem.getToRoles().size(); i++) {
                if (i > 0) {
                    output(",");
                }
                output(elem.getToRoles().get(i).getName());
            }
        }

    }
    
    /**
     * This method visits a recursion component.
     * 
     * @param elem The recursion
     */
    public void accept(Recursion elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }

        indent();

        if (elem.getLabel() != null) {
            output(elem.getLabel());
        }
        
        output(";\r\n");
    }
    
    /**
     * This method indicates the start of a
     * protocol.
     * 
     * @param elem The protocol
     * @return Whether to process the contents
     */
    public boolean start(Protocol elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }

        indent();
        
        output("protocol "+elem.getName());
        
        if (elem.getLocatedRole() != null) {
            output(" at "+elem.getLocatedRole().getName());
        }
        
        if (elem.getParameterDefinitions().size() > 0) {
            output("(");
            
            for (int i=0; i < elem.getParameterDefinitions().size(); i++) {
                ParameterDefinition pd=elem.getParameterDefinitions().get(i);
                
                if (i > 0) {
                    output(", ");
                }
                
                if (pd.getRole() != null) {
                    output("role "+pd.getRole().getName());
                } else {
                    output(pd.getType().getName()+" "+pd.getName());
                }
            }
            
            output(")");
        }
        
        return (true);
    }
    
    /**
     * This method indicates the start of a
     * choice.
     * 
     * @param elem The choice
     * @return Whether to process the contents
     */
    public boolean start(Choice elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        output("choice");
        
        if (elem.getRole() != null) {
            output(" at "+elem.getRole().getName());
        }
        
        //output(" {\r\n");
        
        //m_indent++;
        
        return (true);
    }
    
    /**
     * This method indicates the start of a
     * directed choice.
     * 
     * @param elem The directed choice
     * @return Whether to process the contents
     */
    public boolean start(DirectedChoice elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        if (elem.getFromRole() != null) {
            output("from "+elem.getFromRole().getName()+" ");
        }
        
        if (elem.getToRoles().size() > 0) {
            output("to ");
            
            for (int i=0; i < elem.getToRoles().size(); i++) {
                if (i > 0) {
                    output(", ");
                }
                
                output(elem.getToRoles().get(i).getName());
            }
        }
        
        output(" {\r\n");
        
        _indent++;
        
        return (true);
    }
    
    /**
     * This method indicates the start of a
     * directed choice.
     * 
     * @param elem The on-message
     * @return Whether to process the contents
     */
    public boolean start(OnMessage elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        outputMessageSignature(elem.getMessageSignature());

        output(":\r\n");
        
        //m_indent++;
        
        return (true);
    }
    
    /**
     * This method indicates the end of a
     * choice.
     * 
     * @param elem The choice
     */
    public void end(Choice elem) {
        
        //m_indent--;
        
        //indent();
        
        //output("}\r\n");
    }
    
    /**
     * This method indicates the end of a
     * choice.
     * 
     * @param elem The choice
     */
    public void end(DirectedChoice elem) {
        
        _indent--;
        
        indent();
        
        output("}\r\n");
    }
    
    /**
     * This method indicates the end of a
     * on-message.
     * 
     * @param elem The on-message
     */
    public void end(OnMessage elem) {
    }
    
    /**
     * This method processes an interrupt clause.
     * 
     * @param elem The interrupt
     * @return Whether to process the block contents
     */
    public boolean start(Interrupt elem) {
        
        indent();
        
        output(" interrupt");
        
        return (true);
    }
    
    /**
     * This method outputs information about the supplied
     * message signature.
     * 
     * @param ms The message signature
     */
    private void outputMessageSignature(MessageSignature ms) {
        if (ms != null) {
            if (ms.getOperation() != null) {
                output(ms.getOperation()+"(");
                
                for (int i=0; i < ms.getTypeReferences().size(); i++) {
                    if (i > 0) {
                        output(", ");
                    }    
                    output(ms.getTypeReferences().get(i).getName());
                }
                
                output(")");
            } else if (ms.getTypeReferences().size() > 0) {
                output(ms.getTypeReferences().get(0).getName());                    
            }
        }            
    }
    
    /**
     * This method indicates the start of a
     * run.
     * 
     * @param elem The run
     */
    public void accept(Run elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }

        indent();
        
        output("run "+elem.getProtocolReference().getName());
        
        if (elem.getProtocolReference().getRole() != null) {
            output(" at "+elem.getProtocolReference().getRole().getName());
        }
        
        if (elem.getParameters().size() > 0) {
            output("(");
            
            for (int i=0; i < elem.getParameters().size(); i++) {
                if (i > 0) {
                    output(", ");
                }
                
                output(elem.getParameters().get(i).getName());
            }
            
            output(")");
        }
        
        if (elem.getFromRole() != null) {
            output(" from "+elem.getFromRole().getName());
        }
        
        output(";\r\n");
    }
    
    /**
     * This method indicates the start of a
     * parallel.
     * 
     * @param elem The parallel
     * @return Whether to process the contents
     */
    public boolean start(Parallel elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        output("parallel");
        
        return (true);
    }
    
    /**
     * This method indicates the end of a
     * parallel.
     * 
     * @param elem The parallel
     */
    public void end(Parallel elem) {
    }
    
    /**
     * This method indicates the start of a
     * try/escape.
     * 
     * @param elem The try escape
     * @return Whether to process the contents
     */
    public boolean start(Do elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        output("do");
        
        return (true);
    }
    
    /**
     * This method indicates the end of a
     * try/escape.
     * 
     * @param elem The try escape
     */
    public void end(Do elem) {
    }
    
    /**
     * This method indicates the start of a
     * repeat.
     * 
     * @param elem The repeat
     * @return Whether to process the contents
     */
    public boolean start(Repeat elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        output("repeat");
        
        if (elem.getRoles().size() > 0) {
            output(" at ");
            
            for (int i=0; i < elem.getRoles().size(); i++) {
                if (i > 0) {
                    output(",");
                }
                
                output(elem.getRoles().get(i).getName());
            }
        }
        
        return (true);
    }
    
    /**
     * This method indicates the end of a
     * repeat.
     * 
     * @param elem The repeat
     */
    public void end(Repeat elem) {
    }
    
    /**
     * This method indicates the start of a
     * unordered block.
     * 
     * @param elem The unordered block
     * @return Whether to process the contents
     */
    public boolean start(Unordered elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        output("unordered");
        
        return (true);
    }
    
    /**
     * This method indicates the end of a
     * unordered block.
     * 
     * @param elem The unordered block
     */
    public void end(Unordered elem) {
    }

    /**
     * This method indicates the start of a
     * labelled block.
     * 
     * @param elem The labelled block
     * @return Whether to process the contents
     */
    public boolean start(RecBlock elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        if (elem.getLabel() != null) {
            output("rec "+elem.getLabel());
        }
        
        return (true);
    }
    
    /**
     * This method indicates the end of a
     * labelled block.
     * 
     * @param elem The labelled block
     */
    public void end(RecBlock elem) {
    }
    
    /**
     * This method indents the output.
     */
    protected void indent() {
        for (int i=0; i < _indent; i++) {
            output("\t");
        }
    }
    
    /**
     * This method outputs the supplied string.
     * 
     * @param str The string
     */
    protected void output(String str) {
        try {
            _outputStream.write(str.getBytes());
        } catch (Exception e) {
            _exception = e;
        }
    }
    
    /**
     * This method return the exception.
     * 
     * @return The exception, or null if not found
     */
    public Exception getException() {
        return (_exception);
    }
    
    /**
     * This method returns the journal.
     * 
     * @return The journal
     */
    public Journal getJournal() {
        return (_journal);
    }

    /**
     * This method indicates the end of the protocol.
     * 
     * @param elem The protocol
     */
    public void end(Protocol elem) {
        
        // Perform end of block - needed to be postponed to ensure
        // nested protocols are exported within the outer protocol
        endOfBlock(elem.getBlock());
    }

    /**
     * This method indicates the end of the interrupt.
     * 
     * @param elem The interrupt
     */
    public void end(Interrupt elem) {
    }
    
    /**
     * This method accepts an 'inline' construct.
     * 
     * @param elem The inline construct
     */
    public void accept(Inline elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }

        indent();
        
        output("inline "+elem.getProtocolReference().getName());
        
        if (elem.getProtocolReference().getRole() != null) {
            output(" at "+elem.getProtocolReference().getRole().getName());
        }
        
        if (elem.getParameters().size() > 0) {
            output("(");
            
            for (int i=0; i < elem.getParameters().size(); i++) {
                if (i > 0) {
                    output(", ");
                }
                
                output(elem.getParameters().get(i).getName());
            }
            
            output(")");
        }
        
        output(";\r\n");
    }

    /**
     * This method accepts a type import.
     * 
     * @param elem The type import
     */
    public void accept(TypeImport elem) {
    }

    /**
     * This method accepts a protocol import.
     * 
     * @param elem The protocol import
     */
    public void accept(ProtocolImport elem) {
    }
    
    /**
     * This method visits an end statement.
     * 
     * @param elem The end statement
     */
    public void accept(End elem) {
        for (Annotation annotation : elem.getAnnotations()) {
            indent();
            output("[["+annotation.toString()+"]]\r\n");
        }
        
        indent();
        
        output("end;\r\n");
    }
    
}
