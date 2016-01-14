package org.scribble.visit;

import java.util.HashMap;
import java.util.Map;

import org.scribble.ast.Do;
import org.scribble.ast.InteractionSeq;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GDo;
import org.scribble.ast.local.LDo;
import org.scribble.del.global.GDoDel;
import org.scribble.del.local.LDoDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.env.InlineProtocolEnv;

public class ProtocolDefInliner extends SubprotocolVisitor<InlineProtocolEnv>
{
	private Map<SubprotocolSig, RecVar> recvars = new HashMap<>();
	
	public ProtocolDefInliner(Job job)
	{
		super(job);
	}
	
	public RecVar getRecVar(SubprotocolSig subsig)
	{
		return this.recvars.get(subsig);
	}

	public void setRecVar(SubprotocolSig subsig)
	{
		this.recvars.put(subsig, new RecVar(newRecVarId(subsig)));
	}

	public void removeRecVar(SubprotocolSig subsig)
	{
		this.recvars.remove(subsig);
	}
	
	private String newRecVarId(SubprotocolSig sig)
	{
		// Hacky
		return sig.toString()
				.replace('.', '_')
				.replace('<', '_')
				.replace('>', '_')
				.replace('(', '_')
				.replace(')', '_')
				.replace(' ', '_')
				.replace(',', '_');
	}
	
	@Override
	protected InlineProtocolEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new InlineProtocolEnv();
	}

	@Override
	public ScribNode visitForSubprotocols(ScribNode parent, ScribNode child) throws ScribbleException
	{
		if (child instanceof Do)
		{
			return visitOverrideForDo((InteractionSeq<?>) parent, (Do<?>) child);
		}
		return super.visitForSubprotocols(parent, child);
	}

	protected Do<?> visitOverrideForDo(InteractionSeq<?> parent, Do<?> child) throws ScribbleException
	{
		if (!isCycle())
		{
			//return (GDo) super.visitForSubprotocols(parent, child);
			// Duplicated from SubprotocolVisitor#visitOverrideFoDo and modified to access discarded env -- FIXME: factor out this facility better
			JobContext jc = getJobContext();
			ModuleContext mc = getModuleContext();
			ProtocolDecl<? extends ProtocolKind> pd = child.getTargetProtocolDecl(jc, mc);
			ScribNode seq = applySubstitutions(pd.def.block.seq.clone());
			seq = seq.accept(this);
			pushEnv(popEnv().setTranslation(((InlineProtocolEnv) seq.del().env()).getTranslation()));
			return child;
		}

		// If cycle, super routine does nothing anyway, so we can just replace with new stuff here
		return (child instanceof GDo)
				? ((GDoDel) child.del()).visitForSubprotocolInlining(this, (GDo) child)
				: ((LDoDel) child.del()).visitForSubprotocolInlining(this, (LDo) child);
	}
	
	@Override
	protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterProtocolInlining(parent, child, this);
	}
	
	@Override
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveProtocolInlining(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
	}
}
