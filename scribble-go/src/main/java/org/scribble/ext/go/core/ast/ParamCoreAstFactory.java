package org.scribble.ext.go.core.ast;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.core.ast.global.ParamCoreGActionKind;
import org.scribble.ext.go.core.ast.global.ParamCoreGChoice;
import org.scribble.ext.go.core.ast.global.ParamCoreGEnd;
import org.scribble.ext.go.core.ast.global.ParamCoreGRec;
import org.scribble.ext.go.core.ast.global.ParamCoreGRecVar;
import org.scribble.ext.go.core.ast.global.ParamCoreGType;
import org.scribble.ext.go.core.ast.local.ParamCoreLActionKind;
import org.scribble.ext.go.core.ast.local.ParamCoreLChoice;
import org.scribble.ext.go.core.ast.local.ParamCoreLEnd;
import org.scribble.ext.go.core.ast.local.ParamCoreLRec;
import org.scribble.ext.go.core.ast.local.ParamCoreLRecVar;
import org.scribble.ext.go.core.ast.local.ParamCoreLType;
import org.scribble.ext.go.core.type.ParamRange;
import org.scribble.ext.go.core.type.ParamRole;
import org.scribble.type.Payload;
import org.scribble.type.name.Op;
import org.scribble.type.name.RecVar;


public class ParamCoreAstFactory
{
	public ParamCoreAstFactory()
	{
		
	}
	
	public ParamRole ParamRole(String name, ParamRange range)  // ParamRange not "ast", so not made by af
	{
		return new ParamRole(name, Stream.of(range).collect(Collectors.toSet()));
	}

	// Pre: not null
	public ParamCoreMessage ParamCoreAction(Op op, Payload pay)
	{
		return new ParamCoreMessage(op, pay);
	}
	
	public ParamCoreGChoice ParamCoreGChoice(ParamRole src, ParamCoreGActionKind kind, ParamRole dest, Map<ParamCoreMessage, ParamCoreGType> cases)
	{
		return new ParamCoreGChoice(src, kind, dest, cases);
	}
	
	public ParamCoreGRec ParamCoreGRec(RecVar recvar, ParamCoreGType body)
	{
		return new ParamCoreGRec(recvar, body);
	}
	
	public ParamCoreGRecVar ParamCoreGRecVar(RecVar recvar)
	{
		return new ParamCoreGRecVar(recvar);
	}

	public ParamCoreGEnd ParamCoreGEnd()
	{
		return ParamCoreGEnd.END;
	}

	public ParamCoreLChoice ParamCoreLChoice(ParamRole role, ParamCoreLActionKind kind, Map<ParamCoreMessage, ParamCoreLType> cases)
	{
		return new ParamCoreLChoice(role, kind, cases);
	}
	
	/*public ParamCoreLSend LSend(Role self, Role peer, Op op, Payload pay)
	{
		return new ParamCoreLSend(self, peer, op, pay);
	}
	
	public ParamCoreLReceive LReceive(Role self, Role peer, Op op, Payload pay)
	{
		return new ParamCoreLReceive(self, peer, op, pay);
	}
	
	public ParamCoreLConnect LConnect(Role self, Role peer, Op op, Payload pay)
	{
		return new ParamCoreLConnect(self, peer, op, pay);
	}
	
	public ParamCoreLAccept LAccept(Role self, Role peer, Op op, Payload pay)
	{
		return new ParamCoreLAccept(self, peer, op, pay);
	}*/
	
	/*public ParamCoreLDisconnect LDisconnect(Role self, Role peer)
	{
		return new ParamCoreLDisconnect(self, peer);
	}*/
	
	public ParamCoreLRec ParamCoreLRec(RecVar recvar, ParamCoreLType body)
	{
		return new ParamCoreLRec(recvar, body);
	}
	
	public ParamCoreLRecVar ParamCoreLRecVar(RecVar recvar)
	{
		return new ParamCoreLRecVar(recvar);
	}

	public ParamCoreLEnd ParamCoreLEnd()
	{
		return ParamCoreLEnd.END;
	}
}
