package org.scribble.parser.scribble;

import java.util.Collections;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.scribble.ast.AstFactory;
import org.scribble.ast.AstFactoryImpl;
import org.scribble.ast.Module;
import org.scribble.parser.scribble.ast.tree.MyCommonTree;

public class ScribTreeAdaptor extends CommonTreeAdaptor
{
	private final AstFactory f = new AstFactoryImpl();
	
	@Override
	public Object create(Token payload)
	{
		if (payload == null)
		{
			//System.out.println("aaa: " + payload.getText());
			return new MyCommonTree(payload);
		}
		
		/*if (payload != null)
		{
			return new MyCommonTree(payload);
		}*/
		String lab = payload.getText();
		CommonTree empty = new CommonTree(payload);  // CommonTree constr sets this.token = payload.token
		switch (lab)
		{
			case "MODULE": return this.f.Module(empty, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
			case "MODULEDECL": return this.f.ModuleDecl(empty, null);
			case "GLOBALPROTOCOLDECL": return this.f.GProtocolDecl(empty, Collections.emptyList(), null, null);
			case "GLOBALPROTOCOLHEADER": return this.f.GProtocolHeader(empty, null, null, null);
			case "ROLEDECL": return this.f.RoleDecl(empty, null);
			case "ROLEDECLLIST": return this.f.RoleDeclList(empty, Collections.emptyList());
			case "PARAMETERDECLLIST": return this.f.NonRoleParamDeclList(empty, Collections.emptyList());
			case "GLOBALPROTOCOLDEF": return this.f.GProtocolDef(empty, null);
			case "GLOBALPROTOCOLBLOCK": return this.f.GProtocolBlock(empty, null);
			case "GLOBALINTERACTIONSEQUENCE": return this.f.GInteractionSeq(empty, Collections.emptyList());
			case "GLOBALMESSAGETRANSFER": return this.f.GMessageTransfer(empty, null, null, Collections.emptyList());
			//case "QUALIFIEDNAME": return this.f.Q
			//case "MESSAGESIGNATURE": return this.f.Me
			//case "PAYLOAD":
			/*case tmp
			case Test
			case Proto1
			case A
			case B
			case 1
			case A
			case B*/
			default: //throw new RuntimeException("Shouldn't get here: " + lab);
				return new MyCommonTree(payload);
				//return empty;
		}
	}
}
