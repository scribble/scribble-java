package org.scribble.core.visit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.scribble.core.type.kind.NonRoleParamKind;
import org.scribble.core.type.kind.ProtocolKind;
import org.scribble.core.type.name.DataType;
import org.scribble.core.type.name.MemberName;
import org.scribble.core.type.name.MessageSigName;
import org.scribble.core.type.name.ProtocolName;
import org.scribble.core.type.name.Role;
import org.scribble.core.type.session.Arg;
import org.scribble.core.type.session.Choice;
import org.scribble.core.type.session.DirectedInteraction;
import org.scribble.core.type.session.DisconnectAction;
import org.scribble.core.type.session.Do;
import org.scribble.core.type.session.Message;
import org.scribble.core.type.session.SType;
import org.scribble.core.type.session.Seq;

public class Substitutor<K extends ProtocolKind, B extends Seq<K, B>>
		extends STypeVisitor<K, B>
{
	// Old name -> new name
	private final Map<Role, Role> rsubs = new HashMap<>();
	private final Map<MemberName<? extends NonRoleParamKind>, Arg<? extends NonRoleParamKind>>
			asubs = new HashMap<>();
			// Keys are actually simple names (params)
			// args are MessageSigName or DataType (but not MessageSig) -- N.B. substitution may replace sig name with a sig
			// NonRoleParamKind, not NonRoleArgKind, because latter includes AmbigKind due to parsing requirements
			// Better (CHECKME: necessary?) to separate roles and args -- but MessageSigName and DataType need to be distinct (so can group up, cf. NonRoleArgList)

	public Substitutor(List<Role> rold, List<Role> rnew,
			List<MemberName<? extends NonRoleParamKind>> aold,
			List<Arg<? extends NonRoleParamKind>> anew)
	{
		if (rold.size() != rnew.size())
		{
			throw new RuntimeException(
					"Role lists don't match: " + rold + " ; " + rnew);
		}
		if (aold.size() != anew.size())
		{
			throw new RuntimeException(
					"Arg lists don't match: " + aold + " ; " + anew);
		}
		Iterator<Role> i = rnew.iterator();
		rold.forEach(x -> this.rsubs.put(x, i.next()));
		Iterator<Arg<? extends NonRoleParamKind>> j = anew.iterator();
		aold.forEach(x -> this.asubs.put(x, j.next()));
	}

	@Override
	public SType<K, B> visitChoice(Choice<K, B> n)
	{
		List<B> blocks = n.blocks.stream()
				.map(x -> x.visitWith((STypeVisitor<K, B>) this))  // FIXME
				.collect(Collectors.toList());
		return n.reconstruct(n.getSource(), subsRole(n.subj), blocks);
	}

	@Override
	public SType<K, B> visitDirectedInteraction(DirectedInteraction<K, B> n)
	{
		Message msg = n.msg;
		if (msg instanceof MemberName)
		{
			MemberName<?> name = (MemberName<?>) msg;
			if (hasArg(name))
			{
				msg = (Message) subsArg(name);
			}
		}
		return n.reconstruct(n.getSource(), msg, subsRole(n.src),
				subsRole(n.dst));
	}

	@Override
	public SType<K, B> visitDisconnect(DisconnectAction<K, B> n)
	{
		return n.reconstruct(n.getSource(), subsRole(n.left), subsRole(n.right));
	}

	@Override
	public <N extends ProtocolName<K>> SType<K, B> visitDo(Do<K, B, N> n)
	{
		List<Role> roles = n.roles.stream().map(x -> subsRole(x))
				.collect(Collectors.toList());
		List<Arg<? extends NonRoleParamKind>> args = new LinkedList<>();
		for (Arg<? extends NonRoleParamKind> a : n.args) 
		{
			if (a instanceof MemberName<?> && hasArg((MemberName<?>) a))
			{
				if (a instanceof DataType)
				{
					a = subsArg((DataType) a);
				}
				else if (a instanceof MessageSigName)
				{
					a = subsArg((MessageSigName) a);
				}
			}
			args.add(a);
		}
		return n.reconstruct(n.getSource(), n.getProtocolName(), roles, args);
	}
	
	public Role subsRole(Role old)
	{
		return this.rsubs.get(old);
	}
	
	public //<K extends NonRoleParamKind>
			Arg<? extends NonRoleParamKind> subsArg(MemberName<?> old)  
			// ? param more convenient for accepting DataType/MessageSigName params
	{
		return this.asubs.get(old);
	}
	
	public boolean hasArg(MemberName<?> old)
			// ? param more convenient for accepting DataType/MessageSigName params
	{
		return this.asubs.containsKey(old);
	}

	/*@Override
	public String toString()
	{
		return this.rsubs + "; " + this.asubs;
	}

	@Override
	public int hashCode()
	{
		int hash = 1889;
		hash = 31 * hash + this.rsubs.hashCode();
		hash = 31 * hash + this.asubs.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Substitutions))
		{
			return false;
		}
		Substitutions them = (Substitutions) o;
		return this.rsubs.equals(them.rsubs) && this.asubs.equals(them.asubs);
	}*/
}
