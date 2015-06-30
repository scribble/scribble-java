package org.scribble.visit;

import java.util.HashMap;
import java.util.Map;

import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.context.ModuleContext;
import org.scribble.ast.global.GDo;
import org.scribble.ast.global.GInteractionSeq;
import org.scribble.del.global.GDoDel;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.RecVar;
import org.scribble.visit.env.InlineProtocolEnv;

public class InlineProtocolTranslator extends SubprotocolVisitor<InlineProtocolEnv>
{
	private Map<SubprotocolSig, RecVar> recvars = new HashMap<>();
	
	public InlineProtocolTranslator(Job job)
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
		if (child instanceof GDo)
		{
			return visitOverrideForGDo((GInteractionSeq) parent, (GDo) child);
		}
		return super.visitForSubprotocols(parent, child);
	}

	protected GDo visitOverrideForGDo(GInteractionSeq parent, GDo child) throws ScribbleException
	{
		if (!isCycle())
		{
			//return (GDo) super.visitForSubprotocols(parent, child);
			// Duplicated from SubprotocolVisitor#visitOverrideFoDo -- FIXME: factor out this facility better
			ModuleContext mcontext = getModuleContext();
			ProtocolDecl<? extends ProtocolKind> pd = child.getTargetProtocolDecl(getJobContext(), mcontext);
			ScribNode seq = applySubstitutions(pd.def.block.seq);
			seq.accept(this);
			pushEnv(popEnv().setTranslation(((InlineProtocolEnv) seq.del().env()).getTranslation()));
			return child;
		}
		return ((GDoDel) child.del()).visitForSubprotocolInlining(this, child);  // If cycle, super routine does nothing anyway, so we can just replace with new stuff here
	}
	
	@Override
	protected void subprotocolEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.subprotocolEnter(parent, child);
		child.del().enterInlineProtocolTranslation(parent, child, this);
	}
	
	@Override
	protected ScribNode subprotocolLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveInlineProtocolTranslation(parent, child, this, visited);
		return super.subprotocolLeave(parent, child, visited);
	}
	
	public Map<SubprotocolSig, RecVar> getRecVars()
	{
		return this.recvars;
	}
	
	public void addRecVar()
	{
		
	}
}
