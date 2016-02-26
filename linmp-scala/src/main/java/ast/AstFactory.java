package ast;

import java.util.Map;

import ast.global.GlobalEnd;
import ast.global.GlobalRec;
import ast.global.GlobalSend;
import ast.global.GlobalSendCase;
import ast.global.GlobalType;
import ast.name.MessageLab;
import ast.name.RecVar;
import ast.name.Role;

public class AstFactory
{
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
	
	public GlobalSendCase GlobalSendCase(String data, GlobalType body)
	{
		return new GlobalSendCase(data, body);
	}
	
	public GlobalRec GlobalRec(RecVar recvar, GlobalType body)
	{
		return new GlobalRec(recvar, body);
	}

	public GlobalEnd GlobalEnd()
	{
		return new GlobalEnd();
	}
}
