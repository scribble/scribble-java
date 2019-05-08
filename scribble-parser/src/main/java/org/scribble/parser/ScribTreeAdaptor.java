/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.parser;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.scribble.ast.AuxMod;
import org.scribble.ast.DataDecl;
import org.scribble.ast.DataParamDecl;
import org.scribble.ast.ExplicitMod;
import org.scribble.ast.ImportModule;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.PayElemList;
import org.scribble.ast.ProtoModList;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNil;
import org.scribble.ast.ScribNode;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.SigDecl;
import org.scribble.ast.SigLitNode;
import org.scribble.ast.SigParamDecl;
import org.scribble.ast.UnaryPayElem;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDelegPayElem;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMsgTransfer;
import org.scribble.ast.global.GProtoBlock;
import org.scribble.ast.global.GProtoDecl;
import org.scribble.ast.global.GProtoDef;
import org.scribble.ast.global.GProtoHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.global.GWrap;
import org.scribble.ast.name.qualified.DataNameNode;
import org.scribble.ast.name.qualified.GProtoNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.qualified.SigNameNode;
import org.scribble.ast.name.simple.ExtIdNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.del.DelFactory;
import org.scribble.parser.antlr.ScribbleParser;

// CHECKME: do del setting directly here (or use af?), instead of DelDecorator
// get/setType don't seem to be really used
public class ScribTreeAdaptor extends CommonTreeAdaptor
{
	protected final DelFactory df;  // N.B. not af -- here, create nodes "manually" (with del setting) to preserve original tokens

	public ScribTreeAdaptor(DelFactory df)
	{
		this.df = df;
	}
	
	// Generated parser seems to use nil to create "blank" nodes and then "fill them in"
	@Override
	public Object nil()
	{
		return new ScribNil();
	}

	// Create a Tree (ScribNode) from a Token
	@Override
	public ScribNode create(Token t)
	{
		// Switching on ScribbleParser int type constants -- generated from Scribble.g tokens
		// Previously: String tname = t.getText(); -- by convention of Scribble.g, type constant name given as node text, e.g., module: ... -> ^(MODULE ...)
		ScribNodeBase n;
		switch (t.getType())
		{
			case ScribbleParser.ID: n = new IdNode(t); break;
			case ScribbleParser.EXTID:
				t = new CommonToken(t);
				String text = t.getText();
				t.setText(text.substring(1, text.length()-1));  // N.B. remove surrounding quotes "..."
				n = new ExtIdNode(t);
				break;
			
			// Simple names "constructed directly" by parser, e.g., t=ID -> ID<...Node>[$t] -- N.B. DelDecorator pass needed for them (CHECKME: also do those here instead? to deprecate DelDecorator)

			// Compound names 
			case ScribbleParser.GPROTO_NAME: n = new GProtoNameNode(t); break;
			case ScribbleParser.MODULE_NAME: n = new ModuleNameNode(t); break;
			case ScribbleParser.DATA_NAME: n = new DataNameNode(t); break;
			case ScribbleParser.SIG_NAME: n = new SigNameNode(t); break;

			// Non-name (i.e., general) AST nodes
			case ScribbleParser.MODULE: n = new Module(t); break;
			case ScribbleParser.MODULEDECL: n = new ModuleDecl(t); break;
			case ScribbleParser.IMPORTMODULE: n = new ImportModule(t); break;

			case ScribbleParser.DATADECL: n = new DataDecl(t); break;
			case ScribbleParser.SIGDECL: n = new SigDecl(t); break;
			case ScribbleParser.GPROTODECL: n = new GProtoDecl(t); break;
 
			// CHECKME: refactor into header?
			case ScribbleParser.PROTOMOD_LIST: n = new ProtoModList(t); break;
			case ScribbleParser.AUX_KW: n = new AuxMod(t); break;  // FIXME: KW return by parser directly (cf. other tokens are imaginary)
			case ScribbleParser.EXPLICIT_KW: n = new ExplicitMod(t); break;

			case ScribbleParser.GPROTOHEADER: n = new GProtoHeader(t); break;
			case ScribbleParser.ROLEDECL_LIST: n = new RoleDeclList(t); break;
			case ScribbleParser.ROLEDECL: n = new RoleDecl(t); break;
			case ScribbleParser.PARAMDECL_LIST:
				n = new NonRoleParamDeclList(t);
				break;
			case ScribbleParser.DATAPARAMDECL: n = new DataParamDecl(t); break;
			case ScribbleParser.SIGPARAMDECL: n = new SigParamDecl(t); break;

			case ScribbleParser.GPROTODEF: n = new GProtoDef(t); break;
			case ScribbleParser.GPROTOBLOCK: n = new GProtoBlock(t); break;
			case ScribbleParser.GINTERSEQ: n = new GInteractionSeq(t); break;

			case ScribbleParser.SIG_LIT: n = new SigLitNode(t); break;
			case ScribbleParser.PAYELEM_LIST: n = new PayElemList(t); break;
			case ScribbleParser.UNARY_PAYELEM: n = new UnaryPayElem<>(t); break;
			case ScribbleParser.GDELEG_PAYELEM: n = new GDelegPayElem(t); break;

			case ScribbleParser.GMSGTRANSFER: n = new GMsgTransfer(t); break;
			case ScribbleParser.GCONNECT: n = new GConnect(t); break;
			case ScribbleParser.GDCONN: n = new GDisconnect(t); break;
			case ScribbleParser.GWRAP: n = new GWrap(t); break;

			case ScribbleParser.GCONTINUE: n = new GContinue(t); break;
			case ScribbleParser.GDO: n = new GDo(t); break;

			case ScribbleParser.ROLEARG_LIST: n = new RoleArgList(t); break;
			case ScribbleParser.ROLEARG: n = new RoleArg(t); break;
			case ScribbleParser.NONROLEARG_LIST: n = new NonRoleArgList(t); break;
			case ScribbleParser.NONROLEARG: n = new NonRoleArg(t); break;

			case ScribbleParser.GCHOICE: n = new GChoice(t); break;
			case ScribbleParser.GRECURSION: n = new GRecursion(t); break;

			// Special cases
			case ScribbleParser.EMPTY_OP: n = new OpNode(t); break;  // From Scribble.g, token (t) text is OpNode.EMPTY_OP_TOKEN_TEXT*/

			default:
			{
				throw new RuntimeException("[TODO] Unknown token type (cf. ScribbleParser): " + t);
			}
		}
		n.decorateDel(this.df);
		
		return n;
	}
}
