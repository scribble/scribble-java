package org.scribble.parser.scribble;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.scribble.ast.DataTypeDecl;
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

// get/setType don't seem to be really used
public class ScribTreeAdaptor extends CommonTreeAdaptor
{
	//private final AstFactory f = new AstFactoryImpl();
	
	// Generated parser seems to use nil to create "blank" nodes and then "fill them in"
	@Override
	public Object nil()
	{
		return new ScribNil();
	}

	@Override
	public Object create(Token t)
	{
		/*if (payload == null)
		{
			//System.out.println("aaa: " + payload.getText());
			return new MyCommonTree(payload);
		}*/
		
		if (t == null)  // E.g. nil() -- CHECKME: what are the nils being made for?
		{
			//return super.create(null);
			//return nil();
			throw new RuntimeException("Shouldn't get in here: ");  // null Token only for nil 
		}
		
		/*String lab = "";
		if (t != null)
		{
			//return new MyCommonTree(payload);
			lab = t.getText();
		}*/
		String lab = t.getText();
		//CommonTree empty = new CommonTree(t);  // CommonTree constr sets this.token = payload.token
		switch (lab)
		{
			case "MODULE": //return this.f.Module(empty, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
				return new Module(t);
			case "MODULEDECL": //return this.f.ModuleDecl(empty, null);
				return new ModuleDecl(t);
			case "MODULENAME": //return this.f.Q
				//return new ModuleNameNode(t); 
				return new ModuleNameNode(t);  /*.. HERE ambigname -- or qualifiedname? (ambigname currently singleton name)
						-- or modulename just as identifiers? -- ANTLR uses every (leaf) token as singleton node
								-- AntlrTokenTree? -- i.e. CommonTree (wrapper for Token)*/
			case "PAYLOADTYPEDECL":
				return new DataTypeDecl(t);
			case "MESSAGESIGNATUREDECL":
				return new MessageSigNameDecl(t);
			case "TYPENAME":
				return new DataTypeNode(t);
			case "SIGNAME":
				return new MessageSigNameNode(t);

			case "GLOBALPROTOCOLDECL": //return this.f.GProtocolDecl(empty, Collections.emptyList(), null, null);
				return new GProtocolDecl(t);
			case "GLOBALPROTOCOLDECLMODS":
				return new ProtocolModList(t);
			case "GLOBALPROTOCOLHEADER": //return this.f.GProtocolHeader(empty, null, null, null);
				return new GProtocolHeader(t);
			case "GPROTOCOLNAME":
				return new GProtocolNameNode(t);
			case "ROLEDECLLIST": //return this.f.RoleDeclList(empty, Collections.emptyList());
				return new RoleDeclList(t);
			case "ROLEDECL": //return this.f.RoleDecl(empty, null);
				return new RoleDecl(t);
			case "ROLENAME":
				return new RoleNode(t);
			case "PARAMETERDECLLIST": //return this.f.NonRoleParamDeclList(empty, Collections.emptyList());
				return new NonRoleParamDeclList(t);
			case "TYPEPARAMDECL":
				return new TypeParamDecl(t);
			case "TYPEPARAMNAME":
				return new TypeParamNode(t);
			case "SIGPARAMDECL":
				return new SigParamDecl(t);
			case "SIGPARAMNAME":
				return new SigParamNode(t);
			case "GLOBALPROTOCOLDEF": //return this.f.GProtocolDef(empty, null);
				return new GProtocolDef(t);
			case "GLOBALPROTOCOLBLOCK": //return this.f.GProtocolBlock(empty, null);
				return new GProtocolBlock(t);
			case "GLOBALINTERACTIONSEQUENCE": //return this.f.GInteractionSeq(empty, Collections.emptyList());
				return new GInteractionSeq(t);

			case "GLOBALMESSAGETRANSFER": //return this.f.GMessageTransfer(empty, null, null, Collections.emptyList());
				return new GMessageTransfer(t);
			case "GLOBALCHOICE":
				return new GChoice(t);
			case "GLOBALRECURSION":
				return new GRecursion(t);
			case "GLOBALCONTINUE":
				return new GContinue(t);
			case "GLOBALDO":
				return new GDo(t);
			case "RECURSIONVAR":
				return new RecVarNode(t);

			case "MESSAGESIGNATURE": //return this.f.Me
				return new MessageSigNode(t);
			case "OPNAME":
				return new OpNode(t);
			case "PAYLOAD":
				return new PayloadElemList(t);
				// N.N. UnaryPayloadElem parsed "manually" in Scribble.g
				
			case "ROLEINSTANTIATIONLIST":
				return new RoleArgList(t);
			case "ROLEINSTANTIATION":
				return new RoleArg(t);
			case "ARGUMENTINSTANTIATIONLIST":
				return new NonRoleArgList(t);
			case "NONROLEARG":  // Only for messagesignature -- qualifiedname (datatypenode or ambignamenode) done "manually" in scribble.g (cf. UnaryPayloadElem)
				return new NonRoleArg(t);

				//return new MyCommonTree(t);  // FIXME: placeholder for deleg, qualified or ambig
			//case "QUALIFIEDNAME": //return this.f.Q
			/*case tmp
			case Test
			case Proto1
			case A
			case B
			case 1
			case A
			case B*/

			//case "":  // CHECKME ?  nil? "If you want a flat tree (a list)"
				/*MyCommonTree x = new MyCommonTree(t);
				System.out.println("222: " + t + " ,, " + x);
				return x;*/
				//return nil();  // CHECKME
			case "AMBIGUOUSNAME":
				return new AmbigNameNode(t);
			default:
				
				//System.out.println("aaa1: " + lab);   // FIXME: QUALIFIEDNAME (e.g., good.misc.globals.gdo.Do06b)  // CHECKME: UNARYPAYLOADELEM?
				
				//throw new RuntimeException("Shouldn't get here: " + lab + " ,, " + lab.length());
				//return new MyCommonTree(t);
				return new IdNode(t);  // FIXME: currently all name "leaf" nodes are there, so not ambig, more like ID
				//return empty;
		}
	}
}
