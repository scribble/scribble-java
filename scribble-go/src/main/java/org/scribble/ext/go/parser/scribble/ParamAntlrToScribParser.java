package org.scribble.ext.go.parser.scribble;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.ScribNode;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.ext.go.parser.scribble.ast.ParamAntlrGChoice;
import org.scribble.ext.go.parser.scribble.ast.ParamAntlrRoleDecl;
import org.scribble.ext.go.parser.scribble.ast.global.ParamAntlrGCrossMessageTransfer;
import org.scribble.ext.go.parser.scribble.ast.global.ParamAntlrGDotMessageTransfer;
import org.scribble.parser.scribble.AntlrToScribParser;
import org.scribble.util.ScribParserException;

public class ParamAntlrToScribParser extends AntlrToScribParser
{
	// FIXME: refactor constants following ScribbleAntlrConstants/AntlrToScribParserUtil? -- cannot extend existing node type enum though
	public static final String PARAM_ROLEDECL_NODE_TYPE = "PARAM_ROLEDECL";    
	public static final String PARAM_GLOBALCROSSMESSAGETRANSFER_NODE_TYPE = "PARAM_GLOBALCROSSMESSAGETRANSFER";
	public static final String PARAM_GLOBALDOTMESSAGETRANSFER_NODE_TYPE = "PARAM_GLOBALDOTMESSAGETRANSFER";
	public static final String PARAM_GLOBALCHOICE_NODE_TYPE = "PARAM_GLOBALCHOICE";

	public ParamAntlrToScribParser()
	{

	}

	@Override
	public ScribNode parse(CommonTree ct, AstFactory af) throws ScribParserException
	{
		AntlrToScribParser.checkForAntlrErrors(ct);
		
		ParamAstFactory aaf = (ParamAstFactory) af;
		String type = ct.getToken().getText();  // Duplicated from ScribParserUtil.getAntlrNodeType  // FIXME: factor out with ParamAntlrPayloadElemList.parsePayloadElem
		switch (type)
		{
			case PARAM_ROLEDECL_NODE_TYPE: return ParamAntlrRoleDecl.parseParamRoleDecl(this, ct, aaf);
			case PARAM_GLOBALCROSSMESSAGETRANSFER_NODE_TYPE:
				return ParamAntlrGCrossMessageTransfer.parseParamGCrossMessageTransfer(this, ct, aaf);
			case PARAM_GLOBALDOTMESSAGETRANSFER_NODE_TYPE:
				return ParamAntlrGDotMessageTransfer.parseParamGDotMessageTransfer(this, ct, aaf);
			case PARAM_GLOBALCHOICE_NODE_TYPE:
				return ParamAntlrGChoice.parseParamGChoice(this, ct, aaf);

			default: return super.parse(ct, af);
		}
	}
}
