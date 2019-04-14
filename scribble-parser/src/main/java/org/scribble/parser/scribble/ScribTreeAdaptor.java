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
import org.scribble.ast.DataTypeDecl;
import org.scribble.ast.ExplicitMod;
import org.scribble.ast.ImportModule;
import org.scribble.ast.MessageSigNameDecl;
import org.scribble.ast.MessageSigNode;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonRoleArg;
import org.scribble.ast.NonRoleArgList;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.PayloadElemList;
import org.scribble.ast.ProtocolModList;
import org.scribble.ast.RoleArg;
import org.scribble.ast.RoleArgList;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.ScribNil;
import org.scribble.ast.ScribNode;
import org.scribble.ast.SigParamDecl;
import org.scribble.ast.TypeParamDecl;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GConnect;
import org.scribble.ast.global.GContinue;
import org.scribble.ast.global.GDisconnect;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.global.GRecursion;
import org.scribble.ast.name.qualified.DataTypeNode;
import org.scribble.ast.name.qualified.GProtocolNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.ast.name.simple.AmbigNameNode;
import org.scribble.ast.name.simple.IdNode;
import org.scribble.ast.name.simple.OpNode;
import org.scribble.ast.name.simple.RecVarNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.ast.name.simple.SigParamNode;
import org.scribble.ast.name.simple.TypeParamNode;
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
			case ScribbleParser.IDENTIFIER: return new IdNode(t);
			case ScribbleParser.EXTIDENTIFIER: return new IdNode(t);  // CHECKME: OK?

			/*case ScribbleParser.ROLENAME: return new RoleNode(t);
			case ScribbleParser.TYPEPARAMNAME: return new TypeParamNode(t);
			case ScribbleParser.SIGPARAMNAME: return new SigParamNode(t);
			case ScribbleParser.AMBIGUOUSNAME: return new AmbigNameNode(t);*/
			//case ScribbleParser.OPNAME: return new OpNode(t);  // FIXME: empty special case

			case ScribbleParser.TYPENAME: return new DataTypeNode(t);
			case ScribbleParser.SIGNAME: return new MessageSigNameNode(t);
			case ScribbleParser.MODULENAME: return new ModuleNameNode(t);
			case ScribbleParser.GPROTOCOLNAME: return new GProtocolNameNode(t);

			case ScribbleParser.MODULE: return new Module(t);
			case ScribbleParser.MODULEDECL: return new ModuleDecl(t);
			case ScribbleParser.IMPORTMODULE: return new ImportModule(t);
			case ScribbleParser.PAYLOADTYPEDECL: return new DataTypeDecl(t);
			case ScribbleParser.MESSAGESIGNATUREDECL: return new MessageSigNameDecl(t);
			case ScribbleParser.GLOBALPROTOCOLDECL: return new GProtocolDecl(t);
			case ScribbleParser.GLOBALPROTOCOLDECLMODS: return new ProtocolModList(t);
			case ScribbleParser.AUX_KW: return new AuxMod(t);  // FIXME: KW being used directly
			case ScribbleParser.EXPLICIT_KW: return new ExplicitMod(t);

			case ScribbleParser.GLOBALPROTOCOLHEADER: return new GProtocolHeader(t);
			case ScribbleParser.ROLEDECLLIST: return new RoleDeclList(t);
			case ScribbleParser.ROLEDECL: return new RoleDecl(t);
			case ScribbleParser.PARAMETERDECLLIST: return new NonRoleParamDeclList(t);
			case ScribbleParser.TYPEPARAMDECL: return new TypeParamDecl(t);
			case ScribbleParser.SIGPARAMDECL: return new SigParamDecl(t);
			case ScribbleParser.GLOBALPROTOCOLDEF: return new GProtocolDef(t);
			case ScribbleParser.GLOBALPROTOCOLBLOCK: return new GProtocolBlock(t);
			case ScribbleParser.GLOBALINTERACTIONSEQUENCE: return new GInteractionSeq(t);

			case ScribbleParser.GLOBALMESSAGETRANSFER: return new GMessageTransfer(t);
			case ScribbleParser.GLOBALCONNECT: return new GConnect(t);
			case ScribbleParser.GLOBALDISCONNECT: return new GDisconnect(t);
			case ScribbleParser.GLOBALCHOICE: return new GChoice(t);
			case ScribbleParser.GLOBALRECURSION: return new GRecursion(t);
			case ScribbleParser.GLOBALCONTINUE: return new GContinue(t);
			case ScribbleParser.GLOBALDO: return new GDo(t);
			case ScribbleParser.RECURSIONVAR: return new RecVarNode(t);

			case ScribbleParser.MESSAGESIGNATURE: return new MessageSigNode(t);
			case ScribbleParser.PAYLOAD: return new PayloadElemList(t);  // N.B. UnaryPayloadElem parsed "manually" in Scribble.g
				
			case ScribbleParser.ROLEINSTANTIATIONLIST: return new RoleArgList(t);
			case ScribbleParser.ROLEINSTANTIATION: return new RoleArg(t);
			case ScribbleParser.ARGUMENTINSTANTIATIONLIST: return new NonRoleArgList(t);
			case ScribbleParser.NONROLEARG: return new NonRoleArg(t);  // Only for messagesignature -- qualifiedname (datatypenode or ambignamenode) done "manually" in scribble.g (cf. UnaryPayloadElem)

			// Special cases
			case ScribbleParser.EMPTY_OPERATOR: 
				//return new IdNode(t);  // OpNode.toName checks IdNode child text for OpNode.EMPTY_OP_ID  // CHECKME refactor?  
				return new OpNode(t);

			case ScribbleParser.QUALIFIEDNAME: return new IdNode(t);  
					// Returns an IdNode whose token is "QUALIFIEDNAME", and whose children are the IdNode elements of the qualified name
					// (Using IdNode as a "shell", but "token type" determined by t -- a bit misleading, IdNode here not an actual IDENTIFIER -- CHECKME: make a proper QUALIFIEDNAME?)
					// It is a "temporary" QUALIFIEDNAME, "internally" parsed by ScribbleParser.parsePayloadElem/parseNonRoleArg
					// This temporary IdNode is passed by $qualifiedname.tree to, e.g., parsePayloadElem(CommonTree ct)
					// The parser method takes CommonTree, that can accept IdNode
					// (E.g., good.misc.globals.gdo.Do06b)  

			default:
			{
				throw new RuntimeException("[TODO] Unknown token type (cf. ScribbleParser): " + t);
			}
		}
	}
}
