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
package org.scribble.protocol.parser.antlr;

import java.beans.PropertyDescriptor;
import java.util.logging.Logger;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.scribble.common.logging.Journal;
import org.scribble.common.model.Annotation;
import org.scribble.common.model.DefaultAnnotation;
import org.scribble.protocol.model.Activity;
import org.scribble.protocol.model.Block;
import org.scribble.protocol.model.Choice;
import org.scribble.protocol.model.DataType;
import org.scribble.protocol.model.DirectedChoice;
import org.scribble.protocol.model.Do;
import org.scribble.protocol.model.End;
import org.scribble.protocol.model.ImportList;
import org.scribble.protocol.model.Inline;
import org.scribble.protocol.model.Interaction;
import org.scribble.protocol.model.Interrupt;
import org.scribble.protocol.model.Introduces;
import org.scribble.protocol.model.MessageSignature;
import org.scribble.protocol.model.ModelObject;
import org.scribble.protocol.model.OnMessage;
import org.scribble.protocol.model.Parallel;
import org.scribble.protocol.model.Parameter;
import org.scribble.protocol.model.ParameterDefinition;
import org.scribble.protocol.model.Protocol;
import org.scribble.protocol.model.ProtocolImport;
import org.scribble.protocol.model.ProtocolImportList;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.ProtocolReference;
import org.scribble.protocol.model.RecBlock;
import org.scribble.protocol.model.Recursion;
import org.scribble.protocol.model.Repeat;
import org.scribble.protocol.model.Role;
import org.scribble.protocol.model.Run;
import org.scribble.protocol.model.TypeImport;
import org.scribble.protocol.model.TypeImportList;
import org.scribble.protocol.model.TypeReference;
import org.scribble.protocol.model.Unordered;
import org.scribble.protocol.parser.AnnotationProcessor;

/**
 * This class provides an implementation of the tree adapter.
 *
 */
public class ProtocolTreeAdaptor implements org.antlr.runtime.tree.TreeAdaptor {

    private static final String ANNOTATIONS = "_annotations";
    private static final String ACTIVITY_RULE_NAME = "activityDef";
    private static final java.util.Map<String,String> PROPERTY_TOKENS=
        new java.util.HashMap<String, String>();
    private static final java.util.Map<String,Class<?>> TOKEN_CLASS=
        new java.util.HashMap<String, Class<?>>();
    private static final java.util.Map<String,Class<?>> LIST_CLASS=
        new java.util.HashMap<String, Class<?>>();
    private static final java.util.Map<String,Class<?>> PARSER_GROUPING_RULE_CLASS=
        new java.util.HashMap<String, Class<?>>();
    private static final java.util.List<String> CLEAR_TOKEN_LIST_RULES=
        new java.util.Vector<String>();
    private static final java.util.List<String> TOKENS_TO_IGNORE=
        new java.util.Vector<String>();
    private static final java.util.List<String> STRING_LITERALS=
        new java.util.Vector<String>();
    
    private ScribbleProtocolParser _parser=null;
    private AnnotationProcessor _annotationProcessor=null;
    private Journal _journal=null;
    private Token _currentToken=null;
    
    private ProtocolModel _model=null;
    
    private static final Logger LOG=Logger.getLogger(ProtocolTreeAdaptor.class.getName());
    
    static {
        // The map of root tokens, that begin a grammer
        // rule, and the model class they are associated
        // with
        //m_tokenClass.put("import", ImportList.class);
        TOKEN_CLASS.put("protocol", Protocol.class);
        //m_tokenClass.put("role", RoleList.class);
        TOKEN_CLASS.put("choice", Choice.class);
        TOKEN_CLASS.put("rec", RecBlock.class);
        TOKEN_CLASS.put("parallel", Parallel.class);
        TOKEN_CLASS.put("repeat", Repeat.class);
        TOKEN_CLASS.put("do", Do.class);
        TOKEN_CLASS.put("interrupt", Interrupt.class);
        TOKEN_CLASS.put("run", Run.class);
        TOKEN_CLASS.put("inline", Inline.class);
        TOKEN_CLASS.put("unordered", Unordered.class);
        TOKEN_CLASS.put("end", End.class);

        // Clear token list - determines whether prior to processing
        // a list of tokens, the 'current token' should be cleared
        CLEAR_TOKEN_LIST_RULES.add("parameter");
        CLEAR_TOKEN_LIST_RULES.add("dataTypeDef");
        CLEAR_TOKEN_LIST_RULES.add("importTypeStatement");
        CLEAR_TOKEN_LIST_RULES.add("importProtocolDef");
        CLEAR_TOKEN_LIST_RULES.add("introducesDef");
        CLEAR_TOKEN_LIST_RULES.add("directedChoiceDef");
        
        // The list of tokens that should be ignored when processing
        // the children of a parent node
        TOKENS_TO_IGNORE.add("import");

        // This may define the model object that should be
        // created after processing the named grammer rule
        PARSER_GROUPING_RULE_CLASS.put("importProtocolStatement", ProtocolImportList.class);
        PARSER_GROUPING_RULE_CLASS.put("importTypeStatement", TypeImportList.class);
        PARSER_GROUPING_RULE_CLASS.put("simpleName", String.class);
        PARSER_GROUPING_RULE_CLASS.put("simpleName", String.class);
        PARSER_GROUPING_RULE_CLASS.put("blockDef", Block.class);
        PARSER_GROUPING_RULE_CLASS.put("protocolBlockDef", Block.class);
        PARSER_GROUPING_RULE_CLASS.put("activityList", Block.class);
        PARSER_GROUPING_RULE_CLASS.put("interactionDef", Interaction.class);
        PARSER_GROUPING_RULE_CLASS.put("interactionSignatureDef", MessageSignature.class);
        PARSER_GROUPING_RULE_CLASS.put("typeReferenceDef", TypeReference.class);
        PARSER_GROUPING_RULE_CLASS.put("protocolName", String.class);
        PARSER_GROUPING_RULE_CLASS.put("roleName", Role.class);
        PARSER_GROUPING_RULE_CLASS.put("roleDef", Role.class);
        PARSER_GROUPING_RULE_CLASS.put("importProtocolDef", ProtocolImport.class);
        PARSER_GROUPING_RULE_CLASS.put("importTypeDef", TypeImport.class);
        PARSER_GROUPING_RULE_CLASS.put("protocolRefDef", ProtocolReference.class);
        PARSER_GROUPING_RULE_CLASS.put("parameter", Parameter.class);
        PARSER_GROUPING_RULE_CLASS.put("inlineProtocolDef", Protocol.class);
        PARSER_GROUPING_RULE_CLASS.put("declarationName", String.class);
        PARSER_GROUPING_RULE_CLASS.put("labelName", String.class);
        PARSER_GROUPING_RULE_CLASS.put("dataTypeDef", DataType.class);
        PARSER_GROUPING_RULE_CLASS.put("recursionDef", Recursion.class);
        PARSER_GROUPING_RULE_CLASS.put("parameterDef", ParameterDefinition.class);
        PARSER_GROUPING_RULE_CLASS.put("introducesDef", Introduces.class);
        PARSER_GROUPING_RULE_CLASS.put("directedChoiceDef", DirectedChoice.class);
        PARSER_GROUPING_RULE_CLASS.put("onMessageDef", OnMessage.class);
                
        // When a particular class has multiple properties of the
        // same type, then a preceding token must be used to
        // determine which property to set. This map provides the
        // mapping between the property name and the token.
        PROPERTY_TOKENS.put("interactionDef:fromRole", "from");
        PROPERTY_TOKENS.put("directedChoiceDef:fromRole", "from");
        PROPERTY_TOKENS.put("choiceDef:role", "at");
        PROPERTY_TOKENS.put("interactionDef:toRoles", "to");
        PROPERTY_TOKENS.put("directedChoiceDef:toRoles", "to");
        PROPERTY_TOKENS.put("parameter:boundName", "");
        PROPERTY_TOKENS.put("parameter:localName", ":=");
        PROPERTY_TOKENS.put("dataTypeDef:details", "<string literal>"); // Needed to make sure property not used by default
        PROPERTY_TOKENS.put("importTypeStatement:format", "");
        PROPERTY_TOKENS.put("importTypeStatement:location", "from");
        PROPERTY_TOKENS.put("importProtocolDef:name", "");
        PROPERTY_TOKENS.put("importProtocolDef:location", "from");
        PROPERTY_TOKENS.put("introducesDef:introducer", "");
        PROPERTY_TOKENS.put("introducesDef:introducedRoles", "introduces");
        
        // Defines the list element base type associated with a
        // property name
        LIST_CLASS.put("imports", ImportList.class);
        LIST_CLASS.put("contents", Activity.class);
        LIST_CLASS.put("roles", Role.class);
        LIST_CLASS.put("introducedRoles", Role.class);
        LIST_CLASS.put("toRoles", Role.class);
        LIST_CLASS.put("typeImports", TypeImport.class);
        LIST_CLASS.put("protocolImports", ProtocolImport.class);
        LIST_CLASS.put("typeReferences", TypeReference.class);
        LIST_CLASS.put("blocks", Block.class);
        LIST_CLASS.put("paths", Block.class);
        LIST_CLASS.put("nestedProtocols", Protocol.class);
        LIST_CLASS.put("onMessages", OnMessage.class);
        LIST_CLASS.put("interrupts", Interrupt.class);
        LIST_CLASS.put("parameters", Parameter.class);
        LIST_CLASS.put("interactions", Interaction.class);
        LIST_CLASS.put("parameterDefinitions", ParameterDefinition.class);
        
        STRING_LITERALS.add("dataTypeDef:details");
        STRING_LITERALS.add("importTypeStatement:location");
        STRING_LITERALS.add("importProtocolDef:location");
    }
    
    
    /**
     * This is the constructor for the protocol tree adapter.
     * 
     * @param ap The annotation processor
     * @param journal The journal
     */
    public ProtocolTreeAdaptor(AnnotationProcessor ap, Journal journal) {
        _annotationProcessor = ap;
        _journal = journal;
    }
    
    /**
     * This method returns the protocol model.
     * 
     * @return The protocol model
     */
    public ProtocolModel getProtocolModel() {
        return (_model);
    }
    
    /**
     * This method sets the parser.
     * 
     * @param parser The parser
     */
    public void setParser(ScribbleProtocolParser parser) {
        _parser = parser;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object create(Token token) {
        Object ret=token;
        
        Class<?> cls=TOKEN_CLASS.get(token.getText());
        
        LOG.fine("Token class for '"+token.getText()
                +"' is: "+cls);

        if (cls != null) {
            try {
                ret = cls.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if (ret == token && token.getType() != ScribbleProtocolParser.ID) {
            LOG.fine("Set current token="+token);
            _currentToken = token;
        }
        
        // Check if positional information should be set
        adjustLocationInfo(ret, token);
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public Object dupNode(Object arg0) {
        LOG.finest("DUPNODE "+arg0);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public int getChildIndex(Object arg0) {
        LOG.finest("GET CHILD INDEX "+arg0);
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object getParent(Object arg0) {
        LOG.finest("GET PARENT "+arg0);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Token getToken(Object arg0) {
        LOG.finest("GET TOKEN "+arg0);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public int getTokenStartIndex(Object arg0) {
        LOG.finest("GET TOKEN START INDEX "+arg0);
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    public int getTokenStopIndex(Object arg0) {
        LOG.finest("GET TOKEN STOP INDEX "+arg0);
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    public void setChildIndex(Object arg0, int arg1) {
        LOG.finest("SET CHILD INDEX "+arg0+" "+arg1);        
    }

    /**
     * {@inheritDoc}
     */
    public void replaceChildren(Object arg0, int arg1, int arg2, Object arg3) {
        LOG.finest("REPLACE CHILD "+arg0+" "+arg1+" "+arg2+" "+arg3);
    }
    
    /**
     * {@inheritDoc}
     */
    public void setParent(Object arg0, Object arg1) {
        LOG.finest("SET PARENT "+arg0+" "+arg1);
    }

    /**
     * {@inheritDoc}
     */
    public void setTokenBoundaries(Object arg0, Token arg1, Token arg2) {
        LOG.finest("SET TOKEN BOUNDARIES "+arg0+" "+arg1+" "+arg2);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void addChild(Object parent, Object child) {
        boolean added=false;
        
        LOG.fine("Add child: parent="+parent+" child="+child);
        
        // Associate annotations with the protocol model
        if (parent instanceof ProtocolModel
                && child instanceof Token && ((Token)child).getType()
                        == ScribbleProtocolParser.ANNOTATION) {
            
            // Add the annotation to a temporary location associated
            // with the model, until the real target model object is
            // identified
            java.util.List<String> annotations=(java.util.List<String>)
                ((ProtocolModel)parent).getProperties().get(ANNOTATIONS);
                        
            if (annotations == null) {
                annotations = new java.util.Vector<String>();
                ((ProtocolModel)parent).getProperties().put(ANNOTATIONS, annotations);
            }
            
            String annotation=((Token)child).getText();
            annotation = annotation.substring(2, annotation.length()-2);
            
            annotations.add(annotation);
            
            return;
        }
        
        // Check if child is a list
        if (isNil(child)) {
            java.util.List<Object> nil=
                (java.util.List<Object>)child;
            String ruleName=null;
            
            if (_parser != null
                    && _parser.getRuleInvocationStack().size() > 0) {
                ruleName = (String)_parser.getRuleInvocationStack().get(
                        _parser.getRuleInvocationStack().size()-1);
                                
                // Before processing the sublist of tokens, clear
                // the current token - needed for cases like the
                // 'parameter' syntax rule
                if (nil.size() > 0 && ruleName != null && CLEAR_TOKEN_LIST_RULES.contains(ruleName)) {
                    LOG.fine("Clear current token before processing sublist of tokens: rule="+ruleName);
                    _currentToken = null;
                }
            }
            
            // Check if ID token
            StringBuffer buf=new StringBuffer();
            java.util.List<Token> annotations=new java.util.Vector<Token>();
            
            for (int i=0; i < nil.size(); i++) {
                
                if (nil.get(i) instanceof Token
                        && (((Token)nil.get(i)).getType() == ScribbleProtocolParser.ID
                            || ((Token)nil.get(i)).getType() == ScribbleProtocolParser.FULLSTOP)) {
                    buf.append(((Token)nil.get(i)).getText());
                } else {
                    if (buf.length() > 0) {
                        addChild(parent, buf.toString());
                        
                        buf = new StringBuffer();
                    }
                    
                    if (nil.get(i) instanceof Token
                            && !TOKENS_TO_IGNORE.contains(((Token)nil.get(i)).getText())) {
                        _currentToken = (Token)nil.get(i);
                        LOG.fine("Set current token: "+_currentToken);
                    }

                    if (nil.get(i) instanceof Token && ((Token)nil.get(i)).getType() == ScribbleProtocolParser.ANNOTATION) {
                        annotations.add((Token)nil.get(i));
                    } else {
                        addChild(parent, nil.get(i));
                        
                        if (nil.get(i) instanceof ModelObject) {
                            for (Token annotationToken : annotations) {
                                String text=annotationToken.getText();
                                text = text.substring(2, text.length()-2);
                                
                                Annotation annotation=null;
                                if (_annotationProcessor != null) {
                                    annotation = _annotationProcessor.getAnnotation(text, 
                                            ((ModelObject)nil.get(i)).getProperties(), _journal);
                                }
                                
                                if (annotation == null) {
                                    annotation = new DefaultAnnotation(text);
                                }
                                
                                ((ModelObject)nil.get(i)).getAnnotations().add(annotation);
                            }

                            annotations.clear();
                        }
                    }
                }        
                
                adjustLocationInfo(parent, nil.get(i));
            }
            
            if (buf.length() > 0) {                
                addChild(parent, buf.toString());
            }
        } else if (parent != null && child != null) {

            added = addChildNonList(parent, child);
        }
        
        if (added && parent instanceof ModelObject && child instanceof ModelObject) {
            
            // See if child's textual location can be used to increase the scope of the
            // parent model object
            int parEndLine=-1;
            int parEndCol=-1;
            int chEndLine=-1;
            int chEndCol=-1;
            
            ModelObject parmobj=(ModelObject)parent;
            ModelObject chmobj=(ModelObject)child;
            
            if (parmobj.getProperties().containsKey(Journal.END_LINE)) {
                parEndLine = (Integer)parmobj.getProperties().get(Journal.END_LINE);
            }
            
            if (parmobj.getProperties().containsKey(Journal.END_LINE)) {
                parEndCol = (Integer)parmobj.getProperties().get(Journal.END_COLUMN);
            }
            
            if (chmobj.getProperties().containsKey(Journal.END_LINE)) {
                chEndLine = (Integer)chmobj.getProperties().get(Journal.END_LINE);
            }
            
            if (chmobj.getProperties().containsKey(Journal.END_COLUMN)) {
                chEndCol = (Integer)chmobj.getProperties().get(Journal.END_COLUMN);
            }
            
            if (chEndLine > parEndLine || chEndCol > parEndCol) {
                parmobj.getProperties().put(Journal.END_LINE, chEndLine);
                parmobj.getProperties().put(Journal.END_COLUMN, chEndCol);
            }
        }
    }
    
    /**
     * Add child not associated with a list.
     * 
     * @param parent The parent
     * @param child The child
     * @return Whether child added
     */
    @SuppressWarnings("unchecked")
    protected boolean addChildNonList(Object parent, Object child) {
        boolean added=false;
        
        if (isNil(parent)) {
            java.util.List<Object> nil=
                (java.util.List<Object>)parent;

            LOG.finest("Add child: "+child);
            nil.add(child);
            
        } else {
            LOG.finest("Determine if can be set by property descriptor");
            
            // Check if annotations should be associated with the protocol model child
            if (parent instanceof ProtocolModel && child instanceof ModelObject) {
                java.util.List<String> annotations=(java.util.List<String>)
                            ((ProtocolModel)parent).getProperties().get(ANNOTATIONS);
                
                if (annotations != null) {

                    for (String text : annotations) {
                        Annotation annotation=null;
                        if (_annotationProcessor != null) {
                            annotation = _annotationProcessor.getAnnotation(text, 
                                    ((ModelObject)child).getProperties(), _journal);
                        }
                        
                        if (annotation == null) {
                            annotation = new DefaultAnnotation(text);
                        }
                        
                        ((ModelObject)child).getAnnotations().add(annotation);
                    }
                }
                
                // Clear existing properties
                ((ProtocolModel)parent).getProperties().remove(ANNOTATIONS);
            }
            
            // Check if child is a string literal
            boolean stringLiteral=false;

            if (child instanceof Token 
                    && ((Token)child).getType() == ScribbleProtocolParser.StringLiteral) {
                String strlit = ((Token)child).getText();
                            
                if (((Token)child).getType() == ScribbleProtocolParser.StringLiteral
                                && strlit.length() >= 2) {
                    // Strip the quotes
                    child = strlit.substring(1, strlit.length()-1);
                } else {
                    child = strlit;
                }
                
                stringLiteral = true;
            }
            
            String ruleName="";
            
            if (_parser != null
                       && _parser.getRuleInvocationStack().size() > 0) {
                ruleName = (String)_parser.getRuleInvocationStack().get(
                        _parser.getRuleInvocationStack().size()-1);
            }
                                
            try {
                // Get property descriptors for parent class
                java.beans.BeanInfo bi=
                    java.beans.Introspector.getBeanInfo(parent.getClass());
                
                PropertyDescriptor[] pds=bi.getPropertyDescriptors();
                PropertyDescriptor pd=null;
                
                for (int i=0; pd == null && i < pds.length; i++) {
                    
                    String token=PROPERTY_TOKENS.get(ruleName+":"+pds[i].getName());
                    
                    if (pds[i].getPropertyType().isAssignableFrom(child.getClass())
                            && !pds[i].getName().equals("parent")) {
                        
                        String ruleprop=ruleName+":"+pds[i].getName();
                        
                        if (stringLiteral) {
                            if (STRING_LITERALS.contains(ruleprop)
                                    && pds[i].getWriteMethod() != null) {
                                pd = pds[i];
                            }
                        } else if ((token == null
                                || (token.length() == 0 && _currentToken == null)
                                || (_currentToken != null
                                && token.equals(_currentToken.getText())))
                                && pds[i].getWriteMethod() != null) {
                            pd = pds[i];
                        }
                    } else if (pds[i].getPropertyType() == java.util.List.class) {
                        
                        Class<?> listElementCls=LIST_CLASS.get(pds[i].getName());
                        
                        if ((token == null
                                || (token.length() == 0 && _currentToken == null)
                                || (_currentToken != null
                                && token.equals(_currentToken.getText())))
                                && listElementCls != null
                                && listElementCls.isAssignableFrom(child.getClass())) {
                            java.util.List<Object> list=(java.util.List<Object>)
                                    pds[i].getReadMethod().invoke(parent);
                            
                            LOG.fine("Adding "+child+" to list: "
                                    +list+" on parent "+parent);
                            list.add(child);
                            
                            added = true;
                        }
                    }
                }
                
                // If property descriptor is not for 'parent' property,
                // as this would match any 'child', and the property
                // must have a set method
                if (pd != null && pd.getWriteMethod() != null) {
                    
                    LOG.fine("Set property '"+pd.getName()
                            +"' on="+parent+" (class="+parent.getClass()+") to="+child);
                    
                    pd.getWriteMethod().invoke(parent, child);
                    
                    added = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return (added);
    }

    /**
     * This method adjusts the location information.
     * 
     * @param parent Parent
     * @param child Child
     */
    protected void adjustLocationInfo(Object parent, Object child) {
        if (parent instanceof ModelObject) {
            ModelObject mobj=(ModelObject)parent;
            
            if (child instanceof Token) {
                Token token=(Token)child;
                
                if (!mobj.getProperties().containsKey(Journal.START_LINE)
                        || token.getLine() < ((Integer)mobj.getProperties().get(Journal.START_LINE))) {
                    mobj.getProperties().put(Journal.START_LINE, token.getLine());                    
                    mobj.getProperties().put(Journal.START_COLUMN, token.getCharPositionInLine());
                } else if (token.getLine() == ((Integer)mobj.getProperties().get(Journal.START_LINE))
                        && token.getCharPositionInLine() < ((Integer)mobj.getProperties().get(Journal.START_COLUMN))) {    
                    mobj.getProperties().put(Journal.START_COLUMN, token.getCharPositionInLine());
                }
                
                if (!mobj.getProperties().containsKey(Journal.END_LINE)
                        || token.getLine() > ((Integer)mobj.getProperties().get(Journal.END_LINE))) {
                    mobj.getProperties().put(Journal.END_LINE, token.getLine());                    
                    mobj.getProperties().put(Journal.END_COLUMN, token.getCharPositionInLine()
                            +token.getText().length());
                } else if (token.getLine() == ((Integer)mobj.getProperties().get(Journal.END_LINE))
                        && (token.getCharPositionInLine()+token.getText().length())
                        < ((Integer)mobj.getProperties().get(Journal.END_COLUMN))) {    
                    mobj.getProperties().put(Journal.END_COLUMN, token.getCharPositionInLine()
                            +token.getText().length());
                }
            } else if (child instanceof ModelObject) {
                ModelObject chobj=(ModelObject)child;
                
                if (chobj.getProperties().containsKey(Journal.START_LINE)
                        && chobj.getProperties().containsKey(Journal.START_COLUMN)) {
                    
                    if (!mobj.getProperties().containsKey(Journal.START_LINE)
                            || ((Integer)chobj.getProperties().get(Journal.START_LINE))
                            < ((Integer)mobj.getProperties().get(Journal.START_LINE))) {
                        mobj.getProperties().put(Journal.START_LINE, chobj.getProperties().get(Journal.START_LINE));                    
                        mobj.getProperties().put(Journal.START_COLUMN, chobj.getProperties().get(Journal.START_COLUMN));
                    } else if (((Integer)chobj.getProperties().get(Journal.START_LINE))
                            == ((Integer)mobj.getProperties().get(Journal.START_LINE))
                            && ((Integer)chobj.getProperties().get(Journal.START_COLUMN))
                            < ((Integer)mobj.getProperties().get(Journal.START_COLUMN))) {    
                        mobj.getProperties().put(Journal.START_COLUMN, chobj.getProperties().get(Journal.START_COLUMN));
                    }
                }
            
                if (chobj.getProperties().containsKey(Journal.END_LINE)
                        && chobj.getProperties().containsKey(Journal.END_COLUMN)) {
                    
                    if (!mobj.getProperties().containsKey(Journal.END_LINE)
                            || ((Integer)chobj.getProperties().get(Journal.END_LINE))
                            < ((Integer)mobj.getProperties().get(Journal.END_LINE))) {
                        mobj.getProperties().put(Journal.END_LINE, chobj.getProperties().get(Journal.END_LINE));                    
                        mobj.getProperties().put(Journal.END_COLUMN, chobj.getProperties().get(Journal.END_COLUMN));
                    } else if (((Integer)chobj.getProperties().get(Journal.END_LINE))
                                == ((Integer)mobj.getProperties().get(Journal.END_LINE))
                                && ((Integer)chobj.getProperties().get(Journal.END_COLUMN))
                                < ((Integer)mobj.getProperties().get(Journal.END_COLUMN))) {    
                        mobj.getProperties().put(Journal.END_COLUMN, chobj.getProperties().get(Journal.END_COLUMN));
                    }
                }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public Object becomeRoot(Object newRoot, Object oldRoot) {
        LOG.finest("Become root "+newRoot+" "+oldRoot);
        
        if (oldRoot != null) {
            addChild(newRoot, oldRoot);
            
            if (isNil(newRoot)) {
                java.util.List<?> nil=
                    (java.util.Vector<?>)newRoot;
                
                if (nil.size() == 1) {
                    newRoot = nil.get(0);
                }
            }
        }
        
        return (newRoot);
    }
    
    /**
     * {@inheritDoc}
     */
    public Object becomeRoot(Token arg0, Object arg1) {
        LOG.finest("BECOME_ROOT2 "+arg0+" "+arg1);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object create(int arg0, Token arg1) {
        LOG.finest("CREATE "+arg0+" TOKEN "+arg1);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object create(int arg0, String arg1) {
        LOG.finest("CREATE2 "+arg0+" STR "+arg1);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object create(int arg0, Token arg1, String arg2) {
        LOG.finest("CREATE3 "+arg0+" TOKEN "+arg1+" STR "+arg2);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object deleteChild(Object arg0, int arg1) {
        LOG.finest("DELETE "+arg0+" "+arg1);
        return null;
    }

    
    /**
     * {@inheritDoc}
     */
    public Object dupTree(Object arg0) {
        LOG.finest("DUPTREE "+arg0);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object errorNode(TokenStream arg0, Token arg1, Token arg2,
            RecognitionException arg3) {
        LOG.finest("ERRORNODE "+arg0+" "+arg1+" "+arg2+" "+arg3);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object getChild(Object arg0, int arg1) {
        LOG.finest("GET CHILD "+arg0+" "+arg1);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public int getChildCount(Object arg0) {
        LOG.finest("GET CHILD COUNT "+arg0);
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getText(Object arg0) {
        LOG.finest("GET TEXT "+arg0);
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public int getType(Object arg0) {
        LOG.finest("GET TYPE "+arg0);
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    public int getUniqueID(Object arg0) {
        LOG.finest("GET UNIQUE ID "+arg0);
        return 0;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isNil(Object obj) {
        return (obj instanceof java.util.List<?>);
    }
    
    /**
     * {@inheritDoc}
     */
    public Object nil() {
        Object ret=null;
        
        if (_model == null) {
            _model = new ProtocolModel();
            ret = _model;
        } else {
            ret = new java.util.Vector<Object>();
        }
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public Object rulePostProcessing(Object root) {
        Object ret=root;
        
        LOG.finest("RULE POST PROCESSING "+root);

        // Check if intermediate node required
        if (_parser != null
                && _parser.getRuleInvocationStack().size() > 0) {
            String ruleName=(String)_parser.getRuleInvocationStack().get(
                    _parser.getRuleInvocationStack().size()-1);
            
            Class<?> cls = PARSER_GROUPING_RULE_CLASS.get(ruleName);
            
            LOG.finest("Parser grouping rule for name '"+ruleName+"' is class="+cls);
            
            // Check if rule invocation is associated with activity
            if (ruleName.equals(ACTIVITY_RULE_NAME) || CLEAR_TOKEN_LIST_RULES.contains(ruleName)) {
                LOG.fine("Reset current token");
                _currentToken = null;
            }
            
            if (cls != null) {
                
                if (cls == String.class) {
                    
                    if (isNil(root)) {
                        java.util.List<?> nil=
                            (java.util.List<?>)root;
                        StringBuffer buf=new StringBuffer();
                        
                        for (int i=0; i < nil.size(); i++) {
                            if (nil.get(i) instanceof Token) {
                                buf.append(((Token)nil.get(i)).getText());
                            } else {
                                buf.append(nil.get(i).toString());
                            }
                        }
                        
                        ret = buf.toString();
                    }
                } else {
                    try {
                        Object newRoot=cls.newInstance();
                        
                        LOG.finest("New root is: "+newRoot);
                        
                        addChild(newRoot, root);
                            
                        ret = newRoot;
    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        if (isNil(ret)) {
            java.util.List<?> nil=
                    (java.util.List<?>)ret;
            
            if (nil.size() == 1) {
                return (nil.get(0));
            } else {
                return (nil);
            }
        }
        
        return (ret);
    }
    
    /**
     * {@inheritDoc}
     */
    public void setChild(Object arg0, int arg1, Object arg2) {
        LOG.finest("SET CHILD "+arg0+" "+arg1+" "+arg2);
    }
    
    /**
     * {@inheritDoc}
     */
    public void setText(Object arg0, String arg1) {
        LOG.finest("SET TEXT "+arg0+" "+arg1);
    }
    
    /**
     * {@inheritDoc}
     */
    public void setType(Object arg0, int arg1) {
        LOG.finest("SET TYPE "+arg0+" "+arg1);
    }
}
