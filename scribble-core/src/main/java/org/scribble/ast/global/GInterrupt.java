/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.ast.global;

import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.Constants;
import org.scribble.ast.Interrupt;
import org.scribble.ast.MessageNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.sesstype.kind.Global;

public class GInterrupt extends Interrupt implements GSimpleInteractionNode
{
	/*public static final Function<Interrupt, GlobalInterrupt> toGlobalInterrupt =
			(Interrupt interr) -> (GlobalInterrupt) interr;*/
	
	public GInterrupt(CommonTree source, RoleNode src, List<MessageNode> msgs)
	{
		//this(ct, src, msgs, null, null);
		//this(t, src, msgs, null, null);
		super(source, src, msgs);
	}

	/*// Destination Roles is "type info", not a syntax element -- could wrap in a generic "type info" object
	//public GlobalInterrupt(CommonTree ct, RoleNode src, List<MessageNode> msgs)//, List<Role> dests)
	public GlobalInterrupt(CommonTree ct, RoleNode src, List<MessageNode> msgs, GlobalInterruptContext icontext)
	{
		//super(ct, src, msgs);//, dests);
		super(ct, src, msgs, icontext);
	}*/

	/*protected GlobalInterrupt(CommonTree ct, RoleNode src, List<MessageNode> msgs, GlobalInterruptContext icontext, Env env)
	{
		//super(ct, src, msgs);//, dests);
		super(ct, src, msgs, icontext, env);
	}

	//@Override  // LocalInterrupt has a different signature
	protected GlobalInterrupt reconstruct(CommonTree ct, RoleNode src, List<MessageNode> msgs, GlobalInterruptContext icontext, Env env)
	{
		return new GlobalInterrupt(ct, src, msgs, icontext, env);
	}

	@Override
	public GlobalInterrupt leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Role src = this.src.toName();
		if (!checker.getEnv().isEnabled(src))
		{
			throw new ScribbleException("Role not enabled: " + src);
		}
		return (GlobalInterrupt) super.leaveWFChoiceCheck(checker);
		/*Interrupt interr = super.leaveWFChoiceCheck(checker);
		return new GlobalInterrupt(interr.ct, interr.src, interr.msgs, interr.getContext(), interr.getEnv());* /
	}

	@Override
	public GlobalInterrupt leaveProjection(Projector proj) //throws ScribbleException
	{
		Role self = proj.peekSelf();
		Role srcrole = this.src.toName();
		List<Role> destroles = ((GlobalInterruptContext) getContext()).getDestinations();
		LocalInterrupt projection = null;
		if (srcrole.equals(self) || destroles.contains(self))
		{
			RoleNode src = new RoleNode(null, this.src.toName().toString());  // FIXME: project by visiting
			List<MessageNode> msgs =
					this.msgs.stream().map((m) -> (MessageNode) ((ProjectionEnv) m.getEnv()).getProjection()).collect(Collectors.toList());
			List<RoleNode> dests =
					destroles.stream().map((d) -> new RoleNode(null, d.toString())).collect(Collectors.toList());
			if (srcrole.equals(self))
			{
				projection = new LocalThrows(null, src, msgs, dests);
			}
			else if (destroles.contains(self))
			{
				projection = new LocalCatches(null, src, msgs, dests);
			}
		}
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	/*@Override
	public GlobalInterrupt leaveContextBuilding(Node parent, NodeContextBuilder builder) throws ScribbleException
	{
		GlobalInterrupt interr = (GlobalInterrupt) super.leaveContextBuilding(parent, builder);
		ProtocolBlockContext bcontext = (ProtocolBlockContext) ((GlobalInterruptible) parent).block.getContext();
		
		System.out.println("3: " + bcontext);
		
		List<Role> dests = new LinkedList<>(bcontext.getRoles());
		// FIXME: also include other interrupt sources? (would be better to do in GlobalInterruptible)
		dests.remove(this.src.toName());
		return new GlobalInterrupt(interr.ct, interr.src, interr.msgs, dests);
	}*/
	
	/*@Override
	public GlobalInterrupt substitute(Substitutor subs) throws ScribbleException
	{
		Interrupt interr = super.substitute(subs);
		return new GlobalInterrupt(interr.ct, interr.src, interr.msgs, interr.dests);
	}
	
	@Override
	public GlobalInterrupt checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		GlobalInterrupt gi = (GlobalInterrupt) super.checkWellFormedness(wfc);
		Env env = wfc.getEnv();
		Role src = this.src.toName();
		if (!env.roles.isRoleEnabled(src))
		{
			throw new ScribbleException("Interrupt role not enabled: " + src);
		}
		for (MessageNode msg : this.msgs)
		{
			// Duplicated from GlobalMesssageTransfer
			if (msg.isParameterNode())
			{
				//MessageSignatureParameter param = ((ParameterNode) msg).toMessageSignatureParameter();
				Parameter param = ((ParameterNode) msg).toName();
				if (!env.params.isParameterDeclared(param))
				{
					throw new ScribbleException("Bad parameter: " + param);
				}
				ParameterDecl.Kind pdkind = env.params.getParameterKind(((ParameterNode) msg).toName());
				if (!pdkind.equals(ParameterDecl.Kind.SIG))
				{
					throw new ScribbleException("GlobalMessageTransfer type parameter should be of SIG kind, not: " + pdkind);
				}
			}
		}
		Set<Role> dests = new HashSet<>();
		dests.addAll(env.ops.getSources());
		dests.addAll(env.ops.getAllDestinations());
		dests.remove(this.src.toName());
		return new GlobalInterrupt(gi.ct, gi.src, gi.msgs, new LinkedList<>(dests));
	}
	
	@Override
	public LocalNode project(Projector proj)
	{
		Role role = proj.getRole();
		if (this.src.toName().equals(role))
		{
			return new LocalThrows(null, this.src, this.msgs, this.dests);
		}
		else if (this.dests.contains(role))
		{
			return new LocalCatches(null, this.src, this.msgs, this.dests);
		}
		return null;
	}

	@Override
	public GlobalInterrupt collectRoles(RoleCollector rc) throws ScribbleException
	{
		rc.addRole(this.src.toName());
		for (Role dest : this.dests)
		{
			rc.addRole(dest);
		}
		return (GlobalInterrupt) super.collectRoles(rc);
	}* /
	
	@Override
	public GlobalInterrupt visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Interrupt interr = super.visitChildren(nv);
		//return new GlobalInterrupt(interr.ct, interr.src, interr.msgs); //, interr.dests);
		//return new GlobalInterrupt(interr.ct, interr.src, interr.msgs, interr.getContext(), interr.getEnv());
		return reconstruct(interr.ct, interr.src, interr.msgs, interr.getContext(), interr.getEnv());
	}*/

	// FIXME: shouldn't be needed, but here due to Eclipse bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=436350
	@Override
	public Global getKind()
	{
		return GSimpleInteractionNode.super.getKind();
	}
		
	@Override
	public String toString()
	{
		return this.getMessages().stream().map((msg) -> msg.toString()).collect(Collectors.joining(", "))
				+ " " + Constants.BY_KW + " " + this.src + ";";
	}
}
