package org.scribble2.model.local;

import java.util.List;

import org.scribble2.model.Constants;
import org.scribble2.model.MessageNode;
import org.scribble2.model.MessageTransfer;
import org.scribble2.model.ModelNodeBase;
import org.scribble2.model.del.ModelDelegate;
import org.scribble2.model.name.simple.RoleNode;

public class LocalSend extends MessageTransfer implements LocalInteraction
{
	public LocalSend(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		super(src, msg, dests);
	}

	@Override
	protected LocalSend reconstruct(RoleNode src, MessageNode msg, List<RoleNode> dests)
	{
		ModelDelegate del = del();
		LocalSend ls = new LocalSend(src, msg, dests);
		ls = (LocalSend) ls.del(del);
		return ls;
	}
	
	/*@Override
	public LocalSend leaveGraphBuilding(GraphBuilder builder)
	{
		ProtocolState entry = builder.getEntry();
		ProtocolState exit = builder.getExit();
		
		// FIXME: message interpretation routine should depend on schema
		Message msg = getGraphMessage(builder, this.msg);
		
		UnscopedMulticastSignature msig = new UnscopedMulticastSignature(this.src.toName(), getDestinationRoles(), msg);
		entry.edges.put(msig, exit);
		return this;
	}
	
	protected static Message getGraphMessage(GraphBuilder builder, MessageNode msg)
	{
		if (msg instanceof MessageSignatureNameNodes)
		{
			try
			{
				MessageSignatureName msn = ((MessageSignatureNameNodes) msg).toName();
				MessageSignatureName fmsn = builder.getModuleContext().getFullMessageSignatureName(msn);
				MessageSignatureDecl msd = builder.getJobContext().getModule(fmsn.getPrefix()).getMessageSignatureDecl(msn);
				// Bypassing msd.source because Java has source-extName constraint
				ScribbleExternalMessage tmp = (ScribbleExternalMessage) Class.forName(msd.extName).getConstructor().newInstance();
				//return new MessageSignature(tmp.getOperator(), tmp.getPayloadTypes());
				return new MessageSignature(tmp.op, tmp.getSmtpStaticPayloadTypes());
			}
			catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				throw new RuntimeException(e);
			}
		}
		else
		{
			return msg.toMessage();
		}
	}

	@Override
	public LocalSend visitChildren(NodeVisitor nv) throws ScribbleException
	{
		MessageTransfer mt = super.visitChildren(nv);
		return new LocalSend(mt.ct, mt.src, mt.msg, mt.dests);
	}*/

	@Override
	public String toString()
	{
		String s = this.msg + " " + Constants.TO_KW + " " + this.dests.get(0);
		for (RoleNode dest : this.dests.subList(1, this.dests.size()))
		{
			s += ", " + dest;
		}
		return s + ";";
	}

	@Override
	protected ModelNodeBase copy()
	{
		return new LocalSend(this.src, this.msg, this.dests);
	}

	/*@Override
	public void toGraph(GraphBuilder fsmb)
	{
		toFSM(fsmb, this);
	}

	// FIXME: parameters need to be substituted
	protected static void toFSM(GraphBuilder gb, MessageTransfer trans)
	{
		MessageNode msg = (MessageNode) trans.msg;
		if (msg.isParameterNode())
		{
			//throw new RuntimeException("TODO: " + msg);
			//FIXME: need a proper representation of parameters and runtime should do the substitution
			msg = new MessageSignatureNode(null, msg.getOperator(), new Payload(null, Collections.<PayloadElement>emptyList()));
		}
		MessageSignatureNode sig = (MessageSignatureNode) msg;
		Operator op = sig.getOperator();

	  // FIXME: need to substitute potential parameters first
		List<PayloadType> types = new LinkedList<>();
		for (PayloadElement pe : sig.payload.pes)
		{
			if (!pe.type.isPayloadTypeNode())
			{
				throw new RuntimeException("TODO: " + pe);
			}
			PayloadTypeNode ptn = (PayloadTypeNode) pe.type;
			types.add(new PayloadType(ptn.extType));  // FIXME: ExternalPayloadType?
		}

		/*if (trans.dests.size() == 1)
		{
			State root = gb.getRoot();
			State term = gb.getTerm();
			State tmp = term;
			Role src = trans.src.toName();
			Role dest = trans.dests.iterator().next().toName();
			P2PCommunicationSig sig1 = (trans instanceof LocalSend) ? new P2POutputSig(src, dest, op, types)
					: new P2PInputSig(src, dest, op, types);
			root.edges.put(sig1, tmp);
		}
		else
		{
			State term = gb.getTerm();
			State tmp = new State();
			tmp.edges.put(P2PCommunicationSig.TAU, term);
			gb.setTerm(tmp);*
			toFSM((trans instanceof LocalSend), gb, trans.src.toName(),
				trans.getDestinations(), op, types);
			/*gb.setTerm(term);
		}*
	}

	private static void toFSM(boolean isSend, GraphBuilder gb, Role src,
			List<Role> dests, Operator op, List<PayloadType> types)
	{
		State root = gb.getRoot();
		State term = gb.getTerm();
		for (Role dest : dests)
		{
			State tmp = term;
			if (dests.size() > 1)
			{
				tmp = new State();
			}
			P2PCommunicationSig sig = isSend ? new P2POutputSig(src, dest, op, types)
					: new P2PInputSig(src, dest, op, types);
			root.edges.put(sig, tmp);
			if (dests.size() > 1)
			{
				gb.setRoot(tmp);
				List<Role> rem = new LinkedList<>(dests);
				rem.remove(dest);
				toFSM(isSend, gb, src, rem, op, types);
				gb.setRoot(root);
			}
		}
	}*/
}
