package org.scribble.ast.global;

import java.util.List;

import org.scribble.ast.Choice;
import org.scribble.ast.ScribNodeBase;
import org.scribble.ast.ProtocolBlock;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.sesstype.kind.Global;

//public class GlobalChoice extends Choice<GlobalProtocolBlock> implements CompoundGlobalInteractionNode
public class GChoice extends Choice<Global> implements GCompoundInteractionNode
{
	// Move parser specific constructors (i.e. no context or env yet) to a parser node factory?
	//public GlobalChoice(RoleNode subj, List<GlobalProtocolBlock> blocks)
	public GChoice(RoleNode subj, List<? extends ProtocolBlock<Global>> blocks)
	{
		//this(ct, subj, blocks, null, null);
		super(subj, blocks);
	}

	@Override
	//protected Choice<GlobalProtocolBlock> reconstruct(RoleNode subj, List<GlobalProtocolBlock> blocks)
	protected GChoice reconstruct(RoleNode subj, List<? extends ProtocolBlock<Global>> blocks)
	{
		ScribDel del = del();
		GChoice gc = new GChoice(subj, blocks);
		gc = (GChoice) gc.del(del);
		return gc;
	}

	@Override
	protected ScribNodeBase copy()
	{
		return new GChoice(this.subj, this.blocks);
	}

	/*protected GlobalChoice(CommonTree ct, RoleNode subj, List<GlobalProtocolBlock> blocks, CompoundInteractionNodeContext ccontext)
	{
		super(ct, subj, blocks, ccontext);
	}*/

	/*protected GlobalChoice(CommonTree ct, RoleNode subj, List<GlobalProtocolBlock> blocks, CompoundInteractionNodeContext cicontext, Env env)
	{
		super(ct, subj, blocks, cicontext, env);
	}

	@Override
	protected GlobalChoice reconstruct(CommonTree ct, RoleNode subj, List<GlobalProtocolBlock> blocks, CompoundInteractionNodeContext cicontext, Env env)
	{
		return new GlobalChoice(ct, subj, blocks, cicontext, env);
	}
	
	/*@Override
	public GlobalChoice leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		Choice<GlobalProtocolBlock> cho = super.leaveContextBuilding(builder);
		//return new GlobalChoice(cho.ct, cho.subj, cho.blocks, cho.getContext());
		return reconstruct(cho.ct, cho.subj, cho.blocks, cho.getContext(), cho.getEnv());
	}* /

	@Override
	public GlobalChoice leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		Role subj = this.subj.toName();
		if (!checker.getEnv().getParent().isEnabled(subj))
		{
			throw new ScribbleException("Subject not enabled: " + subj);
		}
		
		Map<Role, Set<ScopedMessage>> seen = null;
		for (WellFormedChoiceEnv benv :
			this.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.getEnv()).collect(Collectors.toList()))
		{
			MessageMap<ScopedMessage> enabled = benv.getEnabled();
			
			Set<Role> dests = enabled.getLeftKeys();
			dests.remove(subj);
			if (seen == null)
			{
				seen = new HashMap<>();
				//dests.forEach((left) -> seen.put(left, enabled.getMessages(left)));
				for (Role dest : dests)
				{
					seen.put(dest, enabled.getMessages(dest));
				}
			}
			else
			{
				if (dests.isEmpty())
				{
					if (!seen.keySet().isEmpty())
					{
						throw new ScribbleException("Mismatched role enabling: " + seen.keySet());
					}
				}
				else 
				{
					for (Role dest : dests)
					{
						if (!seen.containsKey(dest))
						{
							throw new ScribbleException("Mismatched role enabling: " + dest);
						}
						Set<ScopedMessage> current = seen.get(dest);
						Set<ScopedMessage> next = enabled.getMessages(dest);
						for (Message msg : next)
						{
							if (current.contains(msg))
							{
								throw new ScribbleException("Duplicate initial choice message for " + dest + ": " + msg);
							}
						}
						current.addAll(next);
					}
				}
			}
		}
		
		/*Choice<GlobalProtocolBlock> cho = super.leaveWFChoiceCheck(checker);
		//return new GlobalChoice(cho.ct, cho.subj, cho.blocks, cho.getContext(), cho.getEnv());
		// .. reconstruct* /
		return (GlobalChoice) super.leaveWFChoiceCheck(checker);
	}
	
	@Override
	public GlobalChoice leaveProjection(Projector proj) //throws ScribbleException
	{
		RoleNode subj = new RoleNode(null, this.subj.toName().toString());  // Inconsistent to copy role nodes manually, but do via children visiting for other children
		List<LocalProtocolBlock> blocks = 
				this.blocks.stream().map((b) -> (LocalProtocolBlock) ((ProjectionEnv) b.getEnv()).getProjection()).collect(Collectors.toList());	
		LocalChoice projection = null;  // Individual GlobalInteractionNodes become null if not projected -- projected seqs and blocks are never null though
		if (!blocks.get(0).isEmpty())  // WF allows this
		{
			projection = new LocalChoice(null, subj, blocks);
		}
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}*/

	/*@Override
	public GlobalChoice checkWellFormedness(WellFormednessChecker wfc) throws ScribbleException
	{
		Env env = wfc.getEnv();
		List<GlobalProtocolBlock> blocks = new LinkedList<>();
		Set<Role> enabled = null;
		MapToSet<Role, Operator> enabling = null;

		Set<Scope> seen = null;
		Map<Role, Role> enablers = new HashMap<>();

		for (ProtocolBlock pb : this.blocks)
		{
			GlobalProtocolBlock block = (GlobalProtocolBlock) pb;
			wfc.setEnv(new Env(env));
			GlobalProtocolBlock visited = (GlobalProtocolBlock) wfc.visit(block);

			
			//if (!env.getParent().roles.canEnable())
			if (!env.roles.canEnable())
			{
				//if (!env.getParent().roles.getEnabled().equals(wfc.getEnv().roles.getEnabled()))
				if (visited.getEnv().roles.getEnabled().size() != 1)
				{
					Set<Role> deb = new HashSet<>(wfc.getEnv().roles.getEnabled());
					deb.retainAll(env.roles.getEnabled());
					throw new ScribbleException("Roles enabled in inappropriate context: " + deb);  // FIXME: protocol decl default all enabled
				}
			}
			if (!env.getParent().roles.canEnable())
			{
				for (Role role : visited.getEnv().roles.getEnabled())
				{
					if (!env.getParent().roles.isRoleEnabled(role))
					{
						throw new ScribbleException("Role enabled in inappropriate context: " + role);
					}
				}
			}
			
			
			RolesEnv tmp0 = visited.getEnv().roles;
			Set<Role> tmp1 = tmp0.getEnabled();
			MapToSet<Role, Operator> tmp2 = new MapToSet<>();
			
			for (Role role : tmp1)
			{
				tmp2.putAll(role, tmp0.getEnabling(role));

				Role enabler = visited.getEnv().roles.getEnabler(role);
				if (!enablers.containsKey(role))
				{
					enablers.put(role, enabler);
				}
				else
				{
					if (!enablers.get(role).equals(enabler))
					{
						// FIXME: not working for recursive-do (protocol decl default enabler is null)
						//throw new ScribbleException(role + " enabled by mismatching source roles: " + enablers.get(role) + ", " + enabler);
					}
				}
			}
			if (enabled == null)
			{
				enabled = tmp1;
				enabling = tmp2;
				
				seen = new HashSet<>(visited.getEnv().scopes.getScopes());
			}
			else
			{
				if (!enabled.equals(tmp1))
				{
					throw new ScribbleException("Mismatch between GlobalChoice block roles: " + enabled + ", " + tmp1);
				}
				for (Role role : enabled)
				{
					Set<Operator> tmp3 = new HashSet<>(enabling.get(role));
					

					for (Operator op1 : tmp3)
					{
						/*String text1 = op1.text.indexOf(".") == -1 ? op1.text : op1.text.substring(op1.text.lastIndexOf(".") + 1);
						for (Operator op2 : tmp2.values)
						{
							String text2 = op2.text.indexOf(".") == -1 ? op2.text : op2.text.substring(op2.text.lastIndexOf(".") + 1);
							if (text1.equals(text2) && !op1.equals(op2))
							{
								throw new ScribbleException("Scope mismatch for enabling " + role + ": " + op1 + ", " + op2);
							}
						}*
						String pre1 = op1.text.indexOf(".") == -1 ? "" : op1.text.substring(0, op1.text.lastIndexOf("."));
						for (Operator op2 : tmp2.values)
						{
							String pre2 = op2.text.indexOf(".") == -1 ? "" : op2.text.substring(0, op2.text.lastIndexOf("."));
							if (!pre1.equals(pre2))
							{
								throw new ScribbleException("Scope mismatch for enabling " + role + ": " + op1 + ", " + op2);
							}
						}
					}
					

					tmp3.retainAll(tmp2.get(role));
					/*tmp3.remove(RolesEnv.DEFAULT_ENABLING_OP);
					if (!tmp3.isEmpty())
					{
						throw new ScribbleException("Initial operators for " + role + " not disjoint: " + tmp3);
					}*
					for (Operator op : tmp3)
					{
						if (!op.text.endsWith("__DEF_OP"))  // HACK
						{
							throw new ScribbleException("Initial operators for " + role + " not disjoint: " + tmp3);
						}
					}
					enabling.putAll(role, tmp2.get(role));
				}
				
				// Should just do scope uniqueness per protocol in scopeinserter (no need with env-visit)
				Set<Scope> foo1 = visited.getEnv().scopes.getScopes();
				Set<Scope> foo = new HashSet<>(foo1);
				foo.retainAll(seen);
				{
					if (!foo.isEmpty())
					{
						throw new ScribbleException("Duplicate scopes: " + foo);
					}
				}
				seen.addAll(foo1);
			}

			blocks.add(visited);
		}
		
		seen.retainAll(env.getParent().scopes.getScopes());
		if (!seen.isEmpty())
		{
			throw new ScribbleException("Duplicate scope(s): " + seen);
		}
		// leave (pop/merge) will add scopes to parent
		
		wfc.setEnv(env);
		return new GlobalChoice(this.ct, this.subj, blocks);
	}

	@Override
	public GlobalChoice substitute(Substitutor subs) throws ScribbleException
	{
		Choice cho = super.substitute(subs);
		@SuppressWarnings("unchecked")
		List<GlobalProtocolBlock> blocks = (List<GlobalProtocolBlock>) cho.blocks;
		return new GlobalChoice(cho.ct, cho.subj, blocks);
	}
	
	@Override
	//public LocalChoice project(Projector proj) throws ScribbleException
	public LocalNode project(Projector proj) throws ScribbleException
	{
		Env env = proj.getEnv();
		List<LocalProtocolBlock> visited = new LinkedList<>();
		
		
		RoleCollector rc = new RoleCollector(proj.job, proj.getEnv());  // env only used for subprotocol stack
		rc.visit(this.blocks.get(0));
		if (!rc.getRoles().contains(proj.getRole()))
		{
			LocalProtocolBlock vis = null;
			boolean first = true;
			for (ProtocolBlock block : this.blocks) 
			{
				proj.setEnv(new Env(env));
				LocalProtocolBlock b = (LocalProtocolBlock) proj.visit((GlobalProtocolBlock) block);
				if (first)
				{
					vis = b;
					first = false;
				}
				else
				{
					if (vis == null)
					{
						if (b != null)
						{
							throw new ScribbleException("Non-projectable choice for " + proj.getRole() + ": " + this);
						}
					}
					else
					{
						if (b == null)
						{
							throw new ScribbleException("Non-projectable choice for " + proj.getRole() + ": " + this);
						}
						if (!vis.toString().equals(b.toString()))
						{
							throw new ScribbleException("Non-projectable choice for " + proj.getRole() + ": " + this);
						}
					}
				}
			}
			proj.setEnv(env);
			if (vis == null)
			{
				return null;
			}
			return (LocalInteractionSequence) vis.seq;
		}
		else
		{

			for (ProtocolBlock block : this.blocks) 
			{
				//LocalProtocolBlock b = (LocalProtocolBlock) proj.visit((GlobalProtocolBlock) block);

				proj.setEnv(new Env(env));
				LocalProtocolBlock b = (LocalProtocolBlock) proj.visit((GlobalProtocolBlock) block);

				if (b != null)
				{
					visited.add(b);
				}
			}
			proj.setEnv(env);
			if (visited.isEmpty())
			{
				return null;
			}
			return new LocalChoice(null, this.subj, visited);

		}
	}*/

	/*@Override
	public GlobalChoice visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Choice<GlobalProtocolBlock> cho = super.visitChildren(nv);
		//List<GlobalProtocolBlock> blocks = GlobalProtocolBlock.toGlobalProtocolBlockList.apply(cho.blocks);
		//List<GlobalProtocolBlock> blocks = cho.blocks.stream().map(GlobalProtocolBlock.toGlobalProtocolBlock).collect(Collectors.toList());
		return new GlobalChoice(cho.ct, cho.subj, cho.blocks, cho.getContext(), cho.getEnv());
		//return reconstruct(cho.ct, cho.subj, cho.blocks, cho.getContext(), cho.getEnv());
	}*/
}
