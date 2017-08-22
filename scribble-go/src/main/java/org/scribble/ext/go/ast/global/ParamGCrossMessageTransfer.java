package org.scribble.ext.go.ast.global;

import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.scribble.ast.AstFactory;
import org.scribble.ast.Constants;
import org.scribble.ast.MessageNode;
import org.scribble.ast.MessageTransfer;
import org.scribble.ast.global.GMessageTransfer;
import org.scribble.ast.local.LNode;
import org.scribble.ast.name.simple.RoleNode;
import org.scribble.del.ScribDel;
import org.scribble.ext.go.ast.ParamAstFactory;
import org.scribble.main.ScribbleException;
import org.scribble.type.kind.Global;
import org.scribble.type.name.Role;
import org.scribble.visit.AstVisitor;

// Subsumes scatter/gather, src/dest range length one
public class ParamGCrossMessageTransfer extends GMessageTransfer //implements ParamActionAssertNode
{
	//public final ParamAssertion ass;  // null if not specified -- should be the "true" formula in principle, but not syntactically
			// Duplicated in, e.g., ALSend -- could factour out to in Del, but need to consider immutable pattern
	
	// All >= 0; end >= start
	public final int srcRangeStart;
	public final int srcRangeEnd;     // Inclusive
	public final int destRangeStart;
	public final int destRangeEnd;    // Inclusive

	public ParamGCrossMessageTransfer(CommonTree source, RoleNode src, MessageNode msg, RoleNode dest, 
			int srcRangeStart, int srcRangeEnd, int destRangeStart, int destRangeEnd)
	{
		super(source, src, msg, Arrays.asList(dest));
		this.srcRangeStart = srcRangeStart;
		this.srcRangeEnd = srcRangeEnd;
		this.destRangeStart = destRangeStart;
		this.destRangeEnd = destRangeEnd;
	}
	
	@Override
	public LNode project(AstFactory af, Role self)
	{
		/*LNode proj = super.project(af, self);
		if (proj instanceof LInteractionSeq)  // From super, if self communication
		{
			throw new RuntimeException("[assrt] Self-communication not supported: " + proj);
		}
		else if (proj instanceof LSend)
		{
			LSend ls = (LSend) proj;
			proj = ((ParamAstFactory) af).ParamLSend(ls.getSource(), ls.src, ls.msg, ls.getDestinations(), this.ass);
		}
		return proj;*/
		return super.project(af, self);  // FIXME
	}

	@Override
	protected ParamGCrossMessageTransfer copy()
	{
		return new ParamGCrossMessageTransfer(this.source, this.src, this.msg, getDestinations().get(0),
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}
	
	@Override
	public ParamGCrossMessageTransfer clone(AstFactory af)
	{
		RoleNode src = this.src.clone(af);
		MessageNode msg = this.msg.clone(af);
		RoleNode dest = getDestinations().get(0).clone(af);
		return ((ParamAstFactory) af).ParamGCrossMessageTransfer(this.source, src, msg, dest,
				this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}

	@Override
	public ParamGCrossMessageTransfer reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		throw new RuntimeException("[assrt] Shouldn't get in here: " + this);
	}

	public ParamGCrossMessageTransfer reconstruct(RoleNode src, MessageNode msg, RoleNode dest, 
			int srcRangeStart, int srcRangeEnd, int destRangeStart, int destRangeEnd)
	{
		ScribDel del = del();
		ParamGCrossMessageTransfer gmt = new ParamGCrossMessageTransfer(this.source, src, msg, dest, 
				srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd);
		gmt = (ParamGCrossMessageTransfer) gmt.del(del);
		return gmt;
	}

	@Override
	public MessageTransfer<Global> visitChildren(AstVisitor nv) throws ScribbleException
	{
		RoleNode src = (RoleNode) visitChild(this.src, nv);
		MessageNode msg = (MessageNode) visitChild(this.msg, nv);
		RoleNode dest = (RoleNode) visitChild(getDestinations().get(0), nv);
		return reconstruct(src, msg, dest, this.srcRangeStart, this.srcRangeEnd, this.destRangeStart, this.destRangeEnd);
	}

	@Override
	public String toString()
	{
		return this.msg + Constants.FROM_KW + " "
				+ this.src + "[" + this.srcRangeStart + ".." + this.srcRangeEnd + "]"
				+ " " + Constants.TO_KW + " "
				+ getDestinations().get(0) + "[" + this.destRangeStart + ".." + this.destRangeEnd + "]"
				+ ";";
	}
}
