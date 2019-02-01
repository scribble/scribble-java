package org.scribble.parser.scribble;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.scribble.ast.AstFactory;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Module;
import org.scribble.ast.ModuleDecl;
import org.scribble.ast.NonRoleParamDeclList;
import org.scribble.ast.RoleDecl;
import org.scribble.ast.RoleDeclList;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.global.GProtocolBlock;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.global.GProtocolDef;
import org.scribble.ast.global.GProtocolHeader;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.ast.name.qualified.ModuleNameNode;
import org.scribble.parser.scribble.ast.tree.MyCommonTree;

// get/setType don't seem to be really used
public class ScribTreeAdaptor extends CommonTreeAdaptor
{
	//private final AstFactory f = new AstFactoryImpl();
	
	@Override
	public Object create(Token t)
	{
		/*if (payload == null)
		{
			//System.out.println("aaa: " + payload.getText());
			return new MyCommonTree(payload);
		}*/
		
		String lab = "";
		if (t != null)
		{
			//return new MyCommonTree(payload);
			lab = t.getText();
		}
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

			case "GLOBALPROTOCOLDECL": //return this.f.GProtocolDecl(empty, Collections.emptyList(), null, null);
				return new GProtocolDecl(t);
			case "GLOBALPROTOCOLHEADER": //return this.f.GProtocolHeader(empty, null, null, null);
				return new GProtocolHeader(t);
			case "ROLEDECL": //return this.f.RoleDecl(empty, null);
				return new RoleDecl(t);
			case "ROLEDECLLIST": //return this.f.RoleDeclList(empty, Collections.emptyList());
				return new RoleDeclList(t);
			case "PARAMETERDECLLIST": //return this.f.NonRoleParamDeclList(empty, Collections.emptyList());
				return new NonRoleParamDeclList(t);
			case "GLOBALPROTOCOLDEF": //return this.f.GProtocolDef(empty, null);
				return new GProtocolDef(t);
			case "GLOBALPROTOCOLBLOCK": //return this.f.GProtocolBlock(empty, null);
				return new GProtocolBlock(t);

			case "GLOBALINTERACTIONSEQUENCE": //return this.f.GInteractionSeq(empty, Collections.emptyList());
				return new GInteractionSeq(t);
			case "GLOBALMESSAGETRANSFER": //return this.f.GMessageTransfer(empty, null, null, Collections.emptyList());
				return new GMessageTransfer(t);
			case "MESSAGESIGNATURE": //return this.f.Me
				return new MessageSigNameNode(t);
			case "PAYLOAD":
				
				HERE:  check MyCommonTree instantiation
				
				return new MyCommonTree(t);  // FIXME: placeholder for deleg, qualified or ambig
			//case "QUALIFIEDNAME": //return this.f.Q
			/*case tmp
			case Test
			case Proto1
			case A
			case B
			case 1
			case A
			case B*/
			case "":  // CHECKME ?
				return new MyCommonTree(t);
			default:
				//throw new RuntimeException("Shouldn't get here: " + lab + " ,, " + lab.length());
				return new MyCommonTree(t);  // FIXME: rename ID
				//return empty;
		}
	}
}
