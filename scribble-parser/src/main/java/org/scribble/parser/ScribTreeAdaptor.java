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
import org.scribble.ast.SigDecl;
import org.scribble.ast.SigLitNode;
import org.scribble.ast.SigParamDecl;
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
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.qualified.SigNameNode;
import org.scribble.ast.name.simple.ExtIdNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.core.type.kind.PayElemKind;
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
		switch (t.getType())
		{
			case ScribbleParser.ID:
			{
				IdNode n = new IdNode(t);
				this.df.IdNode(n);
				return n;
			}
			case ScribbleParser.EXTID: 
			{
				ExtIdNode n = new ExtIdNode(t);
				this.df.ExtIdNode(n);
				return n;
			}
			
			// Simple names "constructed directly" by parser, e.g., t=ID -> ID<...Node>[$t] -- N.B. DelDecorator pass needed for them (CHECKME: also do those here instead? to deprecate DelDecorator)

			// Compound names 
			case ScribbleParser.GPROTO_NAME:
			{
				GProtoNameNode n = new GProtoNameNode(t);
				this.df.GProtoNameNode(n);
				return n;
			}
			case ScribbleParser.MODULE_NAME:
			{
				ModuleNameNode n = new ModuleNameNode(t);
				this.df.ModuleNameNode(n);
				return n;
			}
			case ScribbleParser.DATA_NAME:
			{
				DataNameNode n = new DataNameNode(t);
				this.df.DataNameNode(n);
				return n;
			}
			case ScribbleParser.SIG_NAME:
			{
				SigNameNode n = new SigNameNode(t);
				this.df.SigNameNode(n);
				return n;
			}

			// Non-name (i.e., general) AST nodes
			case ScribbleParser.MODULE:
			{
				Module n = new Module(t);
				this.df.Module(n);
				return n;
			}
			case ScribbleParser.MODULEDECL:
			{
				ModuleDecl n = new ModuleDecl(t);
				this.df.ModuleDecl(n);
				return n;
			}
			case ScribbleParser.IMPORTMODULE:
			{
				ImportModule n = new ImportModule(t);
				this.df.ImportModule(n);
				return n;
			}

			case ScribbleParser.DATADECL:
			{
				DataDecl n = new DataDecl(t);
				this.df.DataDecl(n);
				return n;
			}
			case ScribbleParser.SIGDECL:
			{
				SigDecl n = new SigDecl(t);
				this.df.SigDecl(n);
				return n;
			}
			case ScribbleParser.GPROTODECL:
			{
				GProtoDecl n = new GProtoDecl(t);
				this.df.GProtoDecl(n);
				return n;
			}
 
			// CHECKME: refactor into header?
			case ScribbleParser.PROTOMOD_LIST:
			{
				ProtoModList n = new ProtoModList(t);
				this.df.ProtoModList(n);
				return n;
			}
			case ScribbleParser.AUX_KW:
			{
				AuxMod n = new AuxMod(t);  // FIXME: KW return by parser directly (cf. other tokens are imaginary)
				this.df.AuxMod(n);
				return n;
			}
			case ScribbleParser.EXPLICIT_KW:
			{
				ExplicitMod n = new ExplicitMod(t);
				this.df.ExplicitMod(n);
				return n;
			}

			case ScribbleParser.GPROTOHEADER:
			{
				GProtoHeader n = new GProtoHeader(t);
				this.df.GProtoHeader(n);
				return n;
			}
			case ScribbleParser.ROLEDECL_LIST:
			{
				RoleDeclList n = new RoleDeclList(t);
				this.df.RoleDeclList(n);
				return n;
			}
			case ScribbleParser.ROLEDECL:
			{
				RoleDecl n = new RoleDecl(t);
				this.df.RoleDecl(n);
				return n;
			}
			case ScribbleParser.PARAMDECL_LIST:
			{
				NonRoleParamDeclList n = new NonRoleParamDeclList(t);
				this.df.NonRoleParamDeclList(n);
				return n;
			}
			case ScribbleParser.DATAPARAMDECL:
			{
				DataParamDecl n = new DataParamDecl(t);
				this.df.DataParamDecl(n);
				return n;
			}
			case ScribbleParser.SIGPARAMDECL:
			{
				SigParamDecl n = new SigParamDecl(t);
				this.df.SigParamDecl(n);
				return n;
			}

			case ScribbleParser.GPROTODEF:
			{
				GProtoDef n = new GProtoDef(t);
				this.df.GProtoDef(n);
				return n;
			}
			case ScribbleParser.GPROTOBLOCK:
			{
				GProtoBlock n = new GProtoBlock(t);
				this.df.GProtoBlock(n);
				return n;
			}
			case ScribbleParser.GINTERSEQ:
			{
				GInteractionSeq n = new GInteractionSeq(t);
				this.df.GInteractionSeq(n);
				return n;
			}

			case ScribbleParser.SIG_LIT:
			{
				SigLitNode n = new SigLitNode(t);
				this.df.SigLitNode(n);
				return n;
			}
			case ScribbleParser.PAYELEM_LIST:
			{
				PayElemList n = new PayElemList(t);
				this.df.PayElemList(n);
				return n;
			}
			case ScribbleParser.UNARY_PAYELEM:
			{
				UnaryPayElem<PayElemKind> n = new UnaryPayElem<>(t);
				this.df.UnaryPayElem(n);
				return n;
			}

			case ScribbleParser.GCONNECT:
			{
				GConnect n = new GConnect(t);
				this.df.GConnect(n);
				return n;
			}
			case ScribbleParser.GDCONN:
			{
				GDisconnect n = new GDisconnect(t);
				this.df.GDisconnect(n);
				return n;
			}
			case ScribbleParser.GMSGTRANSFER:
			{
				GMsgTransfer n = new GMsgTransfer(t);
				this.df.GMsgTransfer(n);
				return n;
			}
			case ScribbleParser.GWRAP:
			{
				GWrap n = new GWrap(t);
				this.df.GWrap(n);
				return n;
			}

			case ScribbleParser.GCONTINUE:
			{
				GContinue n = new GContinue(t);
				this.df.GContinue(n);
				return n;
			}
			case ScribbleParser.GDO:
			{
				GDo n = new GDo(t);
				this.df.GDo(n);
				return n;
			}

			case ScribbleParser.ROLEARG_LIST:
			{
				RoleArgList n = new RoleArgList(t);
				this.df.RoleArgList(n);
				return n;
			}
			case ScribbleParser.ROLEARG:
			{
				RoleArg n = new RoleArg(t);
				this.df.RoleArg(n);
				return n;
			}
			case ScribbleParser.NONROLEARG_LIST:
			{
				NonRoleArgList n = new NonRoleArgList(t);
				this.df.NonRoleArgList(n);
				return n;
			}
			case ScribbleParser.NONROLEARG:
			{
				NonRoleArg n = new NonRoleArg(t);
				this.df.NonRoleArg(n);
				return n;
			}

			case ScribbleParser.GCHOICE:
			{
				GChoice n = new GChoice(t);
				this.df.GChoice(n);
				return n;
			}
			case ScribbleParser.GRECURSION:
			{
				GRecursion n = new GRecursion(t);
				this.df.GRecursion(n);
				return n;
			}

			// Special cases
			case ScribbleParser.EMPTY_OP:
			{
				OpNode n = new OpNode(t);  // From Scribble.g, token (t) text is OpNode.EMPTY_OP_TOKEN_TEXT
				this.df.OpNode(n);
				return n;
			}

			default:
			{
				throw new RuntimeException("[TODO] Unknown token type (cf. ScribbleParser): " + t);
			}
		}
	}
}
