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
package org.scribble.parser.scribble;

import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.scribble.ast.AuxMod;
import org.scribble.ast.DataDecl;
import org.scribble.ast.ExplicitMod;
import org.scribble.ast.ImportModule;
import org.scribble.ast.SigDecl;
import org.scribble.ast.SigLitNode;
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
import org.scribble.ast.SigParamDecl;
import org.scribble.ast.DataParamDecl;
import org.scribble.ast.UnaryPayElem;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.global.GContinue;
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
import org.scribble.ast.name.qualified.SigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.simple.ExtIdNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.parser.antlr.ScribbleParser;

// get/setType don't seem to be really used
public class ScribTreeAdaptor extends CommonTreeAdaptor
{
	public static final List<String> TOKEN_NAMES = 
			Arrays.asList(ScribbleParser.tokenNames);
	
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
		switch (t.getType())
		{
			case ScribbleParser.ID: return new IdNode(t);
			case ScribbleParser.EXTID: return new ExtIdNode(t);
			
			// Simple names "constructed directly" by parser, e.g., t=ID -> ID<...Node>[$t] 

			case ScribbleParser.GPROTO_NAME: return new GProtoNameNode(t);
			case ScribbleParser.MODULE_NAME: return new ModuleNameNode(t);
			case ScribbleParser.DATA_NAME: return new DataNameNode(t);
			case ScribbleParser.SIG_NAME: return new SigNameNode(t);

			case ScribbleParser.MODULE: return new Module(t);
			case ScribbleParser.MODULEDECL: return new ModuleDecl(t);
			case ScribbleParser.IMPORTMODULE: return new ImportModule(t);

			case ScribbleParser.DATADECL: return new DataDecl(t);
			case ScribbleParser.SIGDECL: return new SigDecl(t);
			case ScribbleParser.GPROTODECL: return new GProtoDecl(t);
 
			// CHECKME: refactor into header?
			case ScribbleParser.PROTOMOD_LIST: return new ProtoModList(t);
			case ScribbleParser.AUX_KW: return new AuxMod(t);  // FIXME: KW return by parser directly (cf. other tokens are imaginary)
			case ScribbleParser.EXPLICIT_KW: return new ExplicitMod(t);

			case ScribbleParser.GPROTOHEADER: return new GProtoHeader(t);
			case ScribbleParser.ROLEDECL_LIST: return new RoleDeclList(t);
			case ScribbleParser.ROLEDECL: return new RoleDecl(t);
			case ScribbleParser.PARAMDECL_LIST: return new NonRoleParamDeclList(t);
			case ScribbleParser.DATAPARAMDECL: return new DataParamDecl(t);
			case ScribbleParser.SIGPARAMDECL: return new SigParamDecl(t);

			case ScribbleParser.GPROTODEF: return new GProtoDef(t);
			case ScribbleParser.GPROTOBLOCK: return new GProtoBlock(t);
			case ScribbleParser.GINTERSEQ: return new GInteractionSeq(t);

			case ScribbleParser.SIG_LIT: return new SigLitNode(t);
			case ScribbleParser.PAYELEM_LIST: return new PayElemList(t);
			case ScribbleParser.UNARY_PAYELEM: return new UnaryPayElem<>(t);

			case ScribbleParser.GCONNECT: return new GConnect(t);
			case ScribbleParser.GDCONN: return new GDisconnect(t);
			case ScribbleParser.GMSGTRANSFER: return new GMsgTransfer(t);
			case ScribbleParser.GWRAP: return new GWrap(t);

			case ScribbleParser.GCONTINUE: return new GContinue(t);
			case ScribbleParser.GDO: return new GDo(t);
				
			case ScribbleParser.ROLEARG_LIST: return new RoleArgList(t);
			case ScribbleParser.ROLEARG: return new RoleArg(t);
			case ScribbleParser.ARG_LIST: return new NonRoleArgList(t);
			case ScribbleParser.ARG: return new NonRoleArg(t);

			case ScribbleParser.GCHOICE: return new GChoice(t);
			case ScribbleParser.GRECURSION: return new GRecursion(t);

			// Special cases
			case ScribbleParser.EMPTY_OP: return new OpNode(t);  // From Scribble.g, token (t) text is OpNode.EMPTY_OP_TOKEN_TEXT

			default:
			{
				throw new RuntimeException("[TODO] Unknown token type (cf. ScribbleParser): " + t);
			}
		}
	}
}
