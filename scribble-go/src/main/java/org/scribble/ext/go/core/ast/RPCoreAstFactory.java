package org.scribble.ext.go.core.ast;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.ext.go.core.ast.global.RPCoreGActionKind;
import org.scribble.ext.go.core.ast.global.RPCoreGChoice;
import org.scribble.ext.go.core.ast.global.RPCoreGCont;
import org.scribble.ext.go.core.ast.global.RPCoreGEnd;
import org.scribble.ext.go.core.ast.global.RPCoreGForeach;
import org.scribble.ext.go.core.ast.global.RPCoreGRec;
import org.scribble.ext.go.core.ast.global.RPCoreGRecVar;
import org.scribble.ext.go.core.ast.global.RPCoreGType;
import org.scribble.ext.go.core.ast.local.RPCoreLActionKind;
import org.scribble.ext.go.core.ast.local.RPCoreLCont;
import org.scribble.ext.go.core.ast.local.RPCoreLCrossChoice;
import org.scribble.ext.go.core.ast.local.RPCoreLEnd;
import org.scribble.ext.go.core.ast.local.RPCoreLForeach;
import org.scribble.ext.go.core.ast.local.RPCoreLRec;
import org.scribble.ext.go.core.ast.local.RPCoreLRecVar;
import org.scribble.ext.go.core.ast.local.RPCoreLType;
import org.scribble.ext.go.core.type.RPAnnotatedInterval;
import org.scribble.ext.go.core.type.RPIndexedRole;
import org.scribble.ext.go.core.type.RPInterval;
import org.scribble.type.Message;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;


// For "core" stuff -- not "full" AF (Antlr) AST
public class RPCoreAstFactory
{
	public RPCoreAstFactory()
	{
		
	}
	
	public RPIndexedRole ParamRole(String name, RPInterval range)  // ParamRange not "ast", so not made by af
	{
		return new RPIndexedRole(name, Stream.of(range).collect(Collectors.toSet()));
	}

	// Pre: not null -- ?
	/*public RPCoreMessage ParamCoreAction(Op op, Payload pay)
	{
		return new RPCoreMessage(op, pay);
	}*/
	
	public RPCoreGChoice ParamCoreGChoice(RPIndexedRole src, RPCoreGActionKind kind, RPIndexedRole dest, //LinkedHashMap<RPCoreMessage, RPCoreGType> cases)
			LinkedHashMap<Message, RPCoreGType> cases)
	{
		return new RPCoreGChoice(src, kind, dest, cases);
	}
	
	/*public RPCoreGMultiChoices ParamCoreGMultiChoices(RPIndexedRole src, RPIndexVar var,
			RPIndexedRole dest, List<RPCoreMessage> cases, RPCoreGType cont)
	{
		return new RPCoreGMultiChoices(src, var, dest, cases, cont);
	}*/
	
	public RPCoreGRec ParamCoreGRec(RecVar recvar, RPCoreGType body)
	{
		return new RPCoreGRec(recvar, body);
	}
	
	public RPCoreGRecVar ParamCoreGRecVar(RecVar recvar)
	{
		return new RPCoreGRecVar(recvar);
	}

	public RPCoreGEnd ParamCoreGEnd()
	{
		return RPCoreGEnd.END;
	}

	public RPCoreGCont ParamCoreGCont()
	{
		return RPCoreGCont.CONT;
	}

	public RPCoreGForeach RPCoreGForeach(//Role role, RPForeachVar var, RPIndexExpr start, RPIndexExpr end, 
			Set<Role> roles, Set<RPAnnotatedInterval> ivals,
			RPCoreGType body, RPCoreGType seq)
	{
		return new RPCoreGForeach(//role, var, start, end, 
				roles, ivals,
				body, seq);
	}

	public RPCoreLCrossChoice ParamCoreLCrossChoice(RPIndexedRole role, RPCoreLActionKind kind,
			//LinkedHashMap<RPCoreMessage, RPCoreLType> cases)
			LinkedHashMap<Message, RPCoreLType> cases)
	{
		return new RPCoreLCrossChoice(role, kind, cases);
	}

	/*public RPCoreLDotChoice ParamCoreLDotChoice(RPIndexedRole role, RPIndexExpr offset, RPCoreLActionKind kind,
			LinkedHashMap<RPCoreMessage, RPCoreLType> cases)
	{
		return new RPCoreLDotChoice(role, offset, kind, cases);
	}
	
	public RPCoreLMultiChoices ParamCoreLMultiChoices(RPIndexedRole role, RPIndexVar var,
			List<RPCoreMessage> cases, RPCoreLType cont)
	{
		return new RPCoreLMultiChoices(role, var, cases, cont);
	}*/
	
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
	
	public RPCoreLRec ParamCoreLRec(RecVar recvar, RPCoreLType body)
	{
		return new RPCoreLRec(recvar, body);
	}
	
	public RPCoreLRecVar ParamCoreLRecVar(RecVar recvar)
	{
		return new RPCoreLRecVar(recvar);
	}

	public RPCoreLEnd ParamCoreLEnd()
	{
		return RPCoreLEnd.END;
	}

	public RPCoreLCont ParamCoreLCont()
	{
		return RPCoreLCont.CONT;
	}

	public RPCoreLForeach RPCoreLForeach(//Role role, RPForeachVar var, RPIndexExpr start, RPIndexExpr end, 
			Set<Role> roles, Set<RPAnnotatedInterval> ivals,
			RPCoreLType body, RPCoreLType seq)
	{
		return new RPCoreLForeach(//role, var, start, end, 
				roles, ivals,
				body, seq);
	}
}
