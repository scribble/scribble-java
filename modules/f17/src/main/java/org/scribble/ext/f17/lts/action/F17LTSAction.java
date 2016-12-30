package org.scribble.ext.f17.lts.action;

import org.scribble.ext.f17.ast.local.action.F17LAccept;
import org.scribble.ext.f17.ast.local.action.F17LAction;
import org.scribble.ext.f17.ast.local.action.F17LConnect;
import org.scribble.ext.f17.ast.local.action.F17LDisconnect;
import org.scribble.ext.f17.ast.local.action.F17LReceive;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.model.MAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Op;

// Hacky: adaptor for F17LAction -- F17L includes self, but Global vs. "local" kinds
public class F17LTSAction extends MAction<Global>
{
	public final F17LAction action;
	
	public F17LTSAction(F17LAction action)
	{
		super(action.peer, getMessageId(action), getPayload(action));
		this.action = action;
	}

	@Override
	protected String getCommSymbol()
	{
		if (this.action instanceof F17LSend)
		{
			return "!";
		}
		else if (this.action instanceof F17LReceive)
		{
			return "?";  
		}
		else if (this.action instanceof F17LConnect)
		{
			return "!!";  
		}
		else if (this.action instanceof F17LAccept)
		{
			return "??";  
		}
		else if (this.action instanceof F17LDisconnect)
		{
			return "#";  
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + this.action);
		}
	}

	/*
	@Override
	public int hashCode()
	{
		int hash = 2683;
		hash = 31 * hash + this.action.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o) 
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof F17Action))
		{
			return false;
		}
		F17Action them = (F17Action) o;
		return them.canEqual(this) && this.action.equals(them.action);
	}
	*/

	@Override
	public boolean canEqual(Object o)
	{
		if (!(o instanceof F17LTSAction))
		{
			return false;
		}
		F17LTSAction them = (F17LTSAction) o;
		if (this.action instanceof F17LSend)
		{
			return them.action instanceof F17LSend;
		}
		else if (this.action instanceof F17LReceive)
		{
			return them.action instanceof F17LSend;  
		}
		else if (this.action instanceof F17LConnect)
		{
			return them.action instanceof F17LConnect;  
		}
		else if (this.action instanceof F17LAccept)
		{
			return them.action instanceof F17LAccept;  
		}
		else if (this.action instanceof F17LDisconnect)
		{
			return them.action instanceof F17LDisconnect;  
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + this.action);
		}
	}
	
	private static MessageId<?> getMessageId(F17LAction a)
	{
		if (a instanceof F17LSend)
		{
			return ((F17LSend) a).op;  // TODO: MessageSigName
		}
		else if (a instanceof F17LReceive)
		{
			return ((F17LReceive) a).op;  
		}
		else if (a instanceof F17LConnect)
		{
			return ((F17LConnect) a).op;  
		}
		else if (a instanceof F17LAccept)
		{
			return ((F17LAccept) a).op;  
		}
		else if (a instanceof F17LDisconnect)
		{
			return Op.EMPTY_OPERATOR;  // cf. GDisconnect.UNIT_MESSAGE_SIG_NODE 
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + a);
		}
	}
	
	private static Payload getPayload(F17LAction a)
	{
		if (a instanceof F17LSend)
		{
			return ((F17LSend) a).pay;  // TODO: MessageSigName
		}
		else if (a instanceof F17LReceive)
		{
			return ((F17LReceive) a).pay;  
		}
		else if (a instanceof F17LConnect)
		{
			return ((F17LConnect) a).pay;  
		}
		else if (a instanceof F17LAccept)
		{
			return ((F17LAccept) a).pay;  
		}
		else if (a instanceof F17LDisconnect)
		{
			return Payload.EMPTY_PAYLOAD;  // cf. GDisconnect.UNIT_MESSAGE_SIG_NODE   
		}
		else
		{
			throw new RuntimeException("[f17] Shouldn't get in here: " + a);
		}
	}
}
