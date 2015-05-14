package org.scribble2.model.local;

import java.util.List;

import org.scribble2.model.Constants;
import org.scribble2.model.MessageNode;
import org.scribble2.model.MessageTransfer;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.del.ModelDel;
import org.scribble2.model.name.simple.RoleNode;
import org.scribble2.sesstype.kind.Local;

public class LReceive extends MessageTransfer<Local> implements LSimpleInteractionNode
{
	// HACK: will make dummy dest Nodes, would be better to just be Roles
	public LReceive(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(src, msg, dests);
	}

	@Override
	protected LReceive reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ModelDel del = del();
		LReceive lr = new LReceive(src, msg, dests);
		lr = (LReceive) lr.del(del);
		return lr;
	}
	
	/*@Override
	public LocalReceive leaveGraphBuilding(GraphBuilder builder)
	{
		ProtocolState entry = builder.getEntry();
		ProtocolState exit = builder.getExit();
		// Projection means single self dest role (asymmetric with multicast send)

		// FIXME:
		Message msg = LocalSend.getGraphMessage(builder, this.msg);

		UnscopedMulticastSignature msig = new UnscopedMulticastSignature(this.src.toName(), getDestinationRoles(), msg);
		entry.edges.put(msig, exit);
		return this;
	}*/

	/*@Override
	public LocalReceive visitChildren(NodeVisitor nv) throws ScribbleException
	{
		MessageTransfer mt = super.visitChildren(nv);
		return new LocalReceive(mt.ct, mt.src, mt.msg, mt.dests);
	}*/

	@Override
	public String toString()
	{
		return this.msg + " " + Constants.FROM_KW + " " + this.src + ";";
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new LReceive(this.src, this.msg, this.dests);
	}
	
	/*// Make a LocalCommunication base class
	@Override
	public void toGraph(GraphBuilder gb)
	{
		LocalSend.toFSM(gb, this);
	}*/
}