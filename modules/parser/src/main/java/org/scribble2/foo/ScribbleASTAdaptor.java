/*
 * Copyright 2009-11 www.scribble.org
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
package org.scribble2.foo;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.scribble.parser.antlr.Scribble2Parser;
import org.scribble2.foo.ast.Module;
import org.scribble2.foo.ast.ScribbleAST;
import org.scribble2.foo.ast.global.GlobalChoice;
import org.scribble2.foo.ast.global.GlobalContinue;
import org.scribble2.foo.ast.global.GlobalDo;
import org.scribble2.foo.ast.global.GlobalInteractionSequence;
import org.scribble2.foo.ast.global.GlobalInterruptible;
import org.scribble2.foo.ast.global.GlobalMessageTransfer;
import org.scribble2.foo.ast.global.GlobalParallel;
import org.scribble2.foo.ast.global.GlobalProtocolBlock;
import org.scribble2.foo.ast.global.GlobalProtocolDecl;
import org.scribble2.foo.ast.global.GlobalProtocolDefinition;
import org.scribble2.foo.ast.global.GlobalProtocolHeader;
import org.scribble2.foo.ast.global.GlobalRecursion;

/**
 * This class provides the antlr tree adaptor.
 *
 *
 *
 * To get your parser to build nodes of a different type, override create(Token), errorNode(), and to be safe, YourTreeClass.dupNode(). dupNode is called to duplicate nodes during rewrite operations
 *
 *
 */
public class ScribbleASTAdaptor extends CommonTreeAdaptor
{

	/*private static final Logger LOG=Logger.getLogger(ProtocolTreeAdaptor.class.getName());

	private ParserContext _context=new DefaultParserContext();
	  private ScribbleParser _parser=null;*/
	  private Scribble2Parser _parser=null;

	/**
	 * The constructor.
	 */
	public ScribbleASTAdaptor()
	{
	}

	/*/**
	 * This method returns the module.
	 * 
	 * @return The module, or null if an error occurred
	 * /
	public Module getModule() {
		return (_context.peek() instanceof Module ? (Module)_context.peek() : null);
	}*/
	
	  /**
	   * This method sets the parser.
	   * 
	   * @param parser The parser
	   */
	  public void setParser(Scribble2Parser parser) {
	      _parser = parser;
	  }

	  /**
	   * {@inheritDoc}
	   * /
	  public void addChild(Object arg0, Object arg1) {
		
	}

	  /**
	   * {@inheritDoc}
	   * /
	public Object becomeRoot(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	  /**
	   * {@inheritDoc}
	   * /
	public Object becomeRoot(Token arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/**
	 * {@inheritDoc}
	 */
	public Object create(Token arg0)
	{
		/*if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("CREATE TOKEN="+arg0);
		}
		_context.push(arg0);
		return null;*/

		/*if (arg0 != null && arg0.getText().equals("MODULE"))
		{
			System.out.println("b: " + arg0);
		}
		
		return super.create(arg0); */
		
		//CommonTree n = (CommonTree) arg0;
		if (arg0 == null)
		{
			return super.create(arg0);
		}
		//ScribbleAST o = null;
		Object o = null;
		switch (arg0.getText())
		{
			case "MODULE":
			{
				o = new Module(arg0);//, moddecl, imports, data, protos);
				break;
			}
			case "GLOBALPROTOCOLDECL":
			{
				o = new GlobalProtocolDecl(arg0);
				//o = super.create(arg0);
				//o = new GlobalProtocolDecl((CommonTree) o);
				//System.out.println("b: " + o.getClass());
				break;
			}
			case "GLOBALPROTOCOLHEADER":
			{
				o = new GlobalProtocolHeader(arg0);
				break;
			}
			case "GLOBALPROTOCOLDEF":
			{
				o = new GlobalProtocolDefinition(arg0);
				break;
			}
			case "GLOBALPROTOCOLBLOCK":
			{
				o = new GlobalProtocolBlock(arg0);
				break;
			}
			case "GLOBALINTERACTIONSEQUENCE":
			{
				o = new GlobalInteractionSequence(arg0);//, Collections.emptyList());
				break;
			}
			case "GLOBALMESSAGETRANSFER":
			{
				o = new GlobalMessageTransfer(arg0);//, Collections.emptyList());
				break;
			}
			/*case "GLOBALCHOICE":
			{
				o = new GlobalChoice(arg0);//, Collections.emptyList());
				break;
			}
			case "GLOBALRECURSION":
			{
				o = new GlobalRecursion(arg0);//, Collections.emptyList());
				break;
			}
			case "GLOBALCONTINUE":
			{
				o = new GlobalContinue(arg0);//, Collections.emptyList());
				break;
			}
			case "GLOBALPARALLEL":
			{
				o = new GlobalParallel(arg0);//, Collections.emptyList());
				break;
			}
			case "GLOBALINTERRUPTIBLE":
			{
				o = new GlobalInterruptible(arg0);//, Collections.emptyList());
				break;
			}
			case "GLOBALDO":
			{
				o = new GlobalDo(arg0);//, Collections.emptyList());
				break;
			}*/
			case "qualifiedname":
			{
				//System.out.println("q: " + ((CommonTree) arg0).getChildren() + ", " + ruleName);
				// ...cannot disambiguate here...  -> ambiguous name -> disambiguation pass
				break;
			}
			default:
			{
				//throw new RuntimeException("Unknown token: " + arg0);
				return super.create(arg0);
			}
		}
		
		return o;
	}

	@Override
	public Object rulePostProcessing(Object arg0) {
		
        //if (_parser != null && _parser.getRuleInvocationStack().size() > 0) {
            //String ruleName=(String)_parser.getRuleInvocationStack().get(_parser.getRuleInvocationStack().size()-1);
		
		Object o = super.rulePostProcessing(arg0);
		

		return o;
	}
	
	private static String[] parseQualifiedName(CommonTree qn)
	{
		List<String> names = new LinkedList<String>();
		names.add(qn.getText());
		@SuppressWarnings("unchecked")
		List<CommonTree> children = qn.getChildren();
		if (children != null)
		{
			children.stream().map((Object x) -> names.add(((CommonTree) x).getText()));
		}
		return names.toArray(new String[names.size()]);
	}

	private static List<CommonTree> getChildren(CommonTree n)
	{
		/*List<CommonTree> children = new LinkedList<CommonTree>();
		or (Object obj : n.getChildren())
		{
			children.add((CommonTree) obj);
		}
		return children;*/
		@SuppressWarnings("unchecked")
		List<CommonTree> children = n.getChildren();
		return children;
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends ScribbleAST> List<T> castChildren(List<CommonTree> ns)
	{
		List<T> ts = new LinkedList<T>();
		ns.stream().map((CommonTree n) -> ts.add((T) n));
		return ts;
	}
	
	private static <T extends ScribbleAST> List<T> getAndCastChildren(CommonTree n)
	{
		return ScribbleASTAdaptor.<T>castChildren(getChildren(n));
	}
	
	
	/*/**
	 * {@inheritDoc}
	 * /
	public Object create(int arg0, Token arg1) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public Object create(int arg0, String arg1) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public Object create(int arg0, Token arg1, String arg2) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public Object deleteChild(Object arg0, int arg1) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public Object dupNode(Object arg0) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public Object dupTree(Object arg0) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public Object errorNode(TokenStream arg0, Token arg1, Token arg2,
		RecognitionException arg3) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public Object getChild(Object arg0, int arg1) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public int getChildCount(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
	}

	/**
	 * {@inheritDoc}
	 * /
	public int getChildIndex(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
	}

	/**
	 * {@inheritDoc}
	 * /
	public Object getParent(Object arg0) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 * /
	public String getText(Object arg0) {
	// TODO Auto-generated method stub
	return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Token getToken(Object arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} / public int getTokenStartIndex(Object arg0) { // TODO
	 * Auto-generated method stub return 0; }
	 * 
	 * /** {@inheritDoc} / public int getTokenStopIndex(Object arg0) { // TODO
	 * Auto-generated method stub return 0; }
	 * 
	 * /** {@inheritDoc} / public int getType(Object arg0) { // TODO
	 * Auto-generated method stub return 0; }
	 * 
	 * /** {@inheritDoc} / public int getUniqueID(Object arg0) { // TODO
	 * Auto-generated method stub return 0; }
	 * 
	 * /** {@inheritDoc} / public boolean isNil(Object arg0) { // TODO
	 * Auto-generated method stub return false; }
	 * 
	 * /** {@inheritDoc} / public Object nil() { // TODO Auto-generated method
	 * stub return null; }
	 * 
	 * /** {@inheritDoc} / public void replaceChildren(Object arg0, int arg1, int
	 * arg2, Object arg3) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * /** {@inheritDoc} / public Object rulePostProcessing(Object arg0) { Object
	 * ret=arg0;
	 * 
	 * if (_parser != null && _parser.getRuleInvocationStack().size() > 0) {
	 * String ruleName=(String)_parser.getRuleInvocationStack().get(
	 * _parser.getRuleInvocationStack().size()-1);
	 * 
	 * if (LOG.isLoggable(Level.FINE)) {
	 * LOG.fine("RULE POST PROCESSING="+ruleName); }
	 * 
	 * if (!_parser.isErrorOccurred()) { ModelAdaptor
	 * modelAdaptor=ModelAdaptorFactory.getModelAdaptor(ruleName);
	 * 
	 * if (modelAdaptor != null) { ret = modelAdaptor.createModelObject(_context);
	 * //} else { // LOG.severe("No ModelAdaptor for rule name: "+ruleName); } } }
	 * 
	 * return ret; }
	 * 
	 * /** {@inheritDoc} / public void setChild(Object arg0, int arg1, Object
	 * arg2) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * /** {@inheritDoc} / public void setChildIndex(Object arg0, int arg1) { //
	 * TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * /** {@inheritDoc} / public void setParent(Object arg0, Object arg1) { //
	 * TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * /** {@inheritDoc} / public void setText(Object arg0, String arg1) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * /** {@inheritDoc} / public void setTokenBoundaries(Object arg0, Token arg1,
	 * Token arg2) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * /** {@inheritDoc} / public void setType(Object arg0, int arg1) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

}
