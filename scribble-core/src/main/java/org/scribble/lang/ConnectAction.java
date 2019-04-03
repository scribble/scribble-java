package org.scribble.lang;

import java.util.LinkedList;
import java.util.List;

import org.scribble.type.Message;
import org.scribble.type.MessageSig;
import org.scribble.type.Payload;
import org.scribble.type.kind.ProtocolKind;
import org.scribble.type.name.DataType;
import org.scribble.type.name.MemberName;
import org.scribble.type.name.MessageSigName;
import org.scribble.type.name.PayloadElemType;
import org.scribble.type.name.Role;

public abstract class ConnectAction<K extends ProtocolKind>
		extends DirectedInteraction<K>
{
	public ConnectAction(org.scribble.ast.DirectedInteraction<K> source,  // BaseInteractionNode not ideal
			Role src, Message msg, Role dst)
	{
		super(source, src, msg, dst);
	}

	@Override
	public List<MemberName<?>> getNonProtoDependencies()
	{
		List<MemberName<?>> res = new LinkedList<>();
		if (this.msg.isMessageSigName())
		{
			res.add((MessageSigName) this.msg);
		}
		else //if (this.msg.isMessageSig)
		{
			Payload pay = ((MessageSig) this.msg).payload;
			for (PayloadElemType<?> p : pay.elems)
			{
				if (p.isDataType())
				{
					res.add((DataType) p);
				}
				else if (p.isGDelegationType())
				{
					throw new RuntimeException("Shouldn't get in here: " + this);
				}
				else
				{
					throw new RuntimeException("[TODO]: " + this);
				}
			}
		}
		return res;
	}
}
