package org.scribble.f17.ast;

import java.util.Map;

import org.scribble.f17.ast.global.F17GChoice;
import org.scribble.f17.ast.global.F17GEnd;
import org.scribble.f17.ast.global.F17GRec;
import org.scribble.f17.ast.global.F17GRecVar;
import org.scribble.f17.ast.global.F17GType;
import org.scribble.f17.ast.global.action.F17GAction;
import org.scribble.f17.ast.global.action.F17GMessageTransfer;
import org.scribble.f17.ast.local.F17LChoice;
import org.scribble.f17.ast.local.F17LEnd;
import org.scribble.f17.ast.local.F17LRec;
import org.scribble.f17.ast.local.F17LRecVar;
import org.scribble.f17.ast.local.F17LType;
import org.scribble.f17.ast.local.action.F17LAction;
import org.scribble.f17.ast.local.action.F17LReceive;
import org.scribble.f17.ast.local.action.F17LSend;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;


public class F17AstFactory
{
	public F17GChoice GChoice(Map<F17GAction, F17GType> cases)
	{
		return new F17GChoice(cases);
	}
	
	public F17GMessageTransfer GMessageTransfer(Role src, Role dest, Op op, Payload pay)
	{
		return new F17GMessageTransfer(src, dest, op, pay);
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
		return new F17GEnd();
	}

	public F17LChoice LChoice(Map<F17LAction, F17LType> cases)
	{
		return new F17LChoice(cases);
	}
	
	public F17LSend LSend(Role self, Role dest, Op op, Payload pay)
	{
		return new F17LSend(self, dest, op, pay);
	}
	
	public F17LReceive LReceive(Role self, Role dest, Op op, Payload pay)
	{
		return new F17LReceive(self, dest, op, pay);
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
		return new F17LEnd();
	}
}
