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
package org.scribble.protocol.parser.antlr;

import java.beans.PropertyDescriptor;
import java.util.logging.Logger;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import org.scribble.common.logging.Journal;
import org.scribble.common.model.Annotation;
import org.scribble.common.model.DefaultAnnotation;
import org.scribble.protocol.model.*;
import org.scribble.protocol.parser.AnnotationProcessor;

public class ProtocolTreeAdaptor implements org.antlr.runtime.tree.TreeAdaptor {

	private static final String _ANNOTATIONS = "_annotations";
	private static final String ACTIVITY_RULE_NAME = "activityDef";
	private static java.util.Map<String,String> m_propertyToken=
		new java.util.HashMap<String, String>();
	private static java.util.Map<String,Class<?>> m_tokenClass=
		new java.util.HashMap<String, Class<?>>();
	private static java.util.Map<String,Class<?>> m_listClass=
		new java.util.HashMap<String, Class<?>>();
	private static java.util.Map<String,Class<?>> m_parserGroupingRuleClass=
		new java.util.HashMap<String, Class<?>>();
	private static java.util.List<String> m_clearTokenListRules=
		new java.util.Vector<String>();
	private static java.util.List<String> m_tokensToIgnore=
		new java.util.Vector<String>();
	private static java.util.List<String> m_stringLiterals=
		new java.util.Vector<String>();
	
	private ScribbleProtocolParser m_parser=null;
	private AnnotationProcessor m_annotationProcessor=null;
	private Journal m_journal=null;
	private Token m_currentToken=null;
	
	private ProtocolModel m_model=null;
	
	private static final Logger _log=Logger.getLogger(ProtocolTreeAdaptor.class.getName());
	
	static {
		// The map of root tokens, that begin a grammer
		// rule, and the model class they are associated
		// with
		//m_tokenClass.put("import", ImportList.class);
		m_tokenClass.put("protocol", Protocol.class);
		//m_tokenClass.put("role", RoleList.class);
		m_tokenClass.put("choice", Choice.class);
		m_tokenClass.put("rec", RecBlock.class);
		m_tokenClass.put("parallel", Parallel.class);
		m_tokenClass.put("repeat", Repeat.class);
		m_tokenClass.put("do", Do.class);
		m_tokenClass.put("interrupt", Interrupt.class);
		m_tokenClass.put("run", Run.class);
		m_tokenClass.put("inline", Inline.class);
		m_tokenClass.put("unordered", Unordered.class);
		m_tokenClass.put("end", End.class);

		// Clear token list - determines whether prior to processing
		// a list of tokens, the 'current token' should be cleared
		m_clearTokenListRules.add("parameter");
		m_clearTokenListRules.add("dataTypeDef");
		m_clearTokenListRules.add("importTypeStatement");
		m_clearTokenListRules.add("importProtocolDef");
		m_clearTokenListRules.add("introducesDef");
		m_clearTokenListRules.add("directedChoiceDef");
		
		// The list of tokens that should be ignored when processing
		// the children of a parent node
		m_tokensToIgnore.add("import");

		// This may define the model object that should be
		// created after processing the named grammer rule
		m_parserGroupingRuleClass.put("importProtocolStatement", ProtocolImportList.class);
		m_parserGroupingRuleClass.put("importTypeStatement", TypeImportList.class);
		m_parserGroupingRuleClass.put("simpleName", String.class);
		m_parserGroupingRuleClass.put("simpleName", String.class);
		m_parserGroupingRuleClass.put("blockDef", Block.class);
		m_parserGroupingRuleClass.put("protocolBlockDef", Block.class);
		m_parserGroupingRuleClass.put("activityList", Block.class);
		m_parserGroupingRuleClass.put("interactionDef", Interaction.class);
		m_parserGroupingRuleClass.put("interactionSignatureDef", MessageSignature.class);
		m_parserGroupingRuleClass.put("typeReferenceDef", TypeReference.class);
		m_parserGroupingRuleClass.put("protocolName", String.class);
		m_parserGroupingRuleClass.put("roleName", Role.class);
		m_parserGroupingRuleClass.put("roleDef", Role.class);
		m_parserGroupingRuleClass.put("importProtocolDef", ProtocolImport.class);
		m_parserGroupingRuleClass.put("importTypeDef", TypeImport.class);
		m_parserGroupingRuleClass.put("protocolRefDef", ProtocolReference.class);
		m_parserGroupingRuleClass.put("parameter", Parameter.class);
		m_parserGroupingRuleClass.put("inlineProtocolDef", Protocol.class);
		m_parserGroupingRuleClass.put("declarationName", String.class);
		m_parserGroupingRuleClass.put("labelName", String.class);
		m_parserGroupingRuleClass.put("dataTypeDef", DataType.class);
		m_parserGroupingRuleClass.put("recursionDef", Recursion.class);
		m_parserGroupingRuleClass.put("parameterDef", ParameterDefinition.class);
		m_parserGroupingRuleClass.put("introducesDef", Introduces.class);
		m_parserGroupingRuleClass.put("directedChoiceDef", DirectedChoice.class);
		m_parserGroupingRuleClass.put("onMessageDef", OnMessage.class);
				
		// When a particular class has multiple properties of the
		// same type, then a preceding token must be used to
		// determine which property to set. This map provides the
		// mapping between the property name and the token.
		m_propertyToken.put("interactionDef:fromRole", "from");
		m_propertyToken.put("directedChoiceDef:fromRole", "from");
		m_propertyToken.put("choiceDef:role", "at");
		m_propertyToken.put("interactionDef:toRoles", "to");
		m_propertyToken.put("directedChoiceDef:toRoles", "to");
		m_propertyToken.put("parameter:boundName", "");
		m_propertyToken.put("parameter:localName", ":=");
		m_propertyToken.put("dataTypeDef:details", "<string literal>"); // Needed to make sure property not used by default
		m_propertyToken.put("importTypeStatement:format", "");
		m_propertyToken.put("importTypeStatement:location", "from");
		m_propertyToken.put("importProtocolDef:name", "");
		m_propertyToken.put("importProtocolDef:location", "from");
		m_propertyToken.put("introducesDef:introducer", "");
		m_propertyToken.put("introducesDef:introducedRoles", "introduces");
		
		// Defines the list element base type associated with a
		// property name
		m_listClass.put("imports", ImportList.class);
		m_listClass.put("contents", Activity.class);
		m_listClass.put("roles", Role.class);
		m_listClass.put("introducedRoles", Role.class);
		m_listClass.put("toRoles", Role.class);
		m_listClass.put("typeImports", TypeImport.class);
		m_listClass.put("protocolImports", ProtocolImport.class);
		m_listClass.put("typeReferences", TypeReference.class);
		m_listClass.put("blocks", Block.class);
		m_listClass.put("paths", Block.class);
		m_listClass.put("nestedProtocols", Protocol.class);
		m_listClass.put("onMessages", OnMessage.class);
		m_listClass.put("interrupts", Interrupt.class);
		m_listClass.put("parameters", Parameter.class);
		m_listClass.put("interactions", Interaction.class);
		m_listClass.put("parameterDefinitions", ParameterDefinition.class);
		
		m_stringLiterals.add("dataTypeDef:details");
		m_stringLiterals.add("importTypeStatement:location");
		m_stringLiterals.add("importProtocolDef:location");
	}
	
	
	public ProtocolTreeAdaptor(AnnotationProcessor ap, Journal journal) {
		m_annotationProcessor = ap;
		m_journal = journal;
	}
	
	public ProtocolModel getProtocolModel() {
		return(m_model);
	}
	
	public void setParser(ScribbleProtocolParser parser) {
		m_parser = parser;
	}
	
	public Object create(Token token) {
		Object ret=token;
		
		Class<?> cls=m_tokenClass.get(token.getText());
		
		_log.fine("Token class for '"+token.getText()+
				"' is: "+cls);

		if (cls != null) {
			try {
				ret = cls.newInstance();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if (ret == token && token.getType() != ScribbleProtocolParser.ID) {
			_log.fine("Set current token="+token);
			m_currentToken = token;
		}
		
		// Check if positional information should be set
		adjustLocationInfo(ret, token);
		
		return(ret);
	}
	
	public Object dupNode(Object arg0) {
		_log.finest("DUPNODE "+arg0);
		return null;
	}

	public int getChildIndex(Object arg0) {
		_log.finest("GET CHILD INDEX "+arg0);
		return 0;
	}
	
	public Object getParent(Object arg0) {
		_log.finest("GET PARENT "+arg0);
		return null;
	}
	
	public Token getToken(Object arg0) {
		_log.finest("GET TOKEN "+arg0);
		return null;
	}

	public int getTokenStartIndex(Object arg0) {
		_log.finest("GET TOKEN START INDEX "+arg0);
		return 0;
	}
	
	public int getTokenStopIndex(Object arg0) {
		_log.finest("GET TOKEN STOP INDEX "+arg0);
		return 0;
	}
	
	public void setChildIndex(Object arg0, int arg1) {
		_log.finest("SET CHILD INDEX "+arg0+" "+arg1);		
	}

	public void replaceChildren(Object arg0, int arg1, int arg2, Object arg3) {
		_log.finest("REPLACE CHILD "+arg0+" "+arg1+" "+arg2+" "+arg3);
	}
	
	public void setParent(Object arg0, Object arg1) {
		_log.finest("SET PARENT "+arg0+" "+arg1);
	}

	public void setTokenBoundaries(Object arg0, Token arg1, Token arg2) {
		_log.finest("SET TOKEN BOUNDARIES "+arg0+" "+arg1+" "+arg2);
	}

	@SuppressWarnings("unchecked")
	public void addChild(Object parent, Object child) {
		boolean f_added=false;
		
		_log.fine("Add child: parent="+parent+" child="+child);
		
		// Associate annotations with the protocol model
		if (parent instanceof ProtocolModel &&
				child instanceof Token && ((Token)child).getType() == 
							ScribbleProtocolParser.ANNOTATION) {
			
			// Add the annotation to a temporary location associated
			// with the model, until the real target model object is
			// identified
			java.util.List<String> annotations=(java.util.List<String>)
				((ProtocolModel)parent).getProperties().get(_ANNOTATIONS);
						
			if (annotations == null) {
				annotations = new java.util.Vector<String>();
				((ProtocolModel)parent).getProperties().put(_ANNOTATIONS, annotations);
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
			
			if (m_parser != null &&
					m_parser.getRuleInvocationStack().size() > 0) {
				ruleName = (String)m_parser.getRuleInvocationStack().get(
						m_parser.getRuleInvocationStack().size()-1);
								
				// Before processing the sublist of tokens, clear
				// the current token - needed for cases like the
				// 'parameter' syntax rule
				if (nil.size() > 0 && ruleName != null && m_clearTokenListRules.contains(ruleName)) {
					_log.fine("Clear current token before processing sublist of tokens: rule="+ruleName);
					m_currentToken = null;
				}
			}
			
			// Check if ID token
			StringBuffer buf=new StringBuffer();
			java.util.List<Token> annotations=new java.util.Vector<Token>();
			
			for (int i=0; i < nil.size(); i++) {
				
				if (nil.get(i) instanceof Token &&
						(((Token)nil.get(i)).getType() == ScribbleProtocolParser.ID ||
							((Token)nil.get(i)).getType() == ScribbleProtocolParser.FULLSTOP)) {
					buf.append(((Token)nil.get(i)).getText());
				} else {
					if (buf.length() > 0) {
						addChild(parent, buf.toString());
						
						buf = new StringBuffer();
					}
					
					if (nil.get(i) instanceof Token &&
							!m_tokensToIgnore.contains(((Token)nil.get(i)).getText())) {
						m_currentToken = (Token)nil.get(i);
						_log.fine("Set current token: "+m_currentToken);
					}

					if (nil.get(i) instanceof Token && ((Token)nil.get(i)).getType() == ScribbleProtocolParser.ANNOTATION) {
						/*
						if (parent instanceof When) {
							String text=((Token)nil.get(i)).getText();
							text = text.substring(2, text.length()-2);

							Annotation annotation=null;
							if (m_annotationProcessor != null) {
								annotation = m_annotationProcessor.getAnnotation(text,
										((When)parent).getProperties(), m_journal);
							}
							
							if (annotation == null) {
								annotation = new DefaultAnnotation(text);
							}
							
							((When)parent).getAnnotations().add(annotation);
						} else {
						*/
							annotations.add((Token)nil.get(i));
						//}
					} else {
						addChild(parent, nil.get(i));
						
						if (nil.get(i) instanceof ModelObject) {
							for (Token annotationToken : annotations) {
								String text=annotationToken.getText();
								text = text.substring(2, text.length()-2);
								
								Annotation annotation=null;
								if (m_annotationProcessor != null) {
									annotation = m_annotationProcessor.getAnnotation(text, 
											((ModelObject)nil.get(i)).getProperties(), m_journal);
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

			if (isNil(parent)) {
				java.util.List<Object> nil=
					(java.util.List<Object>)parent;

				_log.finest("Add child: "+child);
				nil.add(child);
				
			} else {
				_log.finest("Determine if can be set by property descriptor");
				
				// Check if annotations should be associated with the protocol model child
				if (parent instanceof ProtocolModel && child instanceof ModelObject) {
					java.util.List<String> annotations=(java.util.List<String>)
								((ProtocolModel)parent).getProperties().get(_ANNOTATIONS);
					
					if (annotations != null) {

						for (String text : annotations) {
							Annotation annotation=null;
							if (m_annotationProcessor != null) {
								annotation = m_annotationProcessor.getAnnotation(text, 
										((ModelObject)child).getProperties(), m_journal);
							}
							
							if (annotation == null) {
								annotation = new DefaultAnnotation(text);
							}
							
							((ModelObject)child).getAnnotations().add(annotation);
						}
					}
					
					// Clear existing properties
					((ProtocolModel)parent).getProperties().remove(_ANNOTATIONS);
				}
				
				// Check if child is a string literal
				boolean f_stringLiteral=false;

				if (child instanceof Token &&
						((Token)child).getType() == ScribbleProtocolParser.StringLiteral) {
					String strlit = ((Token)child).getText();
								
					if (((Token)child).getType() == ScribbleProtocolParser.StringLiteral &&
											strlit.length() >= 2) {
						// Strip the quotes
						child = strlit.substring(1, strlit.length()-1);
					} else {
						child = strlit;
					}
					
					f_stringLiteral = true;
				}
				
				String ruleName="";
				
				if (m_parser != null &&
						m_parser.getRuleInvocationStack().size() > 0) {
					ruleName = (String)m_parser.getRuleInvocationStack().get(
							m_parser.getRuleInvocationStack().size()-1);
				}
									
				try {
					// Get property descriptors for parent class
					java.beans.BeanInfo bi=
						java.beans.Introspector.getBeanInfo(parent.getClass());
					
					PropertyDescriptor[] pds=bi.getPropertyDescriptors();
					PropertyDescriptor pd=null;
					
					for (int i=0; pd == null && i < pds.length; i++) {
						
						String token=m_propertyToken.get(ruleName+":"+pds[i].getName());
						
						if (pds[i].getPropertyType().isAssignableFrom(child.getClass()) &&
								pds[i].getName().equals("parent") == false) {
							
							String ruleprop=ruleName+":"+pds[i].getName();
							
							if (f_stringLiteral) {
								if (m_stringLiterals.contains(ruleprop) &&
										pds[i].getWriteMethod() != null) {
									pd = pds[i];
								}
							} else if ((token == null ||
									(token.length() == 0 && m_currentToken == null) ||
									(m_currentToken != null && 
										token.equals(m_currentToken.getText()))) &&
										pds[i].getWriteMethod() != null) {
								pd = pds[i];
							}
						} else if (pds[i].getPropertyType() == java.util.List.class) {
							
							Class<?> listElementCls=m_listClass.get(pds[i].getName());
							
							if ((token == null ||
									(token.length() == 0 && m_currentToken == null) ||
									(m_currentToken != null && 
										token.equals(m_currentToken.getText()))) &&
									listElementCls != null &&
									listElementCls.isAssignableFrom(child.getClass())) {
								java.util.List<Object> list=(java.util.List<Object>)
										pds[i].getReadMethod().invoke(parent);
								
								_log.fine("Adding "+child+" to list: "+
										list+" on parent "+parent);
								list.add(child);
								
								f_added = true;
							}
						}
					}
					
					// If property descriptor is not for 'parent' property,
					// as this would match any 'child', and the property
					// must have a set method
					if (pd != null && pd.getWriteMethod() != null) {
						
						_log.fine("Set property '"+pd.getName()+
								"' on="+parent+" (class="+parent.getClass()+") to="+child);
						
						pd.getWriteMethod().invoke(parent, child);
						
						f_added = true;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if (f_added && parent instanceof ModelObject && child instanceof ModelObject) {
			
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

	protected void adjustLocationInfo(Object parent, Object child) {
		if (parent instanceof ModelObject) {
			ModelObject mobj=(ModelObject)parent;
			
			if (child instanceof Token) {
				Token token=(Token)child;
				
				if (mobj.getProperties().containsKey(Journal.START_LINE) == false ||
						token.getLine() < ((Integer)mobj.getProperties().get(Journal.START_LINE))) {
					mobj.getProperties().put(Journal.START_LINE, token.getLine());					
					mobj.getProperties().put(Journal.START_COLUMN, token.getCharPositionInLine());
				} else if (token.getLine() == ((Integer)mobj.getProperties().get(Journal.START_LINE)) &&
						token.getCharPositionInLine() < ((Integer)mobj.getProperties().get(Journal.START_COLUMN))) {	
					mobj.getProperties().put(Journal.START_COLUMN, token.getCharPositionInLine());
				}
				
				if (mobj.getProperties().containsKey(Journal.END_LINE) == false ||
						token.getLine() > ((Integer)mobj.getProperties().get(Journal.END_LINE))) {
					mobj.getProperties().put(Journal.END_LINE, token.getLine());					
					mobj.getProperties().put(Journal.END_COLUMN, token.getCharPositionInLine()+
										token.getText().length());
				} else if (token.getLine() == ((Integer)mobj.getProperties().get(Journal.END_LINE)) &&
						(token.getCharPositionInLine()+token.getText().length()) < 
								((Integer)mobj.getProperties().get(Journal.END_COLUMN))) {	
					mobj.getProperties().put(Journal.END_COLUMN, token.getCharPositionInLine()+
										token.getText().length());
				}
			} else if (child instanceof ModelObject) {
				ModelObject chobj=(ModelObject)child;
				
				if (chobj.getProperties().containsKey(Journal.START_LINE) &&
						chobj.getProperties().containsKey(Journal.START_COLUMN)) {
					
					if (mobj.getProperties().containsKey(Journal.START_LINE) == false ||
							((Integer)chobj.getProperties().get(Journal.START_LINE)) < 
								((Integer)mobj.getProperties().get(Journal.START_LINE))) {
						mobj.getProperties().put(Journal.START_LINE, chobj.getProperties().get(Journal.START_LINE));					
						mobj.getProperties().put(Journal.START_COLUMN, chobj.getProperties().get(Journal.START_COLUMN));
					} else if (((Integer)chobj.getProperties().get(Journal.START_LINE)) ==
								((Integer)mobj.getProperties().get(Journal.START_LINE)) &&
								((Integer)chobj.getProperties().get(Journal.START_COLUMN)) <
									((Integer)mobj.getProperties().get(Journal.START_COLUMN))) {	
						mobj.getProperties().put(Journal.START_COLUMN, chobj.getProperties().get(Journal.START_COLUMN));
					}
				}
			
				if (chobj.getProperties().containsKey(Journal.END_LINE) &&
						chobj.getProperties().containsKey(Journal.END_COLUMN)) {
					
					if (mobj.getProperties().containsKey(Journal.END_LINE) == false ||
							((Integer)chobj.getProperties().get(Journal.END_LINE)) < 
								((Integer)mobj.getProperties().get(Journal.END_LINE))) {
						mobj.getProperties().put(Journal.END_LINE, chobj.getProperties().get(Journal.END_LINE));					
						mobj.getProperties().put(Journal.END_COLUMN, chobj.getProperties().get(Journal.END_COLUMN));
					} else if (((Integer)chobj.getProperties().get(Journal.END_LINE)) ==
								((Integer)mobj.getProperties().get(Journal.END_LINE)) &&
								((Integer)chobj.getProperties().get(Journal.END_COLUMN)) <
									((Integer)mobj.getProperties().get(Journal.END_COLUMN))) {	
						mobj.getProperties().put(Journal.END_COLUMN, chobj.getProperties().get(Journal.END_COLUMN));
					}
				}
			}
		}
	}
	
	public Object becomeRoot(Object newRoot, Object oldRoot) {
		_log.finest("Become root "+newRoot+" "+oldRoot);
		
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
		
		return(newRoot);
	}
	
	public Object becomeRoot(Token arg0, Object arg1) {
		_log.finest("BECOME_ROOT2 "+arg0+" "+arg1);
		return null;
	}
	
	public Object create(int arg0, Token arg1) {
		_log.finest("CREATE "+arg0+" TOKEN "+arg1);
		return null;
	}
	
	public Object create(int arg0, String arg1) {
		_log.finest("CREATE2 "+arg0+" STR "+arg1);
		return null;
	}
	
	public Object create(int arg0, Token arg1, String arg2) {
		_log.finest("CREATE3 "+arg0+" TOKEN "+arg1+" STR "+arg2);
		return null;
	}
	
	public Object deleteChild(Object arg0, int arg1) {
		_log.finest("DELETE "+arg0+" "+arg1);
		return null;
	}

	
	public Object dupTree(Object arg0) {
		_log.finest("DUPTREE "+arg0);
		return null;
	}
	
	public Object errorNode(TokenStream arg0, Token arg1, Token arg2,
			RecognitionException arg3) {
		_log.finest("ERRORNODE "+arg0+" "+arg1+" "+arg2+" "+arg3);
		return null;
	}
	
	public Object getChild(Object arg0, int arg1) {
		_log.finest("GET CHILD "+arg0+" "+arg1);
		return null;
	}
	
	public int getChildCount(Object arg0) {
		_log.finest("GET CHILD COUNT "+arg0);
		return 0;
	}
	
	public String getText(Object arg0) {
		_log.finest("GET TEXT "+arg0);
		return null;
	}
	
	public int getType(Object arg0) {
		_log.finest("GET TYPE "+arg0);
		return 0;
	}
	
	public int getUniqueID(Object arg0) {
		_log.finest("GET UNIQUE ID "+arg0);
		return 0;
	}
	
	public boolean isNil(Object obj) {
		return(obj instanceof java.util.List<?>);
	}
	
	public Object nil() {
		Object ret=null;
		
		if (m_model == null) {
			m_model = new ProtocolModel();
			ret = m_model;
		} else {
			ret = new java.util.Vector<Object>();
		}
		
		return(ret);
	}
	
	public Object rulePostProcessing(Object root) {
		Object ret=root;
		
		_log.finest("RULE POST PROCESSING "+root);

		// Check if intermediate node required
		if (m_parser != null &&
				m_parser.getRuleInvocationStack().size() > 0) {
			String ruleName=(String)m_parser.getRuleInvocationStack().get(
					m_parser.getRuleInvocationStack().size()-1);
			
			Class<?> cls = m_parserGroupingRuleClass.get(ruleName);
			
			_log.finest("Parser grouping rule for name '"+ruleName+"' is class="+cls);
			
			// Check if rule invocation is associated with activity
			if (ruleName.equals(ACTIVITY_RULE_NAME) || m_clearTokenListRules.contains(ruleName)) {
				_log.fine("Reset current token");
				m_currentToken = null;
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
						
						_log.finest("New root is: "+newRoot);
						
						addChild(newRoot, root);
							
						ret = newRoot;
	
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if (isNil(ret)) {
			java.util.List<?> nil=
					(java.util.List<?>)ret;
			
			if (nil.size() == 1) {
				return(nil.get(0));
			} else {
				return(nil);
			}
		}
		
		return(ret);
	}
	
	public void setChild(Object arg0, int arg1, Object arg2) {
		_log.finest("SET CHILD "+arg0+" "+arg1+" "+arg2);
	}
	
	public void setText(Object arg0, String arg1) {
		_log.finest("SET TEXT "+arg0+" "+arg1);
	}
	
	public void setType(Object arg0, int arg1) {
		_log.finest("SET TYPE "+arg0+" "+arg1);
	}
}
