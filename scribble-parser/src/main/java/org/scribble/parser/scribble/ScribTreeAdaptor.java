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
import org.scribble.ast.SigParamDecl;
import org.scribble.ast.TypeParamDecl;
import org.scribble.ast.global.GChoice;
import org.scribble.ast.global.GContinue;
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

	@Override
	public Object create(Token t)
	{
		String tname = t.getText();
		
		/*  // CHECKME: use a naming convention between Token and AST class names for reflection?
		try
		{
			Constructor<? extends AstVisitor> cons = c.get Constructor(Job.class);
			for (ModuleName modname : modnames)
			{
				AstVisitor nv = cons.newInstance(this);
				runVisitorOnModule(modname, nv);
			}
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}*/
		
		switch (tname)
		{
			case "MODULE": return new Module(t);
			case "MODULEDECL": return new ModuleDecl(t);
			case "MODULENAME": return new ModuleNameNode(t);
			case "IMPORTMODULE": return new ImportModule(t);
			case "PAYLOADTYPEDECL": return new DataTypeDecl(t);
			case "MESSAGESIGNATUREDECL": return new MessageSigNameDecl(t);
			case "TYPENAME": return new DataTypeNode(t);
			case "SIGNAME": return new MessageSigNameNode(t);
			case "GLOBALPROTOCOLDECL": return new GProtocolDecl(t);
			case "GLOBALPROTOCOLDECLMODS": return new ProtocolModList(t);
			case "aux": return new AuxMod(t);
			case "explicit": return new ExplicitMod(t);

			case "GLOBALPROTOCOLHEADER": return new GProtocolHeader(t);
			case "GPROTOCOLNAME": return new GProtocolNameNode(t);
			case "ROLEDECLLIST": return new RoleDeclList(t);
			case "ROLEDECL": return new RoleDecl(t);
			case "ROLENAME": return new RoleNode(t);
			case "PARAMETERDECLLIST": return new NonRoleParamDeclList(t);
			case "TYPEPARAMDECL": return new TypeParamDecl(t);
			case "TYPEPARAMNAME": return new TypeParamNode(t);
			case "SIGPARAMDECL": return new SigParamDecl(t);
			case "SIGPARAMNAME": return new SigParamNode(t);
			case "GLOBALPROTOCOLDEF": return new GProtocolDef(t);
			case "GLOBALPROTOCOLBLOCK": return new GProtocolBlock(t);
			case "GLOBALINTERACTIONSEQUENCE": return new GInteractionSeq(t);

			case "GLOBALMESSAGETRANSFER": return new GMessageTransfer(t);
			case "GLOBALCHOICE": return new GChoice(t);
			case "GLOBALRECURSION": return new GRecursion(t);
			case "GLOBALCONTINUE": return new GContinue(t);
			case "GLOBALDO": return new GDo(t);
			case "RECURSIONVAR": return new RecVarNode(t);

			case "MESSAGESIGNATURE": return new MessageSigNode(t);
			case "OPNAME": return new OpNode(t);
			case "PAYLOAD": return new PayloadElemList(t);  // N.B. UnaryPayloadElem parsed "manually" in Scribble.g
				
			case "ROLEINSTANTIATIONLIST": return new RoleArgList(t);
			case "ROLEINSTANTIATION": return new RoleArg(t);
			case "ARGUMENTINSTANTIATIONLIST": return new NonRoleArgList(t);
			case "NONROLEARG": return new NonRoleArg(t);  // Only for messagesignature -- qualifiedname (datatypenode or ambignamenode) done "manually" in scribble.g (cf. UnaryPayloadElem)
			case "AMBIGUOUSNAME": return new AmbigNameNode(t);

			default:
			{
				//CHECKME: QUALIFIEDNAME (e.g., good.misc.globals.gdo.Do06b)  
				//CHECKME: UNARYPAYLOADELEM?
				if (TOKEN_NAMES.contains(tname))
				{
					if (!(tname.equals(OpNode.EMPTY_OP_ID)  // TODO: refactor empty op hack
							|| tname.equals("QUALIFIEDNAME")))  
									// Temporary QUALIFIEDNAME created, then internally parsed by ScribbleParser.parsePayloadElem/parseNonRoleArg
					{
						throw new RuntimeException("[TODO] Unhandled token type: " + tname);
					}
				}
				return new IdNode(t);
			}
		}
	}
}
