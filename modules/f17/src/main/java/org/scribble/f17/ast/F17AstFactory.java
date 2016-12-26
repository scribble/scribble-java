package org.scribble.f17.ast;

import java.util.Map;

import org.scribble.f17.ast.global.F17GChoice;
import org.scribble.f17.ast.global.F17GEnd;
import org.scribble.f17.ast.global.F17GRec;
import org.scribble.f17.ast.global.F17GRecVar;
import org.scribble.f17.ast.global.F17GType;
import org.scribble.f17.ast.global.action.F17GAction;
import org.scribble.f17.ast.global.action.F17GMessageTransfer;
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
	
	/*//public LocalSelect LocalSelect(Role self, Role dest, Map<MessageLab, LocalCase> cases)
	public LocalSelect LocalSelect(Role dest, Map<Label, LocalCase> cases)
	{
		//return new LocalSelect(self, dest, cases);
		return new LocalSelect(dest, cases);
	}
	
	//public LocalBranch LocalBranch(Role self, Role src, Map<MessageLab, LocalCase> cases)
	public LocalBranch LocalBranch(Role src, Map<Label, LocalCase> cases)
	{
		//return new LocalBranch(self, src, cases);
		return new LocalBranch(src, cases);
	}
	
	public LocalCase LocalCase(PayloadType pay, LocalType body)
	{
		return new LocalCase(pay, body);
	}
	
	//public LocalRec LocalRec(Role self, RecVar recvar, LocalType body)
	public LocalRec LocalRec(RecVar recvar, LocalType body)
	{
		//return new LocalRec(self, recvar, body);
		return new LocalRec(recvar, body);
	}

	//public LocalEnd LocalEnd(Role self)
	public LocalEnd LocalEnd()
	{
		//return new LocalEnd(self);
		return new LocalEnd();
	}*/
}
