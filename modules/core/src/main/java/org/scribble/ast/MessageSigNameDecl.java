package org.scribble.ast;

import org.scribble.ast.name.qualified.MemberNameNode;
import org.scribble.ast.name.qualified.MessageSigNameNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.SigKind;
import org.scribble.sesstype.name.MessageSigName;
import org.scribble.sesstype.name.ModuleName;

public class MessageSigNameDecl extends NonProtocolDecl<SigKind>
{
	public MessageSigNameDecl(String schema, String extName, String source, MessageSigNameNode name)
	{
		super(schema, extName, source, name);
	}

	@Override
	protected MessageSigNameDecl copy()
	{
		return new MessageSigNameDecl(this.schema, this.extName, this.source, getNameNode());
	}
	
	@Override
	public MessageSigNameDecl clone()
	{
		MessageSigNameNode name = (MessageSigNameNode) this.name.clone();
		return AstFactoryImpl.FACTORY.MessageSigNameDecl(this.schema, this.extName, this.source, name);
	}

	@Override
	public MessageSigNameDecl reconstruct(String schema, String extName, String source, MemberNameNode<SigKind> name)
	{
		ScribDel del = del();
		MessageSigNameDecl msnd = new MessageSigNameDecl(schema, extName, source, (MessageSigNameNode) name);
		msnd = (MessageSigNameDecl) msnd.del(del);
		return msnd;
	}
	
	@Override
	public boolean isMessageSigNameDecl()
	{
		return true;
	}

	@Override
	public MessageSigNameNode getNameNode()
	{
		return (MessageSigNameNode) super.getNameNode();
	}

	@Override
	public MessageSigName getDeclName()
	{
		return (MessageSigName) super.getDeclName();
	}

	@Override
	public MessageSigName getFullMemberName(Module mod)
	{
		ModuleName fullmodname = mod.getFullModuleName();
		return new MessageSigName(fullmodname, getDeclName());
	}

	@Override
	public String toString()
	{
		return Constants.SIG_KW + " <" + this.schema + "> \"" + this.extName
				+ "\" " + Constants.FROM_KW + " \"" + this.source + "\" "
				+ Constants.AS_KW + " " + this.name + ";";
	}
}
