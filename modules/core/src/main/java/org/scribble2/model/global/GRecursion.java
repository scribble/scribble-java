package org.scribble2.model.global;

import org.scribble2.model.ProtocolBlock;
import org.scribble2.model.Recursion;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.simple.RecursionVarNode;
import org.scribble2.sesstype.kind.Global;

//public class GlobalRecursion extends Recursion<GlobalProtocolBlock> implements CompoundGlobalInteractionNode
public class GRecursion extends Recursion<Global> implements GCompoundInteractionNode
{
	//public GlobalRecursion(RecursionVarNode recvar, GlobalProtocolBlock block)
	public GRecursion(RecursionVarNode recvar, ProtocolBlock<Global> block)
	{
		//super(ct, recvar, block, null, null);
		super(recvar, block);
	}

	@Override
	//protected GlobalRecursion reconstruct(RecursionVarNode recvar, GlobalProtocolBlock block)
	protected GRecursion reconstruct(RecursionVarNode recvar, ProtocolBlock<Global> block)
	{
		ModelDel del = del();
		GRecursion gr = new GRecursion(recvar, block);
		gr = (GRecursion) gr.del(del);
		return gr;
	}

	@Override
	protected GRecursion copy()
	{
		return new GRecursion(this.recvar, this.block);
	}

	/*public GlobalRecursion(CommonTree ct, RecursionVarNode recvar, GlobalProtocolBlock block, CompoundInteractionNodeContext cicontext)
	{
		super(ct, recvar, block, cicontext);
	}*/

	/*protected GlobalRecursion(CommonTree ct, RecursionVarNode recvar, GlobalProtocolBlock block, CompoundInteractionNodeContext cicontext, Env env)
	{
		super(ct, recvar, block, cicontext, env);
	}

	@Override
	protected GlobalRecursion reconstruct(CommonTree ct, RecursionVarNode recvar, GlobalProtocolBlock block, CompoundInteractionNodeContext cicontext, Env env)
	{
		return new GlobalRecursion(ct, recvar, block, cicontext, env);
	}
	
	/*@Override
	public GlobalRecursion leaveContextBuilding(NodeContextBuilder builder) throws ScribbleException
	{
		Recursion<GlobalProtocolBlock> rec = super.leaveContextBuilding(builder);
		return new GlobalRecursion(rec.ct, rec.recvar, rec.block, (CompoundInteractionNodeContext) rec.getContext());
	}* /

	@Override
	public GlobalRecursion leaveWFChoiceCheck(WellFormedChoiceChecker checker) throws ScribbleException
	{
		MessageMap<ScopedMessage> outer = checker.getEnv().getEnabled();
		MessageMap<ScopedMessage> inner = ((WellFormedChoiceEnv) this.block.getEnv()).getEnabled();
		Set<Role> tmp1 = outer.getLeftKeys();
		Set<Role> tmp2 = inner.getLeftKeys();
		if (!tmp1.equals(tmp2))
		{
			Set<Role> tmp = new HashSet<>(tmp2);
			tmp.removeAll(tmp1);
			throw new ScribbleException("Bad recursive role enabling: " + tmp);
		}
		
		/*Recursion<GlobalProtocolBlock> rec = super.leaveWFChoiceCheck(checker);
		return new GlobalRecursion(rec.ct, rec.recvar, rec.block, rec.getContext(), rec.getEnv());* /
		return (GlobalRecursion) super.leaveWFChoiceCheck(checker);
	}
	
	@Override
	public GlobalRecursion leaveProjection(Projector proj) //throws ScribbleException
	{
		RecursionVarNode recvar = new RecursionVarNode(null, this.recvar.toName().toString());
		LocalProtocolBlock block = (LocalProtocolBlock) ((ProjectionEnv) this.block.getEnv()).getProjection();	
		LocalRecursion projection = null;
		if (!block.isEmpty())
		{
			List<LocalInteraction> lis = block.seq.actions;
			if (!(lis.size() == 1 && lis.get(0) instanceof Continue))
			{
				projection = new LocalRecursion(null, recvar, block);
			}
		}
		this.setEnv(new ProjectionEnv(proj.getJobContext(), proj.getModuleContext(), projection));
		return this;
	}
	
	/*@Override
	public LocalRecursion project(Projector proj) throws ScribbleException
	{
		RoleCollector rc = new RoleCollector(proj.job, proj.getEnv());  // env only used for subprotocol stack
		rc.visit(this.block);
		if (!rc.getRoles().contains(proj.getRole())) // Handles projection of continue
		{
			return null;
		}
		LocalProtocolBlock block = (LocalProtocolBlock) proj.visit(this.block);
		if (block == null)
		{
			return null;
		}
		if (block.seq.ins.size() == 1 && (block.seq.ins.get(0) instanceof LocalContinue))
		{
			return null;
		}
		return new LocalRecursion(null, this.lab, block);
	}*/

	/*@Override
	public GlobalRecursion visitChildren(NodeVisitor nv) throws ScribbleException
	{
		Recursion<GlobalProtocolBlock> rec = super.visitChildren(nv);
		return new GlobalRecursion(rec.ct, rec.recvar, rec.block, rec.getContext(), rec.getEnv());
	}*/
}
