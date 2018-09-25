package org.scribble.ext.go.parser.scribble;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ScribNode;
import org.scribble.ext.go.ast.RPAstFactory;
import org.scribble.ext.go.parser.scribble.ast.RPAntlrPayloadElemList;
import org.scribble.ext.go.parser.scribble.ast.RPCoreAntlrModule;
import org.scribble.ext.go.parser.scribble.ast.global.RPAntlrGChoice;
import org.scribble.ext.go.parser.scribble.ast.global.RPAntlrGCrossMessageTransfer;
import org.scribble.ext.go.parser.scribble.ast.global.RPAntlrGDotMessageTransfer;
import org.scribble.ext.go.parser.scribble.ast.global.RPCoreAntlrGForeach;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.parser.scribble.ScribbleAntlrConstants;
import org.scribble.util.ScribParserException;

public class RPAntlrToScribParser extends AntlrToScribParser
{
	// FIXME: refactor constants following ScribbleAntlrConstants/AntlrToScribParserUtil? -- cannot extend existing node type enum though
	public static final String PARAM_ROLEDECL_NODE_TYPE = "PARAM_ROLEDECL";    
	public static final String PARAM_GLOBALCROSSMESSAGETRANSFER_NODE_TYPE = "PARAM_GLOBALCROSSMESSAGETRANSFER";
	public static final String PARAM_GLOBALDOTMESSAGETRANSFER_NODE_TYPE = "PARAM_GLOBALDOTMESSAGETRANSFER";
	public static final String PARAM_GLOBALCHOICE_NODE_TYPE = "PARAM_GLOBALCHOICE";
	/*public static final String PARAM_GLOBALMULTICHOICES_NODE_TYPE = "PARAM_GLOBALMULTICHOICES";
	public static final String PARAM_GLOBALMULTICHOICESTRANSFER_NODE_TYPE = "PARAM_GLOBALMULTICHOICESTRANSFER";*/
	public static final String PARAM_DELEGDECL_NODE_TYPE = "PARAM_DELEGDECL";
	public static final String PARAM_GLOBALFOREACH = "PARAM_GLOBALFOREACH";

	public static final String PARAM_DELEGATION = "PARAM_DELEGATION";
	
	public RPAntlrToScribParser()
	{

	}

	@Override
	public ScribNode parse(CommonTree ct, AstFactory af) throws ScribParserException
	{
		AntlrToScribParser.checkForAntlrErrors(ct);
		
		RPAstFactory aaf = (RPAstFactory) af;
		String type = ct.getToken().getText();  // Duplicated from ScribParserUtil.getAntlrNodeType  // FIXME: factor out with ParamAntlrPayloadElemList.parsePayloadElem
		switch (type)
		{
			// "Overrides"
			case ScribbleAntlrConstants.MODULE_NODE_TYPE: 
				return RPCoreAntlrModule.parseModule(this, ct, af);
			case ScribbleAntlrConstants.PAYLOAD_NODE_TYPE:                   
			{
				return RPAntlrPayloadElemList.parsePayloadElemList(this, ct, af);
			}


			// "Extensions"
			//case PARAM_ROLEDECL_NODE_TYPE: return RPAntlrRoleDecl.parseParamRoleDecl(this, ct, aaf);
			case PARAM_GLOBALCROSSMESSAGETRANSFER_NODE_TYPE:
				return RPAntlrGCrossMessageTransfer.parseParamGCrossMessageTransfer(this, ct, aaf);
			case PARAM_GLOBALDOTMESSAGETRANSFER_NODE_TYPE:
				return RPAntlrGDotMessageTransfer.parseParamGDotMessageTransfer(this, ct, aaf);
			case PARAM_GLOBALCHOICE_NODE_TYPE:
				return RPAntlrGChoice.parseParamGChoice(this, ct, aaf);
			/*case PARAM_GLOBALMULTICHOICES_NODE_TYPE:
				return RPAntlrGMultiChoices.parseParamGMultiChoices(this, ct, aaf);
			case PARAM_GLOBALMULTICHOICESTRANSFER_NODE_TYPE:
				return RPAntlrGMultiChoicesTransfer.parseParamGMultiChoicesTransfer(this, ct, aaf);*/
			case PARAM_GLOBALFOREACH:
				return RPCoreAntlrGForeach.parseRPGForeach(this, ct, aaf);
			/*case PARAM_DELEGDECL_NODE_TYPE:  // FIXME: deprecate
				return RPCoreAntlrDelegDecl.parseDelegDecl(this, ct, aaf);*/


			default: return super.parse(ct, af);
		}
	}
}
