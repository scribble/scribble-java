package org.scribble.visit.env;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.Message;
import org.scribble.sesstype.MessageSig;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.SubprotocolSig;
import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;
import org.scribble.util.MessageIdMap;
import org.scribble.visit.InlinedProtocolVisitor;
import org.scribble.visit.WFChoiceChecker;

public class InlinedWFChoiceEnv extends Env
{
	public static final Role DUMMY_ROLE = new Role("__ROLE");
	public static final Op ROOT_OPERATOR = new Op("__ROOT");
	public static final Op SUBJECT_OPERATOR = new Op("__SUBJECT");

	public static final MessageSig ROOT_MESSAGESIGNATURE = new MessageSig(ROOT_OPERATOR, Payload.EMPTY_PAYLOAD);
	public static final MessageSig SUBJECT_MESSAGESIGNATURE = new MessageSig(SUBJECT_OPERATOR, Payload.EMPTY_PAYLOAD);
	
	// dest -> src -> msg
	private MessageIdMap initial;  // message transfers recorded here in block envs
	private MessageIdMap initialInterrupts;  //  interrupts recorded here in interruptible env
	
	// FIXME: interrupts
	private Map<SubprotocolSig, MessageIdMap> subsigs;
	private Set<SubprotocolSig> recording;

	public InlinedWFChoiceEnv()
	{
		this(new MessageIdMap(), new MessageIdMap(), new HashMap<>(), new HashSet<>());
	}
	
	protected InlinedWFChoiceEnv(
			MessageIdMap initial, MessageIdMap initialInterrupts,
			Map<SubprotocolSig, MessageIdMap> subsigs, Set<SubprotocolSig> recording)
	{
		this.initial = new MessageIdMap(initial);
		this.initialInterrupts = new MessageIdMap(initialInterrupts);
		this.subsigs =
				subsigs.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> new MessageIdMap(subsigs.get(k))));
		this.recording = new HashSet<>(recording);
	}

	@Override
	protected InlinedWFChoiceEnv copy()
	{
		return new InlinedWFChoiceEnv(this.initial, this.initialInterrupts, this.subsigs, this.recording);
	}
	
	public InlinedWFChoiceEnv clear()
	{
		InlinedWFChoiceEnv copy = copy();
		copy.initial.clear();
		copy.initialInterrupts.clear();
		return copy;
	}
	
	@Override
	public InlinedWFChoiceEnv mergeContext(Env child)
	{
		return mergeContexts(Arrays.asList(child));
	}

	@Override
	public InlinedWFChoiceEnv mergeContexts(List<? extends Env> children)
	{
		InlinedWFChoiceEnv copy = copy();
		for (InlinedWFChoiceEnv child : castList(children))
		{
			merge(this, copy.initial, child.initial);
			merge(this, copy.initialInterrupts, child.initialInterrupts);
			
			for (SubprotocolSig subsig : child.subsigs.keySet())
			{
				if (!copy.subsigs.containsKey(subsig))  // If already recorded from an earlier child, no need to do anything (will be the same result)
				{
					copy.subsigs.put(subsig, new MessageIdMap());
				}
				copy.subsigs.get(subsig).merge(child.subsigs.get(subsig));
			}
			// No need to merge recording
		}
		return copy;
	}

	private static void merge(InlinedWFChoiceEnv parent, MessageIdMap foo, MessageIdMap child)
	{
		for (Role dest : child.getLeftKeys())
		{
			for (Role src : child.getRightKeys(dest))
			{
				if (!parent.isEnabled(dest))
				{
					foo.putMessages(dest, src, child.getMessages(dest, src));
				}
			}
		}
	}
	
	public InlinedWFChoiceEnv enableRoleForRootProtocolDecl(Role role)
	{
		return addMessage(InlinedWFChoiceEnv.DUMMY_ROLE, role, InlinedWFChoiceEnv.ROOT_MESSAGESIGNATURE);
	}

	public InlinedWFChoiceEnv enableChoiceSubject(Role role)
	{
		return addMessage(InlinedWFChoiceEnv.DUMMY_ROLE, role, InlinedWFChoiceEnv.SUBJECT_MESSAGESIGNATURE);
	}

	// Rename: more like enable-if-not-already
	private InlinedWFChoiceEnv addMessage(Role src, Role dest, Message msg)
	{
		InlinedWFChoiceEnv copy = copy();
		addMessages(copy.initial, src, dest, Arrays.asList(msg.getId()));
		return copy;
	}

	@Override
	public InlinedWFChoiceEnv enterContext()
	{
		return new InlinedWFChoiceEnv(//getJobContext(), getModuleDelegate(),
				this.initial, this.initialInterrupts, 
				this.subsigs.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> new MessageIdMap(this.subsigs.get(k)))),
				this.recording);
	}

	// FIXME: List/Set argument
	// Means: record message as initial enabling message if dest not already enabled
	private static void addMessages(MessageIdMap map, Role src, Role dest, List<MessageId> msgs)
	{
		if (!map.containsLeftKey(dest))  // FIXME: factor out isEnabled
		{
			map.putMessages(dest, src, new HashSet<>(msgs));
		}
	}

	public InlinedWFChoiceEnv addInterrupt(Role src, Role dest, Message msg)
	{
		InlinedWFChoiceEnv copy = copy();
		if (!copy.initial.containsLeftKey(dest))
		{
			copy.initialInterrupts.putMessage(dest, src, msg.getId());
		}
		return copy;
	}

	// The "main" public routine: "addMessage taking into account subprotocols"
	public InlinedWFChoiceEnv addMessageForSubprotocol(InlinedProtocolVisitor<InlinedWFChoiceEnv> spv, Role src, Role dest, Message msg)
	{
		InlinedWFChoiceEnv copy = copy();
		addMessages(copy.initial, src, dest, Arrays.asList(msg.getId()));
		return copy;
	}

	public InlinedWFChoiceEnv enterDoContext(WFChoiceChecker checker) throws ScribbleException
	{
		InlinedWFChoiceEnv copy = copy();
		SubprotocolSig subsig = checker.peekStack();
		if (!copy.subsigs.containsKey(subsig))
		{
			copy.recording.add(subsig);
			copy.subsigs.put(subsig, new MessageIdMap());
		}
		return copy;
	}

	public InlinedWFChoiceEnv leaveWFChoiceCheck(WFChoiceChecker checker) throws ScribbleException
	{
		InlinedWFChoiceEnv copy = copy();
		SubprotocolSig subsig = checker.peekStack();
		if (copy.recording.contains(subsig))
		{
			copy.recording.remove(subsig);
		}

		if (checker.isCycle())
		{
			List<SubprotocolSig> stack = checker.getStack();
			stack = stack.subList(0, stack.size() - 1);
			for (int i = checker.getCycleEntryIndex(); i < stack.size(); i++)
			{
				SubprotocolSig ssubsig = stack.get(i);
				copy.addSubprotocolEnabled(ssubsig);
			}
		}

		return copy;
	}
	
	// No defensive copy
	private void addSubprotocolEnabled(SubprotocolSig subsig)
	{
		MessageIdMap enabled = this.subsigs.get(subsig);
		for (Role dest : enabled.getLeftKeys())
		{
			for (Role src : enabled.getRightKeys(dest))
			{
				// Take do-scopes into account
				List<MessageId> sms = new LinkedList<>();
				for (MessageId sm : enabled.getMessages(dest, src))
				{
					sms.add(sm);
				}
				addMessages(this.initial, src, dest, sms);
			}
		}
	}
	
	public boolean isEnabled(Role role)
	{
		return this.initial.containsLeftKey(role);
	}

	public MessageIdMap getEnabled()
	{
		MessageIdMap tmp = new MessageIdMap(this.initial);
		tmp.merge(this.initialInterrupts);
		return tmp;
	}
	
	// FIXME: move to basic name checking pass (not WF choice)
	public boolean isRoleBound(Role role)
	{
		return this.initial.containsLeftKey(role);  // FIXME: this.initial only contains enabled, not declared
	}

	@Override
	public String toString()
	{
		return "initial=" + this.initial.toString() + ", initialInterrupts=" + this.initialInterrupts.toString();
	}
	
	private static List<InlinedWFChoiceEnv> castList(List<? extends Env> envs)
	{
		return envs.stream().map((e) -> (InlinedWFChoiceEnv) e).collect(Collectors.toList());
	}
}
