package ast;

import java.util.Map;

import ast.global.GlobalEnd;
import ast.global.GlobalRec;
import ast.global.GlobalSend;
import ast.global.GlobalSendCase;
import ast.global.GlobalType;
import ast.local.LocalBranch;
import ast.local.LocalCase;
import ast.local.LocalEnd;
import ast.local.LocalRec;
import ast.local.LocalSelect;
import ast.local.LocalType;
import ast.name.BaseType;
import ast.name.MessageLab;
import ast.name.RecVar;
import ast.name.Role;

public class AstFactory
{
	public BaseType BaseType(String name)
	{
		return new BaseType(name);
	}

	public Role Role(String name)
	{
		return new Role(name);
	}

	public MessageLab MessageLab(String name)
	{
		return new MessageLab(name);
	}

	public RecVar RecVar(String name)
	{
		return new RecVar(name);
	}
	
	public GlobalSend GlobalSend(Role src, Role dest, Map<MessageLab, GlobalSendCase> cases)
	{
		return new GlobalSend(src, dest, cases);
	}
	
	public GlobalSendCase GlobalSendCase(PayloadType pay, GlobalType body)
	{
		return new GlobalSendCase(pay, body);
	}
	
	public GlobalRec GlobalRec(RecVar recvar, GlobalType body)
	{
		return new GlobalRec(recvar, body);
	}

	public GlobalEnd GlobalEnd()
	{
		return new GlobalEnd();
	}
	
	//public LocalSelect LocalSelect(Role self, Role dest, Map<MessageLab, LocalCase> cases)
	public LocalSelect LocalSelect(Role dest, Map<MessageLab, LocalCase> cases)
	{
		//return new LocalSelect(self, dest, cases);
		return new LocalSelect(dest, cases);
	}
	
	//public LocalBranch LocalBranch(Role self, Role src, Map<MessageLab, LocalCase> cases)
	public LocalBranch LocalBranch(Role src, Map<MessageLab, LocalCase> cases)
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
	}
}
