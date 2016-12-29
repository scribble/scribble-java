package org.scribble.ext.f17.ast;

import java.util.Map;

import org.scribble.ext.f17.ast.global.F17GChoice;
import org.scribble.ext.f17.ast.global.F17GEnd;
import org.scribble.ext.f17.ast.global.F17GRec;
import org.scribble.ext.f17.ast.global.F17GRecVar;
import org.scribble.ext.f17.ast.global.F17GType;
import org.scribble.ext.f17.ast.global.action.F17GAction;
import org.scribble.ext.f17.ast.global.action.F17GConnect;
import org.scribble.ext.f17.ast.global.action.F17GDisconnect;
import org.scribble.ext.f17.ast.global.action.F17GMessageTransfer;
import org.scribble.ext.f17.ast.local.F17LChoice;
import org.scribble.ext.f17.ast.local.F17LEnd;
import org.scribble.ext.f17.ast.local.F17LRec;
import org.scribble.ext.f17.ast.local.F17LRecVar;
import org.scribble.ext.f17.ast.local.F17LType;
import org.scribble.ext.f17.ast.local.action.F17LAccept;
import org.scribble.ext.f17.ast.local.action.F17LAction;
import org.scribble.ext.f17.ast.local.action.F17LConnect;
import org.scribble.ext.f17.ast.local.action.F17LDisconnect;
import org.scribble.ext.f17.ast.local.action.F17LReceive;
import org.scribble.ext.f17.ast.local.action.F17LSend;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;


public class F17AstFactory
{
	public static final F17AstFactory FACTORY = new F17AstFactory();
	
	private F17AstFactory()
	{
		
	}
	
	public F17GChoice GChoice(Map<F17GAction, F17GType> cases)
	{
		return new F17GChoice(cases);
	}
	
	public F17GMessageTransfer GMessageTransfer(Role src, Role dest, Op op, Payload pay)
	{
		return new F17GMessageTransfer(src, dest, op, pay);
	}
	
	public F17GConnect GConnect(Role src, Role dest, Op op, Payload pay)
	{
		return new F17GConnect(src, dest, op, pay);
	}
	
	public F17GDisconnect GDisconnect(Role src, Role dest)
	{
		return new F17GDisconnect(src, dest);
	}
	
	public F17GRec GRec(RecVar recvar, F17GType body)
	{
		return new F17GRec(recvar, body);
	}
	
	public F17GRecVar GRecVar(RecVar var)
	{
		return new F17GRecVar(var);
	}

	public F17GEnd GEnd()
	{
		//return new F17GEnd();
		return F17GEnd.END;
	}

	public F17LChoice LChoice(Map<F17LAction, F17LType> cases)
	{
		return new F17LChoice(cases);
	}
	
	public F17LSend LSend(Role self, Role peer, Op op, Payload pay)
	{
		return new F17LSend(self, peer, op, pay);
	}
	
	public F17LReceive LReceive(Role self, Role peer, Op op, Payload pay)
	{
		return new F17LReceive(self, peer, op, pay);
	}
	
	public F17LConnect LConnect(Role self, Role peer, Op op, Payload pay)
	{
		return new F17LConnect(self, peer, op, pay);
	}
	
	public F17LAccept LAccept(Role self, Role peer, Op op, Payload pay)
	{
		return new F17LAccept(self, peer, op, pay);
	}
	
	public F17LDisconnect LDisconnect(Role self, Role peer)
	{
		return new F17LDisconnect(self, peer);
	}
	
	public F17LRec LRec(RecVar recvar, F17LType body)
	{
		return new F17LRec(recvar, body);
	}
	
	public F17LRecVar LRecVar(RecVar var)
	{
		return new F17LRecVar(var);
	}

	public F17LEnd LEnd()
	{
		//return new F17LEnd();
		return F17LEnd.END;
	}
}
