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
import org.scribble.visit.OffsetSubprotocolVisitor;
import org.scribble.visit.WFChoiceChecker;

@Deprecated
public class WFChoiceEnv extends Env
{
	public static final Role DUMMY_ROLE = new Role("__ROLE");
	public static final Op ROOT_OPERATOR = new Op("__ROOT");
	public static final Op SUBJECT_OPERATOR = new Op("__SUBJECT");

	/*public static final ScopedMessageSignature ROOT_MESSAGESIGNATURE = new ScopedMessageSignature(Scope.EMPTY_SCOPE, ROOT_OPERATOR, Collections.emptyList());
	public static final ScopedMessageSignature SUBJECT_MESSAGESIGNATURE = new ScopedMessageSignature(Scope.EMPTY_SCOPE, SUBJECT_OPERATOR, Collections.emptyList());*/
	/*public static final MessageSignature ROOT_MESSAGESIGNATURE = new MessageSignature(Scope.EMPTY_SCOPE, ROOT_OPERATOR, Payload.EMPTY_PAYLOAD);
	public static final MessageSignature SUBJECT_MESSAGESIGNATURE = new MessageSignature(Scope.EMPTY_SCOPE, SUBJECT_OPERATOR, Payload.EMPTY_PAYLOAD);*/
	public static final MessageSig ROOT_MESSAGESIGNATURE = new MessageSig(ROOT_OPERATOR, Payload.EMPTY_PAYLOAD);
	public static final MessageSig SUBJECT_MESSAGESIGNATURE = new MessageSig(SUBJECT_OPERATOR, Payload.EMPTY_PAYLOAD);
	
	// dest -> src -> msg
	/*private MessageMap<ScopedMessage> initial;  // message transfers recorded here in block envs
	private MessageMap<ScopedMessage> initialInterrupts;  //  interrupts recorded here in interruptible env*/
	/*private MessageMap<Message> initial;  // message transfers recorded here in block envs
	private MessageMap<Message> initialInterrupts;  //  interrupts recorded here in interruptible env*/
	private MessageIdMap initial;  // message transfers recorded here in block envs
	private MessageIdMap initialInterrupts;  //  interrupts recorded here in interruptible env
	
	// FIXME: interrupts
	//private Map<SubprotocolSignature, MessageMap<ScopedMessage>> subsigs;
	//private Map<SubprotocolSignature, MessageMap<Message>> subsigs;
	private Map<SubprotocolSig, MessageIdMap> subsigs;
	private Set<SubprotocolSig> recording;

	//public WellFormedChoiceEnv(JobContext jcontext, ModuleDelegate mcontext)
	public WFChoiceEnv()
	{
		/*super(jcontext, mcontext);
		this.initial = new MessageMap<>();
		this.initialInterrupts = new MessageMap<>();
		this.subsigs = new HashMap<>();
		this.recording = new HashSet<>();*/
		//this(jcontext, mcontext, new MessageMap<>(), new MessageMap<>(), new HashMap<>(), new HashSet<>());
		//this(new MessageMap<>(), new MessageMap<>(), new HashMap<>(), new HashSet<>());
		this(new MessageIdMap(), new MessageIdMap(), new HashMap<>(), new HashSet<>());
	}
	
	/*protected WellFormedChoiceEnv(WellFormedChoiceEnv parent)  // "push" constructor
	{
		/*this.initial = new MessageMap<>(parent.initial);
		this.initialInterrupts = new MessageMap<>(parent.initialInterrupts);
		this.subsigs =
				parent.subsigs.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> new MessageMap<>(parent.subsigs.get(k))));
		this.recording = new HashSet<>(parent.recording);*
		this(parent.getJobContext(), parent.getModuleDelegate(), parent.initial, parent.initialInterrupts, 
				parent.subsigs.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> new MessageMap<>(parent.subsigs.get(k)))),
				parent.recording);
	}*/
	
	protected WFChoiceEnv(//JobContext jcontext, ModuleDelegate mcontext, //WellFormedChoiceEnv root, WellFormedChoiceEnv parent,
			/*MessageMap<ScopedMessage> initial, MessageMap<ScopedMessage> initialInterrupts,
			Map<SubprotocolSignature, MessageMap<ScopedMessage>> subsigs, Set<SubprotocolSignature> recording)*/
			/*MessageMap<Message> initial, MessageMap<Message> initialInterrupts,
			Map<SubprotocolSignature, MessageMap<Message>> subsigs, Set<SubprotocolSignature> recording)*/
			MessageIdMap initial, MessageIdMap initialInterrupts,
			Map<SubprotocolSig, MessageIdMap> subsigs, Set<SubprotocolSig> recording)
	{
		//super(jcontext, mcontext);//, root, parent);
		/*this.initial = new MessageMap<>(initial);
		this.initialInterrupts = new MessageMap<>(initialInterrupts);*/
		this.initial = new MessageIdMap(initial);
		this.initialInterrupts = new MessageIdMap(initialInterrupts);
		this.subsigs =
				//subsigs.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> new MessageMap<>(subsigs.get(k))));
				subsigs.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> new MessageIdMap(subsigs.get(k))));
		this.recording = new HashSet<>(recording);
	}

	@Override
	protected WFChoiceEnv copy()
	{
		return new WFChoiceEnv(//getJobContext(), getModuleDelegate(), //getProtocolDeclEnv(), getParent(),
				this.initial, this.initialInterrupts, this.subsigs, this.recording);
	}
	
	public WFChoiceEnv clear()
	{
		WFChoiceEnv copy = copy();
		copy.initial.clear();
		copy.initialInterrupts.clear();
		return copy;
	}
	
	//public WellFormedChoiceEnv merge(WellFormedChoiceEnv child)
	@Override
	public WFChoiceEnv mergeContext(Env child)
	{
		return mergeContexts(Arrays.asList(child));
	}

	@Override
	//public WellFormedChoiceEnv merge(List<WellFormedChoiceEnv> children)
	public WFChoiceEnv mergeContexts(List<? extends Env> children)
	{
		WFChoiceEnv copy = copy();
		for (WFChoiceEnv child : castList(children))
		{
			merge(this, copy.initial, child.initial);
			merge(this, copy.initialInterrupts, child.initialInterrupts);
			
			for (SubprotocolSig subsig : child.subsigs.keySet())
			{
				if (!copy.subsigs.containsKey(subsig))  // If already recorded from an earlier child, no need to do anything (will be the same result)
				{
					//copy.subsigs.put(subsig, new MessageMap<>());
					copy.subsigs.put(subsig, new MessageIdMap());
				}
				copy.subsigs.get(subsig).merge(child.subsigs.get(subsig));
			}
			// No need to merge recording
		}
		return copy;
	}

	//private static void merge(WellFormedChoiceEnv parent, MessageMap<ScopedMessage> foo, MessageMap<ScopedMessage> child)
	//private static void merge(WellFormedChoiceEnv parent, MessageMap<Message> foo, MessageMap<Message> child)
	private static void merge(WFChoiceEnv parent, MessageIdMap foo, MessageIdMap child)
	{
		for (Role dest : child.getLeftKeys())
		{
			for (Role src : child.getRightKeys(dest))
			{
				/*for (ScopedMessage msg : child.getMessages(dest, src))
				{
					//parent.addMessage(src, dest, msg);  // Checks if dest already enabled or not -- takes care of SUBJECT_OPERATOR case
					//addMessages(parent.initial, src, dest, Arrays.asList(msg));
				}*/
				if (!parent.isEnabled(dest))
				{
					foo.putMessages(dest, src, child.getMessages(dest, src));
				}
			}
		}
	}
	
	public WFChoiceEnv enableRoleForRootProtocolDecl(Role role)
	{
		//return addMessage(WellFormedChoiceEnv.DUMMY_ROLE, role, WellFormedChoiceEnv.ROOT_MESSAGESIGNATURE);
		return addMessage(WFChoiceEnv.DUMMY_ROLE, role, WFChoiceEnv.ROOT_MESSAGESIGNATURE);
	}

	public WFChoiceEnv enableChoiceSubject(Role role)
	{
		return addMessage(WFChoiceEnv.DUMMY_ROLE, role, WFChoiceEnv.SUBJECT_MESSAGESIGNATURE);
	}

	// Rename: more like enable-if-not-already
	//private WellFormedChoiceEnv addMessage(Role src, Role dest, ScopedMessage msg)
	private WFChoiceEnv addMessage(Role src, Role dest, Message msg)
	{
		WFChoiceEnv copy = copy();
		//addMessages(copy.initial, src, dest, Arrays.asList(msg));
		addMessages(copy.initial, src, dest, Arrays.asList(msg.getId()));
		return copy;
	}

	@Override
	public WFChoiceEnv enterContext()
	{
		/*WellFormedChoiceEnv env = new WellFormedChoiceEnv(this);
		//env.initial.clear();
		//env.initialInterrupts.clear();
		return env;*/
		return new WFChoiceEnv(//getJobContext(), getModuleDelegate(),
				this.initial, this.initialInterrupts, 
				//this.subsigs.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> new MessageMap<>(this.subsigs.get(k)))),
				this.subsigs.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> new MessageIdMap(this.subsigs.get(k)))),
				this.recording);
	}

	// FIXME: List/Set argument
	// Means: record message as initial enabling message if dest not already enabled
	//private static void addMessages(MessageMap<ScopedMessage> map, Role src, Role dest, List<ScopedMessage> msgs)
	//private static void addMessages(MessageMap<Message> map, Role src, Role dest, List<Message> msgs)
	private static void addMessages(MessageIdMap map, Role src, Role dest, List<MessageId> msgs)
	{
		if (!map.containsLeftKey(dest))  // FIXME: factor out isEnabled
		{
			map.putMessages(dest, src, new HashSet<>(msgs));
		}
	}

	//public WellFormedChoiceEnv addInterrupt(Role src, Role dest, ScopedMessage msg)
	public WFChoiceEnv addInterrupt(Role src, Role dest, Message msg)
	{
		WFChoiceEnv copy = copy();
		if (!copy.initial.containsLeftKey(dest))
		{
			//copy.initialInterrupts.putMessage(dest, src, msg);
			copy.initialInterrupts.putMessage(dest, src, msg.getId());
		}
		return copy;
	}

	// The "main" public routine: "addMessage taking into account subprotocols"
	//public WellFormedChoiceEnv addMessageForSubprotocol(SubprotocolVisitor spv, Role src, Role dest, ScopedMessage msg)
	public WFChoiceEnv addMessageForSubprotocol(OffsetSubprotocolVisitor<WFChoiceEnv> spv, Role src, Role dest, Message msg)
	{
		WFChoiceEnv copy = copy();
		if (!spv.isStackEmpty())
		{
			//WellFormedChoiceEnv root = this;//getRoot();
			//SubprotocolSignature subsig = spv.peekStack().sig;
			SubprotocolSig subsig = spv.peekStack();
			if (copy.recording.contains(subsig))
			{
				//addMessages(copy.subsigs.get(subsig), src, dest, Arrays.asList(msg));
				addMessages(copy.subsigs.get(subsig), src, dest, Arrays.asList(msg.getId()));
			}
		}
		//addMessage(src, dest, msg);
		//addMessages(copy.initial, src, dest, Arrays.asList(msg));
		addMessages(copy.initial, src, dest, Arrays.asList(msg.getId()));
		return copy;
	}

	//public WellFormedChoiceEnv enterDo(ScopedSubprotocolSignature subsig) throws ScribbleException
	//public WellFormedChoiceEnv enterDoContext(SubprotocolSignature subsig) throws ScribbleException
	public WFChoiceEnv enterDoContext(WFChoiceChecker checker) throws ScribbleException
	{
		WFChoiceEnv copy = copy();
		//SubprotocolSignature subsig = checker.peekStack().sig;
		//SubprotocolSignature subsig = checker.peekStack().sig;
		SubprotocolSig subsig = checker.peekStack();
		if (!copy.subsigs.containsKey(subsig))
		{
			copy.recording.add(subsig);
			//copy.subsigs.put(subsig, new MessageMap<>());
			copy.subsigs.put(subsig, new MessageIdMap());
		}
		//checker.setEnv(copy);
		return copy;
	}

	public WFChoiceEnv leaveWFChoiceCheck(WFChoiceChecker checker) throws ScribbleException
	{
		WFChoiceEnv copy = copy();
		//ScopedSubprotocolSignature subsig = checker.peekStack();
		SubprotocolSig subsig = checker.peekStack();
		//if (copy.recording.contains(subsig.sig))
		if (copy.recording.contains(subsig))
		{
			copy.recording.remove(subsig);
		}

		//int isCycle = checker.isCycle();
		//if (isCycle != -1)
		if (checker.isCycle())
		{
			//addSubprotocolEnabled(subsig);
			//List<ScopedSubprotocolSignature> stack = checker.getStack();
			List<SubprotocolSig> stack = checker.getStack();
			stack = stack.subList(0, stack.size() - 1);
			//Scope scope = stack.get(stack.size() - 1).scope;  // Doesn't include final cycle sig scope
			/*for (int i = stack.size() - 2; i >= 0; i--)
			{
				SubprotocolSignature tmp = stack.get(i);
				if (tmp.equals(subsig))
				{
					break;
				}
				addSubprotocolEnabled(tmp);
			}*/
			int entry = checker.getCycleEntryIndex();
			//Scope prev = (entry == 0) ? Scope.ROOT_SCOPE : stack.get(entry).scope;
			for (int i = checker.getCycleEntryIndex(); i < stack.size(); i++)
			{
				//ScopedSubprotocolSignature ssubsig = stack.get(i);
				SubprotocolSig ssubsig = stack.get(i);
				/*if (!prev.equals(ssubsig.scope))
				{
					scope = new Scope(scope, ssubsig.scope.getSimpleName());
					prev = ssubsig.scope;
				}*/
				//copy.addSubprotocolEnabled(scope, ssubsig.sig);
				//copy.addSubprotocolEnabled(scope, ssubsig);
				copy.addSubprotocolEnabled(ssubsig);
			}
		}

		return copy;
		//ev.setEnv(copy);
	}
	
	// No defensive copy
	//private void addSubprotocolEnabled(Scope scope, SubprotocolSignature subsig)
	private void addSubprotocolEnabled(SubprotocolSig subsig)
	{
		//MessageMap<ScopedMessage> enabled = this.subsigs.get(subsig);
		//MessageMap<Message> enabled = this.subsigs.get(subsig);
		MessageIdMap enabled = this.subsigs.get(subsig);
		for (Role dest : enabled.getLeftKeys())
		{
			for (Role src : enabled.getRightKeys(dest))
			{
				// Take do-scopes into account
				//enabled.getMessages(dest, src).stream().map((sm) -> new ScopedMessageSignature(scope, sm.op, sm.payload)).collect(Collectors.toList());
				//List<ScopedMessage> sms = new LinkedList<>();
				//List<Message> sms = new LinkedList<>();
				List<MessageId> sms = new LinkedList<>();
				//for (ScopedMessage sm : enabled.getMessages(dest, src))
				//for (Message sm : enabled.getMessages(dest, src))
				for (MessageId sm : enabled.getMessages(dest, src))
				{
					/*if (sm.isParameter())
					{
						ScopedMessageParameter p = (ScopedMessageParameter) sm;
						sms.add(new ScopedMessageParameter(scope, p.getKindEnum(), p.toString()));
					}
					else
					{
						ScopedMessageSignature smsg = (ScopedMessageSignature) sm;
						sms.add(new ScopedMessageSignature(scope, smsg.op, smsg.payload));
					}*/
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

	//public MessageMap<ScopedMessage> getEnabled()
	//public MessageMap<Message> getEnabled()
	public MessageIdMap getEnabled()
	{
		//MessageMap<ScopedMessage> tmp = new MessageMap<>(this.initial);
		//MessageMap<Message> tmp = new MessageMap<>(this.initial);
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
	
	/*@Override
	public WellFormedChoiceEnv getProtocolDeclEnv()
	{
		return (WellFormedChoiceEnv) super.getProtocolDeclEnv();
	}

	@Override
	public WellFormedChoiceEnv push()
	{
		return (WellFormedChoiceEnv) super.push();
	}
	
	@Override
	public WellFormedChoiceEnv pop()
	{
		return (WellFormedChoiceEnv) super.pop();
	}
	
	@Override
	public WellFormedChoiceEnv getParent()
	{
		return (WellFormedChoiceEnv) super.getParent();
	}*/
	
	/* // Maybe declare these in Env interface -- contract is to update the checker's env using defensive copies accordingly
	public void enter(EnvDelegationNode en, WellFormedChoiceChecker checker)
	{
		//checker.setEnv(new WellFormedChoiceEnv(this));
		checker.setEnv(push());
	}

	public void leave(EnvDelegationNode en, WellFormedChoiceChecker checker)
	{
		//en.setEnv(copy());
		en.setEnv(this);
		checker.setEnv(pop());  // No need to copy and merge? Because the corresponding push was already a copy? -- only true if handling block envs only here?
	}

	public void enter(Choice<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> cho, 
			WellFormedChoiceChecker checker)
	{
		//WellFormedChoiceEnv env = new WellFormedChoiceEnv(this);
		WellFormedChoiceEnv env = push();
		env.initial.clear();
		env.initialInterrupts.clear();
		env = env.enableChoiceSubject(cho.subj.toName());
		checker.setEnv(env);
	}

	public void leave(Choice<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> cho,
			WellFormedChoiceChecker checker)
	{
		List<WellFormedChoiceEnv> benvs =
				cho.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.getEnv()).collect(Collectors.toList());
		WellFormedChoiceEnv merged = merge(benvs);
		cho.setEnv(merged);
		WellFormedChoiceEnv parent = pop();
		parent = parent.merge(merged);
		checker.setEnv(parent);
		
		// FIXME: cho.setEnv(this/parent) ?  merge blocks into a copy, set copy to node, merge copy to parent?
		// -- working so far without that because blocks are the only nodes we get the envs from manually (cf. from the env visitor)
		// -- do by calling the base enter/leave methods -- delegate from node to env for base methods, but override in node for node specific routines
	}

	public void leave(Recursion<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> rec,
			WellFormedChoiceChecker checker)
	{
		//WellFormedChoiceEnv parent = checker.getEnv().getParent();
		WellFormedChoiceEnv merged = merge((WellFormedChoiceEnv) rec.block.getEnv());
		rec.setEnv(merged);
		WellFormedChoiceEnv parent = pop();
		parent = parent.merge(merged);
		checker.setEnv(parent);
	}

	public void leave(Parallel<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>> par,
			WellFormedChoiceChecker checker)
	{
		List<WellFormedChoiceEnv> benvs =
				par.blocks.stream().map((b) -> (WellFormedChoiceEnv) b.getEnv()).collect(Collectors.toList());
		WellFormedChoiceEnv merged = merge(benvs);
		par.setEnv(merged);
		WellFormedChoiceEnv parent = pop();
		parent = parent.merge(benvs);
		checker.setEnv(parent);
	}

	public void leave(Interruptible<? extends ProtocolBlock<? extends InteractionSequence<? extends InteractionNode>>, ? extends Interrupt> intt,
			WellFormedChoiceChecker checker)
	{
		WellFormedChoiceEnv ienv = checker.getEnv();
		WellFormedChoiceEnv merged = merge((WellFormedChoiceEnv) intt.block.getEnv());
		merged.initial.merge(ienv.initialInterrupts);
		intt.setEnv(merged);

		WellFormedChoiceEnv parent = ienv.pop();
		parent = parent.merge(merged);
		checker.setEnv(parent);
	}

	public void enter(Do doo, WellFormedChoiceChecker checker)
	{
		WellFormedChoiceEnv copy = copy();
		SubprotocolSignature subsig = checker.peekStack().sig;
		if (!copy.subsigs.containsKey(subsig))
		{
			copy.recording.add(subsig);
			copy.subsigs.put(subsig, new MessageMap<>());
		}
		checker.setEnv(copy);
	}

	public void leave(Do doo, WellFormedChoiceChecker checker)
	{
		WellFormedChoiceEnv copy = copy();
		ScopedSubprotocolSignature subsig = checker.peekStack();
		if (copy.recording.contains(subsig.sig))
		{
			copy.recording.remove(subsig.sig);
		}

		//int isCycle = checker.isCycle();
		//if (isCycle != -1)
		if (checker.isCycle())
		{
			//addSubprotocolEnabled(subsig);
			List<ScopedSubprotocolSignature> stack = checker.getStack();
			stack = stack.subList(0, stack.size() - 1);
			Scope scope = stack.get(stack.size() - 1).scope;  // Doesn't include final cycle sig scope
			/*for (int i = stack.size() - 2; i >= 0; i--)
			{
				SubprotocolSignature tmp = stack.get(i);
				if (tmp.equals(subsig))
				{
					break;
				}
				addSubprotocolEnabled(tmp);
			}* /
			int entry = checker.getCycleEntryIndex();
			Scope prev = (entry == 0) ? Scope.ROOT_SCOPE : stack.get(entry).scope;
			for (int i = checker.getCycleEntryIndex(); i < stack.size(); i++)
			{
				ScopedSubprotocolSignature ssubsig = stack.get(i);
				if (!prev.equals(ssubsig.scope))
				{
					scope = new Scope(scope, ssubsig.scope.getSimpleName());
					prev = ssubsig.scope;
				}
				copy.addSubprotocolEnabled(scope, ssubsig.sig);
			}
		}

		//return copy;
		checker.setEnv(copy);
	}*/
	
	private static List<WFChoiceEnv> castList(List<? extends Env> envs)
	{
		return envs.stream().map((e) -> (WFChoiceEnv) e).collect(Collectors.toList());
	}
}
